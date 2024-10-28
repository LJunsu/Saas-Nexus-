<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
.pagination {
	display: flex;
	justify-content: flex-end; /* 우측 정렬 */
}
</style>
<!-- Content -->

<div class="container-xxl flex-grow-1 container-p-y" style="margin-top:55px;">

<!-- 	<h4 class="py-3 mb-2" style="margin-top: 20px;">프로젝트 공지</h4> -->


	<div class="card">
		<div class="card-datatable table-responsive" style="padding-bottom:0px !important;">
			<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
<!-- 				<div class="card-header d-flex flex-wrap pb-md-2"> -->
				
				
				<div class="emails-list-header p-3 py-lg-3 py-2 ps-5 pe-4">
					<div class="d-flex justify-content-between align-items-center">
						<div class="d-flex align-items-center w-100">
							<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
							<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
							    <h4 class="mt-3" style="font-weight:700;">프로젝트 공지사항</h4>
								<div class="input-group input-group-merge dataTables_filter shadow-none ps-1" id="DataTables_Table_0_filter">
										<div class="d-flex align-items-center" id="searchUI">
											<form:input path="condition.searchWord" name="search" type="search" class="form-control ms-3" placeholder="공지 제목을 입력해주세요." 
											  style="max-width: 300px; border-top-right-radius: 0; border-bottom-right-radius: 0; border: 1px solid #c9c8ce;"/>
											<button id="searchBtn" type="submit" class="btn btn-primary" style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
										</div>
								</div>
							</div>
						</div>
						<div class="d-flex align-items-center mb-0">
							<div class="dropdown d-flex align-self-center text-nowrap" style="width: 300px; justify-content: flex-end;">
								<c:forEach var="leader" items="${leaders}">
									<c:if test="${realUser.memberId == leader.memberId}">
									<button
										class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light"
										tabindex="0" aria-controls="DataTables_Table_0" type="button"
										onclick="window.location.href='${cPath}/${groupId}/${projectId}/announcement/insert'">
										<span> <i class="ti ti-plus me-0 me-sm-1 mb-1 ti-xs"></i>
											<span class="d-none d-sm-inline-block">공지 생성하기</span>
										</span>
									</button>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				
				
				

<%-- 					<div class="d-flex align-items-center me-5">
						<div id="DataTables_Table_0_filter"
							class="dataTables_filter ms-n3">
							<tr>
								<td colspan="5">
									<div id="searchUI">
										<form:input class="datatable-input form-control"
											path="condition.searchWord" />
										<button id="searchBtn" class="btn btn-primary">검색</button>
									</div>
								</td>
							</tr>
						</div>
					</div> --%>

<%-- 					<div
						class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-md-end gap-3 gap-sm-0 flex-wrap flex-sm-nowrap pt-0">
						<div class="dataTables_length ms-n2 mt-0 mt-md-3 me-2"
							id="DataTables_Table_0_length"></div>

						<div class="dt-buttons btn-group flex-wrap d-flex">
							<div class="btn-group"></div>
							<c:forEach var="leader" items="${leaders}">
								<c:if test="${realUser.memberId == leader.memberId}">
									<button
										class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light"
										tabindex="0" aria-controls="DataTables_Table_0" type="button"
										onclick="window.location.href='${cPath}/${groupId}/${projectId}/announcement/insert'">
										<span> <i class="ti ti-plus me-0 me-sm-1 mb-1 ti-xs"></i>
											<span class="d-none d-sm-inline-block">공지 생성</span>
										</span>
									</button>

								</c:if>
							</c:forEach>
						</div>
					</div> --%>
<!-- 				</div> -->

				<table
					class="datatables-customers table border-top dataTable no-footer dtr-column"
					id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info"
					style="width: 1340px; margin-bottom:0px !important;">
					<thead>
						<tr>
							<th class="control sorting_disabled dtr-hidden" rowspan="1"
								colspan="1" style="width: 0px; display: none;" aria-label="">

							</th>

							<th class="" tabindex="0" aria-controls="DataTables_Table_0"
								rowspan="1" colspan="1"
								style="width: 100px; text-align: center;"
								aria-sort="descending;">공지 번호</th>

							<th class="text-nowrap" tabindex="0"
								aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
								style="text-align: center;">공지 제목</th>

							<th class="" tabindex="0" aria-controls="DataTables_Table_0"
								rowspan="1" colspan="1"
								style="width: 150px; text-align: center;">작성자</th>

							<th class="" tabindex="0" aria-controls="DataTables_Table_0"
								rowspan="1" colspan="1"
								style="width: 150px; text-align: center;">작성일</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${not empty announcementList }">
							<c:forEach items="${announcementList }" var="announcement">
								<tr>
									<td style="text-align: center;">${announcement.rnum}</td>
									<td style="text-align: center;"><a
										href="<c:url value='/${groupId }/${projectId }/announcement/${announcement.boardId }'/>">
											${announcement.boardTitle } </a></td>
									<td style="text-align: center;">${announcement.memberName}</td>
									<td style="text-align: center;">${announcement.boardCreateDate}</td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${empty announcementList }">
							<tr>
								<td colspan="4">공지 없음</td>
							</tr>
						</c:if>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="5">
								<div class="paging-area" style="padding-top:5px;">${pagingHTML}</div>
							</td>
						</tr>
					</tfoot>
				</table>
				<form:form id="searchform" method="get" modelAttribute="condition"
					action="">
					<form:input type="hidden" path="searchType" />
					<form:input type="hidden" path="searchWord" />
					<input type="hidden" name="page" />
				</form:form>
			</div>
		</div>
	</div>
</div>

<!--/ Content -->
<script
	src="${pageContext.request.contextPath }/resources/js/app/utils/paging.js"></script>


