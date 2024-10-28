package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IssueDateDefaultVO implements Serializable {
    private String issueDateIdx;

    private String issueIdx;

    private LocalDateTime issueCreation;

    private LocalDateTime issueDeadline;

    private LocalDateTime issueRegistrationDate;

    private LocalDateTime dateModifiedDate;

    private static final long serialVersionUID = 1L;
}