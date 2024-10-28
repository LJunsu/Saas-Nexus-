package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommonDetailsDefaultVO implements Serializable {
    private String commDetailsId;

    private String comm;

    private String commDetailsName;

    private String commDetailsComment;

    private LocalDateTime commDetailsRegDate;

    private String useStatus;

    private String detailComment1;

    private String detailComment2;

    private static final long serialVersionUID = 1L;
}