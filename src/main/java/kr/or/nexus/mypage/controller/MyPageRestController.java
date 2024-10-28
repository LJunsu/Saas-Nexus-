package kr.or.nexus.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.commons.mapper.AlarmMapper;
import kr.or.nexus.commons.service.SeokjunUtills;
import kr.or.nexus.commons.vo.AlarmVO;
import kr.or.nexus.member.main.service.MemberMainService;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/Mypage/rest")
public class MyPageRestController {
	@Autowired
	private SeokjunUtills service;
	
	@Autowired
	private MemberMainService memService;
	
	@Autowired
	private AlarmMapper mapper;
	
	@GetMapping("getProjectList")
	public Map<String,Object> getProjectList(String memberId, HttpSession session){
		log.info(memberId);
		Map<String,Object> map = new HashMap<String, Object>();
//		List<MemberManagementVO> projectList= service.getgetProjectList(memberId);
		
		
		//도연추가 START
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
		String groupId = user.getGroupMemberVO().getGroupId();
        Map<String, String> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("memberId", memberId);
		List<Map<String, Object>> projectList = memService.retrieveProjectList(params);		
		//도연추가 END
		
		
//		log.info("프로젝트 리스트: {}",projectList);
		map.put("projectList", projectList);
		return map;
	}
	
	
	@PutMapping("updateInfo")
	public Map<String,Object> getProjectList(@RequestBody MemberManagementVO member){
		Map<String,Object> map = new HashMap<String, Object>();
		 int cnt = service.updateUserInfo(member);
		 if(cnt>0) {
			 map.put("success", "success");
			 map.put("cnt",cnt);
		 }
		return map;
	}
	
	
	
	@GetMapping("userLoginLog")
	public Map<String,Object> getLoginLog(String memberId){
		Map<String,Object> map = new HashMap<String, Object>();
		List<AlarmVO> alarmLog = mapper.getUserLog(memberId);
		map.put("log", alarmLog);
		
		
		return map;
	}
	
	
	
	
	
	
}
