package kr.or.nexus.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProjectParticipantsVO implements Serializable {
    
    private String participantId;
    
    private String participantName;
    
    private String participantRole;
    
    private String participantEmail;
    
    private String participantRemark;
    
    private String documentId;
    
    private String eaId;
    
    private String memberId;
    
    private static final long serialVersionUID = 1L;
}