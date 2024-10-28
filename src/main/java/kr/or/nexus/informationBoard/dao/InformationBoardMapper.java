package kr.or.nexus.informationBoard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.informationBoard.vo.InformationBoardAnswersVO;
import kr.or.nexus.informationBoard.vo.InformationBoardLikeVO;
import kr.or.nexus.informationBoard.vo.InformationBoardVO;

@Mapper
public interface InformationBoardMapper {

	/**
	 * 파라미터로 넘긴 ifBoardVo의 값으로 게시글 생성
	 */
	public int informationBoardInsert(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 groupId의 전체 정보마당 게시글 조회
	 */
	public List<InformationBoardVO> informationBoardList(String groupId);

	/**
	 * 파라미터로 넘긴 groupId와 해당 그룹의 boardId에 해당하는 게시글 상세 조회
	 */
	public InformationBoardVO informationBoardDetail(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 ifBoardVo의 값으로 게시글 수정
	 */
	public int informationBoardUpdate(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 ifBoardVo의 groupId, boardId 값으로 게시글 삭제
	 */
	public int informationBoardDelete(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 ifBoardVo의 groupId, boardId 값으로 댓글 조회
	 */
	public List<InformationBoardAnswersVO> informationBoardAnswersList(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 ifBoardAnswVo의 boardId, groupId, memberId, ifBoardParentCmtId 값으로 게시글 댓글 생성
	 */
	public int informationBoardAnswersInsert(InformationBoardAnswersVO ifBoardAnswVo);

	/**
	 * 파라미터로 넘긴 ifBoardAnswVo의 ifBoardCmtContent, ifBoardCmtId, memberId 값으로 게시글 댓글 수정
	 */
	public int informationBoardAnswersUpdate(InformationBoardAnswersVO ifBoardAnswVo);

	/**
	 * 파라미터로 넘긴 ifBoardAnswVo의 ifBoardCmtId, memberId 값으로 게시글 댓글 삭제
	 */
	public int informationBoardAnswersDelete(InformationBoardAnswersVO ifBoardAnswVo);

	/**
	 * 파라미터로 넘긴 ifBoardLikeVo의 ifBoardId, memberId 값으로 게시글의 좋아요 추가
	 */
	public int informationBoardLikeAdd(InformationBoardLikeVO ifBoardLikeVo);

	/**
	 * 파라미터로 넘긴 ifBoardLikeVo의 ifBoardId, memberId 값으로 게시글의 좋아요 삭제
	 */
	public int informationBoardLikeDelete(InformationBoardLikeVO ifBoardLikeVo);

	/**
	 * 파라미터로 넘긴 ifBoardLikeVo의 ifBoardId, memberId 값으로 게시글이 존재하는지 확인
	 */
	public List<InformationBoardLikeVO> informationBoardLikeYN(InformationBoardLikeVO ifBoardLikeVo);

	/**
	 * 게시글 상세조회 시 해당 게시글의 조회수 +1
	 */
	public int informationBoardNumPlue(String ifBoardId);

}
