package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProjectMemberDefaultVO implements Serializable {
    private String projectMemIdx;

    private LocalDateTime projectJoinDate;

    private LocalDateTime projectDelDate;

    private String projectDelyn;

    private String groupId;

    private String memberId;

    private String projectRoleIdx;

    private String projectId;

    private static final long serialVersionUID = 1L;
}