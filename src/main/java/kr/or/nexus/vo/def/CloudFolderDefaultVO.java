package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CloudFolderDefaultVO implements Serializable {
    private String cloudFolderId;

    private String cloudId;

    private String projectId;

    private String cloudFolderName;

    private LocalDateTime cloudFolderCrDate;

    private String cloudFolderDelyn;

    private String cloudFolderCreator;

    private static final long serialVersionUID = 1L;
}