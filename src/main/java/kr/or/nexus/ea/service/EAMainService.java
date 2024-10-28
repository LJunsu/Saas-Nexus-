package kr.or.nexus.ea.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.DocumentsVO;
import kr.or.nexus.vo.ElectronicApprovalVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectParticipantsVO;

/**
 * @author PC-22
 *
 */
public interface EAMainService {

	/**
	 * 그룹 멤버 리스트
	 * @param params
	 * @return
	 */
	public List<MemberManagementVO> retrieveMemberList(Map<String, String> params);

	/**
	 * 전자결재 insert(테이블:ElectronicApprovalVO)
	 * @param electronicApprovalVO
	 */
	public void insertElectronicApproval(ElectronicApprovalVO electronicApprovalVO);

	/**
	 * 최근 insert한 eaId 가져옴
	 * @return
	 */
	public String retrieveRecentEAId();

	/**
	 * 전자결재 insert(테이블:DocumentsVO)
	 * @param documentsVO
	 */
	public void insertDocument(DocumentsVO documentsVO);

	/**
	 * 최근 insert한 documentId 가져옴
	 * @return
	 */
	public String retrieveRecentDocumentId();

	/**
	 * 전자결재 insert(테이블:ProjectParticipantsVO)
	 * @param projectParticipantsVO
	 */
	public void insertParticipants(ProjectParticipantsVO projectParticipantsVO);

	/**
	 * 전체 전자결재 조회
	 * @param memberId
	 * @return
	 */
	public List<Map<String, Object>> retrieveEAList(String memberId);

	/**
	 * 처리 상태별 전자결재 조회
	 * @param memberId
	 * @param approvalProgressStatus
	 * @return
	 */
	public List<Map<String, Object>> retrieveEAListByProgress(Map<String, Object> params);

	/**
	 * 결재 승인 처리
	 * @param electronicApprovalVO
	 */
	public void modifyElectronicApproval(ElectronicApprovalVO electronicApprovalVO);

	/**
	 * ea 조회
	 * @param eaId
	 * @return
	 */
	public ElectronicApprovalVO retrieveEa(String eaId);

	public DocumentsVO retrieveDoc(String eaId);

	public List<ProjectParticipantsVO> retrievePart(String eaId);

	/**
	 * ea listCount 조회
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> retrieveEACountListByProgress(Map<String, Object> params);

	/**
	 * 모든 사원 조회
	 * @param params
	 * @return
	 */
	public List<MemberManagementVO> retrieveAllMemberList(Map<String, String> params);

}