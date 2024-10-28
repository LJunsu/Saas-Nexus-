package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProjectRoleDefaultVO implements Serializable {
    private String projectRoleIdx;

    private String projectId;

    private String projectRoleName;

    private static final long serialVersionUID = 1L;
}