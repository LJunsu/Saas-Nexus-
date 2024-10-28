package kr.or.nexus.informationBoard.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.AtchFileVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = {"ifBoardAnswersList","ifBoardLikeList"})
@EqualsAndHashCode(of="ifBoardId")
public class InformationBoardVO implements Serializable {

	private String ifBoardId;
	@NotBlank
	@Size(max = 50)
	private String ifBoardTitle;
	@NotBlank
	@Size(max = 1000)
	private String ifBoardContent;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate ifBoardCreationDate;
	private String ifBoardDelyn;
	private Integer ifBoardNum;
	private String groupId;
	private String memberId;


	private List<InformationBoardAnswersVO> ifBoardAnswersList;
	private List<InformationBoardLikeVO> ifBoardLikeList;
	private MemberManagementVO member;


	@Nullable
	private String attachedFileId;       //atchfildId의 저장값을 받기 위해 일부러 반든 String타입

	@Nullable
	private AtchFileVO attachedFile;		//DB에서 꺼내오고 저장하기위한 AtchFileVO객체타입


}
