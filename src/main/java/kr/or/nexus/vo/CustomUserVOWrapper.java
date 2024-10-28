package kr.or.nexus.vo;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class CustomUserVOWrapper extends User{
    private MemberManagementVO realUser;
    
    public CustomUserVOWrapper(MemberManagementVO realUser) {
    	super(realUser.getMemberId(), realUser.getMemberPw()
    			,true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_GROUPADMIN", "ROLE_ADMIN"));
    	this.realUser = realUser;
    }
    
    public MemberManagementVO getRealUser() {
    	return realUser;
    }
    
}