package kr.or.nexus.group.project.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nexus.cloud.service.GcpService;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.group.project.service.ProjectService;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.ProjectVO;

@Controller
@RequestMapping("/group/{groupId}")
public class ProjectUpdateController {

	@Autowired
	private ProjectService service;

	@Autowired
	private ProjectRoleService roleService;

	@Autowired
	private ProjectMemberService pmService;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private GcpService gcpService;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("project/{projectId}/update.do")
	public String projectForm(@PathVariable String projectId, Model model) {
		ProjectMemberVO pmvo = new ProjectMemberVO();
		ProjectVO vo = service.retrieveProject(projectId);
		String groupId = vo.getGroupId();
		pmvo.setGroupId(groupId);
		pmvo.setProjectId(projectId);

		List<ProjectRoleVO> prlist = roleService.retrieveRoleList(projectId);
		List<ProjectMemberVO> gmList = service.retrieveProjectMemberInsertList(pmvo);
		List<ProjectMemberVO> pmList = pmService.retrieveProjectMemberSelectedList(pmvo);
		model.addAttribute("projectRole", prlist);
		model.addAttribute("groupMember", gmList);
		model.addAttribute("projectMember", pmList);
		model.addAttribute("project", vo);
		model.addAttribute("groupId", groupId);
		return "tiles:groupManager/projectUpdate";
	}

	@PostMapping("project/{projectId}/update.do")
	public String projectUpdate(@ModelAttribute ProjectVO project
			, @RequestParam ("projectImg") MultipartFile file
			, Errors errors
			, RedirectAttributes redirectAttributes) throws IOException {

		String groupId = project.getGroupId();
		String projectId = project.getProjectId();
		
		redirectAttributes.addFlashAttribute("project", project);
		if(!errors.hasErrors()) {
			redirectAttributes.addFlashAttribute("redirect", "redirect");
			String folderPath = "projectProfile/" + project.getProjectId();
			String profileUrl = gcpService.uploadProfile(file, folderPath);
			if(profileUrl != null)
				project.setProjectProfileImg(profileUrl);
			if(project.getProjectName() == null || project.getProjectName().isEmpty() || project.getProjectDescription() == null || project.getProjectDescription().isEmpty()
					|| project.getProjectEndDate() == null || project.getProjectBudget() == null) {
				service.modifyProject(project);
				 String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				 String url = request.getRequestURI();
				 ProjectVO AfterInsertProject = service.retrieveAfterInsertProject(projectId);
				 Event<ProjectVO, ProjectVO> event = new Event<>(methodName, projectId, null, AfterInsertProject, null, "임시 프로젝트 생성", url);
				 publisher.publishEvent(event);
			}else {
				  service.modifyInsertProject(project);
				  List<MemberManagementVO> participateMember = service.participateMember(projectId);
				  //생성된거 가져오기
				 String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				 String url = request.getRequestURI();
				 ProjectVO AfterInsertProject = service.retrieveAfterInsertProject(projectId);
				 Event<List<MemberManagementVO>, ProjectVO> event = new Event<>(methodName, projectId, participateMember, AfterInsertProject, null, "프로젝트 생성", url);
				 publisher.publishEvent(event);
			}
			return "redirect:/group/{groupId}";
		}else {
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX+"project", errors);
			return "redirect:/group/{groupId}/project/{projectId}/update.do";
		}

	}

	@PostMapping("project/{projectId}/delete.do")
	public String projectDelete(@PathVariable String projectId) {

		service.deleteProject(projectId);

		return "redirect:/group/{groupId}";
	}

}
