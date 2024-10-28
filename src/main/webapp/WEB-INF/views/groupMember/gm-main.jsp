<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}" var="userName" />
<c:set value="${authentication.principal.realUser.memberId}" var="userId" />

<div class="container-xxl container-p-y">
  <h3 class="py-3 mb-3" style="font-weight: 700;"><span style="color: #7367f0; font-weight: 600; padding-right:6px;">현재 진행중인</span> 프로젝트</h3>
    <div class="row g-4">
      <c:choose>
         <c:when test="${not empty projectList}">
            <c:forEach items="${projectList}" var="project" >
              <!-- Card -->
              <div class="col-xl-4 col-lg-6 col-md-6" onclick="location.href='${pageContext.request.contextPath}/${groupId }/<c:out value="${project['PROJECT_ID']}"/>/chMain';">
                <div class="card">
                  <div class="card-header">
                    <div class="d-flex align-items-start">
                      <div class="d-flex align-items-start">
                        <div class="avatar me-2">
                          <img
                            src="${pageContext.request.contextPath }/resources/nexus/assets/img/icons/brands/react-label.png"
                            alt="Avatar"
                            class="rounded-circle mt-1" />
                        </div>
                        <div class="me-2 ms-1">
                          <h5 class="mb-0">
                            <a href="javascript:;" class="stretched-link text-body" style="font-weight: 700;">${project["PROJECT_NAME"]}</a>
                          </h5>
                          <div class="client-info">
                            <span class="fw-medium mt-2">프로젝트 리더: </span>
                             <c:choose>
                                 <c:when test="${empty project['PROJECT_LEADER']}">
                                     <span class="text-muted">리더 미지정</span>
                                 </c:when>
                                 <c:otherwise>
                                     <span class="" style="color: #7367f0; font-weight: 600;">${project["PROJECT_LEADER"]}</span>
                                 </c:otherwise>
                             </c:choose>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center mb-2 pb-1">
                      <small>완료된 이슈: ${project["ISSUE_COUNT_COMPLETE"]}/${project["ISSUE_COUNT"]}</small>
                      <small>${project["ISSUE_COMPLETE_PERCENT"]}% 완성됨</small>
                    </div>
                    <div class="progress mb-2" style="height: 8px">
                      <div
                        class="progress-bar"
                        role="progressbar"
                        style="width: ${project['ISSUE_COMPLETE_PERCENT']}%"
                        aria-valuenow="${project['ISSUE_COMPLETE_PERCENT']}"
                        aria-valuemin="0"
                        aria-valuemax="100"></div>
                    </div>
                    <div class="d-flex align-items-center pt-1">
                      <div class="d-flex align-items-center">
						<ul
							class="list-unstyled d-flex align-items-center avatar-group mb-0 z-2">
							<li><small class="">프로젝트 예산:${project['PROJECT_BUDGET'] }원</small></li>
						</ul>
					  </div>
                      <span class="badge bg-label-warning ms-auto">${project["PROJECT_CREATION_DATE"]} 생성</span>
                    </div>
                  </div>
                </div>
              </div>
            </c:forEach>
           </c:when>
         <c:otherwise>
          <tr>
            <td colspan="5"  style="text-align: center; vertical-align: middle;">
             <span class="fw-medium" style="display: inline-block; margin-top: 10px;">아직 소속되어있는 그룹 워크스페이스가 없습니다.</span>
            </td>
          </tr>
        </c:otherwise>
      </c:choose>

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
                          <th>담당자</th>
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

								<td>
									<ul class="list-unstyled users-list m-0 avatar-group d-flex align-items-center">
										<li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" class="avatar avatar-xs pull-up" title="${issue.issueOfficer}">
											<img src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png" onerror="this.style.display='none'" alt="Avatar" class="rounded-circle" />
										</li>

										<li style="margin-left: 10px;">${userName}</li>
									</ul>
								</td>

								<td>${issue.issueDeadline} 까지</td>

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