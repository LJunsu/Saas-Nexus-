package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LogVO implements Serializable {
    private Integer logIdx;

    private String logSource;
    
    private String logSourceModify;

    private LocalDateTime logGenerationDate;

    private String memberId;
    
    private String groupId;
    
    private String classIdx;
    
    private String issueIdx;
    
    private String projectId;
    
    private static final long serialVersionUID = 1L;
}