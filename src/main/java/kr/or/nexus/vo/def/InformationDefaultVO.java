package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InformationDefaultVO implements Serializable {
    private String ifBoardId;

    private String ifBoardTitle;

    private LocalDateTime ifBoardCreationDate;

    private String ifBoardDelyn;

    private Integer ifBoardLike;

    private Short ifBoardNum;

    private String groupId;

    private String memberId;

    private String attachedFileId;

    private String ifBoardContent;

    private static final long serialVersionUID = 1L;
}