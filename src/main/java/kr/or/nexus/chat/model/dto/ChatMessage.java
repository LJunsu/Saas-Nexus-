package kr.or.nexus.chat.model.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatMessage {
    private String roomId;
    private String sender;
    private String senderName;
    private String content;
    private String timestamp;
    private String messageId; // 메시지 ID 추가
    private String memberProfilePict;

    public ChatMessage() {}

    public ChatMessage(String roomId, String sender, String content, String timestamp, String messageId, String memberProfilePict) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp != null ? timestamp : DateTimeFormatter.ISO_INSTANT.format(Instant.now());
        this.messageId = messageId; // 메시지 ID 설정
        this.memberProfilePict = memberProfilePict;
    }



	public String getMemberProfilePict() {
		return memberProfilePict;
	}

	public void setMemberProfilePict(String memberProfilePict) {
		this.memberProfilePict = memberProfilePict;
	}

	public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
