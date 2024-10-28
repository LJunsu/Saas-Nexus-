package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IssueOfficerDefaultVO implements Serializable {
    private String issueOfficerIdx;

    private String issueIdx;

    private String issueOfficerId;

    private LocalDateTime issueOfficerDate;

    private static final long serialVersionUID = 1L;
}