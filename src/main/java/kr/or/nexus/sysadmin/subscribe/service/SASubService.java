package kr.or.nexus.sysadmin.subscribe.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

/**
 * @author PC-22
 *
 */
public interface SASubService {

	/**
	 * 달별, 날짜별 신규 가입 회원수 조회
	 * @return
	 */
	public List<Integer> retrieveMemCountByRegDt(Map<String, String> params);

	/**
	 * 달별, 날짜별 신규 구독 회원수 조회
	 * @param params
	 * @return
	 */
	public List<Integer> retrieveSubCountByRegDt(Map<String, String> params);

	/**
	 * 날짜별 신규 가입 회원 디테일 조회
	 * @param goToControllerDate
	 * @return
	 */
	public List<MemberManagementVO> retrieveMemDetailByRegDt(String goToControllerDate);

	/**
	 * 날짜별 신규 구독 회원 디테일 조회
	 * @param goToControllerDate
	 * @return
	 */
	public List<Map<String, Object>> retrieveSubDetailByRegDt(String goToControllerDate);

	/**
	 * 그룹 리스트 조회
	 * @return
	 */
	public List<GroupManagementVO> retrieveGroupList();

	/**
	 * 그룹별 total 조회
	 * @param groupId
	 * @return
	 */
	public Map<String, Object> retrieveTotalByGroup(String groupId);

	/**
	 * 월별 누적 구독 수익액 조회
	 * @return
	 */
	public List<Integer> retrievePaymentAmountList();

	/**
	 * 월별 구독 결제 상세 조회
	 * @param params 
	 * @return
	 */
	public List<Map<String, Object>> retrievePaymentDetailList(Map<String, String> params);

	/**
	 * 그룹별 구독 유지율
	 * @param groupId
	 * @return
	 */
	public List<Map<String, Object>> retrievePayTotalByGroup(String groupId);

	/**
	 * 총 서비스 수익
	 * @return
	 */
	public String retrieveTotalPay();

}