<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<div class="container-xxl flex-grow-1 container-p-y">
<h4 class="py-3 mb-2">그룹 정보 수정</h4>
<form:form method="post" modelAttribute="group" enctype="multipart/form-data">
<table class="table">
	<form:input type="hidden" path="groupId"/>
	<tr>
		<th>그룹ID</th>
		<td>${group.groupId }</td>
	</tr>
	<tr>
		<th>그룹명</th>
		<td><form:input type="text" path="groupName" class="form-control"
				required="required" />
			<form:errors path="groupName" cssClass="text-danger" element="span" /></td>
	</tr>
	<tr>
		<th>그룹 소개 사진</th>
			<form:input type="hidden" path="groupProfilePicture"/>
			<td>
				<input type="file" name="groupImage" />
			<td>
				<img alt="" src="/resources/images/${group.groupProfilePicture }">
			</td>
	</tr>
	<tr>
		<th>그룹생성일</th>
		<td>${group.groupCreationDate }</td>
	</tr>
	<tr>
		<th>구독여부</th>
		<td>${group.useStatus }</td>
	</tr>
	<tr>
		<th>사업자등록증 ID</th>
		<td>${group.businessLicenseId }</td>
	</tr>
	<tr>
		<th>코멘트</th>
		<td><form:textarea path="groupComment" class="form-control" />
			<form:errors path="groupComment" cssClass="text-danger"
				element="span" /></td>
	</tr>
	<tr>
		<form:input type="hidden" path="memberId"/>
		<th>그룹 대표</th>
		<td>${group.memberManagementVO.memberName } &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn btn-info">변경</button></td>
	</tr>
</table>
<br>
<div>
	<button type="submit" class="btn btn-primary">수정</button>
</div>
</form:form>
</div>