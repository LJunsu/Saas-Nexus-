package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GroupManagementDefaultVO implements Serializable {
    private String groupId;

    private String groupName;

    private String groupProfilePicture;

    private LocalDateTime groupCreationDate;

    private String groupDelyn;

    private String useStatus;

    private String businessLicenseId;

    private LocalDateTime applicationDate;

    private LocalDateTime approvalDate;

    private String comment;

    private String memberId;


    private static final long serialVersionUID = 1L;
}