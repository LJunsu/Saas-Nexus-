<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
/* Modal 스타일 */
.preModal {
	display: none; /* 기본적으로 숨김 */
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	padding-top: 60px;
}

.modal-content {
	background-color: #fefefe;
	margin: 5% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	max-width: 900px;
	position: relative;
}

.close-btn {
	position: absolute;
	top: 10px;
	right: 25px;
	color: #000;
	font-size: 35px;
	font-weight: bold;
	cursor: pointer;
}

.close-btn:hover, .close-btn:focus {
	color: red;
	text-decoration: none;
	cursor: pointer;
}

.download-btn {
	background-color: #4CAF50; /* Green */
	color: white;
	border: none;
	padding: 10px 20px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin-top: 10px;
	cursor: pointer;
}

.download-btn:hover {
	background-color: #45a049;
}

.preview-img, .preview-video, .preview-pdf {
	max-width: 100%;
	max-height: 500px;
	display: block;
	margin: auto;
}

.selected {
    background-color: #d3d3d3; /* 원하는 색상으로 설정 */
}

.toggleTr {
	display: none;
}
</style>
<input type="hidden" id="projectId" value="${projectId }">
<input type="hidden" id="userRole" value="${user.projectRoleVO.projectRoleName }">
<div class="container-xxl flex-grow-1 container-p-y" style="margin-top:50px;">
	<div class="card">
		<div class="row g-0">
		<div class="emails-list-header p-3 py-lg-3 py-2 ps-5 pe-4">
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center w-100">
						<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
						<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
						    <h4 class="mt-3" style="font-weight:700;">공유 클라우드</h4>
							<div class="shadow-none ps-1">
								<form:form method="get">
									<div class="d-flex align-items-center">
										<input id="searchName" name="search" type="search" class="form-control ms-3" placeholder="파일명을 입력해주세요." style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; border: 1px solid #c9c8ce;">
										<button id="searchBtn" type="button" class="btn btn-primary" style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>
					<div id="tgl">
						<div class="d-flex align-items-center mb-0" id="addBtn">
							<div class="dropdown d-flex align-self-center text-nowrap" style="width: 300px; justify-content: flex-end;">
								<div class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light dropdown-toggle hide-arrow" type="button" data-bs-toggle="dropdown" aria-expanded="false" style="width: auto; height:40.5px;">
									+ 파일 및 폴더 추가하기
								</div>
								<div class="dropdown-menu" style="">
		  							<a id="folderInsert" class="dropdown-item waves-effect" href="javascript:void(0);">폴더</a>
		  							<a id="fileInsert" class="dropdown-item waves-effect" href="javascript:void(0);">파일</a>
		  							<input type="file" id="fileInput" name="file" style="display: none;">
		  						</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
			<div class="d-flex align-items-center justify-content-center" style="margin: 0px 0px 30px 0px; padding:0px 30px; max-height:900px; overflow: auto;">
				<table class="table table-hover" style="width: 100%">
					<thead>
						<tr>
							<th>이름</th>
							<th>파일 유형</th>
							<th>수정일</th>
							<th>파일크기</th>
							<th colspan="2">업로더</th>
						</tr>
					</thead>
					<tbody id="cloudBody">
					</tbody>
				</table>
			</div>
			<!-- <div class="d-flex align-items-center justify-content-center"
				style="margin: 50px 0px 30px 0px;">
				<div>
					<div align="center">
						<h4>Nexus 드라이브</h4>
						<input id="searchName" type="search" class="form-control" placeholder="검색 press enter">
					</div>
					<table class="table table-hover" style="width: 1000px">
						<thead>
							<tr>
								<th>이름</th>
								<th>파일 유형</th>
								<th>수정일</th>
								<th>파일크기</th>
								<th colspan="2">업로더</th>
							</tr>
						</thead>
						<tbody id="cloudBody">
						</tbody>
					</table>
				<div class="d-flex pt-2 justify-content-end">Powered by Google Cloud Storage API</div>
				</div>
			</div> -->
		</div>
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by Google Cloud Storage API</div>
</div>

<div class="modal fade" id="folderModal" tabindex="-1"
	aria-labelledby="folderModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="folderModalLabel">새 폴더 생성</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<input type="text" id="folderNameInput" class="form-control"
					placeholder="폴더명을 입력하세요">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
					data-bs-dismiss="modal">취소</button>
				<button type="button" class="btn btn-primary" id="createFolderBtn">생성</button>
			</div>
		</div>
	</div>
</div>

<!-- Preview Modal -->
    <div id="preview-modal" class="modal preModal">
        <div class="modal-content">
            <span id="close-btn" class="close-btn">&times;</span>
            <div id="modal-body">
                <!-- 미리보기 내용이 여기로 삽입됩니다 -->
            </div>
            <button id="download-btn" class="download-btn">Download</button>
        </div>
    </div>
    
    <!-- Access Modal -->
    <div id="access-modal" class="modal acsModal">
        <div class="modal-content">
            <span id="close-acs" class="close-btn">&times;</span>
            <div id="modal-body">
                <h4>접근 권한 부여</h4>
                <table>
                	<c:forEach items="${roles }" var="role">
                	<c:if test="${role.projectRoleName != '프로젝트 리더' }">
                	<tr>
                		<td><input type="checkbox" class="role-checkbox"></td>
                		<input type="hidden" class="role-idx" value="${role.projectRoleIdx }">
                		<td>${role.projectRoleName }</td>
                	</tr>
                	</c:if>
                	</c:forEach>
                </table>
                <br>
                <button id="access-btn" class="btn btn-primary">확인</button>            
            </div>
        </div>
    </div>

<script src="${cPath }/resources/js/app/cloud/cloudUI.js"></script>