<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Content -->

					<div class="container-xxl flex-grow-1 container-p-y">
						<div class="card">
							<!-- Pricing Plans -->
							<div class="pb-sm-5 pb-2 rounded-top">
								<div class="container py-5">
									<h2 class="text-center mb-2 mt-0 mt-md-4">결제 완료</h2>
									<p class="text-center">저희와 함께 시작하세요  -  개인과 팀에게 완벽합니다.</p>
									<div class="row mx-0 gy-3 px-lg-5">
										<!-- Pro -->
										<div class="col-lg mb-md-0 mb-4">
											<div class="card border-primary border shadow-none">
												<div class="card-body position-relative">
													<div class="position-absolute end-0 me-4 top-0 mt-4">
													</div>
													<div class="my-3 pt-2 text-center">
														<img
															src="${pageContext.request.contextPath}/resources/nexus/assets/img/pages/결제완료.png"
															alt="Standard Image" height="140" />
													</div>
													<a href="${cPath}/group/${groupId}/"
														class="btn btn-primary d-grid w-100">Nexus 시작하기</a>
												</div>
											</div>
										</div>
							<!--/ Pricing Plans -->
						</div>
					</div>
					<!--/ Content -->


<script>
    // 쿼리 파라미터 값이 결제 요청할 때 보낸 데이터와 동일한지 반드시 확인하세요.
    // 클라이언트에서 결제 금액을 조작하는 행위를 방지할 수 있습니다.

    const urlParams = new URLSearchParams(window.location.search);
    const paymentKey = urlParams.get("paymentKey");
    const orderId = urlParams.get("orderId");
    const amount = urlParams.get("amount");
    const groupId = urlParams.get("groupId");
    const contextPath = document.body.dataset.contextPath;

    // contextPath 확인
    console.log("Context Path:", contextPath);

    async function confirm() {
        const requestData = {
            paymentKey: paymentKey,
            orderId: orderId,
            amount: amount,
        };

        const fetchUrl = `/nexus/group/${groupId}/billing/confirm`;
        console.log("Fetch URL: ", fetchUrl);

        try {
            const response = await fetch(fetchUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(requestData),
            });

            console.log("Response Status: ", response.status);

            const json = await response.json();

            if (!response.ok) {
                // 결제 실패 비즈니스 로직을 구현하세요.
                console.log("Failure JSON:", json);
                window.location.href = `/nexus/group/${groupId}/billing/fail?message=${json.message}&code=${json.code}`;
            } else {
                // 결제 성공 비즈니스 로직을 구현하세요.
                console.log("Success JSON:", json);
            }
        } catch (error) {
            console.error("Fetch error: ", error);
            window.location.href = `/nexus/group/${groupId}/billing/fail?message=Unexpected error occurred&code=500`;
        }
    }


    confirm();


    const paymentKeyElement = document.getElementById("paymentKey");
    const orderIdElement = document.getElementById("orderId");
    const amountElement = document.getElementById("amount");


    console.log(orderIdElement);
    orderIdElement.textContent = "주문번호: " + orderId;
    amountElement.textContent = "결제 금액: " + amount;
    paymentKeyElement.textContent = "paymentKey: " + paymentKey;
</script>
