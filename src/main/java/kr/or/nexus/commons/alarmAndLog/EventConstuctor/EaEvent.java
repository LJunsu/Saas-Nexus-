package kr.or.nexus.commons.alarmAndLog.EventConstuctor;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class EaEvent extends ApplicationEvent {
	private String groupId;
	private String senderId;
	private String senderName;
	private String receiverId;
	private String projectName;
	private String creationReason;
	private String comment;
	private String eaId;
	
	
	public EaEvent (Object source,String groupId ,String senderId, String senderName, String receiverId, String creationReason
			,String comment,String eaId,String projectName) {
		super(source);
		this.groupId = groupId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.creationReason = creationReason;
		this.comment = comment;
		this.eaId = eaId;
		this.projectName = projectName;
		this.senderName = senderName;
		
	}
	
	
	
}
