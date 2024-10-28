package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProjectDefaultVO implements Serializable {
    private String projectId;

    private String projectName;

    private String projectDescription;

    private String projectProfileImg;

    private LocalDateTime projectCreationDate;

    private String projectDelyn;

    private String projectSuccessStatus;

    private LocalDateTime projectEndDate;

    private Long projectBudget;

    private Long projectRevenue;

    private String groupId;

    private static final long serialVersionUID = 1L;
}