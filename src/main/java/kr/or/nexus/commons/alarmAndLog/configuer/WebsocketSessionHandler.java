package kr.or.nexus.commons.alarmAndLog.configuer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import kr.or.nexus.vo.MemberManagementVO;

/**
 * @author PC-16
 *	세션 ID , 세션ID로 부터 얻은 MEMBERID 공유 객체
 */
@Configuration
public class WebsocketSessionHandler {


    @Bean
    public Map<String,MemberManagementVO> alarmSessionListAndMemId() {
    	return new HashMap<String, MemberManagementVO>();
    }
    
    @Bean
    public Map<Integer,String> AdminSessionList(){
    	return new HashMap<Integer, String>();
    }
    
    @Bean
    public List<String> adminList(){
    	return new ArrayList<String>() ;
    }



}
