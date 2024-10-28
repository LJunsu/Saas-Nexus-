<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="row" style="width: 1300px; margin: 100px auto;" id="issueSummartyMainDiv" data-project-id="${projectId}">
	<h3>${projectName} 이슈 요약</h3>

	<div class="col-xl-12 col-md-6 mb-4">
		<span style="float: left;">
			<input type="date" id="startDateInp" class="form-control">
		</span>
		<span style="float: left;"> <h3> ~ </h3> </span>
		<span style="float: left;">
			<input type="date" id="endDateInp" class="form-control">
		</span>
	</div>

	<div class="col-xl-12 col-md-6 mb-4">
		<div class="card h-100">

			<div class="card-header d-flex justify-content-between">
				<div class="card-title mb-0">
					<h5 class="mb-0">담당자 별 이슈</h5>
				</div>
			</div>

			<div class="card-body">
				<canvas id="dateChart"></canvas>
			</div>
		</div>
	</div>

	<div class="col-xl-12 col-md-6 mb-4">
		<div class="card h-100">
			<select class="form-control" id="projectMemSelect">
				<option value="전체">전체</option>
				<c:forEach var="projectMem" items="${projectMemList}">
					<option value="${projectMem.projectMemIdx}">${projectMem.projectMemIdx} [${projectMem.projectRole[0].projectRoleName}] ${projectMem.member.memberName}</option>
				</c:forEach>
			</select>
		</div>
	</div>

	<div class="col-xl-6 col-md-6 mb-4">
		<div class="card h-100">

			<div class="card-header d-flex justify-content-between">
				<div class="card-title mb-0">
					<h5 class="mb-0">우선 순위 별 이슈</h5>
				</div>
			</div>

			<div class="card-body">
				<canvas id="priorityChart" style="width: 500px; margin: 0 auto;"></canvas>
			</div>
		</div>
	</div>

	<div class="col-xl-6 col-md-6 mb-4">
		<div class="card h-100">

			<div class="card-header d-flex justify-content-between">
				<div class="card-title mb-0">
					<h5 class="mb-0">상태 별 이슈</h5>
				</div>
			</div>

			<div class="card-body">
				<canvas id="statusChart" style="width: 500px; margin: 0 auto;"></canvas>
			</div>
		</div>
	</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/issueSummary.js"></script>