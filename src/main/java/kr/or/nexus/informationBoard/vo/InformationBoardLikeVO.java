package kr.or.nexus.informationBoard.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="ifBoardLike")
public class InformationBoardLikeVO implements Serializable {

	private Integer ifBoardLike;
	private String ifBoardId;
	private String memberId;

}
