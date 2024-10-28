<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}" var="userName" /> <!--  MEMBER이름 -->
<c:set value="${authentication.principal.realUser.memberId}" var="userId" /> <!--  MEMBER이름 -->
<div id="userName" data-user-Name="${userName}"></div>
<div id="alarmChk" data-alarm-chk="${last}"></div>
<div id="groupIdDiv" data-groupd-id="${groupId}"> </div>
<c:set value="${authentication.principal.realUser.memberRole}"
	var="memberRole" />
<!-- mem001 -->








<!-- SweetAlert2 CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/styles/ag-grid.css">
<link rel="stylesheet"
	href="https://unpkg.com/ag-grid-community/styles/ag-theme-alpine.css">

<style>
.centered-header {
  text-align: center;
}
.swal2-container {
	z-index: 2000;
}

#detailModalLabel {
	color: #ffffff; /* 하얀색으로 설정 */
}

#myGridContainer {
	width: 73%; /* 그리드 너비를 80%로 설정 */
	margin: 0 auto; /* 중앙 정렬을 위한 자동 마진 */
	padding-top: 20px; /* 그리드 위쪽에 여백 추가 */
	padding-bottom: 50px; /* 그리드와 푸터 사이에 여백 추가 */
	position: relative;
}

#myGrid {
	height: 690px; /* 원하는 높이 설정 */
	width: 100%; /* 그리드 너비를 100%로 설정 */
	box-sizing: border-box;
}

.centered {
	text-align: center;
}

.search-container {
	display: flex;
	justify-content: flex-start; /* 왼쪽 정렬 */
	margin-bottom: 10px;
}

.search-container input[type="text"] {
	width: 250px;
	padding: 5px;
}

.search-container button {
	background: #7367f0; /* 더 연하고 밝은 보라색으로 설정 */
	background-size: 20px 20px;
	width: 40px; /* 버튼 크기 조정 */
	height: 40px; /* 버튼 크기 조정 */
	border: none;
	border-radius: 5px; /* 이미지와 유사한 둥근 모서리 */
	cursor: pointer;
	margin-left: 5px;
	display: flex;
	align-items: center;
	justify-content: center;
}

.search-container button i {
	font-size: 18px;
	color: #ffffff; /* 아이콘 색상 흰색으로 설정 */
}

/* 페이지네이션 스타일링 */
.ag-paging-panel {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 10px;
	background-color: #f7f7f7;
	border-radius: 8px;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	position: relative;
}

.ag-paging-panel select {
	padding: 5px;
	border-radius: 4px;
	border: 1px solid #ccc;
}

.ag-paging-button {
	background-color: #7367f0;
	border: none;
	color: white;
	padding: 5px 10px;
	border-radius: 4px;
	margin: 0 2px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.ag-paging-button:hover {
	background-color: #5e57c2;
}

.ag-paging-panel .ag-paging-page-summary-panel {
	font-weight: bold;
}

.ag-paging-panel .ag-paging-page-summary-panel span {
	margin: 0 5px;
}

#writeButton {
	background-color: #7367f0;
	border: none;
	color: white;
	padding: 20px 30px; /* 적당한 크기로 설정 */
	border-radius: 5px;
	cursor: pointer;
	position: absolute;
	top: -40px; /* 그리드 컨테이너 상단과의 거리 설정 */
	right: 0; /* 그리드 컨테이너 오른쪽과의 거리 설정 */
}

/* 전체 테이블의 스타일 */
.table-bordered th, .table-bordered td {
	vertical-align: middle;
	padding: 10px;
	border-color: #dee2e6;
}

/* 제목, 작성자 등의 입력 필드 스타일 */
.form-control {
	border-radius: 5px;
	border: 1px solid #ced4da;
	box-shadow: none;
	transition: border-color 0.3s ease;
}

/* 입력 필드에 포커스 되었을 때의 스타일 */
.form-control:focus {
	border-color: #7367f0;
	box-shadow: 0 0 5px rgba(115, 103, 240, 0.25);
}

/* 파일 업로드 컨테이너 스타일 */
.file-upload-container {
	display: flex;
	flex-direction: column;
	gap: 10px;
	margin-top: 10px;
}

/* 파일 선택 필드 스타일 */
.file-upload-container input[type="file"] {
	padding: 10px;
	border: 1px solid #ced4da;
	border-radius: 5px;
	background-color: #f8f9fa;
	transition: background-color 0.3s ease;
}

/* 파일 선택 필드에 포커스 또는 호버 되었을 때의 스타일 */
.file-upload-container input[type="file"]:hover, .file-upload-container input[type="file"]:focus
	{
	background-color: #e2e6ea;
	border-color: #7367f0;
}

/* 테이블 헤더 스타일 */
.table-header {
	background-color: #f1f3f5;
	font-weight: bold;
}

/* 전반적인 폼 컨테이너 스타일 */
.form-container {
	background-color: #ffffff;
	border: 1px solid #dee2e6;
	border-radius: 10px;
	padding: 20px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 테이블 스타일 */
.table {
	margin-bottom: 0;
}

/* 테이블 내부의 제목 행 스타일 */
.table th {
	background-color: #f8f9fa;
	color: #495057;
	text-align: left;
}

/* 테이블 내부의 데이터 행 스타일 */
.table td {
	background-color: #ffffff;
	color: #495057;
}

/* 도연 추가 START */
.ag-theme-alpine, .ag-theme-alpine-dark, .ag-theme-alpine-auto-dark{
--ag-font-family : var(--bs-body-font-family);
}

.ag-theme-alpine .ag-row, .ag-theme-alpine-dark .ag-row, .ag-theme-alpine-auto-dark .ag-row {
    font-size: 17px;
}

[class*=ag-theme-] {
    font-size: 17px;
}
/* 도연 추가 END */


</style>

<!-- <h1 class="centered">그룹 공지</h1> -->
<!-- 중앙 정렬 추가 -->

<script>
	function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
</script>
<div id="myGridContainer" data-connection-base="${princiapl.realUser}">
<!-- 도연 추가 START -->
	
              <div class="d-flex justify-content-between" id="writeButtonArea">
                <h3 class="py-3 mb-2">그룹 공지</h3>
                 <c:if test="${memberRole eq 'ROLE_GROUPADMIN'}">
                <button type="button" class="btn btn-primary d-block waves-effect waves-light" id="" data-bs-toggle="modal" aria-pressed="false" style="margin:17px 0;"
                data-bs-target="#newBoardModal">
                  + 공지글 작성하기
                </button>
                </c:if>
              </div>
<!-- 도연 추가 END -->
	<div class="search-container">

	</div>
	<div id="myGrid" class="ag-theme-alpine"
		data-connection-memid="${princiapl.realUser.memberId}"></div>

	<!-- "작성" 버튼을 추가 -->
<!-- 	<div id="writeButtonArea"> -->
<!-- 	<button type="button" class="btn btn-primary" id="writeButton" -->
<!-- 		data-bs-toggle="modal" data-bs-target="#newBoardModal">+ 공지사항 -->
<!-- 		작성</button> -->
<!-- 	</div> -->
<div class="d-flex pt-2 justify-content-end">Powered by ag-grid</div>
</div>

<!-- 게시글 작성 MODAL-->
<div class="modal fade" id="newBoardModal" tabindex="-1"
	aria-labelledby="newBoardLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form id='insertForm' method="post" enctype="multipart/form-data">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="newBoardLabel">공지사항 등록</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-bordered">
						<tr>
							<th class="bg-light text-center" style="width: 15%;">제목</th>
							<td colspan="3"><input id="boardaddId" type="text" name="boardTitle"
								class="form-control" /></td>
						</tr>
						<tr>
							<input type="hidden" name="memberId"
								value="${princiapl.realUser.memberId}" />
							<th class="bg-light text-center" style="width: 15%;">작성자</th>
							<td><input type="text" name="memberName"
								class="form-control" readonly="readonly"
								value="${princiapl.realUser.memberName}" disabled="disabled" /></td>
						</tr>
							<input type="hidden" id="groupId"
								name="groupId" class="form-control" value="GRP013" />
						<th class="bg-light text-center">내용</th>
						<td colspan="3"><textarea name="boardContent"
								class="form-control" rows="6" id="boardContenttt"></textarea></td>
						</tr>
						<tr>
							<th class="bg-light text-center">파일 업로드</th>
							<td colspan="3">
								<div class="file-upload-container">
									<input type="file"
										name="attachedFile.fileDetails[0].uploadFile"
										class="form-control" />
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" id="auto" style="opacity: 0;">자동완성</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary">등록</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- 게시글 상세페이지 모달 -->
<div class="modal fade" id="detailModal" tabindex="-1"
	aria-labelledby="detailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<!-- 게시글 수정 폼 -->
			<form id="updateForm" enctype="multipart/form-data"
				action="${cPath}/${boardId}">
				<input type="hidden" name="boardId">
				<div class="modal-header bg-primary text-white">
					<h5 class="modal-title" id="detailModalLabel">공지글 상세 조회</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body p-3">
					<!-- 게시글 정보 -->
					<table class="table table-bordered">
						<tr>
							<th class="bg-light text-center" style="width: 15%;">제목</th>
							<td colspan="3"><input type="text" id="boardTitle"
								name="boardTitle" class="form-control" /></td>
						</tr>
						<tr>
							<th class="bg-light text-center" style="width: 15%;">작성자</th>
							<td><input type="hidden" id="memberId" name="memberId"
								class="form-control" value="${userId}"
								readonly="readonly" /> <input type="text" id="memberName"
								name="memberName" class="form-control" value="${memberName}"
								readonly="readonly" /></td>
							<th class="bg-light text-center" style="width: 15%;">작성일</th>
							<td><input type="text" id="boardCreateDate"
								name="boardCreateDate" class="form-control" disabled /></td>
						</tr>
							<input type="hidden" id="groupId"
								name="groupId" class="form-control" readonly="readonly"
								value="${groupId}" />
						<tr>
						  <th class="bg-light text-center" style="font-size: 26px; padding: 20px; font-weight: bold; color: #5a5a5a; border-bottom: 2px solid #eaeaea;">
						    내용
						  </th>
						  <td colspan="3">
						    <textarea id="boardContent"
						              name="boardContent"
						              class="form-control"
						              rows="6"
						              style="width: 100%; height: 320px; font-size: 18px; padding: 20px; border-radius: 10px; border: 1px solid #d1d1d1; background-color: #f9f9f9; box-shadow: 0 2px 5px rgba(0,0,0,0.1); line-height: 1.8; transition: box-shadow 0.3s ease;">
						    </textarea>
						  </td>
						</tr>


						<tr>
							<th class="bg-light text-center">이미 업로드된 파일</th>
							<td colspan="3">
								<div id="uploadedFilesList">
									<ul>
										<!-- 파일 목록이 여기에 추가 -->
									</ul>
								</div>
							</td>
						</tr>
						<tr id="uploadTr">
							<th class="bg-light text-center">새 파일 업로드</th>
							<td colspan="3">
								<div class="file-upload-container">
									<input type="file" id="filduploadId"
										name="attachedFile.fileDetails[0].uploadFile"
										class="form-control" />
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">닫기</button>
						
					<button type="submit" class="btn btn-primary" id="upBtn">수정</button>
					<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>
				</div>
			</form>

			<!-- 덧글 폼 -->
			<div class="modal-body p-3">
				<h6 class="mt-4">댓글</h6>
				<table class="table table-bordered" id="commentTable">
					<tbody id="answerArea"></tbody>
				</table>

				<form id="answerForm" action="">
					<input type="hidden" id="ansAdd" name="boardId" value=""> <input
						type="hidden" name="memberId"
						value="${princiapl.realUser.memberId}">
					<!-- 덧글 입력창 -->
					<div class="input-group mt-3">
						<input type="text" id="newComment" name="cmtContent"
							class="form-control" placeholder="댓글을 입력하세요" />
						<button type="button" id="answergogo" style="opacity: 0;"></button>
						<button type="submit" id="answerAdd" class="btn btn-primary">댓글등록</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>



<script
	src="https://unpkg.com/ag-grid-community/dist/ag-grid-community.noStyle.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/app/Gnotice.js"></script>


