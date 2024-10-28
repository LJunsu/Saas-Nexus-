package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.SubscriptionPaymentDefaultVO;

@Mapper
public interface SubscriptionPaymentDefaultMapper {
    int deleteByPrimaryKey(String paymentId);

    int insert(SubscriptionPaymentDefaultVO row);

    SubscriptionPaymentDefaultVO selectByPrimaryKey(String paymentId);

    List<SubscriptionPaymentDefaultVO> selectAll();

    int updateByPrimaryKey(SubscriptionPaymentDefaultVO row);
}