package kr.or.nexus.billing.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.def.SubscriptionPaymentDefaultVO;
import kr.or.nexus.vo.def.SubscriptionTypeDefaultVO;

@Mapper
public interface BillingMapper {

	 List<SubscriptionTypeDefaultVO> getSubscriptionByGroupId(String groupId);
	 List<SubscriptionPaymentDefaultVO> getPaymentByGroupId(String groupId);
	 Integer countGroupMembersByGroupId(String groupId);
	List<SubscriptionTypeDefaultVO> monthSubscription();
	List<SubscriptionTypeDefaultVO> yearSubscription();
	void insertPayment(SubscriptionPaymentDefaultVO payment);
	void deleteSub(String groupId);
	void updateUse(String groupId);

}
