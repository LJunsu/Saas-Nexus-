<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- content -->
<div class="container-xxl flex-grow-1 container-p-y">
  <h3 class="py-3 mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">회원 관리
    <!-- Search -->
    <div style="display: flex; flex-direction: row; gap: 10px;">
      <div class="dropdown">
        <button
          type="button"
          class="btn btn-label-primary dropdown-toggle"
          data-bs-toggle="dropdown"
          aria-expanded="false">
          그룹 필터
        </button>
        <ul class="dropdown-menu" style="max-height: 200px; overflow-y: auto;">
          <li><a class="dropdown-item" onclick="reloading();">전체</a></li>
          <c:choose>
            <c:when test="${not empty groupList}">
              <c:forEach items="${groupList}" var="group" >             
                <li><a class="dropdown-item" href="javascript:void(0);" data-id-value="${group.groupId}">${group.groupName}</a></li>
              </c:forEach>
            </c:when>
            <c:otherwise>
            </c:otherwise>
          </c:choose>
          <li><a class="dropdown-item" href="javascript:void(0);">무소속</a></li>
        </ul>
      </div>
      <div id="searchUI" style="display: flex; gap: 7px;">
        <form:select path="condition.searchType" name="DataTables_Table_0_length" aria-controls="DataTables_Table_0" class="form-select" style="width: auto;">
          <form:option value="">전체</form:option>
          <form:option value="name">회원명</form:option>
          <form:option value="id">회원 ID</form:option>
        </form:select>
      
        <form class="d-flex" style="margin-right: 10px;">
          <div class="input-group" style="flex-grow: 1;">
            <form:input path="condition.searchWord" type="text" class="form-control" placeholder="검색어를 입력해주세요" style="flex-grow: 1;" />
            <span class="input-group-text" id="searchBtn"><i class="tf-icons ti ti-search"></i></span>
          </div>
        </form>
      </div>
    </div>
    <!-- /Search -->
  </h3>
  <div class="row g-4">
    <div class="mb-4">
      <div class="table-responsive text-nowrap">
        <table class="table">
          <thead>
            <tr>
              <th>회원ID</th>
              <th>회원명</th>
              <th>소속그룹</th>
              <th>가입일</th>
              <th>회원구분</th>
              <th>탈퇴상태</th>
            </tr>
          </thead>
          <tbody class="table-border-bottom-0 memListArea">
              <c:choose>
                <c:when test="${not empty memberList}">
                    <c:forEach items="${memberList}" var="member" >
                        <tr>
                          <td>
                            <a class="fw-medium sysadmin-list purpleColor" href="<c:url value='/saMember/${member.memberId }'/>" data-bs-toggle="modal" data-bs-target="#detailUser">${member.memberId}</a>
                          </td>
                          <td>
                            <a class="fw-medium sysadmin-list" href="<c:url value='/saMember/${member.memberId }'/>" data-bs-toggle="modal" data-bs-target="#detailUser">${member.memberName}</a>
                          </td>
                          <td>
                            <c:choose>
                                <c:when test="${member.groupManagementVO.groupName != null}">
                                    <span class="fw-medium">${member.groupManagementVO.groupName}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="fw-medium text-muted">무소속</span>
                                </c:otherwise>
                            </c:choose>
                          </td>
                          <td class="fw-medium">${member.memberJoinDate}</td>
                          <td>
                            <c:choose>
                                <c:when test="${member.memberRole == 'ROLE_GROUPADMIN'}">
                                    <span class="badge bg-label-success me-1">관리자</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-label-secondary me-1">멤버</span>
                                </c:otherwise>
                            </c:choose>
                          </td>
                          <td>
                            <c:choose>
                                <c:when test="${member.memberDelyn != 'Y'}">
                                    <span class="badge bg-label-primary me-1">활성화</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-label-danger me-1">탈퇴</span>
                                </c:otherwise>
                            </c:choose>
                          </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                 <tr>
                   <td colspan="5"  style="text-align: center; vertical-align: middle;">
                    <span class="fw-medium" style="display: inline-block; margin-top: 10px;">검색 결과가 존재하지 않습니다.</span>
                   </td>
                 </tr>
                </c:otherwise>
            </c:choose>
          </tbody>
        </table>
      </div>
    </div>
    <div class="paging-area">
        ${pagingHTML}
    </div>
    <form:form id="searchform" method="get" modelAttribute="condition">
        <form:input path="searchType" type="hidden"/>
        <form:input path="searchWord" type="hidden"/>
        <input type="hidden" name="page" />
    </form:form>
  </div>
</div>
            
<!-- Edit User Modal -->
<div class="modal fade" id="detailUser" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-simple modal-edit-user">
    <div class="modal-content p-3 p-md-5">
      <div class="modal-body">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <div class="text-center mb-4">
          <h3 class="mb-2">관리자용 회원 정보조회</h3>
          <p class="text-muted">회원의 상세정보 조회 및 탈퇴 처리가 가능합니다.</p>
        </div>
        <form id="editUserForm" class="row g-3" onsubmit="return false">
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserName">회원명</label>
            <input
              type="text"
              id="detailUserName"
              name="detailUserName"
              class="form-control" readonly/>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserId">회원 ID</label>
            <input
              type="text"
              id="detailUserId"
              name="detailUserId"
              class="form-control" readonly/>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserEmail">회원 Email</label>
            <input
              type="text"
              id="detailUserEmail"
              name="detailUserEmail"
              class="form-control" readonly/>
          </div>
          <div class="col-12 col-md-6" style="display:flex; flex-direction:column;">
            <label class="form-label" for="detailUserCase">회원 구분</label>
            <span class="badge bg-label-success me-1" id="userAdmin" style="transform: translateY(25%); align-self: flex-start;">관리자</span>
            <span class="badge bg-label-secondary me-1" id="userMember" style="transform: translateY(25%); align-self: flex-start;">멤버</span>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserRegDt">가입일</label>
            <input
              type="text"
              id="detailUserRegDt"
              name="detailUserRegDt"
              class="form-control modal-edit-tax-id" readonly/>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserTel">휴대폰 번호</label>
            <input
              type="text"
              id="detailUserTel"
              name="detailUserTel"
              class="form-control modal-edit-tax-id" readonly/>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserGroup">소속 그룹</label>
            <input
              type="text"
              id="detailUserGroup"
              name="detailUserGroup"
              class="form-control modal-edit-tax-id" readonly/>
          </div>
          <div class="col-12 col-md-6">
            <label class="form-label" for="detailUserAns">사용자 확인용 답변</label>
            <input
              type="text"
              id="detailUserAns"
              name="detailUserAns"
              class="form-control modal-edit-tax-id" readonly/>
          </div>
          <div class="col-12 text-center mt-4">
            <button
              type="reset"
              id="confirmBtn"
              class="btn btn-primary"
              data-bs-dismiss="modal"
              aria-label="Close">
              확인
            </button>
            <button type="submit" class="btn btn-label-secondary me-1" id="quitBtn" name="quitBtn" onclick="quitBtnClick();" style="display:none !important;">탈퇴</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<!--/ Edit User Modal -->
<!-- content -->
<script src="${pageContext.request.contextPath}/resources/js/app/utils/paging.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/sysadmin/saMemberList.js"></script>
