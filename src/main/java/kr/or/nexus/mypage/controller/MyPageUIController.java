package kr.or.nexus.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.commons.alarmAndLog.EventType.IssueEventType;
import kr.or.nexus.commons.mapper.AlarmMapper;
import kr.or.nexus.commons.service.SeokjunUtills;
import kr.or.nexus.commons.userSessionManager.UserSessionManager;
import kr.or.nexus.member.main.service.MemberMainService;
import kr.or.nexus.mypage.service.MyPageService;
import kr.or.nexus.sysadmin.member.service.SAMemberService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectVO;
import kr.or.nexus.vo.def.ProjectDefaultVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/Mypage.do")
public class MyPageUIController {


	@Autowired
	private SAMemberService service;
	@Autowired
	private MemberMainService projectService;
	@Autowired
	private SeokjunUtills seokjun;
	@Autowired
	private UserSessionManager userSessionManager;
	@Autowired
	private AlarmMapper mapper;

    @GetMapping()
    public String mypage(Authentication authentication, Model model) {

    	CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
        MemberManagementVO realUser = wrapper.getRealUser();
        String memberId = realUser.getMemberId();
        String groupId = seokjun.getGroupdId(memberId);
        Map<String,String> map = new HashMap<String, String>();
        Map<String, Boolean> onlineUsers = userSessionManager.getOnlineUsers();
        map.put("groupId", groupId);
        map.put("memberId", memberId);
        
        MemberManagementVO member = service.retrieveMember(memberId);
        List<Map<String, Object>> projectList = seokjun.getProjectListByGroupdId(groupId);
        List groupMemberList = seokjun.getGroupMemberList(groupId);
        MemberManagementVO getUserInfo = mapper.getUserInfo(memberId);
        String sIssueCount = mapper.getIssueCount(groupId);
        String projectCount = mapper.getProjectCount(groupId);
        String groupMemberCount = mapper.groupMemberCount(groupId);
        model.addAttribute("memberInfo",member);
        model.addAttribute("groupId",groupId);
        model.addAttribute("projectList",projectList);
        model.addAttribute("groupMemberList",groupMemberList);
        model.addAttribute("onlineUsers", onlineUsers);
        model.addAttribute("userInfo", getUserInfo);
        model.addAttribute("sIssueCount", sIssueCount);
        model.addAttribute("projectCount", projectCount);
        model.addAttribute("groupMemberCount", groupMemberCount);
        
        



        return "tiles:sysAdmin/mypageExample";
    }









}
