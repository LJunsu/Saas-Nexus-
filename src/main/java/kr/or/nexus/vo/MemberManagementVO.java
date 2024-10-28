package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.socket.WebSocketSession;

import kr.or.nexus.validate.groups.LoginGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = {"projectMemberVO","projectVO", "groupManagementVO", "groupMemberVO"})
@EqualsAndHashCode(of = "memberId")
public class MemberManagementVO implements Serializable {


	@NotBlank(groups = LoginGroup.class)
	private String memberId;

	@NotBlank(groups = LoginGroup.class)
	private String memberPw;

	private String memberName;
	private Integer memberRegno1;
	private Integer memberRegno2;
	private Integer memberZip;
	private String memberAddress1;
	private String memberAddress2;
	private String memberTel;
	private String memberEmail;
	private String memberProfilePict;
	private String memberDescription;
	private String memberAuthQuestion;
	private String memberAuthResponse;
	private String memberDelyn;
	private String memberRole;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate memberJoinDate;


	private GroupManagementVO groupManagementVO;
	private GroupMemberVO groupMemberVO;

	//석준 추가 코드
	private List<ProjectMemberVO> projectMemberVO;
	private List<ProjectVO> projectVO;
	private List<IssueVO> issueVO;


	private WebSocketSession session;

}