package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class JobsheetDefaultVO implements Serializable {
    private Short jobsheetId;

    private String jobsheetTitle;

    private LocalDateTime jobsheetRegdt;

    private LocalDateTime jobsheetUpddt;

    private String jobsheetDelyn;

    private String projectMemIdx;

    private String attachedFileId;

    private String jobsheetContent;

    private static final long serialVersionUID = 1L;
}