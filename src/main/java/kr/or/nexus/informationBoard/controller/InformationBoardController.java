package kr.or.nexus.informationBoard.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import kr.or.nexus.informationBoard.service.InformationBoardService;
import kr.or.nexus.informationBoard.vo.InformationBoardAnswersVO;
import kr.or.nexus.informationBoard.vo.InformationBoardLikeVO;
import kr.or.nexus.informationBoard.vo.InformationBoardVO;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.def.AtchFileVO;


@RestController
@RequestMapping("/informationBoard")
public class InformationBoardController {

	@Autowired
	private InformationBoardService informationService;

	@Autowired
	private SmartValidator validator;

	@GetMapping("{groupId}")
	public List<InformationBoardVO> list(@PathVariable String groupId) {

		return informationService.informationBoardList(groupId);

	}

	@GetMapping("{groupId}/{ifBoardId}")
	public InformationBoardVO detail(
		@PathVariable String groupId
		, @PathVariable String ifBoardId
	) {

//		informationService.informationBoardNumPlue(ifBoardId);

		InformationBoardVO informationVO = new InformationBoardVO();

		informationVO.setGroupId(groupId);
		informationVO.setIfBoardId(ifBoardId);


		return informationService.informationBoardDetail(informationVO);

	}

	@GetMapping("{groupId}/{ifBoardId}/boardNumPlue")
	public void boardNumPlue(
		@PathVariable String ifBoardId
	) {

		informationService.informationBoardNumPlue(ifBoardId);

	}

	@PostMapping
	public Map<String, Object> boardAdd(
//		@RequestBody InformationBoardVO informationVO
		InformationBoardVO informationVO
		, BindingResult errors
	) {

		Map<String, Object> result = new HashMap<>();
		AtchFileVO atch  = new AtchFileVO();
		result.put("target", informationVO);

		validator.validate(informationVO, errors, InsertGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			informationService.informationBoardInsert(informationVO);

			result.put("success", true);
			result.put("target", informationService.informationBoardList(informationVO.getGroupId()));
		}

		return result;

	}

	@PostMapping("/update")
//	@PutMapping
	public Map<String, Object> boardUpdate(
		InformationBoardVO informationVO
		, BindingResult errors
	) {

		Map<String, Object> result = new HashMap<>();
		result.put("target", informationVO);

		validator.validate(informationVO, errors, UpdateGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			informationService.informationBoardUpdate(informationVO);
			result.put("success", true);
			result.put("target", informationService.informationBoardList(informationVO.getGroupId()));
		}

		return result;

	}

	@DeleteMapping("{dataDetailBoardId}")
	public boolean boardDelete(
		@PathVariable String dataDetailBoardId
	) {

		InformationBoardVO information = new InformationBoardVO();
		information.setIfBoardId(dataDetailBoardId);
		information.setGroupId("GRP001");

		return informationService.informationBoardDelete(information);
	}

	@PostMapping("{ifBoardId}")
	public Map<String, Object> answAdd(
		@PathVariable String ifBoardId
		, InformationBoardAnswersVO informationAnswersVO
		, BindingResult errors
	) {

		Map<String, Object> result = new HashMap<>();
		result.put("target", informationAnswersVO);

		validator.validate(informationAnswersVO, errors, InsertGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			informationService.informationBoardAnswersInsert(informationAnswersVO);

			result.put("success", true);
			// result.put("target", informationService.informationBoardList(informationAnswersVO.getGroupId())); // 필요한가?????????????????????????????????????????????????????????????????????????
		}

		return result;

	}

	@PostMapping("answ/{ifBoardCmtId}")
	public Map<String, Object> answUpdate(
		@PathVariable String ifBoardCmtId
		, @RequestBody InformationBoardAnswersVO informationAnswersVO
		, BindingResult errors
	) {

		Map<String, Object> result = new HashMap<>();

		result.put("target", informationAnswersVO);

		validator.validate(informationAnswersVO, errors, UpdateGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			informationService.informationBoardAnswersUpdate(informationAnswersVO);

			result.put("success", true);
			result.put("target", informationService.informationBoardList(informationAnswersVO.getGroupId()));
		}

		return result;

	}

	@DeleteMapping ("answ/{ifBoardCmtId}")
	public Map<String, Object> answDelete(
			@PathVariable String ifBoardCmtId
			, @RequestBody InformationBoardAnswersVO informationAnswersVO
			, BindingResult errors
			) {

		Map<String, Object> result = new HashMap<>();

		result.put("target", informationAnswersVO);

		validator.validate(informationAnswersVO, errors, UpdateGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
					errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
					);
		} else {
			informationService.informationBoardAnswersDelete(informationAnswersVO);

			result.put("success", true);
			result.put("target", informationService.informationBoardList(informationAnswersVO.getGroupId()));
		}

		return result;

	}

	@PostMapping("like/{ifBoardId}")
	public Map<String, Object> likeAdd(
		@PathVariable String ifBoardId
		, @RequestBody InformationBoardLikeVO informationLikeVO
		, BindingResult errors
	) {

		Map<String, Object> result = new HashMap<>();
		result.put("target", informationLikeVO);

		validator.validate(informationLikeVO, errors, InsertGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			informationService.informationBoardLikeAdd(informationLikeVO);

			result.put("success", true);
		}

		return result;

	}

	@DeleteMapping("like/{ifBoardId}")
	public Map<String, Object> likeDel(
		@PathVariable String ifBoardId
		, @RequestBody InformationBoardLikeVO informationLikeVO
		, BindingResult errors
	) {

		Map<String, Object> result = new HashMap<>();

		result.put("target", informationLikeVO);

		validator.validate(informationLikeVO, errors, UpdateGroup.class);

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			informationService.informationBoardLikeDelete(informationLikeVO);

			result.put("success", true);
		}

		return result;

	}

	@PostMapping("like/chk/{ifBoardId}")
	public boolean likeSelect(
		@PathVariable String ifBoardId
		, @RequestBody InformationBoardLikeVO informationLikeVO
	) {

		boolean chk = informationService.informationBoardLikeYN(informationLikeVO);

		return chk;

	}

}
