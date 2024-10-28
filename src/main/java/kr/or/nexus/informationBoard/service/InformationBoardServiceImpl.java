package kr.or.nexus.informationBoard.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.commons.AtchFile.service.AtchFileService;
import kr.or.nexus.commons.exception.BoardException;
import kr.or.nexus.informationBoard.dao.InformationBoardMapper;
import kr.or.nexus.informationBoard.exception.PkNotFoundException;
import kr.or.nexus.informationBoard.vo.InformationBoardAnswersVO;
import kr.or.nexus.informationBoard.vo.InformationBoardLikeVO;
import kr.or.nexus.informationBoard.vo.InformationBoardVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;

@Service
public class InformationBoardServiceImpl implements InformationBoardService {

	@Autowired
	private InformationBoardMapper informationMapper;

	@Autowired
	private AtchFileService service;

	@Value("#{appInfo.atchSavePath}")
	private File saveFolder;

	@PostConstruct
	public void init() throws IOException {
		if(!saveFolder.exists()) saveFolder.mkdirs();
	}

	@Override  //파일 업로드를 할때
	public boolean informationBoardInsert(InformationBoardVO ifBoardVo) {
		//쉽게 말해서 글작성시 올린 파일이 있으면
		Optional.ofNullable(ifBoardVo.getAttachedFile())   //쉽게 말해서 글작성시 올린 파일이 있으면
		.ifPresent(af->{								   //null이 니면 여길타는데   af는 ifBoardVo.getAttachedFile()의 반환값이 null이 아닐 경우 그 값을 참조하게 됩니다.
			service.createAtchFile(af, saveFolder);			// 그값을 참조해서 atchedFileId 에 반영이됨

			ifBoardVo.setAttachedFileId(af.getAttachedFileId()); //이건 optional   board 에 atchfile부분에 넣을 AttcahfiledId
		});

		int chk = informationMapper.informationBoardInsert(ifBoardVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<InformationBoardVO> informationBoardList(String groupId) {

		List<InformationBoardVO> information =
			informationMapper.informationBoardList(groupId);

		return information;

	}




	/**
	 * 기존의 첨부파일 그룹이 있는 경우, 신규 파일과 기존 파일 그룹을 병합해 저장함.  --> 게시글 수정시 사용
	 * @param atchFileId
	 */
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
	public InformationBoardVO informationBoardDetail(InformationBoardVO ifBoardVo) {

		InformationBoardVO information =
				informationMapper.informationBoardDetail(ifBoardVo);

		if(information == null) {
			throw new PkNotFoundException(ifBoardVo.getIfBoardId());
		}




		List<InformationBoardAnswersVO> informationAnswers =
			informationMapper.informationBoardAnswersList(ifBoardVo);

		information.setIfBoardAnswersList(informationAnswers);

		return information;

	}


	//기존값을 가져와서 있는지 없는지부터 판단을 해야함 == 기존에 파일이있는지 없는지 부터 판단
	@Override
	public boolean informationBoardUpdate(InformationBoardVO ifBoardVo) { //새롭게 들어온 정보 = ifBoardVo
		InformationBoardVO oldBoard = informationMapper.informationBoardDetail(ifBoardVo);  // 기존 정보 = oldBoard

		Optional.ofNullable(ifBoardVo.getAttachedFile())
		.filter(af->af.getFileDetails()!=null)
		.ifPresent(af->{

			String newAtchFileId = mergeSavedDetailsAndNewDetails(oldBoard.getAttachedFile() ,af);

			ifBoardVo.setAttachedFileId(newAtchFileId);
		});

		Optional.ofNullable(ifBoardVo.getAttachedFile())   //getAttachedFileId가 null이면 empty를 반환
		.ifPresent(af->{									 //그게아니면 여길탐
			service.createAtchFile(af, saveFolder);
			ifBoardVo.setAttachedFileId(af.getAttachedFileId()); //이건 optional   board 에 atchfile부분에 넣을 AttcahfiledId
		});

		int chk = informationMapper.informationBoardUpdate(ifBoardVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}




	@Override
	public boolean informationBoardDelete(InformationBoardVO ifBoardVo) {

		InformationBoardVO oldBoard = informationMapper.informationBoardDetail(ifBoardVo);


		Optional.ofNullable(oldBoard.getAttachedFileId())
		.ifPresent(fid->service.disableAtchFile(fid));



		int chk = informationMapper.informationBoardDelete(ifBoardVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean informationBoardAnswersInsert(InformationBoardAnswersVO ifBoardAnswVo) {

		int chk = informationMapper.informationBoardAnswersInsert(ifBoardAnswVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean informationBoardAnswersUpdate(InformationBoardAnswersVO ifBoardAnswVo) {

		int chk = informationMapper.informationBoardAnswersUpdate(ifBoardAnswVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean informationBoardAnswersDelete(InformationBoardAnswersVO ifBoardAnswVo) {

		int chk = informationMapper.informationBoardAnswersDelete(ifBoardAnswVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean informationBoardLikeAdd(InformationBoardLikeVO ifBoardLikeVo) {

		int chk = informationMapper.informationBoardLikeAdd(ifBoardLikeVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean informationBoardLikeDelete(InformationBoardLikeVO ifBoardLikeVo) {

		int chk = informationMapper.informationBoardLikeDelete(ifBoardLikeVo);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean informationBoardLikeYN(InformationBoardLikeVO ifBoardLikeVo) {

		List<InformationBoardLikeVO> likeChk = informationMapper.informationBoardLikeYN(ifBoardLikeVo);

		boolean likeYN = false; // 좋아요를 이전에 누르지 않았다면
		if(!likeChk.isEmpty()) {
			likeYN = true; // 좋아요를 이전에 눌렀다면
		}

		return likeYN;

	}

	@Override
	public boolean informationBoardNumPlue(String ifBoardId) {

		int chk = informationMapper.informationBoardNumPlue(ifBoardId);

		if(chk > 0) {
			return true;
		} else {
			return false;
		}

	}


	/**
	 *
	 *	파일관련 메소드
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
