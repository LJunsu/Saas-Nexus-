package kr.or.nexus.sysadmin.cs.service;

import java.util.List;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;
import kr.or.nexus.vo.MemberManagementVO;

/**
 * @author PC-22
 *
 */
public interface SACsService {

	/**
	 * 게시판 목록 조회
	 * @return
	 */
	public List<CustomerBoardVO> retrieveCsList(PaginationInfo paging);

    /**
     * 게시판 상세 정보 조회
     * @param memId
     * @return 존재하지 않는 경우, {@link UserNotFoundException 발생}
     */
    public CustomerBoardVO retrieveCs(String csId);
    
	/**
	 * csBoard 관리자 댓글 추가
	 * @param boardAnswers
	 */
	public void insertBoardAnswer(CustomerBoardAnswersVO boardAnswers);

	/**
	 * csBoard 관리자 댓글 삭제
	 * @param csAnsIdx
	 */
	public void removeBoardAnswer(int csAnsIdx);

	/**
	 * csBoard 관리자 댓글 수정
	 * @param boardAnswers
	 */
	public void modifyBoardAnswer(CustomerBoardAnswersVO boardAnswers);

	/**
	 * Customer Board 사용자 문의작성
	 * @param customerBoardVO
	 */
	public void insertCustomerBoard(CustomerBoardVO customerBoardVO);

}