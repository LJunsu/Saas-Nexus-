package kr.or.nexus.announcement.service;

import java.util.List;

import kr.or.nexus.announcement.dto.ProjectLeader;
import kr.or.nexus.announcement.exception.WriterAuthenticationException;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;

public interface AnnouncementService {

	/**
	 * 신규 공지 생성
	 * @param notification
	 */
	public void createAnnouncement(AnnouncementVO announcement);

	public List<AnnouncementVO> readAnnouncementList(PaginationInfo paging);

	public AnnouncementVO readAnnouncement(String boardId);

	public int removeAnnouncement(String boardId);

	public void modifyBoard(AnnouncementVO announcement);

	public AtchFileDetailsVO download(String attachedFileId, String attachedFileDetailId);

	public void removeFile(String attachedFileId, String attachedFileDetailId);

	AnnouncementVO writerAuthenticate(String boardId) throws WriterAuthenticationException;

	public List<ProjectLeader> getProjectLeaders(String projectId);

	public List<GnoticeVO> chMainAnnouncementBoardList(GnoticeVO vo);

}
