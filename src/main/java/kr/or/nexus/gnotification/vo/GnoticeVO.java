package kr.or.nexus.gnotification.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="boardId")
public class GnoticeVO implements Serializable {
	@NotBlank(groups = UpdateGroup.class)
	private String boardId;
	@NotBlank
	private String boardTitle;
	@NotBlank
	private String boardContent;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime boardCreateDate;

	private String memberName;

	private String boardDelyn;

	private String projectId;

	private static final long serialVersionUID = 1L;


	@Nullable
	private String attachedFileId;       //atchfildId의 저장값을 받기 위해 일부러 반든 String타입

	@Nullable
	private AtchFileVO attachedFile;		//DB에서 꺼내오고 저장하기위한 AtchFileVO객체타입

	@NotBlank
	private String groupId;
	@NotBlank
	private String memberId;

	private List<GnoticeAnswerVO> gnoticeAnswerList;

	private MemberManagementVO managementVO;




//	@Nullable
//	private List<MultipartFile> uploadFiles;



}