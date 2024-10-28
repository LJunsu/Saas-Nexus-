package kr.or.nexus.mypage.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface MyPageMapper {

	MemberManagementVO getMyPageInfo(String userId);

}
