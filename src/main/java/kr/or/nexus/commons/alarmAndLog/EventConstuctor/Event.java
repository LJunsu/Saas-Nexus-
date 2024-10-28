package kr.or.nexus.commons.alarmAndLog.EventConstuctor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import kr.or.nexus.commons.alarmAndLog.EventType.IssueEventType;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.Data;

/**
 * @author PC-16
 *
 *	이벤트(작성, 수정, 삭제, 할당) 등의 이벤트에 대해 @EventListener에게 전달할 데이터를 정의하는 객체
 */
@Data
public class Event<T, E> extends ApplicationEvent {

    private String memId;    // 시큐리티에서 받아올 수 있음
    @Nullable
    private T beforeInfo;	// 모든 수정 전의 데이터
    private E afterInfo;     // 모든 수정 후의 데이터
    private IssueEventType eventType;  // 대부분 IssueId, IssueAnswer를 받아서 URL에 사용할 것으로 예상
    private String comment;
    private String url;
    private String senderId;
    private String senderName;
    private Authentication authentication;
    
    public Event(Object source, String memId, T beforeInfo,
    		E afterInfo, IssueEventType eventType, String comment,
    		String url) {
        super(source);
        this.memId = memId;
        this.beforeInfo = beforeInfo;
        this.afterInfo = afterInfo;
        this.eventType = eventType;
        this.comment = comment;
        this.url = url;
        
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		this.authentication = authentication;
		if(authentication.getPrincipal() instanceof CustomUserVOWrapper) {
			CustomUserVOWrapper princiapl = (CustomUserVOWrapper) authentication.getPrincipal();
			MemberManagementVO	memberInfo = princiapl.getRealUser();
			
			this.senderId = memberInfo.getMemberId();
			this.senderName = memberInfo.getMemberName();
			
		}
		
    }

    public Object getFieldFromAfterInfo(String fieldName) {
        return getFieldFromInfo(afterInfo, fieldName);
    }

    public Object getFieldFromBeforeInfo(String fieldName) {
        return getFieldFromInfo(beforeInfo, fieldName);
    }

    private Object getFieldFromInfo(Object info, String fieldName) {
        try {
            if (info instanceof List) {
                // info가 리스트일 경우
                List<?> infoList = (List<?>) info;
                List<Object> results = new ArrayList<>();
                for (Object item : infoList) {
                    String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = item.getClass().getMethod(methodName);
                    results.add(method.invoke(item));
                }
                return results;
            } else {
                // info가 다닐일경우
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method method = info.getClass().getMethod(methodName);
                return method.invoke(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
