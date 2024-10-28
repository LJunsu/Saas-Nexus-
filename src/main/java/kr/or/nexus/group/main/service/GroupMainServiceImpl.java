package kr.or.nexus.group.main.service;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.commons.alarmAndLog.EventConstuctor.Event;
import kr.or.nexus.commons.alarmAndLog.EventConstuctor.NonLoginEvent;
import kr.or.nexus.commons.alarmAndLog.EventType.IssueEventType;
import kr.or.nexus.commons.def.mapper.GroupManagementDefaultMapper;
import kr.or.nexus.enumpkg.ServiceResult;
import kr.or.nexus.group.main.dao.GroupMainMapper;
import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.GroupManagementDefaultVO;

@Service
public class GroupMainServiceImpl implements GroupMainService {

	@Autowired
	private GroupManagementDefaultMapper dao;
	
	@Autowired
	private GroupMainMapper groupDao;
	
	@Autowired
	private MemberManagementService memberService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Value("/resources/images")
	private Resource folder;
	private File saveFolder;
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();

    private String generateRandomId() {
        StringBuilder builder = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }
	
	private void processGroupImage(GroupManagementVO group) {
//		if(1==1) {
//			throw new RuntimeException("트랜잭션 관리 여부를 확인하기 위한 강제 예외");
//		}
		
		// 상품 이미지 2진 데이터 저장.
		MultipartFile groupImage = group.getGroupImage();
		if(groupImage==null) return;
		File saveFile = new File(saveFolder, group.getGroupProfilePicture());
		try {
			groupImage.transferTo(saveFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public GroupManagementDefaultVO retrieveGroup(String groupId) {
		return dao.selectByPrimaryKey(groupId);
	}

	@Override
	public GroupManagementVO retrieveGroupById(String groupId) {
		return groupDao.selectGroupById(groupId);
	}

	@Override
	public void modifyGroup(GroupManagementVO group) {
		groupDao.updateGroup(group);
		if(group.getGroupImage() != null) {
			processGroupImage(group);			
		}
	}
	
	@Override
	public ServiceResult createGroup(GroupManagementVO group) {
		ServiceResult result = null;
		
		String groupId = generateRandomId();
        while (dao.selectByPrimaryKey(groupId) != null) {
            groupId = generateRandomId();
        }
        
        group.setGroupId(groupId);
        
        result = groupDao.insertGroup(group) > 0 ? ServiceResult.OK : ServiceResult.FAILED;
        
        return result;
	}

	@Override
	@Transactional
	public ServiceResult createGroupWithAdmin(GroupManagementVO group, MemberManagementVO member) {
		ServiceResult adminResult = memberService.createGroupAdmin(member);
		if (ServiceResult.OK.equals(adminResult)) {
			group.setMemberId(member.getMemberId());  // 생성된 그룹 ID를 멤버에 설정
            ServiceResult groupResult = createGroup(group);
            if (ServiceResult.OK.equals(groupResult)) {
            	GroupMemberVO gm = new GroupMemberVO();
            	gm.setGroupId(group.getGroupId());
            	gm.setMemberId(member.getMemberId());
            	ServiceResult groupAdminMember = memberService.createGroupAdminMember(gm);
            	if(ServiceResult.OK.equals(groupAdminMember)) {
            		NonLoginEvent<MemberManagementVO, GroupManagementVO> event =
                			new NonLoginEvent<MemberManagementVO, GroupManagementVO>(member.getMemberName(), member.getMemberId(),
                					member, group,IssueEventType.ISSUE_PRIORITY_CHANGED , 
                					"그룹 승인 요청", "url");
                	publisher.publishEvent(event);
                	
            		return ServiceResult.OK;
            	}else {
            		throw new RuntimeException("Failed to create group admin member");
            	}
            } else {
                // 관리자 생성 실패 시 트랜잭션 롤백
                throw new RuntimeException("Failed to create group admin");
            }
        } else {
            // 그룹 생성 실패 시
            return ServiceResult.FAILED;
        }
	}
	

	@Override
	public void updateGroupProfile(GroupManagementVO group) {
		groupDao.updateGroupProfile(group);
	}

}
