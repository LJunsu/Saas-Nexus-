package kr.or.nexus.issuesummary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kr.or.nexus.issuesummary.service.IssueSummaryService;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;

@Controller
public class IssueSummaryController {

	@Autowired
	private IssueSummaryService issueSummaryService;

	@GetMapping("group/{groupId}/issueSummary")
	public String issueSummaryGroupList(
		@PathVariable String groupId
		, Model model
	) {

		GroupManagementVO group = issueSummaryService.issueGroupName(groupId);
		List<ProjectVO> projectMemList = issueSummaryService.projectList(groupId);

		Map<String, Integer> projectMemCountMap = new HashMap();
		Map<String, List<ProjectMemberVO>> projectLeader = new HashMap();
		Map<String, List<IssueVO>> projectIssue = new HashMap();
		Map<String, Double> completedPercentageMap = new HashMap();
		for(ProjectVO projectMem : projectMemList) {
			projectMemCountMap.put(projectMem.getProjectId(), issueSummaryService.projectMemCount(projectMem.getProjectId()));
			projectLeader.put(projectMem.getProjectId(), issueSummaryService.projectLeader(projectMem.getProjectId()));
			projectIssue.put(projectMem.getProjectId(), issueSummaryService.issueSummaryStatus(projectMem.getProjectId()));
			completedPercentageMap.put(projectMem.getProjectId(), issueSummaryService.issueCompletedPercentage(projectMem.getProjectId()));
		}

		model.addAttribute("groupId", groupId);
		model.addAttribute("group", group);
		model.addAttribute("projectMemList", projectMemList);
		model.addAttribute("projectMemCountMap", projectMemCountMap);
		model.addAttribute("projectLeader", projectLeader);
		model.addAttribute("projectIssue", projectIssue);
		model.addAttribute("completedPercentageMap", completedPercentageMap);

		return "tiles:issueSummary/issueSummaryGroup";

	}



	@GetMapping("group/{groupId}/project/{projectId}/issueSummary")
	public String issueSummaryProjectUI(
		@PathVariable String groupId
		, @PathVariable String projectId
		, Model model
	) {

		List<ProjectMemberVO> projectMemList = issueSummaryService.projectMemberInformation(projectId);
		String projectName = issueSummaryService.issueProjectName(projectId).getProjectName();
		model.addAttribute("projectMemList", projectMemList);
		model.addAttribute("projectId", projectId);
		model.addAttribute("projectName", projectName);

		return "tiles2:issueSummary/issueSummaryProject";

	}



	@PostMapping("DateChart")
	public List<IssueVO> issueSummaryDateChart(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueList = issueSummaryService.issueSummaryDateChart(issueVO);
		return issueList;

	}



	@PostMapping("PriorityChart")
	public List<IssueVO> issueSummaryPriorityChart(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueList = issueSummaryService.issueSummaryPriorityChart(issueVO);
		return issueList;

	}

	@PostMapping("StatusChart")
	public List<IssueVO> issueSummaryStatusChart(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueList = issueSummaryService.issueSummaryStatusChart(issueVO);
		return issueList;

	}

}
