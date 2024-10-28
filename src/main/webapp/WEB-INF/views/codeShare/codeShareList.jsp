<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />



<div id="mainDiv" class="container-xxl flex-grow-1 container-p-y" data-group-id="${groupId}" data-project-id="${projectId}" data-real-mem-id="${princiapl.realUser.memberId}">
	<div class="app-email card">
		<div class="row g-0">
			<!-- Emails List -->
			<div class="col app-emails-list">
				<div class="shadow-none border-0">
<%--  					<div class="emails-list-header p-3 py-lg-3 py-2">
						<!-- Email List: Search -->
						<div class="d-flex justify-content-between align-items-center">
							<div class="d-flex align-items-center w-100">
								<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
								<div class="mb-0 mb-lg-2 w-100">
									<div class="input-group input-group-merge shadow-none">

										<form:form method="get">
											<div class="d-flex align-items-center">
												<input name="search" type="search" class="form-control ms-3" placeholder="제목을 입력해주세요." style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; border: 1px solid #c9c8ce;">
												<button type="submit" class="btn btn-primary" style="height: 40px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
											</div>
										</form:form>
									</div>
								</div>
							</div>
							<div class="d-flex align-items-center mb-0 mb-md-2">
								<div class="dropdown d-flex align-self-center" style="width: 300px; justify-content: flex-end;">
									<div class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light" type="button" id="codeShareCreateBtn" style="width: 80px;">
										생성
									</div>
								</div>
							</div>
						</div>
					</div> --%>
			<div class="emails-list-header p-3 py-lg-3 py-2 ps-5 pe-4">
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center w-100">
						<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
						<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
						    <h4 class="mt-3" style="font-weight:700;">코드쉐어</h4>
							<div class="input-group input-group-merge shadow-none ps-1">
								<form:form method="get">
									<div class="d-flex align-items-center">
										<input name="search" type="search" class="form-control ms-3" placeholder="방 제목을 입력해주세요." style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; border: 1px solid #c9c8ce;">
										<button type="submit" class="btn btn-primary" style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>
					<div class="d-flex align-items-center mb-0">
						<div class="dropdown d-flex align-self-center text-nowrap" style="width: 300px; justify-content: flex-end;">
							<div class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light" type="button" id="codeShareCreateBtn" style="width: auto; height:40.5px;">
								+ 방 생성하기
							</div>
						</div>
					</div>
				</div>
			</div>

					<hr class="container-m-nx m-0" />

					<!-- Email List: Items -->
					<div class="email-list pt-0">

						<ul class="list-unstyled m-0" style="height: 100%; overflow-y: auto;">
							<li class="email-list-item email-marked-read">
								<div class="d-flex align-items-center">
									<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="width: 100%; display: flex;">
										<div style="float: left; width: 10%; text-align: center; margin: 0;" class="h6 email-list-item-username me-2">코드쉐어 번호</div>
										<div style="float: left; width: 70%; text-align: center; margin: 0;" class="email-list-item-subject d-xl-inline-block d-block" style="margin-left: 30px;">코드쉐어 제목</div>
										<div style="float: left; width: 10%; text-align: center; margin: 0;" class="email-list-item-subject d-xl-inline-block d-block" style="margin-right: 115px;">생성자</div>
										<div style="float: left; width: 10%; text-align: center; margin: 0;" class="email-list-item-subject d-xl-inline-block d-block">생성일</div>
									</div>
								</div>
							</li>
							<c:forEach var="codeShare" items="${codeShareList}">
								<a href="${pageContext.request.contextPath}/group/${groupId}/project/${projectId}/codeShare/${codeShare.codeShareId}" class="csiATag" data-csi="${codeShare.codeShareId}">
									<li class="email-list-item email-marked-read"">
										<div class="d-flex align-items-center">
											<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="width: 100%; display: flex;">
												<div style="float: left; width: 10%; text-align: center; margin: 0; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="h6 email-list-item-username me-2">${codeShare.codeShareId}</div>
												<div style="float: left; width: 70%; text-align: center; margin: 0; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; max-width: 930px;" class="email-list-item-subject d-xl-inline-block d-block">
													${codeShare.codeShareTitle}
												</div>
												<div style="float: left; width: 10%; text-align: center; margin: 0; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="email-list-item-subject d-xl-inline-block d-block" style="margin-right: 65px;">${codeShare.memberId}</div>
												<div style="float: left; width: 10%; text-align: center; margin: 0; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;" class="email-list-item-subject d-xl-inline-block d-block">${codeShare.codeShareDate}</div>
											</div>
										</div>
									</li>
								</a>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<!-- /Emails List -->
		</div>
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by ACE editor, JavaCompiler</div>
</div>


<script>
	const contextPath = document.body.dataset.contextPath;
	const baseURI = contextPath + "/rest/codeShare";
	const groupId = document.querySelector("#mainDiv").dataset.groupId;
	const projectId = document.querySelector("#mainDiv").dataset.projectId;
	const memberId = document.querySelector("#mainDiv").dataset.realMemId;

	document.addEventListener("DOMContentLoaded", async ()=>{
		document.querySelector("#codeShareCreateBtn").addEventListener("click", codeShareCreate);
	});

	const codeShareCreate = async () => {

		let codeShareVO = {
			groupId : groupId,
			projectId : projectId,
			memberId : memberId
		}

		var newCodeShare = await FetchUtils.fetchForJSON(baseURI + "/create", {
			method : "post",
			headers : {
				"Content-Type" : "application/json",
				"Accept" : "application/json"
			},
			body : JSON.stringify(codeShareVO)
		});

		var detail = newCodeShare.newCodeDetail;
		var newCodeShareId = detail.codeShareId;

		location.href = contextPath + "/group/" + groupId + "/project/" + projectId + "/codeShare/" + newCodeShareId;
	}
</script>