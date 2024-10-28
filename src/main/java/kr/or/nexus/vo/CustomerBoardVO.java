package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class CustomerBoardVO implements Serializable {
	
    private String csId;

    private String memberId;

    private String csTitle;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate csDate;

    private String csDelyn;

    private String csContent;
    
    private List<CustomerBoardAnswersVO> customerBoardAnswersVO; 

    private static final long serialVersionUID = 1L;
}