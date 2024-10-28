package kr.or.nexus.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.email.service.EmailService;
import kr.or.nexus.email.service.GoogleOAuthService;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private GoogleOAuthService oauthService;
    
    @PostMapping("send")
    public void sendEmail(@RequestBody Map<String, Object> payload) {
    	
    	String toUser = (String) payload.get("toUser");
    	String groupId = (String) payload.get("groupId");
    	String groupName = (String) payload.get("groupName");
        try {
            emailService.sendEmail(toUser, groupId, groupName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}