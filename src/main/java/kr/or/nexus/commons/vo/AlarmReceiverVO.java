package kr.or.nexus.commons.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(exclude = "alarmVO")
@EqualsAndHashCode(of = "alarmNo")
@Data
public class AlarmReceiverVO implements Serializable {

	private String alarmNo;
	private String alarmId;
	private String receiverId;
	private String alarmChk;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime alarmChkDateTime;
	
	
	private List<AlarmVO> alarmVO;
	
	
	
	
	
}
