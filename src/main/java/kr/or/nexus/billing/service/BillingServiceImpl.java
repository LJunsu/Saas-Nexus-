package kr.or.nexus.billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.billing.dao.BillingMapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.def.SubscriptionPaymentDefaultVO;
import kr.or.nexus.vo.def.SubscriptionTypeDefaultVO;

@Service
public class BillingServiceImpl implements BillingService {

	@Autowired
	private BillingMapper mapper;

	@Override
	public List<SubscriptionTypeDefaultVO> getSubscriptionByGroupId(String groupId) {

		return mapper.getSubscriptionByGroupId(groupId);
	}

	@Override
	public List<SubscriptionPaymentDefaultVO> getPaymentByGroupId(String groupId) {
		return mapper.getPaymentByGroupId(groupId);
	}

	@Override
	public Integer getcountGroupMembersByGroupId(String groupId) {

		return mapper.countGroupMembersByGroupId(groupId);
	}

	@Override
	public List<SubscriptionTypeDefaultVO> getMonthSubscription() {
		// TODO Auto-generated method stub
		return mapper.monthSubscription();
	}

	@Override
	public List<SubscriptionTypeDefaultVO> getYearSubscription() {
		// TODO Auto-generated method stub
		return mapper.yearSubscription();
	}

	@Override
	public void savePayment(SubscriptionPaymentDefaultVO payment) {
		mapper.insertPayment(payment);

	}

	@Override
	public void cancleSub(String groupId) {
		mapper.deleteSub(groupId);

	}

	@Override
	public void updateUseByGroupId(String groupId) {
		mapper.updateUse(groupId);

	}

}
