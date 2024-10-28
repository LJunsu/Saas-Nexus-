package kr.or.nexus.header.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.header.vo.GroupIssueSearchVO;

@Mapper
public interface GroupIssueSearchMapper {
	
	List<GroupIssueSearchVO> GroupIssueSearch(@Param("groupId") String groupId);

	
	
}
