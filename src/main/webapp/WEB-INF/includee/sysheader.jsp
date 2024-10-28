<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%
  MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
  String memberName = user.getMemberName();
  String memberId = user.getMemberId();
  String memberRole = user.getMemberRole();
  String groupId = user.getGroupMemberVO().getGroupId();
%>
<style>

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

</style>

<nav class="layout-navbar navbar navbar-expand-xl align-items-center bg-navbar-theme" id="layout-navbar">
          <div class="container-xxl">
            <div class="navbar-brand app-brand demo d-none d-xl-flex py-0 me-4">
              <a href="${pageContext.request.contextPath }/saMain.do" class="app-brand-link gap-2">
                <span class="app-brand-logo demo">
                  <svg width="32" height="22" viewBox="0 0 32 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M0.00172773 0V6.85398C0.00172773 6.85398 -0.133178 9.01207 1.98092 10.8388L13.6912 21.9964L19.7809 21.9181L18.8042 9.88248L16.4951 7.17289L9.23799 0H0.00172773Z"
                      fill="#7367F0" />
                    <path
                      opacity="0.06"
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M7.69824 16.4364L12.5199 3.23696L16.5541 7.25596L7.69824 16.4364Z"
                      fill="#161616" />
                    <path
                      opacity="0.06"
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M8.07751 15.9175L13.9419 4.63989L16.5849 7.28475L8.07751 15.9175Z"
                      fill="#161616" />
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M7.77295 16.3566L23.6563 0H32V6.88383C32 6.88383 31.8262 9.17836 30.6591 10.4057L19.7824 22H13.6938L7.77295 16.3566Z"
                      fill="#7367F0" />
                  </svg>
                </span>
                <span class="app-brand-text demo menu-text fw-bold">Nexus</span>
              </a>

              <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto d-xl-none">
                <i class="ti ti-x ti-sm align-middle"></i>
              </a>
            </div>

            <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
              <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                <i class="ti ti-menu-2 ti-sm"></i>
              </a>
            </div>

            <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
              <aside id="layout-menu" class="layout-menu-horizontal menu-horizontal menu bg-menu-theme flex-grow-0" style="width: 77%;">
                  <ul class="menu-inner">
  
                    <!-- 그룹관리 -->
                    <li class="menu-item">
                      <a href="${pageContext.request.contextPath }/saGroup/list.do" class="menu-link">
                        <div>그룹관리</div>
                      </a>
                    </li>
  
                    <!-- 회원관리 -->
                    <li class="menu-item">
                      <a href="${pageContext.request.contextPath }/saMember/list.do" class="menu-link">
                        <div>회원관리</div>
                      </a>
                    </li>
  
                    <!-- 고객센터 -->
                    <li class="menu-item">
                      <a href="${pageContext.request.contextPath }/saSc/list.do" class="menu-link">
                        <div>고객센터</div>
                      </a>
                    </li>
                    
                    <!-- 구독통계 -->
                    <li class="menu-item">
                      <a href="${pageContext.request.contextPath }/saSub/list.do" class="menu-link">
                        <div>구독통계</div>
                      </a>
                    </li>
                  </ul>
              </aside>

              <ul class="navbar-nav flex-row align-items-center ms-auto">

                <!-- Style Switcher -->
                <li class="nav-item dropdown-style-switcher dropdown me-2 me-xl-0">
                  <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                    <i class="ti ti-md"></i>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-end dropdown-styles">
                    <li>
                      <a class="dropdown-item" href="javascript:void(0);" data-theme="light">
                        <span class="align-middle"><i class="ti ti-sun me-2"></i>라이트 모드</span>
                      </a>
                    </li>
                    <li>
                      <a class="dropdown-item" href="javascript:void(0);" data-theme="dark">
                        <span class="align-middle"><i class="ti ti-moon me-2"></i>다크 모드</span>
                      </a>
                    </li>
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
                <li class="nav-item navbar-dropdown dropdown-user dropdown">
                  <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                    <div class="avatar avatar-online">
                      <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/avatars/1.png" alt class=" rounded-circle" />
                    </div>
                  </a>
                  <ul class="dropdown-menu dropdown-menu-end">
                    <li>
                      <a class="dropdown-item">
                        <div class="d-flex">
                          <div class="flex-shrink-0 me-3">
                            <div class="avatar avatar-online">
                              <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/avatars/1.png" alt class="rounded-circle" />
                            </div>
                          </div>
                          <div class="flex-grow-1">
                            <span class="fw-medium d-block"><%=memberName %></span>
                            <small class="text-muted">시스템 관리자</small>
                          </div>
                        </div>
                      </a>
                    </li>
                    <li>
                      <div class="dropdown-divider"></div>
                    </li>
                    <li>
                    <form>
                      <a class="dropdown-item" id="logoutBtn" href="javascript::">
                        <i class="ti ti-logout me-2 ti-sm"></i>
                        <span class="align-middle">로그아웃</span>
                      </a>
                    </form>
                    </li>
                  </ul>
                </li>
                <!--/ User -->
              </ul>
            </div>
          </div>
        </nav>
        <script src="${cPath }/resources/js/app/header/logout.js"></script>
        <script
	src="${pageContext.request.contextPath }/resources/js/app/headerAlarm.js"></script>