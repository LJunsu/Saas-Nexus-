package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VideoMeetingConferenceDefaultVO implements Serializable {
    private String videoConferenceId;

    private String videoMeetingMember;

    private LocalDateTime videoMeetingDate;

    private static final long serialVersionUID = 1L;
}