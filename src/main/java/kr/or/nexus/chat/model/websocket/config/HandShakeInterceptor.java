package kr.or.nexus.chat.model.websocket.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;

public class HandShakeInterceptor extends HttpSessionHandshakeInterceptor{

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
	                               WebSocketHandler chatHandler, Map<String, Object> attributes) throws Exception {


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
	    MemberManagementVO realUser = wrapper.getRealUser();
	    String memberId = realUser.getMemberId();
	    attributes.put("memberId", memberId);
	    System.out.println(memberId);
	    return super.beforeHandshake(request, response, chatHandler, attributes);
	    }
}
