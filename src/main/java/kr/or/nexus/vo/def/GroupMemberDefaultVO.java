package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GroupMemberDefaultVO implements Serializable {
    private String groupId;

    private String memberId;

    private LocalDateTime groupJoinDate;

    private String groupDelyn;
    
    private String groupAdminYn;

    private static final long serialVersionUID = 1L;
}