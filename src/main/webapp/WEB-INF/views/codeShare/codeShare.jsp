<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.20.0/min/vs/editor/editor.main.min.css">
<style>
	#topContainer {
		position: relative;
		margin: 80px auto 0;
	}

	#midContainer {
		position: relative;
		width: 90%;
		height: 90px;
		margin: 0 auto;
		background: #1e1e1e;
	}

	#botContainer {
		width: 20%;
		height: 400px;
		float: right;
		background: #1e1e1e;
	}

	#resultContainer {
		position: relative;
		float: left;
		width: 100%;
		background: #1e1e1e;
		border-top: 1px solide #efefef;
	}

/* 	#container { */
	#editor {
		position: relative;
		z-index: 100px;
		float: left;
		width: 80%;
		height: 400px;
		margin: 0 auto;
		border: 1px solid black;
		font-size: 20px;
	}

	table {
		width: 100%;
		border: 1px solid black;
		color: #ffffff;
	}

	th, td {
		text-align: center;
		border: 1px solid black;
	}

	#editorSubmit {
		position : absolute;
		bottom : 0;
		width: 20%;
		height: 30px;
		float: right;
		background: rgb(115, 103, 240);
		color: #ffffff;
		border: none;
	}

	#compilingBtn {
		position : absolute;
		top: 30px;
		right : 0;
		width: 20%;
		height: 30px;
		float: right;
		background: rgb(79 207 79);
		color: #ffffff;
		border: none;
	}

	#titleInput {
		width: 50%;
		height: 80px;
		font-size: 22px;
		background: #1e1e1e;
		color: #ffffff;
		border: none;
		padding: 5px 20px;
	}

	#titleInput:focus {
		outline:none;
	}

	#resultSpan {
		display: block;
		color: #ffffff;
		width: 80%;
		height: 160px;
		overflow-y: auto;
		padding-left: 15px;
	}

	#codeShareDelete {
	    position: absolute;
	    top: 0;
	    bottom: 0;
		width: 20%;
		height: 30px;
		float: right;
		background: #ff3d3e;
		color: #ffffff;
		border: none;
	}

	#codeShareTitle {
		height: 90px;
	}

	#backBtn {
		position: relative;
		top: 0;
		right: 0;
		color: #ffffff;
	}
</style>



<div
	class="col-xl-12 col-md-6 mb-4"
	id="topContainer"
	data-group-id="${groupId}"
	data-project-id="${projectId}"
	data-real-mem-id="${princiapl.realUser.memberId}"
	data-code-share-id="${codeShareId}"
>
	<textarea id="codeShareContent" style="display:none;">${codeShareDetail.codeShareContent}</textarea>

	<div id="midContainer">
		<div id="codeShareTitle">
			<input id="titleInput" type="text" value="${codeShareDetail.codeShareTitle}">
<!-- 			<a href="javascript:history.back();" class="btn" style="float: right; margin: 23px 335px 0 0; color: #ffffff;">뒤로가기</a> -->

			<c:set var="realName" value="${princiapl.realUser.memberName}" />
			<c:set var="codeShareName" value="${codeShareDetail.memberId}" />
		</div>

		<div id="editor">${codeShareDetail.codeShareContent}</div>

		<div id="botContainer">
			<table>
				<tr>
					<th colspan="2">참여 인원</th>
				</tr>

				<tr>
					<th style="width: 30%">아이디</th>
					<th>이름</th>
				</tr>

					<tr>
						<td><td>
						<td></td>
					</tr>
			</table>

			<c:if test="${realName eq codeShareName}">
				<button id="codeShareDelete">삭제</button>
			</c:if>
			<button id="compilingBtn">컴파일</button>
			<button id="editorSubmit">코드 저장</button>
		</div>

		<div id="resultContainer">
			<span id="resultSpan">콘솔</span>
			<a href="javascript:history.back();" class="btn" id="backBtn">뒤로가기</a>
			<a class="btn" id="insertAuto" style="color: #ffffff;">자동완성</a>
		</div>
	</div>
</div>
<div style="width: 90%; margin: 0 auto;">
	<div class="pt-2 justify-content-end" style="float: right;">Powered by ACE editor, Java Compiler API</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ace.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ext-beautify.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/mode-java.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ext-language_tools.js" type="text/javascript"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/ace/1.2.6/ext-split.js" type="text/javascript"></script>

<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/monaco-editor/0.26.1/min/vs/loader.min.js"></script> -->
<script src="${pageContext.request.contextPath}/resources/js/app/codeShare.js"></script>