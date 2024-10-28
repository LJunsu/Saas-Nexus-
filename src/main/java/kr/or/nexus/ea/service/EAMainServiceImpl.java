package kr.or.nexus.ea.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.ea.dao.EAMainMapper;
import kr.or.nexus.vo.DocumentsVO;
import kr.or.nexus.vo.ElectronicApprovalVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectParticipantsVO;

@Service
public class EAMainServiceImpl implements EAMainService {

	@Autowired
	private EAMainMapper dao;

	@Override
	public List<MemberManagementVO> retrieveMemberList(Map<String, String> params) {
		return dao.retrieveMemberList(params);
	}

	@Override
	public void insertElectronicApproval(ElectronicApprovalVO electronicApprovalVO) {
		dao.insertElectronicApproval(electronicApprovalVO);
	}

	@Override
	public String retrieveRecentEAId() {
		return dao.retrieveRecentEAId();
	}

	@Override
	public void insertDocument(DocumentsVO documentsVO) {
		dao.insertDocument(documentsVO);
	}

	@Override
	public String retrieveRecentDocumentId() {
		return dao.retrieveRecentDocumentId();
	}

	@Override
	public void insertParticipants(ProjectParticipantsVO projectParticipantsVO) {
		dao.insertParticipants(projectParticipantsVO);
	}

	@Override
	public List<Map<String, Object>> retrieveEAList(String memberId) {
		return dao.retrieveEAList(memberId);
	}

	@Override
	public List<Map<String, Object>> retrieveEAListByProgress(Map<String, Object> params) {
		return dao.retrieveEAListByProgress(params);
	}

	@Override
	public void modifyElectronicApproval(ElectronicApprovalVO electronicApprovalVO) {
		dao.modifyElectronicApproval(electronicApprovalVO);
	}

	@Override
	public ElectronicApprovalVO retrieveEa(String eaId) {
		return dao.retrieveEa(eaId);
	}

	@Override
	public DocumentsVO retrieveDoc(String eaId) {
		return dao.selectDoc(eaId);
	}

	@Override
	public List<ProjectParticipantsVO> retrievePart(String eaId) {
		return dao.selectPart(eaId);
	}

	@Override
	public List<Map<String, Object>> retrieveEACountListByProgress(Map<String, Object> params) {
		return dao.retrieveEACountListByProgress(params);
	}

	@Override
	public List<MemberManagementVO> retrieveAllMemberList(Map<String, String> params) {
		return dao.retrieveAllMemberList(params);
	}
}
