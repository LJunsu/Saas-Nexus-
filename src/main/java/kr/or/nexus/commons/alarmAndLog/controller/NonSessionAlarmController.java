package kr.or.nexus.commons.alarmAndLog.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.commons.service.SeokjunUtills;
import kr.or.nexus.commons.vo.AlarmReceiverVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("Nonsession/Alarm")
public class NonSessionAlarmController {

	@Autowired
	private SeokjunUtills service;
	
	
	@GetMapping("all")
	public Map<String,Object> AlarmList(){
		Map<String,Object> map = new HashMap<String, Object>();
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		CustomUserVOWrapper princiapl = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO memberInfo = princiapl.getRealUser();
		String memberId = memberInfo.getMemberId();
		List<AlarmReceiverVO> list = service.NonSessionAlarmList(memberId);
		int listCnt = service.NonSessionAlarmListCnt(memberId);
		map.put("success", "success");	
		map.put("cnt", listCnt);		
		map.put("AlarmList", list);
		log.info("@@AlarmList@@ {} : ",list.toString() );
		
		
		return map;
	}
	
	
	@GetMapping("{alarmNo}")
	public Map<String,Object> alarmChkUpdate(@PathVariable int alarmNo){
		Map<String,Object> map = new HashMap<String, Object>();
		
		int cnt = service.alarmChkUpdate(alarmNo);
		map.put("success","success");
		map.put("cnt", cnt);
		
		
		
		return map;
	}
	
	
	
	
	@GetMapping("non")
	public Map<String,Object> NonReadAlarmList(){
		Map<String,Object> map = new HashMap<String, Object>();
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		CustomUserVOWrapper princiapl = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO memberInfo = princiapl.getRealUser();
		String memberId = memberInfo.getMemberId();
		List<AlarmReceiverVO> list = service.NonChkAlarmList(memberId);
		int listCnt = service.NonSessionAlarmListCnt(memberId);
		map.put("success", "success");	
		map.put("cnt", listCnt);		
		map.put("AlarmList", list);
		log.info("@@AlarmList@@ {} : ",list.toString() );
		
		
		return map;
	}
	
	
	
	
	@GetMapping("read")
	public Map<String,Object> ChkAlarmList(){
		Map<String,Object> map = new HashMap<String, Object>();
		
		Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
		CustomUserVOWrapper princiapl = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO memberInfo = princiapl.getRealUser();
		String memberId = memberInfo.getMemberId();
		List<AlarmReceiverVO> list = service.ChkAlarmList(memberId);
		
		map.put("success", "success");	
		map.put("AlarmList", list);
		log.info("@@AlarmList@@ {} : ",list.toString() );
		
		
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
