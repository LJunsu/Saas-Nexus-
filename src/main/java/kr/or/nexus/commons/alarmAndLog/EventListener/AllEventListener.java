package kr.or.nexus.commons.alarmAndLog.EventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import kr.or.nexus.commons.alarm.AlarmHandler;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.EaEvent;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.LoginEvent;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.NonLoginEvent;
import kr.or.nexus.commons.mapper.AlarmMapper;
import kr.or.nexus.commons.service.SeokjunUtills;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;

@Slf4j
@Component
public class AllEventListener {

	@Resource(name = "webSocketSessionList")
	private List<WebSocketSession> webSocketSessionList;

	@Autowired
	private Map<String, MemberManagementVO> alarmSessionListAndMemId;

	@Autowired
	private AlarmMapper mapper;

	@Autowired
	private SeokjunUtills seokjun;
	
	@Autowired
	private Map<Integer,String> AdminSessionList;
	
	private String alarmId = null;
	
	int adChk = 1;
	

	@EventListener(classes = Event.class)
	public void handleIssueEvent(Event event) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();

		String senderId = event.getSenderId();
		String senderName = event.getSenderName();
		String groupIds = mapper.getGroupId(senderId);

			String str = "";
			webSocketSessionList.forEach((s)->{
				log.info("@@@@@@@@ test@@@@@@ {},{},{}", senderId,s,alarmSessionListAndMemId.get(s.getId()).getMemberId());
				});


			int cnt = 1;
				for (WebSocketSession singleSession : webSocketSessionList ) {
					//그룹관리자한테만 sendMessage 보내기
					List<MemberManagementVO> roledAdminList = mapper.getRoleAdminList();

					MemberManagementVO memberInfo = alarmSessionListAndMemId.get(singleSession.getId());

					String receiverId = memberInfo.getMemberId();

					log.info("senderId : {} ", senderId);
					log.info("Member ID:  {}", memberInfo.getMemberId());
					 Map<String, Object> messageData = new HashMap<>();
					 Map<String,Object> userInfoMap = new HashMap<String, Object>();
					 String htmlMessage ="";
					 String jsonString = "";
					if (event.getComment().equals("이슈 제목 수정")) {
						Object beforeIssueId = event.getFieldFromBeforeInfo("issueIdx");
						Object beforeProjectId = event.getFieldFromBeforeInfo("projectId");
						userInfoMap.put("issueIdx", beforeIssueId);
						userInfoMap.put("projectId", beforeProjectId);
						Object afterprojectIdId = event.getFieldFromAfterInfo("projectId");


						Object beforeTitle = event.getFieldFromBeforeInfo("issueTitle");
						Object afterTitle = event.getFieldFromAfterInfo("issueTitle");
						String data = beforeTitle.toString()+"\t" +"->"+"\t" + afterTitle.toString()+"으(로) 변경됨";

						htmlMessage = "<a href='/nexus/issueAdapter?url=/issue/" + groupIds + "/" + afterprojectIdId + "/issueMain&last=" + beforeIssueId + "' " +
					              "id='aTag' data-issue-idx='" + beforeIssueId + "' " +
					              "style='text-decoration: none; color: #333;'>" +
					              "<div style='font-size: 14px; color: #333;'>" +
					              "<strong style='color: #007BFF;'>[" + senderName + "]</strong> 님이 " +
					              "<strong>" + beforeTitle + "</strong> " +
					              "<span style='font-size: 20px; font-weight: bold; color: #FF5722;'>&rarr;</span> " +  // 화살표 크기와 색상을 강조
					              "<strong style='font-size: 16px; color: #007BFF;'>" + afterTitle + "</strong>" +
					              "로 제목을 변경하였습니다." +
					              "</div>" +
					              "</a>";



						
						String url = "/nexus/issueAdapter?url=/issue/"+ groupIds +"/"+ afterprojectIdId + "/issueMain&last=" + beforeIssueId;
						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("issueIdx", beforeIssueId);
						messageData.put("projectId", beforeProjectId);
						messageData.put("data", data);
						messageData.put("afterprojectIdId", afterprojectIdId);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);

						jsonString = objectMapper.writeValueAsString(messageData);
						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
							
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);


						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                		 
		                		 
		                		 
		                	 }
		                 });
						}

					}else if(event.getComment().equals("이슈 내용 수정")) {

						Object beforeIssueId = event.getFieldFromBeforeInfo("issueIdx");
						Object projectId = event.getFieldFromBeforeInfo("projectId");
						userInfoMap.put("issueIdx", beforeIssueId);
						userInfoMap.put("projectId", projectId);
						Object beforeissueContent = event.getFieldFromBeforeInfo("issueContent");
						Object afterprojectIdId = event.getFieldFromAfterInfo("projectId");
						Object afterissueContent = event.getFieldFromAfterInfo("issueContent");


						htmlMessage = "<div style='font-family: Arial, sans-serif; font-size: 14px; color: #333;'>" +
					              "<span style='font-weight: bold; font-size: 16px; color: #007BFF;'>[" + senderName + "]</span> 님이 " +
					              "<a href='/nexus/issueAdapter?url=/issue/" + groupIds + "/" + afterprojectIdId + "/issueMain&last=" + beforeIssueId + "' " +
					              "id='aTag' data-issue-idx='" + beforeIssueId + "' " +
					              "style='text-decoration: none; font-weight: bold; color: #333;'>" +
					              beforeissueContent + "</a>" +
					              "에서 " +
					              "<span style='font-weight: bold; font-size: 18px; color: #FF9800;'>&rarr;</span>" + // 화살표 크기 키움
					              "<span style='font-weight: bold; font-size: 18px; color: #007BFF;'>" + afterissueContent + "</span>" + // afterissueContent를 파란색으로
					              "(으)로 내용을 변경하였습니다." +
					              "</div>";




						String data = beforeissueContent.toString() +"에서"+"\t" +"->"+"\t"+ afterissueContent.toString()+"으(로) 변경됨";
						String url = "'/nexus/issueAdapter?url=/issue/"+ groupIds +"/"+ afterprojectIdId + "/issueMain&last=" + beforeIssueId + "'";

						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("projectId", projectId);
						messageData.put("data", data);
						messageData.put("issueIdx", beforeIssueId);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);

						jsonString = objectMapper.writeValueAsString(messageData);

						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);


						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                	 }
		                 });
						}




					}else if(event.getComment().equals("이슈 생성")) {

						Object aProjectId = event.getFieldFromAfterInfo("projectId");
						Object afterissueIdx = event.getFieldFromAfterInfo("issueIdx");
						userInfoMap.put("issueIdx", afterissueIdx);
						userInfoMap.put("projectId", aProjectId);
						Object afterissueOfficer = event.getFieldFromAfterInfo("issueOfficer");
						Object afterissueTitle = event.getFieldFromAfterInfo("issueTitle");

						htmlMessage = "<a href='/nexus/issueAdapter?url=/issue/" + groupIds +"/"+ aProjectId + "/issueMain&last=" + afterissueIdx
								+ "' id='aTag' data-issue-idx=" + afterissueIdx + ">" +"["+ senderName +"]"+ "님이"+"\t"
								+ afterissueTitle  + "이슈를 생성하였습니다.</a>";
						
						
						String data = afterissueTitle.toString() +"\t" +"을(를) 생성하였습니다";
						String url = "'/nexus/issueAdapter?url=/issue/"+ groupIds +"/"+ aProjectId + "/issueMain&last=" + afterissueIdx + "'";
						
						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("issueIdx", afterissueIdx);
						messageData.put("projectId", aProjectId);
						messageData.put("data", data);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);

						jsonString = objectMapper.writeValueAsString(messageData);
						
						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
						 
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);


						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                		 
		                		 
		                	 }
		                 });
						}


					}else if(event.getComment().equals("프로젝트 생성")) {
						Object memberIdObj = event.getFieldFromBeforeInfo("memberId");
						Object memberNameObj = event.getFieldFromBeforeInfo("memberName");

						Object aProjectId = event.getFieldFromAfterInfo("projectId");
						Object agroupId = event.getFieldFromAfterInfo("groupId");
						userInfoMap.put("projectId", aProjectId);
						userInfoMap.put("groupId", agroupId);
						Object afterProjectName = event.getFieldFromAfterInfo("projectName");
						Object projectProfileImg = event.getFieldFromAfterInfo("projectProfileImg");

						String str1 = "<a href='/nexus/" + agroupId + "/" + aProjectId + "/chMain' id='aTag' data-issue-idx=" + aProjectId + ">"+ "["+ senderName +"]"+ "님이"
						        + afterProjectName + " 프로젝트를 생성하였습니다.</a>";

						String data = afterProjectName.toString() + "로(으로) 프로젝트를 생성하였습니다";
						String url = "'/nexus/" + agroupId + "/" + aProjectId + "/chMain'";
						


						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("groupdId", agroupId);
						messageData.put("projectId", aProjectId);
						messageData.put("data", data);
						messageData.put("url", url);
						messageData.put("htmlMessage", str1);

						jsonString = objectMapper.writeValueAsString(messageData);
						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);

						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                	 }
		                 });
						}



						// memberIdObj가 List일 경우와 단일 객체일 경우를 각각 처리
						if (memberIdObj instanceof List) {
						    List<?> memberIds = (List<?>) memberIdObj; // List로 캐스팅
						    for (Object id : memberIds) {
						        log.info("받는 사람: {}, 프로젝트에 할당된 사람: {}", receiverId, id);
						        messageData.put("receiverId", id);

						         jsonString = objectMapper.writeValueAsString(messageData);
						        if (!senderId.equals(receiverId) && receiverId.equals(id)) {
						            log.info("여기 타 안타 빨리말해");
						            TextMessage message = new TextMessage(jsonString);
						            singleSession.sendMessage(message);
						            continue;
						        }
						    }
						} else {
						    log.info("받는 사람: {}, 프로젝트에 할당된 사람: {}", receiverId, memberIdObj);
						    	messageData.put("receiverId", memberIdObj);

				                 jsonString = objectMapper.writeValueAsString(messageData);
						    if (!senderId.equals(receiverId) &&receiverId.equals(memberIdObj)) {
						        log.info("여기 타 안타 빨리말해");
						        TextMessage message = new TextMessage(jsonString);
						        singleSession.sendMessage(message);
						        continue;
						    }
						}


					}else if(event.getComment().equals("임시 프로젝트 생성")) {
						Object aProjectId = event.getFieldFromAfterInfo("projectId");
						Object agroupId = event.getFieldFromAfterInfo("groupId");
						userInfoMap.put("projectId", aProjectId);
						userInfoMap.put("groupId", agroupId);
						Object afterProjectName = event.getFieldFromAfterInfo("projectName");
						Object projectProfileImg = event.getFieldFromAfterInfo("projectProfileImg");

						htmlMessage = "<a href='/nexus/"+agroupId+"/" + aProjectId + "/chMain' id='aTag' data-issue-idx=" + aProjectId + ">"+ "["+ senderName +"]"+ "님이"
									+ afterProjectName  + "프로젝트를 생성하였습니다.</a>";

						String data = afterProjectName.toString() +"가 생성되었습니다";
						String url = "'/nexus/" + agroupId + "/" + aProjectId + "/chMain'";

						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("projectId", aProjectId);
						messageData.put("groupdId", agroupId);
						messageData.put("data", data);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);


						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);

						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                	 }
		                 });
						}


						jsonString = objectMapper.writeValueAsString(messageData);

					}else if(event.getComment().equals("코드쉐어방 생성")) {

						Object codeShareId= event.getFieldFromAfterInfo("codeShareId");
						Object groupId = event.getFieldFromAfterInfo("groupId");
						Object projectId = event.getFieldFromAfterInfo("projectId");
						userInfoMap.put("projectId", projectId);
						userInfoMap.put("groupId", groupId);
						Object codeShareTitle = event.getFieldFromAfterInfo("codeShareTitle");


						htmlMessage = "<a href='/nexus/group/"+groupId+"/project/" + projectId + "/codeShare/"+codeShareId +"' id='aTag' data-issue-idx=" + codeShareId + ">"+ "["+ senderName +"]"+ "님이"
								+ codeShareTitle  + "로(으로) 코드쉐어방을 생성하였습니다.</a>";
						String url = "'/nexus/group/" + groupId + "/project/" + projectId + "/codeShare/" + codeShareId + "'";

						String data = codeShareTitle.toString() + "로(으로) 코드쉐어방을 생성하였습니다.";
						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("groupId", groupId);
						messageData.put("projectId", projectId);
						messageData.put("data", data);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);

						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);

						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                	 }
		                 });
						}



						jsonString = objectMapper.writeValueAsString(messageData);

					}else if(event.getComment().equals("그룹공지 작성")){
						Object boardId = event.getFieldFromAfterInfo("boardId");
						Object groupId = event.getFieldFromAfterInfo("groupId");
						userInfoMap.put("groupId", groupId);
						Object boardTitle = event.getFieldFromAfterInfo("boardTitle");

						htmlMessage = "<a href='/nexus/gNoticeAdapter?url=/group/"+groupId+"/noticeBoardUI&last="+boardId+"'  id='aTag' data-issue-idx=" + boardId + ">"+ "["+ senderName +"]"+ "님이"
								+ boardTitle  + "로(으로) 그룹공지글을 작성하였습니다.</a>";

						String data = boardTitle.toString()+"로(으로) 그룹공지을 작성하였습니다";
						String url = "'/nexus/gNoticeAdapter?url=/group/" + groupId + "/noticeBoardUI&last=" + boardId + "'";

						messageData.put("eventType", event.getComment());
						messageData.put("memberId", senderId);
						messageData.put("memberName", senderName);
						messageData.put("groupId", groupId);
						messageData.put("projectId", null);
						messageData.put("data", data);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);


						if(cnt>0) {
							alarmId = seokjun.NonGroupdIdAlarm(messageData);
						}
						log.info("@@@@@@@@@@@ALARM_ID@@@@@@@@@@@@ : {}",alarmId);

						List<MemberManagementVO> members = mapper.projectInfoList(userInfoMap);
						if(alarmId!=null && cnt >0) {
						cnt--;
		                 members.forEach((m)->{
		                	 if(!m.getMemberId().equals("MEM011")) {
		                		 log.info("해당 프로젝트에 참여한 멤버 전체출력 : {}",m.getMemberName());
		                		 mapper.ReceiverInsert(alarmId, m.getMemberId());
		                	 }
		                 });
						}


						jsonString = objectMapper.writeValueAsString(messageData);



					}
					if (!senderId.equals(receiverId) && !messageData.isEmpty()
						&&!receiverId.equals("MEM011")	 ) {
                        TextMessage message = new TextMessage(jsonString);
                        singleSession.sendMessage(message);
					} else {
					}
				} //for end(singleSession)


  	}
	
	
@EventListener(classes = NonLoginEvent.class)
public void NonLoginIssueEvent(NonLoginEvent event) throws IOException {
		

		ObjectMapper objectMapper = new ObjectMapper();

		String senderId = event.getSenderId();
		String senderName = event.getSenderName();
		String groupIds = mapper.getGroupId(senderId);

			String str = "";


			int cnt = 1;
			Map<String, Object> messageData = new HashMap<>();
			Map<String, Object> userInfoMap = new HashMap<String, Object>();
			if(event.getComment().equals("그룹 승인 요청")) {
				
				 String htmlMessage ="";
				 String jsonString = "";
				 
						Object groupId = event.getFieldFromAfterInfo("groupId");
						Object groudName = event.getFieldFromAfterInfo("groupName");
						Object memberId = event.getFieldFromBeforeInfo("memberId"); // 신청한그룹 ID(랜덤)
						Object memberName = event.getFieldFromBeforeInfo("memberName"); // 신청한 그룹 이름
						String data = memberName + " 님이 " + groudName.toString()+"의 그룹 승인을 요청하였습니다";
						
						String url = "'/nexus/saGroup/"+groupId+"'";
						
						htmlMessage = "<a href='/nexus/saGroup/" + groupId + "' " +
					              "style='text-decoration: none; color: #333;'>" +
					              "<div style='font-size: 14px; color: #333;'>" +
					              "<strong style='font-size: 16px; color: #007BFF;'>[" + memberName + "]</strong> 님이 " + // 요청자 이름 강조
					              "<strong style='color: #007BFF;'>" + groudName + "</strong> 의 그룹 승인을 요청하였습니다." +
					              "</div>" +
					              "</a>";

						
						
						messageData.put("eventType", event.getComment());
						messageData.put("memberId", memberId);
						messageData.put("memberName", memberName);
						messageData.put("data", data);
						messageData.put("url", url);
						messageData.put("htmlMessage", htmlMessage);
				
				
				
				if(cnt>0) {
					alarmId = seokjun.NonGroupdIdAlarm(messageData);
				}
				
				List<MemberManagementVO> roledAdminList = mapper.getRoleAdminList();
				roledAdminList.forEach(Admin -> {
				    // DB에 저장
				    mapper.ReceiverInsert(alarmId, Admin.getMemberId());
				});
				
				
				
				
				for (WebSocketSession singleSession : webSocketSessionList) {
					MemberManagementVO memberInfo = alarmSessionListAndMemId.get(singleSession.getId());

					String receiverId = memberInfo.getMemberId();


							jsonString = objectMapper.writeValueAsString(messageData);
							TextMessage message = new TextMessage(jsonString);
						
							
							if(alarmId!=null && cnt >0) {
							cnt--;
							
							roledAdminList.forEach(Admin -> {
							    // DB에 저장
									System.out.println("설마여기까지?");
							    // 세션이 있는지 확인하고, 해당 Admin의 세션에만 메시지 전송
							    if (!receiverId.equals(senderId) && !messageData.isEmpty()) {
							        // alarmSessionListAndMemId에 Admin의 세션이 있는지 확인
							    	System.out.println("어디까지 탄거야1");
							        for (Map.Entry<String, MemberManagementVO> entry : alarmSessionListAndMemId.entrySet()) {
							            String sessionId = entry.getKey();
							            MemberManagementVO sessionUser = entry.getValue();
							             
							            if (sessionUser.getMemberId().equals(Admin.getMemberId())) {
							                // 세션이 Admin.getMemberId()와 동일한 사용자일 경우 메시지 전송
							                WebSocketSession adminSession = webSocketSessionList.stream()
							                        .filter(session -> session.getId().equals(sessionId))
							                        .findFirst().orElse(null);
							                System.out.println("어디까지 탄거야2");
							                if (adminSession != null) {
							                    try {
							                        adChk++;
							                        adminSession.sendMessage(message);
							                        log.info("메시지를 세션으로 전송: {}", Admin.getMemberId());
							                        System.out.println("어디까지 탄거야3");
							                    } catch (IOException e) {
							                        log.error("메시지 전송 중 오류 발생: {}", e.getMessage());
							                        System.out.println("어디까지 탄거야4");
							                    }
							                }
							            }
							        }
							    }
							});

						}
							//그룹관리자한테만 sendMessage 보내기

							
					}
					 
					 
					 
					 
					 
					 
					 
					 
				}
		
		
	}


/**
 * @param event
 * @throws IOException
 * 
 * 로그인 전용 로그 세션전송 x
 */
@EventListener(classes = LoginEvent.class)
public void loginEvent(LoginEvent event) throws IOException {
	String loginMemberId = event.getMemId();
	String loginMemberName = event.getMemName();
	String eventType = event.getComment();
	Map<String, Object> logMap = new HashMap<String, Object>();
	
	logMap.put("memberId",loginMemberId);
	logMap.put("memberName",loginMemberName);
	logMap.put("eventType",eventType);
	
	 String alarmId = seokjun.LogLogin(logMap);
	 mapper.ReceiverInsert(alarmId, loginMemberId);
	 
	
	 
	 
	 
}


/**
 * @param event
 * 전자결재 승인 요청
 * @throws IOException 
 */

@EventListener(classes = EaEvent.class)
public void eaEvent(EaEvent event) throws IOException {
	ObjectMapper objectMapper = new ObjectMapper();
	
	
	String groupId = event.getGroupId();
	String senderId = event.getSenderId();
	String senderName = event.getSenderName();
	String receiverId = event.getReceiverId();
	String projectName = event.getProjectName();
	String creationReason = event.getCreationReason();
	String comment = event.getComment();
	String documentId = event.getEaId();
	
	String data =  projectName +" 로(으로) 프로젝트 기획안 결재를 요청하였습니다 ";
	String url =  "/nexus/eaAdapter?url=/"+ groupId + "/ea&last="+documentId;
	String htmlMessage = "<a href='/nexus/eaAdapter?url=/" + groupId + "/ea&last=" + documentId + "' " +
            "style='text-decoration: none; color: #333;'>" +
            "<div style='font-size: 14px; color: #333;'>" +
            "<strong style='font-size: 16px; color: #007BFF;'>[" + senderName + "]</strong> 님이 " + // 요청자 이름 강조
            "<strong style='color: #007BFF;'>" + projectName + "</strong> 로(으로) " +
            "<span style='font-size: 16px; font-weight: bold; color: #FF5722;'>프로젝트 기획안</span> " +
            "결재를 요청하였습니다." +
            "</div>" +
            "</a>";


	Map<String,Object> eaEventMap = new HashMap<String, Object>();
	eaEventMap.put("eventType",comment);
	eaEventMap.put("memberId", senderId);
	eaEventMap.put("memberName", senderName);
	eaEventMap.put("groupId", groupId);
	eaEventMap.put("data", data);
	eaEventMap.put("url", url);
	eaEventMap.put("htmlMessage", htmlMessage);
	
	String jsonString = objectMapper.writeValueAsString(eaEventMap);
	
	String alarmId = seokjun.eaEvent(eaEventMap);
	
	List<GroupMemberVO> groupdMemberInfo  = mapper.getGroupdMemberList(groupId);
	 
	 groupdMemberInfo.forEach(g->{
		if(!g.getMemberId().equals("MEM011") && g.getMemberId().equals(receiverId)&&
				!g.getMemberId().equals(senderId)) {
			mapper.ReceiverInsert(alarmId,receiverId);
		}
	 });
	 for (WebSocketSession singleSession : webSocketSessionList) {
			MemberManagementVO memberInfo = alarmSessionListAndMemId.get(singleSession.getId());

			String receiverIds = memberInfo.getMemberId();
		 
			if (!senderId.equals(receiverIds) && !eaEventMap.isEmpty()
					&&!receiverId.equals("MEM011")	 ) {
                    TextMessage message = new TextMessage(jsonString);
                    singleSession.sendMessage(message);
				} else {
					
				}
		 
		 
		 
	 }
	 
	 
	
	
}

}