package kr.or.nexus.informationBoard.service;

import java.util.List;

import kr.or.nexus.informationBoard.vo.InformationBoardAnswersVO;
import kr.or.nexus.informationBoard.vo.InformationBoardLikeVO;
import kr.or.nexus.informationBoard.vo.InformationBoardVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;

public interface InformationBoardService {

	/**
	 * 파라미터로 넘긴 ifBoardVo의 값으로 게시글 생성
	 * 생성에 성공하면 true, 실패하면 false
	 */
	public boolean informationBoardInsert(InformationBoardVO ifBoardVo);

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
	 * 수정에 성공하면 true, 실패하면 false
	 */
	public boolean informationBoardUpdate(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 ifBoardVo의 groupId, boardId 값으로 게시글 삭제
	 * 삭제에 성공하면 true, 실패하면 false
	 */
	public boolean informationBoardDelete(InformationBoardVO ifBoardVo);

	/**
	 * 파라미터로 넘긴 ifBoardAnswVo의 boardId, groupId, memberId, ifBoardParentCmtId 값으로 게시글 댓글 생성
	 */
	public boolean informationBoardAnswersInsert(InformationBoardAnswersVO ifBoardAnswVo);

	/**
	 * 파라미터로 넘긴 ifBoardAnswVo의 ifBoardCmtContent, ifBoardCmtId, memberId 값으로 게시글 댓글 수정
	 */
	public boolean informationBoardAnswersUpdate(InformationBoardAnswersVO ifBoardAnswVo);

	/**
	 * 파라미터로 넘긴 ifBoardAnswVo의 ifBoardCmtId, memberId 값으로 게시글 댓글 삭제
	 */
	public boolean informationBoardAnswersDelete(InformationBoardAnswersVO ifBoardAnswVo);

	/**
	 * 파라미터로 넘긴 ifBoardLikeVo의 ifBoardId, memberId 값으로 게시글의 좋아요 추가
	 */
	public boolean informationBoardLikeAdd(InformationBoardLikeVO ifBoardLikeVo);

	/**
	 * 파라미터로 넘긴 ifBoardLikeVo의 ifBoardId, memberId 값으로 게시글의 좋아요 삭제
	 */
	public boolean informationBoardLikeDelete(InformationBoardLikeVO ifBoardLikeVo);

	/**
	 * 파라미터로 넘긴 ifBoardLikeVo의 ifBoardId, memberId 값으로 게시글이 존재하는지 확인
	 */
	public boolean informationBoardLikeYN(InformationBoardLikeVO ifBoardLikeVo);

	/**
	 * 게시글 상세조회 시 해당 게시글의 조회수 +1
	 */
	public boolean informationBoardNumPlue(String ifBoardId);




	/**
	 * 파일 다운로드
	 * @param atchFileId
	 * @param fileSn
	 * @return
	 */
	public AtchFileDetailsVO download(String atchFileId, String atchFileDetailId);


	/**
	 * 파일 한건 삭제
	 * @param atchFileId
	 * @param fileSn
	 */
	public void removeFile(String atchFileId, String atchFileDetailId);
















}
