package kr.or.nexus.announcement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.nexus.announcement.dao.AnnouncementAnswerMapper;
import kr.or.nexus.vo.def.AnnouncementAnswersDefaultVO;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AnnouncementAnswerServiceImpl implements  AnnouncementAnswerService{

	private final AnnouncementAnswerMapper mapper;

	@Override
	public void createAnswer(AnnouncementAnswersDefaultVO answer) {
		mapper.insertAnswer(answer);

	}

	@Override
	public List<AnnouncementAnswersDefaultVO> readAnswerList(String boardId) {
		List<AnnouncementAnswersDefaultVO> answerList = mapper.selectAnswerList(boardId);
		return answerList;

	}


	@Override
	public void modifyAnswer(AnnouncementAnswersDefaultVO answer) {
		// TODO Auto-generated method stub
		mapper.updateAnswer(answer);

	}

	@Override
	public AnnouncementAnswersDefaultVO readAnswer(int boardCmtId) {
		AnnouncementAnswersDefaultVO answer = mapper.selectAnswer(boardCmtId);
		return answer;
	}

	@Override
	public AnnouncementAnswersDefaultVO detail(int boardCmtId) {
		return mapper.detail(boardCmtId);
	}

	@Override
	public void removeAnswer(AnnouncementAnswersDefaultVO answer) {
		mapper.deleteAnswer(answer);
	}






}
