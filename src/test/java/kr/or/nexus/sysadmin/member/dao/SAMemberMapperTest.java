package kr.or.nexus.sysadmin.member.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/nexus/spring/*-context.xml")
class SAMemberMapperTest {

	@Autowired
	SAMemberMapper saMemberMapper;

//	@Test
//	void testRetrieveMemberList() {
//		assertNotNull(saMemberMapper.retrieveMemberList());
//	}
}
