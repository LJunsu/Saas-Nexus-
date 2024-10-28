package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class FolderAccessRoleDefaultVO implements Serializable {
    private String projectRoleIdx;

    private String cloudFolderId;

    private String projectId;

    private String acessLevel;

    private static final long serialVersionUID = 1L;
}