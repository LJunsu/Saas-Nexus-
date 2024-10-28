<%@page import="kr.or.nexus.vo.GroupManagementVO"%>
<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}" var="userName" />
<c:set value="${authentication.principal.realUser.memberId}" var="userId" />

<%
MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");
GroupManagementVO group = (GroupManagementVO) session.getAttribute("group");
String groupId = user.getGroupMemberVO().getGroupId();
%>

<input id="myGroupId" type="hidden" value="${groupId}">
	<form action="" method="get">
<div class="container-xxl container-p-y">
	<div class="d-flex justify-content-between">
		<h3 class="py-3 mb-3 d-flex align-items-center"
			style="font-weight: 700; width: 100%;">
			<span style="color: #7367f0; font-weight: 600; padding-right:6px;">현재 진행중인</span> 프로젝트
			<div class="d-flex align-items-center">
				<input id="searchName" name="searchName" type="search" class="form-control ms-3" placeholder="프로젝트명을 입력해주세요"
					style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; border:none;">
				<button type="submit" class="btn btn-primary" style="height: 39px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
			</div>
		</h3>
			</form>
		<c:choose>
		<c:when test="${group.useStatus == 'Y' }">
		<button type="button"
			class="btn btn-primary d-block waves-effect waves-light"
			data-bs-toggle="button" aria-pressed="false"
			style="margin: 17px 0; white-space: nowrap; min-width: 160px; max-height:44px;"
			onclick="location.href='${cPath}/group/${group.groupId}/project/insert.do';">
			+ 프로젝트 추가</button>
		</c:when>
		<c:otherwise>
		<button type="button"
			class="btn btn-primary d-block waves-effect waves-light useNeed"
			data-bs-toggle="button" aria-pressed="false"
			style="margin: 17px 0; white-space: nowrap; min-width: 160px; max-height:44px;">
			+ 프로젝트 추가</button>
		</c:otherwise>
		</c:choose>
	</div>

	<!-- 	<h4 class="py-3 mb-3" style="font-weight: 700;"> -->
	<!-- 		<span class="text-muted fw-light">현재 진행중인</span> 프로젝트 -->
	<!-- 	</h4> -->
	<!-- 		<div align="right"> -->
	<%-- 			<a href="${cPath }/group/${group.groupId}/project/insert.do" --%>
	<!-- 				class="btn btn-primary" style="color: white;">프로젝트 추가</a> -->
	<!-- 		</div> -->
	<div class="row g-4">
		<c:forEach items="${projectList}" var="project">
			<c:if test="${project.projectDelyn == 'N'}">
			<c:if test="${project.projectState == 'C' }">
				<!-- Card -->
				<div class="col-xl-4 col-lg-6 col-md-6">
					<div class="card">
						<div class="card-header">
							<div class="d-flex align-items-start justify-content-between">
								<div class="d-flex align-items-start">
									<div class="avatar me-2">
										<img
											src="${pageContext.request.contextPath }/resources/nexus/assets/img/icons/brands/react-label.png"
											alt="Avatar" class="rounded-circle mt-1" />
									</div>
									<div class="me-2 ms-1">
										<h5 class="mb-0">
											<c:choose>
											<c:when test="${group.useStatus == 'Y' }">
												<a
													href="${pageContext.request.contextPath}/<%=groupId %>/${project.projectId }/chMain"
													class="text-body" style="font-weight: 700;">${project.projectName }</a>
											</c:when>
											<c:otherwise>
												<a
													href=""
													class="text-body useNeed" style="font-weight: 700;">${project.projectName }</a>
											</c:otherwise>
											</c:choose>
										</h5>
										<div class="client-info">
														<span class="fw-medium mt-2">프로젝트 리더: </span>
											<c:forEach items="${project.projectRole}" var="role">
												<c:if test="${role.projectRoleName == '프로젝트 리더'}">
													<c:forEach items="${role.projectMember}"
														var="projectMember" varStatus="status">
														<span class="text-muted">
							                            <c:choose>
                                                            <c:when test="${not empty projectMember and
										                                not empty projectMember.groupMember and
										                                not empty projectMember.groupMember.member}">
                                                                <span class="" style="color: #7367f0; font-weight: 600;">${projectMember.groupMember.member.memberName}<c:if test="${!status.last}">,</c:if></span>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span class="text-muted">리더 미지정</span>
                                                            </c:otherwise>
                                                        </c:choose>
														</span>
<%-- 														<c:choose> --%>
<%-- 																<c:when --%>
<%-- 																	test="${not empty projectMember and  --%>
<%-- 										                                not empty projectMember.groupMember and  --%>
<%-- 										                                not empty projectMember.groupMember.member}"> --%>
<%-- 										                    ${projectMember.groupMember.member.memberName} --%>
<%-- 										                </c:when> --%>
<%-- 																<c:otherwise> --%>
<!-- 										                    정보 없음 -->
<%-- 										                </c:otherwise> --%>
<%-- 															</c:choose> --%>
													</c:forEach>

												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>
								<div class="dropdown">
									<button type="button"
										class="btn p-0 dropdown-toggle hide-arrow"
										data-bs-toggle="dropdown" aria-expanded="false">
										<i class="ti ti-dots-vertical"></i>
									</button>
									<div class="dropdown-menu">
										<a class="dropdown-item waves-effect updateBtn"
											href="${cPath }/group/${group.groupId}/project/${project.projectId }/update.do">수정</a>
										<form id="deleteForm"
											action="${cPath}/group/${group.groupId}/project/${project.projectId}/delete.do"
											method="post" style="display: inline;">
											<a class="dropdown-item waves-effect text-danger delBtn"
												href="javascript:void(0);" onclick="submitDeleteForm()">삭제</a>
										</form>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body">
							<c:set var="issueCount" value="0" />
							<c:set var="completedIssueCount" value="0" />
							<c:forEach items="${project.issueList}" var="issue">
							<c:if test="${issue.issueDelyn == 'N' }">
								<c:set var="issueCount" value="${issueCount + 1}" />
								<c:if test="${issue.issueProgressStatus == 'Completed'}">
									<c:set var="completedIssueCount"
										value="${completedIssueCount + 1}" />
								</c:if>
							</c:if>
							</c:forEach>
							<c:set var="issuePercentage"
								value="${(completedIssueCount/issueCount * 100) - (completedIssueCount/issueCount * 100) % 1 }" />
							<div
								class="d-flex justify-content-between align-items-center mb-2 pb-1">
								<small>완료된 이슈: ${completedIssueCount}/${issueCount }</small> <small>${issueCount == 0 ? 0 : issuePercentage}%
									완료됨</small>
							</div>
							<div class="progress mb-2" style="height: 8px">
		                      <div
		                        class="progress-bar"
		                        role="progressbar"
		                        style="width: ${issuePercentage}%"
		                        aria-valuenow="${issuePercentage}"
		                        aria-valuemin="0"
		                        aria-valuemax="100"></div>
		                    </div>
							<div class="d-flex align-items-center pt-1">
								<div class="d-flex align-items-center">
									<ul
										class="list-unstyled d-flex align-items-center avatar-group mb-0 z-2">
<!-- 										<li data-bs-toggle="tooltip" data-popup="tooltip-custom" -->
<!-- 											data-bs-placement="top" title="Kaith D'souza" -->
<!-- 											class="avatar avatar-sm pull-up"><img -->
<!-- 											class="rounded-circle" -->
<%-- 											src="${pageContext.request.contextPath }/resources/nexus/assets/img/avatars/5.png" --%>
<!-- 											alt="Avatar" /></li> -->
										<li><small class="">프로젝트 예산:
												${project.budget }원</small></li>
									</ul>
								</div>
								<span class="badge bg-label-warning ms-auto">${project.projectCreationDate }
									생성</span>
							</div>
						</div>
					</div>
				</div>
				<!-- /Card -->
			</c:if>
			</c:if>
			</c:forEach>
			<c:forEach items="${projectList}" var="project">
			<c:if test="${project.projectDelyn == 'N'}">
			<c:if test="${project.projectState == 'N' }">
				<!-- Card -->
				<div class="col-xl-4 col-lg-6 col-md-6">
					<div class="card">
						<div class="card-header">
							<div class="d-flex align-items-start justify-content-between">
								<div class="d-flex align-items-start">
									<div class="avatar me-2">
										<img
											src="${pageContext.request.contextPath }/resources/nexus/assets/img/icons/brands/react-label.png"
											alt="Avatar" class="rounded-circle mt-1" />
									</div>
									<div class="me-2 ms-1">
										<h5 class="mb-0">
												<a
													href="${cPath }/group/${group.groupId}/project/${project.projectId }/update.do"
													class="text-danger" style="font-weight: 700;">${project.projectName }(임시)</a>
										</h5>
										<div class="client-info">
											<c:forEach items="${project.projectRole}" var="role">
												<c:if test="${role.projectRoleName == '프로젝트 리더'}">
													<c:forEach items="${role.projectMember}"
														var="projectMember">
														<span class="fw-medium mt-2">프로젝트 리더: </span>
														<span class="text-muted"> 
															<c:choose>
																<c:when
																		test="${not empty projectMember and
												                              not empty projectMember.groupMember and
												                              not empty projectMember.groupMember.member}">
												                  ${projectMember.groupMember.member.memberName}
												                </c:when>
																<c:otherwise>
												                  정보 없음
												                </c:otherwise>
															</c:choose>
														</span>
													</c:forEach>

												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>
								<div class="dropdown">
									<button type="button"
										class="btn p-0 dropdown-toggle hide-arrow"
										data-bs-toggle="dropdown" aria-expanded="false">
										<i class="ti ti-dots-vertical"></i>
									</button>
									<div class="dropdown-menu">
										<a class="dropdown-item waves-effect updateBtn"
											href="${cPath }/group/${group.groupId}/project/${project.projectId }/update.do">수정</a>
										<form id="deleteForm"
											action="${cPath}/group/${group.groupId}/project/${project.projectId}/delete.do"
											method="post" style="display: inline;">
											<a class="dropdown-item waves-effect text-danger delBtn"
												href="javascript:void(0);" onclick="submitDeleteForm()">삭제</a>
										</form>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body">
							<c:set var="issueCount" value="0" />
							<c:set var="completedIssueCount" value="0" />
							<c:forEach items="${project.issueList}" var="issue">
							<c:if test="${issue.issueDelyn == 'N' }">
								<c:set var="issueCount" value="${issueCount + 1}" />
								<c:if test="${issue.issueProgressStatus == 'Completed'}">
									<c:set var="completedIssueCount"
										value="${completedIssueCount + 1}" />
								</c:if>
							</c:if>
							</c:forEach>
							<c:set var="issuePercentage"
								value="${(completedIssueCount/issueCount * 100) - (completedIssueCount/issueCount * 100) % 1 }" />
							<div
								class="d-flex justify-content-between align-items-center mb-2 pb-1">
								<small>완료된 이슈: ${completedIssueCount}/${issueCount }</small> <small>${issueCount == 0 ? 0 : issuePercentage}%
									Completed</small>
							</div>
							<div class="progress mb-2" style="height: 8px">
		                      <div
		                        class="progress-bar"
		                        role="progressbar"
		                        style="width: ${issuePercentage}%"
		                        aria-valuenow="${issuePercentage}"
		                        aria-valuemin="0"
		                        aria-valuemax="100"></div>
		                    </div>
							<div class="d-flex align-items-center pt-1">
								<div class="d-flex align-items-center">
									<ul
										class="list-unstyled d-flex align-items-center avatar-group mb-0 z-2">
										<li><small class="text-muted">프로젝트 예산:
												${project.budget }원</small></li>
									</ul>
								</div>
								<span class="badge bg-label-warning ms-auto">${project.projectCreationDate }
									생성</span>
							</div>
						</div>
					</div>
				</div>
				<!-- /Card -->
			</c:if>
			</c:if>
		</c:forEach>
	</div>
</div>


<div class="container-xxl flex-grow-1 container-p-y">
	<h3 class="py-3 mb-3" style="font-weight: 700;">
		나에게 할당된 이슈
	</h3>
	<div class="g-4" style="background-color:#ffffff; border-radius:10px;">
		<!-- card -->
		<div class="mb-5">
			<div class="table-responsive text-nowrap" style="padding:10px 20px 15px 20px;">
				<table class="table">
					<thead>
						<tr>
							<th>이슈명</th>
							<th>프로젝트명</th>
							<th class="ps-2">담당자</th>
							<th>마감일</th>
							<th>진행 상태</th>
						</tr>
					</thead>
					<tbody class="table-border-bottom-0">
						<c:forEach var="issue" items="${issueList}" begin="0" end="5">
							<tr>
								<td>
									<span class="fw-medium text-primary" onclick="location.href='${pageContext.request.contextPath}/issue/${groupId}/${issue.projectId}/issueMain';">
										${issue.issueTitle}
									</span>
								</td>

								<td>${issue.project.projectName}</td>

								<td class="text-center">
									<ul class="list-unstyled users-list m-0 avatar-group d-flex align-items-center">
										<li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" class="avatar avatar-xs pull-up" title="${issue.issueOfficer}">
											<img src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png" onerror="this.style.display='none'" alt="Avatar" class="rounded-circle" />
										</li>

										<li style="margin-left: 10px;">${userName}</li>
									</ul>
								</td>
								<c:choose>
									<c:when test="${issue.issueDeadline!=null}">
										<td>${issue.issueDeadline} 까지</td>
									</c:when>
									<c:otherwise>
										<td>
											마감일 없음
										</td>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${issue.issueProgressStatus eq 'Completed'}">
										<td>
											<span class="badge bg-label-success me-1">완료됨</span>
										</td>
									</c:when>

									<c:when test="${issue.issueProgressStatus eq 'In Progress'}">
										<td>
											<span class="badge bg-label-primary me-1">진행중</span>
										</td>
									</c:when>

									<c:otherwise>
										<td>
											<span class="badge bg-label-warning me-1">준비중</span>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- /card -->
	</div>
</div>

<script src="${cPath }/resources/js/app/groupManager/gm-main.js"></script>