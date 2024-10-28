package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import kr.or.nexus.vo.ChattingRoomMemberVO;
import lombok.Data;

@Data
public class ChattingRoomDefaultVO implements Serializable {
    private String chattingRoomId;

    private Short roomPopulation;

    private String roomName;

    private String roomComment;

    private Date createDate;

    private LocalDateTime lastMessageDate;

    private String roomSettingsId;

    private static final long serialVersionUID = 1L;

    private List<ChattingRoomMemberVO> members;

}