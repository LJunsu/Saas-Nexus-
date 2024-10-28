package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChatRoomSettingsDefaultVO implements Serializable {
    private String chatSettingsId;

    private String chatColor;

    private String chatFont;

    private static final long serialVersionUID = 1L;
}