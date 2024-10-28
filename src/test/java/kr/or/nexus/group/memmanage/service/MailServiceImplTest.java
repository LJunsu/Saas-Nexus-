package kr.or.nexus.group.memmanage.service;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/nexus/spring/*-context.xml")
class MailServiceImplTest {

	@Autowired
    private MemberManagementService memService;
	
	@Autowired
	private MailService service;
	
	@Test
	void testSendHtmlEmail() throws EmailException {
		
		HtmlEmail email = new HtmlEmail();
    	String emailAddress = "dlfkgjh7@gmail.com";
    	String emailPass = "xqav qvru cnlm zdvh";
    	String groupName = "그룹1";
    	String groupId = "GRP001";
    	String to = "hyunseong5576@gmail.com";
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthentication(emailAddress, emailPass);
        email.setSSLOnConnect(true);
        email.setFrom(emailAddress);
        email.setSubject("nexus 초대 메시지");
        
        
        email.setHtmlMsg("ㅇㅇㅇ");
        email.addTo(to);
        email.send();
	}

}
