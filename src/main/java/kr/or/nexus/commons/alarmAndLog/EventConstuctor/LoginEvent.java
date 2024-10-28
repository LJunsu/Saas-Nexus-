package kr.or.nexus.commons.alarmAndLog.EventConstuctor;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class LoginEvent extends ApplicationEvent {
private String memId;
private String memName;
private String comment;

	public LoginEvent(Object source, String memId, String memName, String comment) {
		super(source);
		 this.memId = memId;
		 this.memName = memName;
		 this.comment = comment;
	}
	
	
	
	
}
