package kr.or.nexus.vo;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="boardId")
public class NotificationVO {
	@NotBlank
	private String boardId;
	@NotBlank
	private String groupId;
	@NotBlank
	private String memberId;
	@NotBlank
	private String boardTitle;
	@NotBlank
	private String boardContent;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime boardCreateDate;
	
	@Nullable
	private String attachedFileId;
	
	private String boardDelyn;
	
	private String projectId;
	
	private static final long serialVersionUID = 1L;
}
