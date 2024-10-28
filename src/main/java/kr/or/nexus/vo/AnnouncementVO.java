package kr.or.nexus.vo;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.vo.def.AtchFileVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "boardId")
public class AnnouncementVO {

	private int rnum;

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

	private LocalDate boardCreateDate;
	
	private String memberName;

	@Nullable
	private String attachedFileId;

	private String boardDelyn;

	private String projectId;

	private static final long serialVersionUID = 1L;


	@Nullable
	private AtchFileVO atchFile;
}
