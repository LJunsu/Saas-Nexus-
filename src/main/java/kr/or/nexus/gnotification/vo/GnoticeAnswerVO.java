package kr.or.nexus.gnotification.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import kr.or.nexus.validate.groups.DeleteGroup;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.validate.groups.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author PC-16
 *	공지 답글
 */
@Data
@EqualsAndHashCode(of = "boardCmtId")
public class GnoticeAnswerVO implements Serializable  {

	@NotNull(groups = {UpdateGroup.class, DeleteGroup.class})
	private Integer boardCmtId;


	@NotBlank(groups = {InsertGroup.class ,UpdateGroup.class, DeleteGroup.class})
	private String boardId;

	@Nullable
	private Integer parentCmtId;

	private String cmtContent;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private LocalDateTime cmtCreationDate;

	@NotBlank(groups = InsertGroup.class)
	private String memberId;

	private String cmtDelyn;

	private String memberName;



}
