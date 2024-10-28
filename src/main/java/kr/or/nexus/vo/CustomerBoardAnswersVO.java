package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class CustomerBoardAnswersVO implements Serializable {
	
	private int csAnsIdx;
	
	private String csId;
	
	private String csAnsContent;
	
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate csAnsDate;
	
	private String adminId;

	private static final long serialVersionUID = 1L;
}