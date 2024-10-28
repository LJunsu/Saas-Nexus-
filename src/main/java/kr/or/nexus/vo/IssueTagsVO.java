package kr.or.nexus.vo;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "issueTagsId")
public class IssueTagsVO {
	@NotBlank
	private Integer issueTagsId;
	@NotBlank
	private String issueIdx;
	private String issueTagName;
}
