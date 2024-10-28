package kr.or.nexus.commons.AtchFile.service;

import java.io.File;

import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;


/**
 * @author DDIT_CHY
 *
 */
public interface AtchFileService {



	/**
	 * 첨부파일 그룹 저장(메타데이터와 2진 데이터 분리 저장)
	 * @param atchFile
	 * @param saveFolder 2진 데이터 저장 디렉토리
	 * @throws Exception
	 */
	public void createAtchFile(AtchFileVO atchFile, File saveFolder);



	/**
	 * 사용중인 첨부파일 그룹 조회
	 * @param atchFileId
	 * @param saveFolder  2진 데이터 저장 디렉토리
	 * @return
	 */
	public default AtchFileVO readAtchFileEnable(String atchFileId, File saveFolder) {
		return readAtchFile(atchFileId, true, saveFolder);
	}



	/**
	 * 사용 여부에 따른 첨부파일 그룹 조회
	 * @param atchFileId
	 * @param enable
	 * @param saveFolder
	 * @return
	 */
	public AtchFileVO readAtchFile(String atchFileId, boolean enable, File saveFolder);



	/**
	 * 첨부파일 한건 조회
	 * @param atchFileId
	 * @param fileSn
	 * @param saveFolder  2진 데이터 저장 디렉토리
	 * @return
	 */
	public AtchFileDetailsVO readAtchFileDetail(String atchFileId, String fileSn, File saveFolder);



	/**
	 * 파일 한건 삭제 (메타데이터와 2진 데이터 분리 삭제)
	 * @param atchFileId
	 * @param fileSn
	 * @param saveFolder  2진 데이터 저장 디렉토리
	 */
	public void removeAtchFileDetail(String atchFileId, String fileSn, File saveFolder);



	/**
	 * 파일 그룹 비활성화
	 * @param atchFildId
	 */
	public void disableAtchFile(String atchFildId);




	/**
	 * 사용하지 않는 파일 그룹 제거
	 * @param atchFileId
	 */
	public void removeDiabledAtchFile(String atchFileId);
}



















