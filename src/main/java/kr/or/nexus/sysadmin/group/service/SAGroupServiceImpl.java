package kr.or.nexus.sysadmin.group.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.sysadmin.group.dao.SAGroupMapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupRefusalVO;

@Service
public class SAGroupServiceImpl implements SAGroupService {
	
	@Autowired
	private SAGroupMapper dao;

	@Override
	public List<GroupManagementVO> retrieveGroupList(PaginationInfo paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		return dao.retrieveGroupList(paging);
	}

	@Override
	public GroupManagementVO retrieveGroup(String groupId) {
		return dao.retrieveGroup(groupId);
	}

	@Override
	public void modifyGroupApproval(GroupManagementVO groupManagementVO) {
		dao.modifyGroupApproval(groupManagementVO);
	}

	@Override
	public void insertGroupRefusal(GroupRefusalVO groupRefusalVO) {
		dao.insertGroupRefusal(groupRefusalVO);
	}
}
