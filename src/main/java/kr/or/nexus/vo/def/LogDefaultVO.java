package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LogDefaultVO implements Serializable {
    private Integer logIdx;

    private String logSource;

    private String logGenerationService;

    private LocalDateTime logGenerationDate;

    private String logGenerationAction;

    private String memberId;

    private String logSuccessStatus;

    private static final long serialVersionUID = 1L;
}