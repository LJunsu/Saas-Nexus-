package kr.or.nexus.announcement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.or.nexus.announcement.service.AnnouncementAnswerService;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.AnnouncementAnswersDefaultVO;

@RestController
@RequestMapping("/{groupId}/{projectId}/announcement/{boardId}/comment")
public class AnnouncementAnswerController {
	@Autowired
	private AnnouncementAnswerService service;

	@RequestMapping("/insert")
	public void insert(@ModelAttribute AnnouncementAnswersDefaultVO answer, HttpSession session,
			Authentication authentication) {
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		answer.setMemberId(realUser.getMemberId());
		service.createAnswer(answer);
	}

	@RequestMapping("/listJson")
	public Map<String, Object> listJson(@PathVariable String boardId, Authentication authentication) {
		// 댓글 목록 가져오기
		List<AnnouncementAnswersDefaultVO> answerList = service.readAnswerList(boardId);

		// 현재 로그인된 사용자 정보 가져오기
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();

		// 결과를 담을 Map 객체 생성
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("answerList", answerList); // 댓글 목록
		resultMap.put("currentUserId", realUser.getMemberId()); // 현재 로그인된 사용자 ID

		// Map 객체 반환
		return resultMap;
	}

	@PutMapping("/update")
	@ResponseBody
	public String update(@RequestParam("boardCmtId") int boardCmtId, @RequestParam("cmtContent") String cmtContent,
			Authentication authentication) {
		// 현재 로그인된 사용자 정보 가져오기
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();

		// VO 객체 생성 및 값 설정
		AnnouncementAnswersDefaultVO answer = new AnnouncementAnswersDefaultVO();
		answer.setBoardCmtId(boardCmtId); // 댓글 ID 설정
		answer.setCmtContent(cmtContent); // 새로운 댓글 내용 설정
		answer.setMemberId(realUser.getMemberId()); // 수정한 사용자의 ID 설정

		try {
			service.modifyAnswer(answer); // 댓글 수정 처리
			return "success"; // 성공 시 "success" 반환
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // 오류 발생 시 "error" 반환
		}
	}
	@DeleteMapping("/delte")
	@ResponseBody
	public String delete(@RequestParam("boardCmtId") int boardCmtId,
			Authentication authentication) {
		// 현재 로그인된 사용자 정보 가져오기
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();

		// VO 객체 생성 및 값 설정
		AnnouncementAnswersDefaultVO answer = new AnnouncementAnswersDefaultVO();
		answer.setBoardCmtId(boardCmtId); // 댓글 ID 설정
		answer.setMemberId(realUser.getMemberId()); // 수정한 사용자의 ID 설정

		try {
			service.removeAnswer(answer); // 댓글 수정 처리
			return "success"; // 성공 시 "success" 반환
		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // 오류 발생 시 "error" 반환
		}
	}
}
