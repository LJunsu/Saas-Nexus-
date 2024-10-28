package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SubscriptionPaymentDefaultVO implements Serializable {
    private String paymentId;

    private LocalDate paymentDate;

    private Double paymentAmount;

    private LocalDateTime refundDate;

    private LocalDateTime refundApprovalDate;

    private String refundApproval;

    private String groupId;

    private String subscriptionId;

    private static final long serialVersionUID = 1L;
}