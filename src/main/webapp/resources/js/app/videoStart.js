const cPath = document.body.dataset.contextPath;
function getOneMonthLater() {
    const now = new Date();
    const oneMonthLater = new Date(now);
    oneMonthLater.setMonth(now.getMonth() + 1);
    
    // 만약 새로운 월의 날짜가 현재 날짜보다 이전이면 날짜를 조정
    if (oneMonthLater.getDate() < now.getDate()) {
        oneMonthLater.setDate(0); // 마지막 날로 설정
    }
    
    return oneMonthLater;
}

const futureDate = getOneMonthLater();
console.log("한달 뒤 :",futureDate); // 한 달 뒤의 날짜

const nameWithTitle = document.querySelector('#nameWithTitle');//방 제목
const RoomPassword = document.querySelector('#RoomPassword');// 방 비밀번호
async function createVideoRoom(){
	try{
		const videoConferenceTitle = nameWithTitle.value;  // 실제 값으로 대체
		console.log('화상회의 입력 제목:',videoConferenceTitle);
		const videoPassword = RoomPassword.value; 
		console.log('화상회의 입력 비밀번호:',videoPassword);
		const response = await axios.post(`${cPath}/video/rest/start`,{
			videoConferenceTitle: videoConferenceTitle,
			videoPassword: videoPassword
		});
		console.log("방 생성:", response.data); // 응답 데이터 출력
		$('#modalCenter').modal('hide');
		fetchVideoList();
	}catch(errors){
		console.log("방 생성 오류:", errors);
	}
}

function fetchVideoList() {
	//테이블 값을 초기화
	$('#DataTables_Table_0').DataTable().destroy();
	$('#DataTables_Table_0').DataTable({
		 ajax: {
			url: `${cPath}/video/rest/list`,
			order: ([[1, "desc"]]),
			dataSrc: function(response) {
				// 응답 데이터 확인
				console.log("response", response.data.list);
				// response.data.list에서 데이터를 추출하여 반환
				return response.data.list;
			},
		},
		language: {
            search: "",  // 'Search:' 텍스트를 '검색:'으로 변경
			lengthMenu: "_MENU_ 개씩 보기",
			info: "_START_ - _END_ / _TOTAL_ 개의 항목",
			 paginate: {
                next: "다음",
                previous: "이전"
            }
        },
		dom: '<"header-wrapper"><"search-wrapper"f>t<"footer-wrapper d-flex justify-content-between align-items-center"lip>',
		lengthMenu: [ 5, 10, 15, 20 ],
		columns: [
			{ 
			  title: 'roomId', 
			  data : 'roomId',
			  render: function(data){
				return `<span class="badge px-2 bg-label-primary" onclick='openRoom("${data}");' text-capitalized="">입장하기</span>`
			}
			
			},
			{ title: '방 제목', data: 'roomTitle' },
			{
				title: '시작 날짜',
				data: 'startDate',
				render: function(data) {
					return parseDate(data);  // parseDate 함수 적용
				}
			},
			{
				title: '종료 날짜',
				data: 'endDate',
				render: function(data) {
					return parseDate(data);  // parseDate 함수 적용
				}
			},
			{ title: '비밀 방', data: 'isDefinePasswd' }
		],
		drawCallback: function() {
            // Add padding to the pagination controls after table draw
            $('.dataTables_paginate').css('padding-right', '25px');
            $('.footer-wrapper').css('height', '25px'); // Adjust dropdown height
        }

	});
}
		
async function openRoom(roomId) {
	try{
		const memberId = document.querySelector('.card').dataset.memberId;
		const memberName = document.querySelector('.card').dataset.memberName;
		console.log("memberId:",memberId);
		console.log("memberName:",memberName);
		const response = await axios.get(`${cPath}/video/rest/join`,{
			params: {
			roomId : roomId,
			memberId : memberId,
			memberName : memberName
			}
		});
		const joinObject = response.data.data;
		console.log("방 입장:", joinObject);
		const joinUrl = joinObject.url;
		console.log("방 입장 주소:", joinUrl);
		window.open(joinUrl);
	}catch(errors){
		console.log("방 입장 오류:", errors);
	}
}
//한글로 시간 변경 하는 함수
function parseDate(dateString) {
    var date = new Date(dateString);

    var year = date.getFullYear().toString().slice(2); // 연도의 마지막 두 자리
    var month = date.getMonth() + 1; // 월 (0부터 시작하므로 +1)
    var day = date.getDate(); // 일
    var hours = date.getHours(); // 시
    var minutes = date.getMinutes(); // 분

    return `${year}년 ${month}월 ${day}일 ${hours}시 ${minutes}분`;
}

document.addEventListener('DOMContentLoaded', ()=>{
	$('#DataTables_Table_0').DataTable({
	
	});
	fetchVideoList();

	const CreateRoomBtn3 = document.querySelector('#CreateRoomBtn3'); //방 생성 Modal Btn
	CreateRoomBtn3.addEventListener('click', () => {
		createVideoRoom();
	});

	$('#modalCenter').on('show.bs.modal', function() {
		nameWithTitle.value = '';
		RoomPassword.value = '';
	});
});

