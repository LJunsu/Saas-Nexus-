<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="row" style="width: 1300px; margin: 100px auto;">
	<h3>${group.groupName} 이슈 요약</h3>

	<c:forEach var="projectMem" items="${projectMemList}">
		<!-- 프로젝트-->
		<div class="col-xl-4 col-md-6 mb-4">
			<div class="card h-100">

				<div class="card-header d-flex justify-content-between">
					<div class="card-title mb-0">
						<h5 class="mb-0"><a href="${pageContext.request.contextPath}/group/${groupId}/project/${projectMem.projectId}/issueSummary">${projectMem.projectName}</a></h5>
						<small class="text-muted">${projectMem.projectCreationDate} ~ ${projectMem.projectEndDate}</small> <br>

						<c:set var="projectId" value="${projectMem.projectId}" />
						<small class="text-muted">총 ${projectMemCountMap[projectId]} 명</small>

					</div>
				</div>

				<div class="card-body">
					<ul class="p-0 m-0">
						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-mail ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 리더</h6>

								<div class="d-flex">
									<c:forEach var="projectMember" items="${projectLeader[projectId]}">
										${projectMember.member.memberName}
									</c:forEach>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-mail ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 예산</h6>
								<div class="d-flex">
									<p class="mb-0 fw-medium">${projectMem.projectBudget} 원</p>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-mail ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 매출</h6>
								<div class="d-flex">
									<p class="mb-0 fw-medium">${projectMem.projectRevenue} 원</p>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-mail ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 진행률</h6>
								<div class="d-flex">
								    <p class="mb-0 fw-medium">${completedPercentageMap[projectId]}%</p>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<table class="table" style="width: 100%; text-align: center;">
								<tr>
									<th colspan="4" style="padding-bottom: 15px;">프로젝트 이슈 상황</th>
								</tr>

								<tr>
									<c:forEach var="issue" items="${projectIssue[projectId]}">
										<th style="width: 20%; font-size: 10px;">${issue.issueProgressStatus}</th>
									</c:forEach>
								</tr>

								<tr>
									<c:forEach var="issue" items="${projectIssue[projectId]}">
										<td>
										    ${issue.statusCount}
										</td>
									</c:forEach>
								</tr>
							</table>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<!--/ 프로젝트 -->
	</c:forEach>

</div>