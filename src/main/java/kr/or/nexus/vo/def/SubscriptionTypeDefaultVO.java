package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class SubscriptionTypeDefaultVO implements Serializable {
    private String subscriptionId;

    private BigDecimal subscriptionPrice;

    private Long subscriptionPeriod;

    private String subscriptionName;

    private static final long serialVersionUID = 1L;
}