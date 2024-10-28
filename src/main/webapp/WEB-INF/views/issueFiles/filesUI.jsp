<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.folder-group {
    margin-bottom: 20px;
    border: 1px solid #ddd;
    padding: 10px;
}
.folder-group h4 { 
    padding: 5px 5px 0px 15px; 
    margin-top: 0; 
    margin-bottom : 5px !important;
} 
.file-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}
</style>
<input id="groupId" type="hidden" value="${groupId }">
<input id="projectId" type="hidden" value="${projectId }">
<div class="container-xxl flex-grow-1 container-p-y" style="margin-top:55px;">
	<div class="app-chat card overflow-hidden" style="max-height: 900px; overflow: auto;">
		<div class="row g-0" style="overflow: auto; max-height: 100%;">
<!-- 			<div class="d-flex align-items-center justify-content-center" style="margin: 50px 0px 30px 0px;">
				<div>
					<div align="center">
						<h4>프로젝트 파일</h4>
						<input id="searchName" type="search" class="form-control"
							placeholder="검색 press enter">
					</div>
				</div>
			</div> -->
			<div class="emails-list-header p-3 py-lg-3 py-2 ps-5 pe-4">
				 <div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center w-100">
						<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
						<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
						    <h4 class="mt-3" style="font-weight:700;">프로젝트 파일 공유</h4>
							<div class="input-group input-group-merge shadow-none ps-1">
								<form:form method="get">
									<div class="d-flex align-items-center">
										<input id="searchName" name="search" type="search" class="form-control ms-3" placeholder="파일명, 이슈명을 입력해주세요." style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; border: 1px solid #c9c8ce;">
										<button id="searchBtn" type="button" class="btn btn-primary" style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="card-body" style="padding-top:0px;">
				<c:forEach items="${groupedIssueFiles}" var="entry">
					<div class="folder-group" style="background-color:#7367f0; border-radius:16px;">
						<c:if test="${not empty entry.value}">
		                	<c:set var="firstIssueFile" value="${entry.value[0]}" />
		                	<h4><a href="${cPath}/issueAdapter?url=/issue/${groupId }/${projectId}/issueMain&last=${firstIssueFile.folderPath}" style="color:#ffffff;">${entry.key}</a></h4>
						</c:if>
						<div class="file-list">
							<c:forEach items="${entry.value}" var="issueFile">
								<div
									class="dz-preview dz-processing dz-image-preview dz-success dz-complete"
									style="border: none; ">
									<div class="dz-details">
										<div class="dz-thumbnail">
											<img data-dz-thumbnail="" alt="${issueFile.fileName}"
												src="https://storage.googleapis.com/java_bucket_hstest1/${issueFile.folderPath}/${issueFile.fileName}">
											<span class="dz-nopreview">No preview</span>
											<div class="dz-error-mark"></div>
											<div class="dz-error-message">
												<span data-dz-errormessage=""></span>
											</div>
											<div class="progress">
												<div class="progress-bar progress-bar-primary"
													role="progressbar" aria-valuemin="0" aria-valuemax="100"
													data-dz-uploadprogress="" style="width: 100%;"></div>
											</div>
										</div>
										<div class="dz-filename" data-dz-name="">${issueFile.fileName}</div>
										<div class="dz-size" data-dz-size="">
											<strong>${issueFile.formatSize }</strong>
										</div>
									</div>
									<a class="dz-remove" href="javascript::"
										data-dz-download="${issueFile.folderPath}/${issueFile.fileName}">다운로드</a>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>
<script src='${cPath }/resources/js/app/issueFiles/filesUI.js'></script>