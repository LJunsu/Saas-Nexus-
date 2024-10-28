package kr.or.nexus.sysadmin.subscribe.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.sysadmin.member.dao.SAMemberMapper;
import kr.or.nexus.sysadmin.subscribe.dao.SASubMapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

@Service
public class SASubServiceImpl implements SASubService {
	
	@Autowired
	private SASubMapper dao;

	@Override
	public List<Integer> retrieveMemCountByRegDt(Map<String, String> params) {
		return dao.retrieveMemCountByRegDt(params);
	}

	@Override
	public List<Integer> retrieveSubCountByRegDt(Map<String, String> params) {
		return dao.retrieveSubCountByRegDt(params);
	}

	@Override
	public List<MemberManagementVO> retrieveMemDetailByRegDt(String goToControllerDate) {
		return dao.retrieveMemDetailByRegDt(goToControllerDate);
	}

	@Override
	public List<Map<String, Object>> retrieveSubDetailByRegDt(String goToControllerDate) {
		return dao.retrieveSubDetailByRegDt(goToControllerDate);
	}

	@Override
	public List<GroupManagementVO> retrieveGroupList() {
		return dao.retrieveGroupList();
	}

	@Override
	public Map<String, Object> retrieveTotalByGroup(String groupId) {
		return dao.retrieveTotalByGroup(groupId);
	}

	@Override
	public List<Integer> retrievePaymentAmountList() {
		return dao.retrievePaymentAmountList();
	}

	@Override
	public List<Map<String, Object>> retrievePaymentDetailList(Map<String, String> params) {
		return dao.retrievePaymentDetailList(params);
	}

	@Override
	public List<Map<String, Object>> retrievePayTotalByGroup(String groupId) {
		return dao.retrievePayTotalByGroup(groupId);
	}

	@Override
	public String retrieveTotalPay() {
		return dao.retrieveTotalPay();
	}

}
