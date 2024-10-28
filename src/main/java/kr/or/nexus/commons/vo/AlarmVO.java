package kr.or.nexus.commons.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.or.nexus.informationBoard.vo.InformationBoardVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="alarmId")
public class AlarmVO implements Serializable {

	private String alarmId;
	private String alarmSender;
	private String alarmSenderN;
	private String projectId;
	private String groupId;
	private String issueIdx;
	private String data;
	private String urldata;
	private String eventtype;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime eventdate;
	private String loginData;
	private String logLogin;
	
	
	
	
	
	
}
