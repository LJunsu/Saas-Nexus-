package kr.or.nexus.security.conf;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import kr.or.nexus.commons.alarmAndLog.EventConstuctor.LoginEvent;
import kr.or.nexus.custom.mapper.CustomMapper;
import kr.or.nexus.group.main.service.GroupMainService;
import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.security.auth.service.CustomUserDetailServiceImpl;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

@EnableWebSecurity
public class SecurityContextConfig {

	@Autowired
	private MemberManagementService service;
	
	@Autowired
	private GroupMainService groupService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Bean
	public UserDetailsService userDetailService(CustomMapper mapper) {
		CustomUserDetailServiceImpl userDetailService = new CustomUserDetailServiceImpl();
		userDetailService.setMapper(mapper);
		return userDetailService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder;
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**").permitAll()
				.antMatchers("/login.do", "/alarm/**", "/chatting/**", "/login/**", "/gcp/**", "/landing.do").permitAll().anyRequest()
				.authenticated()// 그 외 (public)이 아닌 요청 / authenticated : 로그인된.
				.and().formLogin().loginPage("/login.do").usernameParameter("memberId").passwordParameter("memberPw")
				.successHandler((request, response, authentication) -> {
					SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
					MemberManagementVO member = null;
					
					
					
					

					HttpSession session = request.getSession();
					SecurityContext securityContext = SecurityContextHolder.getContext();
					if (securityContext != null) {
						authentication = securityContext.getAuthentication();
						if (authentication != null && authentication.getPrincipal() instanceof CustomUserVOWrapper) {
							CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
							MemberManagementVO realUser = wrapper.getRealUser();
							member = service.findSessionUser(realUser.getMemberId());
							LoginEvent event = new LoginEvent(member,realUser.getMemberId(), realUser.getMemberName(),"로그인");
							publisher.publishEvent(event);
						}
					}
					GroupManagementVO group = groupService.retrieveGroupById(member.getGroupMemberVO().getGroupId());
					session.setAttribute("user", member);
					session.setAttribute("group", group);
					
					// 역할에 따른 리다이렉션 처리
					String redirectUrl;
					if (savedRequest != null && savedRequest.getRedirectUrl().contains("/group/")
							&& savedRequest.getRedirectUrl().contains("/insertGroupMember")) {
						redirectUrl = savedRequest.getRedirectUrl();
					} else {
						if (member != null) {
							String groupId = member.getGroupMemberVO().getGroupId();
							if ("ROLE_ADMIN".equals(member.getMemberRole())) {
								redirectUrl = request.getContextPath() + "/saMain.do";
							} else if ("ROLE_GROUPADMIN".equals(member.getMemberRole())) {
//								redirectUrl = request.getContextPath() + "/group/" + groupId;
								redirectUrl = request.getContextPath() + "/landing.do";
							} else { // ROLE_USER
								if (groupId == null) {
									redirectUrl = request.getContextPath() + "/landing.do";
								} else {
									redirectUrl = request.getContextPath() + "/landing.do";
//									redirectUrl = request.getContextPath() + "/" + groupId + "/memMain.do";
								}
							}
						} else {
							// 사용자 정보가 없는 경우의 처리
							redirectUrl = request.getContextPath() + "/login.do";
						}
					}
					response.sendRedirect(redirectUrl);
				}).and().logout().logoutUrl("/logout.do").logoutSuccessHandler((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK);
				}).invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
		http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
		http.csrf().disable();
		return http.build();
	}

}
