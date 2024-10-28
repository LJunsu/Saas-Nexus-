package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommonClassificationDefaultVO implements Serializable {
    private String comm;

    private String commName;

    private LocalDateTime commRegDate;

    private String useStatus;

    private static final long serialVersionUID = 1L;
}