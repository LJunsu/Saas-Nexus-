package kr.or.nexus.sysadmin.cs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;

@Mapper
public interface SACsMapper {

	public int selectTotalRecord(PaginationInfo paging);

	public List<CustomerBoardVO> retrieveCsList(PaginationInfo paging);

	public CustomerBoardVO retrieveCs(String csId);

	public void insertBoardAnswer(CustomerBoardAnswersVO boardAnswers);

	public void removeBoardAnswer(int csAnsIdx);

	public void modifyBoardAnswer(CustomerBoardAnswersVO boardAnswers);

	public void insertCustomerBoard(CustomerBoardVO customerBoardVO);
}
