<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="card">
	<c:if test="${not empty error }">
		<input type="hidden" id="error" value="${error }">
	</c:if>
	<div class="d-flex align-items-center justify-content-center h-px-500">
		<form class="w-px-500 border rounded p-3 p-md-5" method="post">
			<h3 class="mb-6">초대한 그룹</h3>
			<div class="row mb-6">
				<div class="col-sm-9">
					<p>그룹명 : ${group.groupName }</p>
					<p>그룹 대표 : ${group.memberManagementVO.memberName }</p>
				</div>
			</div>
			<br>
			<div align="center">
				<button type="submit" class="btn btn-primary">초대 수락</button>
			</div>
		</form>
	</div>
</div>


<script>
	var msg = $('#error').val();
	if(msg){
		alert(msg);		
	}
</script>