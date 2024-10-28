<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />


<style>
	.google-visualization-tooltip {
	    display: none !important;
	}

	g text {
		font-size: 12px !important;
	}

	option[disabled] {
		background: rgba(75, 70, 92, 0.08);
	}
</style>

<div class="container-xxl flex-grow-1 container-p-y" id="informationBoardContainer" data-real-mem-id="${princiapl.realUser.memberId}" data-group-id="${groupId}" data-project-id="${projectId}" data-real-project-mem-id="${projectMemberInfo.projectMemIdx}">

	<div class="card" style="margin-top: 50px;">
		<div class="card-datatable table-responsive">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">


			 <div class="emails-list-header p-3 py-lg-3 py-2 ps-5 pe-4">
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center w-100">
						<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
						<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
						    <h4 class="mt-3" style="font-weight:700;">타임라인</h4>
						</div>
					</div>
					<div class="d-flex justify-content-between">
						<div id="showBtn" class="btn btn-secondary text-nowrap">통계</div>
					<div class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-md-end gap-3 gap-sm-0 flex-wrap flex-sm-nowrap pt-0 ms-2">
						<div class="dt-buttons btn-group flex-wrap text-nowrap d-flex">
							<button
								class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light"
								tabindex="0" aria-controls="DataTables_Table_0" type="button"
								data-bs-toggle="offcanvas"
								data-bs-target="#timeLineInsertModal"
								id="btnTimeLineInsertModal"
							>
								<span>
									<i class="ti ti-plus me-0 me-sm-1 mb-1 ti-xs"></i>
									<span class="d-none d-sm-inline-block">이슈 생성</span>
								</span>
							</button>
						</div>
					</div>
					</div>
				</div>
			</div>
		    <div class="no-wrap rounded d-flex align-items-center justify-content-between" style="background-color:#f5f5f5; padding:3px 12px; margin:0px 20px;">
		        <div>
		    	    <h5 class="ps-4 pt-3" style="font-weight:bold; margin-bottom:2px;">검색 및 필터</h5>
		    	    <p class="ps-4">타임라인 및 통계 데이터에 대해 검색 가능합니다</p>
		        </div>
		        <div>
		    		<form action="" method="post" id="timeLineSearchForm" style="float: left; padding-right:23px;">
		    			<select name="searchName" class="form-control" style="float: left; width: 29%; margin: 0 0 0 2%; border-top-right-radius: 0; border-bottom-right-radius: 0; border-right: none;">
		    				<option value="all">제목 및 태그</option>
		    				<option value="title">제목</option>
		    				<option value="tag">태그</option>
		    			</select>
		    			<div class="d-flex align-items-center">
		    				<input id="timeLineSearch" name="searchInp" type="search" class="form-control ms-3" placeholder="검색어를 입력해주세요." style="max-width:200px; border-top-right-radius: 0; border-bottom-right-radius: 0; border-top-left-radius: 0; border-bottom-left-radius: 0; margin-left: 0 !important;">
		    				<input type="hidden" name="projectId" value="${projectId}" class="form-control">
		    				<button type="submit" class="btn btn-primary" style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
		    			</div>
		    		</form>
		    		<form action="" method="post" id="timeLineMemForm" style="float: left; padding-right:23px;" class="d-flex justify-content-center">
		    			<input type=hidden name="projectId" value="${projectId}">
		    			<select id="chartIssueOfficerSelect" name="issueOfficer" class="form-control" style="float:left; width: 90%; border-top-right-radius: 0; border-bottom-right-radius: 0;">
		    				<option value="">선택</option>
		    				<c:forEach var="projectMem" items="${projectMemberList}">
		    					<option value="${projectMem.projectMemIdx}">[${projectMem.projectRole[0].projectRoleName}] ${projectMem.member.memberName}</option>
		    				</c:forEach>
		    			</select>
		    			<button id="memSubmitBtn" type="submit" class="btn btn-primary" style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">조회</button>
		    		</form>
		    		<button id="resetBtn" class="btn btn-secondary me-3">검색 및 필터 초기화</button>
		    	</div>
             </div>

	<div id="showSummaryDiv" style="
		display: flex;
		margin-top: 20px;
		height: 0;
	  	list-style: none;
	  	overflow: hidden;
	  	transition: height ease-out 0.5s 0s;
	  	padding-bottom: 10px;
  	">
		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">
				<div class="card-body">
					<canvas id="dateChart" style="margin-top: 60px;"></canvas>

					<div class="card-title mb-0">
						<table id="chartTable" style="width: 90%; text-align: center; margin: 5%;">

						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">

				<div class="card-header d-flex justify-content-between" style=" padding-bottom: 0;">
					<div class="card-title mb-0">
						<h5 class="mb-0">우선 순위 별 이슈</h5>
					</div>
				</div>

				<div class="card-body">
					<canvas id="priorityChart" style="width: 300px; margin: 0 auto;" ></canvas>

					<div class="card-title mb-0">
						<table id="priorityChartTable" style="width: 90%; text-align: center; margin: 5%;">

						</table>
					</div>
				</div>
			</div>
		</div>

		<div class="col-12 col-xl-4 col-md-6">
			<div class="card h-100">

				<div class="card-header d-flex justify-content-between" style=" padding-bottom: 0;">
					<div class="card-title mb-0">
						<h5 class="mb-0">상태 별 이슈</h5>
					</div>
				</div>

				<div class="card-body">
					<canvas id="statusChart" style="width: 300px; margin: 0 auto;"></canvas>

					<div class="card-title mb-0">
						<table id="statusChartTable" style="width: 90%; text-align: center; margin: 5%;">

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

				<div style="display: flex;">
					<div style="width: 40px; height: 20px; background: #5e97f6; margin-left: 20px; border-radius: 5px;"></div> <span style="line-height: 20px; margin-left: 5px;">준비중</span>
					<div style="width: 40px; height: 20px; display: flex;  margin-left: 20px;">
						<div style="width: 20px; height: 20px; background: #2a56c6; border-radius: 5px 0 0 5px;"></div>
						<div style="width: 20px; height: 20px; background: #5e97f6; border-radius: 0 5px 5px 0;"></div>
					</div> <span style="line-height: 20px; margin-left: 5px;">진행중</span>
					<div style="width: 40px; height: 20px; background: #2a56c6; margin-left: 20px; border-radius: 5px;"></div> <span style="line-height: 20px; margin-left: 5px;">완료됨</span>
				</div>


				<!-- 타임라인 시작 -->
				<div id="chart_div" style="overflow-y: auto; overflow-x: auto; clear:left; margin-top: 20px;"></div>
				<div id="toolbar_div" style="display: none;"></div>
				<!-- 타임라인 끝 -->



				<div style="width: 1%;"></div>
			</div>
		</div>
	</div>

	<!-- 타임라인 추가 모달 -->
	<div class="offcanvas offcanvas-end" tabindex="-1" id="timeLineInsertModal" aria-labelledby="offcanvasEcommerceCustomerAddLabel">

		<div class="offcanvas-header">
			<h5 id="offcanvasEcommerceCustomerAddLabel" class="offcanvas-title">타임라인</h5>
			<button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
		</div>

		<div class="offcanvas-body mx-0 flex-grow-0">
			<form class="ecommerce-customer-add pt-0 fv-plugins-bootstrap5 fv-plugins-framework" id="timeLineInsert" action="" method="post">
				<div class="ecommerce-customer-add-basic mb-3">

					<div class="mb-3 fv-plugins-icon-container">
<!-- 						<label class="form-label" for="ecommerce-customer-add-name">이슈 번호</label> -->
						<input type="hidden" class="form-control" placeholder="이슈 번호" name="issueIdx" value="${issues.issueIdx}" id="issueIdxInp" readonly>
<!-- 						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div> -->
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 제목</label>
						<input type="text" class="form-control" placeholder="이슈 제목" name="issueTitle" id="issueTitle">
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 내용</label>
<!-- 						<input type="text" class="form-control" placeholder="이슈 내용" name="issueContent" id="issueContent"> -->
						<textarea rows="10" class="form-control" placeholder="이슈 내용" name="issueContent" id="issueContent"></textarea>

						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 담당자</label>
						<select name="issueOfficer" class="form-control" id="issueOfficer">
							<option value="">선택</option>
							<c:forEach var="projectMem" items="${projectMemberList}">
								<option value="${projectMem.projectMemIdx}">[${projectMem.projectRole[0].projectRoleName}] ${projectMem.member.memberName}</option>
							</c:forEach>
						</select>
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 진행 상태</label>
						<select name="issueProgressStatus" class="form-control" id="issueProgressStatus" disabled="true">
							<option value="Not Started">준비중</option>
							<option value="In Progress">진행중</option>
							<option value="Completed">완료됨</option>
						</select>
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 우선 순위</label>
						<select name="issuePriorityName" class="form-control" id="issuePriorityName">
							<option value="">선택</option>
							<option value="Highest">매우높음</option>
							<option value="High">높음</option>
							<option value="Medium">중간</option>
							<option value="Low">낮음</option>
							<option value="Lowest">매우낮음</option>
						</select>
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 태그</label>
<!-- 						<input type="text" class="form-control" placeholder="이슈 태그" name="issueTags[0].issueTagName" id="issueTagName"> -->

						<select name="issueTags[0].issueTagName" class="form-control" id="issueTagName">
							<option value="urgent">긴급</option>
							<option value="bug">버그</option>
							<option value="feature">기능 추가</option>
							<option value="enhancement">기능 개선</option>
							<option value="performance">성능</option>
							<option value="security">보안</option>
							<option value="ui/ux">UI/UX</option>
							<option value="documentation">문서화</option>
							<option value="testing">테스트</option>
							<option value="refactoring">리팩토링</option>
							<option value="maintenance">유지보수</option>
							<option value="technical-debt">기술 부채</option>
							<option value="support">지원</option>
							<option value="training">교육</option>
							<option value="deployment">배포</option>
							<option value="integration">통합</option>
							<option value="bugfix">버그 수정</option>
							<option value="client-request">클라이언트 요청</option>
						</select>

						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 시작일</label>
						<input type="date" class="form-control" placeholder="이슈 시작일" name="issueDate" id="issueDate">
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">이슈 마감일</label>
						<input type="date" class="form-control" placeholder="이슈 마감일" name="issueDeadline" id="issueDeadline">
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

					<div class="mb-3 fv-plugins-icon-container">
						<label class="form-label" for="ecommerce-customer-add-name">상위 이슈</label>
						<select name="subIssueIdx" class="form-control" id="subIssueIdx">
							<option value="">선택</option>
							<c:forEach var="issues" items="${issueList}">
								<option value="${issues.issueIdx}">[${issues.issueIdx}] ${issues.issueTitle}</option>
							</c:forEach>
						</select>
						<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
					</div>

				</div>



				<div class="pt-3" id="timeLineInsertModalBtn" data-project-mem-idx="${projectMemIdx}">
					<button type="submit" class="btn btn-primary me-sm-3 me-1 data-submit waves-effect waves-light">저장</button>
					<button type="reset" class="btn btn-label-danger waves-effect" data-bs-dismiss="offcanvas" id="modalResetBtn">취소</button>

					<a class="btn" id="insertAuto">생성</a>
					<a class="btn" id="updateAuto">수정</a>
				</div>



				<!-- 이후 value에 값을 넣어줘야 함 -->
				<input type="hidden" name="projectId" value="${projectId}">
				<input type="hidden" name="projectMemIdx" value="${projectMemberInfo.projectMemIdx}">
			</form>
		</div>
	</div>
	<!-- 타임라인 추가 모달 끝 -->
	<div class="d-flex pt-2 justify-content-end">Powered by chartJS, googleChart</div>
</div>



<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<link href="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.snow.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/timeLine.js"></script>