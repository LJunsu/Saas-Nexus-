package kr.or.nexus.codeShare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriTemplate;
import org.springframework.web.util.UrlPathHelper;

import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeShareHandler extends TextWebSocketHandler {

	@Autowired
	private Map<String, List<WebSocketSession>> sessionMap;
	@Autowired
    private Map<String, List<String>> userSessionsIdMap;
	@Autowired
    private Map<String, List<String>> userSessionsNameMap;
	@Autowired
    private Map<String, String> codeMap;

	public List<String> memberIdReturn(WebSocketSession session) {
		String memberId = null;
		String memberName = null;

		SecurityContext securityContext = (SecurityContext) session.getAttributes().get("SPRING_SECURITY_CONTEXT");
		if (securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
				MemberManagementVO realUser = wrapper.getRealUser();
				memberId = realUser.getMemberId();
				memberName = realUser.getMemberName();
			}
		}
		List<String> userInfoList = new ArrayList();
		userInfoList.add(memberId);
		userInfoList.add(memberName);
		return userInfoList;
	}

	private void broadcastUserList(String codeShareId) throws Exception {
	    StringBuilder userListMessage = new StringBuilder();

        List<String> userSessionsIdList = userSessionsIdMap.get(codeShareId);
        List<String> userSessionsNameList = userSessionsNameMap.get(codeShareId);

	    for (int i = 0; i < userSessionsIdList.size(); i++) {
	        userListMessage.append(userSessionsIdList.get(i))
	            .append(":")
	            .append(userSessionsNameList.get(i));
	        if (i < userSessionsIdList.size() - 1) {
	            userListMessage.append(",");
	        }
	    }

	    List<WebSocketSession> sessions = sessionMap.get(codeShareId);

	    for (WebSocketSession sess : sessions) {
	        if (sess.isOpen()) {
	            sess.sendMessage(new TextMessage("USER_LIST:" + userListMessage.toString()));
	        }
	    }
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		log.info("codeShareSocket 연결, {}", session.getUri());
		String mappingUri = "/grp/{groupId}/project/{projectId}/codeShare/{codeShareId}";
		UriTemplate temp = new UriTemplate(mappingUri);
		Map<String, String> tempMap = temp.match(session.getUri().toString());
		String codeShareId = tempMap.get("codeShareId");

		sessionMap.putIfAbsent(codeShareId, new ArrayList<>());
        userSessionsIdMap.putIfAbsent(codeShareId, new ArrayList<>());
        userSessionsNameMap.putIfAbsent(codeShareId, new ArrayList<>());

        sessionMap.get(codeShareId).add(session);

	    List<String> userInfo = memberIdReturn(session);
	    String memberId = userInfo.get(0);
	    String memberName = userInfo.get(1);

        userSessionsIdMap.get(codeShareId).add(memberId);
        userSessionsNameMap.get(codeShareId).add(memberName);

        String code = codeMap.get(codeShareId);

	    if (code != null && !code.isEmpty()) {
	        session.sendMessage(new TextMessage(code));
	    }

	    broadcastUserList(codeShareId);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		String mappingUri = "/grp/{groupId}/project/{projectId}/codeShare/{codeShareId}";
		UriTemplate temp = new UriTemplate(mappingUri);
		Map<String, String> tempMap = temp.match(session.getUri().toString());
		String codeShareId = tempMap.get("codeShareId");

		String code = message.getPayload();
        codeMap.put(codeShareId, code);

		for (WebSocketSession sess : sessionMap.get(codeShareId)) {
	        if (sess.isOpen() && !sess.equals(session)) {
	            sess.sendMessage(new TextMessage(code));
	        }
	    }
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("codeShareSocket 닫힘");

		String mappingUri = "/grp/{groupId}/project/{projectId}/codeShare/{codeShareId}";
		UriTemplate temp = new UriTemplate(mappingUri);
		Map<String, String> tempMap = temp.match(session.getUri().toString());
		String codeShareId = tempMap.get("codeShareId");

		sessionMap.get(codeShareId).remove(session);

	    List<String> userInfo = memberIdReturn(session);
	    String memberId = userInfo.get(0);
	    String memberName = userInfo.get(1);

        userSessionsIdMap.get(codeShareId).remove(memberId);
        userSessionsNameMap.get(codeShareId).remove(memberName);
        codeMap.remove(codeShareId);

		broadcastUserList(codeShareId);
	}

}