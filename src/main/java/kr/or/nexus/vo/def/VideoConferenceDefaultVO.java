package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VideoConferenceDefaultVO implements Serializable {
    private String videoConferenceId;

    private String groupId;

    private String projectId;

    private String videoConferenceCr;

    private LocalDateTime videoConferenceCrTime;

    private String videoConferenceTitle;

    private static final long serialVersionUID = 1L;
}