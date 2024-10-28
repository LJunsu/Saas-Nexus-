<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.content-wrapper{
	background-color:#ece3ff;
}
</style>
<!-- content -->
            <!-- <div class="container-xxl flex-grow-1 container-p-y">
              <h3 class="py-3 mb-3" style="font-weight: 700; display: flex; justify-content: space-between;">길도연의 홈페이지 제작</h3>

            </div> -->
            <div class="container-xxl flex-grow-1 container-p-y">
              <!-- Header -->
              <div class="row">
                <div class="col-12">
                  <div class="card mb-4">
                    <div class="user-profile-header-banner">
                      <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/pages/company.jpg" alt="Banner image" class="rounded-top" />
                    </div>
                    <div class="user-profile-header d-flex flex-column flex-sm-row text-sm-start text-center mb-4">
                      <div class="flex-shrink-0 mt-n2 mx-sm-0 mx-auto">
                        <img
                          src="https://storage.googleapis.com/java_bucket_hstest1/${groupDetail.memberManagementVO.memberId}/profile.png"
                          alt="user image"
                          class="d-block ms-0 ms-sm-4 rounded user-profile-img mt-2"  style="box-shadow: 0 0.25rem 1.125rem rgba(75, 70, 92, 0.1);"/>
                      </div>
                      <div class="flex-grow-1 mt-3 mt-sm-5">
                        <div
                          class="d-flex align-items-md-end align-items-sm-start align-items-center justify-content-md-between justify-content-start mx-4 flex-md-row flex-column gap-4">
                          <div class="user-profile-info">
                           <div style="display:flex; flex-direction:row; align-items: center;">
                            <h4 style="font-weight: 600; ">${groupDetail.groupName}</h4>
                              <c:choose>
                                <c:when test="${groupDetail.approvalDate != null}">
                                  <span class="badge bg-label-success me-1" style="align-self: center; margin-bottom:13px; margin-left:10px;">승인완료</span>
                                </c:when>
                                <c:otherwise>
                                  <span class="badge bg-label-danger me-1" style="align-self: center; margin-bottom:13px; margin-left:10px;">승인대기</span>
                                </c:otherwise>
                              </c:choose>
                           </div>
                            <ul
                              class="list-inline mb-0 d-flex align-items-center flex-wrap justify-content-sm-start justify-content-center gap-2">
                              <li class="list-inline-item d-flex gap-1">
                                <i class="ti ti-calendar"></i> 가입일 : ${groupDetail.applicationDate}
                              </li>
                            </ul>
                          </div>
                          <div id="chooseApproval">
                          <c:choose>
                            <c:when test="${groupDetail.approvalDate != null}">
                              <a href="javascript:void(0)" class="btn btn-success">
                                <i class="ti ti-check me-1"></i>승인완료
                              </a>
                            </c:when>
                            <c:otherwise>
                            <input type="hidden" id="businessLicenseId" value="${groupDetail.groupId }/businessLicense.png">
                              <a id="businessLicenseBtn" href="" class="btn btn-outline-secondary waves-effect" download>
                                <i class="ti ti-download" style="padding-right: 1px;"></i>
                                사업자 인증 파일
                              </a>
                              <a href="javascript:void(0)" class="btn btn-primary" id="acceptBtn">
                                승인
                              </a>
                              <a href="javascript:void(0)" class="btn btn-danger" id="refusalBtn" data-bs-toggle="modal" data-bs-target="#refusalModal">
                                거절
                              </a>
                            </c:otherwise>
                          </c:choose>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!--/ Header -->

              <!-- Navbar pills -->
              <div class="row">
                <div class="col-md-12">
                  <ul class="nav nav-pills flex-column flex-sm-row mb-4">
                    <li class="nav-item">
                      <a class="nav-link active" href="javascript:void(0);"
                        ><i class="ti-xs ti ti-user-check me-1"></i> 상세 정보</a>
                    </li>
                  </ul>
                </div>
              </div>
              <!--/ Navbar pills -->

              <!-- User Profile Content -->
              <div class="row">
                <div class="col-xl-4 col-lg-5 col-md-5">
                  <!-- About User -->
                  <div class="card mb-4">
                    <div class="card-body">
                      <p class="card-text text-uppercase" style="font-weight: 500;">그룹 상세정보</p>
                      <ul class="list-unstyled mb-4 mt-3">
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-user text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">그룹명:</span> <span>${groupDetail.groupName}</span>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-flag text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">대표명:</span> <span>${groupDetail.memberManagementVO.memberName}</span>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-crown text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">역할:</span> <span class="badge bg-label-success me-1">관리자</span>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-check text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">구독상태:</span> 
                           <c:choose>
                               <c:when test="${groupDetail.useStatus == 'Y'}">
                                   <span class="badge bg-label-primary me-1">구독중</span>
                               </c:when>
                               <c:otherwise>
                                   <span class="badge bg-label-warning me-1">미구독</span>
                               </c:otherwise>
                           </c:choose>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-file-description text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">회사주소:</span> 
                          <span>${groupDetail.groupAddress}</span>
                        </li>
                      </ul>
                      <p class="card-text text-uppercase pt-2" style="font-weight: 500; font-size: 0.9rem;">Contacts</p>
                      <ul class="list-unstyled mb-4 mt-3">
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-phone-call"></i><span class="fw-medium mx-2 text-heading">전화번호:</span>
                          <span>${groupDetail.memberManagementVO.memberTel}</span>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-mail"></i><span class="fw-medium mx-2 text-heading">이메일:</span>
                          <span>${groupDetail.memberManagementVO.memberEmail}</span>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <!--/ About User -->
                </div>
              <!--/ User Profile Content -->
              <div class="col-xl-8 col-lg-7 col-md-7">
                  <div class="card card-action mb-4">
                    <div class="card-header align-items-center">
                      <h5 class="card-action-title mb-0" style="font-weight: 600;">구독 타임라인</h5>
                      <div class="card-action-element"></div>
                    </div>
                    <div class="card-body pb-0">
                      <ul class="timeline ms-1 mb-0">
			             <c:choose>
			               <c:when test="${groupDetail.approvalDate != null}">
			               <c:forEach items="${groupDetail.subscriptionPaymentList }" var="payment">
			                        <li class="timeline-item timeline-item-transparent">
			                          <span class="timeline-point timeline-point-primary"></span>
			                          <div class="timeline-event">
			                            <c:choose>
				                          <c:when test="${payment.paymentId != null}">
				                            <div class="timeline-header">
				                              <h6 class="mb-0">구독결제 ID: ${payment.paymentId } -<span class="purpleColor"> 결제금액: ${payment.paymentAmount }원 </span></h6>
				                            </div>
				                            <p class="mb-2">결제 날짜 : ${payment.paymentDate }</p>
				                          </c:when>
										<c:otherwise>
											  <div class="timeline-header">
					                            <h6 class="mb-0">구독 결제 내역이 없습니다. </h6>
					                          </div>
					                          <p class="mb-2 text-muted">현재까지 구독 결제 내역이 없습니다.</p>
										</c:otherwise>
			                            </c:choose>
			                          </div>
			                        </li>
			                 </c:forEach>
			               </c:when>
			             </c:choose>
                      </ul>
                    </div>
                  </div>
               </div>
            </div>
            <!-- content -->
            
            <!-- modal -->
            <div class="modal fade" id="refusalModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-simple modal-refer-and-earn">
                  <div class="modal-content p-3 p-md-5">
                    <div class="modal-body">
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="reloading();"></button>
                      <div class=" mb-4">
                        <h3 class="mb-2 mt-2" id="cs-title">그룹 거절 사유를 입력해주세요.</h3>
                        <p id="cs-content" class="text-muted mb-5 m-auto">해당 사유는 고객의 이메일로 전송됩니다.</p>
                      </div>
                      <form class="row g-3" onsubmit="return false">
                        <div class="col-lg-10">
                          <input
                            type="text"
                            id="modalRnFEmail"
                            class="form-control"
                            placeholder="사유를 입력해주세요."
                            aria-label="example@domain.com" />
                        </div>
                        <div class="col-lg-2 d-flex align-items-end">
                          <button type="button" class="btn btn-primary text-nowrap" id="submitBtn" onclick="submitBtnRefusal();">전송하기</button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            <!-- /modal -->
            <script>
             let acceptBtn = document.querySelector("#acceptBtn");
             let refusalBtn = document.querySelector("#refusalBtn");
             let chooseApproval = document.querySelector("#chooseApproval");
             let href = null;
             let groupId = null;
             let memberId = "${groupDetail.memberManagementVO.memberId}";
             let memberEmail = "${groupDetail.memberManagementVO.memberEmail}";
             let today = null;
             function reloading(){
          	   location.reload();
             }
             
             function submitBtnRefusal(){
            	 var answerInput = document.getElementById('modalRnFEmail');
                 var data = {
                		 refusalDate: today,
                   	  	 groupId: groupId,
                   	     refusalContent: answerInput.value,
                   	     memberId: 'MEM011',
                   	     memberEmail:memberEmail
                          };
                          fetch(href, {
                              method: 'POST',
                              headers: {
                                  'Content-Type': 'application/json',
                              },
                              body: JSON.stringify(data)
                          })
                          .then(response => response.json())
                          .then(data => {
                              console.log(data);
                              if (data.success) {
                                  alert('거절이 완료되었습니다.');
                                  reloading();
                              } else {
                                  alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                              }
                          })
                          .catch((error) => {
                              console.error('Error:', error);
                          }); 
             }
             
             chooseApproval.addEventListener("click", (e)=>{
            	 
            	 const event = e || window.event;
                 href = window.location.href;
                 today = new Date();  
                 groupId = href ? href.split('/').pop() : null;
                 console.log(groupId);
                 
            	 if(event.target.id === 'acceptBtn'){
            		const confirmAccept = confirm("해당 그룹을 승인하시겠습니까?");
            		if(confirmAccept){
                      var data = {
                   	  	 approvalDate: today,
                   	  	 groupId : groupId
                          };
                          fetch(href, {
                              method: 'PUT',
                              headers: {
                                  'Content-Type': 'application/json',
                              },
                              body: JSON.stringify(data)
                          })
                          .then(response => response.json())
                          .then(data => {
                              console.log(data);
                              if (data.success) {
                                  alert('그룹 회원이 승인되었습니다');
                                  reloading();
                              } else {
                                  alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                              }
                          })
                          .catch((error) => {
                              console.error('Error:', error);
                          });
            		}else {
                        console.log("승인이 취소되었습니다.");
                    }
            	 }
             })
             
             
             let downloadLicense = function(e){
            	e.preventDefault();
            	let contextPath = document.body.dataset.contextPath;
            	let businessLicenseId = $('#businessLicenseId').val();
            	let file = encodeURIComponent(businessLicenseId);
        		let downUrl = `\${contextPath}/gcp/download?fileName=\${file}`

        		window.location.href = downUrl
             }
             
             $(document).on('click', '#businessLicenseBtn', downloadLicense);

            </script>