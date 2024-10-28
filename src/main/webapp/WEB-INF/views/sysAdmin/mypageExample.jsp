<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}"
	var="userName" />
<!--  MEMBER이름 -->
<c:set value="${authentication.principal.realUser.memberId}"
	var="userId" />
<!--  MEMBER이름 -->


<style>



.modal-dialog {
	max-width: 30%; /* 모달 가로 크기를 80%로 설정 */
}

.modal-xl {
	max-width: 60%;
}

/* 프로젝트 카드 스타일 */
.project-card {
	border: 1px solid #e0e0e0;
	border-radius: 8px;
	box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	transition: transform 0.2s;
	background-color: #ffffff;
}

.project-card:hover {
	transform: translateY(-5px);
}

.project-card-title {
	font-size: 1.25rem;
	font-weight: 600;
	color: #343a40;
}

.project-card-description {
	color: #6c757d;
}

.project-card-footer {
	background-color: #f8f9fa;
	border-top: 1px solid #e9ecef;
	padding: 10px;
	text-align: right;
}

.project-card-footer a {
	text-decoration: none;
}

.badge-project {
	background-color: #198754;
	color: #ffffff;
	padding: 5px 10px;
	border-radius: 20px;
	font-size: 0.9rem;
}
/* Modal Custom Styling */
.stylish-modal {
	border-radius: 15px;
	background-color: #f9f9f9;
	box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

.modal-title-custom {
	font-family: 'Poppins', sans-serif;
	font-weight: 600;
	color: #333;
}

.stylish-input-group .form-control {
	border-radius: 10px;
	border: 1px solid #ddd;
	padding: 10px;
}

.stylish-input-group .input-group-text {
	background-color: #eee;
	border: none;
	border-radius: 10px 0 0 10px;
}

.stylish-button {
	border-radius: 30px;
	padding: 10px 30px;
}

.stylish-button:hover {
	background-color: #004085;
	color: #fff;
}

/* 타임라인 컨테이너 설정 */
#activityTimeline {
	max-height: 610px; /* 원하는 높이 지정 */
	overflow-y: auto; /* 수직 스크롤만 적용 */
	overflow-x: visible; /* 수평 스크롤은 없도록 */
	-ms-overflow-style: none; /* IE 및 Edge 스크롤바 숨기기 */
	scrollbar-width: none; /* Firefox 스크롤바 숨기기 */
}

/* 웹킷 브라우저 (크롬, 사파리 등)에서 스크롤바 숨기기 */
#activityTimeline::-webkit-scrollbar {
	display: none; /* 스크롤바 숨기기 */
}

.timeline {
	padding: 10px;
}


.modal-body {
  max-height: 1000px; /* 모달 본문의 최대 높이를 설정하여 스크롤 가능하게 함 */
  overflow-y: auto; /* Y축에만 스크롤 추가 */
}

/* 스크롤바를 숨기기 위한 스타일 */
.modal-body::-webkit-scrollbar {
  display: none; /* 크롬, 사파리, 엣지 등 웹킷 기반 브라우저에서 스크롤바 숨기기 */
}
.modal-body {
  -ms-overflow-style: none; /* IE와 Edge에서 스크롤바 숨기기 */
  scrollbar-width: none; /* 파이어폭스에서 스크롤바 숨기기 */
}



</style>

<!-- content -->
<!-- <div class="container-xxl flex-grow-1 container-p-y">
              <h3 class="py-3 mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">길도연의 홈페이지 제작</h3>

            </div> -->
<div class="container-xxl flex-grow-1 container-p-y" id="groupIddd"
	data-group-ids="${groupId}">
	<!-- Header -->
	<div class="row" id="memberIds" data-member-id="${userId }">
		<div class="col-12">
			<div class="card mb-4">
				<div class="user-profile-header-banner">
					<img
						src="${pageContext.request.contextPath}/resources/images/profile-banner.png"
						alt="Banner image" class="rounded-top" />
					<!-- 나중에 동적으로  -->
				</div>
				<div
					class="user-profile-header d-flex flex-column flex-sm-row text-sm-start text-center mb-4">
					<div class="flex-shrink-0 mt-n2 mx-sm-0 mx-auto">
						<img
							src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png"
							alt="user image"
							class="mt-2 d-block ms-0 ms-sm-4 rounded user-profile-img"
							style="box-shadow: 0 0.25rem 1.125rem rgba(75, 70, 92, 0.1);" 
							onclick="changeProfileImg('${princiapl.realUser.memberId}')"/>
							 <input type="file" id="profileImgInput" style="display:none;" onchange="uploadProfileImage('${princiapl.realUser.memberId}')">
						<!-- 나중에 동적으로  -->

					</div>
					<div class="flex-grow-1 mt-3 mt-sm-5">
						<div
							class="d-flex align-items-md-end align-items-sm-start align-items-center justify-content-md-between justify-content-start mx-4 flex-md-row flex-column gap-4">
							<div class="user-profile-info">
								<div
									style="display: flex; flex-direction: row; align-items: center;">
									<h4 style="font-weight: 600;">${memberInfo.memberName }(${memberInfo.memberId })</h4>
									<c:choose>
										<c:when
											test="${memberInfo.groupManagementVO.approvalDate != null}">
											<span class="badge bg-label-success me-1"
												style="align-self: center; margin-bottom: 13px; margin-left: 10px;">승인완료</span>
										</c:when>
										<c:otherwise>
											<span class="badge bg-label-danger me-1"
												style="align-self: center; margin-bottom: 13px; margin-left: 10px;">승인대기</span>
										</c:otherwise>
									</c:choose>
								</div>
								<ul
									class="list-inline mb-0 d-flex align-items-center flex-wrap justify-content-sm-start justify-content-center gap-2">
									<li class="list-inline-item d-flex gap-1"><i
										class="ti ti-map-pin" id="addressSub"></i></li>
									<script type="text/javascript">
							  let addressSub = document.querySelector("#addressSub");
							  var address = '${memberInfo.memberAddress1}';
							    // 앞 두 글자 추출
							    var city = address.substring(0, 2);
							    // JSP에 city 값을 표시할 경우
							    addressSub.innerText = city;
							  </script>

									<li class="list-inline-item d-flex gap-1"><i
										class="ti ti-calendar"></i> <span id="joinDate"></span> <!-- 배너에 표기되는 joindate  ex)April 2024 로표기되게 ㄱㄱ -->
										<script type="text/javascript">

								    // dateStr에 memberJoinDate 값을 전달
								    let dateStr = '${memberInfo.memberJoinDate}';

								    // Date 객체로 변환
								    let date = new Date(dateStr);

								    // 월 이름 배열
								    let monthNames = [
								        "January", "February", "March", "April", "May", "June",
								        "July", "August", "September", "October", "November", "December"
								    ];

								    let month = monthNames[date.getMonth()];
								    let year = date.getFullYear();

								    // 원하는 형식으로 변환
								    let formattedDate = month + " " + year;

								    // formattedDate를 HTML에 삽입
								    document.getElementById('joinDate').textContent = formattedDate;
								</script></li>
								</ul>
							</div>
							<a href="javascript:;" class="btn btn-primary me-3"
								data-bs-target="#editUser" data-bs-toggle="modal">개인 정보 수정 </a>

						</div>


					</div>

				</div>
			</div>
		</div>
	</div>
	<!--/ Header -->

	<!-- Navbar pills -->
	<div class="row">
		<div class="col-md-12">
			<ul class="nav nav-pills flex-column flex-sm-row mb-4">
				<li class="nav-item"><a class="nav-link active"
					href="javascript:void(0);"><i
						class="ti-xs ti ti-user-check me-1"></i> 상세 정보</a></li>
			</ul>
		</div>
	</div>
	<!--/ Navbar pills -->

	<!-- User Profile Content -->
	<div class="row">
		<div class="col-xl-4 col-lg-5 col-md-5">
			<!-- About User -->
			<div class="card mb-4">
				<div class="card-body">
					<p class="card-text text-uppercase" style="font-weight: 500;">그룹
						상세정보</p>
					<ul class="list-unstyled mb-4 mt-3">
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-user text-heading"></i><span
							class="fw-medium mx-2 text-heading">그룹명:</span> <span>${memberInfo.groupManagementVO.groupName }</span>
						</li>
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-calendar"></i><span
							class="fw-medium mx-2 text-heading">그룹 생성일:</span> <span>${memberInfo.groupManagementVO.groupCreationDate }</span>
						</li>
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-check text-heading"></i><span
							class="fw-medium mx-2 text-heading">구독상태:</span> <c:choose>
								<c:when
									test="${memberInfo.groupManagementVO.approvalDate != null}">
									<span class="badge bg-label-primary me-1">subscribe</span>
								</c:when>
								<c:otherwise>
									<span class="badge bg-label-danger">No subscribe</span>
								</c:otherwise>
							</c:choose></li>


						<c:choose>
							<c:when test="${memberInfo.memberRole == 'ROLE_GROUPADMIN'}">
								<li class="d-flex align-items-center mb-3"><i
									class="ti ti-crown text-heading"></i><span
									class="fw-medium mx-2 text-heading">역할:</span> <span
									class="badge rounded-pill bg-label-success">그룹 관리자</span></li>
							</c:when>
							<c:when test="${memberInfo.memberRole == 'ROLE_USER'}">
								<li class="d-flex align-items-center mb-3"><i
									class="ti ti-crown text-heading"></i><span
									class="fw-medium mx-2 text-heading">역할:</span> <span
									class="badge rounded-pill bg-label-dark">그룹원</span></li>
							</c:when>
							<c:otherwise>
								<li class="d-flex align-items-center mb-3"><i
									class="ti ti-crown text-heading"></i><span
									class="fw-medium mx-2 text-heading">역할:</span> <span
									class="badge rounded-pill bg-label-info">시스템관리자</span></li>
							</c:otherwise>
						</c:choose>

						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-file-description text-heading"></i><span
							class="fw-medium mx-2 text-heading">회사주소:</span> <span>${memberInfo.groupManagementVO.groupAddress }</span>
						</li>
					</ul>
					<p class="card-text text-uppercase pt-2"
						style="font-weight: 500; font-size: 0.9rem;">연락처</p>
					<ul class="list-unstyled mb-4 mt-3">
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-phone-call"></i><span
							class="fw-medium mx-2 text-heading">전화번호:</span> <span>${memberInfo.memberTel }</span>
						</li>
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-mail"></i><span class="fw-medium mx-2 text-heading">이메일:</span>
							<span>${memberInfo.memberEmail}</span></li>
					</ul>
				</div>
			</div>
			<!--/ About User -->
			<!-- Profile Overview -->
			<div class="card mb-4">
				<div class="card-body">
					<p class="card-text text-uppercase" style="font-weight: 500;">그룹
						요약</p>
					<ul class="list-unstyled mb-0">
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-check"></i><span class="fw-medium mx-2">해결한
								이슈:</span> <span>${sIssueCount }개</span></li>
						<li class="d-flex align-items-center mb-3"><i
							class="ti ti-layout-grid"></i><span class="fw-medium mx-2">생성된
								프로젝트:</span> <span>${projectCount}개</span></li>
						<li class="d-flex align-items-center"><i class="ti ti-users"></i><span
							class="fw-medium mx-2">그룹 멤버:</span> <span>${groupMemberCount}명</span></li>
					</ul>
				</div>
			</div>
			<!--/ Profile Overview -->
		</div>
		<div class="col-xl-8 col-lg-7 col-md-7">
			<!-- Activity Timeline -->
			<div class="card card-action mb-4">
				<div class="card-header align-items-center">
					<h5 class="card-action-title mb-0" style="font-weight: 600;">활동
						타임라인</h5>
					<div class="card-action-element">
						<div class="dropdown">
							<button type="button" class="btn dropdown-toggle hide-arrow p-0"
								data-bs-toggle="dropdown" aria-expanded="false"></button>
							<ul class="dropdown-menu dropdown-menu-end">
								<li><a class="dropdown-item" href="javascript:void(0);">Share
										timeline</a></li>
								<li><a class="dropdown-item" href="javascript:void(0);">Suggest
										edits</a></li>
								<li>
									<hr class="dropdown-divider" />
								</li>
								<li><a class="dropdown-item" href="javascript:void(0);">Report
										bug</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!-- 타임라인을 표시할 빈 div -->
				<div class="card-body pb-0">
					<ul id="activityTimeline" class="timeline ms-1 mb-0"></ul>
				</div>

			</div>
			<!--/ Activity Timeline -->
			<div class="row">
				<!-- Connections -->
				<div class="col-lg-12 col-xl-6">
					<div class="card card-action mb-4">
						<div class="card-header align-items-center">
							<h5 class="card-action-title mb-0" style="font-weight: 600;">그룹
								회원</h5>
							<div class="card-action-element">
								<div class="dropdown">
									<button type="button"
										class="btn dropdown-toggle hide-arrow p-0"
										data-bs-toggle="dropdown" aria-expanded="false"></button>


									<ul class="dropdown-menu dropdown-menu-end">


										<li><a class="dropdown-item" href="javascript:void(0);">Share
												connections</a></li>
										<li><a class="dropdown-item" href="javascript:void(0);">Suggest
												edits</a></li>
										<li>
											<hr class="dropdown-divider" />
										</li>
										<li><a class="dropdown-item" href="javascript:void(0);">Report
												bug</a></li>


									</ul>


								</div>
							</div>
						</div>
						<div class="card-body">
							<ul class="list-unstyled mb-0">

								<c:forEach var="groupMember" items="${groupMemberList}"
									varStatus="status">
									<c:if test="${status.index < 5}">

										<li class="mb-3">
											<div class="d-flex align-items-start" id="ProjectList"
												data-mem-i="${groupMember.memberId}">
												<div class="d-flex align-items-start">
													<div class="avatar me-2">
														<img
															src="https://storage.googleapis.com/java_bucket_hstest1/${groupMember.memberId}/profile.png"
															alt="Avatar" class="rounded-circle" />
													</div>
													<div class="me-2 ms-1">
														<h6 class="mb-0">${groupMember.memberName }</h6>
														<small class="text-muted">${groupMember.issueVO[0].issueCount }개의
															이슈 할당</small>
													</div>
												</div>
												<div class="ms-auto">
													<c:choose>
														<c:when test="${onlineUsers[groupMember.memberId]}">
															<button class="btn btn-label-success btn-icon btn-sm">
																<i class="ti ti-user-check ti-xs"></i>
															</button>
														</c:when>
														<c:otherwise>
															<button class="btn btn-label-danger btn-icon btn-sm">
																<i class="ti ti-user-off ti-xs"></i>
															</button>
														</c:otherwise>
													</c:choose>
												</div>

											</div>
										</li>
									</c:if>
								</c:forEach>

							</ul>
						</div>
					</div>
				</div>
				<!--/ Connections -->
				<!-- Teams -->
				<div class="col-lg-12 col-xl-6">
					<div class="card card-action mb-4">
						<div class="card-header align-items-center">
							<h5 class="card-action-title mb-0" style="font-weight: 600;">프로젝트</h5>
							<div class="card-action-element">
								<div class="dropdown">
									<button type="button"
										class="btn dropdown-toggle hide-arrow p-0"
										data-bs-toggle="dropdown" aria-expanded="false"></button>
									<ul class="dropdown-menu dropdown-menu-end">
										<li><a class="dropdown-item" href="javascript:void(0);">Share
												teams</a></li>
										<li><a class="dropdown-item" href="javascript:void(0);">Suggest
												edits</a></li>
										<li>
											<hr class="dropdown-divider" />
										</li>
										<li><a class="dropdown-item" href="javascript:void(0);">Report
												bug</a></li>
									</ul>
								</div>
							</div>
						</div>
						<div class="card-body">
							<ul class="list-unstyled mb-0">

								<c:forEach var="projectList" items="${projectList}"
									varStatus="status">
									<c:if test="${status.index < 5}">
										<li class="mb-3">
											<div class="d-flex align-items-center">
												<div class="d-flex align-items-start">
													<div class="avatar me-2">
														<img
															src="/nexus/resources/nexus/assets/img/icons/brands/react-label.png"
															alt class="h-auto rounded-circle" />
													</div>
													<div class="me-2 ms-1">
														<a
															href="${cPath}/${groupId}/${projectList['PROJECT_ID']}/chMain">
															<h6 class="mb-0">${projectList["PROJECT_NAME"]}</h6>
														</a> <small class="text-muted">${projectList["MEMBER_COUNT"]}명
															참여중</small>
													</div>
												</div>
												<!-- 대문자로 접근해야함 map으로 받은key가 대문자라 -->
												<div class="ms-auto">
													<c:choose>
														<c:when
															test="${projectList['PROJECT_SUCCESS_STATUS'].equals('N')}">
															<a href="javascript:;"><span
																class="badge bg-label-success">진행 중</span></a>
														</c:when>
														<c:when
															test="${projectList['PROJECT_SUCCESS_STATUS'].equals('Y')}">
															<a href="javascript:;"><span
																class="badge bg-label-warning">완료</span></a>
														</c:when>
													</c:choose>
												</div>
											</div>
										</li>
									</c:if>
								</c:forEach>
								<li class="text-center"><a href="javascript:;"
									data-bs-toggle="modal" data-bs-target="#largeModal">모든 프로젝트
										확인하기</a></li>
							</ul>
						</div>
					</div>
				</div>
				<!--/ Teams -->
			</div>

			<!-- 프로젝트 모달 -->
<!-- 			<div class="modal fade" id="largeModal" tabindex="-1" -->
<!-- 				aria-labelledby="largeModalLabel" aria-hidden="true"> -->
<!-- 				<div class="modal-dialog modal-xl"> -->
<!-- 					'modal-lg' 클래스를 추가하여 가로 크기를 크게 설정 -->
<!-- 					<div class="modal-content"> -->
<!-- 						<div class="modal-header"> -->
<!-- 							<h5 class="modal-title" id="largeModalLabel">모든 프로젝트 리스트</h5> -->
<!-- 							<button type="button" class="btn-close" data-bs-dismiss="modal" -->
<!-- 								aria-label="Close"></button> -->
<!-- 						</div> -->
<!-- 						<div class="modal-body"> -->
<!-- 							<ul class="list-group"> -->
<!-- 								이곳에 프로젝트 리스트를 추가 -->
<%-- 								<c:forEach var="projectList" items="${projectList}"> --%>
<!-- 									<li class="list-group-item"> -->
<!-- 										<div class="d-flex justify-content-between align-items-center"> -->
<!-- 											<div> -->
<%-- 												<h6>${projectList["PROJECT_NAME"]}</h6> --%>
<%-- 												<small class="text-muted">${projectList["MEMBER_COUNT"]}명 --%>
<!-- 													참여중</small> -->
<!-- 											</div> -->
<!-- 											<div> -->
<%-- 												<c:choose> --%>
<%-- 													<c:when --%>
<%-- 														test="${projectList['PROJECT_SUCCESS_STATUS'].equals('N')}"> --%>
<!-- 														<span class="badge bg-label-success">진행 중</span> -->
<%-- 													</c:when> --%>
<%-- 													<c:when --%>
<%-- 														test="${projectList['PROJECT_SUCCESS_STATUS'].equals('Y')}"> --%>
<!-- 														<span class="badge bg-label-warning">완료</span> -->
<%-- 													</c:when> --%>
<%-- 												</c:choose> --%>
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 									</li> -->
<%-- 								</c:forEach> --%>
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 						<div class="modal-footer"> -->
<!-- 							<button type="button" class="btn btn-secondary" -->
<!-- 								data-bs-dismiss="modal">닫기</button> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
			
<div class="modal fade" id="largeModal" tabindex="-1" aria-labelledby="largeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="largeModalLabel">모든 프로젝트 리스트</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body" style="max-height: 600px; overflow-y: auto;"> <!-- 높이를 600px로 변경 -->
        <ul class="list-group">
          <!-- 프로젝트 리스트 -->
          <c:forEach var="projectList" items="${projectList}">
            <li class="list-group-item">
              <div class="d-flex justify-content-between align-items-center">
                <div>
                  <h6>${projectList["PROJECT_NAME"]}</h6>
                  <small class="text-muted">${projectList["MEMBER_COUNT"]}명 참여중</small>
                </div>
                <div>
                  <c:choose>
                    <c:when test="${projectList['PROJECT_SUCCESS_STATUS'].equals('N')}">
                      <span class="badge bg-label-success">진행 중</span>
                    </c:when>
                    <c:when test="${projectList['PROJECT_SUCCESS_STATUS'].equals('Y')}">
                      <span class="badge bg-label-warning">완료</span>
                    </c:when>
                  </c:choose>
                </div>
              </div>
            </li>
          </c:forEach>
        </ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>

			
			

			<!-- 개인의 프로젝트 리스트 모달 -->
			<div class="modal fade" id="projectModal" tabindex="-1"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-xl">
					<!-- 모달 크기를 더 키움 -->
					<div class="modal-content">
						<div class="modal-header bg-primary text-white">
							<!-- 배경색과 텍스트 색상 변경 -->
							<h4 class="modal-title ms-3 mt-2" id="projectModalLabel"
								style="font-weight: 600; color: white;">참여 중인 프로젝트</h4>
							<button type="button" class="btn-close" data-bs-dismiss="modal"
								aria-label="Close"></button>
						</div>
						<div class="modal-body bg-primary">
							<div id="projectDetails" class="row g-3">
								<!-- 자바스크립트로 프로젝트 카드 목록을 추가 -->
							</div>
						</div>
						<!--       <div class="modal-footer">
        <button type="button" class="btn btn-outline-secondary" data-bs-dismiss="modal">닫기</button>
      </div> -->
					</div>
				</div>
			</div>

















			<!-- Projects table -->
			<!--/ User Profile Content -->
		</div>
		<!-- content -->
		<!-- modal -->
		<div class="modal fade" id="refusalModal" tabindex="-1"
			aria-hidden="true">
			<div class="modal-dialog modal-lg modal-simple modal-refer-and-earn">
				<div class="modal-content p-3 p-md-5">
					<div class="modal-body">
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close" onclick="reloading();"></button>
						<div class=" mb-4">
							<h3 class="mb-2 mt-2" id="cs-title">그룹 거절 사유를 입력해주세요.</h3>
							<p id="cs-content" class="text-muted mb-5 m-auto">해당 사유는 고객의
								이메일로 전송됩니다.</p>
						</div>
						<form class="row g-3" onsubmit="return false">
							<div class="col-lg-10">
								<input type="text" id="modalRnFEmail" class="form-control"
									placeholder="사유를 입력해주세요." aria-label="example@domain.com" />
							</div>
							<div class="col-lg-2 d-flex align-items-end">
								<button type="button" class="btn btn-primary" id="submitBtn"
									onclick="submitBtnRefusal();">전송하기</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /modal -->
		<script>
             let acceptBtn = document.querySelector("#acceptBtn");
             let refusalBtn = document.querySelector("#refusalBtn");
             let chooseApproval = document.querySelector("#chooseApproval");
             let href = null;
             let groupId = null;
             let today = null;
             function reloading(){
          	   location.reload();
             }

             function submitBtnRefusal(){
            	 var answerInput = document.getElementById('modalRnFEmail');
                 console.log(answerInput.value);
                 //이제 거절사유 입력한거 이메일로 보내줘야한디 언제하냐 ㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜㅜ
             }

             chooseApproval.addEventListener("click", (e)=>{

            	 const event = e || window.event;
                 href = window.location.href;
                 today = new Date();
                 groupId = href ? href.split('/').pop() : null;
                 console.log(groupId);

            	 if(event.target.id === 'acceptBtn'){
            		const confirmAccept = confirm("해당 그룹을 승인하시겠습니까?");
            		if(confirmAccept){
                      var data = {
                   	  	 approvalDate: today,
                   	  	 groupId : groupId
                          };
                          fetch(href, {
                              method: 'PUT',
                              headers: {
                                  'Content-Type': 'application/json',
                              },
                              body: JSON.stringify(data)
                          })
                          .then(response => response.json())
                          .then(data => {
                              console.log(data);
                              if (data.success) {
                                  alert('그룹 회원이 승인되었습니다');
                                  reloading();
                              } else {
                                  alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                              }
                          })
                          .catch((error) => {
                              console.error('Error:', error);
                          });
            		}else {
                        console.log("승인이 취소되었습니다.");
                    }
            	 }
             })

            </script>



		<!--                                        수정 모달                                  -->
		<!-- Modal Styling -->
		<div class="modal fade" id="editUser" tabindex="-1" aria-hidden="true">
			<div class="modal-dialog modal-xl modal-simple modal-edit-user">
				<div class="modal-content p-4 p-md-5 stylish-modal">
					<div class="modal-body">
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
						<div class="text-center mb-4">
							<h3 class="mb-2" style="font-weight: 700;">정보 수정</h3>
							<p class="text-muted">개인정보를 수정합니다.</p>
						</div>
						<form id="editUserForm" class="row g-3" onsubmit="return false">
							<!-- User Name -->
							<div class="col-12 col-md-6">
								<label class="form-label" for="userName">이름</label>
								<div class="input-group input-group-merge stylish-input-group">
									<span class="input-group-text"><i class="ti ti-user"></i></span>
									<input type="text" class="form-control ps-2" id="memberName"
										placeholder="이름을 입력해주세요" value="${userInfo.memberName}">
								</div>
							</div>

							<!-- Regno -->
							<div class="col-12 col-md-6">
								<label class="form-label" for="memberRegno1">주민등록번호</label>
								<div class="input-group input-group-merge stylish-input-group">
									<input type="text" id="memberRegno1" name="memberRegno1"
										class="form-control" value="${userInfo.memberRegno1}">
									<input type="password" id="memberRegno2" name="memberRegno1"
										class="form-control" value="${userInfo.memberRegno2}">
								</div>
							</div>

							<!-- E-mail -->
							<div class="col-12 col-md-6">
								<label class="form-label" for="email">이메일</label>
								<div class="input-group input-group-merge stylish-input-group">
									<span class="input-group-text"><i class="ti ti-mail"></i></span>
									<input type="text" id="memberEmail" class="form-control ps-2"
										placeholder="이메일을 입력해주세요" value="${userInfo.memberEmail}">
								</div>
							</div>

							<!-- Phone Number -->
							<div class="col-12 col-md-6">
								<label class="form-label" for="phoneNumber">핸드폰 번호</label>
								<div class="input-group stylish-input-group">
									<span class="input-group-text">KR</span> <input type="text"
										id="memberTel" name="memberTel" class="form-control"
										value="${userInfo.memberTel}">
								</div>
							</div>

							<!-- Address -->
							<div class="col-12 col-md-6">
								<label class="form-label" for="zipCode">주소</label>
								<div class="d-flex stylish-input-group">
									<input id="sample6_postcode" name="memberZip"
										placeholder="우편번호" type="text" class="form-control"
										value="${userInfo.memberZip}">
									<button class="ms-2 btn btn-outline-secondary text-nowrap"
										type="button" onclick="sample6_execDaumPostcode()">우편번호
										찾기</button>
								</div>
								<input id="sample6_address" name="memberAddress1"
									placeholder="주소" type="text" class="form-control mt-2" readonly
									value="${userInfo.memberAddress1}"> <input
									id="sample6_detailAddress" name="memberAddress2"
									placeholder="상세주소" type="text" class="form-control mt-2"
									value="${userInfo.memberAddress2}"> <input
									type="hidden" id="sample6_extraAddress" class="form-control"
									placeholder="참고항목" readonly="true">
							</div>

							<!-- Authentication Question -->
							<div class="col-12 col-md-6">
								<label class="form-label" for="language">본인인증질문</label>
								<select id="memberAuthQuestion" name="memberAuthQuestion" class="form-control stylish-input-group">
								    <option value="1" ${userInfo.memberAuthQuestion == '1' ? 'selected' : ''}>나의 출신 초등학교 이름은?</option>
								    <option value="2" ${userInfo.memberAuthQuestion == '2' ? 'selected' : ''}>내가 태어난 도시의 이름은?</option>
								    <option value="3" ${userInfo.memberAuthQuestion == '3' ? 'selected' : ''}>내가 처음 해외여행을 간 국가는?</option>
								    <option value="4" ${userInfo.memberAuthQuestion == '4' ? 'selected' : ''}>내가 가장 좋아하는 영화 캐릭터는 누구?</option>
								    <option value="5" ${userInfo.memberAuthQuestion == '5' ? 'selected' : ''}>내가 처음 키웠던 애완동물의 이름은?</option>
								    <option value="6" ${userInfo.memberAuthQuestion == '6' ? 'selected' : ''}>나의 가장 친한 친구의 이름은?</option>
								</select>
 							<label class="form-label mt-3" for="memberAuthResponse">본인인증답변</label>
								<input id="memberAuthResponse" name="memberAuthResponse"
									placeholder="답변을 입력하세요" type="text"
									class="form-control stylish-input-group"
									value="${userInfo.memberAuthResponse}">
							</div>

							<!-- Buttons -->
							<div class="col-12 text-center">
								<button type="submit" id="updateBtn"
									class="btn btn-primary me-sm-3 me-1">저장하기</button>
								<button type="reset" class="btn btn-secondary "
									data-bs-dismiss="modal">취소</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>



		<script
			src="${pageContext.request.contextPath}/resources/js/app/mypage.js"></script>
		<script
			src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
		<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
    </script>