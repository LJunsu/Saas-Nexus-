const gitdozicPath = document.body.dataset.contextPath;

function autoComplete() {
    const creationReasonElement = document.querySelector('.editable.creationReason');
    const projectNameElement = document.querySelector('.editable.projectName');
    const dueDateElement = document.querySelector('.editable.completionDate');

    // '작성사유' 필드에 텍스트 입력
    if (creationReasonElement) {
        creationReasonElement.textContent = '대기업 마케팅 홍보 프로젝트';
    }

    // '프로젝트명' 필드에 텍스트 입력
    if (projectNameElement) {
        projectNameElement.textContent = '대기업 마케팅 홍보 프로젝트';
    }

    // '프로젝트 예상 마감일' 필드에 날짜 입력
    if (dueDateElement) {
        dueDateElement.textContent = '2024-09-23';
    }
}

document.addEventListener('DOMContentLoaded', function() {
	console.log(groupId);
	const submitButton = document.getElementById('submitButton');
    const form = document.getElementById('projectForm');
    let href = `${gitdozicPath}/${groupId}/ea/build`;
	const contextPath = document.body.dataset.contextPath;
    let uploadHref = `${gitdozicPath}/gcp/upload`;
    let beforeHref = `${gitdozicPath}/${groupId}/ea`;
	let url = '';
	let dueDate = document.getElementById('due-date');
	

//  기입한 문서 제출 START
    submitButton.addEventListener('click', function() {
	  console.log(uploadHref);
	
	  document.querySelectorAll('.btnDummy').forEach(cell => {
	  	cell.style.display = 'none';
	  });
	

	  if(confirm("결재를 제출하시겠습니까? 제출후에는 수정이 불가능합니다.")){
		
         let creationReason = document.querySelector(".creationReason");
         let completionDate = document.querySelector(".completionDate");
         let projectName = document.querySelector(".projectName");
         let totalParticipants = document.querySelector(".totalParticipants");
         let eaReceiver = document.querySelector(".eaReceiver");

         html2canvas(document.querySelector('.container')).then(function(canvas) {
             console.log("캔버스를 성공적으로 캡처했습니다.");
             let projectNames = projectName.textContent;
			 
			 let currentTime = new Date();
			 let formattedTime = currentTime.getFullYear() + "-" 
			                             + ("0" + (currentTime.getMonth() + 1)).slice(-2) + "-"
			                             + ("0" + currentTime.getDate()).slice(-2) + "_"
			                             + ("0" + currentTime.getHours()).slice(-2) + "-"
			                             + ("0" + currentTime.getMinutes()).slice(-2) + "-"
			                             + ("0" + currentTime.getSeconds()).slice(-2);
										 
         
             // 캔버스를 PNG 형식의 Blob 객체로 변환
             canvas.toBlob(function(blob) {
                 if (!blob) {
                     console.error("캔버스에서 Blob을 생성하는 데 실패했습니다.");
                     return;
                 }
                 console.log("Blob이 성공적으로 생성되었습니다.");
                 let fileName = projectNames + '_' + formattedTime + '.png';
         
                 // 다운로드를 위한 임시 링크 생성
                 let link = document.createElement('a');
                 link.href = URL.createObjectURL(blob);
                 link.download = fileName;
//                 link.click();
         
                 // 서버로 전송할 폼 데이터 준비
                 let formData = new FormData();
                 formData.append('file', blob, fileName);
                 formData.append('folderPath', 'pictures/');
         
                 // Axios를 사용해 파일을 서버로 업로드
                 axios.post(uploadHref, formData)
                 .then(response => {
                     console.log('파일이 성공적으로 업로드되었습니다:', response.data);
//                     alert('파일이 성공적으로 업로드되었습니다');
         			 url = response.data;

                     // 파일업로드가 되면 전자문서 insert실행 start
                     var partsList = [];
                     
                     document.querySelectorAll(".partArray").forEach((tr) => {
            	           console.log("사원 아이디는", tr.dataset.memberId);  // 확인용 콘솔 출력
                           let part = {
                               memberId: tr.dataset.memberId,  // 사원 아이디
                               participantName: tr.dataset.memberName,  // 새 사원
                               participantEmail: tr.dataset.participantEmail, // 사원 이메일
                               participantRole: tr.querySelectorAll("td")[3].textContent.trim(),  // 새 역할
                               participantRemark: tr.querySelectorAll("td")[4].textContent.trim() // 비고
                           };
                           partsList.push(part);
                       });
                     
                     console.log(partsList);
                     
                     var data = {
                         creationReason: creationReason.textContent,
                         completionDate: completionDate.textContent,
                         projectName: projectName.textContent,
                         totalParticipants: totalParticipants.textContent,
                         eaReceiver: selectedMemberId,
                         partsList: partsList,
                         fileUrl: url
                     };
                     fetch(href, {
                         method: 'POST',
                         headers: {
                             'Content-Type': 'application/json' // 올바른 Content-Type 설정
                         },
                         body: JSON.stringify(data)
                     })
                     .then(response => response.json())
                     .then(data => {
                         console.log(data);
                         if (data.success) {
                             alert('프로젝트 승인문서가 전달되었습니다.');
                             window.location.replace(beforeHref);
                         } else {
                             alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                         }
                     })
                     .catch((error) => {
                         console.error('Error:', error);
                     });
                     // 파일업로드가 되면 전자문서 insert실행 END
                 })
                 .catch(error => {
                     console.error('파일 업로드에 실패했습니다:', error);
                 });
             }, 'image/png'); // PNG 형식을 명시
         });
      }else{
	      return;
      }

    });
//  기입한 문서 제출 END
	
	
	
	
   //	날짜 today 자동설정 + format 변경 START
   let currentDate = new Date().toLocaleDateString('ko-KR', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    }).replace(/\./g, '-').replace(/\s+/g, '').trim(); // 공백 제거

    // 불필요한 하이픈이 있으면 제거
    currentDate = currentDate.endsWith('-') ? currentDate.slice(0, -1) : currentDate;
    document.querySelectorAll('#creation-date').forEach(element => {
        element.textContent = currentDate;
    });

    const getFormatDate = (date) => {
        const year = date.getFullYear();
        const month = (1 + date.getMonth());
        const day = date.getDate();
        return `${year}년 ${month}월 ${day}일 `;
    }
    const FormatDate = getFormatDate(new Date());
    let gettingFormatDate = document.querySelector("#gettingFormatDate");
    gettingFormatDate.textContent = FormatDate;
    dueDate.innerHTML = currentDate;
//	날짜 today 자동설정 + format 변경 END


//	수신자 선택시 승인자란 이름 자동 변경 START
    const selectElement = document.querySelector('#issuePriorityName');
    const approverNameElement = document.querySelector('.stamp-box div:nth-child(2) div');
    selectElement.addEventListener('change', function() {
        const selectedOptionText = selectElement.options[selectElement.selectedIndex].text;
        const nameOnly = selectedOptionText.replace(/\[.*\]\s*/, ''); // [관리자] 또는 [일반]을 제거
        approverNameElement.textContent = nameOnly;
    });
//	수신자 선택시 승인자란 이름 자동 변경 END

//	사원 컬럼추가 및 삭제 START
    const tableBody = document.querySelector("#addTableForm tbody");
    const addBtn = document.querySelector("#addBtn");
    const delBtn = document.querySelector("#delBtn");
    let totalNum = document.querySelector("#totalNum");
    let nextIndex = 2;    // 순번 추적 변수

    //  사원명 클릭시 이메일 자동부여 START
    tableBody.addEventListener('change', function(e) {
        if (e.target && e.target.matches('select.form-control')) {
            var selectedOption = e.target.options[e.target.selectedIndex];
            var emailField = e.target.closest('tr').querySelector('#emailField');
            var tr = e.target.closest('tr');
            
            if (selectedOption.value) {
                var email = selectedOption.getAttribute('data-email');
                emailField.textContent = email || '이메일 정보가 없습니다.';
                tr.dataset.memberId = selectedOption.value;
                const nameOnly = selectedOption.textContent.replace(/\[.*\]\s*/, '');
                tr.dataset.memberName = nameOnly;
                tr.dataset.participantEmail = email;
            } else {
                emailField.textContent = '사원 이메일';
                tr.removeAttribute('data-member-id'); 
                tr.removeAttribute('data-member-name'); 
                tr.removeAttribute('data-participant-email');
            }
        }
    });
   //  사원명 클릭시 이메일 자동부여 END

    addBtn.addEventListener('click', function(e) {
        e.preventDefault(); 
        console.log("addBtn누름");
        const newRow = document.createElement('tr');
        newRow.classList.add('partArray');
        let options = '<option>선택</option>';
        memberList.forEach(mem => {
            if (mem.memberRole === 'ROLE_GROUPADMIN') {
                options += `<option value="${mem.memberId}" data-email="${mem.memberEmail}">[관리자] ${mem.memberName}</option>`;
            } else if (mem.memberRole === 'ROLE_USER') {
                options += `<option value="${mem.memberId}" data-email="${mem.memberEmail}">[일반] ${mem.memberName}</option>`;
            }
        });
        
        newRow.innerHTML = `
            <td>${nextIndex++}</td> <!-- 순번 자동 증가 -->
            <td data-original="">
                <select name="" class="form-control" id="memberSelect">
                    ${options}
                </select>
            </td>
            <td data-original="" id="emailField">사원 이메일</td>
            <td class="editable" data-original="">새 역할</td>
            <td class="editable" data-original="">비고</td>
        `;
        tableBody.appendChild(newRow);
        totalNum.innerHTML = nextIndex-1;
        newRow.querySelectorAll('.editable').forEach(cell => {
            cell.addEventListener('click', function() {
                makeEditable(this);
            });
        });
    });

    delBtn.addEventListener('click', function(e) {
        e.preventDefault();
        const rows = tableBody.querySelectorAll('tr');
        if (rows.length > 1) {
            tableBody.removeChild(rows[rows.length - 1]);
            nextIndex--;
            totalNum.innerHTML = nextIndex - 1;
        }
    });
//	사원 컬럼추가 및 삭제 END
	
	
//  editable(텍스트)인경우 START
    function makeEditable(cell) {
        if (cell.classList.contains('editing')) return;

        let originalContent = cell.textContent;
        cell.classList.add('editing');
        let input = document.createElement('input');
        input.type = 'text';
        input.value = originalContent;
        input.style.width = '100%';
        input.style.boxSizing = 'border-box';

        cell.innerHTML = '';
        cell.appendChild(input);
        input.focus();

        input.addEventListener('blur', function() {
            const newValue = input.value.trim();
            if (newValue) {
                cell.textContent = newValue;
                cell.dataset.original = newValue;

            } else {
                cell.textContent = originalContent;
            }
            cell.classList.remove('editing');
        });

        input.addEventListener('keydown', function(e) {
            if (e.key === 'Enter') {
                input.blur();
            }
        });
    }
//  editable(텍스트)인경우 END


//  editable(날짜)인경우 START
    document.querySelectorAll('.editable').forEach(cell => {
        cell.addEventListener('click', function() {
            if (this.id === 'creation-date') return;

            if (cell.classList.contains('editing')) return;

            if (this.id === 'due-date') {
	
                const input = document.createElement('input');
                input.type = 'date';
                input.min = currentDate;
                input.value = this.textContent;
                input.style.width = '100%';
                input.style.boxSizing = 'border-box';

                this.classList.add('editing');
                this.innerHTML = '';
                this.appendChild(input);
                input.focus();

                input.addEventListener('blur', () => {
                    if (input.value.trim() === '') {
                        this.textContent = this.dataset.original;
                    } else {
                        this.textContent = input.value;
                        this.dataset.original = input.value;
                    }
                    this.classList.remove('editing');
                });

                input.addEventListener('keydown', (e) => {
                    if (e.key === 'Enter') {
                        input.blur();
                    }
                });
            } else {
                makeEditable(this);
            }
        });
    });
//  editable(날짜)인경우 END

});
