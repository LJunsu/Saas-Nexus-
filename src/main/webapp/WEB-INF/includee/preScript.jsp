<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- Favicon -->
<link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath }/resources/nexus/assets/img/favicon/favicon.ico" />

<!-- Fonts -->
<link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css" />
<link rel="preconnect" href="https://fonts.googleapis.com" />
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
<link
  href="https://fonts.googleapis.com/css2?family=Public+Sans:ital,wght@0,300;0,400;0,500;0,600;0,700;1,300;1,400;1,500;1,600;1,700&ampdisplay=swap"
  rel="stylesheet" />

<!-- Icons -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/fonts/fontawesome.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/fonts/tabler-icons.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/fonts/flag-icons.css" />


<!-- <!-- 이준수 -->
<!-- <link rel="stylesheet" href="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.css"/> -->
<!-- <script src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script> -->

<!-- <!-- simple-datatables CSS -->
<!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css"> -->
<!-- <!-- simple-datatables JS -->
<!-- <script src="https://cdn.jsdelivr.net/npm/simple-datatables@latest" defer></script> -->
<!-- <!-- 이준수 끝 -->

<!-- JSScript -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/simple-datatables@latest/dist/style.css">
<!-- /JSScript -->

<!-- SweetAlert2 CSS -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.min.css">

<!-- Core CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/core.css" class="template-customizer-core-css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/theme-default.css" class="template-customizer-theme-css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/css/demo.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/dy-core.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/ds-core.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/hs-core.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/js-core.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/sd-core.css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/rtl/sj-core.css"/>

<!-- Vendors CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/node-waves/node-waves.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/perfect-scrollbar/perfect-scrollbar.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/typeahead-js/typeahead.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-bs5/datatables.bootstrap5.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-responsive-bs5/responsive.bootstrap5.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-buttons-bs5/buttons.bootstrap5.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/fullcalendar/fullcalendar.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/flatpickr/flatpickr.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/select2/select2.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/quill/editor.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/@form-validation/form-validation.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/bs-stepper/bs-stepper.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-bs5/datatables.bootstrap5.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-responsive-bs5/responsive.bootstrap5.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-buttons-bs5/buttons.bootstrap5.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/bs-stepper/bs-stepper.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/quill/katex.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/quill/editor.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/apex-charts/apex-charts.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/jkanban/jkanban.css" />

<!-- Page CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/app-email.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/app-chat.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/app-calendar.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/page-auth.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/page-profile.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/dropzone/dropzone.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/app-logistics-dashboard.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/nexus/assets/vendor/css/pages/app-kanban.css" />


<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/jquery/jquery.js"></script>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/@form-validation/bootstrap5.js"></script>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/datatables-bs5/datatables-bootstrap5.js"></script>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/bootstrap-maxlength/bootstrap-maxlength.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/app/utils/fetchUtils.js"></script>

<!-- Helpers -->
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/helpers.js"></script>
<!--! Template customizer & Theme config files MUST be included after core stylesheets and helpers.js in the <head> section -->
<!--? Template customizer: To hide customizer set displayCustomizer value false in config.js.  -->
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/js/template-customizer.js"></script>
<!--? Config:  Mandatory theme config file contain global vars & default theme options, Set your preferred theme option in this file.  -->
<script src="${pageContext.request.contextPath }/resources/nexus/assets/js/config.js"></script>
<!-- 실시간 알람 sockjs -->
<script src="${pageContext.request.contextPath }/resources/js/sockjs.min.js"></script>
<!-- 실시간 알람 js -->
<script src="${pageContext.request.contextPath }/resources/js/app/alarm.js"></script>
<!-- SweetAlert2 JS -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="${pageContext.request.contextPath }/resources/js/axios.min.js"></script>

