<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cPath" value="${pageContext.request.contextPath }" scope="application"/>
<!doctype html>
<html
  lang="en"
  class="light-style layout-menu-fixed layout-compact"
  dir="ltr"
  data-theme="theme-default"
  data-assets-path="${pageContext.request.contextPath }/resources/nexus/assets/"
  data-template="horizontal-menu-template">
  <head>

    <meta charset="utf-8" />
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

    <title>Fullcalendar - Apps | Vuexy - Bootstrap Admin Template</title>

    <meta name="description" content="" />

  <tiles:insertAttribute name="preScript" />

  <style>
#scrollingTitle {
  position: fixed;
  bottom: 20px;
  width: 100%;
  text-align: center;
  padding: 10px 0;
  background: rgba(0, 0, 0, 0.7); /* 반투명 검은색 배경 */
  border-radius: 5px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.3); /* 부드러운 그림자 효과 */
  display: none; /* 기본적으로는 숨김 처리 */
  z-index: 1000; /* 페이지의 다른 요소보다 위에 위치 */
}

#scrollingTitle span {
  font-size: 18px;
  color: #fff;
  animation: subtleSparkle 2s ease-in-out infinite;
}

/* 반짝임 애니메이션 */
@keyframes subtleSparkle {
  0%, 100% {
    text-shadow: 0 0 3px #aaa, 0 0 6px #aaa;
    color: #fff;
  }
  50% {
    text-shadow: none;
    color: #ccc;
  }
}

  </style>
</head>

<body data-context-path="${cPath }" class="gm-main">
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-navbar-full layout-horizontal layout-without-menu">
      <div class="layout-container">

        <tiles:insertAttribute name="header" />

        <!-- Layout container -->
         <div class="layout-page">
           <!-- Content wrapper -->
             <div class="content-wrapper">

<%--               <tiles:insertAttribute name="sidebar" /> --%>

	          <tiles:insertAttribute name="contentPage" />

	          <tiles:insertAttribute name="footer" />

            <div class="content-backdrop fade"></div>
          </div>
          <!--/ Content wrapper -->
        </div>
          <!--/ Layout container -->
      </div>
    </div>

    <!-- Overlay -->
    <div class="layout-overlay layout-menu-toggle"></div>

    <!-- Drag Target Area To SlideIn Menu On Small Screens -->
    <div class="drag-target"></div>

  <tiles:insertAttribute name="postScript" />

<div id="scrollingTitle" style="display: none; position: fixed; top: 90%; width: 100%; overflow: hidden;">
  <span id="titleText" style="position: relative; white-space: nowrap;"></span>
</div>


</body>


</html>