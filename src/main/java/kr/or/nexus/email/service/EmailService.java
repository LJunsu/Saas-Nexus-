package kr.or.nexus.email.service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.vo.GroupRefusalVO;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

	private GoogleOAuthService googleOAuthService;

    // Setter 메서드
    public void setGoogleOAuthService(GoogleOAuthService googleOAuthService) {
        this.googleOAuthService = googleOAuthService;
    }
    
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

    public void sendEmail(String to, String groupId, String groupName) throws Exception {
        Gmail service = googleOAuthService.getGmailService();
        String userId = "me";
        String emailAddress = "dlfkgjh7@gmail.com";
        String subject = "nexus 초대 메시지";
        
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "https://nexus"; // 기본값으로 프로덕션 URL 설정
        }
        
        String fullUrl = baseUrl + "/group/"+groupId+"/insertGroupMember";
//        String bodyText = "<a href='"+ fullUrl +"'>"+groupId+" 초대받기</a>";
        String bodyText = String.format(
        	    "<html>" +
        	    "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
        	    "<div style='max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px;'>" +
        	    "<h2 style='color: #4a4a4a;'>%s에 초대되셨습니다!</h2>" +
        	    "<p>안녕하세요,</p>" +
        	    "<p>%s 그룹에서 귀하를 초대하였습니다. 함께 협업하고 아이디어를 공유할 수 있기를 기대합니다.</p>" +
        	    "<p style='margin: 30px 0;'>" +
        	    "<a href='%s' style='background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold;'>" +
        	    "초대 수락하기" +
        	    "</a>" +
        	    "</p>" +
        	    "<p>문의사항이 있으시면 언제든 연락 주시기 바랍니다.</p>" +
        	    "<p>감사합니다!</p>" +
        	    "</div>" +
        	    "</body>" +
        	    "</html>",
        	    groupName, groupName, fullUrl
        	);
        MimeMessage emailContent = createEmail(to, emailAddress, subject, bodyText);
        Message message = createMessageWithEmail(emailContent);
        service.users().messages().send(userId, message).execute();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText, "UTF-8", "html");
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }
    
    public void sendId(String to, String memberId) throws Exception {
        Gmail service = googleOAuthService.getGmailService();
        String userId = "me";
        String emailAddress = "dlfkgjh7@gmail.com";
        String subject = "nexus ID 찾기";
        
        String bodyText = "nexus 회원님의 ID는 "+memberId+" 입니다.";
        MimeMessage emailContent = createEmail(to, emailAddress, subject, bodyText);

        Message message = createMessageWithEmail(emailContent);
        service.users().messages().send(userId, message).execute();
    }
    
    public void sendPw(String to, String memberPw) throws Exception {
        Gmail service = googleOAuthService.getGmailService();
        String userId = "me";
        String emailAddress = "dlfkgjh7@gmail.com";
        String subject = "nexus password 찾기";
        
        String bodyText = "nexus 회원님의 임시 password는 "+memberPw+" 입니다. 비밀번호를 변경할 것을 권장드립니다.";
        MimeMessage emailContent = createEmail(to, emailAddress, subject, bodyText);

        Message message = createMessageWithEmail(emailContent);
        service.users().messages().send(userId, message).execute();
    }
    
    public void sendGroupRefusal(GroupRefusalVO groupRefusalVO) throws Exception {
        Gmail service = googleOAuthService.getGmailService();
        String to = "keliong1015@gmail.com";
        String userId = "me";
        String emailAddress = "dlfkgjh7@gmail.com";
        String subject = "nexus 그룹신청이 거절되었습니다.";
        
        String bodyText = groupRefusalVO.getRefusalContent();
        MimeMessage emailContent = createEmail(to, emailAddress, subject, bodyText);

        Message message = createMessageWithEmail(emailContent);
        service.users().messages().send(userId, message).execute();
    }
}