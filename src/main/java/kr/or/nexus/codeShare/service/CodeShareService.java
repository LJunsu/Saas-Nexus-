package kr.or.nexus.codeShare.service;

import java.util.List;

import kr.or.nexus.codeShare.vo.codeShareVO;

public interface CodeShareService {

	/**
	 * groupId, projectId, projectMemIdx로 코드쉐어 생성
	 */
	public int codeShareInsert(codeShareVO codeShareVO);

	/**
	 * groupId, projectId로 코드쉐어 리스트 조회
	 */
	public List<codeShareVO> codeShareList(codeShareVO codeShareVO);

	/**
	 * groupId, projectId, codeShareIdx로 코드쉐어 디테일 조회
	 */
	public codeShareVO codeShareDetail(codeShareVO codeShareVO);

	/**
	 * codeShareIdx, codeShareContent로 코드쉐어 내용 변경
	 */
	public int codeShareUpdate(codeShareVO codeShareVO);

	/**
	 * codeShareIdx로 코드쉐어 삭제
	 */
	public int codeShareDelete(codeShareVO codeShareVO);

	/**
	 * groupId, projectId, memberId로 코드쉐어 생성 후 생성된 코드쉐어 상세 vo 조회
	 */
	public codeShareVO codeShareCreate(codeShareVO codeShareVO);

}
