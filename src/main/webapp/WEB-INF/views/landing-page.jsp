<%@page import="kr.or.nexus.vo.GroupManagementVO"%>
<%@page import="kr.or.nexus.vo.MemberManagementVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html
  lang="en"
  class="light-style layout-navbar-fixed layout-wide"
  dir="ltr"
  data-theme="theme-default"
  data-assets-path="${pageContext.request.contextPath }/resources/nexus/assets/"
  data-template="front-pages">
  <head>
    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

    <title>Landing Page - Front Pages | Vuexy - Bootstrap Admin Template</title>

    <meta name="description" content="" />

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath }/resources/nexus/assets/img/favicon/favicon.ico" />

    <!-- Fonts -->
    <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&ampdisplay=swap"
      rel="stylesheet" />

    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/fonts/tabler-icons.css" />

    <!-- Core CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/core.css" class="template-customizer-core-css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/theme-default.css" class="template-customizer-theme-css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/css/demo.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/front-page.css" />
    <!-- Vendors CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/node-waves/node-waves.css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/nouislider/nouislider.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/swiper/swiper.css" />

    <!-- Page CSS -->

    <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/front-page-landing.css" />

    <!-- Helpers -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/helpers.js"></script>
    <!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
    <!--? Template customizer: To hide customizer set displayCustomizer value false in config.js.  -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/template-customizer.js"></script>
    <!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/js/front-config.js"></script>
  </head>

  <body>
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/dropdown-hover.js"></script>
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/mega-dropdown.js"></script>
	<%
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");
		GroupManagementVO group = (GroupManagementVO) session.getAttribute("group");
	%>
	<c:set var="cPath" value="${pageContext.request.contextPath }" scope="application"/>
    <!-- Navbar: Start -->
    <input type="hidden" id="contextPath" value="${cPath }">
    <nav class="layout-navbar shadow-none py-0">
      <div class="container">
        <div class="navbar navbar-expand-lg landing-navbar px-3 px-md-4">
          <!-- Menu logo wrapper: Start -->
          <div class="navbar-brand app-brand demo d-flex py-0 py-lg-2 me-4">
            <!-- Mobile menu toggle: Start-->
            <button
              class="navbar-toggler border-0 px-0 me-2"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation">
              <i class="ti ti-menu-2 ti-sm align-middle"></i>
            </button>
            <!-- Mobile menu toggle: End-->
            <a href="landing-page.html" class="app-brand-link">
              <span class="app-brand-logo demo">
                <svg width="32" height="22" viewBox="0 0 32 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M0.00172773 0V6.85398C0.00172773 6.85398 -0.133178 9.01207 1.98092 10.8388L13.6912 21.9964L19.7809 21.9181L18.8042 9.88248L16.4951 7.17289L9.23799 0H0.00172773Z"
                    fill="#7367F0" />
                  <path
                    opacity="0.06"
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M7.69824 16.4364L12.5199 3.23696L16.5541 7.25596L7.69824 16.4364Z"
                    fill="#161616" />
                  <path
                    opacity="0.06"
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M8.07751 15.9175L13.9419 4.63989L16.5849 7.28475L8.07751 15.9175Z"
                    fill="#161616" />
                  <path
                    fill-rule="evenodd"
                    clip-rule="evenodd"
                    d="M7.77295 16.3566L23.6563 0H32V6.88383C32 6.88383 31.8262 9.17836 30.6591 10.4057L19.7824 22H13.6938L7.77295 16.3566Z"
                    fill="#7367F0" />
                </svg>
              </span>
              <span class="app-brand-text demo menu-text fw-bold ms-2 ps-1">Nexus</span>
            </a>
          </div>
          <!-- Menu logo wrapper: End -->
          <!-- Menu wrapper: Start -->
          <div class="collapse navbar-collapse landing-nav-menu" id="navbarSupportedContent">
            <button
              class="navbar-toggler border-0 text-heading position-absolute end-0 top-0 scaleX-n1-rtl"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent"
              aria-expanded="false"
              aria-label="Toggle navigation">
              <i class="ti ti-x ti-sm"></i>
            </button>
            <ul class="navbar-nav me-auto">
              <li class="nav-item">
                <a class="nav-link fw-medium" aria-current="page" href="landing-page.html#landingHero">넥서스</a>
              </li>
              <li class="nav-item">
                <a class="nav-link fw-medium" href="landing-page.html#landingFeatures">서비스 소개</a>
              </li>
           </ul>
          </div>
          <div class="landing-menu-overlay d-lg-none"></div>
          <!-- Menu wrapper: End -->
          <!-- Toolbar: Start -->
          <ul class="navbar-nav flex-row align-items-center ms-auto">
            <!-- Style Switcher -->
            <li class="nav-item dropdown-style-switcher dropdown me-2 me-xl-0">
              <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown">
                <i class="ti ti-sm"></i>
              </a>
              <ul class="dropdown-menu dropdown-menu-end dropdown-styles">
                <li>
                  <a class="dropdown-item" href="javascript:void(0);" data-theme="light">
                    <span class="align-middle"><i class="ti ti-sun me-2"></i>Light</span>
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="javascript:void(0);" data-theme="dark">
                    <span class="align-middle"><i class="ti ti-moon me-2"></i>Dark</span>
                  </a>
                </li>
                <li>
                  <a class="dropdown-item" href="javascript:void(0);" data-theme="system">
                    <span class="align-middle"><i class="ti ti-device-desktop me-2"></i>System</span>
                  </a>
                </li>
              </ul>
            </li>
            <c:if test="${not empty user }">
            <li class="nav-item navbar-dropdown dropdown-user dropdown"><a
					class="nav-link dropdown-toggle hide-arrow"
					href="javascript:void(0);" data-bs-toggle="dropdown">
						<div class="avatar avatar-online">
							<img
								src="https://storage.googleapis.com/java_bucket_hstest1/${user.memberId}/profile.png"
								alt class="rounded-circle" />
						</div>
				</a>
					<ul class="dropdown-menu dropdown-menu-end">
						<li><a class="dropdown-item">
								<div class="d-flex">
									<div class="flex-shrink-0 me-3">
										<div class="avatar avatar-online">
											<img
												src="https://storage.googleapis.com/java_bucket_hstest1/${user.memberId}/profile.png"
												alt class=" rounded-circle" />
										</div>
									</div>
									<div class="flex-grow-1">
										<span class="fw-medium d-block">${user.memberName }</span>
									</div>
								</div>
						</a></li>
						<li>
							<div class="dropdown-divider"></div>
						</li>
						<li><a class="dropdown-item"
							href="${pageContext.request.contextPath }/Mypage.do"> <i
								class="ti ti-user-check me-2 ti-sm"></i> <span
								class="align-middle">마이페이지</span>
						</a></li>
						<li>
							<div class="dropdown-divider"></div>
						</li>
						<li>
							<form>
								<a class="dropdown-item" id="logoutBtn" href="javascript::">
									<i class="ti ti-logout me-2 ti-sm"></i> <span
									class="align-middle">로그아웃</span>
								</a>
							</form>
						</li>
					</ul></li>
			</c:if>
            <!-- / Style Switcher-->

          </ul>
          <!-- Toolbar: End -->
        </div>
      </div>
    </nav>
    <!-- Navbar: End -->

    <!-- Sections:Start -->

    <div data-bs-spy="scroll" class="scrollspy-example">
      <!-- Hero: Start -->
      <section id="hero-animation">
        <div id="landingHero" class="section-py landing-hero position-relative">
          <img
            src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/backgrounds/hero-bg.png"
            alt="hero background"
            class="position-absolute top-0 start-50 translate-middle-x object-fit-contain w-100 h-100"
            data-speed="1" />
          <div class="container">
            <div class="hero-text-box text-center">
              <h1 class="text-primary hero-title display-6 fw-bold" style="line-height: 3.5rem;">복잡한 업무 흐름을 단순하게, <br/>효율성은 극대화로</h1>
              <h2 class="hero-sub-title h6 landing mb-4 pb-1">
                복잡한 업무 과정을 단순화하는 능력<br class="d-none d-lg-block" />
                그 결과로 얻어지는 효율성과 생산성의 향상
              </h2>
              <div class="landing-hero-btn d-inline-block position-relative">
              	<c:if test="${not empty user }">
              		<c:if test="${user.memberRole == 'ROLE_USER' }">
              			<c:if test="${not empty group }">
	              			<a href="${pageContext.request.contextPath }/${group.groupId}/memMain.do" class="btn btn-primary btn-lg">서비스 바로가기</a>
              			</c:if>
              			<c:if test="${empty group }">
              				<a href="javascript:">그룹에 초대받은 후 이용</a>
              			</c:if>
              		</c:if>
              		<c:if test="${user.memberRole == 'ROLE_GROUPADMIN' }">
              			<a href="${pageContext.request.contextPath }/group/${group.groupId}" class="btn btn-primary btn-lg">서비스 바로가기</a>
              		</c:if>
              		<c:if test="${user.memberRole == 'ROLE_ADMIN' }">
              			<a href="${pageContext.request.contextPath }/saMain.do" class="btn btn-primary btn-lg">서비스 바로가기</a>
              		</c:if>
              	</c:if>
              	<c:if test="${empty user }">
	                <a href="${pageContext.request.contextPath }/login.do" class="btn btn-primary btn-lg">서비스 바로가기</a>
              	</c:if>
              </div>
            </div>
            <div id="heroDashboardAnimation" class="hero-animation-img">
              <a target="_blank">
                <div id="heroAnimationImg" class="position-relative hero-dashboard-img">
                  <img
                    src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/landing-page/heronexus.png"
                    alt="hero dashboard"
                    class="animation-img"
                    data-app-light-img="front-pages/landing-page/heronexus.png"
                    data-app-dark-img="front-pages/landing-page/heronexus.png"
                    style="height:80%; border-radius:20px; margin-top:60px;" />
                </div>
              </a>
            </div>
          </div>
        </div>
        <div class="landing-hero-blank"></div>
      </section>
      <!-- Hero: End -->

      <!-- Useful features: Start -->
      <section id="landingFeatures" class="section-py landing-features">
        <div class="container">
          <div class="text-center mb-3 pb-1">
            <span class="badge bg-label-primary">서비스 소개</span>
          </div>
          <h3 class="text-center mb-1">
            <span class="position-relative fw-bold z-1"
              >효율적인 비즈니스
              <img
                src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/section-title-icon.png"
                alt="laptop charging"
                class="section-title-img position-absolute object-fit-contain bottom-0 z-n1" />
            </span>
            를 위해 다음과 같은 기능을 제공합니다.
          </h3>
          <p class="text-center mb-3 mb-md-5 pb-3 pt-1">
            넥서스가 제공하는 기능들을 통해 더욱 효율적으로 프로젝트를 관리해보세요.
          </p>
          <div class="features-icon-wrapper row gx-0 gy-4 g-sm-5">
            <div class="col-lg-4 col-sm-6 text-center features-icon-box">
              <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/laptop.png" alt="laptop charging" />
              </div>
              <h5 class="mb-3">무한한 접근성</h5>
              <p class="features-icon-description">
                노트북만 있으면, 언제 어디서든 <br>나의 업무를 확인 및 관리할 수 있습니다. 
              </p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center features-icon-box">
              <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/rocket.png" alt="transition up" />
              </div>
              <h5 class="mb-3">수많은 프로젝트 생성</h5>
              <p class="features-icon-description">
                프로젝트를 생성하고, 사용자를 초대해보세요.<br> 하나의 워크스페이스가 됩니다.
              </p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center features-icon-box">
              <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/paper.png" alt="edit" />
              </div>
              <h5 class="mb-3">30일 체험판 제공</h5>
              <p class="features-icon-description">
                엔터프라이즈 구독 전, <br>30일 무료 체험판을 제공합니다.
              </p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center features-icon-box">
              <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/check.png" alt="3d select solid" />
              </div>
              <h5 class="mb-3">실시간 로그 수집</h5>
              <p class="features-icon-description">
                로그 관리로 프로젝트의 변화를 확인하고, <br>문제에 실시간으로 대응할 수 있습니다. 
              </p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center features-icon-box">
              <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/user.png" alt="lifebelt" />
              </div>
              <h5 class="mb-3">수많은 사용자 초대</h5>
              <p class="features-icon-description">최대 5000명의 사용자 초대가 가능합니다. <br>다양한 전문가와 프로젝트를 수행해보세요.</p>
            </div>
            <div class="col-lg-4 col-sm-6 text-center features-icon-box">
              <div class="text-center mb-3">
                <img src="${pageContext.request.contextPath }/resources/nexus/assets/img/front-pages/icons/keyboard.png" alt="google docs" />
              </div>
              <h5 class="mb-3">보고서 및 통계 데이터 제공</h5>
              <p class="features-icon-description">일일/주간 등 다양한 보고서 소스, 통계 데이터를 제공합니다. 나의 활동 통계를 확인해보세요.</p>
            </div>
          </div>
        </div>
      </section>
      <!-- Useful features: End -->
    </div>

    <!-- / Sections:End -->

    <!-- Footer: Start -->
    <footer class="landing-footer bg-body footer-text">
      <div class="footer-top position-relative overflow-hidden z-1">
        <img
          src="../../assets/img/front-pages/backgrounds/footer-bg-light.png"
          alt="footer bg"
          class="footer-bg banner-bg-img z-n1"
          data-app-light-img="front-pages/backgrounds/footer-bg-light.png"
          data-app-dark-img="front-pages/backgrounds/footer-bg-dark.png" />
        <div class="container">
          <div class="row gx-0 gy-4 g-md-5">
            <div class="col-lg-5">
              <a href="landing-page.html" class="app-brand-link mb-4">
                <span class="app-brand-logo demo">
                  <svg width="32" height="22" viewBox="0 0 32 22" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M0.00172773 0V6.85398C0.00172773 6.85398 -0.133178 9.01207 1.98092 10.8388L13.6912 21.9964L19.7809 21.9181L18.8042 9.88248L16.4951 7.17289L9.23799 0H0.00172773Z"
                      fill="#7367F0" />
                    <path
                      opacity="0.06"
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M7.69824 16.4364L12.5199 3.23696L16.5541 7.25596L7.69824 16.4364Z"
                      fill="#161616" />
                    <path
                      opacity="0.06"
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M8.07751 15.9175L13.9419 4.63989L16.5849 7.28475L8.07751 15.9175Z"
                      fill="#161616" />
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M7.77295 16.3566L23.6563 0H32V6.88383C32 6.88383 31.8262 9.17836 30.6591 10.4057L19.7824 22H13.6938L7.77295 16.3566Z"
                      fill="#7367F0" />
                  </svg>
                </span>
                <span class="app-brand-text demo footer-link fw-bold ms-2 ps-1">Nexus</span>
              </a>
              <p class="footer-text footer-logo-description mb-4 " style="line-height: 1.8rem;">
                Simplify complex work flow and maximize efficiency with Nexus.
              </p>
            </div>
          </div>
        </div>
      </div>
      <div class="footer-bottom py-3">
        <div
          class="container d-flex flex-wrap justify-content-between flex-md-row flex-column text-center text-md-start">
          <div class="mb-2 mb-md-0">
            <span class="footer-text"
              >©
              <script>
                document.write(new Date().getFullYear());
              </script>
            </span>
            <a href="https://pixinvent.com" target="_blank" class="fw-medium text-white footer-link">Pixinvent,</a>
            <span class="footer-text"> Made with ❤️ 2팀</span>
          </div>
        </div>
      </div>
    </footer>
    <!-- Footer: End -->

    <!-- Core JS -->
    <!-- build:js assets/vendor/js/core.js -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/popper/popper.js"></script>
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/node-waves/node-waves.js"></script>

    <!-- endbuild -->

    <!-- Vendors JS -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/nouislider/nouislider.js"></script>
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/swiper/swiper.js"></script>

    <!-- Main JS -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/js/front-main.js"></script>

    <!-- Page JS -->
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/js/front-page-landing.js"></script>
    <script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/jquery/jquery.js"></script>
	<script>
		let contextPath = $('#contextPath').val();
		function logout() {
			$.ajax({
		        url: contextPath + '/logout.do',
		        type: 'POST',
		        success: function(response) {
		            console.log('Logout successful');
		            // 로그아웃 후 처리 (예: 로그인 페이지로 리다이렉트)
		            window.location.href = contextPath + '/login.do';
		        },
		        error: function(xhr, status, error) {
		            console.error('Logout error:', error);
		        }
		    });
		}
	
		$('#logoutBtn').on('click', (e) => {
			e.preventDefault();
			logout();
		})
	</script>
  </body>
</html>
