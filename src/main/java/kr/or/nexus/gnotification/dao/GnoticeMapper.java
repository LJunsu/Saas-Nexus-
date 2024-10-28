package kr.or.nexus.gnotification.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.gnotification.vo.GnoticeAnswerVO;
import kr.or.nexus.gnotification.vo.GnoticeVO;

@Mapper
public interface GnoticeMapper {


    int deleteByPrimaryKey(GnoticeVO board);

    int insert(GnoticeVO row);

    GnoticeVO selectByPrimaryKey(Map<String, String> map);

    List<GnoticeVO> selectAll(String groupId);

    int updateByPrimaryKey(GnoticeVO row);

    GnoticeVO selectDetail(GnoticeVO noticeVo);

    GnoticeVO createBoardDetail(GnoticeVO noticeVo);

	List<GnoticeAnswerVO> answerList(String boardId);

	int createAnswer(GnoticeAnswerVO gnoticeAnswerVO);

	int updateAnswer(GnoticeAnswerVO gnoticeAnswerVO);

	int deleteAnswer(GnoticeAnswerVO gnoticeAnswerVO);




}