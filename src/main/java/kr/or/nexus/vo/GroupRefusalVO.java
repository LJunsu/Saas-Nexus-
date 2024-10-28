package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupRefusalVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer refusalIdx;
    
    private String refusalContent;
    
    private String memberId;
    
    private String memberEmail;
    
    private String groupId;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate refusalDate;
}