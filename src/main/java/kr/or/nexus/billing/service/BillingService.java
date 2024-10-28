package kr.or.nexus.billing.service;

import java.util.List;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.def.SubscriptionPaymentDefaultVO;
import kr.or.nexus.vo.def.SubscriptionTypeDefaultVO;

public interface BillingService {

	List<SubscriptionTypeDefaultVO> getSubscriptionByGroupId(String groupId);

	List<SubscriptionPaymentDefaultVO> getPaymentByGroupId(String groupId);

	Integer getcountGroupMembersByGroupId(String groupId);

	List<SubscriptionTypeDefaultVO> getMonthSubscription();

	List<SubscriptionTypeDefaultVO> getYearSubscription();

	void savePayment(SubscriptionPaymentDefaultVO payment);

	void cancleSub(String groupId);

	void updateUseByGroupId(String groupId);

}
