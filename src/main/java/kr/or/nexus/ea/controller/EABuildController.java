package kr.or.nexus.ea.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.commons.alarmAndLog.EventConstuctor.EaEvent;
import kr.or.nexus.ea.service.EAMainService;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.vo.DocumentsVO;
import kr.or.nexus.vo.ElectronicApprovalVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectParticipantsVO;

@RestController
@RequestMapping("/{groupId}/ea/build")
public class EABuildController {	
	
	@Autowired
	private EAMainService service;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	public Map<String, Object> insert(@RequestBody Map<String, Object> requestData,  BindingResult errors, HttpSession session,
			@PathVariable String groupId) {
		System.out.println("electronicApprovalVO !!!!! : " + requestData);
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");
		String memberId = user.getMemberId();
		String memberName= user.getMemberName();	
		Map<String, Object> result = new HashMap<>();
		validator.validate(requestData, errors, InsertGroup.class);

		String eaReceiver = (String) requestData.get("eaReceiver");
		String creationReason = (String) requestData.get("creationReason");
		String DcompletionDate = (String) requestData.get("completionDate");
		LocalDate completionDate = LocalDate.parse(DcompletionDate);
		String projectName = (String) requestData.get("projectName");
		String DtotalParticipants = (String) requestData.get("totalParticipants");
		Object fileUrlObj = requestData.get("fileUrl");

		String fileUrl = null;
		if (fileUrlObj instanceof ArrayList) {
		    fileUrl = ((ArrayList<String>) fileUrlObj).get(0);
		} else if (fileUrlObj instanceof String) {
		    fileUrl = (String) fileUrlObj;
		}

		if (fileUrl != null) {
		    System.out.println("File URL: " + fileUrl);
		}


		int totalParticipants = Integer.parseInt(DtotalParticipants);
		List<Map<String, String>> participants = (List<Map<String, String>>)requestData.get("partsList");
		
		ElectronicApprovalVO electronicApprovalVO = new ElectronicApprovalVO();
		electronicApprovalVO.setEaSender(memberId);
		electronicApprovalVO.setEaReceiver(eaReceiver);
		
		DocumentsVO documentsVO = new DocumentsVO();
		documentsVO.setCreationReason(creationReason);
		documentsVO.setCompletionDate(completionDate);
		documentsVO.setProjectName(projectName);
		documentsVO.setTotalParticipants(totalParticipants);
		documentsVO.setFileUrl(fileUrl);
		String eaId = null;
		String documentId = null;

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			service.insertElectronicApproval(electronicApprovalVO);
			eaId = service.retrieveRecentEAId();
			documentsVO.setEaId(eaId);
			
			service.insertDocument(documentsVO);
			documentId = service.retrieveRecentDocumentId();
			
			
			for (Map<String, String> participant : participants) {
				ProjectParticipantsVO projectParticipantsVO = new ProjectParticipantsVO();
				String participantEmail = (String)participant.get("participantEmail");
				String memberId2 = participant.get("memberId");
				String participantName = participant.get("participantName");
				String participantRemark = participant.get("participantRemark");
				String participantRole = participant.get("participantRole");
				projectParticipantsVO.setDocumentId(documentId);
				projectParticipantsVO.setEaId(eaId);
				projectParticipantsVO.setMemberId(memberId2);
				projectParticipantsVO.setParticipantEmail(participantEmail);
				projectParticipantsVO.setParticipantName(participantName);
				projectParticipantsVO.setParticipantRemark(participantRemark);
				projectParticipantsVO.setParticipantRole(participantRole);
				service.insertParticipants(projectParticipantsVO);
			}
			result.put("success", true);
			EaEvent event = new EaEvent(documentId, groupId, memberId, memberName,
					eaReceiver, creationReason,"결재 승인 요청",eaId,projectName);
			publisher.publishEvent(event);
			
			
			/* result.put("target", issueList); */
		}
		return result;
	}
}
