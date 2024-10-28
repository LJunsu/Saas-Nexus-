package kr.or.nexus.timeline.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.issue.service.IssueService;
import kr.or.nexus.timeline.service.TimeLineService;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("timeLine")
public class TimeLineController {

	@Autowired
	private TimeLineService timeLineService;

	@Autowired
	private SmartValidator validator;

	@GetMapping("{projectId}/timeLineList")
	public List<IssueVO> timeLineList(
		@PathVariable String projectId
	) {

		List<IssueVO> issueList = timeLineService.timeLineList(projectId);

		return issueList;

	}



	@PostMapping("{projectId}/addOrUpdate")
	public Map<String, Object> timeLineAddOrUpdate(
		IssueVO issueVO
		, BindingResult errors
		, @PathVariable String projectId
	) {

		Map<String, Object> result = new HashMap<>();

		validator.validate(issueVO, errors, InsertGroup.class);

		if(StringUtils.isBlank(issueVO.getSubIssueIdx()) || issueVO.getSubIssueIdx().equals("선택")) {
			issueVO.setSubIssueIdx(null);
		}

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {

			List<IssueVO> timeLineList = timeLineService.timeLineList(projectId);
			boolean idxChk = false;

			for(int i = 0 ; i < timeLineList.size() ; i++) {
				if(timeLineList.get(i).getIssueIdx().equals(issueVO.getIssueIdx())) idxChk = true;
			}

			if(!idxChk) { // 없다면 insert
				issueVO.setIssueProgressStatus("Not Started");
				timeLineService.timeLineInsert(issueVO);
			} else { // 있다면 update
				timeLineService.timeLineUpdate(issueVO);
			}

			result.put("success", true);
			result.put("target", timeLineList);
		}

		return result;

	}



	@GetMapping("{issueIdx}")
	public IssueVO timeLineUpdateData(
		@PathVariable String issueIdx
	) {

		IssueVO issue = timeLineService.timeLineUpdateInputData(issueIdx);

		return issue;

	}




	@PostMapping("search")
	public List<IssueVO> timeLineSearch(
		IssueVO issueVO
	) {

		List<IssueVO> timeLineList = timeLineService.timeLineSearch(issueVO);

		return timeLineList;

	}



	@PostMapping("memberSelect")
	public List<IssueVO> timeLineMemSelect(
		IssueVO issueVO
	) {

		List<IssueVO> timeLineList = timeLineService.timeLineMemSelect(issueVO);

		return timeLineList;

	}



	@DeleteMapping("{issueIdx}")
	public void timeLineDelete(
		@PathVariable String issueIdx
	) {

		int chk = timeLineService.timeLineDelete(issueIdx);

	}

	@PostMapping("timeLineChart")
	public List<IssueVO> timeLineChart(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueList = timeLineService.timelineChart(issueVO);

		return issueList;

	}

	@PostMapping("timeLinePriorityChart")
	public List<IssueVO> timelinePriorityChart(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueList = timeLineService.timelinePriorityChart(issueVO);

		return issueList;

	}

	@PostMapping("timeLineStatusChart")
	public List<IssueVO> timelineStatusChart(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueList = timeLineService.timelineStatusChart(issueVO);

		return issueList;

	}

	@PostMapping("issueDisabList")
	public List<IssueVO> timeLineIssueListDisab(
		@RequestBody IssueVO issueVO
	) {

		List<IssueVO> issueDisabList = timeLineService.timeLineIssueListDisab(issueVO);

		return issueDisabList;

	}

}
