package kr.or.nexus.member.chmain.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.announcement.service.AnnouncementService;
import kr.or.nexus.commons.mapper.AlarmMapper;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.commons.service.SeokjunUtills;
import kr.or.nexus.commons.vo.AlarmVO;
import kr.or.nexus.gnotification.service.GnoticeService;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.issuesummary.service.IssueSummaryService;
import kr.or.nexus.member.chmain.dao.ChMainMapper;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/{groupId}/{projectId}/chMain")
public class ChMainController {
	
	@Autowired
	private ChMainMapper chMainMapper;
	
	@Autowired
	private IssueSummaryService issueSummaryService;

	@Autowired
	private GnoticeService GnoticeService;

	@Autowired
	private AnnouncementService AnnouncementService;

	@Autowired
	private AlarmMapper alarmMapper;

	@GetMapping
	public String main(
		@PathVariable String groupId
		, @PathVariable String projectId
		, Model model
		, Authentication authentication
		, HttpSession session
	) {

		session.setAttribute("projectId", projectId);
//        System.out.println("현재 세션에는" + projectId + "가 저장되어있음");

		String sessionProjectName = chMainMapper.searchProjectName(projectId);
		session.setAttribute("sessionProjectName", sessionProjectName);
        model.addAttribute("groupId", groupId);
        model.addAttribute("projectId", projectId);

        CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        model.addAttribute("realUser", realUser);


        ProjectVO projectMem = issueSummaryService.projectDetail(projectId);
        int projectMemCount = issueSummaryService.projectMemCount(projectMem.getProjectId());
        List<ProjectMemberVO> projectLeader = issueSummaryService.projectLeader(projectMem.getProjectId());
        List<IssueVO> projectIssue = issueSummaryService.issueSummaryStatus(projectMem.getProjectId());
        Double completedPercentage = issueSummaryService.issueCompletedPercentage(projectMem.getProjectId());

		model.addAttribute("projectMem", projectMem);
		model.addAttribute("projectMemCount", projectMemCount);
		model.addAttribute("projectLeader", projectLeader);
		model.addAttribute("projectIssue", projectIssue);
		model.addAttribute("completedPercentage", completedPercentage);


		List<ProjectMemberVO> projectMemList = issueSummaryService.projectMemberInformation(projectId);
		String projectName = issueSummaryService.issueProjectName(projectId).getProjectName();

		model.addAttribute("projectMemList", projectMemList);
		model.addAttribute("projectName", projectName);


		List<GnoticeVO> GnoticeList = GnoticeService.noticeSelectAll(groupId);
		model.addAttribute("GnoticeList", GnoticeList);


		GnoticeVO GnoticeVO = new GnoticeVO();
		GnoticeVO.setGroupId(groupId);
		GnoticeVO.setProjectId(projectId);
		List<GnoticeVO> AnnouncementList = AnnouncementService.chMainAnnouncementBoardList(GnoticeVO);
		model.addAttribute("AnnouncementList", AnnouncementList);


		List<AlarmVO> alarmList = alarmMapper.alarmIssueList(projectId);
		log.info("alarmList : {}", alarmList);
		model.addAttribute("alarmList", alarmList);

		List<String> timeSinceList = new ArrayList<String>();

		for (AlarmVO alarmVO : alarmList) {
			timeSinceList.add(timeSince(alarmVO.getEventdate().toString()));
		}
		model.addAttribute("timeSinceList", timeSinceList);


		return "tiles2:blank/gm-ch-main";
	}

	public String timeSince(String dateTime) {
		try {
			LocalDateTime pastDateTime = LocalDateTime.parse(dateTime);
			LocalDateTime now = LocalDateTime.now();

			long secondsPast = Duration.between(pastDateTime, now).getSeconds();

			if (secondsPast < 60) return secondsPast + "초 전";
			if (secondsPast < 3600) return (secondsPast / 60) + "분 전";
			if (secondsPast < 86400) return (secondsPast / 3600) + "시간 전";
			if (secondsPast < 2592000) return (secondsPast / 86400) + "일 전";
			if (secondsPast < 31536000) return (secondsPast / 2592000) + "개월 전";
			return (secondsPast / 31536000) + "년 전";
		} catch (DateTimeParseException e) {
			return "알 수 없음";
		}
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