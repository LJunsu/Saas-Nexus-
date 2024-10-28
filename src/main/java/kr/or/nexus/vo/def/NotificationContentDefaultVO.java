package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificationContentDefaultVO implements Serializable {
    private String alarmMessageIdx;

    private String messageTitle;

    private String messageContents;

    private LocalDateTime messageDatetime;

    private String messageClassification;

    private String prefixGu;

    private String memberId;

    private String messageUrl;

    private static final long serialVersionUID = 1L;
}