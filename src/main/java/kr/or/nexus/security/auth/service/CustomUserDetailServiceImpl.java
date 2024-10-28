package kr.or.nexus.security.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.or.nexus.custom.mapper.CustomMapper;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.RequiredArgsConstructor;

@Service("userDetailService")
public class CustomUserDetailServiceImpl implements UserDetailsService {

	private CustomMapper mapper;

	public void setMapper(CustomMapper mapper) {
		this.mapper = mapper;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberManagementVO memberVO = mapper.selectCustomUserByName(username);
		
		if(memberVO==null) {
			throw new UsernameNotFoundException(String.format("%s 사용자 없음", username));
		}
		
		return new CustomUserVOWrapper(memberVO);
	}

}
