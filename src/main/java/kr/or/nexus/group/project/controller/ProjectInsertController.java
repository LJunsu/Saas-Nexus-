package kr.or.nexus.group.project.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.ea.service.EAMainService;
import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.group.project.service.ProjectService;
import kr.or.nexus.vo.DocumentsVO;
import kr.or.nexus.vo.ElectronicApprovalVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectParticipantsVO;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.ProjectVO;
import kr.or.nexus.vo.def.ProjectRoleDefaultVO;

@Controller
@RequestMapping("/group/{groupId}/project")
public class ProjectInsertController {

	@Autowired
	private ProjectService service;
	
	@Autowired
	private EAMainService eaService;

	@Autowired
	private ProjectRoleService roleService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private ProjectMemberService pmService;

	@GetMapping("/insert.do")
	public String insertForm(@PathVariable String groupId, Model model) {
		ProjectVO vo = new ProjectVO();
		vo.setGroupId(groupId);
		service.createProjectForm(vo);
		vo.setGroupId(null);
		service.createProjectFormRole(vo);

		vo = service.retrieveProjectId();

		return "redirect:/group/{groupId}/project/"+vo.getProjectId()+"/update.do";
	}
	
	@PostMapping("/ea/insert")
	public String eaInsert(@PathVariable String groupId, @RequestParam String eaId, Model model) {
		ProjectVO vo = new ProjectVO();
		vo.setGroupId(groupId);
		service.createProjectForm(vo);
		vo.setGroupId(null);
		service.createProjectFormRole(vo);
		
		vo = service.retrieveProjectId();
		
		String projectId = vo.getProjectId();
		
		ElectronicApprovalVO ea = eaService.retrieveEa(eaId);
		DocumentsVO doc = eaService.retrieveDoc(eaId);
		vo.setProjectDescription(doc.getCreationReason());
		vo.setProjectName(doc.getProjectName());
		vo.setProjectEndDate(doc.getCompletionDate());
		if(vo.getProjectName() == null || vo.getProjectName().isEmpty() || vo.getProjectDescription() == null || vo.getProjectDescription().isEmpty()
				|| vo.getProjectEndDate() == null) {
			service.modifyProject(vo);
			 String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			 String url = request.getRequestURI();
			 ProjectVO AfterInsertProject = service.retrieveAfterInsertProject(projectId);
			 Event<ProjectVO, ProjectVO> event = new Event<>(methodName, projectId, null, AfterInsertProject, null, "임시 프로젝트 생성", url);
			 publisher.publishEvent(event);
		}else {
			  service.modifyInsertProject(vo);
			  List<MemberManagementVO> participateMember = service.participateMember(projectId);
			  //생성된거 가져오기
			 String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			 String url = request.getRequestURI();
			 ProjectVO AfterInsertProject = service.retrieveAfterInsertProject(projectId);
			 Event<List<MemberManagementVO>, ProjectVO> event = new Event<>(methodName, projectId, participateMember, AfterInsertProject, null, "프로젝트 생성", url);
			 publisher.publishEvent(event);
		}
		
		List<ProjectParticipantsVO> ppvoList = eaService.retrievePart(eaId);
		ProjectMemberVO pmvo = new ProjectMemberVO();
		ProjectRoleDefaultVO prvo = new ProjectRoleDefaultVO();
		
		prvo.setProjectId(projectId);
		
		pmvo.setGroupId(groupId);
		pmvo.setProjectId(projectId);
		
		for(ProjectParticipantsVO ppvo : ppvoList) {
			prvo.setProjectRoleName(ppvo.getParticipantRole());
			roleService.createProjectRole(prvo);
			ProjectRoleDefaultVO roleIdx = roleService.findIdx(prvo);
			pmvo.setMemberId(ppvo.getMemberId());
			pmvo.setProjectRoleIdx(roleIdx.getProjectRoleIdx());
			pmService.createProjectMember(pmvo);
		}
		
		
		return "redirect:/group/{groupId}/project/"+vo.getProjectId()+"/update.do";
	}

	@PostMapping
	public String projectInsert() {

		return "";
	}
}
