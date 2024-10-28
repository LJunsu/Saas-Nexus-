package kr.or.nexus.mypage.service;

import kr.or.nexus.vo.MemberManagementVO;

public interface MyPageService {

	MemberManagementVO getMyPageInfo(String userId);

}
