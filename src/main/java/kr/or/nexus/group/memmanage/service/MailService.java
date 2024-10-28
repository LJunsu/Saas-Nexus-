package kr.or.nexus.group.memmanage.service;

import org.apache.commons.mail.EmailException;

public interface MailService {

	public String getLoggedInUserEmail();
	
	public void sendHtmlEmail(String to, String groupId, String groupName, String emailPass) throws EmailException;

	
}
