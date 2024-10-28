package kr.or.nexus.commons.userSessionManager;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionManager {

    private Map<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    public void addUser(String memberId) {
        onlineUsers.put(memberId, true); // 로그인 시 상태를 true로 설정
    }

    public void removeUser(String memberId) {
        onlineUsers.remove(memberId); // 로그아웃 시 해당 사용자 제거
    }

    public Map<String, Boolean> getOnlineUsers() {
        return onlineUsers;
    }

    public boolean isUserOnline(String memberId) {
        return onlineUsers.getOrDefault(memberId, false);
    }
}
