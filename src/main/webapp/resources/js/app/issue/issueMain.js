
const senderId = document.querySelector('#userId').dataset.userId;
const senderName = document.querySelector('#userName').dataset.userName;

const editor_area = document.getElementById('editor-area');
const showEditor = document.getElementById('showEditor');
const CmtH4 = document.getElementById('cmt-img');
const formDataToJSON = (form) => {
    let formData = new FormData(form);
    let data = {};
    for (let name of formData.keys()) {
        let value = formData.get(name);
        data[name] = value;
    }
    return JSON.stringify(data);
};
var quillhtml;
function showToast(title) {
  const Toast = Swal.mixin({
    toast: true,
    position: "bottom-end",
    showConfirmButton: false,
    timer: 2500,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.onmouseenter = Swal.stopTimer;
      toast.onmouseleave = Swal.resumeTimer;
    }
  });

  Toast.fire({
    icon: "success",
    title: title
  });
}
//const issueTagModal = document.querySelector('#issueTagModal');
let ModalTagVal='';
$('#issueTagModal').on('change', ()=>{
ModalTagVal= issueTagModal.value;
	
});
console.log('ModalTagVal',ModalTagVal);
// 이슈 생성 함수
document.getElementById("insertIssueModalBtn").addEventListener("click", async function (event) {
    event.preventDefault(); // 폼이 기본적으로 제출되는 것을 방지

    // 각 입력 필드에서 값을 가져오기
    const data = {
        projectMemIdx: document.getElementById("issueCreator").value, // 생성자 ID
        issueTitle: document.querySelector("input[name='issueTitleModal']").value, // 이슈 제목
        issueContent: document.querySelector("textArea[name='issueContentModal']").value, // 이슈 내용
        issueOfficer: document.querySelector("select[name='issueOfficer']").value, // 이슈 담당자
        issueDate: document.querySelector("input[name='issueDate']").value, // 이슈 생성 날짜
        issueDeadline: document.querySelector("input[name='issueDeadline']").value, // 이슈 종료 날짜
        subIssueIdx: document.querySelector("select[name='subIssueIdx']").value, // 상위 이슈 번호
        issueProgressStatus: document.querySelector("input[name='issueProgressStatus']").value, // 이슈 진행 상태
        issuePriorityName: document.querySelector("select[name='issuePriorityNameModal']").value, // 이슈 우선 순위
        issueTagName: document.querySelector("select[name='issueTagModal']").value, // 태그
        projectId: document.querySelector("input[name='projectId']").value, // 프로젝트 ID
        senderId: document.querySelector("input[name='senderId']").value, // 생성자 ID
        senderName: document.querySelector("input[name='senderName']").value // 생성자 이름
    };

    console.log(data); // 데이터 객체 확인 (디버그용)
    try {
        
        // 태그 선택 값을 가져옵니다

        // 태그 값을 함께 포함하여 요청 데이터를 만듭니다
       

        // axios 요청 보내기
        const response =  await axios.post(`${cPath}/rest/issue/create`,data);
			

        // 요청 성공 시 처리
        console.log("이슈 생성 성공:", response.data);

        // 모달 닫기
        $('#largeModal').modal('hide');

        // 리스트 갱신 함수 호출 (예: boardList)
        boardList();

    } catch (error) {
        console.error("이슈 생성 에러:", error);
    }
});






//이슈 진행상태를 변경
const issueProgressStatus = document.querySelector("select[name='issueProgressStatus']");

const issueProgressChangeEvnet = () => issueProgressStatus.addEventListener("change", () => {
	const select_issue$ = $(issueProgressStatus).val();
	const select_issue_name$ = issueProgressStatus.selectedOptions[0].text;
	console.log(select_issue$);
	axios.post(`${cPath}/rest/issue/progress/${issueIdx}`, {
		issueProgressStatus: select_issue$,
		senderId : senderId,
		senderName : senderName
	})
		.then(function(response) {
			console.log("officer:", response.data);
			showToast(`진행상태 변경 완료.`);
			boardList();
		}).catch(error => console.log("진행상태:", error));
});

//이슈 우선순위를 변경
const issuePriority = document.querySelector("select[name='issuePriorityName']");

const issuePriorityChangeEvnet = () => issuePriority.addEventListener("change", () => {
	const select_issue$ = $(issuePriority).val();
	const select_issue_name$ = issuePriority.selectedOptions[0].text;
	console.log(select_issue$);
	axios.post(`${cPath}/rest/issue/priority/${issueIdx}`, {
		issuePriorityName: select_issue$,
		senderId : senderId,
		senderName : senderName
	})
		.then(function(response) {
			console.log("officer:", response.data);
			showToast(` 우선순위 변경 완료.`);
		}).catch(error => console.log("우선순위:", error));
});

//이슈 담당자를 변경
const issueOfficerIdx = document.querySelector("select[name='issueOfficerIdx']");

const issueOfficerChangeEvnet = () => issueOfficerIdx.addEventListener("change", () => {
	const select_issue$ = $(issueOfficerIdx).val();
	const select_issue_name$ = issueOfficerIdx.selectedOptions[0].text;
	console.log(select_issue$);
	axios.post(`${cPath}/rest/issue/officer/${issueIdx}`, {
		issueOfficer: select_issue$,
		senderId : senderId,
		senderName : senderName
	})
		.then(function(response) {
			console.log("officer:", response.data);
			showToast(`${select_issue_name$} 담당자 변경 완료.`);
		}).catch(error => console.log("담당자:", error));
});
//상위이슈 선택후 목록을 업데이트
const setData = async () => {
	    let updateIssueData = await loadIssueData(issueIdx);
	    console.log("업데이트 Promise 반환",updateIssueData);
		loadSubIssue(updateIssueData);
	  };
//상위 이슈 선택시 update 요청을 보냄
const downIssueChangeEvent = () => upIssue_select.addEventListener("change", () => {
	select_issue$ = $(upIssue_select).val();
	axios.post(`${cPath}/rest/issue/upissue/${issueIdx}`, {
		subIssueIdx: select_issue$,
		senderId : senderId,
		senderName : senderName
	}).then(function(response) {
		console.log("upIssueUpdate:", response);
		showToast(`상위 이슈 변경 완료.`);
		setData();
		
		upIssue_select.value = select_issue$;
	}).catch(error => console.log("상위 이슈:", error));
});

const modalOpenBtn = document.querySelector("#createIssueBtn");
const subIssueIdxModal = document.querySelector("select[name='subIssueIdx']");

// 모달 창을 띄우는 버튼 클릭 시 이벤트 핸들러
const modalOpenClickEvent = () => modalOpenBtn.addEventListener("click", () => {
	// 모달을 띄우기 전에 Axios 요청을 보냅니다.
	axios.post(`${cPath}/rest/issue/uplist/${projectId}`)
		.then(response => {
			const upIssueList = response.data;
			console.log("upIssueListModal:", response)
			// 모달 내 select 요소 초기화
			subIssueIdxModal.innerHTML = `<option value="">상위 이슈 선택</option>`;

			// 응답 데이터를 기반으로 모달에 옵션 추가
			upIssueList.forEach(issue => {
				const option = document.createElement("option");
				option.value = issue.issueIdx;
				option.text = `${issue.issueIdx} ${issue.issueTitle}`;
				subIssueIdxModal.appendChild(option);
			});

			// 데이터가 삽입된 후 모달을 띄웁니다.
			$('#largeModal').modal('show');
		})
		.catch(error => {
			console.error("Error:", error);
		});
});

//이슈 제목을 수정
const issueTitleInput = document.querySelector("input[name='issueTitle']");
// 'issueTitle' input 클릭 시 수정 가능하게 설정
const issueTitleClickEvent = () => issueTitleInput.addEventListener("click", () => {
	issueTitleInput.removeAttribute("readonly"); // 수정 가능하게 만듦
	issueTitleInput.focus(); // 입력 필드에 포커스를 줌
});

// 입력 필드에서 포커스를 잃을 때(수정 완료 시) 서버로 수정된 값 전송
const IssueTitleUpdate = () => issueTitleInput.addEventListener("blur", () => {
	const updatedTitle = issueTitleInput.value; // 수정된 값을 가져옴

	// 서버로 Axios 요청을 보냄
	axios.post(`${cPath}/rest/issue/title/${issueIdx}`, {
		issueTitle: updatedTitle,
		senderId : senderId,
		senderName : senderName
	})
		.then(response => {
			console.log('Update successful:', response.data);
			issueTitleInput.setAttribute("readonly", true); // 다시 읽기 전용으로 설정
			showToast("제목 수정 완료");
		})
		.catch(error => {
			console.error('Update failed:', error);
//			alert('업데이트에 실패했습니다.');
		});
});

// Enter 키를 누르면 blur 이벤트를 트리거하여 업데이트
const IssueTitleClickEnter = () => issueTitleInput.addEventListener("keydown", (event) => {
	if (event.key === "Enter") {
		issueTitleInput.blur(); // 포커스를 잃도록 처리
	}
});

// 입력 필드에서 포커스를 잃을 때(수정 완료 시) 서버로 수정된 값 전송
const issueContentModify = () => issueContent.addEventListener("blur", () => {
	const updatedContent = issueContent.value; // 수정된 값을 가져옴

	// 서버로 Axios 요청을 보냄
	axios.post(`${cPath}/rest/issue/content/${issueIdx}`, {
		issueContent: updatedContent,
		senderId : senderId,
		senderName : senderName
	})
		.then(response => {
			console.log('Update successful:', response.data);
			issueContent.setAttribute("readonly", true); // 다시 읽기 전용으로 설정
			showToast("내용 수정 완료");
		})
		.catch(error => {
			console.error('Update failed:', error);
//			alert('업데이트에 실패했습니다.');
		});
});
// Enter 키를 누르면 blur 이벤트를 트리거하여 업데이트
const issueContentPushEnter = () => issueContent.addEventListener("keydown", (event) => {
	if (event.key === "Enter") {
		issueContent.blur(); // 포커스를 잃도록 처리
	}
});

//상위 이슈를 추가
const downIssue = document.querySelector("#downIssue");
const downIssue_area = document.querySelector("#downIssue-area");
var upIssue_select = document.querySelector("#upIssue-select");
const subIssueIdx = document.querySelector("select[name='subIssueIdx']");
// 하위 이슈 리스트를 불러오는 함수
const loadUpIssueList = () => {
	upIssue_select.style.display="block";
	axios.post(`${cPath}/rest/issue/uplist/${projectId}`)
		.then(function(response) {
			console.log(response.data);
			const upIssueList = response.data;
			upIssue_select.innerHTML = `<option value="">상위 이슈 선택</option>`; // 추가: upIssue_select도 초기화
			for (idx in upIssueList) {
				const option = document.createElement("option");
				option.value = upIssueList[idx].issueIdx;
				option.text = `${upIssueList[idx].issueIdx} ${upIssueList[idx].issueTitle}`;
				upIssue_select.appendChild(option);
			}
		});
};

const cPath = document.body.dataset.contextPath;
const issueList = document.getElementById("issue-list");
const projectId = document.querySelector("input[name='projectId']").value;
let sdObject;
console.log("issueList:",issueList);
const boardList =  (status, searchTerm) =>  axios.get(`${cPath}/rest/issue/project/${projectId}`,{
	params: {
                issueProgressStatus: status,
                issueTitle: searchTerm
            },
})
	.then(function (response){
		console.log("성도1",response.data);
		sdObject = response.data; //ISSUE에 모든 데이터를 응답으로 받아온다. data

		let html='';
		//이슈에 리스트를 출력
		for(idx in sdObject){
			let date = sdObject[idx].issueCreationDate;
			let year = date[0];
			let month = date[1];
			let day = date[2];
			if(date[1] < 10){
				month = "0"+date[1];
			}
			if(date[2] < 10){
				//9일 이라면 09로 출력하기 위해 작성
				day = "0"+date[2];
			}
			html +=
			`
			<li class="chat-contact-list-item"><a
							class="d-flex align-items-center" id="target_${sdObject[idx].issueIdx}">
								<div class="flex-shrink-0 avatar">
									<p id="issue-progerss-color${sdObject[idx].issueIdx}">●</p>

								</div>
								<div class="chat-contact-info flex-grow-1 ms-2">
									<h6 class="chat-contact-name text-truncate m-0 issue-list"
										id="issue-contact" data-issue-idx="${sdObject[idx].issueIdx}" style="font-weight: 600;">${sdObject[idx].issueTitle}</h6>
									<p class="chat-contact-status text-muted text-truncate mb-0">
										${sdObject[idx].issueContent}</p>
								</div> <div><small class="text-muted mb-auto">${year+"/"+month+"/"+day}</small><br>
								<small class="text-muted mb-auto" style=margin-left: 33%;">${sdObject[idx].issueIdx}</small>
								</div>
						</a></li>
			`

		}
		issueList.innerHTML = html;
		// 스타일 적용
		for (const idx in sdObject) {
			let issueProgressColor = document.getElementById(`issue-progerss-color${sdObject[idx].issueIdx}`);
			if (sdObject[idx].issueProgressStatus == "Completed") {
				issueProgressColor.style.color = "green";
			}
			if (sdObject[idx].issueProgressStatus == "In Progress") {
				issueProgressColor.style.color = "blue";
			}
			if (sdObject[idx].issueProgressStatus == "Not Started") {
				issueProgressColor.style.color = "red";
			}
		}

	})
	.catch(function (error){
		console.log("이슈 리스트:",error);
	})

let currentStatus = '';
let currentSearchTerm = '';
function updateStatusAndFetch(status) {
	currentStatus = status;
	boardList(currentStatus, currentSearchTerm);
}
 function sendRequest(status) {
        axios.get(`${cPath}/rest/issue/project/${projectId}`, {
            params: {
                issueProgressStatus: status
            }
        })
        .then(response => {
            console.log('이슈 리스트:', response.data);
        })
        .catch(error => {
            console.error('요청 실패:', error);
        });
    }



let memObject;
const officerSelectByName = document.querySelector("select[name='issueOfficerIdx']");
const officerSelectByPath = document.querySelector("select[name='issueOfficer']");

officerSelectByName.innerHTML = ""; // 기존 옵션 초기화
officerSelectByPath.innerHTML = ""; // 기존 옵션 초기화

//로그인 정보를 가져옴
const loginId = document.querySelector("input[name='projectMemIdx']").dataset.issueCreator;
const issueCreator = document.querySelector("#issueCreator");
const issueCreatorDisplay = document.querySelector('#issueCreatorDisplay');

console.log("projectmem:",loginId);
console.log("issueCreator:",issueCreator);
const projectMemberName = new Map();
const projectMemberList = () => axios.get(`${cPath}/rest/issue/member/${projectId}`)
  .then(function (response) {
    memObject = response.data;
	console.log("memObject:",memObject);
    memObject.forEach(member => {
      const optionByName = document.createElement("option");
      optionByName.value = member.projectMemIdx; // value에 projectMemIdx 사용
      optionByName.text = member.member.memberName; // 텍스트에 이름 사용
	  projectMemberName.set(member.memberId,member.member.memberName);
	  projectMemberName.set(member.projectMemIdx,member.member.memberName);
	  if(member.memberId == loginId){
	console.log("실제 생성 아이디를 아렬다오>>>>>>>>:",member.projectMemIdx);
	  issueCreator.value = member.projectMemIdx;
	  issueCreatorDisplay.value = projectMemberName.get(member.projectMemIdx);
		console.log("memberId:",member.memberId);
		console.log("이름:",projectMemberName.get(member.projectMemIdx));
	}
      const optionByPath = optionByName.cloneNode(true); // 동일한 옵션을 복사

      officerSelectByName.appendChild(optionByName); // 첫 번째 select에 옵션 추가
      officerSelectByPath.appendChild(optionByPath); // 두 번째 select에 옵션 추가
    });
  })
  .catch(function (error) {
    console.error("프로젝트 멤버 조회:", error);
  });
console.log("projectMemberName:",projectMemberName);
//이슈 내용을 수정
const issueContent = document.querySelector("textarea[name='content-line']");
// 'issueTitle' input 클릭 시 수정 가능하게 설정
const contentClick = () => issueContent.addEventListener("click", () => {
	issueContent.removeAttribute("readonly"); // 수정 가능하게 만듦
	issueContent.focus(); // 입력 필드에 포커스를 줌
});

function formatDate(dateArray) {
    // 년도는 그대로 사용
    const year = dateArray[0];

    // 월이 10보다 작으면 앞에 0을 붙임
    const month = dateArray[1] < 10 ? '0' + dateArray[1] : dateArray[1];

    // 일이 10보다 작으면 앞에 0을 붙임
    const day = dateArray[2] < 10 ? '0' + dateArray[2] : dateArray[2];

    // YYYY/MM/DD 형식으로 반환
    return `${year}-${month}-${day}`;
}
var issueIdx;
const issueDelBtnArea = document.querySelector('#issueDelBtnArea');

function loadIssueData(issueIdx) {
	document.getElementById("loading").style.display = "block";
	
	upIssue_select.style.display = "none";

	document.querySelector(".container-xxl.flex-grow-1.container-p-y").classList.add("blur");
    return axios.get(`${cPath}/rest/issue/${issueIdx}`)
        .then(function (response) {
			 setTimeout(function() {
		      // 요청이 끝나면 로딩창을 숨김
		      document.getElementById("loading").style.display = "none";
		      document.querySelector(".container-xxl.flex-grow-1.container-p-y").classList.remove("blur");
		    }, 500); 
            const issueObject = response.data;
            console.log("issueObject:", issueObject);
			showPreview(issueIdx);
            document.querySelector("#issueNum").innerHTML = issueObject.issueIdx;

            document.querySelector("input[name='issueTitle']").value = issueObject.issueTitle;
            document.querySelector("input[name='projectMemIdx']").value = projectMemberName.get(issueObject.projectMemIdx);
            document.querySelector("textarea[name='content-line']").value = issueObject.issueContent || "내용 없음";
			console.log("loginId: ", projectMemberName.get(loginId));
			console.log("projectMemIdx", projectMemberName.get(issueObject.projectMemIdx));
			if(projectMemberName.get(loginId).startsWith(projectMemberName.get(issueObject.projectMemIdx))){
			issueDelBtnArea.innerHTML = `<img src="/nexus/resources/nexus/assets/img/icons/brands/Ximg.jpg" onclick='issuedelete(issueIdx)' id="issueDelShowBtn">`;
			}
            document.querySelector("select[name='issueOfficerIdx']").value = issueObject.issueOfficer;
			document.querySelector("select[name='issueProgressStatus']").value = issueObject.issueProgressStatus;
			document.querySelector("select[name='issuePriorityName']").value = issueObject.issuePriorityName;
            document.querySelector("input[name='issueCreation']").value = formatDate(issueObject.issueDate);
            document.querySelector("input[name='issueDead']").value = formatDate(issueObject.issueDeadline);
            const issueTagNameInput$ = $("select[name='issueTagName']");
            const issueTagName = issueObject.issueTags[0]?.issueTagName;
            if (issueTagName) {
                issueTagNameInput$.val(issueTagName).select2({ tags: true });
                $(".select2-selection__clear").val(issueTagName);
            } else {
                issueTagNameInput$.val("");
            }

            return issueObject.subIssueIdx;
        })
        .catch(function (error) {
            console.error("이슈 데이터 조회 에러:", error);
        });
}


//이슈를 삭제 하는 요청
async function issuedelete(issueIdx) {
    try {
        const result = await Swal.fire({
            title: "이슈를 정말 삭제하시겠습니까?",
            text: "삭제 시 복구가 불가능합니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        });

        if (result.isConfirmed) {
			//댓글 삭제 요청을 보냄
            const response = await axios.post(`${cPath}/rest/issue/issueDelete/${issueIdx}`, {

            });

            console.log('댓글 삭제 성공:', response.data);

            // 삭제 성공 후 메시지 표시
            await Swal.fire({
                title: "삭제되었습니다.",
                text: "이슈 삭제 완료.",
                icon: "success"
            });

            let issueNumber = parseInt(issueIdx.split("_")[1]) - 1;
            let previousIssueIdx = `ISS_${issueNumber}`;

            // UI를 업데이트
			boardList();
			loadIssueData(previousIssueIdx);
        }
    } catch (error) {
        console.error('댓글 삭제 에러:', error);
        await Swal.fire({
            icon: "error",
            title: "실패하였습니다.",
            text: "관리자에게 문의 바랍니다.",
        });
    }
}

//파일 업로드
document.getElementById('fileName').addEventListener('input', async function () {
    const fileInput = document.getElementById('fileName');
    const files = fileInput.files;

	console.log("files",files)

    const formData = new FormData();
	try{
		
        formData.append('folderPath', issueIdx);
   for (let i = 0; i < files.length; i++) {
        formData.append('files', files[i]);
		
    }

    // 파일을 서버로 전송
    const response = await axios.post(`${cPath}/gcp/upload`, formData, { 
        headers: {
             'Content-Type': 'multipart/form-data'
        },
		
    });
		console.log('File:',response)
		showPreview(issueIdx);
		if (response.data && response.data.uploadedFiles) {
            displayUploadedFiles(response.data.uploadedFiles);
        }
	}catch(error){
		console.log('FileFail:',error);
	}
    // 파일을 FormData에 추가
});

//업로드 된 파일을 프리뷰로 출력
let showPreview = function(issueIdx) {
	
	axios.get(`${cPath}/gcp/listFiles`, { 
		params: { 
				  folderName : issueIdx					
		 } 
		})
		.then(response => {
			let trueFileName = '';
			let listFile = response.data;
			console.log("listFile:",listFile);
			let preview_content = '';
			if (listFile && listFile.length !== 0){
			for(idx in listFile){
			 trueFileName = listFile[idx].name;
			console.log("trueFileName",trueFileName)
			const url = `https://storage.googleapis.com/java_bucket_hstest1/${issueIdx}/${trueFileName}`
			const fileExtension = trueFileName.split('.').pop().toLowerCase();
			
			preview_content += `<div id="preview-content">`;
			//삭제를 위한 아이콘 추가.
			preview_content += `<span class="delete-icon" onclick="deleteFile('${trueFileName}')">X</span>`;
			if (['jpg', 'jpeg', 'png', 'gif'].includes(fileExtension)) {
				preview_content += `<img src="${url}" class="preview-img" alt="Image Preview" onclick="downloadFile('${trueFileName}')">`;
			} else if (['mp4', 'webm', 'ogg'].includes(fileExtension)) {
				preview_content += `<video src="${url}" class="preview-video" onclick="downloadFile('${trueFileName}')" controls>Your browser does not support the video tag.</video>`;
			} else if (fileExtension === 'pdf') {
				preview_content += `<embed src="${url}" class="preview-pdf" onclick="downloadFile('${trueFileName}')" type="application/pdf">`;
			} else {
				preview_content += '<small><p>미리보기를 <br>지원하지 않는 <br>파일 형식입니다.</p></small>';
			}
			preview_content += `<div class="preview-filename">${trueFileName}</div>`
			preview_content += '</div>';
			}
			}else{
				preview_content += `<h4>첨부된 파일이 없습니다.</h4>`;
			}
			
			const modalBody = document.getElementById('preview-area');
			modalBody.innerHTML = preview_content;
		})
		.catch(error => {
			console.error('미리보기 로딩 중 오류 발생:', error);
		});
}
//선택한 파일을 삭제하는 요청 함수
async function deleteFile(fileName) {
    try {
		const result = await Swal.fire({
            title: `${fileName} 삭제하시겠습니까?`,
            text: "삭제 시 복구가 불가능합니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        });
		 if (result.isConfirmed) {
        const response = await axios.delete(`${cPath}/gcp/${issueIdx}/delete`, {
            params: {
                fileName: encodeURIComponent(fileName)
            }
        });
        console.log("삭제 성공:", response.data);
		await Swal.fire({
                title: "삭제되었습니다.",
                text: "파일 삭제 완료.",
                icon: "success"
            });
        // 파일 삭제 후 미리보기 갱신 또는 필요한 동작 추가
        showPreview(issueIdx); // 삭제 후 미리보기 영역을 갱신
		}
    } catch (error) {
        console.error("삭제 실패:", error);
    }
}

//선택한 파일을 다운로드
async function downloadFile(fileName){
    try {
        // 파일 다운로드 요청
        const response = await axios.get(`${cPath}/gcp/download`, {
            params: {
                fileName: `${issueIdx}/${fileName}` // 이미 인코딩된 파일 이름 사용
            },
            responseType: 'blob' // 바이너리 데이터로 응답받음
        });

        if (response.data) {
            // Blob을 사용하여 파일 다운로드
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', fileName); // 다운로드할 파일 이름 지정
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);

            console.log('Download 성공');
        } else {
            console.error('데이터가 없음');
        }
    } catch (error) {
        console.error('Download 실패:', error);
    }
};




//상위 이슈에 대한 데이터를 가져온다.
function loadSubIssue(subIssueIdx) {
    return axios.get(`${cPath}/rest/issue/${subIssueIdx}`)
        .then(function (response) {
            const subIssueObject = response.data;
			console.log("subIssueObject",subIssueObject);
            const downIssue_view = document.getElementById("downIssue-view");
            downIssue_view.innerHTML = "";
            if (subIssueObject.length !== 0) {
                downIssue_view.innerHTML = `
                    <fieldset>
                        <legend>상위 이슈</legend>
                        <label for="name1">${subIssueObject.issueIdx}</label>
                        <input type="text" style="margin-left= 1%;" value="${subIssueObject.issueTitle}" id="name1" disabled>
                        <label for="name2" style="margin-left= 8%;">생성날짜</label>
                        <input type="text" id="name2" style="width: 18%;" value="${formatDate(subIssueObject.issueDate)}">
                    </fieldset>
                `;
            }
        })
        .catch(function (error) {
            console.error("서브 이슈 조회 에러:", error);
        });
}

function loadComments(issueIdx) {
    const cmtHtml_area = document.getElementById("CmtHtml-area");
    return axios.get(`${cPath}/rest/issue/command/${issueIdx}`)
        .then(function (response) {
            const commandObject = response.data;
            console.log("commandObject:", commandObject);
            cmtHtml_area.innerHTML = "";
            for (let idx in commandObject) {
                const commandHtml = `
					<div id="CmtAllLine-area">
					
                    <div id="CmtWriter-area" data-issue-comment-id="${commandObject[idx].issueCommentId}" ">
					<img src="https://storage.googleapis.com/java_bucket_hstest1/${commandObject[idx].issueCmtWriter}/profile.png" alt="" id="cmt-img"class="h-auto rounded-circle" style="margin-left: 2%;width: 7%;height: 43px;">
                        <p name="issueCmtWriter" id="issueCmtWriter${idx}"
                            class="form-control" style="border: none; margin-bottom:0px;"required="required" value="${commandObject[idx].issueCmtWriter}" readonly/>
                            ${projectMemberName.get(commandObject[idx].issueCmtWriter)}</p>

                    </div>
                    <div id="Cmt-area">
                        <div id="CmtContent-area" class="Cmtcontent-area${commandObject[idx].issueCommentId}">
                            <span type="text" name="issueCmtContent" id="issueCmtContent${idx}"
                                class="form-control" readonly style="border: none;"></span>
                        </div>
                        <div id="CmtCrDate-area">
                            <input type="date" name="issueCmtCrDate"
                                class="form-control" required="required" value="${formatDate(commandObject[idx].issueCmtCrDate)}" style="border:none;"readonly/>
                        </div>
                        <div id="CommentId-area">
                            <input type="hidden" name="issueCommentId"
                                class="form-control" value="${commandObject[idx].issueCommentId}" />
                        </div>
                    </div>
				</div>
                `;
                cmtHtml_area.innerHTML += commandHtml;
                document.getElementById(`issueCmtContent${idx}`).innerHTML = commandObject[idx].issueCmtContent;
				if(commandObject[idx].issueCmtWriter == loginId){
                document.getElementById(`issueCmtWriter${idx}`).innerHTML += `
																	<div class="dropdown">
																	      <button class="btn p-0 dropdown-toggle hide-arrow" >
																	        <i class="ti ti-dots-vertical" style="margin-right: 10px"></i>
																	      </button>
																	      <div class="dropdown-content">
																	        <a id="modifyCmt">수정</a>
																	        <a id="delCmt">삭제</a>
																	      </div>
																	    </div>`;
				}
            }
        })
        .catch(function (error) {
            console.error("댓글 조회 에러:", error);
        });
}

const CmtHtml_area = document.querySelector('#CmtHtml-area');

//클릭한 댓글에 commentId를 찾는다.
	function findCommntId(e, selector) {
    const target = e.target.closest(selector); // selector로 지정된 요소에 가장 가까운 상위 부모를 찾는다
    if (target) { // 해당 요소가 존재한다면
        const commentElement = target.closest('#CmtWriter-area'); // 'CmtWriter-area' 영역을 찾는다
        if (commentElement) {
            const commentId = commentElement.dataset.issueCommentId; // data-issue-comment-id 값을 가져온다
            console.log('commentId:', commentId);
            return commentId;
        }
    }
    return null; // 없을 경우 null 반환
}
const cmtAreaClickfindId = () => CmtHtml_area.addEventListener('click', (e) => {
    const deleteTarget = e.target.closest('#delCmt');
    const modifyTarget = e.target.closest('#modifyCmt');

    if (deleteTarget) { //삭제하기 버튼 클릭시
        let commentId = findCommntId(e, "#delCmt");
        IssueCmtDelete(commentId);
    } else if (modifyTarget) { //수정하기 버튼 클릭시
        let commentId = findCommntId(e, "#modifyCmt");
        modifyCommentContent(commentId);
    }
});

//수정하기 버튼 클릭시 이벤트
var quill2Text;

function modifyCommentContent(commentId) {

	document.getElementById("CmtHtml-area").addEventListener('click', (e) => {
		// 클릭된 요소가 수정 버튼인 경우에만 처리
		if (e.target && e.target.id === 'modifyCmt') {
			// 클릭된 버튼의 부모 요소 중에서 댓글 영역을 찾는다
			const commentElement = e.target.closest('#CmtWriter-area');
			if (commentElement) {
				// 댓글 내용이 있는 요소를 찾는다
				const CmtContent_area = commentElement.parentElement.querySelector(`.Cmtcontent-area${commentId} span`);
				console.log('수정할 댓글 ID:', commentId);
				console.log('CmtContent_area:', CmtContent_area);
				const CmtContent_areaText = CmtContent_area.innerText.replace(/\n/g, '').trim();
				// CmtContent_area를 수정 모드로 변경 Quill editor를 삽입한다.
				CmtContent_area.innerHTML = `
							<div id="editorContentArea">
							<div id="editor2"></div>
							<div id="length"></div>
							

							<input type="button" id="CmtTargetbtn" value="저장" class="btn btn-primary waves-effect waves-light">
							<input type="button" id="CmtTargetCanclebtn"  value="취소" class="btn btn-danger waves-effect waves-light">
							</div>`;
				//Quill에 내용을 담을 수 있는 변수 생성
				//Quill.js 를 호출
					const quill2 = new Quill('#editor2', {
					theme: 'snow'
				});
				
				 // 줄바꿈 제거
				quill2.setText(CmtContent_areaText);
				const divlength = document.querySelector("#length");
				quill2.on('text-change', () => {
					quill2Text = quill2.root.innerHTML; // quillhtml의 값을 업데이트
					const length = quill2.getLength();
					divlength.innerHTML = `<p>(${length}/150)</p>`;
					if (149 < length) {
						quill2.deleteText(150, 160);
						const Toast = Swal.mixin({
							toast: true,
							position: "bottom-end",
							showConfirmButton: false,
							timer: 1500,
							timerProgressBar: true,
							didOpen: (toast) => {
								toast.onmouseenter = Swal.stopTimer;
								toast.onmouseleave = Swal.resumeTimer;
							}
						});

						Toast.fire({
							icon: "warning",
							title: '댓글 150자 초과'
						});
					}
					console.log('quill2Text:', quill2Text);
				});
				const CmtTargetbtn = document.getElementById('CmtTargetbtn');
				const CmtTargetCanclebtn = document.getElementById('CmtTargetCanclebtn');
				const qull2Area = document.querySelector('#editor2');
				const length = document.querySelector('#length');
				const editorContentArea = document.querySelector('#editorContentArea');
				//댓글 저장하기 버튼 클릭시
				CmtTargetbtn.addEventListener('click', () => {
					modifyCmtTarget(quill2Text, commentId, quill2);
				});
				//댓글 취소버튼 클릭시
				CmtTargetCanclebtn.addEventListener('click', () => {
					loadComments(issueIdx);
					qull2Area.style.display = "none";
					editorContentArea.style.display = "none";
					length.style.display = "none";
					qull2Area.root.innerHTML = "";
				});
			}
		}
	});
}



//댓글 수정 함수
async function modifyCmtTarget(quill2Text, commentId, quill2) {
    try {
        // 서버로 보낼 데이터 객체 생성
        const data = {
            issueCommentId: commentId,
            issueCmtContent: quill2Text
        };

        //댓글수정 axios 요청 보내기
        const response = await axios.post(`${cPath}/rest/issue/modifyComment/${issueIdx}`, data);

        console.log("댓글 수정 성공:", response.data);
		showToast("댓글 수정 성공");
		loadComments(issueIdx);
		quill2.root.innerHTML ="";
		quill2Text = "";
    } catch (error) {
        console.error("댓글 수정 에러:", error);
    }
}



// 수정된 댓글 서버에 저장
function saveModifiedComment(commentId, modifiedContent) {
    axios.post(`${cPath}/rest/cmtModify/${issueIdx}`, {
        issueCommentId: commentId,
        issueCmtContent: modifiedContent
    }).then(response => {
        console.log('댓글 수정 성공:', response.data);
        loadComments(issueIdx); // 댓글 목록을 다시 로드
    }).catch(error => {
        console.error('댓글 수정 에러:', error);
    });
}


async function IssueCmtDelete(commentId) {
    try {
        const result = await Swal.fire({
            title: "정말 삭제하시겠습니까?",
            text: "삭제 시 복구가 불가능합니다.",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        });

        if (result.isConfirmed) {
			//댓글 삭제 요청을 보냄
            const response = await axios.post(`${cPath}/rest/issue/cmtdel/${issueIdx}`, {
                issueCommentId: commentId,
            });

            console.log('댓글 삭제 성공:', response.data);

            // 삭제 성공 후 메시지 표시
            await Swal.fire({
                title: "삭제되었습니다.",
                text: "댓글 삭제 완료.",
                icon: "success"
            });

            // UI를 업데이트
			loadComments(issueIdx);
        }
    } catch (error) {
        console.error('댓글 삭제 에러:', error);
        await Swal.fire({
            icon: "error",
            title: "실패하였습니다.",
            text: "관리자에게 문의 바랍니다.",
        });
    }
}


const issueListClick = () => issueList.addEventListener("click", (e) => {
    const target = e.target.closest(".d-flex.align-items-center");
    if (target) {
         issueIdx = target.querySelector(".issue-list").dataset.issueIdx;
		showEditor.style.display = "block";
		CmtH4.style.display = "block";
		editor_area.style.display = "none";
		editorLength.style.display = "none";
//		quill.root.innerHTML = "";
        loadIssueData(issueIdx)
            .then((subIssueIdx) => {
                return loadComments(issueIdx)
                    .then(() => loadSubIssue(subIssueIdx));
            })
            .catch((error) => {
                console.error("에러:", error);
            });
    }
});

	$(document).ready(function() {
    $('.chat-contact-list-item').on('click', function() {
        // 클릭된 요소의 정보 가져오기
        const issueIdx = $(this).find('.issue-list').data('issue-idx');
        const issueName = $(this).find('.issue-list').text();

        // 콘솔에 정보 로그
        console.log('클릭된 이슈 인덱스:', issueIdx);
        console.log('클릭된 이슈 이름:', issueName);

        // 여기서 원하는 동작 추가 가능 (예: 모달 열기, 세부정보 표시 등)
    });
});
// 하위 이슈 버튼 클릭 시 화면에 보이기 및 리스트 로드
const showDownIssueList = () => downIssue.addEventListener("click", () => {
	downIssue_area.style.display = "block";
	loadUpIssueList();
});




document.addEventListener("DOMContentLoaded", ()=>{
	const limeButton = document.getElementById('progress-status-lime');
	const blueButton = document.getElementById('progress-status-blue');
	const redButton = document.getElementById('progress-status-red');
	const defaultButton = document.getElementById('progress-status-default');
	const searchInput = document.querySelector('.chat-search-input');
	let debounceTimer;
	$.datepicker.setDefaults({
    closeText: "닫기",
    currentText: "오늘",
    prevText: '이전 달',
    nextText: '다음 달',
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNames: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesShort: ['일', '월', '화', '수', '목', '금', '토'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    weekHeader: "주",
    yearSuffix: '년'
    });    
	//두개의 날짜를 선택하는 datepick 함수
	 $( function() {
    var dateFormat = "mm/dd/yy",
      from = $( "#startPicker" )
        .datepicker({
          defaultDate: "+1w",
          changeMonth: true,
          numberOfMonths: 1
        })
        .on( "change", function() {
          to.datepicker( "option", "minDate", getDate( this ));
		  console.log("startDate:",getDate( this ));
        }),
      to = $( "#deadPicker" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        numberOfMonths: 1
      })
      .on( "change", function() {
        from.datepicker( "option", "maxDate", getDate( this ) );
		  console.log("deadDate:",getDate( this ));
      });
 
    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( dateFormat, element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }
  } );
	searchInput.addEventListener('input', function() {
        // 디바운싱: 입력 후 300ms 이내에 호출되면 이전 요청 취소
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
			const searchTerm = searchInput.value.trim();
			if (searchTerm === '') {
				// 검색어가 비어있을 때
				boardList();  // 파라미터 없이 호출
			} else {
				// 검색어가 있을 때
				boardList(null,searchTerm);  // 검색어를 파라미터로 전달
			}
        }, 300); // 300ms 디바운스 시간
    });

	// 버튼 클릭 이벤트 리스너 등록
	limeButton.addEventListener('click', () => boardList('Completed'));
	blueButton.addEventListener('click', () => boardList('In Progress'));
	redButton.addEventListener('click', () => boardList('Not Started'));


	defaultButton.addEventListener('click', () => boardList());
	 const quill = new Quill('#editor', {
        theme: 'snow'
    });

    var quillhtml = ''; // quillhtml을 상위 스코프에 선언
	const editorLength = document.getElementById('editorLength');
    quill.on('text-change', () => {
        console.log('Text change!');
        quillhtml = quill.root.innerHTML; // quillhtml의 값을 업데이트
		const length = quill.getLength();
		editorLength.innerHTML = `<p>(${length}/150)</p>`;
		if (149 < length){
						quill.deleteText(150, 160 );
						const Toast = Swal.mixin({
							toast: true,
							position: "bottom-end",
							showConfirmButton: false,
							timer: 1500,
							timerProgressBar: true,
							didOpen: (toast) => {
								toast.onmouseenter = Swal.stopTimer;
								toast.onmouseleave = Swal.resumeTimer;
							}
						});

			Toast.fire({
				icon: "warning",
				title: '댓글 150자 초과'
			});
					}
 	console.log('quillhtml:',quillhtml);
    });

    CmtSaveBtnEvent();

    function CmtSaveBtnEvent() {
        document.getElementById('CmtSavebtn').addEventListener('click', () => {
            console.log('quillhtml:', quillhtml);
            setupSaveCommentButton(quillhtml, loginId);
        });
    }

    function saveComment(issueIdx, quillhtml, loginId) {
        return axios.post(`${cPath}/rest/issue/cmt/${issueIdx}`, {
            issueCmtContent: quillhtml,
            issueCmtWriter: loginId
        }).then(function (response) {
            console.log("cmtSave:", response.data);
            showToast("댓글 저장됨.");
            editor_area.style.display = "none";
            showEditor.style.display = "block";
            CmtH4.style.display = "block";
            quill.root.innerHTML = "";
			editorLength.style.display = "none";
            // 댓글 저장 후 새로 고침
            loadComments(issueIdx);
        }).catch(function (error) {
            console.error("댓글 저장 에러:", error);
        });
    }

    function setupSaveCommentButton(quillhtml, loginId) {
        saveComment(issueIdx, quillhtml, loginId);
    }


	boardList(); //이슈의 목록을 출력

	projectMemberList(); //프로젝트에 속한 멤버를 조회


	const alarmChkDiv = document.querySelector('#alarmChk');
	const chkIsseuIdx = alarmChkDiv.dataset.alarmChk;
	issueListClick(); //클릭한 이슈 데이터를 삽입


	loadUpIssueList(); //하위 이슈를 불러온다

	contentClick(); //이슈 내용을 수정할 수 있게 스타일 변경

	issueContentModify(); //이슈 내용 수정 비동기 요청

	issueContentPushEnter(); //내용 수정시 enter 입력시 이벤트 발생

	issueTitleClickEvent(); //이슈 타이틀 클릭시 수정가능하게 변경

	IssueTitleUpdate(); //이슈 타이틀 업데이트 비동기 요청

	IssueTitleClickEnter(); //이슈 타일 Enter클릭시 포커스를 잃음

	issueContentPushEnter(); //이슈 타이블 엔터 클릭 이벤트

	showDownIssueList(); //하위이슈 추가 버튼 클릭시 화면에 보이기

	modalOpenClickEvent(); //이슈 생성하기 버튼 클릭시 모달 이벤트

	downIssueChangeEvent(); //하위 이슈 선택시 수정 이벤트

	issueOfficerChangeEvnet(); //이슈 담당자를 변경 비동기 요청 이벤트

	issuePriorityChangeEvnet(); //이슈 우선순위를 변경 비동기 요청 이벤트

	issueProgressChangeEvnet(); //이슈 진행도를 변경 비동기 요청 이벤트

	cmtAreaClickfindId(); //클릭한 댓글 id를 찾아서 삭제 모듈

	var contact_name = document.querySelector('#issue-list');
	console.log("contact_name", contact_name);
	setTimeout(() => {
		console.log("Delayed for 1 second.");
	}, "500");
	contact_name.click();

	const issueDOMClick = document.querySelector('#issue-list');

	issueDOMClick.addEventListener("click", (event) => {
		console.log("target", event.target);
	});

	console.log("issueDOMClick:", issueDOMClick);
 	var firstATag
	const observer = new MutationObserver((mutationsList) => {
    for (let mutation of mutationsList) {
      if (mutation.type === 'childList') {
		if (chkIsseuIdx) {
		firstATag = issueDOMClick.querySelector(`#target_${chkIsseuIdx}`);
		}else{
        firstATag = issueDOMClick.querySelector('a.d-flex.align-items-center');
		}
        if (firstATag) {
          console.log("firstATag:", firstATag);
          firstATag.click();
          observer.disconnect(); // 첫 번째 요소 클릭 후 옵저버 종료
          break;
        }
      }
    }
  });

  // 옵저버 설정
  observer.observe(issueDOMClick, { childList: true, subtree: true });


    $(document).ready(function() {
        $('.select2').select2({
            placeholder: "선택하세요",
            allowClear: true
        });
    });

	const issueTagNameInput$ = $("select[name='issueTagName']");
	//이슈 태그 변경 이벤트
	issueTagNameInput$.on('change', function() {
		const selectedTagName = issueTagNameInput$.val();

		try {
			const response = $.ajax({
				url: `${cPath}/rest/issue/tag/${issueIdx}`,
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({ issueTagName: selectedTagName }),
			});

			console.log('issue tag:', response);
			showToast("이슈 태그가 변경되었습니다.");
			// 성공 처리 로직 추가
		} catch (error) {
			console.error('issue tag error:', error);
			// 에러 처리 로직 추가
		}
	});



	//modal date 수정
	const issueCreationModal = document.querySelector("input[name='issueDate']");
	const issueDeadlineModal = document.querySelector("input[name='issueDeadline']");
	function ModalDates() {
		const start = new Date(issueCreationModal.value);
		const end = new Date(issueDeadlineModal.value);

		if (start > end) {
			Swal.fire({
				icon: "error",
				title: "경고",
				text: "이슈 시작기한은 마감기한보다 이후일 수 없습니다.",
			});
			issueCreationModal.value = ""; // 시작기한 필드 초기화
		} else {
		}
	}

	//이슈 시작기한이 마감기한보다 높을수 없도록 설정.
	const issueCreation = document.querySelector("input[name='issueCreation']");
	const issueDead = document.querySelector("input[name='issueDead']");
	issueCreationModal.addEventListener("change", ModalDates);
	issueDeadlineModal.addEventListener("change", ModalDates);

	function validateDates() {
		const startDate = new Date(issueCreation.value);
		const endDate = new Date(issueDead.value);

		if (startDate > endDate) {
			Swal.fire({
				icon: "error",
				title: "경고",
				text: "이슈 시작기한은 마감기한보다 이후일 수 없습니다.",
			});
			issueCreation.value = ""; // 시작기한 필드 초기화
		} else {
			// 유효한 날짜일 경우 서버로 요청 전송
			sendDatesToServer();
		}
	}


	function sendDatesToServer() {
		const startDate = issueCreation.value;
		const endDate = issueDead.value;

		// axios 요청 보내기 (시작기한)
		if (startDate) {
			axios.post(`${cPath}/rest/issue/startDate/${issueIdx}`, {
				issueDate: startDate
			})
				.then(response => {
					console.log("startDate:", response.data);
					showToast("이슈 시작기한이 저장되었습니다.");
				})
				.catch(error => {
					console.error("시작 날짜:", error);
				});
		}

		// axios 요청 보내기 (마감기한)
		if (endDate) {
			axios.post(`${cPath}/rest/issue/deadline/${issueIdx}`, {
				issueDeadline: endDate
			})
				.then(response => {
					console.log("endDate:", response.data);
					showToast("이슈 마감기한이 저장되었습니다.");
				})
				.catch(error => {
					console.error("마감 날짜:", error);
				});
		}
	}

	// 날짜 변경 이벤트 리스너 등록
	issueCreation.addEventListener("change", validateDates);
	issueDead.addEventListener("change", validateDates);



	document.querySelectorAll('.chat-contact-list-item').forEach(function(item) {
		item.addEventListener('click', function() {
			// 모든 리스트 아이템에서 active 클래스를 제거
			document.querySelectorAll('.chat-contact-list-item').forEach(function(el) {
				el.classList.remove('active');
			});
			// 클릭한 리스트 아이템에 active 클래스 추가
			this.classList.add('active');
		});
	});



//댓글 작성하기 버튼 클릭시
showEditor.addEventListener('click', function() {
	showEditor.style.display = "none";
	CmtH4.style.display = "none";
	editor_area.style.display = 'block';
	editorLength.style.display = "block";

	// 댓글 취소버튼 클릭시 이벤트
	document.getElementById('CmtCanclebtn').addEventListener('click', () => {
		showEditor.style.display = "block";
		CmtH4.style.display = "block";
		editor_area.style.display = "none";
		editorLength.style.display = "none";
		quill.root.innerHTML = "";
	});


});

document.getElementById('insertAutoModalBtn').addEventListener('click', function() {

  // 이슈 제목 자동완성
  document.querySelector('input[name="issueTitleModal"]').value = '데이터 수집 및 전처리 문제 해결';

  // 이슈 내용 자동완성
  document.querySelector('textArea[name="issueContentModal"]').value = '다양한 소스에서 수집된 데이터 간 불일치 문제를 해결하기 위해 전처리 파이프라인을 개선해 주세요. 특히 결측치 및 중복 데이터를 처리하는 알고리즘이 필요합니다.';

  // 이슈 담당자 자동선택
  let issueOfficerSelect = document.querySelector('select[name="issueOfficer"]');
  issueOfficerSelect.value = issueOfficerSelect.options[1].value; // 첫 번째 옵션 선택

  // 이슈 시작 날짜 자동완성
  document.querySelector('input[name="issueDate"]').value = '2024-09-18';

  // 이슈 종료 날짜 자동완성
  document.querySelector('input[name="issueDeadline"]').value = '2024-09-25';

  // 상위 이슈 선택 자동완성
  let subIssueSelect = document.querySelector('select[name="subIssueIdx"]');
  subIssueSelect.value = subIssueSelect.options[1].value; // 첫 번째 옵션 선택

  // 이슈 우선 순위 자동선택
  let prioritySelect = document.querySelector('select[name="issuePriorityNameModal"]');
  prioritySelect.value = 'High'; // 높음 선택

  // 태그 자동선택
  let tagSelect = document.querySelector('select[name="issueTagModal"]');
  tagSelect.value = 'bug'; // 버그 선택
});


});
