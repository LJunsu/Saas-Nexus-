package kr.or.nexus.ea.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.DocumentsVO;
import kr.or.nexus.vo.ElectronicApprovalVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectParticipantsVO;

@Mapper
public interface EAMainMapper {

	public List<MemberManagementVO> retrieveMemberList(Map<String, String> params);

	public void insertElectronicApproval(ElectronicApprovalVO electronicApprovalVO);

	public String retrieveRecentEAId();

	public void insertDocument(DocumentsVO documentsVO);

	public String retrieveRecentDocumentId();

	public void insertParticipants(ProjectParticipantsVO projectParticipantsVO);

	public List<Map<String, Object>> retrieveEAList(String memberId);

	public List<Map<String, Object>> retrieveEAListByProgress(Map<String, Object> params);

	public void modifyElectronicApproval(ElectronicApprovalVO electronicApprovalVO);

	public ElectronicApprovalVO retrieveEa(String eaId);

	public DocumentsVO selectDoc(String eaId);

	public List<ProjectParticipantsVO> selectPart(String eaId);

	public List<Map<String, Object>> retrieveEACountListByProgress(Map<String, Object> params);

	public List<MemberManagementVO> retrieveAllMemberList(Map<String, String> params);

}
