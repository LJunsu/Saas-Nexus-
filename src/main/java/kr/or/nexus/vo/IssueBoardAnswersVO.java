package kr.or.nexus.vo;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "issueCommentId")
public class IssueBoardAnswersVO {
	@NotBlank
	private String issueCommentId;
	@NotBlank
	private String issueIdx;
	private String issueParentCmtId;
	@NotBlank
	private String issueCmtContent;
	@NotBlank
	private String issueCmtWriter;
	@NotBlank
	private LocalDate issueCmtCrDate;
	@NotBlank
	private LocalDate issueCmtModifiedDate;
	
	//석준 추가
	@Nullable
	private Integer answerCount;
	
	
}
