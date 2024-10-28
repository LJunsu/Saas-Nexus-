package kr.or.nexus.group.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.ProjectMemberVO;

@Mapper
public interface ProjectMemberMapper {

	public List<ProjectMemberVO> selectProjectMemberList(String projectId);

	public void insertProjectMember(ProjectMemberVO vo);

	public List<ProjectMemberVO> selectProjectMemberSelectedList(ProjectMemberVO vo);

	public void deleteProjectMember(ProjectMemberVO vo);

	public List<ProjectMemberVO> issueProjectMember(String projecId);

	public ProjectMemberVO confirmLeader(Map<String, Object> params);

	public ProjectMemberVO selectProjectMember(ProjectMemberVO input);

	public ProjectMemberVO findByPk(String projectMemIdx);

	public ProjectMemberVO selectProjectMemberVO(ProjectMemberVO input);

}
