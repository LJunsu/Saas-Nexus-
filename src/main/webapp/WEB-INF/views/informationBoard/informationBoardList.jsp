<%@page import="kr.or.nexus.informationBoard.vo.InformationBoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />


<style>
.datatable-container th button {
	text-align: center;
}

.datatable-container tbody td {
	text-align: center;
}

textarea::-webkit-scrollbar {
	display: none;
}

/* 도연 추가 START */
.datatable-dropdown label {
	display: flex;
	align-items: center; /* 수직 가운데 정렬 */
	justify-content: center; /* 수평 가운데 정렬 */
	gap: 5px; /* select 태그와 텍스트 사이에 약간의 간격 추가 */
	white-space: nowrap;
}

.datatable-top {
	padding-top: 13px;
}

.datatable-info {
	display: none;
}

body .datatable-container {
	line-height: 1.7;
}

table.dataTable {
	margin-bottom: 0px !important;
}
/* 도연 추가 END */
</style>


<!-- Content -->
<div class="container-xxl flex-grow-1 container-p-y"
	id="informationBoardContainer"
	data-real-mem-id="${princiapl.realUser.memberId}"
	data-group-id="${groupId}">
	<!-- 도연 추가 START -->
	<div class="d-flex justify-content-between">
		<h3 class="py-3 mb-2">정보마당 게시판</h3>
		<button type="button"
			class="btn btn-primary d-block waves-effect waves-light"
			data-bs-toggle="offcanvas" aria-pressed="false"
			style="margin: 17px 0;" aria-controls="DataTables_Table_0"
			data-bs-target="#informationInsertModal">+ 게시글 생성</button>
	</div>
	<!-- 도연 추가 END -->

	<!-- customers List Table -->
	<div class="card">
		<div class="card-datatable table-responsive">
			<div id="DataTables_Table_0_wrapper"
				class="dataTables_wrapper dt-bootstrap5 no-footer">
				<!-- 				<div class="card-header d-flex flex-wrap pb-md-2"> -->

				<!-- 					<div class="d-flex align-items-center me-5"> -->
				<!-- 						<div id="DataTables_Table_0_filter" class="dataTables_filter ms-n3"> -->

				<!-- 						</div> -->
				<!-- 					</div> -->

				<!-- 					<div class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-md-end gap-3 gap-sm-0 flex-wrap flex-sm-nowrap pt-0"> -->

				<!-- 						<div class="dt-buttons btn-group flex-wrap d-flex"> -->
				<!-- 							<button -->
				<!-- 								class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light" -->
				<!-- 								tabindex="0" aria-controls="DataTables_Table_0" type="button" -->
				<!-- 								data-bs-toggle="offcanvas" -->
				<!-- 								data-bs-target="#informationInsertModal" -->
				<!-- 							> -->
				<!-- 								<span> -->
				<!-- 									<i class="ti ti-plus me-0 me-sm-1 mb-1 ti-xs"></i> -->
				<!-- 									<span class="d-none d-sm-inline-block">게시글 생성</span> -->
				<!-- 								</span> -->
				<!-- 							</button> -->
				<!-- 						</div> -->
				<!-- 					</div> -->

				<!-- 				</div> -->

				<table
					class="datatables-customers table border-top dataTable no-footer dtr-column"
					id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info"
					style="width: 1394px;">
					<thead>

					</thead>

				</table>

				<div style="width: 1%;"></div>
			</div>
		</div>

		<!-- Offcanvas to add new customer -->
		<div class="offcanvas offcanvas-end" tabindex="-1"
			id="informationInsertModal"
			aria-labelledby="offcanvasEcommerceCustomerAddLabel">

			<div class="offcanvas-header">
				<h5 id="offcanvasEcommerceCustomerAddLabel" class="offcanvas-title">정보마당
					게시글</h5>
				<button type="button" class="btn-close text-reset"
					data-bs-dismiss="offcanvas" aria-label="Close"></button>
			</div>

			<div class="offcanvas-body mx-0 flex-grow-0">
				<form
					class="ecommerce-customer-add pt-0 fv-plugins-bootstrap5 fv-plugins-framework"
					id="informationFormInsert" action="" method="post"
					enctype="multipart/form-data">
					<div class="ecommerce-customer-add-basic mb-3">

						<div class="mb-3 fv-plugins-icon-container">
							<label class="form-label" for="ecommerce-customer-add-name">게시글
								제목</label> <input type="text" class="form-control" placeholder="게시글 제목"
								name="ifBoardTitle">
							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

						<div class="mb-3 fv-plugins-icon-container">
							<label class="form-label" for="ecommerce-customer-add-email">게시글
								내용</label>
							<!-- 							<input type="text" class="form-control" placeholder="게시글 내용" name="ifBoardContent"> -->
							<textarea rows="10" class="form-control" placeholder="게시글 내용"
								name="ifBoardContent"></textarea>
							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

						<div class="mb-3 fv-plugins-icon-container">
							<label class="form-label" for="ecommerce-customer-add-email">파일
								업로드</label>

							<!-- 							<input type="file" name="attachedFile.fileDetails" class="form-control" multiple/> -->
							<input type="file" name="attachedFile.fileDetails[0].uploadFile"
								class="form-control" />


							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

					</div>

					<div class="pt-3">
						<button type="submit"
							class="btn btn-primary me-sm-3 me-1 data-submit waves-effect waves-light">게시글
							생성</button>
						<button type="reset" class="btn btn-label-danger waves-effect"
							data-bs-dismiss="offcanvas" id="resetBtn">취소</button>

						<a class="btn" id="insertAuto">자동완성</a>
					</div>



					<!-- 이후 value에 값을 넣어줘야 함 -->
					<input type="hidden" name="groupId" value="${groupId}"> <input
						type="hidden" name="memberId"
						value="${princiapl.realUser.memberId}">
				</form>
			</div>
		</div>
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by DataTable</div>
</div>
<!--/ Content -->

<!-- Modal Detail -->
<div class="modal" id="detailModal" tabindex="-1"
	aria-labelledby="detailModalLabel" aria-hidden="true"
	style="-bs-modal-width: 45rem;">
	<div class="modal-dialog modal-xl">
		<div class="modal-content">
			<form id='detailForm' method="post">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="detailModalLabel">정보마당 게시판</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<table class="table table-bordered" id="detailModalBody">

					</table>

					<hr>

					<table class="table table-bordered" id="detailModalAnsw">

					</table>


					<input type="hidden" name="ifBoardId" value="" id="answBoardInp">
					<input type="hidden" name="groupId" value="${groupId}"> <input
						type="hidden" name="memberId"
						value="${princiapl.realUser.memberId}">
					<div style="width: 100%; display: flex;">
						<input type="text" name="ifBoardParentCmtId" class="form-control"
							value="" id="boardAnswId" placeholder="상위 댓글"
							style="width: 90px; margin: 10px 10px 0 0;"> <input
							type="text" name="ifBoardCmtContent" class="form-control"
							id="answInput" style="margin: 10px 0 0 0;">
					</div>
					<button id="informationAnswAdd" type="submit"
						class="btn btn-primary"
						style="float: right; margin: 10px 10px 10px 0">댓글 작성</button>
						<a class="btn" id="answInsertAuto" style="float: right; margin: 10px 10px 10px 0;">자동완성</a>
				</div>

				<div class="modal-footer" style="margin-top: 40px">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<div id="myBoardBtn" style="display: none;">
						<button type="button" class="btn btn-primary" id="updateBtn">수정</button>
						<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- Modal Detail -->

<!-- Modal Update -->
<div class="modal" id="updateModal" tabindex="-1"
	aria-labelledby="updateModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h1 class="modal-title fs-5" id="updateModalLabel">정보마당 게시판 수정</h1>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>

			<div class="modal-body">
				<form
					class="ecommerce-customer-add pt-0 fv-plugins-bootstrap5 fv-plugins-framework"
					id="informationFormUpdate" action="" enctype="multipart/form-data">
					<div class="ecommerce-customer-add-basic mb-3">

						<div class="mb-3 fv-plugins-icon-container">
							<input type="hidden" class="form-control" placeholder="게시글 번호"
								name="ifBoardId" id="ifBoardId" value="">
							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

						<div class="mb-3 fv-plugins-icon-container">
							<label class="form-label" for="ecommerce-customer-add-name">게시글
								제목</label> <input type="text" class="form-control" placeholder="게시글 제목"
								name="ifBoardTitle" id="ifBoardTitle">
							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

						<div class="mb-3 fv-plugins-icon-container">
							<label class="form-label" for="ecommerce-customer-add-email">게시글
								내용</label>
							<!-- 								<input type="text" class="form-control" placeholder="게시글 내용" name="ifBoardContent" id="ifBoardContent"> -->
							<textarea rows="10" class="form-control" placeholder="게시글 내용"
								name="ifBoardContent" id="ifBoardContent"></textarea>
							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

						<div class="mb-3 fv-plugins-icon-container">
							<label class="form-label" for="ecommerce-customer-add-email">파일
								업로드</label>
							<div id="fileDiv" style="margin: 15px 0"></div>
							<input type="file" name="attachedFile.fileDetails[0].uploadFile"
								class="form-control" />
							<div
								class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
						</div>

					</div>

					<!-- 이후 value에 값을 넣어줘야 함 -->
					<input type="hidden" name="groupId" value="${groupId}"> <input
						type="hidden" name="memberId"
						value="${princiapl.realUser.memberId}">

					<div class="modal-footer" style="margin-top: 40px">
						<a class="btn" id="UpdateAuto" style="float: right; margin: 10px 10px 10px 0;">자동완성</a>

						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Close</button>
						<button type="button" class="btn btn-primary" id="updateSubmitBtn">수정</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- Modal Update -->


<script
	src="${pageContext.request.contextPath}/resources/js/app/informationBoard.js"></script>
<script
	src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest"
	defer></script>