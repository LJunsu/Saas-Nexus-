package kr.or.nexus.announcement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.AnnouncementAnswersDefaultVO;

@Mapper
public interface AnnouncementAnswerMapper {

	public List<AnnouncementAnswersDefaultVO> selectAnswerList(String boardId);

	public int insertAnswer(AnnouncementAnswersDefaultVO answer);

	public int updateAnswer(AnnouncementAnswersDefaultVO answer);

	public int deleteAnswer(AnnouncementAnswersDefaultVO answer);

	public AnnouncementAnswersDefaultVO selectAnswer(int boardCmtId);

	public AnnouncementAnswersDefaultVO detail(int boardCmtId);
	}
