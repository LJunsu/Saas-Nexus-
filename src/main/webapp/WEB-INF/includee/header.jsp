<%@page import="kr.or.nexus.vo.GroupManagementVO"%>
<%@page import="kr.or.nexus.vo.ProjectVO"%>
<%@page import="kr.or.nexus.member.main.service.MemberMainService"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}"
	var="userName" />
<!--  MEMBER이름 -->
<c:set value="${authentication.principal.realUser.memberId}"
	var="userId" />
<!--  MEMBER이름 -->

<%
MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");
GroupManagementVO group = (GroupManagementVO) session.getAttribute("group");
String memberName = user.getMemberName();
String memberId = user.getMemberId();
String memberRole = user.getMemberRole();
String groupId = user.getGroupMemberVO().getGroupId();
List<Map<String, Object>> projectList = (List<Map<String, Object>>) session.getAttribute("projectList");
List<Map<String, Object>> projectLists = (List<Map<String, Object>>) session.getAttribute("projectLists");
%>

<!-- alarm style Area -->
<style>
/* 필터 섹션의 전체 배경과 여백 처리 */
.notification-filters {
    display: flex;
    justify-content: center; /* 버튼을 수평 중앙 정렬 */
    align-items: center; /* 수직 중앙 정렬 */
    gap: 15px;  /* 버튼 간격 */
    background-color: transparent;
    margin: 0;  /* 상하 간격 최소화 */
    padding: 10px;
}


.filter-btn {
    padding: 10px 20px;
    border: none;
    border-radius: 20px;
    background-color: #f0f0f0; /* 비활성화 상태 배경색 */
    color: #555; /* 비활성화 상태 텍스트 색상 */
    font-weight: bold;
    cursor: pointer;
    transition: background-color 0.3s, color 0.3s;
}

.filter-btn.active {
    background-color: #007bff; /* 활성화된 상태 배경색 */
    color: #fff;  /* 활성화된 상태 텍스트 색상 */
}

.filter-btn:hover {
    background-color: #e0e0e0; /* 호버 시 배경색 */
    color: #333;  /* 호버 시 텍스트 색상 */
}
/* 도연 추가 START */
.select2-selection.select2-selection--single {
  display: flex;
  align-items: center;
}

.select2-selection__rendered {
  padding-top: 1px;
}
/* .bg-menu-theme { */
/* 	background-color:rgba(255, 255, 255, 0.95) !important */
/* } */

/* 도연 추가 END */


</style>

<!-- Navbar -->
<nav
	class="layout-navbar navbar navbar-expand-xl align-items-center bg-navbar-theme"
	id="layout-navbar" style="backdrop-filter:none !important;">
	<input type="hidden" id="myGroupId" value="${groupId }">
	<div class="container-xxl" data-group-id='${groupId }' data-context-path='${pageContext.request.contextPath}'>
		<div class="navbar-brand app-brand demo d-none d-xl-flex py-0 me-4">
			<%
			if (memberRole.equals("ROLE_USER")) {
			%>
			<a href="${pageContext.request.contextPath}/<%=groupId %>/memMain.do"
				class="app-brand-link gap-2"> <%
 } else {
 %> <a
				href="${pageContext.request.contextPath}/group/<%=groupId %>"
				class="app-brand-link gap-2"> <%
 }
 %> <span class="app-brand-logo demo"> <svg
							width="32" height="22" viewBox="0 0 32 22" fill="none"
							xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" clip-rule="evenodd"
								d="M0.00172773 0V6.85398C0.00172773 6.85398 -0.133178 9.01207 1.98092 10.8388L13.6912 21.9964L19.7809 21.9181L18.8042 9.88248L16.4951 7.17289L9.23799 0H0.00172773Z"
								fill="#7367F0" />
                    <path opacity="0.06" fill-rule="evenodd"
								clip-rule="evenodd"
								d="M7.69824 16.4364L12.5199 3.23696L16.5541 7.25596L7.69824 16.4364Z"
								fill="#161616" />
                    <path opacity="0.06" fill-rule="evenodd"
								clip-rule="evenodd"
								d="M8.07751 15.9175L13.9419 4.63989L16.5849 7.28475L8.07751 15.9175Z"
								fill="#161616" />
                    <path fill-rule="evenodd" clip-rule="evenodd"
								d="M7.77295 16.3566L23.6563 0H32V6.88383C32 6.88383 31.8262 9.17836 30.6591 10.4057L19.7824 22H13.6938L7.77295 16.3566Z"
								fill="#7367F0" />
                  </svg>
				</span> <span class="app-brand-text demo menu-text fw-bold">Nexus</span>
			</a> <a href="javascript:void(0);"
				class="layout-menu-toggle menu-link text-large ms-auto d-xl-none">
					<i class="ti ti-x ti-sm align-middle"></i>
			</a>
		</div>

		<div
			class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
			<a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
				<i class="ti ti-menu-2 ti-sm"></i>
			</a>
		</div>

		<div class="navbar-nav-right d-flex align-items-center"
			id="navbar-collapse">
			<aside id="layout-menu"
				class="layout-menu-horizontal menu-horizontal menu bg-menu-theme flex-grow-0"
				style="width: 77%; background-color:rgba(255, 255, 255, 0.95) !important; backdrop-filter: blur(10px) !important;">
				<ul class="menu-inner" style="margin-left: 0px;margin-right: 59px;">
					<!-- 프로젝트 -->
					<li class="menu-item"><a href="" class="menu-link menu-toggle">
							<div>프로젝트</div>
					</a>
						<ul class="menu-sub" style="max-height: 230px; overflow-y: auto; ">
							<%
							if (memberRole.equals("ROLE_USER")) {
							%>
							<c:choose>
								<c:when test="${not empty projectList}">
									<c:forEach items="${projectList}" var="project">
										<li class="menu-item">
										<c:choose>
										<c:when test="${group.useStatus == 'Y' }">
										<a
											href="${pageContext.request.contextPath}/<%=groupId %>/<c:out value="${project['PROJECT_ID']}"/>/chMain"
											class="menu-link"> <i
												class="menu-icon tf-icons ti ti-files"></i>
												<div>${project["PROJECT_NAME"]}</div>
										</a>
										</c:when>
										<c:otherwise>
										<a
											href=""
											class="menu-link useNeedUser"> <i
												class="menu-icon tf-icons ti ti-files"></i>
												<div>${project["PROJECT_NAME"]}</div>
										</a>
										</c:otherwise>
										</c:choose>
										</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<li>참여중인 프로젝트가 없습니다.</li>
								</c:otherwise>
							</c:choose>
							<%
							} else {
							%>
							<c:choose>
								<c:when test="${not empty projectLists}">
									<c:forEach items="${projectLists}" var="project">
										<li class="menu-item">
										<c:choose>
										<c:when test="${group.useStatus == 'Y' }">
										<a
											href="${pageContext.request.contextPath}/<%=groupId %>/<c:out value="${project['PROJECT_ID']}"/>/chMain"
											class="menu-link"> <i
												class="menu-icon tf-icons ti ti-files"></i>
												<div>${project["PROJECT_NAME"]}</div>
										</a>
										</c:when>
										<c:otherwise>
										<a
											href=""
											class="menu-link useNeed"> <i
												class="menu-icon tf-icons ti ti-files"></i>
												<div>${project["PROJECT_NAME"]}</div>
										</a>
										</c:otherwise>
										</c:choose>
										</li>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<li>참여중인 프로젝트가 없습니다.</li>
								</c:otherwise>
							</c:choose>
							<%
							}
							%>
						</ul></li>
					<%
					if (memberRole.equals("ROLE_GROUPADMIN")) {
					%>
					<!-- 그룹 관리 -->
					<li class="menu-item"><a href="" class="menu-link menu-toggle">
							<div>그룹 관리</div>
					</a>
						<ul class="menu-sub">
							<li class="menu-item"><a
								href="${cPath }/group/<%=groupId %>/memberManage/list.do"
								class="menu-link"> <i class="menu-icon tf-icons ti ti-users"></i>
									<div>그룹 멤버 리스트</div>
							</a></li>
							<li class="menu-item"><a
								href="${pageContext.request.contextPath}/group/<%=groupId %>/memberInvite"
								class="menu-link"> <i class="menu-icon tf-icons ti ti-users"></i>
									<div>그룹 멤버 초대</div>
							</a></li>
						</ul></li>
					<%
					}
					%>

					<!-- 전자 결재 -->
					<li class="menu-item">
					<%
							if (memberRole.equals("ROLE_USER")) {
							%>
					<c:choose>
						<c:when test="${group.useStatus == 'Y' }">
							<a
								href="${pageContext.request.contextPath}/<%=groupId %>/ea"
								class="menu-link">
									<div>전자결재</div>
							</a>
							</c:when>
							<c:otherwise>
							<a 
								href=""
								class="menu-link useNeedUser">
									<div>전자결재</div>
							</a>
						</c:otherwise>
					</c:choose>
					<%
						}else{
					%>
						<c:choose>
						<c:when test="${group.useStatus == 'Y' }">
							<a
								href="${pageContext.request.contextPath}/<%=groupId %>/ea"
								class="menu-link">
									<div>전자결재</div>
							</a>
							</c:when>
							<c:otherwise>
							<a 
								href=""
								class="menu-link useNeed">
									<div>전자결재</div>
							</a>
						</c:otherwise>
					</c:choose>
					<%
					}
					%>
					</li>

					<!-- 채팅 -->
					<li class="menu-item"><a
						href="${pageContext.request.contextPath}/group/<%=groupId %>/chat/main"
						class="menu-link">
							<div>채팅</div>
					</a></li>

					<!-- 커뮤니티 -->
					<li class="menu-item"><a href="" class="menu-link menu-toggle">
							<div>커뮤니티</div>
					</a>
						<ul class="menu-sub">
							<li class="menu-item"><a
								href="${pageContext.request.contextPath}/group/<%=groupId %>/informationBoardUI"
								class="menu-link"> <i class="menu-icon tf-icons ti ti-users"></i>
									<div>정보 마당</div>
							</a></li>
							<li class="menu-item"><a
								href="${pageContext.request.contextPath}/group/<%=groupId %>/noticeBoardUI"
								class="menu-link"> <i class="menu-icon tf-icons ti ti-users"></i>
									<div>그룹 공지</div>
							</a></li>
						</ul></li>



				</ul>
			</aside>

			<ul class="navbar-nav flex-row align-items-center ms-auto" style="padding-left:10px;">
				<!-- Search -->
				<form class="d-flex" style="margin-right: 10px;">
					<div class="input-group" style="margin-right: 30px;width: 400px;flex-grow: 1;flex-wrap: nowrap;">
						<select class="form-control" style="flex-grow: 1;" id="projectSearchSelect" style="width: 25%"><option></option></select> 
						<a id="issueheaderSearchBtn" href="#">
							<span class="input-group-text" style="border-radius:0px 5px 5px 0px !important;">
								<i class="tf-icons ti ti-search" id="dotbogi"style="border-radius:0; "></i>
							</span>
						</a>
					</div>
				</form>
				<!-- /Search -->

				<!-- Style Switcher -->
				<li class="nav-item dropdown-style-switcher dropdown me-2 me-xl-0">
					<a class="nav-link dropdown-toggle hide-arrow"
					href="javascript:void(0);" data-bs-toggle="dropdown"> <i
						class="ti ti-md"></i>
				</a>
					<ul class="dropdown-menu dropdown-menu-end dropdown-styles">
						<li><a class="dropdown-item" href="javascript:void(0);"
							data-theme="light"> <span class="align-middle"><i
									class="ti ti-sun me-2"></i>라이트모드</span>
						</a></li>
						<li><a class="dropdown-item" href="javascript:void(0);"
							data-theme="dark"> <span class="align-middle"><i
									class="ti ti-moon me-2"></i>다크모드</span>
						</a></li>
					</ul>
				</li>
				<!-- / Style Switcher-->

				<!-- Notification -->
				<li class="nav-item dropdown-notifications navbar-dropdown dropdown me-3 me-xl-1">
					<a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false"> 
						<i class="ti ti-bell ti-md"></i> 
						<span id="alarmCount" class="badge bg-danger rounded-pill badge-notifications"></span>
					</a>
					<ul class="dropdown-menu dropdown-menu-end py-0">
						<li class="dropdown-menu-header border-bottom" style="display: flex; justify-content: center; align-items: center;">
							<div class="dropdown-header d-flex align-items-center py-3">
								<div class="notification-filters">
									<button id="filter-all" class="filter-btn active">전체</button>
									<button id="filter-read" class="filter-btn">읽음</button>
									<button id="filter-unread" class="filter-btn">읽지 않음</button>
								</div>
								<a href="javascript:void(0)" class="dropdown-notifications-all text-body"
									data-bs-toggle="tooltip" data-bs-placement="top" title="Mark all as read">
								</a>
							</div>
						</li>
						<li class="dropdown-notifications-list scrollable-container">
							<ul class="list-group list-group-flush">
							</ul>
						</li>
					</ul>
				</li>
				<!--/ Notification -->


				<!-- User -->
				<li class="nav-item navbar-dropdown dropdown-user dropdown"><a
					class="nav-link dropdown-toggle hide-arrow"
					href="javascript:void(0);" data-bs-toggle="dropdown">
						<div class="avatar avatar-online">
							<img
								src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png"
								alt class="rounded-circle" />
						</div>
				</a>
					<ul class="dropdown-menu dropdown-menu-end">
						<li><a class="dropdown-item">
								<div class="d-flex">
									<div class="flex-shrink-0 me-3">
										<div class="avatar avatar-online">
											<img
												src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png"
												alt class=" rounded-circle" />
										</div>
									</div>
									<div class="flex-grow-1">
										<span class="fw-medium d-block"><%=memberName%></span>
										<%
										if (memberRole.equals("ROLE_GROUPADMIN")) {
										%>
										<small class="text-muted">관리자</small>
										<%
										} else {
										%>
										<small class="text-muted">일반</small>
										<%
										}
										%>
									</div>
								</div>
						</a></li>
						<li>
							<div class="dropdown-divider"></div>
						</li>
						<li><a class="dropdown-item"
							href="${pageContext.request.contextPath }/Mypage.do"> <i
								class="ti ti-user-check me-2 ti-sm"></i> <span
								class="align-middle">마이페이지</span>
						</a></li>
						<li>
							<div class="dropdown-divider"></div>
						</li>
						<li>
							<form>
								<a class="dropdown-item" id="logoutBtn" href="javascript::">
									<i class="ti ti-logout me-2 ti-sm"></i> <span
									class="align-middle">로그아웃</span>
								</a>
							</form>
						</li>
					</ul></li>
				<!--/ User -->
			</ul>
		</div>
	</div>
</nav>
<script src="${cPath }/resources/js/app/header/logout.js"></script>
<script src="${cPath }/resources/js/app/header/headerGroupIssueSearch.js"></script>
<script
	src="${pageContext.request.contextPath }/resources/js/app/headerAlarm.js"></script>