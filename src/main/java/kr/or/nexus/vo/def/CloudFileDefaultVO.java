package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CloudFileDefaultVO implements Serializable {
    private String cloudFileId;

    private String cloudFolderId;

    private String cloudId;

    private String projectId;

    private String originalFileName;

    private String saveFileName;

    private String fileExtensions;

    private Integer fileSize;

    private LocalDateTime fileCreationDate;

    private LocalDateTime fileDelDate;

    private String fileDelyn;

    private String field;

    private String savePath;

    private static final long serialVersionUID = 1L;
}