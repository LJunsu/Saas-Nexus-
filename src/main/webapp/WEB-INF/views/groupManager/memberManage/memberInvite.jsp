<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<security:authentication property="principal" var="principal" />
<div class="">
	<input type="hidden" id="groupId" value="${group.groupId }">
	<input type="hidden" id="groupName" value="${group.groupName }">
	<div class="d-flex align-items-center justify-content-center h-px-500">
		<form class="w-px-500 rounded p-3 p-md-5" style="background-color:#ffffff;">
			<h3 class="mb-2" style="text-align:center;">그룹 멤버 초대하기</h3>
			<span style="text-align:center; display: block; padding-bottom:20px;">그룹 멤버를 초대하기 위해 멤버 이메일을 입력해주세요 📧</span>
			<div class="row mb-6" >
<!-- 				<label class="col-sm-3 col-form-label" for="form-alignment-username">이메일 -->
<!-- 					입력</label> -->
				<div style="display:inline-block;">
					<input type="text" id="form-alignment-email" class="form-control" 
						placeholder="초대할 사원의 이메일을 입력해주세요">
				</div>
			</div>
			<br>
			<div align="center">
				<button type="button" id="emailBtn" class="btn btn-primary"
					data-bs-toggle="modal" data-bs-target="#emailModal">초대 링크 전송하기</button>
			</div>
			<a class="btn" id="UpdateAuto" style="float: right; margin: 10px 10px 10px 0;">자동완성</a>
		</form>
	</div>
</div>

<div class="modal fade" id="emailModal" tabindex="-1"
	aria-labelledby="emailModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-xl" style="width:800px;">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="emailModalLabel" style="font-weight:700; padding:10px 10px 0px 10px;">🏸 찾으시는 회원이 맞나요?<h4>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<form>
				<div id="emailBody" class="modal-body" style="padding-top:5px; max-height: 500px; overflow-y: auto;">
					<table class="table">
						<thead>
							<tr>
								<th>아이디</th>
								<th>이름</th>
								<th>이메일</th>
								<th>확인</th>
							</tr>
						</thead>
						<tbody id="emailhtml">
						</tbody>
					</table>
					<div class="d-flex pt-2 justify-content-end">Powered by Google OAuth</div>
				</div>
			</form>
		</div>
	</div>
</div>


<script src="${cPath }/resources/js/app/groupManager/memberManage/memberInvite.js"></script>
