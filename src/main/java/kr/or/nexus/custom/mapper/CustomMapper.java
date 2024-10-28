package kr.or.nexus.custom.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface CustomMapper {
    public MemberManagementVO selectCustomUserByName(String username);
}
