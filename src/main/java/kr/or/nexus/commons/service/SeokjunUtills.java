package kr.or.nexus.commons.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.mapper.AlarmMapper;
import kr.or.nexus.commons.vo.AlarmReceiverVO;
import kr.or.nexus.vo.MemberManagementVO;

@Service
public class SeokjunUtills {

	@Autowired
	private AlarmMapper mapper;


	public String NonGroupdIdAlarm(Map<String, Object> map) {

		mapper.NonGroupdIdAlarm(map);

		return (String) map.get("alarmId");
	}

	public String YGroupdIdAlarm(Map<String, Object> map) {

		mapper.YGroupdIdAlarm(map);

		return (String) map.get("alarmId");
	}
	
	public String LogLogin(Map<String, Object> map) {
		
		mapper.LoginLog(map);
		
		return (String) map.get("alarmId");
	}
	
	public String eaEvent(Map<String, Object> map) {
		
		mapper.eaEvent(map);
		
		
		return (String) map.get("alarmId");
		
	}
	
	
	public List<AlarmReceiverVO> NonSessionAlarmList(String memberID){
		
		return  mapper.NonSessionAlarmList(memberID);
	}
	
	
	public int NonSessionAlarmListCnt(String memberId) {
		
		return mapper.NonSessionAlarmListCnt(memberId);
	}
	
	
	public int alarmChkUpdate(int alarmNo) {
		
		return mapper.alarmChkUpdate(alarmNo);
	}
	
	public List<AlarmReceiverVO> NonChkAlarmList(String memberID){
		
		return mapper.NonChkAlarmList(memberID);
	}
	
	
	public List<AlarmReceiverVO> ChkAlarmList(String memberID){
		
		return mapper.ChkAlarmList(memberID);
	}

	public String getGroupdId(String memberId) {
		return mapper.getGroupdId(memberId);
	}
	
	public List<Map<String, Object>> getProjectListByGroupdId(String groupId){
		
		return mapper.getProjectListByGroupdId(groupId);
	}
	
	
	public List<MemberManagementVO> getGroupMemberList(String groupId){
		return mapper.getGroupMemberList(groupId);
	}
	
	
	public List<MemberManagementVO> getgetProjectList(String memberId){
		
		return mapper.getgetProjectList(memberId);
	}
	
	public int updateUserInfo(MemberManagementVO member) {
		return mapper.updateUserInfo(member);
	}
	
	



}
