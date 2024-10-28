package kr.or.nexus.gnotification.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.MidiDevice.Info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import kr.or.nexus.commons.alarm.AlarmHandler;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.gnotification.service.GnoticeService;
import kr.or.nexus.gnotification.vo.GnoticeAnswerVO;
import kr.or.nexus.gnotification.vo.GnoticeVO;
import kr.or.nexus.validate.groups.DeleteGroup;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import kr.or.nexus.vo.def.AtchFileVO;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/group/{groupId}/noticeBoard")
public class GnoticeController {



	@Autowired
	private GnoticeService service;

	@Autowired
	private SmartValidator validator;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 공지글 작성
	 *
	 * @param board
	 * @param errors
	 * @return
	 */
	@PostMapping
	public Map<String, Object> insert(@PathVariable("groupId")String groupId, GnoticeVO board, BindingResult errors) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("target", board);
		board.setGroupId(groupId);
		validator.validate(board, errors, InsertGroup.class);
		if (errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
		} else {
			String boardId  = service.createNotice(board);
			System.out.println("ddddddddd"+boardId);
			if(boardId!=null) {
				board.setBoardId(boardId);
				String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
				String url = request.getRequestURI();
				
				GnoticeVO vo = service.createBoardDetail(board);
				Event<GnoticeVO, GnoticeVO> event = new Event<>
												(methodName, boardId, board,
														vo, null, "그룹공지 작성", url);
				publisher.publishEvent(event);
				result.put("success", true);
				result.put("target", vo);
			}



		}
		return result;
	}

	/**
	 * 전체 리스트 조회
	 *
	 * @return
	 */
	@GetMapping("list.do")
	public List<GnoticeVO> list(@PathVariable("groupId") String groupId) {
		return service.noticeSelectAll(groupId);
	}

	@GetMapping("{boardId}")
	public GnoticeVO detail(@PathVariable("groupId")String groupId, @PathVariable String boardId) {
		GnoticeVO selectDetail = new GnoticeVO();
		selectDetail.setGroupId(groupId);
		selectDetail.setBoardId(boardId);

		return service.detailNotice(selectDetail);
	}

	/**
	 * 업데이트
	 *
	 * @param board
	 * @param errors
	 * @return
	 */
	@PostMapping("{boardId}")
	public Map<String, Object> update(GnoticeVO board, BindingResult errors) {
		System.out.println(board.getBoardTitle());
		System.out.println(board.getBoardId());
		System.out.println(board.getGroupId());
		System.out.println(board.getAttachedFile());

		Map<String, Object> result = new HashMap<>();
		result.put("target", board);
		validator.validate(result, errors, UpdateGroup.class);

		if (errors.hasErrors()) {
			System.out.println(board.getBoardTitle() + 1);
			result.put("success", false);
			result.put("errors", errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
		} else {
			try {
				service.modifyBoard(board);
				result.put("success", true);
				result.put("target", service.createBoardDetail(board));
			} catch (Exception e) {
				System.out.println(board.getBoardTitle() + 3);
				result.put("message", e.getMessage());
			}
		}

		return result;
	}
	@DeleteMapping("{boardId}")
	public Map<String, Object> delete(@PathVariable String boardId, @RequestBody GnoticeVO board, BindingResult errors) {
		Map<String, Object> dRslt = new HashMap<String, Object>();
		board.setBoardId(boardId);
		if (errors.hasErrors()) {
			dRslt.put("errors", errors);
		} else {
			try {
				int rslt = service.deleteNoitce(board);
				dRslt.put("rslt", rslt);
				dRslt.put("success", true);
			} catch (Exception e) {
				dRslt.put("message", e.getMessage());
			}
		}

		return dRslt;
	}

	@GetMapping("{boardId}/answerList")
	public List<GnoticeAnswerVO> listAnswer(@PathVariable String boardId) {
		return service.answerList(boardId);
	}


	@PostMapping("{boardId}/answerAdd")
	public Map<String, Object> addAnswer
	(@PathVariable String boardId
		,@RequestBody GnoticeAnswerVO gnoticeAnswerVO
		,BindingResult errors	){

		System.out.println("boardId체크"+boardId);
		System.out.println("컨탠츠 체크"+gnoticeAnswerVO.getCmtContent());
		System.out.println("memberId 체크"+gnoticeAnswerVO.getMemberId());

		Map<String, Object> result = new HashMap<String, Object>();
		gnoticeAnswerVO.setBoardId(boardId);
		result.put("target", gnoticeAnswerVO );
		validator.validate(gnoticeAnswerVO, errors, InsertGroup.class);

		if(errors.hasErrors()) {
		result.put("success", false);
		result.put("errors", errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
		}else {
			service.createAnswer(gnoticeAnswerVO);
			result.put("success", true);
//			result.put("rslt",service.);
		}

		return result;

	}


	@PutMapping("{boardId}/{boardCmtId}/updateAns")
	public Map<String, Object> updateAns(
			 @PathVariable String boardId
			,@RequestBody GnoticeAnswerVO gnoticeAnswerVO
			,BindingResult errors	){
		Map<String, Object> rslt = new HashMap<String, Object>();
		System.out.println("boardId CHK    "+boardId);
		System.out.println("MemberId CHK    "+gnoticeAnswerVO.getMemberId());
		System.out.println("boardContent CHK    "+gnoticeAnswerVO.getCmtContent());
		gnoticeAnswerVO.setBoardId(boardId);
		rslt.put("target", gnoticeAnswerVO);
		validator.validate(gnoticeAnswerVO, errors,UpdateGroup.class);
		if(errors.hasErrors()) {
			rslt.put("success", false);
			rslt.put("errors", errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
		}else {
			int cnt = service.updateAnswer(gnoticeAnswerVO);
			rslt.put("success", true);
			rslt.put("cnt", cnt);
			rslt.put("result", service.answerList(boardId));

		}

		return rslt;
	}




	@DeleteMapping("{boardId}/{boardCmtId}/deleteAns")
	public Map<String,Object> deleteAns(
			 @PathVariable String boardId
			,@PathVariable Integer boardCmtId
			){
		Map<String, Object> rslt = new HashMap<String, Object>();
		GnoticeAnswerVO gnoticeAnswerVO = new GnoticeAnswerVO();

		gnoticeAnswerVO.setBoardId(boardId);
		gnoticeAnswerVO.setBoardCmtId(boardCmtId);
		int cnt = service.deleteAnswer(gnoticeAnswerVO);
		rslt.put("target", gnoticeAnswerVO);
		if(cnt>0) {
			rslt.put("success", true);
			rslt.put("result", service.answerList(boardId));
			rslt.put("cnt",cnt);
		}else {
			rslt.put("success", false);
		}

		return rslt;
	}










}
