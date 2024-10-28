package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class IssueContentDefaultVO implements Serializable {
    private String issueContentIdx;

    private String issueIdx;

    private LocalDateTime issueContentDate;

    private LocalDateTime contentModifiedDate;

    private String issueContent;

    private static final long serialVersionUID = 1L;
}