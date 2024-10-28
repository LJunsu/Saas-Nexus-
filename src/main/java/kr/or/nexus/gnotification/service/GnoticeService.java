package kr.or.nexus.gnotification.service;

import java.util.List;

import kr.or.nexus.gnotification.vo.GnoticeAnswerVO;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;

public interface GnoticeService {



	/**
	 * @return  그룹-공지 전체
	 */
	public List<GnoticeVO> noticeSelectAll(String groupId);

    /**
     *  그룹 공지 작성
     * @param noticeVo
     * @return success : 1 or failed 0
     */
    public String createNotice(GnoticeVO noticeVo);

    /**
     * @param noticeVo.getBoardId
     * @return 그룹 공지 세부사항
     */
    public GnoticeVO detailNotice(GnoticeVO noticeVo);

    /**
     * @param noticeVo.update form
     * 	 그룹 공지 업데이트
     */
    public void modifyBoard(GnoticeVO noticeVo);

    /**
     * 방금 만들어진 공지 하나의 세부사항 raw
     * @param noticeVo
     * @return
     */
    public GnoticeVO createBoardDetail(GnoticeVO noticeVo);


    /**
     * 삭제
     * @param boardId
     * @return int
     */
    public int deleteNoitce(GnoticeVO board);


	/**
	 *  해당 공지글의 댓글 리스트
	 * @param boardId
	 * @return
	 */
	public List<GnoticeAnswerVO> answerList(String boardId);


	/**
	 * 공지글에 대한 댓글 작성
	 * @param gnoticeAnswerVO
	 */
	public int createAnswer(GnoticeAnswerVO gnoticeAnswerVO);

	/**
	 * 공지글에 대한 댓글 수정
	 * @param gnoticeAnswerVO
	 */
	public int updateAnswer(GnoticeAnswerVO gnoticeAnswerVO);
	/**
	 * 공지글에 대한 댓글 삭제
	 * @param gnoticeAnswerVO
	 * @return
	 */
	public int deleteAnswer(GnoticeAnswerVO gnoticeAnswerVO);





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
