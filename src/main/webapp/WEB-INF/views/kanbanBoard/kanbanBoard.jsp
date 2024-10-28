<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}"
	var="userName" />
<!--  MEMBER이름 -->
<c:set value="${authentication.principal.realUser.memberId}"
	var="userId" />
<!--  MEMBER이름 -->

<style>
.kanban-wrapper::-webkit-scrollbar {
	display: none;
}
</style>



<script
	src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/select2/select2.js"></script>
<div class="dummy" style="height: 62px;"></div>



<div id="projectIdDiv" class="app-kanban" data-project-id="${projectId}">
	<div id="divArea" data-member-id="${userId }">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
<!-- 					<h4 class="py-3 mb-4"> -->
<!-- 						<span class="text-muted fw-light">일정관리/</span> KANBAN BOARD -->
<!-- 					</h4> -->
			 <div style="background-color:#FFFFFF; border-radius:10px; margin : 20px 20px; margin-left:60px; padding-left:27px;">
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center w-100">
						<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
						<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
						    <h4 class="mt-3" style="font-weight:700;">칸반보드</h4>
						    <p class="ps-3 pt-1" style="margin-bottom:0px;">칸반보드를 활용해서 더욱 편하게 이슈 상태를 변경해보세요.</p>
						</div>
					</div>
				</div>
			</div>
					<div class="kanban-wrapper"></div>
					<div class="d-flex pt-2 justify-content-end">Powered by jKanban</div>
					<div class="offcanvas offcanvas-end kanban-update-item-sidebar">
						<div class="offcanvas-header" style="padding-bottom:0px;">
							<h5 class="offcanvas-title">이슈 상세</h5>
							<button type="button" class="btn-close"
								data-bs-dismiss="offcanvas" aria-label="Close"></button>
						</div>
						<div class="offcanvas-body">
							<div class="tab-content px-0 pb-0 pt-0">
								<!-- 수정 -->
								<div class="tab-pane fade show active" id="tab-update"
									role="tabpanel">
									<form id="kanban-update-item-form">
										<div class="mb-3">
											<label class="form-label" for="title">이슈 제목</label> <span
												class="badge badge-dot bg-danger me-1"
												style="margin-top: -10px; width: 0.3rem; height: 0.3rem;"></span>
											<input type="text" id="issueTitle" name="issueTitle"
												class="form-control" placeholder="이슈의 제목을 입력해주세요" />
										</div>

										<div class="mb-3 fv-plugins-icon-container">
											<label class="form-label" for="ecommerce-customer-add-name">이슈
												내용</label>
											<textarea rows="10" class="form-control"
												placeholder="이슈 내용을 입력해주세요" name="issueContent"
												id="issueContent"></textarea>
											<div
												class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
										</div>

										<div class="mb-3 fv-plugins-icon-container">
											<label class="form-label" for="ecommerce-customer-add-name">이슈
												담당자</label> <select name="issueOfficer" class="form-control"
												id="issueOfficer">
												<option value="">선택</option>
												<c:forEach var="projectMem" items="${projectMemberList}">
													<option value="${projectMem.projectMemIdx}">
														[${projectMem.projectRole[0].projectRoleName}]
														${projectMem.member.memberName}</option>
												</c:forEach>
											</select>
											<div
												class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
										</div>



										<div class="mb-3 fv-plugins-icon-container">
											<label class="form-label" for="ecommerce-customer-add-name">이슈
												진행 상태</label> <select name="issueProgressStatus"
												class="form-control" id="issueProgressStatus"
												disabled="disabled">
												<option value="Not Started">준비중</option>
												<option value="In Progress">진행중</option>
												<option value="Completed">완료됨</option>
											</select>
											<div
												class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
										</div>

										<div class="mb-3">
											 <label class="form-label" for="label-update">이슈
												우선 순위</label> <select class="select2 select2-label form-select"
												id="label-update">
												<option data-color="bg-label-success" value="Highest">매우높음</option>
												<option data-color="bg-label-warning" value="High">높음</option>
												<option data-color="bg-label-info" value="Medium">중간</option>
												<option data-color="bg-label-danger" value="Low">낮음</option>
												<option data-color="bg-label-primary" value="Lowest">매우낮음</option>
											</select>
										</div>

										<div class="mb-3 fv-plugins-icon-container">
											<label class="form-label" for="ecommerce-customer-add-name">이슈
												태그</label>
											<!-- 						<input type="text" class="form-control" placeholder="이슈 태그" name="issueTags[0].issueTagName" id="issueTagName"> -->

											<select name="issueTags[0].issueTagName" class="form-control"
												id="issueTagName">
												<option value="">선택</option>
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
												<option value="urgent">긴급</option>
											</select>
											<!-- 생성일 Sysdate-->
											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">이슈
													시작일</label> <input type="date" class="form-control"
													placeholder="이슈 시작일" name="issueDate" id="issueDate">
												<div
													class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>

											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="due-date">이슈 마감일</label> <input
													type="date" class="form-control" placeholder="이슈 마감일"
													name="issueDeadline" id="issueDeadline">
												<div
													class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>
											<!-- 							<div class="mb-3"> -->
											<!-- 								<label class="form-label" for="due-date">기한</label> <input -->
											<!-- 									type="text" id="due-date" class="form-control" -->
											<!-- 									placeholder="할일을 입력하세요" required /> -->
											<!-- 							</div> -->



											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">상위
													이슈</label> <select name="subIssueIdx" class="form-control"
													id="subIssueIdx">
													<option value="">선택</option>
													<c:forEach var="issues" items="${issueList}">
														<option value="${issues.issueIdx}">
															${issues.issueTitle}</option>
													</c:forEach>
												</select>
												<div
													class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>


											<div class="d-flex flex-wrap">
												<button type="submit" class="btn btn-primary me-3">수정</button>
												<button type="button" class="btn btn-label-danger"
													data-bs-dismiss="offcanvas">삭제</button>
											</div>
											<input type="hidden" id="projectIdx" name="projectId"
												value="${projectId}"> <input type="hidden"
												id="projectMemIdx" name="projectMemIdx" value="${projectMemberInfo.projectMemIdx}">
											<input type="hidden" id="issueIdx" name="issueIdx">
									</form>
								</div>
							</div>




								<!-- 새 할일 -->
								<div class="tab-pane fade" id="tab-new" role="tabpanel">
								
									<form id="kanban-add-item-form">
									 <div class="ecommerce-customer-add-basic mb-3">
										<div class="mb-3">
											<label class="form-label" for="new-title">이슈 제목</label> <span
												class="badge badge-dot bg-danger me-1"
												style="margin-top: -10px; width: 0.3rem; height: 0.3rem;"></span>
											<input type="text" id="new-title" class="form-control" name="issueTitle" 
												placeholder="할일을 입력하세요" required />
										</div>
										
										<div class="mb-3 fv-plugins-icon-container">
											<label class="form-label" for="new-content">이슈 내용</label>
					<!-- 						<input type="text" class="form-control" placeholder="이슈 내용" name="issueContent" id="issueContent"> -->
											<textarea rows="10" class="form-control" placeholder="이슈 내용" name="issueContent" id="new-content"></textarea>
					
											<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
										</div>
										
											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">이슈 담당자</label>
												<select name="issueOfficer" class="form-control" id="new-issueOfficer">
													<option value="">선택</option>
													<c:forEach var="projectMem" items="${projectMemberList}">
														<option value="${projectMem.projectMemIdx}"> [${projectMem.projectRole[0].projectRoleName}] ${projectMem.member.memberName}</option>
													</c:forEach>
												</select>
												<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>
										
											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">이슈 진행 상태</label>
												<select name="issueProgressStatus" class="form-control" id="new-issueProgressStatus">
													<option value="Not Started">준비중</option>
													<option value="In Progress">진행중</option>
													<option value="Completed">완료됨</option>
												</select>
												<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>
											
											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">이슈 우선 순위</label>
												<select name="issuePriorityName" class="form-control" id="new-issuePriorityName">
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
						
												<select name="issueTagName" class="form-control" id="new-issueTagName">
													<option value="">선택</option>
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
													<option value="urgent">긴급</option>
												</select>
						
												<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>
											
											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">이슈 시작일</label>
												<input type="date" class="form-control" placeholder="이슈 시작일" name="issueDate" id="new-issueDate">
												<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>
											
											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">이슈 마감일</label>
												<input type="date" class="form-control" placeholder="이슈 마감일" name="issueDeadline" id="new-due-date">
												<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>

											<div class="mb-3 fv-plugins-icon-container">
												<label class="form-label" for="ecommerce-customer-add-name">상위 이슈</label>
												<select name="subIssueIdx" class="form-control" id="new-subIssueIdx1">
													<option value="">선택</option>
													<c:forEach var="issues" items="${issueList}">
														<option value="${issues.issueIdx}">[${issues.issueIdx}] ${issues.issueTitle}</option>
													</c:forEach>
												</select>
												<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
											</div>
									</div>										
										
										<div class="d-flex flex-wrap">
											<button type="submit" class="btn btn-primary me-3">생성</button>
											<button type="button" class="btn btn-label-secondary me-3"
												data-bs-dismiss="offcanvas">취소</button>
											<button type="button" id="exampleBtn"
											        style="margin-left: 15px; background-color: transparent; color: black; padding: 10px 20px; font-size: 14px; font-weight: bold; cursor: pointer; transition: color 0.3s ease;">
											    자동
											</button>



										</div>
										<!-- hidden area -->
											<input type="hidden" id="projectIdx" name="projectId"
												value="${projectId}"> <input type="hidden"
												id="projectMemIdx" name="projectMemIdx" value="${projectMemberInfo.projectMemIdx}">
											<input type="hidden" id="issueIdx" name="issueIdx">
										
									</form>
									
									
									
								</div>
							</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/flatpickr/flatpickr.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/jkanban/jkanban.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/nexus/assets/js/app-kanban.js"></script>
