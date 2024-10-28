package kr.or.nexus.vo;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class SubscriptionPaymentVO {
	private String paymentId;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime paymentDate;
	private Integer paymentAmount;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime refundDate;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime refundApprovalDate;
	private String refundApproval;
	private String groupId;
	private String subscriptionId;
}
