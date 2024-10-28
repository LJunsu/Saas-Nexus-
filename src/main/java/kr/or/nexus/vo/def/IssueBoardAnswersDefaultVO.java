package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IssueBoardAnswersDefaultVO implements Serializable {
    private String issueCommentId;

    private String issueIdx;

    private String issueParentCmtId;

    private String issueCmtWriter;

    private LocalDateTime issueCmtCrDate;

    private LocalDateTime issueCmtModifiedDate;

    private String issueCmtContent;

    private static final long serialVersionUID = 1L;
}