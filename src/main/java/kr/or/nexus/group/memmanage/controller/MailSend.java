package kr.or.nexus.group.memmanage.controller;

import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nexus.group.main.service.GroupMainService;
import kr.or.nexus.group.memmanage.service.MailService;
import kr.or.nexus.vo.def.GroupManagementDefaultVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/sendEmail")
public class MailSend {

    @Autowired
    private MailService mailService;
    
    @Autowired
    private GroupMainService service;

    @PostMapping
    public void sendEmail(@RequestBody Map<String, Object> payload) {
    	
    	String toUser = (String) payload.get("toUser");
    	String groupId = (String) payload.get("groupId");
    	String emailPass = (String) payload.get("emailPass");
    	GroupManagementDefaultVO group = service.retrieveGroup(groupId);
    	String groupName = group.getGroupName();
    	
        try {
        	mailService.sendHtmlEmail(toUser, groupId, groupName, emailPass);
        } catch (EmailException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}