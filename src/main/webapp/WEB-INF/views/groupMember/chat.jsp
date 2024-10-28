
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="kr.or.nexus.vo.GroupManagementVO"%>
<%@page import="kr.or.nexus.vo.ProjectVO"%>
<%@page import="kr.or.nexus.member.main.service.MemberMainService"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
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

<style>
/* 채팅방 리스트의 점을 없애기 위한 스타일 */
#chat-room-item {
	list-style-type: none !important;
	padding: 0 !important;
	margin-right: 10px;
}

#members-icon {
	display: flex;
	align-items: center;
	position: relative;
	cursor: pointer;
}

#members-icon i {
	font-size: 20px;
	color: #007bff;
}

#members-dropdown {
	display: none;
	position: absolute;
	top: 100%; /* 이 값은 필요에 따라 조정 */
	left: 0; /* 왼쪽 끝에 위치 */
	background-color: white;
	border: 1px solid #ddd;
	z-index: 1000;
	width: 200px;
	border-radius: 5px;
}

#members-dropdown ul {
	list-style-type: none;
	padding: 0;
	margin: 0;
}

#members-dropdown li {
	padding: 10px 15px;
	border-bottom: 1px solid #ddd;
	cursor: pointer;
	transition: background-color 0.3s;
}

#members-dropdown li:last-child {
	border-bottom: none;
}

#members-dropdown li:hover {
	background-color: #f5f5f5;
}

#dropdown-menu {
	display: none;
	position: absolute;
	top: 45px; /* 이 값은 필요에 따라 조정 */
	right: 0; /* 오른쪽 끝에 위치 */
	background-color: #ffffff;
	border: 1px solid #ddd;
	box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
	z-index: 1;
	width: 150px;
}

.dropdown-menu ul {
	list-style: none;
	padding: 0;
	margin: 0;
}

.dropdown-menu ul li {
	padding: 10px;
	border-bottom: 1px solid #f1f1f1;
}

.dropdown-menu ul li a {
	color: #333;
	text-decoration: none;
	display: block;
}

.dropdown-menu ul li:hover {
	background-color: #f1f1f1;
}

.dropdown-menu ul li:last-child {
	border-bottom: none;
}

.chat-sidebar-left-user, .sidebar-body {
	overflow-y: auto;
	max-height: calc(100vh - 200px);
	scrollbar-width: none;
}

.chat-history-header {
	display: flex;
	justify-content: space-between; /* 좌우 끝에 요소를 정렬 */
	align-items: center; /* 수직 가운데 정렬 */
	width: 100%;
	padding: 10px;
	position: relative;
}

.chat-history-header .chat-contact-info {
	display: flex;
	align-items: center;
	width: 100%;
	flex-grow: 1; /* 제목이 가운데에 위치하도록 공간을 차지함 */
}

#chat-room-title {
	margin-left: 20px;
	text-align: left;
}

.menu-toggle::after {
	content: none; /* `::after` 제거 */
}

#chat-messages {
	max-height: 100%;
	overflow-y: auto;
	scrollbar-width: none;
}

.modal {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
	background-color: #ffffff;
	margin: 5% auto;
	padding: 20px;
	border: 1px solid #ddd;
	border-radius: 8px;
	width: 70%;
	max-width: 600px;
}

.modal-header, .modal-footer {
	padding: 15px;
	border-bottom: 1px solid #ddd;
}

.modal-footer {
	border-top: 1px solid #ddd;
	text-align: right;
}

.modal-header h4, .modal-footer button {
	margin: 0;
}

.modal-footer button {
	margin-left: 10px;
	background-color: #007bff;
	color: white;
	border: none;
	padding: 10px 20px;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.modal-footer button:hover {
	background-color: #0056b3;
}

/* 추가된 스타일 */
</style>

<div class="container-xxl flex-grow-1 container-p-y">
	<div class="app-chat card overflow-hidden">
		<div class="row g-0">
			<!--                   Sidebar Left -->
			<div class="col app-chat-sidebar-left app-sidebar overflow-hidden"
				id="app-chat-sidebar-left">
				<div
					class="chat-sidebar-left-user sidebar-header d-flex flex-column justify-content-center align-items-center flex-wrap px-4 pt-5">
					<div class="avatar avatar-xl">
						<img src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png" alt="Avatar"
							class="rounded-circle" />
					</div>
					<h5 class="mt-2 mb-0">${realUser.memberName }</h5>
					<span></span> <i class="ti ti-x ti-sm cursor-pointer close-sidebar"
						data-bs-toggle="sidebar" data-overlay
						data-target="#app-chat-sidebar-left"></i>
				</div>
				<div class="sidebar-body px-4 pb-4">
					<div class="my-4"></div>
				</div>
			</div>
			<!--                   /Sidebar Left -->
			<!--                   Chat & Contacts -->
			<div
				class="col app-chat-contacts app-sidebar flex-grow-0 overflow-hidden border-end"
				id="app-chat-contacts">
				<div class="sidebar-header">
					<div class="d-flex align-items-center me-3 me-lg-0">
						<div class="flex-shrink-0 avatar avatar-online me-3"
							data-bs-toggle="sidebar" data-overlay="app-overlay-ex"
							data-target="#app-chat-sidebar-left">
							<img class="user-avatar rounded-circle cursor-pointer"
								src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png" alt="Avatar" />
						</div>
						<button id="add-room-btn" class="btn btn-primary">채팅방 추가</button>
					</div>
					<i
						class="ti ti-x cursor-pointer d-lg-none d-block position-absolute mt-2 me-1 top-0 end-0"
						data-overlay data-bs-toggle="sidebar"
						data-target="#app-chat-contacts"></i>
				</div>
				<hr class="container-m-nx m-0" />
				<div class="sidebar-body">
					<div class="chat-room-list" id="chat-room-list">
						<div class="chat-contact-list-item-title">
							<h5 class="text-primary mb-0 px-4 pt-3 pb-2">채팅방 목록</h5>
						</div>
						<!-- Chats -->
						<ul class="chat-rooms">
							<c:forEach var="room" items="${chatRooms}">
								<c:forEach var="members" items="${room.members}">
									<c:if test="${realUser.memberId == members.memberId}">
										<li id="chat-room-item" class="chat-contact-list-item mb-1"
											data-room-id="${room.chattingRoomId}"
											onclick="openChatRoom('${room.chattingRoomId}')">
											<div class="chat-contact-info flex-grow-1 ms-4">
												<div
													class="d-flex justify-content-between align-items-center">
													<h6 id="chat-room-name"
														class="chat-contact-name text-truncate m-0 fw-normal">${room.roomName}</h6>
													<small id="message-time-${room.chattingRoomId}"
														class="recent-message">--:--</small>
												</div>
												<small class="text-muted"
													id="recent-message-${room.chattingRoomId}"></small>
											</div>
										</li>
									</c:if>
								</c:forEach>
							</c:forEach>
						</ul>
					</div>
					<!--                       Contacts -->
					<ul class="list-unstyled chat-contact-list mb-0" id="contact-list">
						<li class="chat-contact-list-item chat-contact-list-item-title">
							<h5 class="text-primary mb-0">그룹원 목록</h5>
						</li>
						<li class="chat-contact-list-item contact-list-item-0 d-none">
							<h6 class="text-muted mb-0">그룹원이 없습니다.</h6>
						</li>
						<c:forEach var="groupMember" items="${groupMembers }">
							<li class="chat-contact-list-item"><a
								class="d-flex align-items-center">
									<div class="flex-shrink-0 avatar">
										<img src="https://storage.googleapis.com/java_bucket_hstest1/${groupMember.memberId}/profile.png" alt="Avatar"
											class="rounded-circle" />
									</div>
									<div class="chat-contact-info flex-grow-1 ms-2">
										<h6 class="chat-contact-name text-truncate m-0">
											${groupMember.memberName }</h6>
									</div>
							</a></li>
						</c:forEach>
					</ul>
				</div>
			</div>

			<!--                   /Chat contacts -->

			<!--                   Chat History -->
			<div class="col app-chat-history bg-body" id="chat-window">
				<div class="chat-history-wrapper">
					<div class="chat-history-header border-bottom">
						<div class="d-flex align-items-center" style="width: 100%;">
							<!-- 멤버 아이콘을 왼쪽에 정렬 -->
							<div id="members-icon"
								style="flex: 0 0 auto; margin-right: auto; cursor: pointer;">
								<i class="fa fa-users"></i>
								<!-- 드롭다운 메뉴 -->
								<div id="members-dropdown">
									<ul id="members-list"
										style="list-style-type: none; padding: 0; margin: 0;">
										<!-- 멤버 항목이 동적으로 추가됩니다 -->
									</ul>
								</div>
							</div>

							<!-- 채팅방 제목을 가운데 정렬 -->
							<h4 id="chat-room-title" style="flex: 1; text-align: center;"></h4>

							<!-- 메뉴 토글을 오른쪽에 정렬 -->
							<h4 style="flex: 0 0 auto; cursor: pointer;" class="menu-toggle"
								id="menu-toggle">≡</h4>

							<!-- 드롭다운 메뉴 추가 -->
							<div class="dropdown-menu hidden" id="dropdown-menu">
								<ul>
									<li><a href="#" id="leave-room-btn">채팅방 나가기</a></li>
									<li><a href="#" id="invite-room-btn">채팅방 초대</a></li>
								</ul>
							</div>
						</div>
					</div>

					<div class="chat-history-body bg-body">
						<ul class="list-unstyled chat-history" id="chat-messages">
						</ul>
					</div>

					<!--                       Chat message form -->
					<div class="chat-history-footer shadow-sm">
						<form
							class="form-send-message d-flex justify-content-between align-items-center">
							<input type="hidden" id="room-id"> <input type="hidden"
								id="user-id" value="${realUser.memberId}"> <input
								type="hidden" id="user-name" value="${realUser.memberName}">
							<input type="hidden" id="member-profict"
								value="${user.memberProfilePict}"> <input type="hidden"
								id="group-id" value="${groupId}"> <input
								id="message-input"
								class="form-control message-input border-0 me-3 shadow-none"
								placeholder="메시지를 전송해주세요..." />
							<div class="message-actions d-flex align-items-center">
								<button class="btn btn-primary d-flex align-items-center"
									id="send-message-btn" style="white-space: nowrap;">
									<i class="ti ti-send me-1"></i> <span class="align-middle">전송</span>
								</button>

							</div>
						</form>
					</div>
				</div>
			</div>

			<!--                   /Chat History -->
			<div class="app-overlay"></div>
		</div>
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by SockJs</div>
</div>
</head>
<!-- 채팅방 추가 모달 -->
<!-- 채팅방 추가 모달 -->
<div id="add-room-modal" class="modal">
	<div class="modal-content">
		<div class="modal-header">
			<h4>채팅방 추가</h4>
		</div>
		<div class="modal-body">
			<label for="room-name">채팅방 이름:</label> <input type="text"
				id="room-name" class="form-control" placeholder="채팅방 이름을 입력하세요">

			<h5>그룹원:</h5>
			<div id="group-members" class="form-control">
				<!-- JavaScript를 통해 AJAX로 그룹원 목록이 동적으로 채워집니다. -->
			</div>
		</div>
		<div class="modal-footer">
			<button id="close-modal-btn" class="btn btn-secondary">닫기</button>
			<button id="create-room-btn" class="btn btn-primary">채팅방 생성</button>
		</div>
	</div>
</div>
<div id="invite-room-modal" class="modal">
	<div class="modal-content">
		<div class="modal-header">
			<h4>채팅방 초대</h4>
		</div>
		<div class="modal-body">
			<h5>초대할 멤버:</h5>
			<div id="invite-members" class="form-control">
				<!-- JavaScript를 통해 AJAX로 사용자 목록이 동적으로 채워집니다. -->
			</div>
		</div>
		<div class="modal-footer">
			<button id="close-invite-modal-btn" class="btn btn-secondary">닫기</button>
			<button id="invite-members-btn" class="btn btn-primary">초대</button>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/js/app/chat.js"></script>