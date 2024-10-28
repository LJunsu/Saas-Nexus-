/**
 *
 */
const contextPath2 = document.body.dataset.contextPath;
const detailURI3 = `${contextPath2}`;
const groupIdS = document.querySelector("#groupIddd");
const groupIdd = groupIdS.dataset.groupIds;
const updateBtn = document.querySelector('#updateBtn');
const memberIds = document.querySelector('#memberIds');
const memberId = memberIds.dataset.memberId;
const editUserModal = document.querySelector('#editUser');



const timeAgo = (eventDate) => {
	const pastDateTime = new Date(eventDate);

	if (isNaN(pastDateTime)) {
		console.error("Invalid date:", eventDate);
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


const loginList = () => FetchUtils.fetchForJSON(`${detailURI3}/Mypage/rest/userLoginLog?memberId=${memberId}`);

// 타임라인에 list의 데이터를 렌더링하는 함수
const renderTimeline = (list) => {
	const timelineContainer = document.getElementById('activityTimeline');
	let timelineHTML = '';

	// list의 각 데이터를 순회하며 타임라인 항목 생성
	list.log.forEach(item => {
		const times = timeAgo(item.eventdate);
		const senderName = item.alarmSenderN || '알림 발신자 없음'; // 알림을 보낸 사람
		const projectId = item.projectId || '프로젝트 ID 없음'; // 프로젝트 ID
		const eventType = item.eventtype || '알림 타입 없음'; // 이벤트 타입
		const description = item.data || ''; // 알림 내용
		const url = item.urldata || '#'; // 링크 (클릭 가능한 경우)
		const iconClass = getIconClass(item.eventtype); // 이벤트 타입에 따라 아이콘 색상 설정

		timelineHTML += `
      <li class="timeline-item timeline-item-transparent">
        <span class="timeline-point ${iconClass}"></span>
        <div class="timeline-event">
          <div class="timeline-header">
            <h6 class="mb-0">${eventType}</h6>
            <small class="text-muted">${times}</small>
          </div>
          <p class="mb-2">${description}</p>
          ${url !== '#' ? `<a href=${url} >관련 링크</a>` : ''}
        </div>
      </li>`;
	});

	// 생성된 타임라인 HTML을 DOM에 삽입
	timelineContainer.innerHTML = timelineHTML;
};


// 아이템 타입에 따라 아이콘 색상을 반환하는 함수
const getIconClass = (type) => {
	switch (type) {
		case '로그인':
			return 'timeline-point-success';
		case '이슈 제목 수정':
			return 'timeline-point-danger';
		case '이슈 내용 수정':
			return 'timeline-point-info';
		default:
			return 'timeline-point-primary';
	}
};

// loglistC 함수 내에서 list를 받아 타임라인을 렌더링
const loglistC = async () => {
	let list = await loginList();
	console.log(list);
	renderTimeline(list); // 타임라인에 데이터 렌더링
};


function changeProfileImg(memberProfileId) {
    // 파일 선택창 열기
    document.getElementById('profileImgInput').click();
}

async function uploadProfileImage(memberProfileId) {
    const fileInput = document.getElementById('profileImgInput');
    const files = fileInput.files;

    if (files.length === 0) {
        console.log('No file selected');
        return;
    }

    const formData = new FormData();
 	formData.append('folderPath', memberProfileId);
    formData.append('files', files[0]); // 첫 번째 파일 첨부

    try {
        // 서버로 파일 전송
        const response = await axios.post(`${contextPath2}/gcp/upload`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

      
            console.log('프로필 이미지 업로드', response.data);
            // 업로드된 프로필 이미지를 새로 표시
            document.querySelector('.user-profile-img').src = response.data[0];
    } catch (error) {
        console.error('Error uploading profile image:', error);
    }
}

// DOMContentLoaded 이벤트 핸들러
document.addEventListener("DOMContentLoaded", function() {
	loglistC();
















	updateBtn.addEventListener("click", async function(e) {
		e.preventDefault();

		// SweetAlert2를 사용하여 확인창 띄우기
		Swal.fire({
			title: '정말 회원정보를 변경하시겠습니까?',
			text: "확인 버튼 클릭시 회원정보가 변경됩니다!",
			icon: 'warning',
			showCancelButton: true, // 취소 버튼 추가
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: '확인',
			cancelButtonText: '취소'
		}).then((result) => {
			if (result.isConfirmed) { // 확인 버튼 클릭 시 진행
				const memberN = document.querySelector('#memberName').value;
				const memberR1 = document.querySelector('#memberRegno1').value;
				const memberR2 = document.querySelector('#memberRegno2').value;
				const memberE = document.querySelector('#memberEmail').value;
				const memberT = document.querySelector('#memberTel').value;
				const memberZ = document.querySelector('#sample6_postcode').value;
				const memberA1 = document.querySelector('#sample6_address').value;
				const memberA2 = document.querySelector('#sample6_detailAddress').value;
				const memberAq = document.querySelector('#memberAuthQuestion').value;
				const memberAr = document.querySelector('#memberAuthResponse').value;

				const updateData = {
					memberId: memberId,
					memberName: memberN,
					memberRegno1: memberR1,
					memberRegno2: memberR2,
					memberEmail: memberE,
					memberTel: memberT,
					memberZip: memberZ,
					memberAddress1: memberA1,
					memberAddress2: memberA2,
					memberAuthQuestion: memberAq,
					memberAuthResponse: memberAr,
				};

				// 데이터 업데이트 API 요청
				FetchUtils.fetchForJSON(`${detailURI3}/Mypage/rest/updateInfo`, {
					method: 'PUT',
					headers: {
						"accept": "application/json",
						'Content-Type': 'application/json'
					},
					body: JSON.stringify(updateData) // 요청 본문 설정
				}).then(response => {
					if (response.success) {
						// 성공 시 처리할 로직 추가 (예: 알림 띄우기)
						Swal.fire(
							'성공!',
							'회원 정보가 변경되었습니다.',
							'success'
						);
						const bootstrapModalInstance = bootstrap.Modal.getInstance(editUserModal);
						bootstrapModalInstance.hide();
						window.location.reload();
					} else {
						// 실패 시 처리할 로직 추가
						Swal.fire(
							'실패!',
							'회원 정보 변경에 실패했습니다.',
							'error'
						);
					}
				}).catch(error => {
					// 에러 발생 시 처리할 로직 추가
					console.log(error);
					Swal.fire(
						'오류!',
						'서버와 통신 중 오류가 발생했습니다.',
						'error'
					);
				});
			} else {
				// 취소 버튼 클릭 시 실행할 로직 (필요하면 추가)
				console.log("취소되었습니다.");
			}
		});
	});












	// body에서 클릭 이벤트를 감지
	document.body.addEventListener("click", async function(event) {

		// 클릭된 요소에서 id가 "ProjectList"인 가장 가까운 상위 요소 찾기
		const targetElement = event.target.closest("#ProjectList");

		if (targetElement) {
			// data-mem-i 속성에서 memberId 추출
			const memberId = targetElement.getAttribute("data-mem-i");
			console.log("Clicked member ID: ", memberId);

			// Fetch API를 사용하여 서버에서 데이터 가져오기
			const resp = await FetchUtils.fetchForJSON(`${detailURI3}/Mypage/rest/getProjectList?memberId=${memberId}`);
			console.log("응답", resp);
			console.log(Array.isArray(resp)); // resp가 배열인지 확인
			const projectList = resp.projectList;
			console.log("프로젝트 리스트", projectList);

			if (Array.isArray(projectList)) {
				let modalContent = ''; // 모달에 렌더링할 HTML을 담을 변수
				projectList.forEach(project => {
					let leader = '';
					if (project["PROJECT_LEADER"] === null) {
						leader = '<span class="text-muted">리더 미지정</span>';
					} else {
						leader = `<span class="text-muted">${project["PROJECT_LEADER"]}</span>`;
					}
					modalContent += `
	            		 <div class="col-xl-4 col-lg-6 col-md-6" onclick="location.href='${detailURI3}/${groupIdd}/${project['PROJECT_ID']}/chMain';">
                            <div class="card">
                              <div class="card-header">
                                <div class="d-flex align-items-start">
                                  <div class="d-flex align-items-start">
                                    <div class="avatar me-2">
                                      <img
                                        src="${detailURI3}/resources/nexus/assets/img/icons/brands/react-label.png"
                                        alt="Avatar"
                                        class="rounded-circle mt-1" />
                                    </div>
                                    <div class="me-2 ms-1">
                                      <h5 class="mb-0">
                                        <a href="javascript:;" class="stretched-link text-body" style="font-weight: 700;">${project["PROJECT_NAME"]}</a>
                                      </h5>
                                      <div class="client-info">
                                        <span class="fw-medium mt-2">프로젝트 리더: </span>
                                        ${leader}
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>
                              <div class="card-body">
                                <div class="d-flex justify-content-between align-items-center mb-2 pb-1">
                                  <small>완료된 이슈: ${project["ISSUE_COUNT_COMPLETE"]}/${project["ISSUE_COUNT"]}</small>
                                  <small>${project["ISSUE_COMPLETE_PERCENT"]}% Completed</small>
                                </div>
                                <div class="progress mb-2" style="height: 8px">
                                  <div
                                    class="progress-bar"
                                    role="progressbar"
                                    style="width: ${project['ISSUE_COMPLETE_PERCENT']}%"
                                    aria-valuenow="${project['ISSUE_COMPLETE_PERCENT']}"
                                    aria-valuemin="0"
                                    aria-valuemax="100"></div>
                                </div>
                                <div class="d-flex align-items-center pt-1">
                                  <div class="d-flex align-items-center">
                                    <ul class="list-unstyled d-flex align-items-center avatar-group mb-0 z-2">
                                      <li
                                        data-bs-toggle="tooltip"
                                        data-popup="tooltip-custom"
                                        data-bs-placement="top"
                                        title="Kaith D'souza"
                                        class="avatar avatar-sm pull-up">
                                        <img class="rounded-circle" src="${detailURI3}/resources/nexus/assets/img/avatars/5.png" alt="Avatar" />
                                      </li>
                                      <li
                                        data-bs-toggle="tooltip"
                                        data-popup="tooltip-custom"
                                        data-bs-placement="top"
                                        title="John Doe"
                                        class="avatar avatar-sm pull-up">
                                        <img class="rounded-circle" src="${detailURI3}/resources/nexus/assets/img/avatars/1.png" alt="Avatar" />
                                      </li>
                                      <li
                                        data-bs-toggle="tooltip"
                                        data-popup="tooltip-custom"
                                        data-bs-placement="top"
                                        title="Alan Walker"
                                        class="avatar avatar-sm pull-up me-2">
                                        <img class="rounded-circle" src="${detailURI3}/resources/nexus/assets/img/avatars/6.png" alt="Avatar" />
                                      </li>
                                      <li><small class="text-muted">${project["PROJECT_MEM_COUNT"]}명의 멤버</small></li>
                                    </ul>
                                  </div>
                                  <span class="badge bg-label-warning ms-auto">${project["PROJECT_CREATION_DATE"]} 생성</span>
                                </div>
                              </div>
                            </div>
                          </div>
                                    `;

				});
				document.getElementById('projectDetails').innerHTML = modalContent;
				const projectModal = new bootstrap.Modal(document.getElementById('projectModal'));
				projectModal.show();
			} else {
				console.log("projectVO 데이터가 존재하지 않습니다.");
			}


		}
	});
});
