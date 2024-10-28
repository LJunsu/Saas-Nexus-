let kanban;
const contextPath10 = document.body.dataset.contextPath;
const detailURI4 = `${contextPath10}`;
const projectId = document.querySelector("#projectIdDiv").dataset.projectId;
const memberId = document.querySelector("#divArea").dataset.memberId;
// 비동기 함수 시작
(async function() {

	// Kanban 사이드바 및 기타 DOM 요소 초기화
	const kanbanSidebar = document.querySelector('.kanban-update-item-sidebar'); // Kanban 사이드바 요소 선택
	const kanbanWrapper = document.querySelector('.kanban-wrapper'); // Kanban 래퍼 요소 선택
	const datePicker = document.querySelector('#issueDeadline'); // 날짜 선택기 요소 선택
	const datePicker1 = document.querySelector('#issueDate'); // 날짜 선택기 요소 선택
	const newDatePicker = document.querySelector('#new-due-date'); // 새 날짜 선택기 요소 선택
	const newDatePicker2 = document.querySelector('#new-issueDate'); // 새 날짜 선택기 요소 선택
	const select2Update = document.getElementById('label-update'); // 업데이트 라벨 선택기 요소 선택
	const select2New = document.getElementById('label-new'); // 새 라벨 선택기 요소 선택
	// 부트스트랩 오프캔버스 초기화 전에 요소가 존재하는지 확인
	let kanbanOffcanvas;
	if (kanbanSidebar) {
		kanbanOffcanvas = new bootstrap.Offcanvas(kanbanSidebar); // 부트스트랩 오프캔버스 초기화
	}

	// Kanban 보드 설정
	const boards = [
		{ id: 'board-in-ready', title: '준비중', item: [] }, // 준비중 보드 설정
		{ id: 'board-in-progress', title: '진행중', item: [] }, //진행중 보드 설정
		{ id: 'board-done', title: '완료', item: [] } // 완료 보드 설정
	];
	let isSubmitting = false; // 제출 중인지 여부를 나타내는 변수
	const maxTitleLength = 100; // 제목 최대 길이 설정
	const maxContentLength = 500; // 내용 최대 길이 설정

	const transformTodoData = (data) => data.map((issue, index) => {
		const content = issue.issueVO[0].issueContent || '';
    	const truncatedContent = content.length > 100 ? content.substring(0, 100) + '...' : content;
		const CreationDate = new Date(issue.issueVO[0].issueCreationDate); // 서버에서 받은 마감 날짜를 JavaScript Date 객체로 변환
		const issueDate = new Date(issue.issueVO[0].issueDate); // 서버에서 받은 마감 날짜를 JavaScript Date 객체로 변환
		const issueDeadline = new Date(issue.issueVO[0].issueDeadline); // 서버에서 받은 마감 날짜를 JavaScript Date 객체로 변환
		const issueProgressStatus = issue.issueVO[0].issueProgressStatus;

		const formattedCreationDate = isNaN(CreationDate.getTime()) ? '' : formatDate(CreationDate); // 날짜가 유효하지 않으면 빈 문자열, 유효하면 포맷된 날짜 반환
		const formattedIssueDate = isNaN(issueDate.getTime()) ? '' : formatDate(issueDate); // 날짜가 유효하지 않으면 빈 문자열, 유효하면 포맷된 날짜 반환
		const formattedIssueDeadline = isNaN(issueDeadline.getTime()) ? '' : formatDate(issueDeadline); // 날짜가 유효하지 않으면 빈 문자열, 유효하면 포맷된 날짜 반환
		console.log(issueProgressStatus);

		return {
			id: `${getBoardIdByStatus(issueProgressStatus)}-${index + 1}`, // 분류 (in-ready, in-progress , complete 의 첫번째 두번째 )
			title: issue.issueVO[0].issueTitle || 'No Title', // 제목 설정, 제목이 없으면 'No Title' 사용
			content: truncatedContent, //표시되는 컨텐츠
			fullContent : content, //실제 컨텐츠 데이터
			TagName: issue.issueVO[0].issueTags[0].issueTagName || 'x', // 배지 텍스트 설정
			badge: getBadgeColor(issue.issueVO[0].issuePriorityName),
			stts: issue.issueVO[0].issueProgressStatus, // 카테고리에 따른 배지 색상 설정
			issueCreationDate: formattedCreationDate, // 생성날짜
			issueDate: formattedIssueDate, // 언제 할건지 이슈날자
			dueDate: formattedIssueDeadline, // 마감 날짜 설정
			issueId: issue.issueVO[0].issueIdx, // 할 일의 ID 추가
			answer: issue.issueVO[0].issueBoardAnswers[0].answerCount,
			badgeText: issue.issueVO[0].issuePriorityName,
			answerCount : issue.issueVO[0].issueBoardAnswers[0].answerCount,
			memberName : issue.memberName,
			memberId : memberId,
			issueOfficer :issue.issueVO[0].issueOfficer,
			subIssueIdx : issue.issueVO[0].subIssueIdx
		};
	});
	// 날짜 형식 변환 함수
	const formatDate = (date) => {
		const year = date.getFullYear(); // 연도 가져오기
		const month = ('0' + (date.getMonth() + 1)).slice(-2); // 월 가져오기 및 2자리 형식으로 변환
		const day = ('0' + date.getDate()).slice(-2); // 일 가져오기 및 2자리 형식으로 변환
		return `${year}-${month}-${day}`; // 'YYYY-MM-DD' 형식으로 반환
	};



	// tag에 따라서 어떤 뱃지로 할찌
	const getBadgeColor = (category) => {
		switch (category) {
			case 'Highest': return 'danger'; // Personal : 노란색 배지
			case 'High': return 'warning'; // Study : 빨간색 배지danger
			case 'Medium': return 'primary'; // Hobby : 파란색 배지
			case 'Low': return 'secondary'; // Others : 회색 배지
			case 'Lowest': return 'info'; // Family : 파란색 배지
			default: return 'secondary'; // 기본 : 회색 배지
		}
	};
	
  const getBadgeText = (category) => {
 	 switch (category) {
    case 'Highest': return '매우높음'; // DB에서 'Highest' -> UI에선 '매우높음'
    case 'High': return '높음'; // DB에서 'High' -> UI에선 '높음'
    case 'Medium': return '중간'; // DB에서 'Medium' -> UI에선 '중간'
    case 'Low': return '낮음'; // DB에서 'Low' -> UI에선 '낮음'
    case 'Lowest': return '매우낮음'; // DB에서 'Lowest' -> UI에선 '매우낮음'
    default: return '낮음'; // 기본값 -> '낮음'
	  }
	};
	

	//호출시 case에 따라서 칸반에 boardId가 정해지고 가져온 하나의 raw의 정보가 어디로갈지 정해짐
	const getBoardIdByStatus = (status) => {
		switch (status) {
			case 'Not Started': return 'board-in-ready'; // 상태가 0일 경우 'board-in-progress'
			case 'In Progress': return 'board-in-progress'; // 상태가 1일 경우 'board-in-review'
			case 'Completed': return 'board-done'; // 상태가 2일 경우 'board-done'
			default: return 'board-in-ready'; // 기본적으로 'board-in-progress'
		}
	};

	const loadData = async () => {
		try {
			const data = await FetchUtils.fetchForJSON(`${detailURI4}/${projectId}/kanban/rest/getMyIssueList`);

			console.log(data); // 데이터를 콘솔에 출력
			console.log(data.List[0]); // 데이터를 콘솔에 출력
			const issueData = data.List; // 데이터를 콘솔에 출력
			console.log('0000000', issueData);

			const transformedData = transformTodoData(issueData); // 데이터를 변환
			boards.forEach(board => {
				board.item = transformedData.filter(item => getBoardIdByStatus(item.stts) === board.id); // 각 보드에 변환된 데이터를 필터링(보드 ID)하여 할당
			});
			if (kanbanWrapper) {
				kanbanWrapper.innerHTML = ''; // Kanban 보드의 내용을 초기화
				initializeKanban(); // Kanban 보드를 초기화
			}
		} catch (error) {
			console.error('Error loading todo data:', error); // 데이터 로드 중 오류 발생 시 콘솔에 출력
		}
	};

	const initializeKanban = () => {

		// 페이지가 처음 로드되었을 때에만 실행(중복 초기화 방지)
		if (!document.querySelector('.kanban-container')) {
			
			// Kanban 보드의 아이템 추가, 클릭, 드래그 기능을 설정
			kanban = new jKanban({

				element: '.kanban-wrapper', // Kanban 요소 지정
				gutter: '60px', // 보드 간격 설정
				widthBoard: '1px', // 보드 너비 설정
				dragItems: true, // 아이템 드래그 가능 여부 설정
				boards: boards, // 보드 데이터 설정
				dragBoards: false, // 보드 드래그 가능 여부 설정 
				addItemButton: false, // 아이템 추가 버튼 사용 여부 설정
				buttonContent: '+ 새 할일 추가', // 아이템 추가 버튼 내용 설정
				itemAddOptions: { enabled: false }, // 아이템 추가 옵션 설정
				// 아이템 클릭 시 호출되는 함수
				click: function(el) {
					console.log(el);
					const element = el;
					const title = element.getAttribute('data-eid') ? element.querySelector('.kanban-text').textContent : element.textContent; // 제목 설정
					const content = element.getAttribute('data-fullcontent'); // 내용 설정
					const creationDate = element.getAttribute('data-issuecreationdate'); // 생성 날짜 설정
					const issuedate = element.getAttribute('data-issuedate'); // 이슈 날짜 설정
					const date = element.getAttribute('data-duedate'); // 마감 날짜 설정
					const dateObj = new Date(); // 현재 날짜 객체 생성
					const year = dateObj.getFullYear(); // 현재 연도 가져오기
					const cdateToUse = creationDate ? creationDate : `${dateObj.getDate()} ${dateObj.toLocaleString('en', { month: 'long' })}, ${year}`; // 생성 날짜 설정
					const idateToUse = issuedate ? issuedate : `${dateObj.getDate()} ${dateObj.toLocaleString('en', { month: 'long' })}, ${year}`;  // 이슈 날짜 설정
					const dateToUse = date ? date : `${dateObj.getDate()} ${dateObj.toLocaleString('en', { month: 'long' })}, ${year}`; // 마감 날짜 설정
					
					
					const sttts = element.getAttribute('data-stts'); // 이슈 pk -- hidden
					const issueIdx = element.getAttribute('data-issueid'); // 이슈 pk -- hidden
					const label = element.getAttribute('data-badgetext'); // 이슈 중요도
					const color = element.getAttribute('data-badge'); // 이슈 중요도 배지 색상 설정
					const answercount = element.getAttribute('data-answercount'); // 이슈에 대한 답글 갯수
					const tagName = element.getAttribute('data-tagname'); // 이슈에 대한 답글 갯수
					const projectOfficer = element.getAttribute('data-issueofficer'); // 이슈에 대한 답글 갯수
					const subIssueIdx = element.getAttribute('data-subissueidx'); // 이슈에 대한 답글 갯수
					
					kanbanSidebar.setAttribute('data-editing-id', element.getAttribute('data-eid')); // Kanban 사이드바에 편집 중인 ID 설정
					kanbanOffcanvas.show(); // 오프캔버스 표시
					document.getElementById('tab-update').classList.add('show', 'active'); // 업데이트 탭 활성화
					document.getElementById('tab-new').classList.remove('show', 'active'); // 새 탭 비활성화
					kanbanSidebar.querySelector('#issueTitle').value = title; // 제목 입력 필드에 값 설정
					kanbanSidebar.querySelector('#issueContent').value = content; // 내용 입력 필드에 값 설정
					
					kanbanSidebar.querySelector('#issueOfficer').value = projectOfficer; // 내용 입력 필드에 값 설정
					kanbanSidebar.querySelector('#issueProgressStatus').value = sttts; // 내용 입력 필드에 값 설정
					
					//이슈 날짜
					kanbanSidebar.querySelector('#issueDate').value = idateToUse; // 내용 입력 필드에 값 설정
					kanbanSidebar.querySelector('#issueDeadline').value = dateToUse; // 내용 입력 필드에 값 설정
					//이슈 상위
					kanbanSidebar.querySelector('#subIssueIdx').value = subIssueIdx; // 내용 입력 필드에 값 설정
					
					//이슈 태그
					kanbanSidebar.querySelector('#issueTagName').value = tagName; // 내용 입력 필드에 값 설정
					//hidden 값 설정
					kanbanSidebar.querySelector('#issueIdx').value = issueIdx; // 내용 입력 필드에 값 설정
					$('#label-update').val(label).trigger('change'); // 라벨 선택기에 값 설정
          			$('#label-update').next().find('.badge').attr('class', 'badge bg-label-' + color + ' rounded-pill').text(label); // 배지 클래스 및 텍스트 설정
				},

				// 새 할일 추가 버튼 클릭 시 호출되는 함수
				buttonClick: function(el, boardId) {
					kanbanOffcanvas.show(); // Offcanvas를 표시
					document.getElementById('tab-update').classList.remove('show', 'active'); // 할일 탭 비활성화
					document.getElementById('tab-new').classList.add('show', 'active'); // 새할일 탭을 활성화
					kanbanSidebar.querySelector('#new-title').value = ''; // 제목 입력 필드 초기화
					kanbanSidebar.querySelector('#new-due-date').value = ''; // 마감일 입력 필드를 오늘 날짜로 설정
					kanbanSidebar.querySelector('new-issueDate').value = ''; // 마감일 입력 필드를 오늘 날짜로 설정
					kanbanSidebar.querySelector('#new-content').value = ''; // 내용 입력 필드를 초기화
					$('#label-new').val(null).trigger('change'); // 라벨 필드를 초기화
				},
				//
				// 아이템이 드롭될 때 호출되는 함수
				dropEl: async function(el, target, source, sibling) {
					const boardId = target.parentElement.getAttribute('data-id'); //  board-done-> 이런거 가져오기
					const issueId = el.getAttribute('data-issueid'); // 이슈 ID가져오기
					const issueTitle = el.querySelector('.kanban-text').textContent; // 이슈 제목 가져오기
					const issueContent = el.getAttribute('data-content'); // 내용 가져오기
					const issueProgressStatus = el.getAttribute('data-stts'); // 이슈 중요도 가져오기
					const issueDueDate = el.getAttribute('data-duedate'); // 이슈 마감일 가져오기
					const stts = getStatusFromBoardId(boardId); // 보드 ID를 상태 값으로 변환하는 함수 사용
					
					// 보드가 Done일 경우 마감 날짜의 text-danger 클래스 제거
					if (stts === 'board-done') {
						issueDueDate.classList.remove('text-danger');
					} else {
						const dueDate = new Date(issueDueDate.textContent); // 마감 날짜 가져오기
						if (dueDate < new Date()) {
							dueDateElement.classList.add('text-danger'); // 마감 날짜가 현재 날짜보다 이전일 경우 text-danger 클래스 추가
						}
					}

					const issueData = {
						issueIdx : issueId,
						issueTitle: issueTitle || 'No Title', // 제목이 없으면 기본값 설정
						issueDeadline: issueDueDate || formatDate(new Date()), // 마감일이 없으면 기본값 설정
						issueProgressStatus: stts || 'Others', // 카테고리가 없으면 기본값 설정
						issueContent: issueContent || '' // 내용이 없으면 기본값 설정
					};

					// 서버에 상태 변경 요청
					try {
						const response = await FetchUtils.fetchForJSON(`${detailURI4}/${projectId}/kanban/rest/updateIssue`, {
							method: 'PUT',
							headers: {
								"accept": "application/json",
								'Content-Type': 'application/json'
							},
							body: JSON.stringify(issueData) // 요청 본문 설정
						});

						if (response.cnt > 0) {
							console.log('상태가 성공적으로 변경되었습니다.');
						} else {
							console.error('상태 변경 중 오류가 발생했습니다.');
						}
					} catch (error) {
						console.error('상태 변경 중 오류가 발생했습니다.', error);
					}
				}
			});

			// 아이템 추가 버튼을 TO DO 보드 밑에 추가
			const addItemButtonHTML = '<button class="kanban-title-button btn">+ 새 할일 추가</button>'; // 아이템 추가 버튼 HTML 생성
			const targetBoardId = 'board-in-ready'; // 타겟 보드 ID 설정
			const targetBoard = document.querySelector(`.kanban-board[data-id="${targetBoardId}"] .kanban-drag`); // 타겟 보드 요소 선택

			if (!targetBoard) {
				console.error(`targetBoard with ID ${targetBoardId} not found`); // 타겟 보드가 없는 경우 에러 출력
			} else {
				targetBoard.insertAdjacentHTML('beforeend', addItemButtonHTML); // 타겟 보드에 아이템 추가 버튼 삽입
				const addButton = targetBoard.querySelector('.kanban-title-button'); // 아이템 추가 버튼 선택

				// 아이템 추가 버튼이 있는 경우 클릭 이벤트 리스너 추가
				if (addButton) {
					addButton.addEventListener('click', function() {
						const offcanvas = document.querySelector('.kanban-update-item-sidebar'); // 오프캔버스 요소 선택
						if (offcanvas) {
							document.getElementById('tab-update').classList.remove('show', 'active'); // 할일 탭 비활성화
							document.getElementById('tab-new').classList.add('show', 'active'); // 새할일 탭 활성화
							kanbanSidebar.querySelector('#new-title').value = ''; // 제목 입력 필드 초기화
							kanbanSidebar.querySelector('#new-due-date').value = ''; // 날짜 선택기에 현재 날짜 설정
							kanbanSidebar.querySelector('#new-issueDate').value = ''; // 날짜 선택기에 현재 날짜 설정
							kanbanSidebar.querySelector('#new-content').value = ''; // 내용 입력 필드 초기화
				              $('#label-new').val(null).trigger('change'); // 라벨 선택기 초기화
				              kanbanOffcanvas.show(); // 오프캔버스 표시
						}
					});
				}
			}
			//
			//      //Kanban 아이템의 텍스트, 내용, 헤더, 푸터를 초기화 및 추가
			const kanbanItems = document.querySelectorAll('.kanban-item'); // Kanban 아이템 선택
			kanbanItems.forEach(el => {
				const element = "<span class='kanban-text fw-bold'>" + el.textContent + '</span>'; // 제목 요소 생성
				const content = el.getAttribute('data-content') || ''; // 내용 가져오기
				 const fullContent = el.getAttribute('data-fullcontent') || content; // 전체 내용 저장
				const contentElement = "<div class='kanban-content'>" + content + "</div>"; // 내용 요소 생성
				el.textContent = ''; // 아이템 내용 초기화
				if (el.getAttribute('data-badge') !== undefined && el.getAttribute('data-badgetext') !== undefined) {
					el.insertAdjacentHTML('afterbegin', renderHeader(el.getAttribute('data-badge'), getBadgeText(el.getAttribute('data-badgetext')), el.getAttribute('data-issueid')) + element + contentElement); // 헤더, 텍스트, 내용, todoID 요소 삽입
				}
				const dueDate = el.getAttribute('data-duedate'); // 마감 날짜 가져오기
				const issueStart = el.getAttribute('data-issuedate'); // 마감 날짜 가져오기
				const boardId = el.closest('.kanban-board').getAttribute('data-id'); // 보드 ID 가져오기
				const answercount =	el.getAttribute('data-answercount');
				const memberName = el.getAttribute('data-membername');
				const memberId = el.getAttribute('data-memberid');
				let footer = renderFooter(dueDate, boardId , answercount,memberName,memberId,issueStart); // 푸터 요소 생성
				if (footer) {
					el.insertAdjacentHTML('beforeend', footer); // 아이템의 맨 끝에 푸터 요소 삽입
				}
			});
			//
			      addDeleteEventListeners(); // 삭제 이벤트 리스너 추가
			      addDeleteEventListeners1(); // 삭제 이벤트 리스너 추가
		}
	};
	
document.addEventListener('DOMContentLoaded', loadData);
	const autoBtn = document.querySelector('#exampleBtn');
	
	const newTitle1 = document.getElementById('new-title');
    const newContent1 = document.querySelector('#new-content');
    const newIssueOfficer1 = document.querySelector('#new-issueOfficer');
    const newIssueProgressStatus1 = document.querySelector('#new-issueProgressStatus');
    const newIssuePriorityName1 = document.querySelector('#new-issuePriorityName');
    const newIssueTagName1 = document.querySelector('#new-issueTagName');
    const newIssueDate1 = document.querySelector('#new-issueDate');
    const newIssueDeadLine1 = document.getElementById('new-due-date');
    const newSubIssueIdx1 = document.querySelector('#new-subIssueIdx1');
    const newProjectIdx1 = document.getElementById('projectIdx');
    const newProjectMemIdx1 = document.getElementById('projectMemIdx');
	
	autoBtn.addEventListener('click',function(){
		newTitle1.value='비밀번호 변경 이슈';
		newContent1.value=' 보안 강화를 위해 정기적으로 비밀번호를 변경해 주시길 권고드립니다. 최근 3개월 이내 비밀번호를 변경하지 않으신 분들은 보안을 위해 꼭 변경해 주시기 바랍니다. 감사합니다. 알리기';
	    newIssueOfficer1.value = 'PM831';  // 'PM830' 값이 맞는지 확인하세요
        newIssueProgressStatus1.value = 'Not Started';
        newIssuePriorityName1.value = 'Medium';
        newIssueTagName1.value = 'integration';
        newIssueDate1.value = '2024-09-20';
        newIssueDeadLine1.value = '2024-10-03';


		
	});
	

	// DatePicker 초기화 함수
  function initDatePicker(picker) {
    if (picker) {
      picker.flatpickr({
        monthSelectorType: 'static', // 월 선택기 유형 설정
        dateFormat: 'Y-m-d', // 날짜 형식 설정
        defaultDate: new Date() // 기본 날짜 설정
      });
    }
  }

  // Select2 초기화 함수
function initSelect2(select2) {
  // jQuery로 select2 요소를 감싸서 jQuery 객체로 변환
  const $select2 = $(select2); 

  // 변환된 $select2 객체에 대해 each 메소드를 사용
  if ($select2.length) {
    function renderLabels(option) {
      if (!option.id) {
        return option.text; // 옵션 ID가 없는 경우 텍스트 반환
      }
	const text = getBadgeText($(option.element).data('badgetext')); // 텍스트 결정

      var $badge = "<div class='badge " + $(option.element).data('color') + " rounded-pill'> " + text + '</div>'; // 배지 HTML 생성
      return $badge; // 배지 HTML 반환
    }

    $select2.each(function () {
      var $this = $(this); // 현재 요소 지정
      $this.wrap("<div class='position-relative'></div>").select2({
        placeholder: '라벨을 선택하세요', // 선택기 플레이스홀더 설정
        dropdownParent: $this.parent(), // 드롭다운 부모 요소 설정
        templateResult: renderLabels, // 옵션 템플릿 설정
        templateSelection: renderLabels, // 선택 템플릿 설정
        escapeMarkup: function (es) {
          return es; // 마크업 이스케이프 설정
        }
      });
    });
  }
}

  initDatePicker(datePicker); // 날짜 선택기 초기화
  initDatePicker(newDatePicker); // 새 날짜 선택기 초기화
  initDatePicker(datePicker1); // 날짜 선택기 초기화
  initDatePicker(newDatePicker2); // 새 날짜 선택기 초기화
  initSelect2(select2Update); // 업데이트 라벨 선택기 초기화
  initSelect2(select2New); // 새 라벨 선택기 초기화
	
	function renderBoardDropdown() {
		return (
			"<div class='dropdown'>" +
			"<i class='dropdown-toggle ti ti-dots-vertical cursor-pointer' id='board-dropdown' data-bs-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></i>" +
			"<div class='dropdown-menu dropdown-menu-end' aria-labelledby='board-dropdown'>" +
			"<a class='dropdown-item delete-board' href='javascript:void(0)'> <i class='ti ti-trash ti-xs me-1'></i> <span class='align-middle'>Delete</span></a>" +
			"<a class='dropdown-item' href='javascript:void(0)'><i class='ti ti-edit ti-xs me-1'></i> <span class='align-middle'>Rename</span></a>" +
			"<a class='dropdown-item' href='javascript:void(0)'><i class='ti ti-archive ti-xs me-1'></i> <span class='align-middle'>Archive</span></a>" +
			'</div>' +
			'</div>'
		);
	}

	function renderDropdown() {
		return ( // 삭제 아이콘 HTML을 반환 (구 드롭다운css 때문에 class유지함)
			"<div id='hiddenDBtn' class='dropdown kanban-tasks-item-dropdown'>" +
			"<i class='ti ti-trash delete-task'></i>" +
			'</div>'
		);
	}


	// 아이템 헤더를 렌더링하는 함수
	function renderHeader(color, text, todoId) {
		return (
			"<div class='d-flex justify-content-between flex-wrap align-items-center mb-2 pb-1'>" +
			"<div class='item-badges'> " +
			"<div class='badge rounded-pill bg-label-" +
			color +
			"'> " +
			text +
			'</div>' +
			"<input type='hidden' class='todo-id' value='" + todoId + "'>" + // hidden 필드로 todoId 추가
			'</div>' +
			renderDropdown() + // 아이템 삭제 메뉴를 포함
			'</div>'
		);
	}


	function renderFooter(dueDate, boardId, answerCount, memberName , memberId, issueStart) {
		console.log(boardId);
		const startDate = new Date(issueStart); //이슈시작날까
		const currentDate = new Date(); // 현재 날짜 객체 생성 (비교)
		const date = new Date(dueDate); // 마감 날짜 객체 생성
		const formattedStartDate = startDate.toISOString().split('T')[0]; // 날짜를 'YYYY-MM-DD' 형식으로 포맷 (2024-06-13T15:00:00.000Z -> 2024-06-13)
		const formattedDate = date.toISOString().split('T')[0]; // 날짜를 'YYYY-MM-DD' 형식으로 포맷 (2024-06-13T15:00:00.000Z -> 2024-06-13)
		const isOverdue = date < currentDate; // 마감일이 지난 경우를 확인
		const dateClass = (isOverdue && boardId !== 'board-done') ? 'text-danger' : ''; // 마감일이 지났고 완료되지 않은 경우 text-danger 클래스 설정
		// 풋터 HTML을 반환
		return (
		 "<div class='d-flex justify-content-between align-items-center flex-wrap mt-2 pt-1'>" +
         "<div class='d-flex'>" +
         "<div class='avatar-group d-flex align-items-center assigned-avatar'>"+
       
         "<span class='d-flex align-items-center me-2'><i class='ti ti-calendar-check ti-xs me-1'></i><span style='font-size:14px' class='due-date " + dateClass + "'>" +formattedStartDate+"  ~  "+ formattedDate + "</span></span>" +
         "<span class='d-flex align-items-center ms-2'><i class='ti ti-message-2 me-1'></i><span style='font-size:14px'>" + answerCount + "</span></span>" +
         "</div>" +
         "</div>" +
		 "<div class='avatar avatar-xs'>"+
         "<img src='https://storage.googleapis.com/java_bucket_hstest1/"+memberId+"/profile.png' alt='Avatar' class='rounded-circle  pull-up'>"+
         "</div>"+
         "</div>"
		);
	}
	
	
const kanbanAddItemForm = document.getElementById('kanban-add-item-form');
if (kanbanAddItemForm) {
  kanbanAddItemForm.addEventListener('submit', async function (event) {
    event.preventDefault(); // 기본 이벤트 방지(폼 제출 시 페이지 새로고침 방지)

    // 중복 제출 방지
    if (isSubmitting) return;
    isSubmitting = true;

    // 새로운 할 일 데이터 가져오기
    const newTitle = document.getElementById('new-title').value;
    const newContent = event.target.querySelector('#new-content').value;
    const newIssueOfficer = event.target.querySelector('#new-issueOfficer').value;
    const newIssueProgressStatus = event.target.querySelector('#new-issueProgressStatus').value;
    const newIssuePriorityName = event.target.querySelector('#new-issuePriorityName').value;
    const newIssueTagName = event.target.querySelector('#new-issueTagName').value;
    const newIssueDate = event.target.querySelector('#new-issueDate').value;
    const newIssueDeadLine = document.getElementById('new-due-date').value;
    const newSubIssueIdx = document.querySelector('#new-subIssueIdx1').value;
    const newProjectIdx = document.getElementById('projectIdx').value;
    const newProjectMemIdx = document.getElementById('projectMemIdx').value;

//    const titleByteLength = getByteLength(newTitle);
//    const contentByteLength = getByteLength(newContent);

//    if (titleByteLength > maxTitleLength) {
//      Swal.fire({
//        title: '제목 글자수 초과',
//        text: `제목의 최대 글자 수는 ${maxTitleLength}byte입니다. 현재: ${titleByteLength}byte`,
//        icon: 'warning',
//        customClass: {
//          popup: 'swal2-custom-zindex'
//        }
//      });
//      isSubmitting = false;
//      return;
//    }
//
//    if (contentByteLength > maxContentLength) {
//      Swal.fire({
//        title: '내용 글자수 초과',
//        text: `내용의 최대 글자 수는 ${maxContentLength}byte입니다. 현재: ${contentByteLength}byte`,
//        icon: 'warning',
//        customClass: {
//          popup: 'swal2-custom-zindex'
//        }
//      });
//      isSubmitting = false;
//      return;
//    }

    if (!newTitle) {
      Swal.fire({
        title: '제목 필수 입력',
        text: '제목은 필수 입력값입니다.',
        icon: 'warning',
        customClass: {
          popup: 'swal2-custom-zindex'
        }
      });
      isSubmitting = false;
      return;
    }

    // 데이터 객체 생성
    const issueData = {
      issueTitle: newTitle,
      issueContent: newContent,
      issueOfficer: newIssueOfficer,
      issueProgressStatus: newIssueProgressStatus,
      issuePriorityName: newIssuePriorityName,
      issueTagName: newIssueTagName,
      issueDate: newIssueDate,
      issueDeadline: newIssueDeadLine,
      subIssueIdx: newSubIssueIdx,
      projectId: newProjectIdx,
      projectMemIdx: newProjectMemIdx,
    };

    try {
      // SweetAlert2로 확인창 표시
      const result = await Swal.fire({
        title: '일정을 등록하시겠습니까?',
        text: '네 버튼을 클릭시 일정이등록됩니다!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네, 등록할게요!',
        cancelButtonText: '취소'
      });

      if (!result.isConfirmed) {
        isSubmitting = false;
        return;
      }

      // 서버에 데이터 전송
      const response = await FetchUtils.fetchForJSON(`${detailURI4}/${projectId}/kanban/rest/addIssue`, {
        method: 'POST',
        headers: {
          "accept": "application/json",
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(issueData)
      });

      if (response.success) {
        // 성공 메시지
        Swal.fire({
          title: '등록 성공!',
          text: '일정이 성공적으로 등록되었습니다.',
          icon: 'success',
          customClass: {
            popup: 'swal2-custom-zindex'
          },
          timer: 3000,
          showConfirmButton: false
        });

        kanbanOffcanvas.hide();
        document.getElementById('kanban-add-item-form').reset();
        $('#label-new').val(null).trigger('change');
        document.getElementById('new-due-date')._flatpickr.clear();
        loadData(); // 데이터 재로드
      } else {
        // 오류 메시지
        Swal.fire({
          title: '오류 발생',
          text: '일정 등록 중 오류가 발생했습니다.',
          icon: 'error',
          customClass: {
            popup: 'swal2-custom-zindex'
          }
        });
      }
    } catch (error) {
      console.error('Error:', error);
      Swal.fire({
        title: '오류 발생',
        text: '일정 등록 중 오류가 발생했습니다.',
        icon: 'error',
        customClass: {
          popup: 'swal2-custom-zindex'
        }
      });
    } finally {
      isSubmitting = false;
    }
  });
}


 const kanbanUpdateItemForm = document.getElementById('kanban-update-item-form');
if (kanbanUpdateItemForm) {
  kanbanUpdateItemForm.addEventListener('submit', async function (event) {
    event.preventDefault(); // 기본 이벤트 방지(폼 제출 시 페이지 새로고침 방지)

    if (isSubmitting) return; // 이미 제출 중인 경우 중단
    isSubmitting = true; // 제출 중 상태로 설정

    const issueTitle = document.getElementById('issueTitle').value;
    const issueContent = document.getElementById('issueContent').value;
    const issueOfficer = document.getElementById('issueOfficer').value;
    const issueProgressStatus = document.getElementById('issueProgressStatus').value;
    const issueTagName = document.getElementById('issueTagName').value;
    const issueDate = document.getElementById('issueDate').value;
    const issueDeadline = document.getElementById('issueDeadline').value;
    const subIssueIdx = document.getElementById('subIssueIdx').value;
    const projectIdx = document.getElementById('projectIdx').value;
    const projectMemIdx = document.getElementById('projectMemIdx').value;
    const issueIdx = document.getElementById('issueIdx').value;
    const updatedCategory = $('#label-update').val();
    const updatedCategoryColor = $('#label-update').find(':selected').data('color').replace('bg-label-', '');

//    const titleByteLength = getByteLength(issueTitle);
//    const contentByteLength = getByteLength(issueContent);
//
//    if (titleByteLength > maxTitleLength) {
//      Swal.fire({
//        title: '제목 글자수 초과',
//        text: `제목의 최대 글자 수는 ${maxTitleLength}byte입니다. 현재: ${titleByteLength}byte`,
//        icon: 'warning',
//        customClass: {
//          popup: 'swal2-custom-zindex'
//        }
//      });
//      isSubmitting = false;
//      return;
//    }
//
//    if (contentByteLength > maxContentLength) {
//      Swal.fire({
//        title: '내용 글자수 초과',
//        text: `내용의 최대 글자 수는 ${maxContentLength}byte입니다. 현재: ${contentByteLength}byte`,
//        icon: 'warning',
//        customClass: {
//          popup: 'swal2-custom-zindex'
//        }
//      });
//      isSubmitting = false;
//      return;
//    }

    if (!issueTitle) {
      Swal.fire({
        title: '제목은 필수 입력값입니다.',
        text: '제목을 입력해주세요.',
        icon: 'warning',
        customClass: {
          popup: 'swal2-custom-zindex'
        }
      });
      isSubmitting = false;
      return;
    }

    const editingId = kanbanSidebar.getAttribute('data-editing-id');
    const todoElement = document.querySelector(`[data-eid="${editingId}"]`);
    const todoId = todoElement.getAttribute('data-todoid');
    const boardId = todoElement.closest('.kanban-board').getAttribute('data-id');
    const todoStts = getStatusFromBoardId(boardId);

    const issueData = {
      issueIdx,
      projectId: projectIdx,
      subIssueIdx,
      issueDate,
      issueProgressStatus,
      issuePriorityName: updatedCategory,
      issueTitle,
      issueContent,
      issueOfficer,
      issueDeadline,
	  issueTagName
    };

    try {
      // SweetAlert2로 확인 창 표시
      const result = await Swal.fire({
        title: '일정을 수정하시겠습니까?',
        text: '네 버튼을 클릭 시 일정이 수정됩니다!',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '네, 수정할게요!',
        cancelButtonText: '취소'
      });

      if (!result.isConfirmed) {
        isSubmitting = false;
        return;
      }

      // 수정 API 호출
      const response = await FetchUtils.fetchForJSON(`${detailURI4}/${projectId}/kanban/rest/updateAllIssue`, {
        method: 'PUT',
        headers: {
          "accept": "application/json",
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(issueData)
      });

      if (!response.success) {
        Swal.fire({
          title: '수정 실패',
          text: '일정 수정 중 문제가 발생했습니다.',
          icon: 'error',
          customClass: {
            popup: 'swal2-custom-zindex'
          }
        });
        return;
      }

      // SweetAlert2로 성공 메시지 표시
      Swal.fire({
        title: '일정 수정 성공!',
        text: '일정이 성공적으로 수정되었습니다.',
        icon: 'success',
        customClass: {
          popup: 'swal2-custom-zindex'
        },
        timer: 3000,
        showConfirmButton: false
      });

      // Kanban 보드에서 해당 아이템 업데이트
      todoElement.querySelector('.kanban-text').textContent = issueTitle;
      todoElement.setAttribute('data-content', issueContent);
      todoElement.setAttribute('data-badgetext', updatedCategory);
      todoElement.setAttribute('data-badge', updatedCategoryColor);
      todoElement.setAttribute('data-duedate', issueDeadline);
      todoElement.querySelector('.badge').className = `badge rounded-pill bg-label-${updatedCategoryColor}`;
      todoElement.querySelector('.badge').textContent = updatedCategory;
      todoElement.querySelector('.kanban-content').textContent = issueContent;
      todoElement.querySelector('.due-date').textContent = issueDeadline;

      kanbanOffcanvas.hide();
      loadData();
    } catch (error) {
      Swal.fire({
        title: '오류 발생',
        text: '일정 수정 중 오류가 발생했습니다.',
        icon: 'error',
        customClass: {
          popup: 'swal2-custom-zindex'
        }
      });
      console.error('Error:', error);
    } finally {
      isSubmitting = false;
    }
  });
}

 // 할 일 삭제 버튼 클릭 시 호출되는 함수
 function addDeleteEventListeners() {
    const deleteButton = document.querySelector('.btn.btn-label-danger');
    if (deleteButton) {
      deleteButton.addEventListener('click', async function (event) {
        event.preventDefault();
        const editingId = kanbanSidebar.getAttribute('data-editing-id');
        if (editingId) {
          const todoElement = document.querySelector(`[data-eid="${editingId}"]`);
          if (todoElement) {
            const issueIdx = todoElement.getAttribute('data-issueid');
            Swal.fire({
              title: '삭제하시겠습니까?',
              icon: 'warning',
              confirmButtonText: '예',
              cancelButtonText: '아니오',
              customClass: {
                confirmButton: 'btn btn-primary me-3 waves-effect waves-light',
                cancelButton: 'btn btn-label-secondary waves-effect waves-light'
              },
              buttonsStyling: false
            }).then(async function (result) {
              if (result.value) {
                try {
                  const response = await FetchUtils.fetchForJSON(`${detailURI4}/${projectId}/kanban/rest/issues/${issueIdx}`, {
                    method: 'DELETE',
                    headers: {
						  'accept' :'application/json'
                    }
                  });


                  if (response.success) {
                    kanban.removeElement(editingId);
                    kanbanOffcanvas.hide(); // 팝업 닫기
                    Swal.fire({
                      title: '삭제 완료!',
                      icon: 'success',
                      timer: 1000,
                      showConfirmButton: false,
                      customClass: {
                        confirmButton: 'btn btn-success waves-effect waves-light'
                      },
                      buttonsStyling: false
                    }).then(function (result) {
                      if (result.dismiss === Swal.DismissReason.timer) {
                        console.log('The alert was closed by the timer');
                      }
                    });
                  } else {
                    console.error('Error deleting todo:', response.statusText);
                    alert('삭제 중 오류가 발생했습니다.');
                  }
                } catch (error) {
                  console.error('Error:', error);
                  alert('삭제 중 오류가 발생했습니다.');
                }
              }
            });
          }
        }
      });
    }
  }
 function addDeleteEventListeners1() {
  const deletePopupButton = document.querySelector('#hiddenDBtn');
  console.log(deletePopupButton);
  if (deletePopupButton) {
    deletePopupButton.addEventListener('click', async function (event) {
		
      event.preventDefault();
      const editingId = kanbanSidebar.getAttribute('data-editing-id');
      if (editingId) {
        const todoElement = document.querySelector(`[data-eid="${editingId}"]`);
        if (todoElement) {
         const issueIdx = todoElement.getAttribute('data-issueid');
          Swal.fire({
            title: '삭제하시겠습니까?',
            icon: 'warning',
            confirmButtonText: '예',
            cancelButtonText: '아니오',
            customClass: {
              confirmButton: 'btn btn-primary me-3 waves-effect waves-light',
              cancelButton: 'btn btn-label-secondary waves-effect waves-light'
            },
            buttonsStyling: false
          }).then(async function (result) {
            if (result.value) {
              try {
                const response = await await FetchUtils.fetchForJSON(`${detailURI4}/${projectId}/kanban/rest/issues/${issueIdx}`, {
                  method: 'DELETE',
                  headers: {
                    'accept' :'application/json'
                  }
                });

                if (response.success) {
                  kanban.removeElement(editingId);
                  kanbanOffcanvas.hide(); // 팝업 닫기
                  Swal.fire({
                    title: '삭제 완료!',
                    icon: 'success',
                    timer: 1000,
                    showConfirmButton: false,
                    customClass: {
                      confirmButton: 'btn btn-success waves-effect waves-light'
                    },
                    buttonsStyling: false
                  }).then(function (result) {
                    if (result.dismiss === Swal.DismissReason.timer) {
                      console.log('The alert was closed by the timer');
                    }
                  });
                } else {
                  console.error('Error deleting todo:');
                  alert('삭제 중 오류가 발생했습니다.');
                }
              } catch (error) {
                console.error('Error:', error);
                alert('삭제 중 오류가 발생했습니다.');
              }
            }
          });
        }
      }
    });
  }
};


  function getStatusFromBoardId(boardId) {
    switch (boardId) {
      case 'board-in-ready':
        return 'Not Started';
      case 'board-in-progress':
        return 'In Progress';
      case 'board-done':
        return 'Completed';
      default:
        return 'Not Started'; // 기본값 설정
    }
  }

//  function getByteLength(str) {
//    let byteLength = 0; // 바이트 길이 초기화
//    for (let i = 0; i < str.length; i++) {
//      const charCode = str.charCodeAt(i); // 문자 코드 가져오기
//      if (charCode <= 0x007F) {
//        byteLength += 1; // 1바이트 문자일 경우 1 증가
//      } else if (charCode <= 0x07FF) {
//        byteLength += 2; // 2바이트 문자일 경우 2 증가
//      } else if (charCode <= 0xFFFF) {
//        byteLength += 3; // 3바이트 문자일 경우 3 증가
//      } else {
//        byteLength += 4; // 4바이트 문자일 경우 4 증가
//      }
//    }
//    return byteLength; // 바이트 길이 반환
//  }

  // DatePicker와 Select2 초기화 호출
  initDatePicker(datePicker); // 날짜 선택기 초기화
  initDatePicker(newDatePicker); // 새 날짜 선택기 초기화
  initSelect2(select2Update); // 업데이트 라벨 선택기 초기화
  initSelect2(select2New); // 새 라벨 선택기 초기화


})();
