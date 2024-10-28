package kr.or.nexus.timeline.service;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.timeline.dao.TimeLineMapper;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Service
@Aspect
public class TimeLineServiceImpl implements TimeLineService {

	@Autowired
	private TimeLineMapper timeLineMapper;

	@Override
	public List<IssueVO> timeLineList(String projectId) {

		List<IssueVO> issueList = timeLineMapper.timeLineList(projectId);

		return issueList;

	}

	@Override
	public int timeLineInsert(IssueVO issueVO) {

		return timeLineMapper.timeLineInsert(issueVO);

	}

	@Override
	public List<ProjectMemberVO> timeLineProjectMemberList(String projectId) {

		return timeLineMapper.timeLineProjectMemberList(projectId);

	}

	@Override
	public List<IssueVO> timeLineIssueList(String projectId) {

		return timeLineMapper.timeLineIssueList(projectId);

	}

	@Override
	public List<IssueVO> timeLineIssueListDisab(IssueVO issueVO) {

		return timeLineMapper.timeLineIssueListDisab(issueVO);

	}

	@Override
	public IssueVO timeLineUpdateInputData(String issueIdx) {

		return timeLineMapper.timeLineUpdateInputData(issueIdx);

	}

	@Override
	public boolean timeLineUpdate(IssueVO issueVO) {

		int issueChk = timeLineMapper.timeLineUpdate(issueVO);
		int issueTagChk = timeLineMapper.timeLineTagUpdate(issueVO);

		boolean chk = false;
		if(issueChk > 0 && issueTagChk > 0) chk = true;

		return chk;

	}

	@Override
	public List<IssueVO> timeLineSearch(IssueVO issueVO) {

		return timeLineMapper.timeLineSearch(issueVO);

	}

	@Override
	public List<IssueVO> timeLineMemSelect(IssueVO issueVO) {

		return timeLineMapper.timeLineMemSelect(issueVO);

	}

	@Override
	public ProjectMemberVO timeLineOfficerChk(ProjectMemberVO projectMemberVO) {

		return timeLineMapper.timeLineOfficerChk(projectMemberVO);

	}

	@Override
	public int timeLineDelete(String issueIdx) {

		return timeLineMapper.timeLineDelete(issueIdx);

	}

	@Override
	public ProjectMemberVO projectMemId(ProjectMemberVO projectMemberVO) {

		return timeLineMapper.projectMemId(projectMemberVO);

	}

	@Override
	public List<IssueVO> timelineChart(IssueVO issueVO) {

		return timeLineMapper.timelineChart(issueVO);

	}

	@Override
	public List<IssueVO> timelinePriorityChart(IssueVO issueVO) {

		return timeLineMapper.timelinePriorityChart(issueVO);

	}

	@Override
	public List<IssueVO> timelineStatusChart(IssueVO issueVO) {

		return timeLineMapper.timelineStatusChart(issueVO);

	}

}
