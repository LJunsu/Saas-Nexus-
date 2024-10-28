package kr.or.nexus.announcement.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.announcement.dto.ProjectLeader;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.vo.AnnouncementVO;

@Mapper
public interface AnnouncementMapper {



	/**
	 * 새글 등록
	 * @param announcement
	 * @return
	 */
	public int insertAnnouncement (AnnouncementVO announcement);

	/**
	 * 게시글 목록 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo paging);

	/**
	 * 특정 글 조회
	 * @param boardId 조
	 * @return
	 */
	public AnnouncementVO selectAnnouncement(String boardId);


	/**
	 * 페이징 있는 글 목록 조회
	 * @param paging
	 * @return
	 */
	public List<AnnouncementVO> selectAnnouncementList(PaginationInfo paging);

	public int deleteAnnouncement(String boardId);

	public int updateAnnouncement(AnnouncementVO announcement);

	public List<ProjectLeader> findProjectLeadersByProjectId(String projectId);

	public List<GnoticeVO> chMainAnnouncementBoardList(GnoticeVO vo);
}
