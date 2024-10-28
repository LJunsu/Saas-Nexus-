package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.SubscriptionTypeDefaultVO;

@Mapper
public interface SubscriptionTypeDefaultMapper {
    int deleteByPrimaryKey(String subscriptionId);

    int insert(SubscriptionTypeDefaultVO row);

    SubscriptionTypeDefaultVO selectByPrimaryKey(String subscriptionId);

    List<SubscriptionTypeDefaultVO> selectAll();

    int updateByPrimaryKey(SubscriptionTypeDefaultVO row);
}