package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class IssueTagsDefaultVO implements Serializable {
    private Integer issueTagsId;

    private String issueIdx;

    private String issueTagName;

    private static final long serialVersionUID = 1L;
}