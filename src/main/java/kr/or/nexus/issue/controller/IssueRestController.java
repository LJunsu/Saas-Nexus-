package kr.or.nexus.issue.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.commons.AtchFile.service.AtchFileService;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.commons.alarmAndLog.EventType.IssueEventType;
import kr.or.nexus.group.project.dao.ProjectMemberMapper;
import kr.or.nexus.issue.service.IssueService;
import kr.or.nexus.timeline.service.TimeLineService;
import kr.or.nexus.vo.IssueBoardAnswersVO;
import kr.or.nexus.vo.IssueTagsVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/issue")
public class IssueRestController {

	@Autowired
	private IssueService service;
	
	@Autowired
	private AtchFileService FileService;

	@Autowired
	private TimeLineService timeLineService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ProjectMemberMapper mapper;
	
	
	


	//이슈 리스트 출력
	@GetMapping("/project/{projectId}")
	public List<IssueVO> projectList(@PathVariable String projectId,
			@RequestParam(required = false) String issueProgressStatus,
	        @RequestParam(required = false) String issueTitle){
		return service.projectIssueList(projectId,issueProgressStatus,issueTitle);
	}

	//선택한 이슈 상세조회
	@GetMapping("{issueIdx}")
	public IssueVO issueSelect(@PathVariable String issueIdx){
			String url = request.getRequestURI();
			log.info("현재 요청 페이지 {}",url);

		return service.issueSelect(issueIdx);
	}
	//프로젝트에 속한 멤버를 조회
	@GetMapping("/member/{projectId}")
	public List<ProjectMemberVO> projectMember(@PathVariable String projectId){

		return mapper.issueProjectMember(projectId);
	}
	//댓글을 조회
	@GetMapping("/command/{issueIdx}")
	public List<IssueBoardAnswersVO> issueCommandList(@PathVariable String issueIdx){
		return service.issueCommentList(issueIdx);
	}
	//내용을 수정
	@PostMapping("/content/{issueIdx}")
	public int updateContent(@RequestBody IssueVO issueVO, @PathVariable("issueIdx") String issueIdx) {
		log.info("senderId {}:",issueVO.getSenderId());
		log.info("senderName {}:",issueVO.getSenderName());
		log.info("Received issueIdx: {}", issueIdx);
		IssueVO beforeData = service.beforeIssueDetail(issueIdx);
		int cnt = service.correctionIssueContent(issueVO.getIssueContent(), issueIdx);
		if(cnt >0) {
			IssueVO afterData = service.issueSelect(issueIdx);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String url = request.getRequestURI();
			log.info("현재 요청 페이지 {}",url);
			Event<IssueVO,IssueVO> event = new Event<>(methodName,beforeData.getProjectMemIdx()
					,beforeData ,afterData, IssueEventType.ISSUE_UPDATED ,"이슈 내용 수정",url
					);
			log.info(methodName);
			publisher.publishEvent(event);
			}
		return cnt;
	}
	//제목을 수정
	@PostMapping("/title/{issueIdx}")
	public int updateTitle( @RequestBody IssueVO issueVO, @PathVariable("issueIdx") String issueIdx , HttpSession session) {
		log.info("senderId {}:",issueVO.getSenderId());
		log.info("senderName {}:",issueVO.getSenderName());
		log.info("Received issueIdx: {}", issueIdx);
		IssueVO beforeData = service.beforeIssueDetail(issueIdx);
		int cnt = service.correctionIssueTitle(issueVO.getIssueTitle(), issueIdx);
		if(cnt >0) {
		IssueVO afterData = service.issueSelect(issueIdx);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String url = request.getRequestURI();
		log.info("현재 요청 페이지 {}",url);
		Event<IssueVO,IssueVO> event = new Event<>(methodName,beforeData.getProjectMemIdx()
				,beforeData ,afterData, IssueEventType.ISSUE_UPDATED ,"이슈 제목 수정",url
				);
		log.info(methodName);
		publisher.publishEvent(event);
		}

		return cnt;
	}
	//상위 이슈를 조회
	@PostMapping("/uplist/{projectId}")
	public List<IssueVO> upList(@PathVariable("projectId") String projectId){
		return timeLineService.timeLineIssueList(projectId);

	}

	//상위 이슈를 변경
	@PostMapping("/upissue/{issueIdx}")
	public int upIssue(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueVO issueVO) {
			IssueVO beforeData = service.issueSelect(issueIdx);
			int cnt = service.correctionUpIssue(issueIdx, issueVO.getSubIssueIdx());
			if(cnt >0) {
			IssueVO afterData = service.issueSelect(issueIdx);
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			String url = request.getRequestURI();
			log.info("현재 요청 페이지 {}",url);
			Event event = new Event(methodName,beforeData.getProjectMemIdx()
					,beforeData ,afterData, IssueEventType.ISSUE_UPDATED ,"상위 이슈 변경",url
					);
			log.info(methodName);
			publisher.publishEvent(event);
			}
			return cnt;
		}


	//담당자를 변경
	@PostMapping("/officer/{issueIdx}")
	public int updateOfficer(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueVO issueVO) {
		IssueVO beforeData = service.issueSelect(issueIdx);
		int cnt = service.correctionOfficer(issueIdx, issueVO.getIssueOfficer());
		if(cnt >0) {
		IssueVO afterData = service.issueSelect(issueIdx);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String url = request.getRequestURI();
		log.info("현재 요청 페이지 {}",url);
		Event event = new Event(methodName,beforeData.getProjectMemIdx()
				,beforeData ,afterData, IssueEventType.ISSUE_UPDATED ,"담당자 변경",url);
		log.info(methodName);
		publisher.publishEvent(event);
		}
		return cnt;
	}

	//우선순위 변경
	@PostMapping("/priority/{issueIdx}")
	public int correctionPriority(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueVO issueVO) {


		IssueVO beforeData = service.issueSelect(issueIdx);
		int cnt =  service.correctionPriority(issueIdx, issueVO.getIssuePriorityName());
		if(cnt >0) {
		IssueVO afterData = service.issueSelect(issueIdx);
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		String url = request.getRequestURI();
		log.info("현재 요청 페이지 {}",url);
		Event event = new Event(methodName,beforeData.getProjectMemIdx()
				,beforeData ,afterData, IssueEventType.ISSUE_UPDATED ,"우선 순위 변경",url);
		log.info(methodName);
		publisher.publishEvent(event);
		}
		return cnt;
	}

	//우선순위 변경
	@PostMapping("/progress/{issueIdx}")
	public int correctionProgressStatus(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueVO issueVO) {
		return service.correctionProgressStatus(issueIdx, issueVO.getIssueProgressStatus());
	}

	// 이슈 시작기한 변경
    @PostMapping("/startDate/{issueIdx}")
    public int updateIssueStartDate(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueVO issueVO) {
        return service.correctionIssueStartDate(issueVO.getIssueDate(), issueIdx);
    }

    // 이슈 마감기한 변경
    @PostMapping("/deadline/{issueIdx}")
    public int updateIssueDeadLineDate(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueVO issueVO) {
        return service.correctionIssueDeadLineDate(issueVO.getIssueDeadline(), issueIdx);
    }
    @PostMapping("/tag/{issueIdx}")
    public int updatetags(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueTagsVO issuetagsVO) {
    	return service.correctionIssueTags(issuetagsVO.getIssueTagName(), issueIdx);
    }

    //이슈 생성
    @PostMapping("/create")
    public String insertIssue(@RequestBody IssueVO issueVO) {
    	log.info("senderId {}  ",issueVO.getSenderId());
    	log.info("senderName {}  ",issueVO.getSenderName());
    	String issueIdx = service.createIssue(issueVO);
    	service.issueCreateTag(issueVO);
    	log.info("issueIdx {}  ",issueIdx);
    	if(issueIdx!=null) {
    		IssueVO createResult = service.beforeIssueDetail(issueIdx);
    			if(createResult!=null) {
    			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
    			String url = request.getRequestURI();
    			log.info("현재 요청 페이지 {}",url);
    			Event event = new Event(methodName,issueIdx
    					,null ,createResult, IssueEventType.ISSUE_UPDATED ,"이슈 생성",url);
    				log.info(methodName);
    				publisher.publishEvent(event);
    			}
    	}
    	return issueIdx;
    }

    @PostMapping("/cmt/{issueIdx}")
    public int updatetags(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueBoardAnswersVO issueBoardVO) {
    	return service.createIssueBoard(issueBoardVO.getIssueCmtWriter(),issueBoardVO.getIssueCmtContent() , issueIdx);
    }

    @PostMapping("/cmtdel/{issueIdx}")
    public int issueCmtDelete(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueBoardAnswersVO issueBoardVO) {
    	return service.issueCmtDelete(issueIdx, issueBoardVO.getIssueCommentId());
    }
    @PostMapping("/modifyComment/{issueIdx}")
    public int modifyComment(@PathVariable("issueIdx") String issueIdx, @RequestBody IssueBoardAnswersVO issueBoardVO) {
    	return service.issueCmtDelete(issueIdx, issueBoardVO.getIssueCommentId(), issueBoardVO.getIssueCmtContent() );
    }
    @PostMapping("/issueDelete/{issueIdx}")
    public int issueDelete(@PathVariable("issueIdx") String issueIdx) {
    	return service.issueDelete(issueIdx);
    }
    
   

}
