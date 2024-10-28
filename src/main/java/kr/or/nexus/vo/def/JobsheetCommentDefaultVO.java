package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JobsheetCommentDefaultVO implements Serializable {
    private Long jobsheetCommentId;

    private Short jobsheetId;

    private Long parentCommentId;

    private LocalDateTime jobsheetRegdt;

    private String projectMemberIdx;

    private String jobsheetContent;

    private static final long serialVersionUID = 1L;
}