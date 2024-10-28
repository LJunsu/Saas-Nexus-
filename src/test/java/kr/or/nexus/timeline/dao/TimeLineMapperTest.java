package kr.or.nexus.timeline.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.nexus.vo.IssueVO;
import lombok.extern.slf4j.Slf4j;

@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/nexus/spring/*-context.xml")
@Slf4j
class TimeLineMapperTest {

	@Autowired
	private TimeLineMapper timeLineMapper;

	@Test
	void testTimeLineList() {
		List<IssueVO> list = timeLineMapper.timeLineList("GRP001");
		System.out.println(list);
		assertNotNull(list);
	}

}
