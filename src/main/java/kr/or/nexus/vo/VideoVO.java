package kr.or.nexus.vo;

import java.time.LocalDate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "videoConferenceId")
public class VideoVO {
	private String videoAddr; //화상회의 주소
	private String videoConferenceId; //화상회의 ID
	private String projectId; //프로젝트 아이디
	private String videoConferenceCr; //화상회의 생성자
	private LocalDate videoConferenceCrTime; //화상회의 생성날짜
	private String videoConferenceTitle;// 화상회의 제목
	private String videoPassword;// 화상회의 비밀번호
}
