package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InformationAnswersDefaultVO implements Serializable {
    private Long ifBoardCmtId;

    private String ifBoardId;

    private Long ifBoardParentCmtId;

    private LocalDateTime ifBoardCmtCrDate;

    private String groupId;

    private String memberId;

    private String ifBoardCmtContent;

    private static final long serialVersionUID = 1L;
}