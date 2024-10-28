<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- content -->
            <div class="container-xxl flex-grow-1 container-p-y">
              <h3 class="py-3 mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">그룹 관리
                <!-- Search -->
                <div id="searchUI" style="display: flex; gap: 7px;">
                  <form:select path="condition.searchType" name="DataTables_Table_0_length" aria-controls="DataTables_Table_0" class="form-select" style="width: auto;">
                    <form:option value="">전체</form:option>
                    <form:option value="Gname">그룹명</form:option>
                    <form:option value="Mname">대표자</form:option>
                  </form:select>

                  <form class="d-flex" style="margin-right: 10px;">
                    <div class="input-group" style="flex-grow: 1;">
                      <form:input path="condition.searchWord" type="text" class="form-control" placeholder="검색어를 입력해주세요" style="flex-grow: 1;"/>
                      <span class="input-group-text" id="searchBtn"><i class="tf-icons ti ti-search"></i></span>
                    </div>
                  </form>
                </div>
              </h3>
              <div class="row g-4">
                <!-- card -->
                <div class="mb-4">
                  <div class="table-responsive text-nowrap">
                    <table class="table">
                      <thead>
                        <tr>
                          <th>그룹명</th>
                          <th>대표자</th>
                          <th>가입일</th>
                          <th>승인여부</th>
                          <th>구독여부</th>
                        </tr>
                      </thead>
                      <tbody class="table-border-bottom-0">
                         <c:choose>
                             <c:when test="${not empty groupList}">
                                 <c:forEach items="${groupList}" var="group" >
                                     <tr>
                                       <td>
                                         <a class="sysadmin-list purpleColor" href="<c:url value='/saGroup/${group.groupId }'/>"><span class="fw-medium">${group.groupName}</span></a>
                                       </td>
                                       <td>
                                         <span class="fw-medium">${group.memberManagementVO.memberName}</span>
                                       </td>
                                       <td class="fw-medium">${group.applicationDate}</td>
                                       <td>
                                         <c:choose>
                                             <c:when test="${group.approvalDate != null}">
                                                 <span class="badge bg-label-success me-1">승인 완료</span>
                                             </c:when>
                                             <c:otherwise>
                                                 <span class="badge bg-label-danger me-1">승인 대기</span>
                                             </c:otherwise>
                                         </c:choose>
                                       </td>
                                       <td>
                                         <c:choose>
                                             <c:when test="${group.useStatus == 'Y'}">
                                                 <span class="badge bg-label-primary me-1">구독중</span>
                                             </c:when>
                                             <c:otherwise>
                                                 <span class="badge bg-label-warning me-1">미구독</span>
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
                <!-- paging -->
                <div class="paging-area">
                    ${pagingHTML}
                </div>
                <form:form id="searchform" method="get" modelAttribute="condition">
                    <form:input path="searchType" type="hidden"/>
                    <form:input path="searchWord" type="hidden"/>
                    <input type="hidden" name="page" />
                </form:form>
                <!-- /card -->
              </div>
            </div>
            <!-- content -->
           <script src="${pageContext.request.contextPath}/resources/js/app/utils/paging.js"></script>