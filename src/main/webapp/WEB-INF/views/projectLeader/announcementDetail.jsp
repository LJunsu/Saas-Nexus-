<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<style>

/* 댓글 입력 창 스타일 */
textarea#cmtContent, #editTextarea {
	width: 100%; /* 부모 요소의 100% 너비 */
	max-width: 100%; /* 최대 너비를 부모 요소에 맞춤 */
	height: 100px;
	padding: 10px;
	border: 1px solid #ced4da;
	border-radius: 8px;
	font-size: 14px;
	font-family: Arial, sans-serif;
	resize: none; /* 크기 조정 불가 */
	outline: none;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	transition: border-color 0.3s, box-shadow 0.3s;
}

textarea#cmtContent:focus {
	border-color: #007bff;
	box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

/* 댓글 작성 버튼 스타일 */
button#btnAnswer, #btnSave, #autoAdd {
	background-color: #007bff;
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 8px;
	cursor: pointer;
	margin-top: 10px;
	margin-bottom: 10px;
	transition: background-color 0.3s, transform 0.2s;
	box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
	width: auto; /* 버튼의 너비를 컨텐츠에 맞추기 */
	heigght: 100%;
}

button#btnAnswer:hover {
	background-color: #0056b3;
	transform: translateY(-2px); /* 버튼에 살짝 들어올리는 효과 */
}

button#btnAnswer:active {
	transform: translateY(0); /* 클릭 시 원래 위치로 돌아옴 */
}

button#btnAnswer:active {
	transform: translateY(0); /* 클릭 시 원래 위치로 돌아옴 */
}

/* 댓글 리스트 영역 */
#listJson {
	width: 100%; /* 부모 요소에 맞춤 */
}

.btnModify, .btnDelete {
	border-radius: 5px; /* 둥글게 만들기 */
	border: 1px solid transparent;
}

.answerSave {
	border-radius: 5px; /* 둥글게 만들기 */
	border: 1px solid transparent;
}
.card-header {
    display: flex;
    flex-direction: column; /* 필요한 경우 세로 정렬로 설정 */
    justify-content: space-between; /* 버튼을 우측으로 정렬 */
}

.button-container {
    display: flex;
    justify-content: flex-end; /* 버튼을 우측으로 정렬 */
    gap: 10px; /* 버튼 사이 간격 조정 */
    margin-top: 10px; /* 필요 시 마진 조정 */
}


</style>
<div class="container-xxl flex-grow-1 container-p-y">
	<div class="container-xxl flex-grow-1 container-p-y">
		<h4 class="py-3 mb-2">프로젝트 공지</h4>


		<div class="card">

			<div class="card-header d-flex flex-wrap pb-md-2">


				<table
					class="datatables-customers table border-top dataTable no-footer dtr-column"
					id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info">
					<thead>
						<tr>
							<th class="control sorting_disabled dtr-hidden" rowspan="1"
								colspan="1" style="width: 0px; display: none;" aria-label="">
							</th>
							<th class="text-nowrap" tabindex="0"
								aria-controls="DataTables_Table_0" rowspan="1" colspan="1">
								공지 제목</th>
							<th>${announcement.boardTitle }</th>
						</tr>
						<tr>
							<th class="" tabindex="0" aria-controls="DataTables_Table_0"
								rowspan="1" colspan="1" style="width: 150px;">작성자</th>
							<th>${announcement.memberName }</th>
						</tr>
						<tr>
							<th class="" tabindex="0" aria-controls="DataTables_Table_0"
								rowspan="1" colspan="1" style="width: 150px;">작성일</th>
							<th>${announcement.boardCreateDate}</th>
						</tr>
						<tr>
							<th class="text-nowrap" tabindex="0"
								aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
								style="height: 400px;">공지 내용</th>
							<th style="vertical-align: middle;">${announcement.boardContent }</th>
						</tr>
						<tr>
							<th>파일</th>
							<th><c:forEach items="${announcement.atchFile.fileDetails }"
									var="fd">
									<a
										href='${pageContext.request.contextPath}/attached/announcement/${fd.attachedFileId }/${fd.attachedFileDetailId }'>${fd.originalFileName}(${fd.fileFancysize})</a>
								</c:forEach></th>
						</tr>

					</thead>
				</table>

				<form id="insertAnswer" method="post" class="col-12">
					<div
						class="datatables-customers table border-top dataTable no-footer dtr-column"
						id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info">
						<input type="hidden" id="announcementId"
							value="${announcement.boardId}">
						<textarea id="cmtContent" placeholder="댓글을 작성해주세요"></textarea>
						<div class="button-container">
							<button type="button" id="autoAdd">자동완성</button>
							<button type="button" id="btnAnswer">댓글작성</button>
						</div>
					</div>

					<div id="listJson"></div>
				</form>

				<!-- 버튼을 감싸는 컨테이너 -->
				<div class="button-container">
					<c:if test="${announcement.memberId == realUser.memberId}">
						<a
							href="<c:url value='/${groupId }/${projectId }/announcement/${boardId}/update' />"
							class="btn btn-primary">수정하기</a>
						<a id="announcementDelete"
							href="<c:url value='/${groupId }/${projectId }/announcement/${boardId}/delete' />"
							class="btn btn-danger">삭제하기</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>

	<!--/ Content -->


	<script
		src="${pageContext.request.contextPath}/resources/js/app/AnnouncementAnswer.js"></script>
	<script>
		  document.addEventListener('DOMContentLoaded', function() {
			  document.getElementById("cmtContent").addEventListener("keydown",
						function(event) {
							if (event.key === "Enter" && !event.shiftKey) {
								event.preventDefault(); // 엔터 키 기본 동작 방지

								document.getElementById("btnAnswer").click(); // 버튼 클릭 트리거
							}
						});
		    document.getElementById('announcementDelete').addEventListener('click', function(e) {
		      e.preventDefault(); // 링크 기본 동작 방지
		      Swal.fire({
		        title: '삭제 확인',
		        text: '정말로 삭제하시겠습니까?',
		        icon: 'warning',
		        showCancelButton: true,
		        confirmButtonColor: '#3085d6',
		        cancelButtonColor: '#d33',
		        confirmButtonText: '삭제',
		        cancelButtonText: '취소'
		      }).then((result) => {
		        if (result.isConfirmed) {
		          swal.fire({
		        	  icon: 'success',
						title: '삭제 완료',
						text: '공지가 삭제되었습니다.'
		          })
		          window.location.href = e.target.href; // 링크로 이동하여 삭제 처리
		        }
		      });
		    });
		  });
		  const hardcodedData = {
				    content: '이 댓글은 자동완성 기능을 통해 채워진 내용입니다.'
				};

				// 자동완성 버튼 클릭 이벤트 리스너
				document.getElementById('autoAdd').addEventListener('click', function() {
				    // 제목 입력 필드에 하드코딩된 값 채우기
				    document.getElementById('cmtContent').value = hardcodedData.content;

				});
		</script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>