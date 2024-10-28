package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IssueDefaultVO implements Serializable {
    private String issueIdx;

    private String subIssueIdx;

    private LocalDateTime issueModifiedDate;

    private String issueDelyn;

    private String projectMemIdx;

    private String issueProgressStatus;

    private String issuePriorityName;

    private String issueTitle;

    private static final long serialVersionUID = 1L;
}