package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class ElectronicApprovalVO implements Serializable {

    private String eaId;
    
    private String eaSender;
    
    private String eaReceiver;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate eaSendDate;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate eaApprovalDate;
    
    private String eaApprovalYn;
    
    private static final long serialVersionUID = 1L;
    
    private DocumentsVO documentsVO;
    
    private List<ProjectParticipantsVO> projectParticipantsVO;
    
    private List<MemberManagementVO> memberManagementVO;
}