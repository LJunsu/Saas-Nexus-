package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.ProjectDefaultVO;

@Mapper
public interface ProjectDefaultMapper {
    int deleteByPrimaryKey(String projectId);

    int insert(ProjectDefaultVO row);

    ProjectDefaultVO selectByPrimaryKey(String projectId);

    List<ProjectDefaultVO> selectAll();

    int updateByPrimaryKey(ProjectDefaultVO row);
}