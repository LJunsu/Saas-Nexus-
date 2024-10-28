<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/app-logistics-dashboard.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/apex-charts/apex-charts.css" />
<style>
.content-wrapper{
	background-color:#ece3ff;
}
</style>
<!-- content -->
<div class="container-xxl flex-grow-1 container-p-y" style="margin-top: 20px;">
  <h3 class="py-3 mb-3" style="font-weight: 700; display: flex; justify-content: space-between; color:#000000; padding-left:10px;">서비스 통계
  </h3>
  <!-- 서비스 가입 및 구독률 -->
  <div>
    <div class="card">
      <div class="card-header d-flex align-items-center justify-content-between" style="margin:20px 20px 0px 20px;">
        <div class="card-title mb-0">
          <h4 class="m-0 me-2" style="font-weight:700;">서비스 가입 및 구독현황</h4>
          <p class="text-muted mt-2">사용자의 서비스 회원가입 및 구독 현황을 조회한 통계입니다</p>
        </div>
        <div>
        <div class="dropdown">
          <button
            type="button"
            class="btn btn-label-primary dropdown-toggle selectYear"
            data-bs-toggle="dropdown"
            aria-expanded="false">
            2024년
          </button>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="javascript:void(0);">2024년</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">2023년</a></li>
          </ul>
        </div>
        <div class="dropdown">
          <button
            type="button"
            class="btn btn-label-primary dropdown-toggle selectMonth"
            data-bs-toggle="dropdown"
            aria-expanded="false">
            9월
          </button>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" href="javascript:void(0);">1월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">2월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">3월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">4월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">5월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">6월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">7월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">8월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">9월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">10월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">11월</a></li>
            <li><a class="dropdown-item" href="javascript:void(0);">12월</a></li>
          </ul>
        </div>
        </div>
      </div>
      <div class="card-body">
        <div id="shipmentStatisticsChart"></div>
      </div>
    </div>
  </div>
  <!-- /서비스 가입 및 구독률 -->
  
  <!--   서비스 가입 및 구독률 상세 -->
  <div class="col-md mb-6 mb-md-2 mb-2">
    <div class="accordion mt-3 mb-3" id="accordionExample">
      <div class="card accordion-item showMemDetail1">
        <h2 class="accordion-header" id="headingOne">
          <button
            type="button"
            class="accordion-button"
            data-bs-toggle="collapse"
            data-bs-target="#accordionOne"
            aria-expanded="true"
            aria-controls="accordionOne" style="font-size:18px; font-weight:700; padding: 20px 25px 20px 45px; color:#312D2D;">
            서비스 가입 및 구독현황 상세
          </button>
        </h2>
        <div id="accordionOne" class="accordion-collapse collapse showMemDetail2" data-bs-parent="#accordionExample">
          <div class="accordion-body" style="margin:0px 10px;">
            <div class="mb-2 mt+1">
              <div class="table-responsive text-nowrap">
                <table class="table">
                  <thead>
                    <tr>
                      <th>분류</th>
                      <th>회원ID</th>
                      <th>회원명</th>
                      <th>회원 이메일</th>
                      <th>가입일</th>
                      <th>회원구분</th>
                    </tr>
                  </thead>
                  <tbody class="table-border-bottom-0 memDetailBox">
                    <tr>
                      <td class="mt-2" colspan="6" style="text-align:center; padding-top:20px;">
                        선택된 날짜가 없습니다.
                      </td>
                    </tr>
                  </tbody>
                </table>
                  <table class="table" style="margin-top:20px;">
                  <thead>
                    <tr>
                      <th>분류</th>
                      <th>회사명</th>
                      <th>대표명</th>
                      <th>회사주소</th>
                      <th>구독일</th>
                      <th>승인여부</th>
                    </tr>
                  </thead>
                  <tbody class="table-border-bottom-0 memDetailBox2">
                    <tr>
                      <td class="mt-2" colspan="6" style="text-align:center; padding-top:20px;">
                        선택된 날짜가 없습니다.
                      </td>
                    </tr>
                  </tbody>
                  </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--   /서비스 가입 및 구독율 상세 -->
  

  <!-- 시간대별 서비스 사용 지표 -->
<!--     <div class="mb-4">   col-xl-8 col-12 
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center" style="margin:20px 20px 0px 20px;">
          <div class="card-title mb-0">
            <h4 class="m-0 me-2" style="font-weight:700;">시간대별 서비스 사용 지표</h4>
            <p class="text-muted mt-2">사용자의 활동지표를 시간대별로 나타낸 통계입니다</p>
          </div>
          <div class="dropdown">
            <button
              type="button"
              class="btn btn-label-primary dropdown-toggle"
              data-bs-toggle="dropdown"
              aria-expanded="false">
              Angular
            </button>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="javascript:void(0);">Angular</a></li>
              <li><a class="dropdown-item" href="javascript:void(0);">Vue</a></li>
              <li><a class="dropdown-item" href="javascript:void(0);">React</a></li>
            </ul>
          </div>
        </div>
        <div class="card-body">
          <div id="scatterChart"></div>
        </div>
      </div>
    </div> -->
  <!-- /시간대별 서비스 사용 지표 -->
  <!-- 전체 서비스 사용 통계 -->
    <div class="mb-4">
		<div class="card"  style="height:100%;">
			<div class="card-header d-flex justify-content-between align-items-center" style="margin:20px 20px 0px 20px;">
			  <div class="card-title mb-0">
			    <h4 class="m-0 me-2" style="font-weight:700;">프로젝트 메뉴 사용 통계</h4>
			    <p class="text-muted mt-2">프로젝트 메뉴 서비스 전체 사용 통계입니다</p>
			  </div>
			</div>
			<div class="card-body">
			  <div id="horizontalBarChart"></div>
			</div>
		</div>
    </div>
  <!-- /전체 서비스 사용 통계 -->

    
    
  <div class="row" style="display:flex;">
  <!-- 구독 수익액 -->
    <div class="col-lg-6 mb-4 order-md-0 order-lg-0">
      <div class="card">
        <div class="card-header d-flex justify-content-between" style="margin:20px 20px 0px 20px;">
          <div>
            <h4 class="card-title mb-0" style="font-weight:700;">구독 수익액
              <span class="totalPay" style="padding-left:6px; color:#7367f0;">총 ${totalPay }원</span>
            </h4>
            <p class="text-muted mt-2">구독으로 발생한 수익 현황입니다</p>
          </div>
        </div>
        <div class="card-body">
          <div id="lineChart"></div>
        </div>                  
      </div>
    </div>
  <!-- /구독 수익액 -->  
  
  <!-- 그룹별 구독 유지률 -->
    <div class="col-lg-6 mb-4 order-md-0 order-lg-0">
      <div class="card" >
        <div class="card-header d-flex justify-content-between" style="margin:20px 20px 0px 20px;">
          <div>
            <h4 class="card-title mb-0" style="font-weight:700; display:flex;">그룹별 구독 유지률
              <span class="percentagePay" style="padding-left:6px; color:#7367f0; display:none;">
              </span>
            </h4>
            <p class="text-muted mt-2">그룹별 구독 유지률 현황입니다</p>
          </div>
          <div class="dropdown">
            <button
              type="button"
              class="btn btn-label-primary dropdown-toggle"
              data-bs-toggle="dropdown"
              aria-expanded="false">
              그룹 필터
            </button>
            <ul class="dropdown-menu groupDropDown" style="max-height: 200px; overflow-y: auto;">
              <c:choose>
                <c:when test="${not empty groupList}">
                  <c:forEach items="${groupList}" var="group" >             
                    <li><a class="dropdown-item" href="javascript:void(0);" data-id-value="${group.groupId}">${group.groupName}</a></li>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                </c:otherwise>
              </c:choose>
            </ul>
          </div>
        </div>
        <div class="card-body">
          <h5 class="text-muted paymentGroupNone" style="display : flex; align-items:center; text-align:center; justify-content:center; padding:103px 0px;">
            조회할 그룹을 선택해주세요.
          </h5>
          <div id="lineChart2" style="display:none;"></div>
        </div>                  
      </div>
    </div>
  <!-- /그룹별 구독 유지률 -->
  </div>
  
  <div class="row">
  <!-- /서비스 수익 상세 -->
    <div class="col-md mb-md-2">
      <div class="accordion" id="accordionExample">
        <div class="card accordion-item">
          <h2 class="accordion-header" id="headingOne">
            <button type="button" class="accordion-button collapsed showPayDetail1" data-bs-toggle="collapse" 
             data-bs-target="#accordionOne" aria-expanded="false" aria-controls="accordionOne" style="font-size:18px; font-weight:700; padding: 20px 25px 20px 45px; color:#312D2D;">
             <span class="monthChange" style="padding-right:5px; color:#7367F0;">9월 </span> 서비스 수익 상세
            </button>
          </h2>
          <div id="accordionOne" class="accordion-collapse collapse showPayDetail2" data-bs-parent="#accordionExample" style="">
            <div class="accordion-body">
              <div class="mb-2 mt+1">
                <div class="table-responsive text-nowrap">
                  <table class="table">
                    <thead>
                      <tr>
                        <th>그룹ID</th>
                        <th>그룹명</th>
                        <th>결제금액</th>
                        <th>결제일</th>
                      </tr>
                    </thead>
                    <tbody class="table-border-bottom-0 paymentDetailBox">
                    <c:choose>
                      <c:when test="${not empty paymentDetailList}">
                        <c:forEach items="${paymentDetailList}" var="payment" >  
                          <tr>
                            <td>
                              <a class="sysadmin-list"><span class="fw-medium">${payment['GROUP_ID']}</span></a>
                            </td>
                            <td>
                              <span class="fw-medium">${payment['GROUP_NAME']}</span>
                            </td>
                            <td>
                              <span class="fw-medium">${payment['PAYMENT_AMOUNT']}원</span>
                            </td>
                            <td class="fw-medium">${payment['PAYMENT_DATE']}</td>
                          </tr>
                        </c:forEach>
                      </c:when>
                    </c:choose>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- /서비스 수익 상세 -->
    
  <!-- 그룹별 서비스 사용 통계 -->
    <div class="col-md mb-md-2">
      <div class="accordion" id="accordionExample">
        <div class="card accordion-item">
          <h2 class="accordion-header" id="headingOne">
            <button type="button" class="accordion-button collapsed showPayDetail3" data-bs-toggle="collapse" 
             data-bs-target="#accordionTwo" aria-expanded="false" aria-controls="accordionTwo" style="font-size:18px; font-weight:700; padding: 20px 25px 20px 45px; color:#312D2D;">
              그룹별 서비스 사용 통계
            </button>
          </h2>
          <div id="accordionTwo" class="accordion-collapse collapse showPayDetail4" data-bs-parent="#accordionExample" style="">
            <div class="accordion-body">
              <div class="">
                <div class="">
                  <div class="card-body totalDiv" style="padding:0px 25px;">
                    <h5 class="text-muted" style="display : flex; align-items:center; justify-content:center; padding-top:15px;">조회할 그룹을 선택해주세요.</h5>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  <!-- /그룹별 서비스 사용 통계 -->
  </div>
 <div class="d-flex pt-2 justify-content-end">Powered by ApexCharts API</div>
</div>
<!-- /content -->
<script src="${pageContext.request.contextPath }/resources/nexus/assets/js/charts-apex.js"></script>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/apex-charts/apexcharts.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/sysadmin/saSubList.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/sysadmin/saSubList2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/sysadmin/saSubList3.js"></script>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/js/cards-analytics.js"></script>
<script>
	const memCountByRegDt = [
	    <c:forEach var="count" items="${memCountByRegDt}" varStatus="status">
	        ${count}<c:if test="${!status.last}">,</c:if>
	    </c:forEach>
	];
	const subCountByRegDt = [
	    <c:forEach var="count" items="${subCountByRegDt}" varStatus="status">
	        ${count}<c:if test="${!status.last}">,</c:if>
	    </c:forEach>
	];
	const paymentAmount = [
	  <c:forEach var="count" items="${paymentAmount}" varStatus="status">
	    ${count}<c:if test="${!status.last}">,</c:if>
	  </c:forEach>
	];
</script>