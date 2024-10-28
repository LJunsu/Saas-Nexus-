const contextPath1 = document.body.dataset.contextPath;
const detailURI2 = `${contextPath1}/Nonsession/Alarm`;
const alarmDiv = document.querySelector("#alarmCount");
const readAllFilter = document.querySelector("#filter-all");
const readFilter = document.querySelector("#filter-read");
const nonReadFilter = document.querySelector("#filter-unread");


const filterButtons = document.querySelectorAll('.filter-btn');
filterButtons.forEach(button => {
    button.addEventListener('click', () => {
        filterButtons.forEach(btn => btn.classList.remove('active'));
        
        button.classList.add('active');
    });
});

//전체 버튼 클릭시 
const AllFilterList = () => FetchUtils.fetchForJSON(`${detailURI2}/all`)
  .then((resp) => {
		console.log(resp);
        if (resp.AlarmList && resp.AlarmList.length > 0) {
            AlarmListUI(resp.AlarmList);
        } else {
            AlarmListUI([]);
        }
    });


// 읽은 알림 리스트를 가져오는 함수
const readFilterList = () => FetchUtils.fetchForJSON(`${detailURI2}/read`)
  .then((resp) => {
		console.log(resp);
        if (resp.AlarmList && resp.AlarmList.length > 0) {
            AlarmListUI(resp.AlarmList);
        } else {
            AlarmListUI([]);
        }
    });


// 읽지 않은 알림 리스트를 가져오는 함수
const nonReadFilterList = () => FetchUtils.fetchForJSON(`${detailURI2}/non`)
  .then((resp) => {
		console.log(resp);
        if (resp.AlarmList && resp.AlarmList.length > 0) {
            AlarmListUI(resp.AlarmList);
        } else {
            AlarmListUI([]);
        }
    });
  


// 클릭 이벤트 핸들러 설정
readFilter.addEventListener("click", async () => {
	readFilterList();
});

nonReadFilter.addEventListener("click", async () => {
 nonReadFilterList();
});

readAllFilter.addEventListener("click", async () => {
 AllFilterList();
});

// 상대적인 시간을 계산하는 함수
const timeSince = (dateTime) => {
    const pastDateTime = new Date(dateTime);

    if (isNaN(pastDateTime)) {
        console.error("Invalid date:", dateTime);
        return "알 수 없음";
    }

    const now = new Date();
    const secondsPast = Math.floor((now - pastDateTime) / 1000);

    if (secondsPast < 60) return `${secondsPast}초 전`;
    if (secondsPast < 3600) return `${Math.floor(secondsPast / 60)}분 전`;
    if (secondsPast < 86400) return `${Math.floor(secondsPast / 3600)}시간 전`;
    if (secondsPast < 2592000) return `${Math.floor(secondsPast / 86400)}일 전`;
    if (secondsPast < 31536000) return `${Math.floor(secondsPast / 2592000)}개월 전`;
    return `${Math.floor(secondsPast / 31536000)}년 전`;
};

// 알림 리스트를 UI에 렌더링하는 함수
const AlarmListUI = (list) => {
    const notificationContainer = document.querySelector(".dropdown-notifications-list .list-group");
    notificationContainer.innerHTML = '';

    if (list.length === 0) {
        notificationContainer.innerHTML = `
            <li class="list-group-item text-center">
                <p class="mb-0">알림 없음</p>
            </li>
        `;
        return;
    }

    list.forEach(alarm => {
        const timeAgo = timeSince(alarm.alarmVO[0].eventdate);

        const opacity = alarm.alarmChk === 'N' ? 1 : 0.5;

        const alarmItem = document.createElement("li");
        alarmItem.classList.add("list-group-item", "list-group-item-action", "dropdown-notifications-item");
        alarmItem.style.position = "relative";
        alarmItem.style.opacity = opacity;

        alarmItem.innerHTML = `
            <a href=${alarm.alarmVO[0].urldata} id="urlData"
                data-alarm-no="${alarm.alarmNo}" class="dropdown-notifications-item" style="padding: 0;text-decoration: none;display: inline-block;">
                <div class="d-flex">
                    <div class="flex-shrink-0 me-3">
                        <div class="avatar">
                            <img src="https://storage.googleapis.com/java_bucket_hstest1/${alarm.alarmVO[0].alarmSender}/profile.png" alt="Avatar" class="h-auto rounded-circle" />
                        </div>
                    </div>
                    <div class="flex-grow-1">
 						<div style-"display:flex; justify-content: space-between;">
                        	<h6 class="mb-1">${alarm.alarmVO[0].alarmSenderN}</h6>
                        	<p class="mb-0" style="color:#7367f0;">${alarm.alarmVO[0].eventtype}</p>
                        </div>
                        <p class="mb-0" style="color:#000000; max-width:280px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${alarm.alarmVO[0].data}</p>
                        <small class="text-muted">${timeAgo}</small>
                    </div>
                </div>
            </a>
        `;
        notificationContainer.appendChild(alarmItem);
    });
};










document.querySelector(".dropdown-notifications-list").addEventListener("click", async (e) => {
    const target = e.target.closest('a[data-alarm-no]');
    if (target) {
        e.preventDefault();
        
        const alarmNo = target.getAttribute('data-alarm-no');
        console.log("알림노 쳌:", alarmNo);

        // 비동기 요청을 보내고 클릭 이벤트를 처리
        try {
            const response = await fetch(`${detailURI2}/${alarmNo}`)

            if (response.ok) {
                const successData = await response.json();
				if(successData.cnt>0){
					console.log("성공");
				}
                // 성공적으로 처리한 후, 실제로 이동할 링크를 따로 처리
                window.location.href = target.href;  // 클릭된 링크로 이동
            } else {
            }
        } catch (error) {
        }
    }
});





// 기본 알림 리스트를 가져와서 렌더링하는 함수
const AlarmList = () => FetchUtils.fetchForJSON(`${detailURI2}/all`)
    .then((resp) => {
		console.log(resp);
        alarmDiv.innerHTML = resp.cnt;
        if (resp.AlarmList && resp.AlarmList.length > 0) {
            AlarmListUI(resp.AlarmList);
        } else {
            AlarmListUI([]);
        }
    });

document.addEventListener("DOMContentLoaded", async () => {
    AlarmList();
});
