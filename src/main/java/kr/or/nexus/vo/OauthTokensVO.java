package kr.or.nexus.vo;

import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Data;

@Data
public class OauthTokensVO {
	private final String userId;
    private final String accessToken;
    private final String refreshToken;
    private final Long expirationTime;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public OauthTokensVO(String userId, String accessToken, String refreshToken, Long expirationTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expirationTime = expirationTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OauthTokensVO tokenVO = (OauthTokensVO) o;
        return Objects.equals(userId, tokenVO.userId) &&
               Objects.equals(accessToken, tokenVO.accessToken) &&
               Objects.equals(refreshToken, tokenVO.refreshToken) &&
               Objects.equals(expirationTime, tokenVO.expirationTime) &&
               Objects.equals(createdAt, tokenVO.createdAt) &&
               Objects.equals(updatedAt, tokenVO.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accessToken, refreshToken, expirationTime, createdAt, updatedAt);
    }



	@Override
	public String toString() {
		return "OauthTokensVO [userId=" + userId + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ ", expirationTime=" + expirationTime + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
    
    
}
