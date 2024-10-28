package kr.or.nexus.commons.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.commons.vo.AlarmReceiverVO;
import kr.or.nexus.commons.vo.AlarmVO;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface AlarmMapper {

	List<MemberManagementVO> projectInfoList(Map<String, Object> map);
	int NonGroupdIdAlarm(Map<String, Object> map);
	int YGroupdIdAlarm(Map<String, Object> map);
	int ReceiverInsert(@Param("alarmId") String alarmId ,@Param("memberId") String memberId);
	List<AlarmReceiverVO> NonSessionAlarmList(@Param("memberId") String memberId);
	int NonSessionAlarmListCnt(@Param("memberId") String memberId);
	int alarmChkUpdate(@Param("alarmNo") int alarmNo);
	List<AlarmReceiverVO> NonChkAlarmList(@Param("memberId") String memberId);
	List<AlarmReceiverVO> ChkAlarmList(@Param("memberId") String memberId);
	String getGroupdId (@Param("memberId") String memberId);
	List<Map<String,Object>> getProjectListByGroupdId(@Param("groupId") String groupId);
	List<MemberManagementVO> getGroupMemberList(@Param("groupId") String groupId);
	List<MemberManagementVO> getgetProjectList(@Param("memberId")String memberId);
	List<AlarmVO> alarmIssueList(String projectId);
	String getGroupId(@Param("memberId") String memberId);
	List<MemberManagementVO> getRoleAdminList();
	int LoginLog(Map<String, Object> map);
	MemberManagementVO getUserInfo(@Param("memberId") String memberId);
	int updateUserInfo(MemberManagementVO member);
	List<AlarmVO> getUserLog(@Param("memberId") String memberId);
	String getIssueCount(@Param("groupId") String groupId);
	String getProjectCount(@Param("groupId") String groupId);
	String groupMemberCount(@Param("groupId") String groupId);
	int eaEvent(Map<String, Object> map);
	List<GroupMemberVO> getGroupdMemberList(@Param("groupId") String groupId);
	

}
