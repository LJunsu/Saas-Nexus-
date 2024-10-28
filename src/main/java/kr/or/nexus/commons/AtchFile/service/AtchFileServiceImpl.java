package kr.or.nexus.commons.AtchFile.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.Failable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.AtchFile.dao.AtchFileMapper;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;

@Service
public class AtchFileServiceImpl implements AtchFileService {

	// AtchFileMapper 빈 주입
	@Autowired
	private AtchFileMapper mapper;

	// 파일 첨부 생성 메서드
	@Override
	public void createAtchFile(AtchFileVO atchFile, File saveFolder) {
		// Optional을 사용하여 파일 첨부 객체가 존재하는지 확인하고, 존재하면 파일을 저장


		Optional.of(atchFile)
		.map(AtchFileVO::getFileDetails) // 파일 상세 정보 가져오기
		.ifPresent(fds -> fds.forEach(Failable.asConsumer(fd -> fd.uploadFileSaveTo(saveFolder)) // 각 파일을 저장 폴더에
																									// 업로드
		));

		mapper.insertAtchFile(atchFile); // 파일 첨부 정보를 DB에 저장
	}

//////////////////////////////////////////////////////////////////////////////////////////////////
	// 파일 메타데이터와 2진 데이터(징짜 데이터) 결합 메서드
	private void mergeMetadAndBinaryData(AtchFileDetailsVO fileDetail, File saveFolder) {
		// 저장된 파일의 경로를 FileSystemResource로 설정
		FileSystemResource savedFile = new FileSystemResource(new File(saveFolder, fileDetail.getSaveFileName()));
		fileDetail.setSavedFile(savedFile); // 파일 상세 정보에 저장된 파일 설정
	} // 여기서 합쳐서 읽어야 진짜 읽히는듯?
	// 파일 첨부 읽기 메서드
	// return mergeMetadAndBinaryData + readAtchFile

	@Override
	public AtchFileVO readAtchFile(String atchFileId, boolean enable, File saveFolder) {
		AtchFileVO atchFile = mapper.selectAtchFile(atchFileId, enable); // 파일 첨부 정보 조회
		Optional.ofNullable(atchFile).map(AtchFileVO::getFileDetails) // 파일 상세 정보 가져오기
				.ifPresent(fds -> fds.forEach(fd -> mergeMetadAndBinaryData(fd, saveFolder)) // 각 파일의 메타데이터와 2진 데이터를 결합
				);
		return atchFile; // 파일 첨부 객체 반환
	}
//////////////////////////////////////////////////////////////////////////////////////////////

	// 파일 상세 정보 읽기 메서드
	// 실제 다운로드
	@Override
	public AtchFileDetailsVO readAtchFileDetail(String atchFileId, String fileSn, File saveFolder) {
		AtchFileDetailsVO fileDetail = mapper.selectAtchFileDetail(atchFileId, fileSn); // 파일 상세 정보 조회
		if (fileDetail != null) {
			mergeMetadAndBinaryData(fileDetail, saveFolder); // 메타데이터와 2진 데이터를 결합
			mapper.incrementDowncount(atchFileId, fileSn); // 다운로드 횟수 증가
		}
		return fileDetail; // 파일 상세 정보 객체 반환
	}

	// 파일 상세 정보와 2진 데이터를 삭제하는 메서드
	private void deleteFileDetail(AtchFileDetailsVO fileDetail, File saveFolder) throws IOException {
		mergeMetadAndBinaryData(fileDetail, saveFolder); // 메타데이터와 2진 데이터를 결합
		FileUtils.deleteQuietly(fileDetail.getSavedFile().getFile()); // 파일 삭제
		mapper.deleteAtchFileDetail(fileDetail.getAttachedFileDetailId(), fileDetail.getAttachedFileId()); // 파일 상세 정보
																											// 삭제
	}

	// 파일 상세 정보 삭제 메서드
	@Override
	public void removeAtchFileDetail(String atchFileId, String fileSn, File saveFolder) {
		AtchFileDetailsVO target = mapper.selectAtchFileDetail(atchFileId, fileSn); // 삭제할 파일 상세 정보 조회
		Optional.ofNullable(target).ifPresent(Failable.asConsumer(fd -> deleteFileDetail(fd, saveFolder))); // 파일 상세 정보를
																											// 삭제
	}

	// 파일 첨부 비활성화 메서드
	@Override
	public void disableAtchFile(String atchFildId) {
		mapper.disableAtchFile(atchFildId); // 파일 첨부 비활성화
	}

	// 비활성화된 파일 첨부 삭제 메서드
	@Override
	public void removeDiabledAtchFile(String atchFileId) {
		mapper.deleteDisabledAtchFileDetails(atchFileId); // 비활성화된 파일 상세 정보 삭제
		mapper.deleteDisabledAtchFile(atchFileId); // 비활성화된 파일 첨부 삭제
	}
}
