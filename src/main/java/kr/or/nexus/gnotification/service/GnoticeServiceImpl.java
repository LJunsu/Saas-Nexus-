package kr.or.nexus.gnotification.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.commons.AtchFile.service.AtchFileService;
import kr.or.nexus.commons.exception.BoardException;
import kr.or.nexus.gnotification.dao.GnoticeMapper;
import kr.or.nexus.gnotification.vo.GnoticeAnswerVO;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;

@Service
public class GnoticeServiceImpl implements GnoticeService {

	@Autowired
	private GnoticeMapper mapper;

	@Autowired
	private AtchFileService service;

	@Value("#{appInfo.atchSavePath}")
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		if(!saveFolder.exists()) saveFolder.mkdirs();
	}


	/**
	 * 그룹-공지 전체 리스트
	 */
	@Override
	public List<GnoticeVO> noticeSelectAll(String groupId) {
		return mapper.selectAll(groupId);
	}


private String  mergeSavedDetailsAndNewDetails(AtchFileVO savedAtchFile, AtchFileVO newAtchFile) {  //기존 파일들(savedAtchFile)과 새롭게 들어온 파일(newAtchFile)을 매개변수로 받음

		AtchFileVO mergeAtchFile = new AtchFileVO();											//기존파일 이던지, 새롭게 들어온 파일이던 담을 빈공간
		List<AtchFileDetailsVO> fileDetails = new ArrayList<>();
		mergeAtchFile.setFileDetails(fileDetails);			        							//파일들의 그룹에 해당 파일 한건의 데이터를 넣어서 그룹 ->1.. 이 속할수 있게됨
		fileDetails.addAll(newAtchFile.getFileDetails());										//새롭게 들어온 파일들중에 파일 한건의 데이터를 List에 담음

		if(savedAtchFile!=null && savedAtchFile.getFileDetails()!=null) {						//기존파일의 그룹이 널이 아니거나 , 기존파일의그룹아래에있는 파일한건한건의 데이터들이 널이 아니면
			fileDetails.addAll(savedAtchFile.getFileDetails());									//기존 데이터들도 위에 만들었던  mergeAtchFile 이 빈공간에 담음

			// 기존 첨부파일 그룹은 비활성화
		service.disableAtchFile(savedAtchFile.getAttachedFileId());
		}


		 // 5. 병합된 파일 리스트로 새로운 첨부 파일 그룹 생성 및 저장

		service.createAtchFile(mergeAtchFile, saveFolder);

		return mergeAtchFile.getAttachedFileId();
	}







	@Override
	public String createNotice(GnoticeVO noticeVo) {


		Optional.ofNullable(noticeVo.getAttachedFile())   //쉽게 말해서 글작성시 올린 파일이 있으면
		.ifPresent(af->{								   //null이 아니면 여길타는데   af는 ifBoardVo.getAttachedFile()의 반환값이 null이 아닐 경우 그 값을 참조하게 됩니다.
			System.out.println("서비스 딴 :"+noticeVo.getAttachedFile());

			service.createAtchFile(af, saveFolder);			// 그값을 참조해서 atchedFileId 에 반영이됨

			System.out.println("AttachedFileVO: " + af);
            System.out.println("FileDetails: " + af.getFileDetails());

//			ifBoardVo.setAttachedFileId(af.getAttachedFileId()); //이건 optional   board 에 atchfile부분에 넣을 AttcahfiledId
			noticeVo.setAttachedFileId(af.getAttachedFileId()); //이건 optional   board 에 atchfile부분에 넣을 AttcahfiledId
		});

		 mapper.insert(noticeVo);

		 return noticeVo.getBoardId();

	}


	@Override
	public GnoticeVO detailNotice(GnoticeVO gnoticeVO) {

		GnoticeVO selectDetail = mapper.selectDetail(gnoticeVO);

		if (selectDetail == null) {
			throw new BoardException(String.format("%s해당 글 이 없음", gnoticeVO.getBoardId()));
		}

		return selectDetail;
	}


	@Override
	public void modifyBoard(GnoticeVO noticeVo) {
	GnoticeVO oldBoard = mapper.selectDetail(noticeVo);
	Optional.ofNullable(noticeVo.getAttachedFile())
	.filter(af->af.getFileDetails()!=null)
	.ifPresent(af->{
		String newAtchFileId = mergeSavedDetailsAndNewDetails(oldBoard.getAttachedFile() ,af);

		noticeVo.setAttachedFileId(newAtchFileId);
	});
	//검증후 업데이트 치기
	mapper.updateByPrimaryKey(noticeVo);
	}


	/**
	 * 	생성된 게시글을 바로 조회하여 상세게시글을 읽어오는 메소드
	 */
	@Override
	public GnoticeVO createBoardDetail(GnoticeVO noticeVo) {

		return mapper.createBoardDetail(noticeVo);
	}



	/**
	 *공지 게시글 한건 삭제
	 */
	@Override
	public int deleteNoitce(GnoticeVO board) {
		return mapper.deleteByPrimaryKey(board);
	}






	/**
	 * 공지게시글 한건에 대한 댓글
	 */
	@Override
	public List<GnoticeAnswerVO> answerList(String boardId) {

		return mapper.answerList(boardId);
	}

	/**
	 *  댓글 작성
	 */
	@Override
	public int createAnswer(GnoticeAnswerVO gnoticeAnswerVO) {

		return mapper.createAnswer(gnoticeAnswerVO);
	}

	/**
	 * 댓글 수정
	 */
	@Override
	public int updateAnswer(GnoticeAnswerVO gnoticeAnswerVO) {

		return mapper.updateAnswer(gnoticeAnswerVO);
	}

	/**
	 *댓글 삭제
	 */
	@Override
	public int deleteAnswer(GnoticeAnswerVO gnoticeAnswerVO) {

		return mapper.deleteAnswer(gnoticeAnswerVO);
	}
















	/**
	 *
	 *
	 *
	 *
	 *
	 *	파일관련 메소드
	 *
	 *
	 *
	 *
	 *
	 */
	@Override
	public AtchFileDetailsVO download(String atchFileId, String atchFileDetailId) {
		return Optional.ofNullable(service.readAtchFileDetail(atchFileId, atchFileDetailId, saveFolder))
				.filter(fd->fd.getSavedFile().exists())
				.orElseThrow(()->new BoardException(String.format("[%d, %d]해당 파일이 없음.", atchFileId, atchFileDetailId)));
	}

	@Override
	public void removeFile(String atchFileId, String atchFileDetailId) {
				service.removeAtchFileDetail(atchFileId, atchFileDetailId, saveFolder);
	}























}
