package kr.or.nexus.commons.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;

public class Utils {
	
	public String memberIdReturn() {
        String memberId = null;
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null && authentication.getPrincipal() instanceof CustomUserVOWrapper) {
                CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
                MemberManagementVO realUser = wrapper.getRealUser();
                memberId = realUser.getMemberId();
            }
        }
        return memberId;
    }
	
}
