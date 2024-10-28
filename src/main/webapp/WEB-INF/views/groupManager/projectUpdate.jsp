<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.form-control {
    width: 100%;
    max-width: 500px;
    margin: 1%;
}

.custom-select {
    position: relative;
    width: 100%;
    max-width: 200px;
    font-family: Arial, sans-serif;
}

.select-selected {
    background-color: #f8f9fa;
    color: #495057;
    padding: 2% 3%;
    border: 1px solid #ced4da;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    justify-content: space-between;
    align-items: center;
    transition: all 0.2s ease-in-out;
}

.select-selected:hover {
    background-color: #e9ecef;
}

.select-selected.select-arrow-active {
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
}

.select-selected i {
    transition: transform 0.2s ease-in-out;
}

.select-selected.select-arrow-active i {
    transform: rotate(180deg);
}

.select-items {
    position: absolute;
    background-color: #fff;
    top: 100%;
    left: 0;
    right: 0;
    z-index: 99;
    max-height: 200px;
    overflow-y: auto;
    border: 1px solid #ced4da;
    border-top: none;
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.select-hide {
    display: none;
}

.select-items div {
    padding: 2% 3%;
    cursor: pointer;
    transition: background-color 0.2s ease-in-out;
}

.select-items div:hover, .same-as-selected {
    background-color: #e9ecef;
}

.image-container {
    width: 26%;
    max-width: 260px;
    height: 20%;
/*     padding-bottom: 26%; */
    max-height: 200px;
    border: 1px solid #ccc;
    display: inline-block;
}

.image-container img {
    width: 100%;
    height: 100%;
/*     object-fit: cover; */
}

.image-container img:not([src]) {
    visibility: hidden;
}

.main-container {
    display: flex;
    flex-wrap: wrap;
    max-width: 1400px;
    margin: 0 auto;
}

.form-section {
    flex: 0 0 40%;
    padding: 20px;
}

.member-section {
    flex: 0 0 60%;
    padding: 20px;
}

.member-table {
    width: 100%;
    table-layout: fixed;
}

.member-table th:nth-child(1), .member-table td:nth-child(1) {
    width: 15%;
}

.member-table th:nth-child(2), .member-table td:nth-child(2) {
    width: 35%;
}

.member-table th:nth-child(3), .member-table td:nth-child(3) {
    width: 25%;
}

.member-table th:nth-child(4), .member-table td:nth-child(4) {
    width: 25%;
}
</style>
<div class="container-xxl flex-grow-1 container-p-y">
    <div class="d-flex justify-content-between">
      <h3 class="py-3 mb-2">프로젝트 추가하기</h3>
    </div>
    <div class="card">
	    <div class="card-header d-flex flex-wrap" style="display: flex; justify-content: space-between; padding:15px 20px;">
		<div class="main-container">
		    <div class="form-section">
		      <h4 style="font-weight:700;">프로젝트 기본 정보</h4>
				<form action="${cPath }/group/${project.groupId }/project/${project.projectId}/update.do" method="post" enctype="multipart/form-data">
					<input type="hidden" id="projectId" name="projectId" value="${project.projectId }"> 
					<input type="hidden" id="groupId" name="groupId" value="${project.groupId }">
					<c:choose>
						<c:when test="${not empty project.projectProfileImg }">
							<div class="image-container">
								<img id="image" alt="" src="${project.projectProfileImg}">
							</div>
						</c:when>
						<c:otherwise>
							<div class="image-container">
								<img id="image" alt="" src="${cPath }/resources/images/white.PNG">
							</div>
						</c:otherwise>
					</c:choose>
					<table>
						<tr>
							<th>프로젝트 이미지</th>
							<td>
								<input type="file" id="projectImg" name="projectImg" class="form-control" value="${project.projectName}">
								<span class="text-danger">${errors.projectName }</span>
							</td>
						</tr>
						<tr>
							<th>프로젝트명</th>
							<td>
								<input type="text" id="projectName" name="projectName" class="form-control" value="${project.projectName}">
								<span class="text-danger">${errors.projectName }</span>
							</td>
						</tr>
						<tr>
							<th>프로젝트 설명</th>
							<td>
								<textarea id="projectDescription" name="projectDescription" class="form-control">${project.projectDescription}</textarea>
								<span class="text-danger">${errors.projectDescription }</span>
							</td>
						</tr>
						<tr>
							<th>프로젝트 예상 종료일</th>
							<td>
								<input type="date" id="projectEndDate" name="projectEndDate" class="form-control" value="${project.projectEndDate}">
								<span class="text-danger">${errors.projectEndDate }</span>
							</td>
						</tr>
						<tr>
							<th>프로젝트 예산</th>
							<td>
								<input type="number" id="projectBudget" name="projectBudget" class="form-control" value="${project.projectBudget}">
								<span class="text-danger">${errors.projectBudget }</span>
							</td>
						</tr>
						<tr>
							<th>프로젝트 역할</th>
							<td>
								<input type="text" class="form-control" id="projectRoleName" style="max-width: 160px; display: inline-block; vertical-align: middle;">
								<button id="roleInsert" class="btn btn-primary" style="display: inline-block; vertical-align: middle;">추가</button>
							</td>
						</tr>
						<tr>
							<table class="table">
								<thead>
									<tr>
										<th>역할목록</th>
										<th>제거</th>
									</tr>
								</thead>
								<tbody id="roleList">
									<c:forEach items="${projectRole }" var="role">
										<tr>
											<input type="hidden" class="roleName" value="${role.projectRoleIdx }">
											<input type="hidden" class="prn" value="${role.projectRoleName }">
											<td>${role.projectRoleName }</td>
											<td>
												<button class="roleDelBtn btn btn-danger btn-sm">제거</button>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</tr>
					</table>
			</div>
			
			<div class="member-section">
			<h4 style="font-weight:700;">프로젝트 인원 목록</h4>
				<table class="table table-striped" style="margin-bottom:50px;">
					<thead>
						<tr>
							<th>이름</th>
							<th>이메일</th>
							<th>역할</th>
						</tr>
					</thead>
					<tbody id="projectMember">
						<c:choose>
							<c:when test="${not empty projectMember}">
								<c:forEach items="${projectMember }" var="pm">
									<tr>
										<td>${pm.groupMember.member.memberName }</td>
										<td>${pm.groupMember.member.memberEmail }</td>
										<td>
											<table class="table">
												<c:forEach items="${pm.projectRole }" var="role" varStatus="vs">
													<tr>
														<input type="hidden" class="projectMemIdx" value="${pm.projectMemIdxes[vs.index] }">
														<td>${role.projectRoleName }</td>
														<td>
															<button class="btn btn-sm btn-danger projectMemberDelete">역할 제거</button>
														</td>
													</tr>
												</c:forEach>
											</table>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<td colspan="4">현재 프로젝트에 인원이 없습니다.</td>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			    <div class="d-flex" style="justify-content:space-between;">
					<h4 style="font-weight:700;">그룹 회원 목록</h4>
					<div class="d-flex justify-content-center pb-2 mb-2">
						<input id="searchMember" type="search" class="form-control" style="margin: 0px 4px 0px 0px; max-width: 250px; display: inline-block; vertical-align: middle;">
						<button id="groupMemberSearch" class="btn btn-primary text-nowrap" style="display: inline-block; vertical-align: middle;">검색</button>
					</div>
				</div>
				<div style="max-height: 400px; overflow-y: scroll;">
					<table class="table table-striped member-table">
						<thead>
							<tr>
								<th>이름</th>
								<th>이메일</th>
								<th>역할선택</th>
								<th>추가</th>
							</tr>
						</thead>
						<tbody id="groupMemberList">
							<c:choose>
								<c:when test="${not empty groupMember}">
									<c:forEach items="${groupMember}" var="gm">
										<tr>
											<input type="hidden" class="memberId" value="${gm.memberId }">
											<td>${gm.member.memberName}</td>
											<td>${gm.member.memberEmail }</td>
											<td class="roleSelect">
												<div class="custom-select">
													<div class="select-selected">
														<span>역할 선택</span> <i class="fas fa-chevron-down"></i>
													</div>
													<div class="select-items select-hide">
														<c:forEach items="${projectRole}" var="role">
															<div data-value="${role.projectRoleIdx}">${role.projectRoleName}</div>
														</c:forEach>
													</div>
												</div>
												<div class="selecter">
													<select style="display: none;">
														<option value="">역할 선택</option>
														<c:forEach items="${projectRole}" var="role">
															<option value="${role.projectRoleIdx}">${role.projectRoleName}</option>
														</c:forEach>
													</select>
												</div>
											</td>
											<td>
												<button class="projectMemberInsert btn btn-sm btn-primary">프로젝트 인원 등록</button>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<td colspan="4">표시할 회원이 없습니다.</td>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<a class="btn" id="UpdateAuto" style="float: right; margin: 10px 10px 10px 0;">자동완성</a>
				<!-- 		<input id="searchMember" type="search" style="margin-top: 5px;"> -->
				<!-- 		<button id="groupMemberSearch" class="btn btn-primary">검색</button> -->
				<br> <br> <br>
				<br>
				<div align="right">
					<button type="submit" class="btn btn-primary">프로젝트 등록</button>
					<button id="deleteProject" class="btn btn-danger">삭제</button>
				</div>
			</form>
		
				<form action="${cPath }/group/${project.groupId }/project/${project.projectId }/delete.do" method="post" id="delForm"></form>
			</div>
		</div>
	    </div>
    </div>
</div>
<!-- <div> -->
<!-- 	<select> -->
<!-- 		<option value="">초대할 그룹원</option> -->
<%-- 		<c:forEach items="${groupMember }" var="gm"> --%>
<%-- 			<option value="${gm.memId }">${gm.name }</option> --%>
<%-- 		</c:forEach> --%>
<!-- 	</select> -->
<!-- </div> -->
<script src="${cPath }/resources/js/app/groupManager/projectUpdate.js"></script>