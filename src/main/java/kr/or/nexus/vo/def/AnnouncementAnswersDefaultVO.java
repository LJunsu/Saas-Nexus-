package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "boardCmtId")
public class AnnouncementAnswersDefaultVO implements Serializable {
	private int boardCmtId;

	private String boardId;

	private Long parentCmtId;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime cmtCreationDate;

	private String memberId;


	private String cmtContent;

	private static final long serialVersionUID = 1L;

	private String cmtDelyn;

	private String memberName;
}