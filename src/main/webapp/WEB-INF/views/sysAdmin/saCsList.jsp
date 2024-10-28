<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- content -->
            <div class="container-xxl flex-grow-1 container-p-y">
              <h3 class="py-3 mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">고객센터

                <!-- Search -->
                <div id="searchUI" style="display: flex; gap: 7px;">
                  <form:select path="condition.searchType" name="DataTables_Table_0_length" aria-controls="DataTables_Table_0" class="form-select" style="width: auto;">
                    <form:option value="">전체</form:option>
                    <form:option value="title">문의 제목</form:option>
                    <form:option value="id">문의자 ID</form:option>
                  </form:select>

                  <form class="d-flex" style="margin-right: 10px;">
                    <div class="input-group" style="flex-grow: 1;">
                      <form:input path="condition.searchWord" type="text" class="form-control" placeholder="검색어를 입력해주세요" style="flex-grow: 1;" />
                      <span class="input-group-text" id="searchBtn"><i class="tf-icons ti ti-search"></i></span>
                    </div>
                  </form>
                </div>
              </h3>
              <div class="row g-4">

                    <!-- card -->
                    <div class="mb-4">
                      <div class="table-responsive text-nowrap">
                        <table class="table" id="tableGrid">
                          <thead>
                            <tr>
                              <th>문의제목</th>
                              <th>문의내용</th>
                              <th>문의자 ID</th>
                              <th>문의일</th>
                              <th>답변상태</th>
                            </tr>
                          </thead>
                          <tbody class="table-border-bottom-0">
                           <c:choose>
                             <c:when test="${not empty csList}">
                                 <c:forEach items="${csList}" var="cs" >
                                     <tr>
                                       <td>
                                         <a class="sysadmin-list purpleColor" href="<c:url value='/saSc/${cs.csId }'/>" data-bs-toggle="modal" data-bs-target="#referAndEarn">
                                           <span class="fw-medium">${cs.csTitle}</span>
                                         </a>
                                       </td>
                                       <td>
                                         <span class="fw-medium">${cs.csContent}</span>
                                       </td>
                                       <td>
                                         <span class="fw-medium">${cs.memberId}</span>
                                       </td>
                                       <td class="fw-medium">${cs.csDate}</td>
                                       <td>
                                       <c:choose>
                                         <c:when test="${not empty cs.customerBoardAnswersVO}">
                                           <c:choose>
                                             <c:when test="${cs.customerBoardAnswersVO[0].csAnsContent != null}">
                                               <span class="badge bg-label-success me-1">답변완료</span>
                                             </c:when>
                                             <c:otherwise>
                                               <span class="badge bg-label-danger me-1">답변대기</span>
                                             </c:otherwise>
                                           </c:choose>
                                         </c:when>
                                         <c:otherwise>
                                           <span class="badge bg-label-danger me-1">답변대기</span>
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
                    <!-- /card -->

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
            
            <!-- modal -->
            <div class="modal fade" id="referAndEarn" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-simple modal-refer-and-earn">
                  <div class="modal-content p-3 p-md-5">
                    <div class="modal-body">
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="reloading();"></button>
                      <div class=" mb-4">
                        <span class="badge bg-label-danger me-1" id="answerN">답변대기</span>
                        <span class="badge bg-label-success me-1" id="answerY">답변완료</span>
                        <h3 class="mb-2 mt-2" id="cs-title"></h3>
                        <p id="cs-content" class="text-muted mb-5 m-auto">
                        </p>
                      </div>
                      <div style="display: flex; flex-direction: row; align-items: center;">
                        <span>작성자 : </span>
                        <span style="margin-left: 13px;" id="cs-writer"></span>
                         <button type="button" class="btn btn-secondary ms-auto" id="autoCompleteBtn" style="margin-left: auto;" onclick="autoComplete()">자동완성</button>
                      </div>
                      <hr class="my-3" />
                      <div class=" dylayout" id="answer-div">
                        <div class="col app-emails-list">
                            <div class="email-list dylayout pt-0">
                              <ul class="list-unstyled m-0">
                                <li class="email-list-item" data-starred="true" data-bs-toggle="sidebar" data-target="#app-email-view">
                                  <div class="d-flex align-items-center">
                                    <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/avatars/1.png" alt="user-avatar"
                                      class="d-block flex-shrink-0 rounded-circle me-sm-3 me-2" height="32" width="32" />
                                    <div class="email-list-item-content ms-2 ms-sm-0 me-1">
                                      <span class="h6 email-list-item-username me-1" style="font-weight:600rem;">관리자</span>
                                      <span class="email-list-item-subject d-xl-inline-block d-block" id="answer-content"></span>
                                    </div>
                                    <div class="email-list-item-meta ms-auto d-flex align-items-center">
                                      <ul class="list-inline email-list-item-actions text-nowrap">
                                        <li class="ti ti-pencil" id="editBtn" style="margin-right: 2px;"></li>
                                        <li class="ti ti-x" id="deleteBtn"></i>
                                      </ul>
                                    </div>
                                  </div>
                                </li>
                              </ul>
                            </div>
                          </div>
                      </div>
                      <form class="row g-3" onsubmit="return false">
                        <div class="col-lg-10">
                          <input
                            type="text"
                            id="modalRnFEmail"
                            class="form-control"
                            placeholder="답변을 입력해주세요."
                            aria-label="example@domain.com" />
                        </div>
                        <div class="col-lg-2 d-flex align-items-end">
                          <button type="button" class="btn btn-primary" id="submitBtn" onclick="submitBtnCase();" style="padding:10px 20px !important;">답변하기</button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            <!-- /modal -->
           <script src="${pageContext.request.contextPath}/resources/js/app/utils/paging.js"></script>
           <script src="${pageContext.request.contextPath}/resources/js/app/sysadmin/saCsList.js"></script>
           <script>
               var contextPath = "${pageContext.request.contextPath}";
           </script>
