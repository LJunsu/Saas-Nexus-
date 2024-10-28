<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.eventCard .card::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%; 
    border-bttom-style: solid;
    border-bottom-width: 2px; 
    pointer-events: none;
    transition: border-bottom-width 0.3s ease;
}

.eventCard.primary.active ::after {
    border-bottom-color: #7367f0; 
    border-bottom-width: 4px;
    box-shadow: 0 0.25rem 1.125rem #E5E0F5;
}

.eventCard.warning.active .card::after {
    border-bottom-color: #ff9f43; 
    border-bottom-width: 4px;
     box-shadow: 0 0.25rem 1.125rem #E5E0F5;
}

.eventCard.danger.active .card::after {
    border-bottom-color: #ea5455; 
    border-bottom-width: 4px; 
     box-shadow: 0 0.25rem 1.125rem #E5E0F5;
}

.eventCard.info.active .card::after {
    border-bottom-color: #00cfe8; 
    border-bottom-width: 4px; 
     box-shadow: 0 0.25rem 1.125rem #E5E0F5;
}

.eventCard.primary.active .activeWord {
    color: #7367f0; 
    font-weight:600;
}
.eventCard.warning.active .activeWord {
    color: #ff9f43; 
    font-weight:600;
}
.eventCard.danger.active .activeWord {
    color: #ea5455; 
    font-weight:600;
}
.eventCard.info.active .activeWord {
    color: #00cfe8; 
    font-weight:600;
}

</style>
<!-- Content -->
<div class="container-xxl flex-grow-1 container-p-y" id="lastArea" data-last-id="${last }">
              <div class="d-flex justify-content-between">
                <h3 class="py-3 mb-2">전자결재</h3>
                <button type="button" class="btn btn-primary d-block waves-effect waves-light" data-bs-toggle="button" aria-pressed="false" style="margin:17px 0;"
                onclick="location.href='${pageContext.request.contextPath}/${groupId}/ea/buildUI';">
                  + 전자결재 생성
                </button>
              </div>
              <!-- Card Border Shadow -->
              <div class="row">
                <div class="eventCard primary col-sm-6 col-lg-3 mb-4 active" id="waitingApprovalFromSender">
                  <div class="card card-border-shadow-primary">
                    <div class="card-body">
                      <div class="d-flex align-items-center mb-2 pb-1">
                        <div class="avatar me-2">
                          <span class="avatar-initial rounded bg-label-primary"><i class="ti ti-truck ti-md"></i></span>
                        </div>
                        <h4 class="ms-1 mb-0 activeWord">
                        <c:if test="${eaCountList[0]['EA_RECEIVER_COUNT']==0||eaCountList[0]['EA_RECEIVER_COUNT']==null}">
                           0건
                        </c:if>
                        <c:if test="${eaCountList[0]['EA_RECEIVER_COUNT']>0}">
                           ${eaCountList[0]['EA_RECEIVER_COUNT']}건
                        </c:if>
                        </h4>
                      </div>
                      <p class="mb-0" style="font-weight: 700;">승인대기중인 결재요청</p>
                      <p class="mb-0">
                        <small class="text-muted">확인하지 않은 결재요청 확인 가능합니다</small>
                      </p>
                    </div>
                  </div>
                </div>
                <div class="eventCard warning col-sm-6 col-lg-3 mb-4" id="waitingApproval">
                  <div class="card card-border-shadow-warning">
                    <div class="card-body">
                      <div class="d-flex align-items-center mb-2 pb-1">
                        <div class="avatar me-2">
                          <span class="avatar-initial rounded bg-label-warning"
                            ><i class="ti ti-alert-triangle ti-md"></i
                          ></span>
                        </div>
                        <h4 class="ms-1 mb-0 activeWord">
                        <c:if test="${eaCountList[0]['EA_SENDER_COUNT']==0||eaCountList[0]['EA_SENDER_COUNT']==null}">
                           0건
                        </c:if>
                        <c:if test="${eaCountList[0]['EA_SENDER_COUNT']>0}">
                           ${eaCountList[0]['EA_SENDER_COUNT']}건
                        </c:if>
                         </h4>
                      </div>
                      <p class="mb-0" style="font-weight: 700;">승인요청한 결재</p>
                      <p class="mb-0">
                        <small class="text-muted">승인요청한 결재건 확인 가능합니다</small>
                      </p>
                    </div>
                  </div>
                </div>
                <div class="eventCard danger col-sm-6 col-lg-3 mb-4" id="completeApproval">
                  <div class="card card-border-shadow-danger">
                    <div class="card-body">
                      <div class="d-flex align-items-center mb-2 pb-1">
                        <div class="avatar me-2">
                          <span class="avatar-initial rounded bg-label-danger"
                            ><i class="ti ti-git-fork ti-md"></i
                          ></span>
                        </div>
                        <h4 class="ms-1 mb-0 activeWord">
                        <c:if test="${eaCountList[0]['EA_Y_COUNT']==0||eaCountList[0]['EA_Y_COUNT']==null}">
                           0건
                        </c:if>
                        <c:if test="${eaCountList[0]['EA_Y_COUNT']>0}">
                           ${eaCountList[0]['EA_Y_COUNT']}건
                        </c:if>
                        </h4>
                      </div>
                      <p class="mb-0" style="font-weight: 700;">완료된 결재</p>
                      <p class="mb-0">
                        <small class="text-muted">완료된 결재건 확인 가능합니다</small>
                      </p>
                    </div>
                  </div>
                </div>
                <div class="eventCard info col-sm-6 col-lg-3 mb-4" id="totalApproval">
                  <div class="card card-border-shadow-info">
                    <div class="card-body">
                      <div class="d-flex align-items-center mb-2 pb-1">
                        <div class="avatar me-2">
                          <span class="avatar-initial rounded bg-label-info"><i class="ti ti-clock ti-md"></i></span>
                        </div>
                        <h4 class="ms-1 mb-0 activeWord">${eaCountList[0]['EA_SENDER_COUNT'] + eaCountList[0]['EA_RECEIVER_COUNT'] + eaCountList[0]['EA_Y_COUNT']}건</h4>
                      </div>
                      <p class="mb-0" style="font-weight: 700;">전체 전자결재</p>
                      <p class="mb-0">
                        <small class="text-muted">전체 전자결재 현황을 확인 가능합니다</small>
                      </p>
                    </div>
                  </div>
                </div>
              </div>
              <!--/ Card Border Shadow -->
              <!-- LIST START -->
       <div class="row" id="eaListDiv">
          <c:choose>
            <c:when test="${not empty eaList}">
              <c:forEach items="${eaList}" var="ea" >
                <div class="mb-2 order-0 order-xxl-4" id="${ea['EA_ID']}">
                  <div class="card">
                    <div class="d-flex">
                      <div class="card-header d-flex justify-content-between pb-1">
                        <div class="card-title" style="padding:20px 50px 0px 20px;">
                          <div>
                            <c:choose>
                             <c:when test="${ea['EA_APPROVAL_YN'] eq 'Y'}">
                               <span class="badge bg-label-success mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 완료</span>
                             </c:when>
                             <c:when test="${ea['EA_APPROVAL_YN'] eq 'N'}">
                               <span class="badge bg-label-danger mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 거절</span>
                             </c:when>
                             <c:otherwise>
                               <span class="badge bg-label-warning mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 대기</span>
                             </c:otherwise>
                            </c:choose>
<!--                             <span class="badge bg-label-warning mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 대기</span> -->
<!--                             <span class="badge bg-label-danger mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 거절</span> -->
<!--                             <span class="badge bg-label-success mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 완료</span> -->
                          </div>
                        </div>
                        <div class="card-title" style="padding-top:11px; padding-right: 270px;">
                          <h5 class="m-0 me-2" style="font-size: 1.3rem; font-weight: 600;">${ea['PROJECT_NAME']}</h5>
                          <div>
                            <small class="text-muted pt-1 scrolling-text" style="white-space : nowrap; overflow : hidden; text-overflow : ellipsis; width: 400px; display: inline-block;">
                              ${ea['CREATION_REASON']}
                            </small>
                          </div>
                        </div>
                      </div>
                      <div class="d-flex">
                        <!-- 첫 번째 컨텐츠 -->
                        <div class="flex-fill me-5" style="padding-right: 20px;">
                          <div class="nav-align-top">
                            <div class="tab-content px-2 mx-1 pb-0">
                              <div class="tab-pane fade show active" id="navs-justified-new" role="tabpanel">
                                <ul class="timeline mb-0 pb-1">
                                  <li class="timeline-item ps-4 border-left-dashed pb-1">
                                    <span class="timeline-indicator-advanced timeline-indicator-success">
                                      <i class="ti ti-circle-check"></i>
                                    </span>
                                    <div class="timeline-event px-0 pb-0">
                                      <div class="timeline-header pb-1">
                                        <small class="text-success text-uppercase fw-medium">sender</small>
                                      </div>
                                      <h6 class="mb-1">${ea['EA_SENDER_NAME']}</h6>
                                      <p class="text-muted mb-0" style="white-space : nowrap; overflow : hidden; text-overflow : ellipsis; width: 150px; display: inline-block;">${ea['EA_SENDER_EMAIL']}</p>
                                    </div>
                                  </li>
                                </ul>
                              </div>
                            </div>
                          </div>
                        </div>
                        <!-- 두 번째 컨텐츠 -->
                        <div class="flex-fill me-3">
                          <div class="nav-align-top">
                            <div class="tab-content px-2 mx-1 pb-0">
                              <div class="tab-pane fade show active" id="navs-justified-new" role="tabpanel">
                                <ul class="timeline mb-0 pb-1" style="padding-bottom: 0;">
                                  <li class="timeline-item ps-4 border-left-dashed pb-1">
                                    <span class="timeline-indicator-advanced timeline-indicator-primary">
                                      <i class="ti ti-map-pin"></i>
                                    </span>
                                    <div class="timeline-event px-0 pb-0">
                                      <div class="timeline-header pb-1">
                                        <small class="text-primary text-uppercase fw-medium">Receiver</small>
                                      </div>
                                      <h6 class="mb-1">${ea['EA_RECEIVER_NAME']}</h6>
                                      <p class="text-muted mb-0" style="white-space : nowrap; overflow : hidden; text-overflow : ellipsis; width: 150px; display: inline-block;">${ea['EA_RECEIVER_EMAIL']}</p>
                                    </div>
                                  </li>
                                </ul>
                              </div>
                            </div>
                          </div>
                        </div>
                     </div>
                  </div>
                </div>
              </div>
             </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="d-flex justify-content-center align-items-center" style="height:470px;">
                	<div>
                 		<span class="justify-content-center align-items-center" style="font-size:50px; padding-left:120px;">😯</span>
                 		<span class="d-flex fw-medium" style="display: inline-block; margin-top: 10px; font-size:26px;">전자결재가 존재하지 않습니다!</span>
                 	</div>
                </div>
            </c:otherwise>
           </c:choose>
              <!-- / LIST END -->
         </div>
            <!-- MODAL START -->
            <div class="modal-onboarding modal fade animate__animated" id="onboardImageModal" tabindex="-1"  aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content text-center">
                  <div class="modal-header border-0">
                    <button
                      type="button"
                      class="btn-close"
                      data-bs-dismiss="modal"
                      aria-label="Close"></button>
                  </div>
                  <div class="modal-body p-0" style="width:100%; height:100%;">
                    <div class="onboarding-media" style="padding:0px 15px;">
                      <div class="mx-2">
                        <img
                          src="../../assets/img/illustrations/girl-unlock-password-light.png"
                          alt="girl-unlock-password-light"
                          width="100%"
                          height="100%"
                          class="img-fluid"
                          id="image" />

			             <!-- 승인도장 -->
						 <img id="Stamp" alt="승인도장" src="${cPath}/resources/images/승인도장.png" style="width: 50px; position: absolute; top: 56px; left: 460px; z-index: 9999; opacity: 0.8; display: none;">
						 <!-- /승인도장 -->

                      </div>
                    </div>
                  </div>
                  <div class="modal-footer border-0 g-1">
                    <div style="margin: 0 auto;" id="chooseApproval">
                     <a href="javascript:void(0)" class="btn btn-primary" id="acceptBtn">
                       승인
                     </a>
                     <a href="javascript:void(0)" class="btn btn-danger" id="refusalBtn" data-bs-toggle="modal" data-bs-target="#refusalModal">
                       거절
                     </a>
                     <a href="javascript:void(0)" class="btn btn-success" id="acceptSuccess" style="display:none !important;">
                       <i class="ti ti-check me-1"></i>승인완료
                     </a>
                     </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- MODAL END -->
            <!-- MODAL -->
            <div class="modal fade" id="refusalModal" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-simple modal-refer-and-earn">
                  <div class="modal-content p-3 p-md-5">
                    <div class="modal-body">
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="reloading();"></button>
                      <div class=" mb-4">
                        <h3 class="mb-2 mt-2" id="cs-title">결재 거절 사유를 입력해주세요.</h3>
                        <p id="cs-content" class="text-muted mb-5 m-auto">해당 사유는 사원의 이메일로 전송됩니다.</p>
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
                          <button type="button" class="btn btn-primary" id="submitBtn" onclick="submitBtnRefusal();">전송하기</button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>

            <!-- /MODAL -->
            <!-- /Content -->

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            <script src="${pageContext.request.contextPath}/resources/js/app/ea/eaList.js"></script>
            <script>

               var groupId = "${groupId}";
               var memberId = "${memberId}";

               const cards = document.querySelectorAll('.eventCard');
               const words = document.querySelectorAll('.activeWord');

               const handleClick = (event) => {
                   cards.forEach(card => card.classList.remove('active'));
                   words.forEach(word => word.classList.remove('active'));

                   const clickedCard = event.currentTarget;
                   const clickedWord = event.currentTarget;
                   clickedCard.classList.add('active');
                   clickedWord.classList.add('active');
                   console.log('클릭됨', clickedCard);
               };

               // Add event listeners to each card
               cards.forEach(card => {
                   card.addEventListener('click', handleClick);
               });

           </script>