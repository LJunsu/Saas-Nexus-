package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CustomerBoardDefaultVO implements Serializable {
    private String csId;

    private String memberId;

    private String csTitle;

    private LocalDateTime csCreationDate;

    private String csDelyn;

    private String csContent;

    private static final long serialVersionUID = 1L;
}