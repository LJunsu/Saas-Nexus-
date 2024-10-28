package kr.or.nexus.commons.alarm;

import java.net.Socket;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import kr.or.nexus.commons.mapper.AlarmMapper;
import kr.or.nexus.commons.userSessionManager.UserSessionManager;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlarmHandler extends TextWebSocketHandler {

	@Resource(name = "webSocketSessionList")
	private List<WebSocketSession> webSocketSessionList;

	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();

	@Autowired
	private Map<String, MemberManagementVO> alarmSessionListAndMemId;

	private List<String> memberList = new ArrayList<String>();

	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private UserSessionManager userSessionManager;
	@Autowired
	private AlarmMapper mapper;
	@Autowired
	private Map<Integer,String> AdminSessionList;
	int cnt = 1;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {// 클라이언트와 서버가 연결
		Authentication authentication =(Authentication) session.getPrincipal();
		String sessionId = session.getId();
		if (authentication != null) {
			webSocketSessionList.add(session);
			CustomUserVOWrapper princiapl  =(CustomUserVOWrapper) authentication.getPrincipal();
			MemberManagementVO memberInfo= princiapl.getRealUser();
			
			log.info("memberInfo@@@@@@@@@@@@@@", memberInfo);
			log.info("session정보 {} Socket 생성{}", memberInfo.getMemberId() , session.getId());
			memberList.add(memberInfo.getMemberName());
			alarmSessionListAndMemId.put(sessionId, memberInfo);
			log.info("webSocketSessionList {}, alarmSessionListAndMemId {}", webSocketSessionList.size() , alarmSessionListAndMemId.size());
			userSessionManager.addUser(memberInfo.getMemberId());
			List<MemberManagementVO> roledAdminList = mapper.getRoleAdminList();
			roledAdminList.forEach(admin->{
				if(memberInfo.getMemberId().equals(admin.getMemberId())) {
					AdminSessionList.put(cnt, admin.getMemberId());
					cnt++;
				}else {
					AdminSessionList.put(cnt, admin.getMemberId());
					cnt++;
				}
				 
			});
		} else {
			log.info("세션 ID:{}", session.getId());
		}


		for(int i=0; i<memberList.size(); i++) {
			log.info("소켓 세션에있는 인원 {} {}   ", alarmSessionListAndMemId.size(),memberList.get(i));
		}

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {// 메시지

			


	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {// 연결 해제
		log.info("Socket close {}", session.getId());
		webSocketSessionList.remove(session);
		alarmSessionListAndMemId.remove(session.getId());
		
		
		  	Authentication authentication = (Authentication) session.getPrincipal();
	        if (authentication != null) {
	            CustomUserVOWrapper principal = (CustomUserVOWrapper) authentication.getPrincipal();
	            MemberManagementVO memberInfo = principal.getRealUser();
	            String memberId = memberInfo.getMemberId();

	            userSessionManager.removeUser(memberId);
		
	   }
	}

	/**
	 * 세션ID를 가져와서 DB에 저장된 MEMBER의 ID와 매칭시켜주는 메서드
	 *
	 * @param session
	 * @return
	 */

	public List<WebSocketSession> getSessions() {
		log.info("현재 세션 목록 요청: {}개의 세션", webSocketSessionList.size());

		return webSocketSessionList;
	}

}
