package kr.or.nexus.billing.controller;

import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.or.nexus.billing.dto.PaymentRequestDTO;
import kr.or.nexus.billing.service.BillingService;
import kr.or.nexus.vo.def.SubscriptionPaymentDefaultVO;
import kr.or.nexus.vo.def.SubscriptionTypeDefaultVO;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@Controller
@RequestMapping("/group/{groupId}/billing")
public class BillingController {

//	 @Autowired
//	 private RestTemplate restTemplate;
//

	@Autowired
	private BillingService service;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping("/subscription")
	public String subscriptionInformation(@PathVariable String groupId, Model model) {
	    List<SubscriptionTypeDefaultVO> subscriptions = service.getSubscriptionByGroupId(groupId);
	    List<SubscriptionPaymentDefaultVO> payments = service.getPaymentByGroupId(groupId);
	    Integer countGroupMembers = service.getcountGroupMembersByGroupId(groupId);
	    List<SubscriptionTypeDefaultVO> monthsubscription = service.getMonthSubscription();
	    List<SubscriptionTypeDefaultVO> yearubscription = service.getYearSubscription();

	    model.addAttribute("monthsubscription", monthsubscription);
	    model.addAttribute("yearubscription", yearubscription);
	    model.addAttribute("groupId", groupId);
	    model.addAttribute("countGroupMembers", countGroupMembers);

	    System.out.println(countGroupMembers);

	    // Handle cases where no subscription or payment data is available
	    SubscriptionTypeDefaultVO subscription = subscriptions.isEmpty() ? null : subscriptions.get(0);
	    model.addAttribute("subscription", subscription);

	    if (payments.isEmpty() || subscription == null) {
	        model.addAttribute("payment", null);
	        model.addAttribute("remainingDays", "구독 정보 없음");
	    } else {
	        SubscriptionPaymentDefaultVO payment = payments.get(0);
	        LocalDate paymentDate = payment.getPaymentDate();

	        Long subscriptionPeriod = subscription.getSubscriptionPeriod();
	        LocalDate subscriptionEndDate = paymentDate.plusDays(subscriptionPeriod);

	        LocalDate today = LocalDate.now();
	        long remainingDays = ChronoUnit.DAYS.between(today, subscriptionEndDate);

	        // 남은 일수가 0 이하일 경우, 구독 만료로 처리
	        if (remainingDays <= 0) {
	            model.addAttribute("remainingDays", "구독 정보없음");
	        } else {
	            model.addAttribute("remainingDays", remainingDays);
	        }

	        model.addAttribute("payment", payment);
	    }

	    return "tiles:groupMember/billing";
	}


	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody, @PathVariable String groupId)
			throws Exception {

		JSONParser parser = new JSONParser();
		String orderId;
		String amount;
		String paymentKey;

		try {
			JSONObject requestData = (JSONObject) parser.parse(jsonBody);
			paymentKey = (String) requestData.get("paymentKey");
			orderId = (String) requestData.get("orderId");
			amount = (String) requestData.get("amount");
		} catch (ParseException e) {
			JSONObject errorResponse = new JSONObject();
			errorResponse.put("message", "Invalid request data");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		// 결제 승인 요청 데이터 설정
		JSONObject obj = new JSONObject();
		obj.put("orderId", orderId);
		obj.put("amount", amount);
		obj.put("paymentKey", paymentKey);

		// 토스페이먼츠 API 인증 설정
		String widgetSecretKey = "";
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
		String authorization = "Basic " + new String(encodedBytes);

		URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("Authorization", authorization);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);

		try (OutputStream outputStream = connection.getOutputStream()) {
			outputStream.write(obj.toString().getBytes(StandardCharsets.UTF_8));
		}

		int code = connection.getResponseCode();
		boolean isSuccess = code == 200;
		InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

		if (responseStream == null) {
			JSONObject errorResponse = new JSONObject();
			errorResponse.put("message", "No response from payment service");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

		JSONObject jsonObject;
		try (Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8)) {
			jsonObject = (JSONObject) parser.parse(reader);
		}

		if (isSuccess) {

			SubscriptionPaymentDefaultVO payment = new SubscriptionPaymentDefaultVO();
			String shortOrderId = orderId.length() > 7 ? orderId.substring(0, 7) : orderId;
			payment.setPaymentDate(LocalDate.now());
			payment.setPaymentAmount(Double.valueOf(amount));
			payment.setGroupId(groupId);
			payment.setSubscriptionId(shortOrderId);

			service.savePayment(payment);
			service.updateUseByGroupId(groupId);
		} else {

			try (InputStream errorStream = connection.getErrorStream();
					Reader errorReader = errorStream != null
							? new InputStreamReader(errorStream, StandardCharsets.UTF_8)
							: null) {
				if (errorReader != null) {
					JSONObject errorJson = (JSONObject) parser.parse(errorReader);
					System.out.println("Error response: " + errorJson.toJSONString());
					JSONObject errorResponse = new JSONObject();
					errorResponse.put("message", "Payment failed: " + errorJson.toJSONString());
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
				} else {
					System.out.println("Error response stream is null");
					JSONObject errorResponse = new JSONObject();
					errorResponse.put("message", "Unknown error occurred");
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
				}
			}
		}

		return ResponseEntity.status(code).body(jsonObject);
	}

	@GetMapping("/success")
	public String paymentSuccess(@PathVariable String groupId, Model model, @RequestParam Map<String, String> params) {

		System.out.println("wqrhwqkjherjwqklkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		model.addAttribute("paymentKey", params.get("paymentKey"));
		model.addAttribute("orderId", params.get("orderId"));
		model.addAttribute("amount", params.get("amount"));
		return "tiles:groupMember/success";
	}

	@GetMapping("/fail")
	public String paymentFail(@PathVariable String groupId, Model model, @RequestParam Map<String, String> params) {

		model.addAttribute("message", params.get("message"));
		model.addAttribute("code", params.get("code"));
		return "tiles:groupMember/fail";
	}

//	@PostMapping("/cancel")
//	public ResponseEntity<String> cancelPayment(@RequestBody Map<String, String> request) {
//		String paymentKey = request.get("paymentKey");
//		String cancelReason = request.get("cancelReason");
//
//		String cancelUrl = "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";
//
//		// 헤더 설정
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.set("Authorization", "Basic " + "your_payment_key_base64_encoded");
//
//		// 바디 설정
//		Map<String, Object> body = new HashMap<>();
//		body.put("cancelReason", cancelReason);
//
//		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//
//		// 결제 취소 API 호출
//		ResponseEntity<String> response = restTemplate.exchange(cancelUrl, HttpMethod.POST, entity, String.class);
//
//		return ResponseEntity.status(HttpStatus.OK).body(response.getBody());
//	}
}
