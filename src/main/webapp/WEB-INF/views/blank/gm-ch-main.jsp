<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<style>
	#groupNotiTable tbody {
		display: block;
		width: 100%;
	}

	#groupNotiTable tr {
		display: block;
	}

	#groupNotiTable td {
	    white-space: nowrap;
    	overflow: hidden;
    	text-overflow: ellipsis;
    	display: block;
    	float: left;
    	line-height: 48px;
	}

	#projectNotiTable tbody {
		display: block;
		width: 100%;
	}

	#projectNotiTable tr {
		display: block;
	}

	#projectNotiTable td {
	    white-space: nowrap;
    	overflow: hidden;
    	text-overflow: ellipsis;
    	display: block;
    	float: left;
    	line-height: 48px;
	}

	#issueLogTable tbody {
		display: block;
		width: 100%;
	}

	#issueLogTable tr {
		display: block;
	}

	#issueLogTable td {
	    white-space: nowrap;
    	overflow: hidden;
    	text-overflow: ellipsis;
    	display: block;
    	float: left;
    	line-height: 48px;
	}
</style>


<div class="container-xxl flex-grow-1 container-p-y">
	<!-- Hour chart  -->
	<div class="card bg-transparent shadow-none border-0" style="margin-top: 60px;">
		<div class="card-body row p-0 pb-3">
			<h3 style="font-weight:700;">안녕하세요, ${realUser.memberName}님 👋🏻</h3>

			<div class="col-12 col-lg-7">
				<p>여기에서 현재 업무 진행상태를 전체적으로 확인할 수 있습니다.</p>
			</div>
		</div>
	</div>
	<!-- Hour chart End  -->

	<!-- Topic and Instructors -->
	<div class="row mb-4 g-4">
		<div class="col-12 col-xl-8">
			<div class="card h-100">
				<div class="card-header d-flex align-items-center justify-content-between" style="padding-bottom:10px; padding-top:35px;">
					<h4 class=" m-0 me-2 ms-2" style="font-weight:700;">그룹 공지</h4>
				</div>

				<div class="card-body row g-3" style="height: 200px;">
					<div class="col-md-12">
						<table style="display: block; width: 100%;" id="groupNotiTable">
							<tr style="width: 100%; height: 50px; border-bottom: 1.5px solid #c7c7c7;">
								<td style="width: 15%; text-align: center;">순번</td>
								<td style="width: 40%; text-align: center;">제목</td>
								<td style="width: 20%; text-align: center;">작성자</td>
								<td style="width: 25%; text-align: center;">생성일</td>
							</tr>
							<c:forEach var="Gnotice" items="${GnoticeList}" begin="0" end="2" varStatus="status">
								<tr style="width: 100%; height: 50px; border-bottom: 1px solid #c7c7c7;">
									<td style="width: 15%; text-align: center; font-weight:600;"> ${status.index+1}</td>
									<td style="width: 40%; font-weight:600;"><a href="${cPath}/group/${groupId}/noticeBoardUI">${Gnotice.boardTitle}</a></td>
									<td style="width: 20%; text-align: center; font-weight:600;">${Gnotice.managementVO.memberName}</td>
									<td style="width: 25%; text-align: center; font-weight:600;">${Gnotice.boardCreateDate}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>



				<div class="card-header d-flex align-items-center justify-content-between" style="padding-bottom:10px; padding-top:20px;">
					<h4 class=" m-0 me-2 ms-2" style="font-weight:700;">프로젝트 공지</h4>
				</div>

				<div class="card-body row g-3" style="height: 200px;">
					<div class="col-md-12">
						<table style="display: block; width: 100%;" id="projectNotiTable">
							<tr style="width: 100%; height: 50px; border-bottom: 1.5px solid #c7c7c7;">
								<td style="width: 15%; text-align: center;">순번</td>
								<td style="width: 40%; text-align: center;">제목</td>
								<td style="width: 20%; text-align: center;">작성자</td>
								<td style="width: 25%; text-align: center;">생성일</td>
							</tr>
							<c:forEach var="Gnotice" items="${AnnouncementList}" begin="0" end="2" varStatus="status">
								<tr style="width: 100%; height: 50px; border-bottom: 1px solid #c7c7c7;">
									<td style="width: 15%; text-align: center; font-weight:600;"> ${status.index+1}</td>
									<td style="width: 40%; font-weight:600;"><a href="${cPath}/${groupId}/${projectId}/announcement/${Gnotice.boardId}">${Gnotice.boardTitle}</a></td>
									<td style="width: 20%; text-align: center; font-weight:600;">${Gnotice.managementVO.memberName}</td>
									<td style="width: 25%; text-align: center; font-weight:600;">${Gnotice.boardCreateDate}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">
				<div class="card-header d-flex justify-content-between">
					<div class="card-title mb-0 w-100">
						<div style="display:flex; justify-content:space-between; padding-top:10px;">
							<h5 class="mb-2"  style="font-weight:700;">${projectMem.projectName}</h5>
							<span class="">총 ${projectMemCount}명</span>
						</div>
						<span>${projectMem.projectCreationDate} ~
						<c:choose>
						    <c:when test="${projectMem.projectEndDate==null}">
						      마감기한 없음
						    </c:when>
						    <c:otherwise>
						      ${projectMem.projectEndDate}
						    </c:otherwise>
						</c:choose>
						</span>
						<c:set var="projectId" value="${projectMem.projectId}" />
					</div>
				</div>

				<div class="card-body" id="issueSummartyMainDiv" data-project-id="${projectId}">
					<ul class="p-0 m-0">
						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-users"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 리더</h6>
								<div class="d-flex">
									<c:forEach var="projectMember" items="${projectLeader}" varStatus="status">
										${projectMember.member.memberName}
										<c:if test="${!status.last}">,</c:if>
									</c:forEach>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-wallet ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 예산</h6>
								<div class="d-flex">
									<p class="mb-0 fw-medium"><fmt:formatNumber value="${projectMem.projectBudget}" type="number" /> 원</p>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-currency-dollar ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 매출</h6>
								<div class="d-flex">
									<p class="mb-0 fw-medium"><fmt:formatNumber value="${projectMem.projectRevenue}" type="number" /> 원</p>
								</div>
							</div>
						</li>

						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<div class="badge bg-label-success rounded p-2">
								<i class="ti ti-discount-check ti-sm"></i>
							</div>

							<div class="d-flex justify-content-between w-100 flex-wrap">
								<h6 class="mb-0 ms-3">프로젝트 진행률</h6>
								<div class="d-flex">
								    <p class="mb-0 fw-medium">${completedPercentage}%</p>
								</div>
							</div>
						</li>
						<li class="mb-4 pb-1 d-flex justify-content-between align-items-center">
							<table class="table" style="text-align: center;">
								<tr>
									<th colspan="3" style="padding-bottom: 30px; font-size:19px !important; font-weight:700 !important;">프로젝트 이슈 현황</th>
								</tr>

								<tr>
									<c:forEach var="issue" items="${projectIssue}">
										<c:choose>
											<c:when test="${issue.issueProgressStatus eq 'Completed'}">
												<th style="font-size: 17px !important;">
													완료됨
												</th>
											</c:when>

											<c:when test="${issue.issueProgressStatus eq 'In Progress'}">
												<th style="font-size: 17px !important;">
													진행중
												</th>
											</c:when>

											<c:otherwise>
												<th style="font-size: 17px !important;">
													준비중
												</th>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</tr>

								<tr style="line-height:35px;">
									<c:forEach var="issue" items="${projectIssue}">
										<td style="font-size: 17px;">
										    ${issue.statusCount}개
										</td>
									</c:forEach>
								</tr>
							</table>
						</li>
					</ul>
				</div>
			</div>
		</div>


	<div class="card bg-transparent shadow-none border-0" style="margin-top: 60px;">
		<div class="card-body row p-0 ">
		<h4 style="font-size:27px; font-weight:700; margin-bottom:5px;">프로젝트 통계 📈</h4>
			<div class="col-12 col-lg-7">
				<p>전체적인 프로젝트 통계현황을 확인할 수 있습니다.</p>
			</div>
		</div>
	</div>


		<div style="margin-top:3px;">
			<span style="float: left;">
				<input type="date" id="startDateInp" class="form-control">
			</span>
			<span style="float: left;"> <h3> ~ </h3> </span>
			<span style="float: left;">
				<input type="date" id="endDateInp" class="form-control">
			</span>

			<select class="form-control" id="projectMemSelect" style="float: left; width: 20%; margin-left: 15px;">
				<option value="전체">담당자를 선택해주세요</option>
				<c:forEach var="projectMem" items="${projectMemList}">
					<option value="${projectMem.projectMemIdx}">${projectMem.projectMemIdx} [${projectMem.projectRole[0].projectRoleName}] ${projectMem.member.memberName}</option>
				</c:forEach>
			</select>
		</div>

		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">

				<div class="card-header d-flex justify-content-between">
					<div class="card-title mb-0">
						<h5 class="mb-0" style="font-weight:700;">담당자별 이슈</h5>
					</div>
				</div>

				<div class="card-body">
					<canvas id="dateChart"></canvas>
					<!--  -->
					<div class="card-title mb-0">
						<table id="chartTable" style="margin-top:20px; width: 100%; text-align: center;">

						</table>
					</div>
					<!--  -->
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">

				<div class="card-header d-flex justify-content-between">
					<div class="card-title mb-0">
						<h5 class="mb-0" style="font-weight:700;">우선 순위별 이슈</h5>
					</div>
				</div>

				<div class="card-body">
					<canvas id="priorityChart" style="width: 350px; margin: 0 auto;"></canvas>

					<!--  -->
					<div class="card-title mb-0" style="margin-top: 0px;">
						<table id="priorityChartTable" style="width: 100%; text-align: center;">

						</table>
					</div>
					<!--  -->
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">

				<div class="card-header d-flex justify-content-between">
					<div class="card-title mb-0">
						<h5 class="mb-0" style="font-weight:700;">상태별 이슈</h5>
					</div>
				</div>

				<div class="card-body">
					<canvas id="statusChart" style="width: 350px; margin: 0 auto;"></canvas>

					<!--  -->
					<div class="card-title mb-0" style="margin-top: 0px;">
						<table id="statusChartTable" style="width: 100%; text-align: center;">

						</table>
					</div>
					<!--  -->
				</div>
			</div>
		</div>
		<div class="d-flex pt-2 justify-content-end" style="margin-top: 10px;">Powered by chartJS</div>
	</div>
	<!--  Topic and Instructors  End-->

	<div class="col-12 col-xl-12">
		<div class="card h-100">
			<div class="card-header d-flex justify-content-between" style="padding-bottom:10px; padding-top:30px;">
				<div class="card-title mb-0">
					<h4 class="mb-0 me-2 ms-2" style="font-weight:700;">이슈 로그</h4>
				</div>
			</div>

			<div class="card-body row g-3">
				<div class="col-md-12">
					<table id="issueLogTable" style="width: 100%;">
						<tr style="height: 50px; border-bottom: 1.5px solid #c7c7c7;">
						        <td style="width: 5%; text-align: center;">순번</td>
								<td style="width: 12%; text-align: center;">이슈</td>
								<td style="width: 67%; text-align: center;">내용</td>
								<td style="width: 8%; text-align: center;">생성자</td>
								<td style="width: 8%; text-align: center;">생성일</td>
						</tr>
						<c:forEach var="alarm" items="${alarmList}" begin="0" end="3" varStatus="status">
							<tr style="height: 50px; border-bottom: 1px solid #c7c7c7;">
							    <td style="width: 5%; text-align: center; font-weight:600;"> ${status.index+1}</td>
								<td style="width: 12%; text-align: center;"><a href=${alarm.urldata}>${alarm.issueIdx}</a></td>
								<td style="width: 67%; max-width:900px;">${alarm.data}</td>
								<td style="width: 8%; text-align: center;">${alarm.alarmSenderN}</td>
<%-- 								<td style="width: 15%; text-align: center;">${alarm.eventdate}</td> --%>
								<td style="width: 8%; text-align: center;">${timeSinceList[status.index]}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/issueSummary.js"></script>