package kr.or.nexus.announcement.service;

import java.util.List;

import kr.or.nexus.vo.def.AnnouncementAnswersDefaultVO;

public interface AnnouncementAnswerService {

	/**
	 * 신규 공지 생성
	 * @param notification
	 */
	public void createAnswer(AnnouncementAnswersDefaultVO answer);

	public List<AnnouncementAnswersDefaultVO> readAnswerList(String boardId);

	public void removeAnswer(AnnouncementAnswersDefaultVO answer);

	public void modifyAnswer(AnnouncementAnswersDefaultVO answer);

	public AnnouncementAnswersDefaultVO readAnswer(int boardCmtId);


	public AnnouncementAnswersDefaultVO detail(int boardCmtId);

}
