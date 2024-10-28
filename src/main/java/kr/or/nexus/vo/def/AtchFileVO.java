package kr.or.nexus.vo.def;





import java.time.LocalDate;
import java.util.List;

import kr.or.nexus.vo.IssueVO;
import lombok.Data;

/**
 * 한번에 올린 첨부파일 그룹 VO
 *
 */
@Data
public class AtchFileVO {
	
	
	
	private String attachedFileId;
	private LocalDate attachedFileDate;
	private String attachedFileDelyn;
	
	
	
	private IssueVO issueIdx;
	
	
	private List<AtchFileDetailsVO> fileDetails;
	
	
}
