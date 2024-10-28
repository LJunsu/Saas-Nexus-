<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.image-container {
	width: 100px;
	height: 130px;
	border: 1px solid #ccc;
	display: inline-block;
}

.image-container img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.image-container img:not([src]) {
	visibility: hidden;
}

th, tr, td {
	border: none; /* 테이블, 행, 셀의 테두리를 제거 */
}

.custom-file-upload {
	position: relative;
	display: inline-block;
}

.custom-file-upload input[type="file"] {
	display: none; /* 기본 파일 선택 버튼 숨기기 */
}

.custom-file-upload label {
	padding: 8px 20px;
	cursor: pointer;
}

#file-name {
	margin-left: 10px;
	vertical-align: middle;
}
</style>
<div
	class="container-xxl d-flex justify-content-center align-items-center"
	style="min-height: 100vh;">
	<div class="">
		<div class="authentication-inner py-4">
			<div class="card" style="width: 700px;">
				<div class="card-body">
					<h4 class="mb-1 pt-2 text-center"
						style="margin-top: 10px; font-weight: 600;">NEXUS 회원가입을 진행합니다
						🚀</h4>
					<p class="mb-4 text-center mt-2">회원가입을 하고 넥서스의 모든 서비스를 이용해보세요</p>
					<form:form method="post" modelAttribute="member"
						enctype="multipart/form-data">
						<div class="container">
							<table class="table">
								<tr>
									<th>회원 프로필</th>
									<td>
										<div class="image-container"
											style="border: solid 1px #dfdfdf;">
											<img id="image" alt="" width="100" height="130"
												src="${cPath }/resources/images/white.PNG">
										</div>
										<div class="custom-file-upload ms-3">
											<input type="file" id="memberImage" name="memberImage"
												accept=".jpg, .png" /> <label for="memberImage"
												class="btn btn-primary">파일 선택</label> <span id="file-name">선택된
												파일 없음</span>
											<!-- 파일명을 표시할 공간 -->
										</div> <!-- 				<input type="file" id="memberImage" name="memberImage" accept=".jpg, .png" class="btn btn-primary"/> -->
									</td>
								</tr>
								<tr>
									<th>회원ID</th>
									<td><form:input type="text" id="memberId" path="memberId"
											class="form-control" required="required" />
										<button id="id-chk-btn" type="button" style="display: none;">중복체크</button>
										<form:errors path="memberId" id="memberId-error"
											cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>회원비밀번호</th>
									<td><form:input type="password" id="memberPw" path="memberPw"
											class="form-control" required="required" /> <form:errors
											path="memberPw" cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>회원명</th>
									<td><form:input type="text" id="memberName" path="memberName"
											class="form-control" required="required" /> <form:errors
											path="memberName" cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>주민번호1</th>
									<td><form:input type="number" id="memberRegno1" path="memberRegno1"
											class="form-control" required="required" /> <form:errors
											path="memberRegno1" cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>주민번호2</th>
									<td><form:input type="number" id="memberRegno2" path="memberRegno2"
											class="form-control" required="required" /> <form:errors
											path="memberRegno2" cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>주소</th>
									<td>
										<div class="row g-2 mb-2">
											<div class="col-md-6">
												<div class="input-group" style="max-width: 300px;">
													<form:input type="text" path="memberZip"
														id="sample6_postcode" class="form-control"
														placeholder="우편번호" readonly="readonly"
														style="max-width: 120px;" />
													<button class="btn btn-outline-secondary" type="button"
														onclick="sample6_execDaumPostcode()">우편번호 찾기</button>
												</div>
											</div>
										</div>
										<div class="row g-2 mb-2">
											<div class="col-md-6">
												<form:input type="text" path="memberAddress1"
													id="sample6_address" class="form-control" placeholder="주소"
													readonly="true" />
											</div>
										</div>
										<div class="row g-2">
											<div class="col-md-6">
												<form:input type="text" path="memberAddress2"
													id="sample6_detailAddress" class="form-control"
													placeholder="상세주소" />
											</div>
											<div class="col-md-6">
												<input type="text" id="sample6_extraAddress"
													class="form-control" placeholder="참고항목" readonly="true">
											</div>
										</div> <span id="guide" class="form-text text-muted mt-1"
										style="display: none"></span>
									</td>
								</tr>
								<tr>
									<th>전화번호</th>
									<td><form:input type="text" id="memberTel" path="memberTel"
											class="form-control" required="required" /> <form:errors
											path="memberTel" cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>이메일</th>
									<td><form:input type="text" id="memberEmail" path="memberEmail"
											class="form-control" required="required" /> <form:errors
											path="memberEmail" cssClass="text-danger" element="span" /></td>
								</tr>
								<tr>
									<th>회원 설명</th>
									<td><form:textarea id="memberDescription" path="memberDescription"
											class="form-control" /> <form:errors
											path="memberDescription" cssClass="text-danger"
											element="span" /></td>
								</tr>
								<tr>
									<th>본인인증질문</th>
									<td><form:select id="memberAuthQuestion" path="memberAuthQuestion"
											class="form-control" required="required">
											<form:option value="">선택해주세요.</form:option>
											<form:option value="1">나의 출신 초등학교 이름은?</form:option>
											<form:option value="2">내가 태어난 도시의 이름은?</form:option>
											<form:option value="3">내가 처음 해외여행을 간 국가는?</form:option>
											<form:option value="4">내가 가장 좋아하는 영화 캐릭터는 누구?</form:option>
											<form:option value="5">내가 처음 키웠던 애완동물의 이름은?</form:option>
											<form:option value="6">나의 가장 친한 친구의 이름은?</form:option>
										</form:select> <form:errors path="memberAuthQuestion" cssClass="text-danger"
											element="span" /></td>
								</tr>
								<tr>
									<th
										style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">본인인증답변</th>
									<td><form:input type="text" id="memberAuthResponse" path="memberAuthResponse"
											class="form-control" required="required" /> <form:errors
											path="memberAuthResponse" cssClass="text-danger"
											element="span" /></td>
								</tr>
								<tr>
									<td colspan="2" class="text-center">
										<button class="btn btn-primary" type="submit">등록</button>
										<button id="cancelBtn" class="btn btn-danger" type="reset">취소</button>
									</td>
								</tr>
							</table>
							<a class="btn" id="UpdateAuto" style="float: right; margin: 10px 10px 10px 0;">자동완성</a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${cPath }/resources/js/app/login/idCheck.js"></script>
<script src="${cPath }/resources/js/app/login/createAccountUI_gm.js"></script>
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	function sample6_execDaumPostcode() {
		new daum.Postcode(
				{
					oncomplete : function(data) {
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
						if (data.userSelectedType === 'R') {
							// 법정동명이 있을 경우 추가한다. (법정리는 제외)
							// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
							if (data.bname !== ''
									&& /[동|로|가]$/g.test(data.bname)) {
								extraAddr += data.bname;
							}
							// 건물명이 있고, 공동주택일 경우 추가한다.
							if (data.buildingName !== ''
									&& data.apartment === 'Y') {
								extraAddr += (extraAddr !== '' ? ', '
										+ data.buildingName : data.buildingName);
							}
							// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
							if (extraAddr !== '') {
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
						document.getElementById("sample6_detailAddress")
								.focus();
					}
				}).open();
	}
</script>