<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />


			<div class="container-xxl flex-grow-1 container-p-y">
			<div class="card mb-4">
                    <div class="user-profile-header-banner">
                      <img src="https://storage.googleapis.com/java_bucket_hstest1/${princiapl.realUser.memberId}/profile.png" alt="Banner image" class="rounded-top">
                    </div>
                    <div class="user-profile-header d-flex flex-column flex-sm-row text-sm-start text-center mb-4">
                      <div class="flex-shrink-0 mt-n2 mx-sm-0 mx-auto">
                        <img src="https://storage.googleapis.com/java_bucket_hstest1/${princiapl.realUser.memberId}/profile.png" 
                        alt="user image" class="mt-2 d-block ms-0 ms-sm-4 rounded user-profile-img" onclick="changeProfileImg('${princiapl.realUser.memberId}')" style="box-shadow: 0 0.25rem 1.125rem rgba(75, 70, 92, 0.1);">
                        <input type="file" id="profileImgInput" style="display:none;" onchange="uploadProfileImage('${princiapl.realUser.memberId})">
                      </div>
                      <div class="flex-grow-1 mt-3 mt-sm-5">
                        <div class="d-flex align-items-md-end align-items-sm-start align-items-center justify-content-md-between justify-content-start mx-4 flex-md-row flex-column gap-4">
                          <div class="user-profile-info">
                            <h4>${memberInfo.memberName}</h4>
                            <ul class="list-inline mb-0 d-flex align-items-center flex-wrap justify-content-sm-start justify-content-center gap-2">
                              <li class="list-inline-item d-flex gap-1">
                                <i class="ti ti-color-swatch"></i> UX Designer
                              </li>
                              <li class="list-inline-item d-flex gap-1" >
                              <i class="ti ti-map-pin" id="addressSub">
                              </i>
                              </li>
                              <script type="text/javascript">
							  let addressSub = document.querySelector("#addressSub");
							  var address = '${memberInfo.memberAddress1}';
							    // 앞 두 글자 추출
							    var city = address.substring(0, 2);
							    // JSP에 city 값을 표시할 경우
							    addressSub.innerText = city;
							  </script>
                              <li class="list-inline-item d-flex gap-1">
							<i class="ti ti-calendar"></i> <span id="joinDate"></span>

								<!-- 배너에 표기되는 joindate  ex)April 2024 로표기되게 ㄱㄱ -->
								<script type="text/javascript">

								    // dateStr에 memberJoinDate 값을 전달
								    let dateStr = '${memberInfo.memberJoinDate}';

								    // Date 객체로 변환
								    let date = new Date(dateStr);

								    // 월 이름 배열
								    let monthNames = [
								        "January", "February", "March", "April", "May", "June",
								        "July", "August", "September", "October", "November", "December"
								    ];

								    let month = monthNames[date.getMonth()];
								    let year = date.getFullYear();

								    // 원하는 형식으로 변환
								    let formattedDate = month + " " + year;

								    // formattedDate를 HTML에 삽입
								    document.getElementById('joinDate').textContent = formattedDate;
								</script>



                              </li>
                            </ul>
                          </div>
                          <a href="javascript:void(0)" class="btn btn-primary waves-effect waves-light">
                            <i class="ti ti-check me-1"></i>Connected
                          </a>
                        </div>
                      </div>
                    </div>
                  </div>
				</div>
				<div class="container-xxl flex-grow-1 container-p-y">
				  <ul class="nav nav-pills flex-column flex-sm-row mb-4">
                    <li class="nav-item">
                      <a class="nav-link active" href="javascript:void(0);"><i class="ti-xs ti ti-user-check me-1"></i> 상세 정보</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="pages-profile-teams.html"><i class="ti-xs ti ti-users me-1"></i> 그룹 회원</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="pages-profile-projects.html"><i class="ti-xs ti ti-layout-grid me-1"></i> 프로젝트</a>
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="pages-profile-connections.html"><i class="ti-xs ti ti-link me-1"></i> 캘린더</a>
                    </li>
                  </ul>
				</div>





   			<div class="container-xxl flex-grow-1 container-p-y">
              <h4 class="py-3 mb-4"><span class="text-muted fw-light">User</span> Info</h4>
              <div class="row">
                <!-- User Sidebar -->
                <div class="col-xl-4 col-lg-5 col-md-5 order-1 order-md-0">
                  <!-- User Card -->
                  <div class="card mb-4">
                    <div class="card-body">
                      <div class="user-avatar-section">
                        <div class="d-flex align-items-center flex-column">
                          <img
                            class="img-fluid rounded mb-3 pt-1 mt-4"
                            src="${pageContext.request.contextPath}/resources/images/스크린샷 2024-04-17 135844.png"
                            height="100"
                            width="100"
                            alt="User avatar" />
                          <div class="user-info text-center">
                            <h4 class="mb-2">${princiapl.realUser.memberName}(${princiapl.realUser.memberId})</h4>
                            <span class="badge bg-label-success">${memberInfo.memberRole }</span>
                          </div>
                        </div>
                      </div>
                      <div class="d-flex justify-content-around flex-wrap mt-3 pt-3 pb-4 border-bottom">
                        <div class="d-flex align-items-start me-4 mt-3 gap-2">
                          <span class="badge bg-label-primary p-2 rounded"><i class="ti ti-checkbox ti-sm"></i></span>
                          <div>
                            <p class="mb-0 fw-medium">1.23k</p>
                            <small>Tasks Done</small>
                          </div>
                        </div>
                        <div class="d-flex align-items-start mt-3 gap-2">
                          <span class="badge bg-label-primary p-2 rounded"><i class="ti ti-briefcase ti-sm"></i></span>
                          <div>
                            <p class="mb-0 fw-medium">568</p>
                            <small>Projects Done</small>
                          </div>
                        </div>
                      </div>
                      <p class="mt-4 small text-uppercase text-muted">Details</p>
                      <div class="info-container">
                        <ul class="list-unstyled">
                          <li class="mb-2">
                            <span class="fw-medium me-1">Username:</span>
                            <span>${princiapl.realUser.memberName}</span>
                          </li>
                          <li class="mb-2 pt-1">
                            <span class="fw-medium me-1">Email:</span>
                            <span>${memberInfo.memberEmail}</span>
                          </li>
                          <li class="mb-2 pt-1">
                            <span class="fw-medium me-1">Address:</span>
<%--                             <span class="badge bg-label-success">${memberInfo.memberAddress1 } ${memberInfo.memberAddress2 }</span> --%>
                            <span>${memberInfo.memberAddress1 } ${memberInfo.memberAddress2 }</span>
                          </li>
                          <li class="mb-2 pt-1">
                            <span class="fw-medium me-1">Tel:</span>
                            <span>${memberInfo.memberTel }</span>
                          </li>
                          <li class="mb-2 pt-1">
                            <span class="fw-medium me-1">Languages:</span>
                            <span>Korea</span>
                          </li>
                          <li class="pt-1">
                            <span class="fw-medium me-1">JoinDate:</span>
                            <span>${memberInfo.memberJoinDate }</span>
                          </li>
                        </ul>
                        <div class="d-flex justify-content-center">

                          <a
                            href="javascript:;"
                            class="btn btn-primary me-3"
                            data-bs-target="#editUser"
                            data-bs-toggle="modal"
                            >Edit</a
                          >
                          <a href="javascript:;" class="btn btn-label-danger suspend-user">Suspended</a>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- /User Card -->
                </div>
                <!--/ User Sidebar -->

                <!-- User Content -->
                <div class="col-xl-8 col-lg-7 col-md-7 order-0 order-md-1">
                  <!-- User Pills -->
                  <ul class="nav nav-pills flex-column flex-md-row mb-4">
                    <li class="nav-item">
                      <a class="nav-link active" href="javascript:void(0);"
                        ><i class="ti ti-user-check ti-xs me-1"></i>Account</a
                      >
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="app-user-view-security.html"
                        ><i class="ti ti-lock ti-xs me-1"></i>Security</a
                      >
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="app-user-view-billing.html"
                        ><i class="ti ti-currency-dollar ti-xs me-1"></i>Billing & Plans</a
                      >
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="app-user-view-notifications.html"
                        ><i class="ti ti-bell ti-xs me-1"></i>Notifications</a
                      >
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="app-user-view-connections.html"
                        ><i class="ti ti-link ti-xs me-1"></i>Connections</a
                      >
                    </li>
                  </ul>
                  <!--/ User Pills -->

                  <!-- Project table -->
                  <div class="card mb-4">
                    <h5 class="card-header">User's Projects List</h5>
                    <div class="table-responsive mb-3">
                      <table class="table datatable-project border-top">
                        <thead>
                          <tr>
                            <th></th>
                            <th>Project</th>
                            <th class="text-nowrap">Total Task</th>
                            <th>Progress</th>
                            <th>Hours</th>
                          </tr>
                        </thead>
                      </table>
                    </div>
                  </div>
                  <!-- /Project table -->

                  <!-- Activity Timeline -->
                  <div class="card mb-4">
                    <h5 class="card-header">User Activity Timeline</h5>
                    <div class="card-body pb-0">
                      <ul class="timeline mb-0">
                        <li class="timeline-item timeline-item-transparent">
                          <span class="timeline-point timeline-point-primary"></span>
                          <div class="timeline-event">
                            <div class="timeline-header mb-1">
                              <h6 class="mb-0">12 Invoices have been paid</h6>
                              <small class="text-muted">12 min ago</small>
                            </div>
                            <p class="mb-2">Invoices have been paid to the company</p>
                            <div class="d-flex">
                              <a href="javascript:void(0)" class="me-3">
                                <img
                                  src="../../assets/img/icons/misc/pdf.png"
                                  alt="PDF image"
                                  width="15"
                                  class="me-2" />
                                <span class="fw-medium text-heading">invoices.pdf</span>
                              </a>
                            </div>
                          </div>
                        </li>
                        <li class="timeline-item timeline-item-transparent">
                          <span class="timeline-point timeline-point-warning"></span>
                          <div class="timeline-event">
                            <div class="timeline-header mb-1">
                              <h6 class="mb-0">Client Meeting</h6>
                              <small class="text-muted">45 min ago</small>
                            </div>
                            <p class="mb-2">Project meeting with john @10:15am</p>
                            <div class="d-flex flex-wrap">
                              <div class="avatar me-3">
                                <img src="../../assets/img/avatars/3.png" alt="Avatar" class="rounded-circle" />
                              </div>
                              <div>
                                <h6 class="mb-0">Lester McCarthy (Client)</h6>
                                <small>CEO of Pixinvent</small>
                              </div>
                            </div>
                          </div>
                        </li>
                        <li class="timeline-item timeline-item-transparent">
                          <span class="timeline-point timeline-point-info"></span>
                          <div class="timeline-event">
                            <div class="timeline-header mb-1">
                              <h6 class="mb-0">Create a new project for client</h6>
                              <small class="text-muted">2 Day Ago</small>
                            </div>
                            <p class="mb-2">5 team members in a project</p>
                            <div class="d-flex align-items-center avatar-group">
                              <div
                                class="avatar pull-up"
                                data-bs-toggle="tooltip"
                                data-popup="tooltip-custom"
                                data-bs-placement="top"
                                title="Vinnie Mostowy">
                                <img src="../../assets/img/avatars/5.png" alt="Avatar" class="rounded-circle" />
                              </div>
                              <div
                                class="avatar pull-up"
                                data-bs-toggle="tooltip"
                                data-popup="tooltip-custom"
                                data-bs-placement="top"
                                title="Marrie Patty">
                                <img src="../../assets/img/avatars/12.png" alt="Avatar" class="rounded-circle" />
                              </div>
                              <div
                                class="avatar pull-up"
                                data-bs-toggle="tooltip"
                                data-popup="tooltip-custom"
                                data-bs-placement="top"
                                title="Jimmy Jackson">
                                <img src="../../assets/img/avatars/9.png" alt="Avatar" class="rounded-circle" />
                              </div>
                              <div
                                class="avatar pull-up"
                                data-bs-toggle="tooltip"
                                data-popup="tooltip-custom"
                                data-bs-placement="top"
                                title="Kristine Gill">
                                <img src="../../assets/img/avatars/6.png" alt="Avatar" class="rounded-circle" />
                              </div>
                              <div
                                class="avatar pull-up"
                                data-bs-toggle="tooltip"
                                data-popup="tooltip-custom"
                                data-bs-placement="top"
                                title="Nelson Wilson">
                                <img src="../../assets/img/avatars/4.png" alt="Avatar" class="rounded-circle" />
                              </div>
                            </div>
                          </div>
                        </li>
                        <li class="timeline-item timeline-item-transparent border-transparent">
                          <span class="timeline-point timeline-point-success"></span>
                          <div class="timeline-event">
                            <div class="timeline-header mb-1">
                              <h6 class="mb-0">Design Review</h6>
                              <small class="text-muted">5 days Ago</small>
                            </div>
                            <p class="mb-0">Weekly review of freshly prepared design for our new app.</p>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                  <!-- /Activity Timeline -->

                </div>
                <!--/ User Content -->
              </div>





 <!--                                        수정 모달                                  -->
             <div class="modal fade" id="editUser" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-simple modal-edit-user">
                  <div class="modal-content p-3 p-md-5">
                    <div class="modal-body">
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      <div class="text-center mb-4">
                        <h3 class="mb-2">정보 수정</h3>
                        <p class="text-muted">Update your Info</p>
                      </div>
                      <form id="editUserForm" class="row g-3" onsubmit="return false">


						<div class="col-12 col-md-6">
                          <label class="form-label" for="basic-icon-default-fullname">User Name</label>
                          <div class="input-group input-group-merge">
                            <span id="basic-icon-default-fullname2" class="input-group-text"><i class="ti ti-user"></i></span>
                            <input type="text" class="form-control" id="basic-icon-default-fullname" placeholder="이름을 입력해주세요" aria-label="John Doe" aria-describedby="basic-icon-default-fullname2">
                          </div>
                        </div>

						<div class="col-12 col-md-6" >
						<label class="form-label" for="basic-icon-default-fullname">E-mail</label>
						<div class="input-group input-group-merge ">
                            <span class="input-group-text"><i class="ti ti-mail"></i></span>
                            <input type="text" id="basic-icon-default-email" class="form-control" placeholder="john.doe" aria-label="john.doe" aria-describedby="basic-icon-default-email2">
                            <span id="basic-icon-default-email2" class="input-group-text">@example.com</span>
                         </div>
						</div>

                        <div class="col-12 col-md-6">
                          <label class="form-label" for="modalEditUserStatus">Status</label>
                          <select
                            id="modalEditUserStatus"
                            name="modalEditUserStatus"
                            class="select2 form-select"
                            aria-label="Default select example">
                            <option selected>Status</option>
                            <option value="1">Active</option>
                            <option value="2">Inactive</option>
                            <option value="3">Suspended</option>
                          </select>
                        </div>



                        <div class="col-12 col-md-6">
                          <label class="form-label" for="modalEditTaxID">Tax ID</label>
                          <input
                            type="text"
                            id="modalEditTaxID"
                            name="modalEditTaxID"
                            class="form-control modal-edit-tax-id"
                            placeholder="123 456 7890" />
                        </div>
                        <div class="col-12 col-md-6">
                          <label class="form-label" for="modalEditUserPhone">Phone Number</label>
                          <div class="input-group">
                            <span class="input-group-text">US (+1)</span>
                            <input
                              type="text"
                              id="modalEditUserPhone"
                              name="modalEditUserPhone"
                              class="form-control phone-number-mask"
                              placeholder="202 555 0111" />
                          </div>
                        </div>
                        <div class="col-12 col-md-6">
                          <label class="form-label" for="modalEditUserLanguage">Language</label>
                          <select
                            id="modalEditUserLanguage"
                            name="modalEditUserLanguage"
                            class="select2 form-select"
                            multiple>
                            <option value="">Select</option>
                            <option value="english" selected>English</option>
                            <option value="spanish">Spanish</option>
                            <option value="french">French</option>
                            <option value="german">German</option>
                            <option value="dutch">Dutch</option>
                            <option value="hebrew">Hebrew</option>
                            <option value="sanskrit">Sanskrit</option>
                            <option value="hindi">Hindi</option>
                          </select>
                        </div>
                        <div class="col-12 col-md-6">
                          <label class="form-label" for="modalEditUserCountry">Country</label>
                          <select
                            id="modalEditUserCountry"
                            name="modalEditUserCountry"
                            class="select2 form-select"
                            data-allow-clear="true">
                            <option value="">Select</option>
                            <option value="Australia">Australia</option>
                            <option value="Bangladesh">Bangladesh</option>
                            <option value="Belarus">Belarus</option>
                            <option value="Brazil">Brazil</option>
                            <option value="Canada">Canada</option>
                            <option value="China">China</option>
                            <option value="France">France</option>
                            <option value="Germany">Germany</option>
                            <option value="India">India</option>
                            <option value="Indonesia">Indonesia</option>
                            <option value="Israel">Israel</option>
                            <option value="Italy">Italy</option>
                            <option value="Japan">Japan</option>
                            <option value="Korea">Korea, Republic of</option>
                            <option value="Mexico">Mexico</option>
                            <option value="Philippines">Philippines</option>
                            <option value="Russia">Russian Federation</option>
                            <option value="South Africa">South Africa</option>
                            <option value="Thailand">Thailand</option>
                            <option value="Turkey">Turkey</option>
                            <option value="Ukraine">Ukraine</option>
                            <option value="United Arab Emirates">United Arab Emirates</option>
                            <option value="United Kingdom">United Kingdom</option>
                            <option value="United States">United States</option>
                          </select>
                        </div>
                        <div class="col-12">
                          <label class="switch">
                            <input type="checkbox" class="switch-input" />
                            <span class="switch-toggle-slider">
                              <span class="switch-on"></span>
                              <span class="switch-off"></span>
                            </span>
                            <span class="switch-label">Use as a billing address?</span>
                          </label>
                        </div>
                        <div class="col-12 text-center">
                          <button type="submit" class="btn btn-primary me-sm-3 me-1">Submit</button>
                          <button
                            type="reset"
                            class="btn btn-label-secondary"
                            data-bs-dismiss="modal"
                            aria-label="Close">
                            Cancel
                          </button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>




















<script src="${pageContext.request.contextPath}/resources/js/app/mypage.js" ></script>
