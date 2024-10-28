package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ProjectRoleDefaultVO;

@Mapper
public interface ProjectRoleDefaultMapper {
	
    public int deleteByPrimaryKey(@Param("projectRoleIdx") String projectRoleIdx, @Param("projectId") String projectId);

    public int insert(ProjectRoleDefaultVO row);

    public ProjectRoleDefaultVO selectByPrimaryKey(@Param("projectRoleIdx") String projectRoleIdx, @Param("projectId") String projectId);

    public List<ProjectRoleDefaultVO> selectAll();

    public int updateByPrimaryKey(ProjectRoleDefaultVO row);

	public List<ProjectRoleVO> selectRoleList(String projectId);

	public int deleteProjectRole(ProjectRoleDefaultVO projectRoleVO);

	public ProjectRoleVO selectRole(String role);

	public ProjectRoleVO findByPk(String projectRoleIdx);

	public ProjectRoleDefaultVO retrieve(ProjectRoleDefaultVO vo);
}