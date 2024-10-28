
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<style>
	option[disabled] {
		background: rgba(75, 70, 92, 0.08);
	}
</style>

<div class="container-xxl flex-grow-1 container-p-y" style="margin-top:55px;">
  <div class="card app-calendar-wrapper">
    <div class="row g-0">
      <!-- Calendar Sidebar -->
      <div class="col app-calendar-sidebar" id="app-calendar-sidebar">
        <div class="border-bottom p-4 my-sm-0 mb-3">
          <div class="d-grid">
            <button
              class="btn btn-primary btn-toggle-sidebar"
              data-bs-toggle="offcanvas"
              data-bs-target="#addEventSidebar"
              aria-controls="addEventSidebar"
              id="btnIssueCreate">
              <i class="ti ti-plus me-1"></i>
              <span class="align-middle">이슈 생성하기</span>
            </button>
          </div>
        </div>
        <div class="p-3">
          <!-- 검색 및 필터 -->
          <div class="mb-2 d-flex align-items-center justify-content-between p-2">
            <h5 style="font-weight:700; margin: 0;" class="mt-1">검색 및 필터</h5>

            <!-- GIT 연결 스위치 -->
            <label class="switch" style="padding-right:22px;">
              <span class="switch-label" style="font-weight:700; color:#685dd8">GIT 연결</span>
              <input type="checkbox" class="switch-input" id="gitSwitch" />
              <span class="switch-toggle-slider"></span>
            </label>
          </div>

          <!-- Search -->
          <div class="mb-1 ms-2">
            <small class="text-small text-muted text-uppercase align-middle">검색</small>
          </div>
          <form class="d-flex" style="margin-right: 5px; margin-left:5px;">
            <div class="input-group" style="flex-grow: 1;">
              <input type="text" class="form-control" placeholder="검색어를 입력해주세요(제목)" style="flex-grow: 1;" id="searchInputForm"/>
            </div>
          </form>
          <!-- /Search -->

          <!-- Filter -->
          <div class="mb-1 ms-2 mt-3">
            <small class="text-small text-muted text-uppercase align-middle">태그</small>
          </div>
          <form class="d-flex" style="margin-right: 5px; margin-left:5px; padding-bottom:65px;">
            <div class="input-group" style="flex-grow: 1;">
              <select name="issueTags1[0].issueTagName" class="form-control" id="searchIssueTagName">
        	     <option value="">전체보기</option>
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
          </form>
          <!-- /Filter -->
          <hr class="container-m-nx mb-3 mt-3" />
          <!-- inline calendar (flatpicker) -->
          <div class="inline-calendar" id="inline-calendar"></div>
        </div>
      </div>
      <!-- /Calendar Sidebar -->

      <!-- Calendar & Modal -->
      <div class="col app-calendar-content">
        <div class="card shadow-none border-0">
          <div class="card-body pb-0">
            <!-- FullCalendar -->
            <div id="calendar"></div>
          </div>
        </div>
        <div class="app-overlay"></div>


        <!-- addEventSidebar 모달 -->
        <div class="offcanvas offcanvas-end event-sidebar" tabindex="-1" id="addEventSidebar" aria-labelledby="offcanvasEcommerceCustomerAddLabel">
        	<div class="offcanvas-header" style="padding-bottom:10px;">
        		<h5 id="offcanvasEcommerceCustomerAddLabel" class="offcanvas-title" style="font-weight:600;">이슈 생성하기</h5>
        		<button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close" onclick="formReset();"></button>
        	</div>

        	<div class="offcanvas-body mx-0 flex-grow-0">
        		<form class="ecommerce-customer-add pt-0 fv-plugins-bootstrap5 fv-plugins-framework" id="timeLineInsert" action="" method="post">
        			<div class="ecommerce-customer-add-basic mb-3">

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 제목</label>
        					<input type="text" class="form-control" placeholder="이슈 제목" name="issueTitle" id="issueTitle">
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 내용</label>
        					<input type="text" class="form-control" placeholder="이슈 내용" name="issueContent" id="issueContent">
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 담당자</label>
        					<select name="issueOfficer" class="form-control" name="issueOfficer" id="issueOfficer">
        						<option>선택</option>
        						<c:forEach var="projectMem" items="${projectMemberList}">
        							<option value="${projectMem.projectMemIdx}">${projectMem.projectMemIdx} [${projectMem.projectRole[0].projectRoleName}] ${projectMem.member.memberName}</option>
        						</c:forEach>
        					</select>
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 진행 상태</label>
        					<select name="issueProgressStatus" class="form-control" id="issueProgressStatus">
        						<option>선택</option>
        						<option>Not Started</option>
        						<option>In Progress</option>
        						<option>Completed</option>
        					</select>
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 우선 순위</label>
        					<select name="issuePriorityName" class="form-control" id="issuePriorityName">
        						<option>선택</option>
							    <option value="Highest">Highest</option>
							    <option value="High">High</option>
							    <option value="Medium">Medium</option>
							    <option value="Low">Low</option>
							    <option value="Lowest">Lowest</option>
        					</select>
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 태그</label>
        					<select name="issueTags[0].issueTagName" class="form-control" id="issueTagName">
        					  <option value="">선택하세요</option>
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
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 시작일</label>
        					<input type="date" class="form-control" placeholder="이슈 시작일" name="issueDate" id="issueDate">
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">이슈 마감일</label>
        					<input type="date" class="form-control" placeholder="이슈 마감일" name="issueDeadline" id="issueDeadline">
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>

        				<div class="mb-3 fv-plugins-icon-container">
        					<label class="form-label" for="ecommerce-customer-add-name">상위 이슈</label>
        					<select name="subIssueIdx" class="form-control" id="subIssueIdx">
        						<option>선택</option>
        						<c:forEach var="issues" items="${issueList}">
        							<option value="${issues.issueIdx}">[${issues.issueIdx}] ${issues.issueTitle}</option>
        						</c:forEach>
        					</select>
        					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback"></div>
        				</div>
        			</div>

        			<div class="pt-3" id="timeLineInsertModalBtn" data-project-mem-idx="${projectMemIdx}">
        				<button type="submit" class="btn btn-primary me-1 data-submit waves-effect waves-light" id="modalCreateBtn">생성하기</button>
        				<button type="reset" class="btn btn-label-danger waves-effect" data-bs-dismiss="offcanvas" id="modalResetBtn">취소</button>
        			</div>

        			<!-- 이후 value에 값을 넣어줘야 함 -->
        			<input type="hidden" name="projectId" value="${projectId}">
        			<input type="hidden" name="projectMemIdx" value="PM001">
        			<input type="hidden" name="issueIdx" value="${issues.issueIdx}" id="issueIdxInp">
        		</form>
        	</div>
        </div>
        <!-- addEventSidebar 모달 끝 -->
        <!-- Git modal -->
        <div class="modal fade" id="gitConnect" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog modal-lg modal-simple modal-edit-user">
            <div class="modal-content p-3 p-md-5">
              <div class="modal-body">
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                <div class="text-center mb-4">
                  <h3 class="mb-2" style="font-weight:700;">깃허브 레포지토리 연결</h3>
                  <p class="text-muted" style="font-weight:500;">연결하고자 하는 레포지토리 주소와 접근 토큰을 입력해주세요.</p>
                </div>
                <form id="editUserForm" class="row g-3" onsubmit="return false">
                  <div class="col-12">
                    <label class="form-label" for="gitConnectURL">Repository 주소</label>
                    <input
                      type="text"
                      id="gitConnectURL"
                      name="gitConnectURL"
                      class="form-control"
                      placeholder="레포지토리 주소를 입력해주세요" />
                  </div>
                  <div class="col-12">
                    <label class="form-label" for="gitConnectToken">접근 토큰</label>
                    <input
                      type="text"
                      id="gitConnectToken"
                      name="gitConnectToken"
                      class="form-control"
                      placeholder="해당 레포지토리 접근 토큰을 입력해주세요"/>
                  </div>
                  <div class="col-12 text-center mt-4">
                    <button type="submit" class="btn btn-primary me-1" onclick="connectBtnGit();">연결하기</button>
                    <button
                      type="reset"
                      class="btn btn-label-secondary"
                      data-bs-dismiss="modal"
                      aria-label="Close">
                      취소
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <!-- /Git modal -->
      </div>
      <!-- /Calendar & Modal -->
    </div>
  </div>
</div>
<script src="${pageContext.request.contextPath }/resources/nexus/assets/vendor/libs/fullcalendar/fullcalendar.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar/main.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar/locales/ko.js"></script>
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<script src="https://npmcdn.com/flatpickr/dist/l10n/ko.js"></script>
<script>
const contextPath = document.body.dataset.contextPath;
const groupId = "${groupId}";
const projectId = "${projectId}";
const gitDetail = "${gitDetail}";
console.log("gitDetail:",gitDetail);
const ownerMatch = gitDetail.match(/owner=([^,]+)/);
const repoMatch = gitDetail.match(/repo=([^,]+)/);
const gitTokenMatch = gitDetail.match(/gitToken=([^,]+)/);

let gitOwner2 = ownerMatch ? ownerMatch[1].trim() : null;
let gitRepo2 = repoMatch ? repoMatch[1].trim() : null;
let gitToken2 = gitTokenMatch ? gitTokenMatch[1].trim() : null;

console.log("gitOwner:", gitOwner2);
console.log("gitRepo:", gitRepo2);
console.log("gitToken:", gitToken2);
const gitSwitch = document.getElementById('gitSwitch');
const gitHref = `\${contextPath}/\${groupId}/\${projectId}/calendar/git`;
let gitOwner = localStorage.getItem('gitOwner') || null;
let gitRepo = localStorage.getItem('gitRepo') || null;
let gitToken = localStorage.getItem('gitToken') || null;
let isGitConnected = false;

//JSP에 담긴 gitDetail이 있으면 그것을 사용, 없으면 그대로 로컬 스토리지 값을 사용
var gitDetail2 = "${gitDetail != null ? 'true' : 'false'}";
// GIT 연결 상태 설정
if (gitDetail2 === 'true') {
    console.log("gitDetail:",gitDetail);
    gitOwner = gitOwner2;
    gitRepo = gitRepo2;
    gitToken = gitToken2;

    localStorage.setItem('gitOwner', gitOwner);
    localStorage.setItem('gitRepo', gitRepo);
    localStorage.setItem('gitToken', gitToken);

    setSwitchState(true);
    isGitConnected = true;
    removeSwitchClickEvent(); // GIT 연결 시 클릭 이벤트 제거
} else {
    // JSP에서 gitDetail이 존재하지 않을 때, false 처리
    gitOwner = null;
    gitRepo = null;
    gitToken = null;

    // 로컬 스토리지에서 값 제거
    localStorage.removeItem('gitOwner');
    localStorage.removeItem('gitRepo');
    localStorage.removeItem('gitToken');

    // 스위치 상태를 꺼짐으로 설정
    setSwitchState(false);
    isGitConnected = false;
    addSwitchClickEvent(); // GIT 연결 안 됐을 때만 클릭 이벤트 추가
}

// //Debounce 함수
function debounce(func, wait) {
    let timeout;
    return function (...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}



//스위치 초기 상태 설정 함수
function setSwitchState(isChecked) {
 if (isChecked) {
     gitSwitch.checked = true;
     console.log("스위치가 켜졌습니다.");
 } else {
     gitSwitch.checked = false;
     console.log("스위치가 꺼졌습니다.");
 }
}

// 스위치 클릭 이벤트 제거
function removeSwitchClickEvent() {
 gitSwitch.onclick = null; // 클릭 이벤트 제거
 console.log('스위치 클릭 이벤트가 제거되었습니다.');
}

// 스위치 클릭 이벤트 추가
function addSwitchClickEvent() {
 gitSwitch.addEventListener('click', function(event) {
     console.log('GIT이 연결되지 않은 상태입니다. 모달이 뜨도록 설정합니다.');
     $('#gitConnect').modal('show'); // 모달을 수동으로 띄움
 });
}

handleGitSwitchChange();

function removeGitHubDataFromCalendar() {
    const allEvents = calendar.getEvents();

    // 커밋 이벤트만 제거
    allEvents.forEach(function(event) {
        if (event.extendedProps.commit) {
            event.remove();  // 커밋 이벤트 제거
        }
    });

    console.log('GitHub 데이터가 캘린더에서 제거되었습니다.');
}

//스위치 상태에 따라 GitHub 데이터를 캘린더에서 추가/제거하는 함수
function handleGitSwitchChange() {
    gitSwitch.addEventListener('change', function(event) {
        if (gitSwitch.checked) {
            console.log('GIT 스위치 켜짐: 커밋 데이터를 추가합니다.');
            loadCalendarEvents();  // 커밋 데이터를 추가
        } else {
            console.log('GIT 스위치 꺼짐: 커밋 데이터를 제거합니다.');
            removeGitHubDataFromCalendar();  // 커밋 데이터를 제거
        }
    });
}



function connectBtnGit(){
	var gitUrl = document.querySelector("#gitConnectURL");
	var gitToken = document.querySelector("#gitConnectToken");
	let getGitUrl = gitUrl.value;
	const parts = getGitUrl.split('/');
	let owner = parts[parts.length - 2];
	let repo = parts[parts.length - 1];
	console.log(owner);
	console.log(repo);
	console.log("제출 버튼이 클릭됨");
	if(!confirm('레포지토리 연결을 계속 하시겠습니까?')){
		  return;
	}else{
	  var data = {
			 gitUrl: gitUrl.value,
			 gitToken: gitToken.value,
			 owner: owner,
			 repo: repo,
	    	 projectId: "${projectId}"
	       };
	       fetch(gitHref, {
	           method: 'POST',
	           headers: {
	               'Content-Type': 'application/json',
	           },
	           body: JSON.stringify(data)
	       })
	       .then(response => response.json())
	       .then(data => {
	    	   let gitDetail = data.target;
	           console.log(gitDetail);
	           console.log(gitDetail.owner);
	           gitOwner = gitDetail.owner;
	           gitRepo = gitDetail.repo;
	           gitToken = gitDetail.gitToken;

	            localStorage.setItem('gitOwner', gitOwner);
	            localStorage.setItem('gitRepo', gitRepo);
	            localStorage.setItem('gitToken', gitToken);

	            console.log("설정된 gitOwner: ", gitOwner);
	            console.log("설정된 gitRepo: ", gitRepo);
	            console.log("설정된 gitToken: ", gitToken)
	           if (data.success) {
	               alert('GITHUB 연결이 완료되었습니다.');
	               loadCalendarEvents();
	               reloading();
	           } else {
	               alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
	           }
	       })
	       .catch((error) => {
	           console.error('Error:', error);
	       });
	}
}

// GitHub 커밋 데이터를 가져오는 함수 (Personal Access Token 필요)
async function fetchCommits(owner, repo, token = null) {
	const url = `https://api.github.com/repos/\${owner}/\${repo}/commits`;
	const headers = token ? { 'Authorization': `token \${token}` } : {}; // 토큰이 있으면 헤더에 추가

    try {
        const response = await fetch(url, { headers });
        if (!response.ok) {
            throw new Error(`GitHub API 요청에 실패했습니다: ${response.status}`);
        }
        const data = await response.json();
        	console.log("커밋", data);

        // 커밋 데이터를 FullCalendar 이벤트 형식으로 변환
        return data.map(commit => ({
            title: commit.commit.message,  // 커밋 메시지를 제목으로 설정
            start: commit.commit.committer.date,  // 커밋 날짜를 시작 날짜로 설정
            url: commit.html_url,  // 커밋 URL을 클릭할 때 연결
            allDay: false,
            backgroundColor: '#FFEB3B',  // 커밋은 노란색으로 표시
            textColor: 'black',
            extendedProps: {
                commit: true  // 커밋 이벤트임을 표시하는 속성 추가
            }
        }));
    } catch (error) {
        console.error('Error fetching commits:', error);
        return [];
    }
}

// 이슈 및 커밋 데이터를 합쳐서 FullCalendar에 추가하는 함수
async function loadCalendarEvents() {
    // 기존 이슈 데이터는 events 배열에 이미 있음
    const issues = events;

    // GitHub 커밋 데이터 가져오기 (토큰이 필요한 경우 추가, 아니면 null)
    const commits = await fetchCommits(gitOwner, gitRepo, gitToken);

    // 이슈 데이터와 커밋 데이터를 합침
    const allEvents = issues.concat(commits);
    
    originalEvents = allEvents;

    // 캘린더에 새로운 이벤트 설정
    calendar.removeAllEvents();  // 기존 이벤트 제거
    calendar.addEventSource(allEvents);  // 새로운 이벤트 추가
}

// FullCalendar 설정 및 이벤트 데이터 추가
document.addEventListener('DOMContentLoaded', function() {
    // 기존 캘린더 초기화 및 이슈 로드
    calendar.render();

    // 커밋 데이터와 함께 캘린더 이벤트를 로드
    loadCalendarEvents();
});

var calendarEl = document.getElementById('calendar');
let isEditMode = false;
let issueIdx = null;

flatpickr.localize(flatpickr.l10ns.ko);

flatpickr('#inline-calendar', {
    locale: 'ko',
    inline: true
});


let events = [
    <c:forEach var="issue" items="${issueList}" varStatus="issueStatus">
    {
        id: "${issue.issueIdx}",
        title: "${issue.issueTitle}",
        start: "${issue.issueDate}",
        end: "${issue.issueDeadline}",
        allDay: true,
        backgroundColor: '#E2E0FF',
        textColor: 'black',
        extendedProps: {
            issueTagName: "${issue.issueTags[0].issueTagName}"
        }
    }
    <c:if test="${!issueStatus.last}">,</c:if>
    </c:forEach>
];


const issueDisabList = async (issueIdx) => {
	let issueVO = {
		issueIdx : issueIdx,
		projectId : projectId
	}

	axios({
		url : contextPath + "/timeLine/issueDisabList",
		method : "post",
		header : {
			"Accept" : "application/json"
		},
		data : issueVO
	})
	.then(resp => {

		for(let i = 0 ; i < resp.data.length ; i++) {
			$("select[name=subIssueIdx] option[value='" + resp.data[i].issueIdx + "']").prop('disabled', false);
		}
		return resp;
	})

}


var calendar = new FullCalendar.Calendar(calendarEl, {
	headerToolbar: {
		  left: 'prev,next today',
		  center: 'title',
		  right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
	},
	editable: true,
	height: '750px', // calendar 높이 설정
	expandRows: true, // 화면에 맞게 높이 재설정
	selectable: true, // 달력 일자 드래그 설정가능
	dayMaxEvents: true, // 이벤트가 오버되면 높이 제한 (+ 몇 개식으로 표현)
	locale: 'ko',
	initialView: 'dayGridMonth',
	select: function(arg) { // 캘린더에서 드래그로 이벤트를 생성할 수 있다.
		  const modal = document.querySelector("#addEventSidebar");
		  const endsDate = arg.end;
		  if (endsDate instanceof Date && !isNaN(endsDate)) {
		      const formattedStartDate = endsDate.toISOString().split('T')[0];
		      document.getElementById("issueDate").value = formattedStartDate;
		  } else {
		      console.error('Invalid end date value:', endsDate);
		  }

		  new bootstrap.Offcanvas(modal).show();
	},
	events: events,
	eventClick: async function(info) {
		issueIdx = info.event.id;
		console.log("Clicked Event ID:", issueIdx);

		$("#subIssueIdx option").prop('disabled', true);
		await issueDisabList(issueIdx);

		axios({
		url: `\${baseURI}/\${issueIdx}`,
			method : "get",
			header : {
				"Content-Type" : "application/json"
			}
		})
		.then(resp => {
			let headTitle = document.querySelector("#offcanvasEcommerceCustomerAddLabel");
			headTitle.innerHTML="이슈 조회하기";
			let issueData = resp.data;
			console.log("issueData:",issueData);
			modal.querySelector("#issueTitle").value = issueData.issueTitle;
			modal.querySelector("#issueContent").value = issueData.issueContent;
			document.querySelector("select[name='issueOfficer']").value = issueData.issueOfficer;
			document.querySelector("select[name='issueProgressStatus']").value = issueData.issueProgressStatus;
			document.querySelector("select[name='issuePriorityName']").value = issueData.issuePriorityName;
			document.querySelector("select[name='issueTags[0].issueTagName']").value = issueData.issueTags[0].issueTagName;
			modal.querySelector("#issueDate").valueAsDate = new Date(issueData.issueDate[0], issueData.issueDate[1] - 1, issueData.issueDate[2] + 1);
		    console.log("시작일은", new Date(issueData.issueDate[0], issueData.issueDate[1] - 1, issueData.issueDate[2]));
		    console.log("마감일은", new Date(issueData.issueDeadline[0], issueData.issueDeadline[1] - 1, issueData.issueDeadline[2]));
			if(new Date(issueData.issueDate[0], issueData.issueDate[1] - 1, issueData.issueDate[2])<=new Date(issueData.issueDeadline[0], issueData.issueDeadline[1] - 1, issueData.issueDeadline[2])){
			    modal.querySelector("#issueDeadline").valueAsDate = new Date(issueData.issueDeadline[0], issueData.issueDeadline[1] - 1, issueData.issueDeadline[2]+1);
			}else{
				modal.querySelector("#issueDeadline").valueAsDate = new Date(issueData.issueDeadline[0], issueData.issueDeadline[1] - 1, issueData.issueDeadline[2]);
			}
			modal.querySelector("select[name='subIssueIdx']").value = issueData.subIssueIdx;
				modalCreateBtn.innerHTML = "수정하기";
				modalResetBtn.innerHTML = "삭제하기";
			isEditMode = true;
			new bootstrap.Offcanvas(modal).show();
		})
		.catch((error) => {
		    console.error('Error:', error);
		});

		modal.addEventListener('hidden.bs.offcanvas', function () {
		    document.getElementById("timeLineInsert").reset();
		    let headTitle = document.querySelector("#offcanvasEcommerceCustomerAddLabel");
		    headTitle.innerHTML = "이슈 생성하기";
		    let modalCreateBtn = document.querySelector("#modalCreateBtn");
		    modalCreateBtn.innerHTML = "생성하기";
		    let modalResetBtn = document.querySelector("#modalResetBtn");
		    modalResetBtn.innerHTML = "취소";
		    isEditMode = false;
		});
	},
	eventDidMount: function(info) {
		// 오늘 날짜 가져오기
		var today = new Date().toISOString().split('T')[0];  // 오늘 날짜 (YYYY-MM-DD 형식)
		var eventDate = info.event.start.toISOString().split('T')[0];  // 이벤트 날짜 (YYYY-MM-DD 형식)

		// 커밋 이벤트인지 확인
		if (info.event.extendedProps.commit) {
		        info.el.style.backgroundColor = '#FFEB3B'; 
		        info.el.style.color = 'black';
		} else {
		    // 커밋이 아닌 이벤트의 기본 스타일 적용 (예: 이슈)
		    info.el.style.backgroundColor = '#E2E0FF'; 
		    info.el.style.color = 'black';
		}
	}
});
console.log("이벤트:", events);
originalEvents = calendar.getEvents().map(event => ({
	id: event.id,
	title: event.title,
	start: event.start,
	end: event.end,
	allDay: event.allDay,
	backgroundColor: '#E2E0FF',
	textColor: 'black',
	extendedProps: {
	    ...event.extendedProps,
	    issueTagName: event.extendedProps.issueTagName
	}
}));

//검색과 태그에 따라 이벤트 필터링
function filterEvents(query, filterTag) {
    const allEvents = originalEvents.filter(function(event) {
        const title = event.title ? event.title.toLowerCase() : ''; // title이 없으면 빈 문자열로 처리
        const tag = event.extendedProps && event.extendedProps.issueTagName ? event.extendedProps.issueTagName.toLowerCase() : ''; // tag가 없으면 빈 문자열로 처리

        const matchesQuery = query ? title.includes(query.toLowerCase()) : true; // query가 있으면 필터, 없으면 true
        const matchesTag = filterTag ? tag.includes(filterTag.toLowerCase()) : true; // filterTag가 있으면 필터, 없으면 true

        return matchesQuery && matchesTag;
    });

    // 모든 이벤트 제거 후 필터된 이벤트만 추가
    calendar.removeAllEvents();
    calendar.addEventSource(allEvents);
}

calendar.render();
const modal = document.querySelector("#addEventSidebar");
const newProjectMemIdx = document.querySelector("#timeLineInsertModalBtn").dataset.projectMemIdx;
let modalCreateBtn = null;
let modalResetBtn = null;
const baseURI = `\${contextPath}/\${groupId}/\${projectId}/calendar`;

document.addEventListener("DOMContentLoaded", async ()=>{
	dDayAlarm();
	let searchInputForm = document.querySelector("#searchInputForm");
	let searchIssueTagName = document.querySelector("#searchIssueTagName");
	
	// 검색 입력 시 이벤트 필터링
    searchInputForm.addEventListener("input", debounce(function(e) {
        let query = e.target.value || ''; // 값이 없으면 빈 문자열로 처리
        let filterTag = searchIssueTagName.value || ''; // 값이 없으면 빈 문자열로 처리
        filterEvents(query, filterTag);
    }, 300));

    // 태그 선택 시 이벤트 필터링 (검색 입력값이 없어도 동작하도록 변경)
    $("#searchIssueTagName").change(debounce(function(e) {
    	console.log("클릭되었습니다1!!!!!!!!!!!!!!!!");
        let query = searchInputForm.value || ''; // 검색어가 없을 경우 빈 문자열로 처리
        let filterTag = e.target.value || ''; // 태그 값이 없을 경우 빈 문자열로 처리
        console.log("클릭되었습니다1!!!!!!!!!!!!!!!!", filterTag);
        filterEvents(query, filterTag);
    }, 300));
    
	modalCreateBtn = document.querySelector("#modalCreateBtn");
	modalResetBtn = document.querySelector("#modalResetBtn");
	document.querySelector("#addEventSidebar").addEventListener("submit", function(e) {
		if(isEditMode === false){
	      issueInsert(e);
		}else{
		  issueUpdate(e);
		}
	});
	document.querySelector("#modalResetBtn").addEventListener("click", function(e) {
		if(isEditMode === true){
	      issueDelete(e);
		}
	});
 	document.querySelector("#btnIssueCreate").addEventListener("click", function() {
 		$("#subIssueIdx option").prop('disabled', false);
		document.querySelector("#addEventSidebar").reset();
	});

 	function dDayAlarm(){
		const allEvents = calendar.getEvents();
		let today = new Date();

		// 오늘의 시간 부분을 제거하여 날짜만 비교하도록 설정
		today.setHours(0, 0, 0, 0);
		console.log("today", today);

		allEvents.forEach(function(event) {
			let endDate = null;

			if (event.end === null) {
			    endDate = event.start;
			} else {
			    endDate = new Date(event.end);
			    endDate.setDate(endDate.getDate() - 1);
			}

			endDate.setHours(0, 0, 0, 0);

			if (today.getTime() === endDate.getTime()) {
				alert(event.title + "이슈가 오늘 마감입니다!");
			}
		});
 	}


// 	searchInputForm.addEventListener("input", debounce(function(e) {

// 		console.log("oninput", e.target.value);
// 		const query = e.target.value;
// 		console.log(query);
// 		const allEvents = calendar.getEvents();

// 		allEvents.forEach(function(event) {
// 		console.log(event.title);
// 		  event.remove();
// 		});

// 		if (query === '') {
// 			originalEvents.forEach(function(event) {
// 	        	calendar.addEvent(event);
// 	        });
// 		} else {
// 		    originalEvents.forEach(function(event) {
// 		        const title = event.title.toLowerCase();
// 		        if (title.includes(query)) {
// 		          calendar.addEvent(event);
// 		        }
// 	     	});
// 		}
// 	}, 300));

// 	$("#searchIssueTagName").change(debounce(function(){
// 		console.log($(this).val()); //value값 가져오기
// 		const query = $(this).val();
// 		const allEvents = calendar.getEvents();

// 		allEvents.forEach(function(event) {
// 	  	  event.remove();
// 	  	});

// 	  	if (query === '') {
// 		    originalEvents.forEach(function(event) {
// 		    	calendar.addEvent(event);
// 		    });
// 	  	} else {
// 		  	originalEvents.forEach(function(event) {
// 		        const title = event.extendedProps.issueTagName;
// 		        if (title.includes(query)) {
// 		          calendar.addEvent(event);
// 		        }
// 		    });
// 	  	}
// 	}, 300));
});


// 캘린더에 적용될 이슈 리스트
const issueList = async ()=> {
	return FetchUtils.fetchForJSON(baseURI);
}

// 캘린더 이슈 추가
const issueInsert = (e) => {
	e.preventDefault();
	let formData = new FormData(e.target);

	axios({
		url : baseURI,
		method : "post",
		header : {
			"Content-Type" : "application/json"
		},
		data : formData
	})
	.then(resp => {
		if(resp.data.success == false) {
			let err = "";
			Object.keys(resp.data.errors).forEach(function(k){
			    err += k + " : " + resp.data.errors[k] + ". ";
			});
			Swal.fire({
				title: '오류 발생',
				text: err,
				icon: 'error',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});
		}else{
			console.log(resp);
			alert('이슈가 성공적으로 등록되었습니다.');
			reloading();
		}
	})
	.catch((error) => {
	    console.error('Error:', error);
    });
}

// 캘린더 이슈 수정
const issueUpdate = (e) => {
	e.preventDefault();
	let formData = new FormData(document.querySelector("#timeLineInsert"));
	axios({
		url : `\${baseURI}/\${issueIdx}`,
		method : "post",
		header : {
			"Content-Type" : "application/json"
		},
		data : formData
	})
	.then(resp => {
		console.log(resp);
		alert('이슈가 성공적으로 수정되었습니다.');
		modalCreateBtn.innerHTML = "생성하기";
		isEditMode = false;
		reloading();
	})
	.catch((error) => {
		alert('수정 실패 : ', error);
	    console.error('Error:', error);

	    isEditMode = false;
	    reloading();
	});
}

// 캘린더 이슈 삭제
const issueDelete = (e) => {
	e.preventDefault();
	axios.delete(`\${baseURI}/\${issueIdx}`)
	.then(resp => {
	    console.log(resp);
	    alert('이슈가 성공적으로 삭제되었습니다.');
	    isEditMode = false;
	    reloading();
	})
	.catch((error) => {
	    console.error('Error:', error);
	    isEditMode = false;
	});
}

function reloading(){
	let isEditMode = true;
    location.reload();
}

function formReset(){
	 document.getElementById("timeLineInsert").reset();
}

</script>