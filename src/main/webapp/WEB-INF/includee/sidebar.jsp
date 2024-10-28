<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
String projectId = (String) session.getAttribute("projectId");
String sessionProjectName = (String) session.getAttribute("sessionProjectName");
String groupId = user.getGroupMemberVO().getGroupId();
%>
            <aside id="layout-menu" class="layout-menu-horizontal menu-horizontal menu bg-menu-theme flex-grow-0">
              <div class="container-xxl d-flex h-100">
                <div class="text-nowrap pe-2" style="padding-top:19px;">
                  <a href="${pageContext.request.contextPath}/<%=groupId %>/${projectId}/chMain" class="menu-link">
                    <div style="font-weight:700; font-color:#000000;">${sessionProjectName}</div>
                    </a>
                </div>
                  
                <ul class="menu-inner">
                  <!-- 이슈 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/issue/<%=groupId %>/${projectId}/issueMain" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-checklist"></i>
                      <div>이슈</div>
                    </a>
                  </li>
                  <!-- 타임라인 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/group/<%=groupId %>/project/${projectId}/timeLineUI" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-timeline-event"></i>
                      <div>이슈 타임라인</div>
                    </a>
                  </li>

                  <!-- 캘린더 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/<%=groupId %>/${projectId}/calendarUI" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-calendar"></i>
                      <div>일정 및 이슈관리</div>
                    </a>
                  </li>
                  
                  <!-- 보드 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/<%=groupId %>/${projectId}/kanbanBoard" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-files"></i>
                      <div>칸반보드</div>
                    </a>
                  </li>

                  <!-- 공유 클라우드 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/group/<%=groupId %>/project/${projectId}/cloud" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-folders"></i>
                      <div>공유 클라우드</div>
                    </a>
                  </li>
                  <!-- 파일 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/group/<%=groupId %>/project/${projectId}/files" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-files"></i>
                      <div>프로젝트 파일 공유</div>
                    </a>
                  </li>

                  <!-- 코드쉐어 -->
                  <li class="menu-item" >
                    <a href="${pageContext.request.contextPath}/group/<%=groupId %>/project/${projectId}/codeShareList" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-app-window"></i>
                      <div>코드쉐어</div>
                    </a>
                  </li>

                  <!-- 캔버스 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/group/<%=groupId %>/project/${projectId}/canvas" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-writing"></i>
                      <div>프로젝트 노트</div>
                    </a>
                  </li>
                  
                  <!-- 화상회의 -->
                  <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/<%=groupId %>/${projectId}/video" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-live-photo"></i>
                      <div>화상회의</div>
                    </a>
                  </li>
                  
                  <!-- 공지 -->
                  <li class="menu-item" style="padding-right:40px;">
                    <a href="${pageContext.request.contextPath}/<%=groupId %>/${projectId}/announcement" class="menu-link">
                      <i class="menu-icon tf-icons ti ti-alert-circle"></i>
                      <div>공지사항</div>
                    </a>
                  </li>
                </ul>
              </div>
            </aside>