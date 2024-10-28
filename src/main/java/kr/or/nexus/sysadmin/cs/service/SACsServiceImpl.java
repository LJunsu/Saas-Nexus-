package kr.or.nexus.sysadmin.cs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.sysadmin.cs.dao.SACsMapper;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;

@Service
public class SACsServiceImpl implements SACsService {

	@Autowired
	private SACsMapper dao;

	@Override
	public List<CustomerBoardVO> retrieveCsList(PaginationInfo paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		return dao.retrieveCsList(paging);
	}

	@Override
	public CustomerBoardVO retrieveCs(String csId) {
		return dao.retrieveCs(csId);
	}

	@Override
	public void insertBoardAnswer(CustomerBoardAnswersVO boardAnswers) {
		dao.insertBoardAnswer(boardAnswers);
	}

	@Override
	public void removeBoardAnswer(int csAnsIdx) {
		dao.removeBoardAnswer(csAnsIdx);
	}

	@Override
	public void modifyBoardAnswer(CustomerBoardAnswersVO boardAnswers) {
		dao.modifyBoardAnswer(boardAnswers);
	}

	@Override
	public void insertCustomerBoard(CustomerBoardVO customerBoardVO) {
		dao.insertCustomerBoard(customerBoardVO);
	}
}
