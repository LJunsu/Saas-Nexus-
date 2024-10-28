package kr.or.nexus.billing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDTO {

	private String paymentKey;
    private String orderId;
    private Double amount;
    private String groupId;
    private String subscriptionId;

    // Getters and Setters
}
