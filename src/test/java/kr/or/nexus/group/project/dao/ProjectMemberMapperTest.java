package kr.or.nexus.group.project.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.nexus.vo.ProjectMemberVO;

@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/nexus/spring/*-context.xml")
class ProjectMemberMapperTest {
	
	@Autowired
	private ProjectMemberMapper dao;

	@Test
	void test() {
		ProjectMemberVO vo = new ProjectMemberVO();
		vo.setGroupId("GRP001");
		vo.setProjectId("PRJ033");
		List<ProjectMemberVO> memberList = dao.selectProjectMemberSelectedList(vo);
	}

}
