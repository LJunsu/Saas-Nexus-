package kr.or.nexus.member.chmain.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChMainMapper {
	public String searchProjectName(@Param("projectId") String projectId);
}
