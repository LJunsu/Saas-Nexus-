package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.ProjectMemberDefaultVO;

@Mapper
public interface ProjectMemberDefaultMapper {
    int deleteByPrimaryKey(String projectMemIdx);

    int insert(ProjectMemberDefaultVO row);

    ProjectMemberDefaultVO selectByPrimaryKey(String projectMemIdx);

    List<ProjectMemberDefaultVO> selectAll();

    int updateByPrimaryKey(ProjectMemberDefaultVO row);
}