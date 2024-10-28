package kr.or.nexus.codeShare.vo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "codeShareId")
public class codeShareVO implements Serializable {

	private String codeShareId;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDate codeShareDate;
	private String codeShareDelyn;
	private String codeShareContent;
	private String codeShareTitle;
	private String memberId;
	private String groupId;
	private String projectId;
	private String search;
	private String language;

}
