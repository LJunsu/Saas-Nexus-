let kanban; // 전역 변수로 선언

// 비동기 함수 시작
(async function () {

  // Kanban 사이드바 및 기타 DOM 요소 초기화
  const kanbanSidebar = document.querySelector('.kanban-update-item-sidebar'); // Kanban 사이드바 요소 선택
  const kanbanWrapper = document.querySelector('.kanban-wrapper'); // Kanban 래퍼 요소 선택
  const datePicker = document.querySelector('#due-date'); // 날짜 선택기 요소 선택
  const newDatePicker = document.querySelector('#new-due-date'); // 새 날짜 선택기 요소 선택
  const select2Update = $('#label-update'); // 업데이트 라벨 선택기 요소 선택
  const select2New = $('#label-new'); // 새 라벨 선택기 요소 선택
  // 부트스트랩 오프캔버스 초기화 전에 요소가 존재하는지 확인
  let kanbanOffcanvas;
  if (kanbanSidebar) {
    kanbanOffcanvas = new bootstrap.Offcanvas(kanbanSidebar); // 부트스트랩 오프캔버스 초기화
  }

  // Kanban 보드 설정
  const boards = [
    { id: 'board-in-progress', title: 'To Do', item: [] }, // To Do 보드 설정
    { id: 'board-in-review', title: 'In Progress', item: [] }, // In Progress 보드 설정
    { id: 'board-done', title: 'Done', item: [] } // Done 보드 설정
  ];

  let isSubmitting = false; // 제출 중인지 여부를 나타내는 변수
  const maxTitleLength = 100; // 제목 최대 길이 설정
  const maxContentLength = 500; // 내용 최대 길이 설정



  // DB데이터 ->  Kanban보드용 데이터로 변환
  const transformTodoData = (data) => data.map((todo, index) => {
    const dueDate = new Date(todo.todoDdln); // 서버에서 받은 마감 날짜를 JavaScript Date 객체로 변환
    const formattedDueDate = isNaN(dueDate.getTime()) ? '' : formatDate(dueDate); // 날짜가 유효하지 않으면 빈 문자열, 유효하면 포맷된 날짜 반환
    return {
      id: `${getBoardIdByStatus(todo.todoStts)}-${index + 1}`, // 상태에 따른 보드 ID와 인덱스를 조합하여 고유 ID 생성
      title: todo.todoTtl || 'No Title', // 제목 설정, 제목이 없으면 'No Title' 사용
      content: todo.todoCn || '', // 내용 설정, 내용이 없으면 빈 문자열 사용
      badgeText: todo.todoCtgr, // 배지 텍스트 설정
      badge: getBadgeColor(todo.todoCtgr), // 카테고리에 따른 배지 색상 설정
      dueDate: formattedDueDate, // 포맷된 마감 날짜 설정
      todoStts: todo.todoStts, // 할 일의 상태 설정
      todoId: todo.todoId // 할 일의 ID 추가
    };
  });

  // 날짜 형식 변환 함수
  const formatDate = (date) => {
    const year = date.getFullYear(); // 연도 가져오기
    const month = ('0' + (date.getMonth() + 1)).slice(-2); // 월 가져오기 및 2자리 형식으로 변환
    const day = ('0' + date.getDate()).slice(-2); // 일 가져오기 및 2자리 형식으로 변환
    return `${year}-${month}-${day}`; // 'YYYY-MM-DD' 형식으로 반환
  };

  // 카테고리에 따른 배지 색상 설정
  const getBadgeColor = (category) => {
    switch (category) {
      case 'Work': return 'success'; // Work : 초록색 배지
      case 'Personal': return 'warning'; // Personal : 노란색 배지
      case 'Family': return 'info'; // Family : 파란색 배지
      case 'Study': return 'danger'; // Study : 빨간색 배지
      case 'Hobby': return 'primary'; // Hobby : 파란색 배지
      case 'Others': return 'secondary'; // Others : 회색 배지
      default: return 'secondary'; // 기본 : 회색 배지
    }
  };

  // 상태에 따른 보드 ID 반환
  const getBoardIdByStatus = (status) => {
    switch (status) {
      case '0': return 'board-in-progress'; // 상태가 0일 경우 'board-in-progress'
      case '1': return 'board-in-review'; // 상태가 1일 경우 'board-in-review'
      case '2': return 'board-done'; // 상태가 2일 경우 'board-done'
      default: return 'board-in-progress'; // 기본적으로 'board-in-progress'
    }
  };

  // 데이터를 불러오는 함수
  const loadData = async () => {
    try {
      const response = await fetch('/schedule/todos'); // 할 일 데이터를 가져오기 위한 fetch 요청
      const data = await response.json(); // 응답을 JSON 형식으로 파싱
      console.log('Loaded Data:', data); // 데이터를 콘솔에 출력
      const transformedData = transformTodoData(data); // 데이터를 변환
      boards.forEach(board => {
        board.item = transformedData.filter(item => getBoardIdByStatus(item.todoStts) === board.id); // 각 보드에 변환된 데이터를 필터링(보드 ID)하여 할당
      });
      if (kanbanWrapper) {
        kanbanWrapper.innerHTML = ''; // Kanban 보드의 내용을 초기화
        initializeKanban(); // Kanban 보드를 초기화
      }
    } catch (error) {
      console.error('Error loading todo data:', error); // 데이터 로드 중 오류 발생 시 콘솔에 출력
    }
  };

  // Kanban 초기화 함수
  const initializeKanban = () => {

    // 페이지가 처음 로드되었을 때에만 실행(중복 초기화 방지)
    if (!document.querySelector('.kanban-container')) {

      // Kanban 보드의 아이템 추가, 클릭, 드래그 기능을 설정
      kanban = new jKanban({

        element: '.kanban-wrapper', // Kanban 요소 지정
        gutter: '15px', // 보드 간격 설정
        widthBoard: '250px', // 보드 너비 설정
        dragItems: true, // 아이템 드래그 가능 여부 설정
        boards: boards, // 보드 데이터 설정
        dragBoards: false, // 보드 드래그 가능 여부 설정
        addItemButton: false, // 아이템 추가 버튼 사용 여부 설정
        buttonContent: '+ 새 할일 추가', // 아이템 추가 버튼 내용 설정
        itemAddOptions: { enabled: false }, // 아이템 추가 옵션 설정
        
        // 아이템 클릭 시 호출되는 함수
        click: function (el) {
          const element = el;
          const title = element.getAttribute('data-eid') ? element.querySelector('.kanban-text').textContent : element.textContent; // 제목 설정
          const content = element.getAttribute('data-content'); // 내용 설정
          const date = element.getAttribute('data-duedate'); // 마감 날짜 설정
          const dateObj = new Date(); // 현재 날짜 객체 생성
          const year = dateObj.getFullYear(); // 현재 연도 가져오기
          const dateToUse = date ? date : `${dateObj.getDate()} ${dateObj.toLocaleString('en', { month: 'long' })}, ${year}`; // 마감 날짜 설정
          const label = element.getAttribute('data-badgetext'); // 라벨 텍스트 설정
          const color = element.getAttribute('data-badge'); // 배지 색상 설정
          kanbanSidebar.setAttribute('data-editing-id', element.getAttribute('data-eid')); // Kanban 사이드바에 편집 중인 ID 설정
          kanbanOffcanvas.show(); // 오프캔버스 표시
          document.getElementById('tab-update').classList.add('show', 'active'); // 업데이트 탭 활성화
          document.getElementById('tab-new').classList.remove('show', 'active'); // 새 탭 비활성화
          kanbanSidebar.querySelector('#title').value = title; // 제목 입력 필드에 값 설정
          kanbanSidebar.querySelector('#due-date')._flatpickr.setDate(dateToUse, true); // 날짜 선택기에 값 설정
          kanbanSidebar.querySelector('#new-content').value = content; // 내용 입력 필드에 값 설정
          $('#label-update').val(label).trigger('change'); // 라벨 선택기에 값 설정
          $('#label-update').next().find('.badge').attr('class', 'badge bg-label-' + color + ' rounded-pill').text(label); // 배지 클래스 및 텍스트 설정
        },

        // 새 할일 추가 버튼 클릭 시 호출되는 함수
        buttonClick: function (el, boardId) {
          kanbanOffcanvas.show(); // Offcanvas를 표시
          document.getElementById('tab-update').classList.remove('show', 'active'); // 할일 탭 비활성화
          document.getElementById('tab-new').classList.add('show', 'active'); // 새할일 탭을 활성화
          kanbanSidebar.querySelector('#new-title').value = ''; // 제목 입력 필드 초기화
          kanbanSidebar.querySelector('#new-due-date')._flatpickr.setDate(new Date(), true); // 마감일 입력 필드를 오늘 날짜로 설정
          kanbanSidebar.querySelector('#new-content').value = ''; // 내용 입력 필드를 초기화
          $('#label-new').val(null).trigger('change'); // 라벨 필드를 초기화
        },

        // 아이템이 드롭될 때 호출되는 함수
        dropEl: async function (el, target, source, sibling) {
          const boardId = target.parentElement.getAttribute('data-eid'); // 타겟 보드 ID 가져오기
          const todoId = el.getAttribute('data-todoid'); // 할 일 ID 가져오기
          const dueDateElement = el.querySelector('.due-date'); // 마감일 요소를 가져옴
          const todoTitle = el.querySelector('.kanban-text').textContent; // 제목 가져오기
          const todoContent = el.getAttribute('data-content'); // 내용 가져오기
          const todoCategory = el.getAttribute('data-badgetext'); // 카테고리 가져오기
          const todoDueDate = el.getAttribute('data-duedate'); // 마감일 가져오기
          const todoStts = getStatusFromBoardId(boardId); // 보드 ID를 상태 값으로 변환하는 함수 사용

          // 보드가 Done일 경우 마감 날짜의 text-danger 클래스 제거
          if (boardId === 'board-done') {
            dueDateElement.classList.remove('text-danger');
          } else {
            const dueDate = new Date(dueDateElement.textContent); // 마감 날짜 가져오기
            if (dueDate < new Date()) {
              dueDateElement.classList.add('text-danger'); // 마감 날짜가 현재 날짜보다 이전일 경우 text-danger 클래스 추가
            }
          }

          const todoData = {
            todoTtl: todoTitle || 'No Title', // 제목이 없으면 기본값 설정
            todoDdln: todoDueDate || formatDate(new Date()), // 마감일이 없으면 기본값 설정
            todoCtgr: todoCategory || 'Others', // 카테고리가 없으면 기본값 설정
            todoCn: todoContent || '', // 내용이 없으면 기본값 설정
            todoStts: todoStts // 상태 값 설정
          };

          // 서버에 상태 변경 요청
          try {
            const response = await fetch(`/schedule/todos/${todoId}`, {
              method: 'PUT',
              headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken // CSRF 토큰 설정
              },
              body: JSON.stringify(todoData) // 요청 본문 설정
            });

            if (response.ok) {
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
      const targetBoardId = 'board-in-progress'; // 타겟 보드 ID 설정
      const targetBoard = document.querySelector(`.kanban-board[data-id="${targetBoardId}"] .kanban-drag`); // 타겟 보드 요소 선택
      
      if (!targetBoard) {
        console.error(`targetBoard with ID ${targetBoardId} not found`); // 타겟 보드가 없는 경우 에러 출력
      } else {
        targetBoard.insertAdjacentHTML('beforeend', addItemButtonHTML); // 타겟 보드에 아이템 추가 버튼 삽입
        const addButton = targetBoard.querySelector('.kanban-title-button'); // 아이템 추가 버튼 선택
        
        // 아이템 추가 버튼이 있는 경우 클릭 이벤트 리스너 추가
        if (addButton) {
          addButton.addEventListener('click', function () {
            const offcanvas = document.querySelector('.kanban-update-item-sidebar'); // 오프캔버스 요소 선택
            if (offcanvas) {
              document.getElementById('tab-update').classList.remove('show', 'active'); // 할일 탭 비활성화
              document.getElementById('tab-new').classList.add('show', 'active'); // 새할일 탭 활성화
              kanbanSidebar.querySelector('#new-title').value = ''; // 제목 입력 필드 초기화
              kanbanSidebar.querySelector('#new-due-date')._flatpickr.setDate(new Date(), true); // 날짜 선택기에 현재 날짜 설정
              kanbanSidebar.querySelector('#new-content').value = ''; // 내용 입력 필드 초기화
              $('#label-new').val(null).trigger('change'); // 라벨 선택기 초기화
              kanbanOffcanvas.show(); // 오프캔버스 표시
            }
          });
        }
      }

      //Kanban 아이템의 텍스트, 내용, 헤더, 푸터를 초기화 및 추가
      const kanbanItems = document.querySelectorAll('.kanban-item'); // Kanban 아이템 선택
      kanbanItems.forEach(el => {
        const element = "<span class='kanban-text fw-bold'>" + el.textContent + '</span>'; // 제목 요소 생성
        const content = el.getAttribute('data-content') || ''; // 내용 가져오기
        const contentElement = "<div class='kanban-content'>" + content + "</div>"; // 내용 요소 생성
        el.textContent = ''; // 아이템 내용 초기화
        if (el.getAttribute('data-badge') !== undefined && el.getAttribute('data-badgetext') !== undefined) {
          el.insertAdjacentHTML('afterbegin', renderHeader(el.getAttribute('data-badge'), el.getAttribute('data-badgetext'), el.getAttribute('data-todoid')) + element + contentElement); // 헤더, 텍스트, 내용, todoID 요소 삽입
        }
        const dueDate = el.getAttribute('data-duedate'); // 마감 날짜 가져오기
        const boardId = el.closest('.kanban-board').getAttribute('data-id'); // 보드 ID 가져오기
        let footer = renderFooter(dueDate, boardId); // 푸터 요소 생성
        if (footer) {
          el.insertAdjacentHTML('beforeend', footer); // 아이템의 맨 끝에 푸터 요소 삽입
        }
      });

      addDeleteEventListeners(); // 삭제 이벤트 리스너 추가
    }
  };

  // 페이지 로드 시 서버 데이터를 불러와 초기화하는 이벤트 리스너
  document.addEventListener('DOMContentLoaded', loadData); // DOMContentLoaded 이벤트 발생 시 loadData 함수 호출

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
    if (select2.length) {
      function renderLabels(option) {
        if (!option.id) {
          return option.text; // 옵션 ID가 없는 경우 텍스트 반환
        }
        var $badge = "<div class='badge " + $(option.element).data('color') + " rounded-pill'> " + option.text + '</div>'; // 배지 HTML 생성
        return $badge; // 배지 HTML 반환
      }

      select2.each(function () {
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

  // DatePicker와 Select2 초기화 호출
  initDatePicker(datePicker); // 날짜 선택기 초기화
  initDatePicker(newDatePicker); // 새 날짜 선택기 초기화
  initSelect2(select2Update); // 업데이트 라벨 선택기 초기화
  initSelect2(select2New); // 새 라벨 선택기 초기화

  // 보드 드롭다운을 렌더링하는 함수(사용안함)
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

  // 아이템 삭제 메뉴를 렌더링하는 함수
  function renderDropdown() {
    return ( // 삭제 아이콘 HTML을 반환 (구 드롭다운css 때문에 class유지함)
      "<div class='dropdown kanban-tasks-item-dropdown'>" +
      "<i class='ti ti-trash delete-task' aria-hidden='true'></i>" +
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

  // 아이템 풋터를 렌더링하는 함수
  function renderFooter(dueDate, boardId) {
    const currentDate = new Date(); // 현재 날짜 객체 생성
    const date = new Date(dueDate); // 마감 날짜 객체 생성
    const formattedDate = date.toISOString().split('T')[0]; // 날짜를 'YYYY-MM-DD' 형식으로 포맷 (2024-06-13T15:00:00.000Z -> 2024-06-13)
    const isOverdue = date < currentDate; // 마감일이 지난 경우를 확인
    const dateClass = (isOverdue && boardId !== 'board-done') ? 'text-danger' : ''; // 마감일이 지났고 완료되지 않은 경우 text-danger 클래스 설정
    // 풋터 HTML을 반환
    return (
      "<div class='d-flex justify-content-between align-items-center flex-wrap mt-2 pt-1'>" +
      "<div class='d-flex'>" +
      "<span class='d-flex align-items-center me-2'><i class='ti ti-calendar-check ti-xs me-1'></i><span class='due-date " + dateClass + "'>" + formattedDate + "</span></span>" +
      "</div>" +
      "</div>"
    );
  }

  // 새로운 할 일을 Kanban 보드에 등록
  const kanbanAddItemForm = document.getElementById('kanban-add-item-form');
  if (kanbanAddItemForm) {
    kanbanAddItemForm.addEventListener('submit', async function (event) {
      event.preventDefault(); // 기본 이벤트 방지(폼 제출 시 페이지 새로고침 방지)

      // 중복 제출 방지
      if (isSubmitting) return; // 이미 제출 중인 경우 중단
      isSubmitting = true; // 제출 중 상태로 설정

      // 새로운 할 일 데이터 가져오기
      const newTitle = document.getElementById('new-title').value; // 새 제목 가져오기
      const newDueDate = document.getElementById('new-due-date').value; // 새 마감 날짜 가져오기
      const newCategory = $('#label-new').val(); // 새 카테고리 가져오기
      const newContent = event.target.querySelector('#new-content').value; // 새 내용 가져오기

      const titleByteLength = getByteLength(newTitle); // 제목 바이트 길이 계산
      const contentByteLength = getByteLength(newContent); // 내용 바이트 길이 계산

      if (titleByteLength > maxTitleLength) {
        alert(`제목의 최대글자수 : ${maxTitleLength}byte\n현재 글자수 : ${titleByteLength}byte`); // 제목 길이 초과 알림
        isSubmitting = false; // 제출 중 상태 해제
        return;
      }
      if (contentByteLength > maxContentLength) {
        alert(`내용의 최대글자수 : ${maxContentLength}byte\n현재 글자수 : ${contentByteLength}byte`); // 내용 길이 초과 알림
        isSubmitting = false; // 제출 중 상태 해제
        return;
      }
      if (!newTitle) {
        alert('제목은 필수 입력값입니다.'); // 제목 필수 입력값 알림
        isSubmitting = false; // 제출 중 상태 해제
        return;
      }

      // 데이터 객체 생성
      const todoData = {
        todoTtl: newTitle, // 제목 설정
        todoDdln: newDueDate, // 마감 날짜 설정
        todoCtgr: newCategory, // 카테고리 설정
        todoCn: newContent // 내용 설정
      };

      // 서버에 데이터 전송
      try {
        const response = await fetch('/schedule/todos', {
          method: 'POST', // POST 요청 설정
          headers: {
            'Content-Type': 'application/json', // Content-Type 헤더 설정
            [csrfHeader]: csrfToken // CSRF 헤더 설정
          },
          body: JSON.stringify(todoData) // 요청 본문 설정
        });

        const responseData = await response.json(); // 응답 데이터 파싱
        console.log('Server Response:', responseData); // 응답 데이터 콘솔 출력

        // 응답 성공,오류 처리
        if (response.ok) {
          alert('일정이 성공적으로 등록되었습니다.'); // 성공 알림
          kanbanOffcanvas.hide(); // 오프캔버스 숨기기
          document.getElementById('kanban-add-item-form').reset(); // 폼 초기화
          $('#label-new').val(null).trigger('change'); // 라벨 선택기 초기화
          document.getElementById('new-due-date')._flatpickr.clear(); // 날짜 선택기 초기화
          loadData(); // 데이터 재로드
        } else {
          alert(`일정 등록 중 오류가 발생했습니다. 오류 메시지: ${responseData.message}`); // 오류 알림
        }
      } catch (error) {
        console.error('Error:', error); // 오류 콘솔 출력
        alert('일정 등록 중 오류가 발생했습니다.'); // 오류 알림
      } finally {
        isSubmitting = false; // 제출 중 상태 해제
      }
    });
  }

  // 기존에 등록된 할 일의 내용을 수정
  const kanbanUpdateItemForm = document.getElementById('kanban-update-item-form');
  if (kanbanUpdateItemForm) {
    kanbanUpdateItemForm.addEventListener('submit', async function (event) {
      event.preventDefault(); // 기본 이벤트 방지(폼 제출 시 페이지 새로고침 방지)

      if (isSubmitting) return; // 이미 제출 중인 경우 중단
      isSubmitting = true; // 제출 중 상태로 설정

      const updatedTitle = document.getElementById('title').value; // 수정할 제목 가져오기
      const updatedDueDate = document.getElementById('due-date').value; // 수정할 마감 날짜 가져오기
      const updatedCategory = $('#label-update').val(); // 수정할 카테고리 가져오기
      const updatedCategoryColor = $('#label-update').find(':selected').data('color').replace('bg-label-', ''); // 수정할 카테고리 색상 가져오기
      const updatedContent = document.getElementById('new-content').value; // 수정할 내용 가져오기

      const titleByteLength = getByteLength(updatedTitle); // 제목 바이트 길이 계산
      const contentByteLength = getByteLength(updatedContent); // 내용 바이트 길이 계산

      if (titleByteLength > maxTitleLength) {
        alert(`제목의 최대글자수 : ${maxTitleLength}byte\n현재 글자수 : ${titleByteLength}byte`); // 제목 길이 초과 알림
        isSubmitting = false; // 제출 중 상태 해제
        return;
      }
      if (contentByteLength > maxContentLength) {
        alert(`내용의 최대글자수 : ${maxContentLength}byte\n현재 글자수 : ${contentByteLength}byte`); // 내용 길이 초과 알림
        isSubmitting = false; // 제출 중 상태 해제
        return;
      }
      if (!updatedTitle) {
        alert('제목은 필수 입력값입니다.'); // 제목 필수 입력값 알림
        isSubmitting = false; // 제출 중 상태 해제
        return;
      }

      const editingId = kanbanSidebar.getAttribute('data-editing-id'); // 편집 중인 ID 가져오기
      const todoElement = document.querySelector(`[data-eid="${editingId}"]`);
      const todoId = todoElement.getAttribute('data-todoid'); // 실제 todoId 가져오기

      // Kanban 보드에서 상태 값 가져오기
      const boardId = todoElement.closest('.kanban-board').getAttribute('data-id');
      const todoStts = getStatusFromBoardId(boardId); // 보드 ID를 상태 값으로 변환하는 함수

      const todoData = {
        todoTtl: updatedTitle,
        todoDdln: updatedDueDate,
        todoCtgr: updatedCategory,
        todoCn: updatedContent,
        todoStts: todoStts // 상태 값을 추가
      };

      try {
        const response = await fetch(`/schedule/todos/${todoId}`, { // todoId 사용
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
          },
          body: JSON.stringify(todoData)
        });

        if (!response.ok) {
          alert('일정 수정 중 오류가 발생했습니다.');
          return;
        }

        // 응답이 JSON 형식인지 확인하고 처리
        const responseData = await response.json().catch(() => null);
        if (responseData) {
          console.log('Server Response:', responseData);
          alert('일정이 성공적으로 수정되었습니다.');

          // Kanban 보드에서 해당 아이템 업데이트
          todoElement.querySelector('.kanban-text').textContent = updatedTitle;
          todoElement.setAttribute('data-content', updatedContent);
          todoElement.setAttribute('data-badgetext', updatedCategory);
          todoElement.setAttribute('data-badge', updatedCategoryColor);
          todoElement.setAttribute('data-duedate', updatedDueDate);
          todoElement.querySelector('.badge').className = `badge rounded-pill bg-label-${updatedCategoryColor}`;
          todoElement.querySelector('.badge').textContent = updatedCategory;
          todoElement.querySelector('.kanban-content').textContent = updatedContent;
          todoElement.querySelector('.due-date').textContent = updatedDueDate;
        } else {
          console.warn('서버 응답이 예상된 JSON 형식이 아닙니다.');
        }

        kanbanOffcanvas.hide();
        loadData();
      } catch (error) {
        console.error('Error:', error);
        alert('일정 수정 중 오류가 발생했습니다.');
      } finally {
        isSubmitting = false;
      }
    });
  }


  // 할 일 삭제 버튼 클릭 시 호출되는 함수
  function addDeleteEventListeners() {
    const deleteButton = document.querySelector('.btn.btn-label-danger.waves-effect');
    if (deleteButton) {
      deleteButton.addEventListener('click', async function (event) {
        event.preventDefault();
        const editingId = kanbanSidebar.getAttribute('data-editing-id');
        if (editingId) {
          const todoElement = document.querySelector(`[data-eid="${editingId}"]`);
          if (todoElement) {
            const todoId = todoElement.getAttribute('data-todoid');
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
                  const response = await fetch(`/schedule/todos/${todoId}`, {
                    method: 'DELETE',
                    headers: {
                      'Content-Type': 'application/json',
                      [csrfHeader]: csrfToken
                    }
                  });

                  if (response.ok) {
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

  // 팝업의 삭제 버튼 이벤트 리스너 추가
  const deletePopupButton = document.querySelector('.btn.btn-label-danger.waves-effect');
  if (deletePopupButton) {
    deletePopupButton.addEventListener('click', async function (event) {
      event.preventDefault();
      const editingId = kanbanSidebar.getAttribute('data-editing-id');
      if (editingId) {
        const todoElement = document.querySelector(`[data-eid="${editingId}"]`);
        if (todoElement) {
          const todoId = todoElement.getAttribute('data-todoid');
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
                const response = await fetch(`/schedule/todos/${todoId}`, {
                  method: 'DELETE',
                  headers: {
                    'Content-Type': 'application/json',
                    [csrfHeader]: csrfToken
                  }
                });

                if (response.ok) {
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

  // 보드 ID를 상태 값으로 변환하는 함수
  function getStatusFromBoardId(boardId) {
    switch (boardId) {
      case 'board-in-progress':
        return '0';
      case 'board-in-review':
        return '1';
      case 'board-done':
        return '2';
      default:
        return '0'; // 기본값 설정
    }
  }

  // 문자열 바이트 길이 계산 함수
  function getByteLength(str) {
    let byteLength = 0; // 바이트 길이 초기화
    for (let i = 0; i < str.length; i++) {
      const charCode = str.charCodeAt(i); // 문자 코드 가져오기
      if (charCode <= 0x007F) {
        byteLength += 1; // 1바이트 문자일 경우 1 증가
      } else if (charCode <= 0x07FF) {
        byteLength += 2; // 2바이트 문자일 경우 2 증가
      } else if (charCode <= 0xFFFF) {
        byteLength += 3; // 3바이트 문자일 경우 3 증가
      } else {
        byteLength += 4; // 4바이트 문자일 경우 4 증가
      }
    }
    return byteLength; // 바이트 길이 반환
  }

  // DatePicker와 Select2 초기화 호출
  initDatePicker(datePicker); // 날짜 선택기 초기화
  initDatePicker(newDatePicker); // 새 날짜 선택기 초기화
  initSelect2(select2Update); // 업데이트 라벨 선택기 초기화
  initSelect2(select2New); // 새 라벨 선택기 초기화

})();
