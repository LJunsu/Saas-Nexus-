package kr.or.nexus.informationBoard.vo;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="ifBoardCmtId")
public class InformationBoardAnswersVO implements Serializable {

	private String commentLevel;

	private Integer ifBoardCmtId;
	private String ifBoardId;
	private Integer ifBoardParentCmtId;
	@NotBlank
	@Size(max = 1000)
	private String ifBoardCmtContent;
	private LocalDate ifBoardCmtCrDate;

	private String groupId;
	@NotBlank
	private String memberId;

	private String memberName;

}
