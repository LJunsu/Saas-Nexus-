<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.image-container {
	width: 100px;
	height: 130px;
	border: 1px solid #ccc;
	display: inline-block;
}

.image-container img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.image-container img:not([src]) {
	visibility: hidden;
}
</style>
<!-- Content -->

<div class="container-xxl flex-grow-1 container-p-y">
    <div class="d-flex justify-content-between">
      <h3 class="py-3 mb-2">그룹 멤버 리스트</h3>
      <button type="button" class="btn btn-primary d-block waves-effect waves-light" tabindex="0" aria-controls="DataTables_Table_0" type="button"
      data-bs-toggle="button" aria-pressed="false" style="margin:17px 0;" onclick="location.href='${cPath }/group/${groupId }/memberInvite'">
        + 그룹원 초대하기
      </button>
    </div>
<!-- 	<h4 class="py-3 mb-2">그룹원 리스트</h4> -->
	<input type="hidden" id="groupId" value="${groupId }">
	<div class="card">
<!-- 		<div class="card-datatable table-responsive"> -->
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
				<div class="card-header d-flex flex-wrap" style="display: flex; justify-content: space-between; padding:15px 20px;">
				    <h5 style="margin-bottom:0px !important; margin-left:5px;">총 <span class="totalMember">${totalCount }명</span></h5>
					<div class="d-flex align-items-center" id="searchUI">
						<form:select style="display: none;"
	 						path="condition.searchType">
	 						<form:option value="" label="전체" />
	 						<form:option value="name" label="이름" /> 
	 					</form:select>
						<form:input path="condition.searchWord" id="searchName" name="searchName" type="search" class="form-control ms-3" placeholder="사원 이름을 검색해주세요" aria-controls="DataTables_Table_0"
							style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; box-shadow: 0 0 0 0.2px #dbdade inset;"/>
						<button id="searchBtn" type="submit" class="btn btn-primary" style="height: 40.4px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
					</div>
				
<!-- 								<div id="searchUI" class="form-group"> -->
<!-- 										<label> -->
<!-- 											<div class="input-group"> -->
<%--  												<form:input path="condition.searchWord" id="searchName" type="search" class="form-control" placeholder="이름 검색" aria-controls="DataTables_Table_0" /> --%>
<!-- 												<span class="input-group-btn"> -->
<!-- 												<button id="searchBtn" class="btn btn-primary">검색</button> -->
<!-- 												</span> -->
<!-- 											</div> -->
<!-- 										</label> -->
<!-- 									</div> -->
									
									
									
									
									

<!-- 					<div class="d-flex align-items-center me-5"> -->
<!-- 						<div id="DataTables_Table_0_filter" -->
<!-- 							class="dataTables_filter ms-n3"> -->
<!-- 							<label> <input id="searchName" type="search" -->
<!-- 								class="form-control" placeholder="이름 검색" -->
<!-- 								aria-controls="DataTables_Table_0"> -->
<!-- 								<button id="searchBtn" class="btn btn-primary">검색</button> -->
<!-- 							</label> -->
<!-- 						</div> -->
<!-- 					</div> -->

<!-- 					<div -->
<!-- 						class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-md-end gap-3 gap-sm-0 flex-wrap flex-sm-nowrap pt-0"> -->
<!-- 						<div class="dataTables_length ms-n2 mt-0 mt-md-3 me-2" -->
<!-- 							id="DataTables_Table_0_length"></div> -->

<!-- 						<div class="dt-buttons btn-group flex-wrap d-flex"> -->
<!-- 							<div class="btn-group"></div> -->

<!-- 							<button -->
<!-- 								class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light" -->
<!-- 								tabindex="0" aria-controls="DataTables_Table_0" type="button"> -->
<!-- 								<span> <i class="ti ti-plus me-0 me-sm-1 mb-1 ti-xs"></i> -->
<!-- 									<span class="d-none d-sm-inline-block"><a -->
<%-- 										href="${cPath }/group/${groupId }/memberInvite" --%>
<!-- 										style="color: white">새 그룹원 초대하기</a></span> -->
<!-- 								</span> -->
<!-- 							</button> -->
<!-- 						</div> -->
<!-- 					</div> -->

				</div>
				<div>
					<table
						class="datatables-customers table border-top dataTable no-footer dtr-column"
						id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info"
						style="width: 1394px; margin-bottom:0px !important;">
						<thead>
							<tr>
								<th class="" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 100px;" aria-sort="descending">
									ID
								</th>
								<th class="text-nowrap" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1">
									이름
								</th>
								<th class="" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 150px;">
									이메일
								</th>
								<th class="" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 150px;">연락처</th>
								<th class="" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 150px;">주소</th>
							</tr>
						</thead>

						<tbody>
							<c:if test="${not empty groupMemberList }">
								<c:forEach items="${groupMemberList }" var="groupMember">
									<tr>
										<td class="memberId">${groupMember.memberId}</td>
										<td style="width: 10px"><a href="javascript::"
											class="my-modal">${groupMember.member.memberName }</a></td>
										<td>${groupMember.member.memberEmail}</td>
										<td>${groupMember.member.memberTel}</td>
										<td>${groupMember.member.memberAddress1}</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty groupMemberList }">
								<tr>
									<td colspan="4">그룹원 없음</td>
								</tr>
							</c:if>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="8">
									<div class="paging-area" style="display: flex; justify-content: flex-end; padding-top:10px;">${pagingHTML }</div>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
<!-- 		</div> -->
	</div>
</div>
<form:form id="searchform" method="get" modelAttribute="condition">
	<form:input type="hidden" path="searchType" />
	<form:input type="hidden" path="searchWord" />
	<input type="hidden" name="page">
</form:form>
<div class="modal fade" id="myModal" tabindex="-1"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="myModalLabel">상세정보</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button id="delMemberBtn" type="button" class="btn btn-danger">그룹원
					강퇴</button>
			</div>
		</div>
	</div>
</div>

<script src="${cPath }/resources/js/app/utils/paging.js"></script>
<script
	src="${cPath}/resources/js/app/groupManager/memberManage/memberList.js"></script>