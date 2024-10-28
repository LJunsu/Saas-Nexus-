package kr.or.nexus.codeShare.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Configuration
//@EnableWebSocket
public class CodeShareConfig {

//    @Bean
//    public List<String> userSessionsIdList() {
//    	return new ArrayList<String>();
//    }
//
//    @Bean
//    public List<String> userSessionsNameList() {
//    	return new ArrayList<String>();
//    }


	@Bean
	public Map<String, List<WebSocketSession>> sessionMap() {
		return new HashMap<String, List<WebSocketSession>>();
	}

    @Bean(name = "userSessionsIdMap")
    public Map<String, List<String>> userSessionsIdMap() {
    	return new HashMap<String, List<String>>();
    }

    @Bean(name = "userSessionsNameMap")
    public Map<String, List<String>> userSessionsNameMap() {
    	return new HashMap<String, List<String>>();
    }

    @Bean
    public Map<String, String> codeMap() {
    	return new HashMap<String, String>();
    }

}