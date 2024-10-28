package kr.or.nexus.group.memmanage.service;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import kr.or.nexus.vo.MemberManagementVO;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
    private MemberManagementService userRepository;
	
    public String getLoggedInUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            MemberManagementVO user = userRepository.findByUsername(username);
            return user.getMemberEmail();
        } else {
            return null;
        }
    }
    
    @Override
	public void sendHtmlEmail(String to, String groupId, String groupName, String emailPass) throws EmailException {
    	HtmlEmail email = new HtmlEmail();
    	String emailAddress = getLoggedInUserEmail();
//    	String emailAddress = "dlfkgjh7@gmail.com";
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setAuthentication(emailAddress, emailPass);
        email.setSSLOnConnect(true);
        email.setFrom(emailAddress, groupName);
        email.setSubject("nexus 초대 메시지");
        
     // contextPath 포함한 URL 생성
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String fullUrl = baseUrl + "/group/"+groupId+"/insertGroupMember";
        
        email.setHtmlMsg("<a href='"+ fullUrl +"'>초대받기</a>");
        email.addTo(to);
        email.send();
	}

}
