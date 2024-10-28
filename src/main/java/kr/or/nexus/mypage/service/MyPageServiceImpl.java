package kr.or.nexus.mypage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.mypage.dao.MyPageMapper;
import kr.or.nexus.vo.MemberManagementVO;

@Service
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private MyPageMapper mapper;

	@Override
	public MemberManagementVO getMyPageInfo(String userId) {
		return	mapper.getMyPageInfo(userId);
	}

}
