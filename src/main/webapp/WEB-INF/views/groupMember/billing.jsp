<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Content -->
<script src="https://js.tosspayments.com/v2/standard"></script>

<style>
#subscriptionInfo {
	border: 1px solid #e0e0e0;
	border-radius: 8px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	background-color: #ffffff; /* 배경색을 제거하고 흰색으로 설정 */
	width: 100%; /* 가로폭을 100%로 설정 */
}

#subscriptionInfo h6 {
	border-bottom: 1px solid #e0e0e0;
	padding-bottom: 8px;
	margin-bottom: 12px;
	font-size: 16px;
}

#subscriptionInfo p {
	font-size: 14px;
	color: #333;
}

#payment-button {
	width: 100%; /* 가로폭을 100%로 설정 */
	border-radius: 8px; /* 버튼의 모서리 둥글게 */
}

.btn-primary {
	background-color: #007bff;
	border-color: #007bff;
}
</style>
<!-- Content -->
<div class="container-xxl flex-grow-1 container-p-y">
	<h4 class="py-3 mb-4">
		<span class="text-muted fw-light"></span>구독관리
	</h4>

	<div class="row">
		<div class="col-md-12">
			<ul class="nav nav-pills flex-column flex-md-row mb-4">
				<li class="nav-item"><a class="nav-link active"
					href="javascript:void(0);"><i
						class="ti-xs ti ti-file-description me-1"></i>청구 및 계획</a></li>
			</ul>
			<div class="card mb-4">
				<!-- Current Plan -->
				<h5 class="card-header">현재 구독 단계</h5>
				<div class="card-body">
					<div class="row">
						<div class="col-md-6 mb-1">
							<div class="mb-3">
								<h6 class="mb-1">현재 구독 단계는 ${subscription.subscriptionName }
									입니다.</h6>
							</div>
							<div class="mb-3">
								<h6 class="mb-1"></h6>
								<p>현재 그룹원 수 ${countGroupMembers } 명 입니다.</p>
							</div>
						</div>
						<div class="col-md-6 mb-1">
							<div class="alert alert-warning mb-3" role="alert">
								<h5 class="alert-heading mb-1">현재 남은 구독 기간</h5>
							</div>
							<div class="plan-statistics">
								<div class="d-flex justify-content-between">
									<h6 class="mb-2">Days</h6>
									<h6 class="mb-2">${remainingDays}(일)</h6>
								</div>

								<c:choose>
									<c:when
										test="${subscription.subscriptionPeriod != null and remainingDays != null}">
										<c:set var="subLevel"
											value="${subscription.subscriptionPeriod}"></c:set>
										<c:set var="remainingDay" value="${remainingDays}"></c:set>
										<c:set var="remainingPercentage"
											value="${(remainingDay / subLevel) * 100 }"></c:set>
									</c:when>
									<c:otherwise>
										<!-- 기본값 설정 또는 빈 상태로 처리 -->
										<c:set var="remainingPercentage" value="0"></c:set>
									</c:otherwise>
								</c:choose>

								<div class="progress mb-2" style="height: 8px">
									<div class="progress-bar" role="progressbar"
										style="width: ${remainingPercentage}%;"
										aria-valuenow="${remainingPercentage}" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- /Current Plan -->
			</div>
			<div class="card mb-4">
				<!-- Billing Address -->
				<h5 class="card-header">이용약관</h5>
				<div class="card-body" id="agreement">
					<p>
						<strong>1. 결제 방식</strong><br> 본 서비스는 신용카드, 체크카드, 또는 기타 전자 결제
						수단을 통한 결제를 지원합니다. 결제는 사용자가 선택한 방식에 따라 안전하게 처리됩니다.
					</p>
					<p>
						<strong>2. 결제 일정 및 처리</strong><br> 결제는 사용자가 서비스에 가입한 날짜를 기준으로
						매월 자동 청구됩니다. 청구서 발행 후 7일 이내에 결제가 처리되지 않으면 서비스가 제한될 수 있습니다.
					</p>
					<p>
						<strong>3. 카드 정보 보안</strong><br> 본 서비스는 결제 대행사(Toss Payments
						등)의 결제 시스템을 통해 처리되며, 어떠한 경우에도 귀하의 카드 정보는 암호화되어 안전하게 저장됩니다. 귀하의 카드
						정보는 직접적으로 저장되지 않으며, 보안 표준을 준수한 토큰화 방식으로 처리됩니다.
					</p>
					<p>
						<strong>4. 환불 정책</strong><br> 사용자가 결제를 완료한 시점부터 3일 후에는 환불이
						불가능합니다. 단, 서비스 결함이나 기타 법적 사유에 의한 환불 요청은 지원됩니다. 환불 절차는 당사의 정책에 따라
						진행됩니다.
					</p>
					<p>
						<strong>5. 결제 실패 및 서비스 중단</strong><br> 결제 실패 시, 사용자에게 알림이
						제공되며, 연체 상태가 7일 이상 지속될 경우 서비스 이용이 일시 중단될 수 있습니다.
					</p>
					<p>
						<strong>6. 약관 변경</strong><br> 당사는 필요에 따라 이용약관을 변경할 수 있습니다. 변경
						시 사전 통지를 통해 사용자가 약관 변경 사항을 확인할 수 있도록 하며, 변경된 약관은 즉시 효력을 발휘합니다.
					</p>
				</div>
			</div>

			<!-- /Billing Address -->
			<div class="card mb-4" id="payment-method">
				<h5 class="card-header">결제</h5>
				<div class="card-body">
					<div class="row">
						<div class="col-md-6" style="width: 1300px;">
							<!-- 구독 상품 선택 버튼 -->
							<div class="col-12 mb-2">
								<button class="btn btn-primary me-2 waves-effect waves-light"
									data-bs-toggle="modal" data-bs-target="#pricingModal">
									구독상품선택</button>
							</div>

							<!-- 선택된 구독 상품 정보 표시 영역 -->
							<div class="col-12 mb-2">
								<!-- 구독 상품 정보 자동 입력 영역 -->
								<div id="subscriptionInfo" class="border p-3 rounded">
									<h6 class="text-primary">선택된 구독 상품</h6>
									<p id="subscriptionName" class="mb-1">
										<strong>상품명:</strong> -
									</p>
									<p id="subscriptionPrice" class="mb-1">
										<strong>단가:</strong> -
									</p>
									<p id="subscriptionDuration" class="mb-1">
										<strong>구독 기간:</strong> -
									</p>
								</div>
								<button class="btn btn-primary w-100 mt-3" id="payment-button">
									결제하기</button>
							</div>

						</div>
					</div>
				</div>
			</div>

			<!-- Modal -->

			<!--/ Modal -->
		</div>
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by TossPayments</div>
</div>



<!-- Modal -->
<!-- Pricing Modal -->
<!-- Modal -->
<!-- Pricing Modal -->
<div class="modal fade" id="pricingModal" tabindex="-1"
	aria-hidden="true">
	<div class="modal-dialog modal-lg modal-dialog-centered">
		<!-- Adjust size with modal-lg -->
		<div class="modal-content p-2 p-md-5">
			<div class="modal-body">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
				<!-- Pricing Plans -->
				<div class="d-flex flex-wrap justify-content-between">
					<!-- Monthly Subscription -->
					<div class="card border-primary border shadow-none flex-fill mb-4"
						style="flex: 1 1 45%;">
						<div class="card-body position-relative">
							<div class="my-3 pt-2 text-center">
								<img
									src="${pageContext.request.contextPath}/resources/nexus/assets/img/illustrations/page-pricing-standard.png"
									alt="Standard Image" height="140" />
							</div>
							<h3 class="card-title text-center text-capitalize mb-1">1달
								구독 상품</h3>
							<div class="text-center h-px-100 mb-2">
								<div class="d-flex justify-content-center">
									<h1
										class="price-toggle price-monthly display-4 text-primary mb-0">27000(인)</h1>
									<sub
										class="h6 text-muted pricing-duration mt-auto mb-2 fw-normal">/월</sub>
								</div>
							</div>
							<button type="button" class="btn btn-primary d-grid w-100 mt-3"
								data-subscription-id="SUB_001" data-subscription-name="1달 구독!"
								data-subscription-price="27000" data-subscription-duration="1개월"
								onclick="setSubscriptionInfo(this)">구독하기</button>
						</div>
					</div>

					<!-- Yearly Subscription -->
					<div class="card border-primary border shadow-none flex-fill mb-4"
						style="flex: 1 1 45%;">
						<div class="card-body position-relative">
							<div class="my-3 pt-2 text-center">
								<img
									src="${pageContext.request.contextPath}/resources/nexus/assets/img/illustrations/page-pricing-standard.png"
									alt="Standard Image" height="140" />
							</div>
							<h3 class="card-title text-center text-capitalize mb-1">1년
								구독 상품</h3>
							<div class="text-center h-px-100 mb-2">
								<div class="d-flex justify-content-center">
									<h1
										class="price-toggle price-monthly display-4 text-primary mb-0">240000(인)</h1>
									<sub
										class="h6 text-muted pricing-duration mt-auto mb-2 fw-normal">/년</sub>
								</div>
							</div>
							<button type="button" class="btn btn-primary d-grid w-100 mt-3"
								data-subscription-id="SUB_002" data-subscription-name="1년 구독!"
								data-subscription-price="240000"
								data-subscription-duration="12개월"
								onclick="setSubscriptionInfo(this)">구독하기</button>
						</div>
					</div>
				</div>
				<!--/ Pricing Plans -->
			</div>
		</div>
	</div>
</div>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // 결제위젯 초기화
    const clientKey = "";
    const tossPayments = TossPayments(clientKey);
    const widgets = tossPayments.widgets({
        customerKey: ""
    });

    // 초기 금액 설정 (임시 금액)
    widgets.setAmount({
        currency: "KRW",
        value: 1, // 임시 금액
    }).then(() => {
        // 결제 UI 렌더링
        widgets.renderPaymentMethods({
            selector: "#payment-method",
            variantKey: "DEFAULT",
        });
        widgets.renderAgreement({
            selector: "#agreement",
            variantKey: "AGREEMENT"
        });
    }).catch(error => {
        console.error("결제 위젯 초기화 중 오류 발생:", error);
    });

    // 상품 선택 시 호출될 함수
    window.setSubscriptionInfo = function(button) {
        var subscriptionName = button.getAttribute('data-subscription-name');
        var subscriptionPrice = button.getAttribute('data-subscription-price');
        var subscriptionDuration = button.getAttribute('data-subscription-duration');
        var countGroupMembers = ${countGroupMembers}; // JSP 변수를 JS로 삽입
        const urlParams = new URLSearchParams(window.location.search);
        const groupId = urlParams.get("groupId");
        var subscriptionId = button.getAttribute('data-subscription-id');


        // 단가를 숫자로 변환
        var price = parseInt(subscriptionPrice, 10);

        // 총 가격 계산
        var totalPrice = price * countGroupMembers;

        // HTML 요소에 값 설정하기
        document.getElementById('subscriptionName').textContent = '상품명: ' + subscriptionName;
        document.getElementById('subscriptionPrice').textContent = '단가: ' + price + '원 (총: ' + totalPrice + '원)';
        document.getElementById('subscriptionDuration').textContent = '구독 기간: ' + subscriptionDuration;

        var paymentButton = document.getElementById("payment-button");
        paymentButton.onclick = async function () {
            if (totalPrice <= 0) {
                alert("총 결제 금액이 유효하지 않습니다. 상품을 선택해 주세요.");
                return;
            }

            try {
                // 실제 주문의 결제 금액으로 업데이트
                await widgets.setAmount({
                    currency: "KRW",
                    value: totalPrice,
                });

                // 결제창 띄우기
                await widgets.requestPayment({
                    orderId: subscriptionId + Date.now(),
                    orderName: subscriptionName,
                    successUrl: window.location.origin + `/nexus/group/${groupId}/billing/success`,
                    failUrl: window.location.origin + `/nexus/group/${groupId}/billing/fail`,
                    customerEmail: subscriptionId,
                    customerName: "김토스",
                    customerMobilePhone: "01012341234",
                });
            } catch (error) {
                console.error("결제 요청 중 오류 발생:", error);
                alert("결제 요청 중 오류가 발생했습니다. 다시 시도해 주세요.");
            }
            console.log("Payment request subscriptionId: " + subscriptionId); // 값 확인
        };

        var modalElement = document.getElementById('pricingModal');
        var modal = bootstrap.Modal.getInstance(modalElement);
        modal.hide();
    };
});
</script>
<script>

</script>
