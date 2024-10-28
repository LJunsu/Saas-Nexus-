package kr.or.nexus.gnotification.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import kr.or.nexus.gnotification.vo.GnoticeVO;


@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/nexus/spring/*-context.xml")
//@Transactional
class GnoticeMapperTest {

	@Autowired
	private GnoticeMapper mapper;

	GnoticeVO vo;

	//Group-notice Delete Test Case
	@Test
	void testDeleteByPrimaryKey() {
		Map<String,String> trueTest = new HashMap<String, String>();
		trueTest.put("boardId","BOARD017" );
		trueTest.put("groupId", "GRP017");
		trueTest.put("memberId", "MEM017");




	}


	//Group-notice Insert Test Case
	@Test
	void testInsert() {
		vo = new GnoticeVO();
		vo.setBoardTitle("이번 게시글은 게시글입니다11223344");
		vo.setBoardContent("게시글인데 게시글인데 게시글인데 어쩌라고11223344");
		vo.setMemberId("MEM001");
		vo.setGroupId("GRP003");

		assertEquals(1,mapper.insert(vo));
	}

	@Test
	void testSelectByPrimaryKey() {

		// true test case
		Map<String,String> trueTest = new HashMap<String, String>();
		trueTest.put("boardId","ALM045" );
		trueTest.put("groupId", "GRP003");
		trueTest.put("memberId", "MEM001");

		assertNotNull(mapper.selectByPrimaryKey(trueTest));



		// false test case
		Map<String,String> falseTest = new HashMap<String, String>();
		falseTest.put("boardId","ALM077" );
		falseTest.put("groupId", "GRP003");
		falseTest.put("memberId", "MEM001");

		assertNull(mapper.selectByPrimaryKey(falseTest));




	}

	// Group-notice List Test Case
	@Test
	void testSelectAll() {

	}

	//Group-notice  Update Test Case
	@Test
	void testUpdateByPrimaryKey() {
		vo = new GnoticeVO();
		vo.setBoardTitle("공지사항 제목 업데이트 테스트");
		vo.setBoardContent("공지사항 내용 업데이트 테스트");
		vo.setBoardId("BOARD001");
		vo.setMemberId("MEM001");
		vo.setGroupId("GRP001");

		assertEquals(1, mapper.updateByPrimaryKey(vo));


	}

}
