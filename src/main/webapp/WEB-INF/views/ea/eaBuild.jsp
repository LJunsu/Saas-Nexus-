<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    <style>

        .container {
            width: 210mm;  /* A4 width */
            height: 297mm; /* A4 height */
            margin: 0 auto;
            padding: 15mm 20mm;
            box-sizing: border-box;
            background-color: white;
            border: 1px solid #E8E8E8;
            border-radius: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid black;
        }

        th, td {
            padding: 8px;
            text-align: left;
            vertical-align: middle;
        }

        th {
            background-color: #e2e2e2;
        }

        .center {
            text-align: center;
        }

        .right {
            text-align: right;
        }

        .bold {
            font-weight: bold;
        }

        .total {
            background-color: #f9f9f9;
        }
        
        .editable {
            cursor: pointer;
        }
        
        .editing {
            background-color: #f0f0f0;
        }
        

        @page {
            size: A4;
            margin: 0;
        }
        @media print {
            body, .container {
                width: 210mm;
                height: 297mm;
            }
        }
    </style>
<div class="container-xxl flex-grow-1 container-p-y row g-2" style="max-width: 1200px; margin: 0 auto;">
  <div class="container" id="projectForm" method="post">
      <div class="header" style="display: flex; justify-content: space-between; align-items: center;">
          <div class="title" style="flex: 1; text-align: center;">
              <h2 style="margin-bottom: 0; padding-bottom: 0; padding-left:70px;">프로젝트 기획안</h2>
          </div>
          <div class="stamp-box mb-3" style="border: 1px solid black; width: 100px; height: 100px; display: flex; flex-direction: column;">
              <div style="background-color: #e2e2e2; width: 100%; height: 20px; display: flex; justify-content: center; align-items: center; border-bottom: 1px solid black;">
                  <span style="display: inline-block;">승인자</span>
              </div>
              <div style="flex: 1; display: flex; justify-content: center; align-items: center;">
                  <div style="text-align: center;" class="eaReceiver">승인자명</div>
              </div>
          </div>
      </div>
      <table>
          <tr>
              <th>기안자</th>
              <td>${memberName}</td>
              <th>이메일</th>
              <td style="padding-right: 10px; padding-left: 5px;">${memberEmail}</td>
          </tr>
          <tr>
              <th>기안일</th>
              <td id="creation-date"></td>
              <th>문서분류</th>
              <td style="padding-right: 10px; padding-left: 5px;" readOnly>프로젝트 기획안</td>
          </tr>
      </table>
  
      <table>
         <tr>
             <th>작성사유</th>
             <td class="editable creationReason" data-original="" contenteditable="true">작성사유를 입력해주세요.</td>
         </tr>
         <tr>
             <th>작성일자</th>
             <td id="creation-date"></td>
         </tr>
         <tr>
             <th>프로젝트 예상 마감일</th>
             <td class="editable completionDate" id="due-date" data-original=""></td>
         </tr>
     </table>

      <div class="right pb-2 btnDummy">
          <a href="#" class="btn btn-success" id="addBtn" style="padding: 0.4rem 1.0rem;">추가</a>
          <a href="#" class="btn btn-danger" id="delBtn" style="padding: 0.4rem 1.0rem;">삭제</a>
      </div>
      <table id="addTableForm">
          <tr>
            <th>프로젝트명</th>
            <td colspan="4" class="bold center editable projectName" data-original="">프로젝트명을 입력해주세요.</td>
          </tr>
          <tr>
              <th>순 번</th>
              <th>참여 사원명</th>
              <th>사원 이메일</th>
              <th>사원 역할</th>
              <th>비 고</th>
          </tr>
          <tr class="partArray">
              <td class="">1</td>
              <td data-original="">
	           <select name="" class="form-control" id="memberSelect">
	           	<option>선택</option>
	           	 <c:choose>
                    <c:when test="${not empty allMemberList}">
                        <c:forEach items="${allMemberList}" var="mem" >
                         <c:if test="${mem.memberRole == 'ROLE_GROUPADMIN'}">
	           	         <option value="${mem.memberId}" data-email="${mem.memberEmail}">[관리자] ${mem.memberName}</option>                    
                         </c:if>
                         <c:if test="${mem.memberRole == 'ROLE_USER'}">
	           	         <option value="${mem.memberId}" data-email="${mem.memberEmail}">[일반] ${mem.memberName}</option>                    
                         </c:if>
	           	       </c:forEach>
	           	   </c:when>
	           	   <c:otherwise>
	           	      <option>그룹 회원이 존재하지 않습니다.</option>
	           	   </c:otherwise>
	           	 </c:choose>
	           </select>
              </td>
              <td data-original="" id="emailField">사원 이메일</td>
              <td class="editable" data-original="">새 역할</td>
              <td class="editable" data-original="">비고</td>
          </tr>
          <tbody>
          </tbody>
          <tr class="total">
              <td colspan="5" class="bold center">총 <span id="totalNum" class="totalParticipants">1</span>명</td>
          </tr>
      </table>
      <p class="center" style="padding-top: 40px;"><span id="gettingFormatDate"></span> 
       ${memberName} 작성함
      </p>
  </div>
  <div class="col-12 col-lg-3">
    <!-- Pricing Card -->
    <div class="card mb-4">
      <div class="card-header d-flex justify-content-between align-items-center mb+2" style="gap: 10px;">
          <h5 class="card-title mb-0" style="font-weight:600;">결재 제출</h5>
          <button type="button" id="submitButton" class="btn btn-primary waves-effect waves-light" data-bs-toggle="button" aria-pressed="false">결재제출</button>
      </div>
      <div class="card-body">
        <!-- Discounted Price -->
        <div class="mb-3">
          <label class="form-label" for="ecommerce-product-discount-price">수신자</label>
	      <select name="issuePriorityName" class="form-control" id="issuePriorityName">
	      	<option>선택</option>
	      	 <c:choose>
               <c:when test="${not empty memberList}">
                   <c:forEach items="${memberList}" var="mem" >
                    <c:if test="${mem.memberRole == 'ROLE_GROUPADMIN'}">
	      	         <option value="${mem.memberId}">[관리자] ${mem.memberName}</option>                    
                    </c:if>
                    <c:if test="${mem.memberRole == 'ROLE_USER'}">
	      	         <option value="${mem.memberId}">[일반] ${mem.memberName}</option>                    
                    </c:if>
	      	       </c:forEach>
	      	   </c:when>
	      	   <c:otherwise>
	      	      <option>그룹 회원이 존재하지 않습니다.</option>
	      	   </c:otherwise>
	      	 </c:choose>
	      </select>
        </div>
      </div>
    </div>
    <button type="button" class="btn btn-secondary ms-auto" id="autoCompleteBtn" style="margin-left: auto;" onclick="autoComplete()">자동완성</button>
    <!-- /Pricing Card -->
  </div>
</div>
<script src="${pageContext.request.contextPath}/resources/js/app/ea/eaBuild.js"></script>
<script src="https://cdn.jsdelivr.net/npm/html2canvas@1.4.1/dist/html2canvas.min.js"></script>
<script>
    var memberList = JSON.parse('${memberListJson}');
    console.log(memberList);
	const groupId = "${groupId}";
	let selectedMemberId = null; // 전역 변수로 선언하여 다른 JS 파일에서 접근 가능

	const selectElement2 = document.querySelector('#issuePriorityName');
	selectElement2.addEventListener('change', function() {
		 selectedMemberId = selectElement2.value; // 선택된 option의 value, 즉 memberId
		 console.log("셀렉", selectedMemberId);
	});

</script>