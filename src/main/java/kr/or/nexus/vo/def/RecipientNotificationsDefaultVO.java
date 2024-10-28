package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RecipientNotificationsDefaultVO implements Serializable {
    private String alarmMessageIdx;

    private String alarmNotifications;

    private String alarmNotification;

    private LocalDateTime alarmTime;

    private LocalDateTime alarmCheck;

    private static final long serialVersionUID = 1L;
}