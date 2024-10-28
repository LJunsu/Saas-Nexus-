package kr.or.nexus.codeShare.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.tiles.autotag.core.runtime.annotation.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.WebSocketSession;

import kr.or.nexus.codeShare.service.CodeShareService;
import kr.or.nexus.codeShare.vo.codeShareVO;
import kr.or.nexus.timeline.service.TimeLineService;
import kr.or.nexus.vo.ProjectMemberVO;

@Controller
@RequestMapping
public class CodeShareController {

	// !!!!!
	@Autowired
	@Qualifier("userSessionsIdMap")
    private Map<String, List<String>> userSessionsIdMap;
	@Autowired
	@Qualifier("userSessionsNameMap")
    private Map<String, List<String>> userSessionsNameMap;
	// !!!!!


	@Autowired
	private CodeShareService codeShareService;

	@GetMapping("group/{groupId}/project/{projectId}/codeShare/{codeShareId}")
	public String codeShareUI(
		@PathVariable String groupId
		, @PathVariable String projectId
		, @PathVariable String codeShareId
		, Model model
		, String language
	) {

		codeShareVO vo = new codeShareVO();
		vo.setGroupId(groupId);
		vo.setProjectId(projectId);
		vo.setCodeShareId(codeShareId);
		codeShareVO codeShareDetail = codeShareService.codeShareDetail(vo);

		// !!!!!
		List<String> userSessionsIdList = userSessionsIdMap.get(codeShareId);
		List<String> userSessionsNameList = userSessionsNameMap.get(codeShareId);
		// !!!!!


		model.addAttribute("groupId", groupId);
		model.addAttribute("projectId", projectId);
		model.addAttribute("codeShareId", codeShareId);
		model.addAttribute("userSessionsIdList", userSessionsIdList);
		model.addAttribute("userSessionsNameList", userSessionsNameList);
		model.addAttribute("codeShareDetail", codeShareDetail);
		model.addAttribute("language", codeShareDetail.getLanguage());

		return "tiles2:codeShare/codeShare";

	}

	@GetMapping("group/{groupId}/project/{projectId}/codeShareList")
	public String codeShareList(
		@PathVariable String groupId
		, @PathVariable String projectId
		, Model model
		, @RequestParam (required = false, defaultValue = "null") String search
	) {

		codeShareVO vo = new codeShareVO();
		vo.setGroupId(groupId);
		vo.setProjectId(projectId);
		vo.setSearch(search);
		List<codeShareVO> codeShareList = codeShareService.codeShareList(vo);

		model.addAttribute("groupId", groupId);
		model.addAttribute("projectId", projectId);
		model.addAttribute("codeShareList", codeShareList);

		return "tiles2:codeShare/codeShareList";

	}

}
