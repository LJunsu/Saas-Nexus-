<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- content -->
<!-- 그룹 관리 -->
<div>
  <div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="mb-3" style="font-weight: 700; display: flex; justify-content: space-between; padding-top:20px;">
      그룹 관리<a class="mt-1 sysadmin-list purpleColor" style="font-size: 1.1rem; font-weight: 600; text-decoration: underline;" href="${pageContext.request.contextPath }/saGroup/list.do">전체보기</a>
    </h4>
    <div class="row g-4">
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
                       <c:forEach items="${groupList}" var="group" begin="0" end="2" step="1">
                           <tr>
                             <td>
                               <a class="sysadmin-list"><span class="fw-medium">${group.groupName}</span></a>
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
    </div>
  </div>
  <!-- /그룹 관리 -->
  
  <!-- 회원 관리 -->
  <div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">
      회원 관리<a class="mt-1 sysadmin-list purpleColor" style="font-size: 1.1rem; font-weight: 600; text-decoration: underline;" href="${pageContext.request.contextPath }/saMember/list.do">전체보기</a>
    </h4>
    <div class="row g-4">
      <div class="mb-4">
        <div class="table-responsive text-nowrap">
          <table class="table">
            <thead>
              <tr>
                <th>회원 ID</th>
                <th>회원명</th>
                <th>소속그룹</th>
                <th>가입일</th>
                <th>회원구분</th>
                <th>탈퇴상태</th>
              </tr>
            </thead>
            <tbody class="table-border-bottom-0">
              <c:choose>
                <c:when test="${not empty memberList}">
                    <c:forEach items="${memberList}" var="member" begin="0" end="2" step="1">
                        <tr>
                          <td>
                            <span class="fw-medium">${member.memberId}</span>
                          </td>
                          <td>
                            <a class="fw-medium sysadmin-list">${member.memberName}</a>
                          </td>
                          <td>
                            <c:choose>
                                <c:when test="${member.groupManagementVO.groupName != null}">
                                    <span class="fw-medium">${member.groupManagementVO.groupName}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="fw-medium mute">무소속</span>
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
    </div>
  </div>
  <!-- /회원 관리 -->
  
  <!-- 고객센터 -->
  <div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">
      고객센터<a class="mt-1 sysadmin-list purpleColor" style="font-size: 1.1rem; font-weight: 600; text-decoration: underline;" href="${pageContext.request.contextPath }/saSc/list.do">전체보기</a>
    </h4>
    <div class="row g-4">
      <div class="mb-4">
        <div class="table-responsive text-nowrap">
          <table class="table">
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
                  <c:forEach items="${csList}" var="cs" begin="0" end="2" step="1">
                    <tr>
                      <td>
                        <a class="sysadmin-list" href="<c:url value='/saSc/${cs.csId }'/>" data-bs-toggle="modal" data-bs-target="#referAndEarn">
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
    </div>
  </div>
  <!-- /고객센터 -->
  <!-- content -->
</div>