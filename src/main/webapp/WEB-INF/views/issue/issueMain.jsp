<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser}" var="user" />
<c:set value="${authentication.principal.realUser.memberName}" var="userName" /> <!--  MEMBER이름 -->
<c:set value="${authentication.principal.realUser.memberId}" var="userId" /> <!--  MEMBER이름 -->


<!-- Content -->
<div class="container-xxl flex-grow-1 container-p-y" style="
    margin-bottom: -3px;">
<div id="userId" data-user-id="${userId}"></div>
<div id="userName" data-user-name="${userName}"></div>
<div id="alarmChk" data-alarm-chk="${last}"></div>
<div id="loading" style="display: none; position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); z-index: 100;">
    <img src="${cPath}/resources/nexus/assets/img/icons/brands/Spinner.gif" alt="Loading..." />
  </div>
	<div class="app-chat card overflow-hidden">
		<div class="row g-0">
			<!-- Issue List -->
			<div
				class="col app-chat-contacts app-sidebar flex-grow-0 overflow-hidden border-end"
				id="app-chat-contacts">
				<div class="sidebar-header">
					<div class="d-flex align-items-center me-3 me-lg-0">
						<h5 class="mb-0 pt-2 pb-2 issue-lists " style="font-weight: 600;">이슈
							목록</h5>
						<div
							class="flex-grow-1 input-group input-group-merge rounded-pill"
							style="width: 40%;">
							<span class="input-group-text" id="basic-addon-search31"><i
								class="ti ti-search"></i></span> <input type="text"
								class="form-control chat-search-input" placeholder="검색..."
								aria-label="검색..." aria-describedby="basic-addon-search31" />
						</div>
					</div>
					<div id="progress-color">
					<span class="badge bg-success bg-glow"id="progress-status-lime">완료됨</span>
					<span class="badge bg-info bg-glow"id="progress-status-blue">진행중</span>
					<span class="badge bg-warning bg-glow"id="progress-status-red">준비중</span>
					<span class="badge bg-secondary bg-glow"id="progress-status-default">전체보기</span>
					</div>
					
					<i
						class="ti ti-x cursor-pointer d-lg-none d-block position-absolute mt-2 me-1 top-0 end-0"
						data-overlay data-bs-toggle="sidebar"
						data-target="#app-chat-contacts"></i>
				</div>
				<div class="sidebar-body pt-1" style="overflow: scroll; padding-bottom: 27%;">
					<ul class="list-unstyled chat-contact-list" id="issue-list">
						<li class="chat-contact-list-item chat-list-item-0 d-none">
							<h6 class="text-muted mb-0">이슈가 없습니다.</h6>
						</li>

					</ul>
				</div>
			</div>
			<!-- /Issue List -->

			<!-- Chat History -->
			<div class="col app-chat-history" style="overflow-y: auto;">
				<div class="chat-history-wrapper">
					<div class="chat-history-body "
						style="height: 95%; ">

							<div id="creatIssue-area">
								<button type="button" id="createIssueBtn"  class="btn btn-primary waves-effect waves-light" data-bs-toggle="modal" data-bs-target="#largeModal" >
					              <span></span>+이슈 생성하기
					            </button>
							</div>
							<div>
								<small class="text-muted mb-auto" id="issueNum"></small>


							</div>
							<div>
								<input type="text" name="issueTitle" class="form-control"
									 style="border: none; font-size:1.7375rem; font-family:bold;"
									maxlength='25' readonly/>
							</div>
							<div>

								<label for="fileName" id="fileNameLabel">파일첨부</label>
								<input class="fileAddButton" id="fileName" type="file" multiple="multiple">
								<button id="downIssue">상위 이슈 추가</button>
								
							</div>
							<!-- 컨텐츠를 처리하는 영역 -->
							<div id="content-area">
								<textarea id="content-line" name="content-line"></textarea>

							</div>
							<div id="content-form" style="display:none;">
							<textarea name="issueContent" id="issue-content"
									style="width: 100%; height: 150px; resize:none;"></textarea>
							<button id="content-cancle" style="float: right;">취소</button>
							<button id="content-button" style="float: right;">저장</button>
							</div>
							<!-- 첨부파일 버튼 생성시 보여주는 영역 -->

							<div id="file-area" style="margin-top: 5%;">
								첨부파일
								<br>
								<div id="preview-area"></div>
							</div>

							<!-- 하위 이슈를 생성하는 영역! -->
							<div id="downIssue-area" style="margin-top: 3%; ">
								<select id="upIssue-select">
								

								</select>
							</div>

							<div id="downIssue-view" style="margin-top: 3%; ">

							</div>
							<h4 id="CmtH4">댓글</h4>
							<div id="cmtCreate-area">
								<img src="https://storage.googleapis.com/java_bucket_hstest1/${userId}/profile.png" alt="" id="cmt-img"class="h-auto rounded-circle" style="width: 7%;">
								<input type="text" value="댓글 작성하기" id="showEditor" />
							</div>
							<div id="editor-area" style="display: block;width: 75%;">
							<div id="editor">
							  <p>내용을 입력하세요.</p>

							</div>
							<div id="editorLength" style="display:none;"></div>
							<input type="button" id="CmtSavebtn" value="저장" class="btn btn-primary waves-effect waves-light">
							<input type="button" id="CmtCanclebtn" value="취소" class="btn btn-danger waves-effect waves-light">
							</div>

							<div style="margin-top: 3%" id="CmtHtml-area">
						
							</div>
							</div>
				</div>
			</div>
			<!-- Right Detail Area -->
			<div
				class="col app-chat-contacts app-sidebar flex-grow-0 overflow-hidden border-end">

				<div class="card h-100">
				 <div class="card mb-4">
                    <div class="card-body">
                      	<div id="issueDelArea">
                      <p class="card-text text-uppercase" style="font-weight: 500;">이슈 상세설정</p>
                      <div id="issueDelBtnArea"></div>
                      </div>
						<div id="smallModal" style="display:none; position: absolute;"></div>
                      <ul class="list-unstyled mb-4 mt-3">
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-user text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">생성자:</span>
                          <input type="text" name="projectMemIdx" class="form-control"
							required="required" value="" data-issue-creator="${memberId}" readonly style="border:0; width:50%;"/>
						<span>
						</span>
                        </li>
                        <li class="d-flex align-items-center mb-3">
                        <!-- 담당자 form -->
                          <i class="ti ti-flag text-heading"></i
                          ><span class="fw-medium mx-2 text-heading" style="width:37%">담당자:</span>
                          <select  name="issueOfficerIdx" class="form-control"
							 required="required" style=" width: 31%;margin-left: -18%;">

							</select>
                        </li>
                       <!-- 이슈 진행상태 -->
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-check text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">진행상태:</span>
                          <span>
                          <select name="issueProgressStatus"
							class="form-control" >
								<option value="Completed" label="완료됨"></option>
								<option value="In Progress" label="진행중"></option>
								<option value="Not Started" label="준비중" selected></option>
							</select>
					</span>
                        </li>
                        <!-- 이슈 우선순위 -->
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-crown text-heading"></i
                          ><span class="fw-medium mx-2 text-heading" >우선순위:</span>

                          <select name="issuePriorityName"
						class="form-control" required="required" style="width: 35%;">
							<option value="Highest" label="매우높음"/>
							<option value="High" label="높음"/>
							<option value="Medium" label="중간" selected/>
							<option value="Low" label="낮음"/>
							<option value="Lowest" label="매우낮음"/>
						</select>
                        </li>
                        <!-- 이슈 시작기한 -->
                        <li class="d-flex align-items-center mb-3">
                          <i class="ti ti-file-description text-heading"></i
                          ><span class="fw-medium mx-2 text-heading" >시작:</span>
                          <span><input type="date" name="issueCreation" class="form-control" /></span>
						 </li>
						<!-- 이슈 마감기한 -->
						<li class="d-flex align-items-center mb-3">
                          <i class="ti ti-file-description text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">마감:</span>
                          <span>
                          <input type="date" name="issueDead" class="form-control" />
                          </span>
						</li>
						<!-- 이슈 태그 -->
						<li class="d-flex align-items-center mb-3">
						<i class="ti ti-file-description text-heading"></i
                          ><span class="fw-medium mx-2 text-heading">태그:</span>
						<span> <select name="issueTagName" class="form-control select2"
										style="width: 50%">
											<option value="bug">버그</option>
											<option value="feature">기능 추가</option>
											<option value="enhancement">기능 개선</option>
											<option value="performance">성능</option>
											<option value="security">보안</option>
											<option value="ui/ux">UI/UX</option>
											<option value="documentation">문서화</option>
											<option value="testing">테스트</option>
											<option value="refactoring">리팩토링</option>
											<option value="maintenance">유지보수</option>
											<option value="technical-debt">기술 부채</option>
											<option value="support">지원</option>
											<option value="training">교육</option>
											<option value="deployment">배포</option>
											<option value="integration">통합</option>
											<option value="bugfix">버그 수정</option>
											<option value="client-request">클라이언트 요청</option>
											<option value="urgent">긴급</option>
									</select>
								</span>
						</li>

                      </ul>

                    </div>
                  </div>

				<input type="hidden" value="${projectId}" name="projectId" class="form-control" />
				</div>
			</div>
			<!-- /Chat History -->
			<div class="app-overlay"></div>
		</div>
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by Google Cloud Storage API</div>
</div>
	<!-- isuue 생성 모달 -->
		<div class="modal fade" id="largeModal" tabindex="-1" style="display: none;" aria-hidden="true" data-backdrop='static' data-keyboard='false'>
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel3">이슈 생성하기</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal"  aria-label="Close"></button>
      </div>
      <div class="modal-body" >
      <!-- 프로젝트 회원 IDX -->
        <div class="mb-6">
		  <label class="form-label" for="issueCreator">생성자</label>
		  <input type="text" id="issueCreatorDisplay" class="form-control" readonly />
		  <!-- 실제 전송되는 값 -->
		  <input type="hidden" id="issueCreator" name="projectMemIdx" class="form-control" required="required" />
		</div>


        <!-- 이슈 제목 -->
        <div class="mb-6">
          <label class="form-label" for="issueTitleModal">이슈 제목</label>
          <input type="text" name="issueTitleModal" class="form-control" maxlength='25'/>
        </div>

         <!-- 이슈 내용 -->
        <div class="mb-6">
          <label class="form-label" for="issueContent">이슈 내용</label>
          <textArea name="issueContentModal" style="height: 123px;" class="form-control" ></textArea>
        </div>

         <!-- 이슈 담당자 -->
        <div class="mb-6">
          <label class="form-label" for="issueOfficer">이슈 담당자</label>
          <select name="issueOfficer"   class="form-control"></select>
        </div>

       <!-- 이슈 생성 날짜 -->
        <div class="mb-6">
          <label class="form-label" for="issueDate">이슈 시작 날짜</label>
          <input type="date" name="issueDate" class="form-control" />
        </div>

        <!-- 이슈 종료 날짜 -->
        <div class="mb-6">
          <label class="form-label" for="issueDeadline">이슈 종료 날짜</label>
          <input type="date" name="issueDeadline" class="form-control" />
        </div>

        <!-- 상위 이슈 번호 -->
        <div class="mb-6">
          <label class="form-label" for="subIssueIdx">상위 이슈</label>
          <select name="subIssueIdx" class="form-control" ></select>

        </div>

        <!-- 이슈 진행 상태 -->
        <div class="mb-6">
          <input type="hidden" value="Not Started" name="issueProgressStatus" class="form-control" />
        </div>

        <!-- 이슈 우선 순위 -->
        <div class="mb-6">
          <label class="form-label" for="issuePriorityName">이슈 우선 순위</label>
          <select  name="issuePriorityNameModal" class="form-control" required="required">
          <option value="Highest">매우높음</option>
          <option value="High">높음</option>
          <option value="Medium" selected="selected">중간</option>
          <option value="Low">낮음</option>
          <option value="Lowest">매우낮음</option>
          </select>
        </div>
         <div class="mb-6">
        <label class="form-label" for="issueTagModal">태그</label>
						 <select name="issueTagModal" id="issueTagModal"class="form-control"
										style="width: 100%">
											<option value="" selected>선택</option>
											<option value="bug">버그</option>
											<option value="feature">기능 추가</option>
											<option value="enhancement">기능 개선</option>
											<option value="performance">성능</option>
											<option value="security">보안</option>
											<option value="ui/ux">UI/UX</option>
											<option value="documentation">문서화</option>
											<option value="testing">테스트</option>
											<option value="refactoring">리팩토링</option>
											<option value="maintenance">유지보수</option>
											<option value="technical-debt">기술 부채</option>
											<option value="support">지원</option>
											<option value="training">교육</option>
											<option value="deployment">배포</option>
											<option value="integration">통합</option>
											<option value="bugfix">버그 수정</option>
											<option value="client-request">클라이언트 요청</option>
											<option value="urgent">긴급</option>
									</select>
								</div>

         <!-- 프로젝트 ID -->
        <div class="mb-6">
          <input type="hidden" value="${projectId}" name="projectId" class="form-control" />
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary waves-effect waves-light" id="insertAutoModalBtn">자동완성</button>
        <button type="submit" class="btn btn-primary waves-effect waves-light" id="insertIssueModalBtn">저장</button>
        <button type="button" class="btn btn-label-secondary waves-effect" data-bs-dismiss="modal">닫기</button>
      </div>
		 <input type="hidden" value="${userId}" name="senderId" class="form-control" />
		 <input type="hidden" value="${userName}" name="senderName" class="form-control" />
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://code.jquery.com/ui/1.14.0/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.14.0/themes/base/jquery-ui.css">
<script src="${cPath }/resources/js/app/issue/issueMain.js"></script>
<link href="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.snow.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/quill@2.0.2/dist/quill.js"></script>
