let myModal = null;
let exampleModal$ = null;
let memberId = null;
let href = null;
let page = 1;
let groupId = null;
const quitBtn = document.querySelector("#quitBtn");
const memListArea = document.querySelector('.memListArea');
const pagingArea = document.querySelector('.paging-area');
const cPath = document.body.dataset.contextPath;

pagingArea.addEventListener("click", function(e){
    console.log("clicked1!!!!!");
    e.preventDefault();
    const target = e.target.closest('.page-link'); // page-link를 클릭했는지 확인
    if (target) {
	    e.preventDefault(); // 기본 동작 막기
        page = target.getAttribute('data-page'); // data-page 속성에서 페이지 번호 가져오기
        if (!isNaN(page)) {
            console.log("클릭한 페이지 번호:", page);
            fetchMemberData(groupId, page);
        } else {
            console.log("페이지 번호가 유효하지 않습니다.");
        }
    }	
});

function reloading(){
	   location.reload();
}

function quitBtnClick() {
	const confirmQuit = confirm("해당 회원을 탈퇴로 변경하시겠습니까?");
    if (confirmQuit) {
         var data = {
             memberId : memberId,
             memberDelyn : 'Y'
         };
         fetch(href, {
             method: 'PUT',
             headers: {
                 'Content-Type': 'application/json',
             },
             body: JSON.stringify(data)
         })
         .then(response => response.json())
         .then(data => {
             console.log(data);
             if (data.success) {
                 alert('탈퇴 상태로 정상 변경되었습니다.');
                 refreshModalContent();
                 reloading();
             } else {
                 alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
             }
         })
         .catch((error) => {
             console.error('Error:', error);
         });
   }else{
    	console.log("탈퇴 변경이 취소되었습니다.");
   }
}
            
//CRUD후 모달 리로드 START
function refreshModalContent() {
	console.log(href);
    $.ajax({
        url: href,
        method: "GET",
        dataType: "json",
        success: function(data) {
        	console.log(data.memberDelyn);
            exampleModal$.find("#detailUserName").val(data.memberName);
            exampleModal$.find("#detailUserId").val(data.memberId);
            exampleModal$.find("#detailUserEmail").val(data.memberEmail);
            exampleModal$.find("#detailUserRegDt").val(data.memberJoinDate);
            exampleModal$.find("#detailUserTel").val(data.memberTel);
            exampleModal$.find("#detailUserGroup").val(data.groupManagementVO.groupName);
            exampleModal$.find("#detailUserAns").val(data.memberAuthResponse);
            if (data.memberRole == "ROLE_GROUPADMIN") {
                exampleModal$.find("#userAdmin").show();
                exampleModal$.find("#userMember").hide();
            } else {
                exampleModal$.find("#userMember").show();
                exampleModal$.find("#userAdmin").hide();
            }
            if(!data.groupManagementVO.groupName || data.groupManagementVO.groupName === 'null'){
	            exampleModal$.find("#quitBtn").show();
            }
            if (data.groupManagementVO.groupName || data.memberDelyn === 'Y'){
            	 exampleModal$.find("#quitBtn")[0].setAttribute("style", "display: none !important");
            }
            myModal.show();
        },
        error: function(jqXHR, errorStatus, errorText) {
            console.log("AJAX Error Status:", errorStatus);
            console.log("AJAX Error Text:", errorText);
            console.log("AJAX Full Response:", jqXHR.responseText);
        }
    });
}


// 멤버 데이터 요청 함수
function fetchMemberData(groupId, page) {
    axios({
        url: `${cPath}/saMember/group/${groupId}?page=${page}`, // 그룹 ID와 페이지 번호 함께 전송
        method: "get",
        headers: {
            "Content-Type": "application/json"
        }
    })
    .then(resp => {
        console.log("응답입니다", resp.data);
        let memberListByGroup = resp.data.target;
        let html = '';

        if (Array.isArray(memberListByGroup) && memberListByGroup.length > 0) {
            memberListByGroup.forEach(member => {
                let memDetailHref = `${cPath}/saMember/${member.memberId}`;
                let joinDate = new Date(member.memberJoinDate);
                let formattedDate = `${joinDate.getFullYear()}-${String(joinDate.getMonth() + 1).padStart(2, '0')}-${String(joinDate.getDate()).padStart(2, '0')}`;
        
                let badge1 = member.groupManagementVO.groupName ? `<span class="fw-medium">${member.groupManagementVO.groupName}</span>` : '<span class="fw-medium">무소속</span>';
                let badge2 = member.memberRole === 'ROLE_GROUPADMIN' ? '<span class="badge bg-label-success me-1">관리자</span>' : '<span class="badge bg-label-secondary me-1">멤버</span>';
                let badge3 = member.memberDelyn !== 'Y' ? '<span class="badge bg-label-primary me-1">Active</span>' : '<span class="badge bg-label-danger me-1">Quit</span>';

                html += `
                    <tr>
                        <td><a class="fw-medium sysadmin-list purpleColor" href="${memDetailHref}" data-bs-toggle="modal" data-bs-target="#detailUser">${member.memberId}</a></td>
                        <td><a class="fw-medium">${member.memberName}</a></td>
                        <td>${badge1}</td>
                        <td class="fw-medium">${formattedDate}</td>
                        <td>${badge2}</td>
                        <td>${badge3}</td>
                    </tr>
                `;
            });
        } else {
            html += `
                <tr>
                    <td class="mt-2" colspan="6" style="text-align:center; padding-top:20px;">
                        조회된 데이터가 없습니다.
                    </td>
                </tr>
            `;
        }
        memListArea.innerHTML = html;
        pagingArea.innerHTML = resp.data.pagingHTML;

        // 새로 로드된 데이터에 이벤트 핸들러 재설정
        setupModalEventHandlers();
    })
    .catch(err => {
        console.error("Error fetching data:", err);
    });
}

// 모달 이벤트 핸들러 설정
function setupModalEventHandlers() {
    document.querySelectorAll(".sysadmin-list").forEach(anchor => {
        anchor.addEventListener("click", (e) => {
            e.preventDefault();
            const a = e.target.closest("a");
            href = a.getAttribute("href");
            memberId = href ? href.split('/').pop() : null;
            refreshModalContent();
        });
    });
}

// 초기 DOMContentLoaded 이벤트
document.addEventListener("DOMContentLoaded", () => {
    exampleModal$ = $("#detailUser").on("hidden.bs.modal", ({target}) => {});
    myModal = new bootstrap.Modal(exampleModal$[0]);

    setupModalEventHandlers();

    // 그룹 필터 클릭 시
    document.querySelectorAll('.dropdown-item').forEach(item => {
        item.addEventListener('click', function(event) {
            const closestDropdownItem = event.target.closest('.dropdown-item');
            if (closestDropdownItem) {
                groupId = closestDropdownItem.getAttribute('data-id-value'); // 그룹 ID 저장
                console.log('Clicked group ID:', groupId);
                page = 1;
                fetchMemberData(groupId, page); // 첫 페이지에서 데이터 로드
            }
        });
    });
});