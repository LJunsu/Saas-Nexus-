package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class DocumentsVO implements Serializable {

    private String documentId;

    private String eaId;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate draftDate;
    
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate completionDate;
    
    private String creationReason;
    
    private String projectName;
    
    private int totalParticipants;
    
    private String documentClass;
    
    private String fileUrl;
    
    private static final long serialVersionUID = 1L;
}