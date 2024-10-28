package kr.or.nexus.sysadmin.subscribe.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface SASubMapper {

	public List<Integer> retrieveMemCountByRegDt(Map<String, String> params);

	public List<Integer> retrieveSubCountByRegDt(Map<String, String> params);

	public List<MemberManagementVO> retrieveMemDetailByRegDt(String goToControllerDate);

	public List<Map<String, Object>> retrieveSubDetailByRegDt(String goToControllerDate);

	public List<GroupManagementVO> retrieveGroupList();

	public Map<String, Object> retrieveTotalByGroup(String groupId);

	public List<Integer> retrievePaymentAmountList();

	public List<Map<String, Object>> retrievePaymentDetailList(Map<String, String> params);

	public List<Map<String, Object>> retrievePayTotalByGroup(String groupId);

	public String retrieveTotalPay();

}
