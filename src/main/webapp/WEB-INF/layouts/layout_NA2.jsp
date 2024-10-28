<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="cPath" value="${pageContext.request.contextPath}" scope="application" />
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
</head>

<body data-context-path="${cPath }">
    <!-- Layout wrapper -->
    <div class="layout-wrapper layout-navbar-full layout-horizontal layout-without-menu">
      <div class="layout-container">

        <tiles:insertAttribute name="header" />

        <!-- Layout container -->
         <div class="layout-page">
           <!-- Content wrapper -->
             <div class="content-wrapper">

              <tiles:insertAttribute name="sidebar" />

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

<div id="customAlarm" style="display: none; position: fixed; bottom: 20px; right: 20px; width: 350px; padding: 10px; background-color: #f5f5f5; border: 1px solid #ddd; box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.2); z-index: 1000; border-radius: 5px; font-family: Arial, sans-serif; color: #333; align-items: center;">
    <div style="background-color: #00c73c; width: 40px; height: 40px; border-radius: 5px; display: flex; align-items: center; justify-content: center; margin-right: 10px;">
        <img src="${cPath}/resources/images/화면 캡처 2024-08-22 152751.png" alt="Chat Icon" style="width: 24px; height: 24px;">
    </div>
    <div style="flex-grow: 1;">
        <div style="font-size: 12px; color: #888;" id="ccc"></div>
        <div style="font-size: 14px; font-weight: bold;" id="bbb"></div>
    </div>
    <div style="cursor: pointer; margin-left: 10px;" onclick="document.getElementById('customAlarm').style.display='none';">✕</div>
</div>


  <tiles:insertAttribute name="postScript" />
</body>
</html>