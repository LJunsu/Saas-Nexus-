<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.form-control {
	width: 500px;
	margin: 10px;
}
</style>
<div style="display: flex;">
	<div style="padding-left: 150px; padding-top: 50px;">
		<form method="post">
			<table>
					<input type="hidden" id="projectId" name="projectId" value="${projectId }">
				<tr>
					<th>프로젝트 명</th>
					<td><input type="text" name="projectName" class="form-control"
						value="${project.projectName}"><span
						class="text-danger">${errors.projectName }</span></td>
				</tr>
				<tr>
					<th>프로젝트 설명</th>
					<td><input type="text" name="projectDescription"
						class="form-control" value="${project.projectDescription}"><span
						class="text-danger">${errors.projectDescription }</span></td>
				</tr>
				<tr>
					<th>프로젝트 예상 종료일</th>
					<td><input type="date" name="projectEndDate"
						class="form-control" value="${project.projectEndDate}"><span
						class="text-danger">${errors.projectEndDate }</span></td>
				</tr>
				<tr>
					<th>프로젝트 예산</th>
					<td><input type="number" name="projectBudget"
						class="form-control" value="${project.projectBudget}"><span
						class="text-danger">${errors.projectBudget }</span></td>
				</tr>
		</form>
				<tr>
					<th>프로젝트 역할</th>
					<td><input type="text" id="projectRoleName"> <button id="roleInsert" class="btn btn-primary">추가</button></td>
				</tr>
				<tr>
					<table>
						<thead>
							<tr>
								<th>역할목록</th>
							</tr>
						</thead>
						<tbody id="roleList">
							<c:forEach items="${projectRole }" var="role">
								<tr>
									<td>${role.projectRoleName }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</tr>
			</table>
	</div>
	<div style="padding: 20px; margin: 20px;">
		<table class="table">
			<c:choose>
				<c:when test="${not empty groupMember}">
					<thead>
						<tr>
							<th>회원 이름</th>
							<th>이메일</th>
							<th>역할</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${groupMember}" var="gm">
							<tr>
								<td>${gm.member.memberName}</td>
								<td>${gm.member.memberEmail }</td>
								<td><select>
									<option value="">역할선택</option>
									<c:forEach items="${projectRole }" var="role">
										<option value="${role.projectRoleIdx }">${role.projectRoleName }</option>
									</c:forEach>
								</select></td>
							</tr>
						</c:forEach>
					</tbody>
				</c:when>
				<c:otherwise>
					<p>표시할 회원이 없습니다.</p>
				</c:otherwise>
			</c:choose>
		</table>
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
<script src="${cPath }/resources/js/app/groupManager/projectInsert.js"></script>