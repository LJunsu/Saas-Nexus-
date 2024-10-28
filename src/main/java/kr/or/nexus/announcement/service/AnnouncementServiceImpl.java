package kr.or.nexus.announcement.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.nexus.announcement.dao.AnnouncementMapper;
import kr.or.nexus.announcement.dto.ProjectLeader;
import kr.or.nexus.commons.AtchFile.service.AtchFileService;
import kr.or.nexus.commons.exception.BoardException;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

	@Value("#{appInfo.atchSavePath}")
	private File saveFolder; // AppInfo 설정 및 properties 파읿 빈 등록(root-context.xml)

	@PostConstruct
	public void init() throws IOException {
		if (!saveFolder.exists())
			saveFolder.mkdirs();
	}

	private final AtchFileService atchFileService;

	private final AnnouncementMapper mapper;

	@Override
	public AnnouncementVO writerAuthenticate(String boardId) throws kr.or.nexus.announcement.exception.WriterAuthenticationException  {
		AnnouncementVO authBoard = readAnnouncement(boardId);
		return authBoard;
	}

	@Override
	public void createAnnouncement(final AnnouncementVO announcement) {
	    // 파일이 있는 경우에만 처리
	    if (announcement.getAtchFile() != null && !announcement.getAtchFile().getFileDetails().isEmpty()) {
	        List<AtchFileDetailsVO> validFiles = announcement.getAtchFile().getFileDetails().stream()
	            .filter(fileDetails -> fileDetails.getOriginalFileName() != null && fileDetails.getFileSize() > 0) // 0KB 파일 체크
	            .collect(Collectors.toList());

	        if (!validFiles.isEmpty()) {
	            AtchFileVO atchFile = announcement.getAtchFile();
	            atchFile.setFileDetails(validFiles);
	            atchFileService.createAtchFile(atchFile, saveFolder);
	            announcement.setAttachedFileId(atchFile.getAttachedFileId());
	            announcement.setAtchFile(atchFile);
	        } else {
	            announcement.setAtchFile(null); // 유효한 파일이 없으면 설정하지 않음
	        }
	    }

	    mapper.insertAnnouncement(announcement);
	}


	@Override
	public List<AnnouncementVO> readAnnouncementList(PaginationInfo paging) {
		paging.setTotalRecord(mapper.selectTotalRecord(paging));
		List<AnnouncementVO> announcementList = mapper.selectAnnouncementList(paging);
		return announcementList;
	}

	@Override
	public AnnouncementVO readAnnouncement(String boardId) {
		AnnouncementVO announcement = mapper.selectAnnouncement(boardId);
		return announcement;
	}

	@Override
	public int removeAnnouncement(String boardId) {
		AnnouncementVO saved = writerAuthenticate(boardId);
		Optional.ofNullable(saved.getAttachedFileId())
				.ifPresent(fid->atchFileService.disableAtchFile(fid));

		return mapper.deleteAnnouncement(boardId);

	}

	@Override
	public void modifyBoard(AnnouncementVO announcement) {
	    AnnouncementVO saved = writerAuthenticate(announcement.getBoardId());

	    // 파일 유효성 검사 및 파일 처리
	    Optional.ofNullable(announcement.getAtchFile()).filter(af -> af.getFileDetails() != null).ifPresent(af -> {
	        List<AtchFileDetailsVO> validFiles = af.getFileDetails().stream()
	            .filter(fileDetails -> fileDetails.getOriginalFileName() != null && fileDetails.getFileSize() > 0) // 0KB 파일 체크
	            .collect(Collectors.toList());

	        if (!validFiles.isEmpty()) {
	            AtchFileVO validAtchFile = new AtchFileVO();
	            validAtchFile.setFileDetails(validFiles);
	            String newAtchFileId = mergeSavedDetailsAndNewDetails(saved.getAtchFile(), validAtchFile);
	            announcement.setAttachedFileId(newAtchFileId);
	        } else {
	            announcement.setAtchFile(null); // 유효한 파일이 없으면 파일을 설정하지 않음
	        }
	    });

	    mapper.updateAnnouncement(announcement);
	}


	/**
	 * 기존의 첨부파일 그룹이 있는 경우, 신규 파일과 기존 파일 그룹을 병합해 저장함. --> 게시글 수정시 사용
	 *
	 * @param atchFileId
	 */
	private String mergeSavedDetailsAndNewDetails(AtchFileVO savedAtchFile, AtchFileVO newAtchFile) { // 기존
																										// 파일들(savedAtchFile)과
																										// 새롭게 들어온
																										// 파일(newAtchFile)을
																										// 매개변수로 받음

		AtchFileVO mergeAtchFile = new AtchFileVO(); // 기존파일 이던지, 새롭게 들어온 파일이던 담을 빈공간
		List<AtchFileDetailsVO> fileDetails = new ArrayList<>();
		mergeAtchFile.setFileDetails(fileDetails); // 파일들의 그룹에 해당 파일 한건의 데이터를 넣어서 그룹 ->1.. 이 속할수 있게됨
		fileDetails.addAll(newAtchFile.getFileDetails()); // 새롭게 들어온 파일들중에 파일 한건의 데이터를 List에 담음

		if (savedAtchFile != null && savedAtchFile.getFileDetails() != null) { // 기존파일의 그룹이 널이 아니거나 , 기존파일의그룹아래에있는
																				// 파일한건한건의 데이터들이 널이 아니면
			fileDetails.addAll(savedAtchFile.getFileDetails()); // 기존 데이터들도 위에 만들었던 mergeAtchFile 이 빈공간에 담음

			// 기존 첨부파일 그룹은 비활성화
			atchFileService.disableAtchFile(savedAtchFile.getAttachedFileId());
		}

		// 5. 병합된 파일 리스트로 새로운 첨부 파일 그룹 생성 및 저장

		atchFileService.createAtchFile(mergeAtchFile, saveFolder);

		return mergeAtchFile.getAttachedFileId();
	}

	@Override
	public AtchFileDetailsVO download(String attachedFileId, String attachedFileDetailId) {
		return Optional.ofNullable(atchFileService.readAtchFileDetail(attachedFileId, attachedFileDetailId, saveFolder))
				.filter(fd -> fd.getSavedFile().exists()).orElseThrow(() -> new BoardException(
						String.format("[%d, %d]해당 파일이 없음.", attachedFileId, attachedFileDetailId)));
	}

	@Override
	@Transactional
	public void removeFile(String attachedFileId, String attachedFileDetailId) {
		atchFileService.removeAtchFileDetail(attachedFileId, attachedFileDetailId, saveFolder);

	}

	@Override
	public List<ProjectLeader> getProjectLeaders(String projectId) {

		return mapper.findProjectLeadersByProjectId(projectId);
	}

	@Override
	public List<GnoticeVO> chMainAnnouncementBoardList(GnoticeVO vo) {

		return mapper.chMainAnnouncementBoardList(vo);

	}

}
