package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChattingRoomFileDefaultVO implements Serializable {
    private String chattingRoomFileId;

    private String chattingRoomId;

    private String attachedFileId;

    private static final long serialVersionUID = 1L;
}