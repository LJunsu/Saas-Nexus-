/**
 *  REST 관련 모든 UI 라이브러리의 model 은 동일함.
 */

// formData 를 Json으로 변환

const userDiv = document.querySelector("#userName");
console.log(userDiv);
const userName = userDiv.dataset.userName;
console.log(userName);

const formDataToJSON = (form) => {
	let formData = new FormData(form);
	let data = {};
	for (let name of formData.keys()) {
		let value = formData.get(name);
		data[name] = value;
	}
	return JSON.stringify(data);
};

// command object의 프로퍼티 값을 form 의 입력 태그(type='password' 제외)의 값으로 초기화
const objectToFormData = (obj, form) => {
	Object.keys(obj)
		.map(p => form[p])
		.filter(i => i && i.type !== "password")
		.forEach(i => i.value = obj[i.name]);
};

// 댓글 작성시 비동기로 작성된 댓글 생성
const singleAnswerUI = (noticeAnswer) => {
	let answerList = `
       <tr id="boardcmtid" data-board-cmt-id="${noticeAnswer.boardCmtId}">
                <td class="bg-light text-center" style="width: 15%;">${noticeAnswer.memberName}(${noticeAnswer.memberId})</td>
                <td>
                <div class="answer-content" style="margin-bottom: 10px; padding: 10px; background-color: #f8f9fa;">${noticeAnswer.cmtContent}</div>
                <div class="text-muted small" style="padding: 10px; background-color: #e9ecef;">작성일 : ${noticeAnswer.cmtCreationDate}</div>
                </td>
    `;

	let btnArea = document.querySelector("#btnArea");
	if (noticeAnswer.memberId === connetctionMemberId) {
		answerList += `
        <td class="text-end" id="btnArea" style="width: 10%; ">
            <button type="button" id="ansUpBtn"  class="btn btn-sm btn-outline-secondary edit-btn">수정</button>
            <button type="button" id="ansDelBtn" class="btn btn-sm btn-outline-danger delete-btn">삭제</button>
        </td>
        `;
	} else {
		answerList += `<td></td>`;
	}
	answerList += `</tr>`;

	return answerList;
};

// 댓글 삭제 버튼 리스너 등록
const removeButtonListeners = (boardId, memberId) => {
	document.querySelectorAll('.delete-btn').forEach(btn => {
		btn.addEventListener('click', async function() {
			const boardcmtid = this.closest('tr').dataset.boardCmtId;
			const boardCmtId = parseInt(boardcmtid);

			const data = {
				boardId: boardId,
				memberId: memberId,
				boardCmtId: boardCmtId
			};

			Swal.fire({
				title: '정말 삭제 하시겠습니까?',
				icon: 'warning',
				showCancelButton: true,
				confirmButtonText: '네, 삭제할게요!',
				cancelButtonText: '아니요, 취소할래요!',
				backdrop: `rgba(0, 0, 0, 0.4)`,
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			}).then(async (result) => {
				if (result.isConfirmed) {
					const delChk = await deleteAnswer(data);
					if (delChk.cnt > 0) {
						Swal.fire({
							title: '삭제 완료',
							text: '정상적으로 삭제되었습니다',
							icon: 'success',
							customClass: {
								popup: 'swal2-custom-zindex'
							}
						});
						this.closest('tr').remove();
					} else {
						Swal.fire({
							title: '오류 발생',
							text: delChk.errors,
							icon: 'error',
							customClass: {
								popup: 'swal2-custom-zindex'
							}
						});
					}
				}
			});
		});
	});
};

// 댓글 수정 버튼 리스너 등록
const addEditButtonListeners = (boardId, memberId) => {
	document.querySelectorAll('.edit-btn').forEach(btn => {
		btn.addEventListener('click', function() {
			const updateForm = document.createElement('form');
			const answerContentDiv = this.closest('tr').querySelector('.answer-content');
			const boardCmtId = this.closest('tr').dataset.boardCmtId;

			updateForm.action = `${detailURI}/${boardId}/${boardCmtId}/updateAns`;
			updateForm.method = 'put';

			const currentText = answerContentDiv.innerHTML;

			const inputField = document.createElement('input');
			inputField.style.width = '100%';
			inputField.style.height = '100px';
			inputField.type = 'text';
			inputField.name = 'cmtContent';
			inputField.value = currentText;
			inputField.className = 'form-control';

			const hiddenAnswerId = document.createElement('input');
			hiddenAnswerId.type = 'hidden';
			hiddenAnswerId.name = 'boardCmtId';
			hiddenAnswerId.value = parseInt(boardCmtId);

			const hiddenBoardId = document.createElement('input');
			hiddenBoardId.type = 'hidden';
			hiddenBoardId.name = 'boardId';
			hiddenBoardId.value = boardId;

			const hiddenMemberId = document.createElement('input');
			hiddenMemberId.type = 'hidden';
			hiddenMemberId.name = 'memberId';
			hiddenMemberId.value = memberId;

			updateForm.appendChild(inputField);
			updateForm.appendChild(hiddenAnswerId);
			updateForm.appendChild(hiddenBoardId);
			updateForm.appendChild(hiddenMemberId);
			answerContentDiv.replaceWith(updateForm);

			const submitButton = document.createElement('button');
			submitButton.type = 'submit';
			submitButton.textContent = '수정 하기';
			submitButton.className = 'btn btn-primary';

			updateForm.appendChild(submitButton);

			updateForm.addEventListener('submit', async function(event) {
				event.preventDefault();
				const updateChk = await updateAnswer(updateForm);

				Swal.fire({
					title: '수정하시겠습니까?',
					icon: 'question',
					showCancelButton: true,
					confirmButtonText: '네, 수정할게요!',
					cancelButtonText: '아니요, 취소할래요!',
					backdrop: `rgba(0, 0, 0, 0.4)`,
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				}).then((result) => {
					if (result.isConfirmed) {
						if (updateChk.cnt > 0) {
							Swal.fire({
								title: '수정 완료',
								text: '정상적으로 수정 처리되었습니다',
								icon: 'success',
								customClass: {
									popup: 'swal2-custom-zindex'
								}
							});
							const newDiv = document.createElement('div');
							newDiv.className = 'answer-content';
							newDiv.textContent = inputField.value;
							newDiv.style.marginBottom = '10px';
							newDiv.style.padding = '10px';
							newDiv.style.backgroundColor = '#f8f9fa';
							inputField.replaceWith(newDiv);

							if (socket) {
								let msg = "수정";
								socket.send(msg);
							}
						} else {
							Swal.fire({
								title: '오류 발생',
								text: updateChk.errors,
								icon: 'error',
								customClass: {
									popup: 'swal2-custom-zindex'
								}
							});
						}
					} else {
						const newDiv = document.createElement('div');
						newDiv.className = 'answer-content';
						newDiv.textContent = currentText;
						newDiv.style.marginBottom = '10px';
						newDiv.style.padding = '10px';
						newDiv.style.backgroundColor = '#f8f9fa';
						inputField.replaceWith(newDiv);
					}
				});
				submitButton.remove();
			});
		});
	});
};
// 기본 설정 및 API 호출 관련 코드
const connetctionBase = document.querySelector("#myGridContainer").dataset.connectionBase;
const connetctionMemberId = document.querySelector("#myGrid").dataset.connectionMemid;
const contextPath = document.body.dataset.contextPath;
const groupId = document.querySelector("#groupIdDiv").dataset.groupdId;
const baseURI = `${contextPath}/group/noticeBoard/list.do`;
const detailURI = `${contextPath}/group/${groupId}/noticeBoard`;

// 글 목록 전체조회
const boardList = () => FetchUtils.fetchForJSON(`${detailURI}/list.do`);

// 글 상세 조회
const boardDetail = (boardId) => FetchUtils.fetchForJSON(`${detailURI}/${boardId}`);

// 상세 조회한 글 댓글
const answerList = (boardId) => FetchUtils.fetchForJSON(`${detailURI}/${boardId}/answerList`);

// 글 생성
const boardInsert = (form) => FetchUtils.fetchForJSON(`${detailURI}`, {
	method: form.method,
	headers: {
		"accept": "application/json"
	},
	body: new FormData(form)
});

// 글 수정
const boardUpdate = (form) => FetchUtils.fetchForJSON(form.action, {
	method: "post",
	headers: {
		"accept": "application/json"
	},
	body: new FormData(form)
});

// 글 삭제
const deleteBoard = (boardData) => FetchUtils.fetchForJSON(boardData.action, {
	method: "delete",
	headers: {
		"accept": "application/json",
		"content-type": "application/json"
	},
	body: JSON.stringify(boardData)
});

// 댓글 작성
const createAnswer = (form, boardId) => FetchUtils.fetchForJSON(`${detailURI}/${boardId}/answerAdd`, {
	method: "post",
	headers: {
		"accept": "application/json",
		"content-type": "application/json"
	},
	body: formDataToJSON(form)
});

// 댓글 수정
const updateAnswer = (form) => FetchUtils.fetchForJSON(form.action, {
	method: "PUT",
	headers: {
		"accept": "application/json",
		"content-type": "application/json"
	},
	body: formDataToJSON(form)
});

// 댓글 삭제
const deleteAnswer = (data) => FetchUtils.fetchForJSON(`${detailURI}/${data.boardId}/${data.boardCmtId}/deleteAns`, {
	method: "DELETE",
	headers: {
		"accept": "application/json"
	}
});

// 댓글 작성 컨트롤러
const boardAdd = async (e) => {
	e.preventDefault();
	const selBoard = gridApi.getSelectedRows()[0];
	const boardIdInput = document.querySelector("#ansAdd");

	boardIdInput.value = selBoard.boardId;

	// SweetAlert2로 "답글 작성" 확인 메시지
	const result = await Swal.fire({
		title: '답글을 작성하시겠습니까?',
		text: "네 버튼을 클릭 시 댓글이 작성됩니다!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '네, 작성할게요!',
		cancelButtonText: '취소'
	});

	if (result.isConfirmed) {
		let answerChk = await createAnswer(e.target, selBoard.boardId);

		if (answerChk.success) {
			// 답글 작성이 성공했을 때
			let answerL = await answerList(selBoard.boardId);
			if (Array.isArray(answerL) && answerL.length > 0) {
				answerArea.innerHTML = '';
				answerL.forEach(answer => {
					answerArea.innerHTML += singleAnswerUI(answer);
				});
				// 새로 추가된 댓글의 수정/삭제 버튼에 이벤트 리스너 등록
				addEditButtonListeners(selBoard.boardId, connetctionMemberId);
				removeButtonListeners(selBoard.boardId, connetctionMemberId);
			}

			// SweetAlert2로 답글 작성 성공 알림
			Swal.fire({
				title: '답글 작성 성공!',
				text: '새로운 답글이 성공적으로 작성되었습니다.',
				icon: 'success',
				customClass: {
					popup: 'swal2-custom-zindex'
				},
				timer: 3000,  // 자동으로 사라짐
				showConfirmButton: false
			});
		} else {
			// 답글 작성 실패 시
			Swal.fire({
				title: '오류 발생',
				text: answerChk.message ?? JSON.stringify(answerChk.errors),
				icon: 'error',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});
		}
	}

	// 새로운 답글 입력 필드 초기화
	let newComment = document.querySelector('#newComment');
	newComment.value = '';
};


// 글 목록 전체 조회 컨트롤러
const readBoardList = async () => {
	let list = await boardList();
	gridApi.setGridOption("rowData", list); // setGridOption을 사용하여 데이터를 설정
};

// 상세 조회 컨트롤러
const readBoard = async (e) => {
	
	let writerInput = document.querySelector('#memberId').value; // 작성자 ID
	


	let boardTitle = document.querySelector('#boardTitle'); // 제목
	let writerName = document.querySelector('#memberName'); // 작성자 이름
	let createInput = document.querySelector('#boardCreateDate'); // 작성일
	let groupInput = document.querySelector('#groupId'); // 그룹 ID
	let contentInput = document.querySelector('#boardContent'); // 내용
	let filduploadId = document.querySelector('#filduploadId'); // 파일 업로드 필드
	let uploadTr = document.querySelector('#uploadTr'); // 파일 업로드 행

	console.log(boardTitle);
	let gridApi = e.api;
	let selBoard = gridApi.getSelectedRows()[0]; // 내가 선택한 그 해당 게시글

	console.log(selBoard.memberName);
	console.log(selBoard.boardTitle);

	if (boardTitle) {
		boardTitle.value = selBoard.boardTitle;
	} else {
		console.error('boardTitle 요소를 찾을 수 없습니다.');
	}

	if (writerInput) {
		writerInput.value = selBoard.memberId;
	} else {
		console.error('writerInput 요소를 찾을 수 없습니다.');
	}

	if (writerName) {
		writerName.value = selBoard.memberName;
	} else {
		console.error('writerName 요소를 찾을 수 없습니다.');
	}

	if (createInput) {
		createInput.value = selBoard.boardCreateDate;
	} else {
		console.error('createInput 요소를 찾을 수 없습니다.');
	}

	if (groupInput) {
		groupInput.value = selBoard.groupId;
	} else {
		console.error('groupInput 요소를 찾을 수 없습니다.');
	}

	if (contentInput) {
		contentInput.value = selBoard.boardContent;
	} else {
		console.error('contentInput 요소를 찾을 수 없습니다.');
	}

	if (filduploadId) {
		filduploadId.value = '';
	} else {
		console.error('filduploadId 요소를 찾을 수 없습니다.');
	}

	let modalEl = document.getElementById(detailModalId);

	console.log(modalEl);

	let managerModal = bootstrap.Modal.getOrCreateInstance(modalEl);

	let boardOne = await boardDetail(selBoard.boardId);

	if (Array.isArray(boardOne.boardCreateDate)) {
		const [year, month, day, hour, minute, second] = boardOne.boardCreateDate;
		const formattedDate = new Date(year, month - 1, day, hour, minute, second).toLocaleString("ko-KR", {
			year: 'numeric',
			month: '2-digit',
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
			second: '2-digit'
		});
		boardOne.boardCreateDate = formattedDate;
	}

	document.getElementById('uploadedFilesList').innerHTML =
		boardOne.attachedFile?.fileDetails
			?.filter(f => f && f.originalFileName) // null 값 및 유효하지 않은 항목 필터링
			.map(
				f =>
					`<li><a href="${contextPath}/attached/Gnotice/${f.attachedFileId}/${f.attachedFileDetailId}" >${f.originalFileName}</a></li>`
			).join('') || "<li>첨부된 파일 없음</li>"; // 파일 목록이 없을 경우 빈 문자열 출력

	let answerL = await answerList(selBoard.boardId);

	const answerArea = document.querySelector("#answerArea");

	if (Array.isArray(answerL) && answerL.length > 0) {
		answerArea.innerHTML = '';
		answerL.forEach(answer => {
			answerArea.innerHTML += singleAnswerUI(answer);
		});
	} else {
		answerArea.innerHTML = "<tr><td colspan='3'>댓글이 없습니다.</td></tr>";
	}

	let form = modalEl.querySelector("form");
	form.action = `${detailURI}/${boardOne.boardId}`;
	objectToFormData(boardOne, form);

	gridApi.getRowNode(boardOne.boardId).updateData(boardOne);

	if (connetctionMemberId == boardOne.memberId) {
		managerModal.show();
	} else {
		if (boardTitle) {
			const titleDiv = document.createElement('div');
			titleDiv.textContent = boardTitle.value;
			titleDiv.className = boardTitle.className;
			titleDiv.id = boardTitle.id;
			boardTitle.parentNode.replaceChild(titleDiv, boardTitle);
		}

		// 작성자 이름 필드 변경
		if (writerName) {
			const memberNameDiv = document.createElement('div');
			memberNameDiv.textContent = writerName.value;
			memberNameDiv.className = writerName.className;
			memberNameDiv.id = writerName.id;
			writerName.parentNode.replaceChild(memberNameDiv, writerName);
		}

		// 작성일 필드 변경
		if (createInput) {
			const createDateDiv = document.createElement('div');
			createDateDiv.textContent = createInput.value;
			createDateDiv.className = createInput.className;
			createDateDiv.id = createInput.id;
			createInput.parentNode.replaceChild(createDateDiv, createInput);
		}

		// 그룹 ID 필드 변경
		if (groupInput) {
			const groupIdDiv = document.createElement('div');
			groupIdDiv.textContent = groupInput.value;
			groupIdDiv.className = groupInput.className;
			groupIdDiv.id = groupInput.id;
			groupInput.parentNode.replaceChild(groupIdDiv, groupInput);
		}

		// 내용 필드 변경
		if (contentInput) {
			const contentDiv = document.createElement('div');
			contentDiv.textContent = contentInput.value;
			contentDiv.className = contentInput.className;
			contentDiv.id = contentInput.id;
			contentInput.parentNode.replaceChild(contentDiv, contentInput);
		}

		if (uploadTr) {
			uploadTr.style.display = 'none';
		}

		managerModal.show();
	}

	addEditButtonListeners(selBoard.boardId, connetctionMemberId);
	removeButtonListeners(selBoard.boardId, connetctionMemberId);
};

// 모달이 닫힐 때 원래의 상태로 복원하는 함수
const resetModalContent = () => {
	// 제목 필드 복원
	const titleDiv = document.querySelector('#boardTitle');
	if (titleDiv && titleDiv.tagName === 'DIV') {
		const inputField = document.createElement('input');
		inputField.type = 'text';
		inputField.id = 'boardTitle';
		inputField.name = 'boardTitle';
		inputField.className = titleDiv.className;
		inputField.value = titleDiv.textContent;
		titleDiv.parentNode.replaceChild(inputField, titleDiv);
	}

	// 작성자 ID 필드 복원
	const memberIdDiv = document.querySelector('#memberId');
	if (memberIdDiv && memberIdDiv.tagName === 'DIV') {
		const inputField = document.createElement('input');
		inputField.type = 'hidden';
		inputField.id = 'memberId';
		inputField.name = 'memberId';
		inputField.className = memberIdDiv.className;
		inputField.value = memberIdDiv.textContent;
		memberIdDiv.parentNode.replaceChild(inputField, memberIdDiv);
	}

	// 작성자 이름 필드 복원
	const memberNameDiv = document.querySelector('#memberName');
	if (memberNameDiv && memberNameDiv.tagName === 'DIV') {
		const inputField = document.createElement('input');
		inputField.type = 'text';
		inputField.id = 'memberName';
		inputField.name = 'memberName';
		inputField.className = memberNameDiv.className;
		inputField.value = memberNameDiv.textContent;
		memberNameDiv.parentNode.replaceChild(inputField, memberNameDiv);
	}

	// 작성일 필드 복원
	const createDateDiv = document.querySelector('#boardCreateDate');
	if (createDateDiv && createDateDiv.tagName === 'DIV') {
		const inputField = document.createElement('input');
		inputField.type = 'text';
		inputField.id = 'boardCreateDate';
		inputField.name = 'boardCreateDate';
		inputField.className = createDateDiv.className;
		inputField.value = createDateDiv.textContent;
		createDateDiv.parentNode.replaceChild(inputField, createDateDiv);
	}

	// 그룹 ID 필드 복원
	const groupIdDiv = document.querySelector('#groupId');
	if (groupIdDiv && groupIdDiv.tagName === 'DIV') {
		const inputField = document.createElement('input');
		inputField.type = 'text';
		inputField.id = 'groupId';
		inputField.name = 'groupId';
		inputField.className = groupIdDiv.className;
		inputField.value = groupIdDiv.textContent;
		groupIdDiv.parentNode.replaceChild(inputField, groupIdDiv);
	}

	// 내용 필드 복원
	const contentDiv = document.querySelector('#boardContent');
	if (contentDiv && contentDiv.tagName === 'DIV') {
		const textareaField = document.createElement('textarea');
		textareaField.id = 'boardContent';
		textareaField.name = 'boardContent';
		textareaField.className = contentDiv.className;
		textareaField.textContent = contentDiv.textContent;
		contentDiv.parentNode.replaceChild(textareaField, contentDiv);
	}

	if (uploadTr) {
		uploadTr.style.display = 'table-row';
	}

	// th 태그의 class 복원
	const uploadTh = document.querySelector('#uploadTr th');
	if (uploadTh) {
		uploadTh.className = 'bg-light text-center';  // 원래 class 값으로 복원
	}

	const fileInput = document.querySelector('#uploadTr input[type="file"]');
	if (fileInput) {
		fileInput.name = 'attachedFile.fileDetails[0].uploadFile';  // 원래 class 값으로 복원
	}
};

// 글 수정 컨트롤
const modifyBoard = async (e) => {
	e.preventDefault();
	let form = e.target;

	// SweetAlert2로 "게시물 수정" 확인 메시지
	const result = await Swal.fire({
		title: '게시물을 수정하시겠습니까?',
		text: "네 버튼을 누르시면 저장됩니다!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '네, 수정할게요!',
		cancelButtonText: '취소'
	});

	if (result.isConfirmed) {
		// 사용자가 확인을 눌렀을 때만 수정 진행
		let modal = bootstrap.Modal.getInstance(`#${detailModalId}`);
		let rslt = await boardUpdate(form);

		if (rslt.success) {
			// 수정 성공 시
			gridApi.applyTransaction({ update: [rslt.target] });
			modal.hide();

			// SweetAlert2로 수정 성공 알림
			Swal.fire({
				title: '게시물 수정 성공!',
				text: `공지글이 성공적으로 수정되었습니다.`,
				icon: 'success',
				customClass: {
					popup: 'swal2-custom-zindex'
				},
				timer: 3000,  // 자동으로 사라짐
				showConfirmButton: false
			});
		} else {
			// 수정 실패 시
			Swal.fire({
				title: '오류 발생',
				text: rslt.message ?? JSON.stringify(rslt.errors),
				icon: 'error',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});

			// 오류가 발생한 필드로 포커스 이동
			for (let prop in rslt.errors) {
				if (form[prop]) {
					form[prop].focus();
					break;
				}
			}
		}
	}
};

// 글 작성 컨트롤
const createBoard = async (e) => {
	e.preventDefault();
	let form = e.target;

	// SweetAlert2로 "글 작성" 확인 메시지
	const result = await Swal.fire({
		title: '글작성을 하시겠습니까?',
		text: "네 버튼을 클릭시 공지글이 작성됩니다!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '네, 작성할게요!',
		cancelButtonText: '취소'
	});

	if (result.isConfirmed) {
		// 사용자가 확인을 눌렀을 때만 실행
		let modal = bootstrap.Modal.getInstance(`#${insertModalId}`);
		let rslt = await boardInsert(form);

		if (rslt.success) {
			// 게시글 작성 성공 시
			gridApi.applyTransaction({ add: [rslt.target], addIndex: 0 }); 
			modal.hide();

			// SweetAlert2로 작성 성공 알림
			Swal.fire({
				title: '게시물 작성 성공!',
				text: `공지글이 성공적으로 작성되었습니다.`,
				icon: 'success',
				customClass: {
					popup: 'swal2-custom-zindex'
				},
				timer: 3000,  // 자동으로 사라짐
				showConfirmButton: false
			});

			// WebSocket을 통한 알림 전송

		} else {
			// 게시글 작성 실패 시
			Swal.fire({
				title: '오류 발생',
				text: rslt.message ?? JSON.stringify(rslt.errors),
				icon: 'error',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});

			// 오류가 발생한 필드로 포커스 이동
			for (let prop in rslt.errors) {
				if (form[prop]) {
					form[prop].focus();
					break;
				}
			}
		}
	}
};


// 글 삭제 컨트롤
const removeBoard = async () => {
	Swal.fire({
		title: '정말 삭제하시겠습니까?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: '네, 삭제할게요!',
		cancelButtonText: '아니요, 취소할래요!',
		backdrop: `rgba(0, 0, 0, 0.4)`,
		customClass: {
			popup: 'swal2-custom-zindex'
		}
	}).then(async (result) => {
		if (result.isConfirmed) {
			let modalEl = document.getElementById(detailModalId);
			let modal = bootstrap.Modal.getInstance(`#${detailModalId}`);
			let form = modalEl.querySelector("form");

			let boardData = {
				action: form.action,
				groupId: form.groupId.value,
				memberId: form.memberId.value
			};
			console.log(form.action);
			console.log(form.groupId.value);
			console.log(form.memberId.value);

			let rslt = await deleteBoard(boardData);

			if (rslt.rslt < 0) {
				const error = rslt.errors;
				Swal.fire({
					title: '오류 발생',
					text: error,
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});
			} else {
				const sel = gridApi.getSelectedRows();
				gridApi.applyTransaction({ remove: sel });
				modal.hide();
				Swal.fire({
					title: '삭제 완료',
					text: '삭제가 완료되었습니다.',
					icon: 'success',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});
			}
		} else {
			Swal.fire({
				title: '삭제 취소',
				text: '삭제가 취소되었습니다.',
				icon: 'info',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});
		}
	});
};

// 기본 설정
const detailModalId = "detailModal";
const insertModalId = "newBoardModal";
const insertFormId = "insertForm";
const updateFormId = "updateForm";
const delBtnId = "deleteBtn";
const noManager = "noManager";
const answerForm = "answerForm";

let gridApi;

const gridOptions = {
	getRowId: (params) => `${params.data.boardId}`,
	pagination: true,
	paginationPageSizeSelector: [20, 40, 60],
	paginationPageSize: 20,
	defaultColDef: {
		flex: 1,
		filter: true,
		resizable: true,
		minWidth: 50,
		headerClass:"centered-header"
	},
	columnDefs: [
		{ field: "boardTitle", headerName: "제목" },
		{
			field: "memberName",
			headerName: "작성자",
			valueFormatter: (params) => `${params.data.managementVO.memberName} (${params.data.memberId})`
		},
		{
			field: "boardCreateDate",
			headerName: "작성일",
			sortable: true,
			valueFormatter: params => {
				const dateArray = params.value;
				if (Array.isArray(dateArray) && dateArray.length === 6) {
					const [year, month, day, hour, minute, second] = dateArray;
					const date = new Date(year, month - 1, day, hour, minute, second);
					return date.toLocaleString("ko-KR", {
						year: 'numeric',
						month: '2-digit',
						day: '2-digit',
						hour: '2-digit',
						minute: '2-digit',
						second: '2-digit',
						hour12: true
					});
				}
				return params.value || '';
			}
		}
	],
	rowSelection: "single",
	onGridReady: (params) => {
		gridApi = params.api;
	}
};

document.addEventListener("DOMContentLoaded", async () => {
	const auto = document.getElementById('auto');
	const boardaddId = document.getElementById('boardaddId');
	const boardcontentt = document.getElementById('boardContenttt');
	auto.addEventListener("click",function(){
		boardaddId.value='사내 인트라넷 유지보수 작업 안내';
		boardcontentt.value =`안녕하세요, 임직원 여러분

더 나은 근무 환경과 안정적인 서비스 제공을 위해 사내 인트라넷 유지보수 작업이 다음과 같이 진행될 예정입니다. 이번 작업은 인트라넷 성능 향상과 보안 강화를 목표로 하며, 작업 후에는 더 빠르고 안전한 네트워크 환경을 제공할 수 있을 것으로 기대됩니다.

작업 일시: 2024년 11월 5일(화) 오후 10시 ~ 11월 6일(수) 오전 6시
작업 대상: 사내 인트라넷 전체 시스템
유의사항
작업 시간 동안에는 인트라넷 접속이 불가하오니, 각 부서에서는 미리 업무에 필요한 데이터를 저장하거나, 중요 업무가 있는 경우 작업 시간을 피해 처리해 주시기 바랍니다. 특히, 실시간으로 진행되는 프로젝트나 문서 관리 시스템을 이용하는 부서에서는 사전 준비를 철저히 해주시기 바랍니다. 작업 중 인트라넷에서 제공하는 이메일, 파일 공유, 전자 결재 시스템, 인사관리 시스템 등의 모든 기능이 일시 중단될 수 있습니다.

작업 후 예상 효과
이번 유지보수 작업을 통해 인트라넷 시스템의 속도와 안정성이 크게 향상될 예정입니다. 더불어, 최근 강화된 보안 규정에 따라 보안 시스템이 한층 더 업그레이드되어 외부 위협으로부터 데이터를 더욱 안전하게 보호할 수 있을 것입니다. 또한, 일부 기능의 업데이트를 통해 사용자 경험이 개선되며, 특히 검색 기능과 데이터 처리 속도가 이전보다 빨라질 것으로 기대됩니다.

작업 완료 후 시스템 점검 및 테스트가 진행될 예정이며, 혹시라도 예상치 못한 문제가 발생할 경우 IT팀에서 신속하게 대응할 계획입니다.

불편을 드려 죄송하며, 임직원 여러분의 양해와 협조에 감사드립니다. 추가적으로 궁금한 사항이 있거나 도움이 필요하신 경우, IT팀으로 문의해 주시기 바랍니다.

감사합니다.`;

	});
	const answergogo = document.getElementById('answergogo');
	const newComment1 = document.getElementById('newComment');
	answergogo.addEventListener("click",function(){
		newComment1.value='네,확인했습니다 즐거운 명절되세요';
	});
	const myGridElement = document.querySelector('#myGrid');
	gridApi = agGrid.createGrid(myGridElement, gridOptions);
	readBoardList();
	const upb =document.querySelector('#deleteBtn');
	const deb=document.querySelector('#upBtn');
	console.log('업데이트 버튼',upb);
	console.log('삭제 버튼',deb);

	    gridApi.addEventListener("rowClicked", function(event) {
        // 선택된 행의 데이터에서 boardId 가져오기
        const selectedBoard = event.data;
        const boardId = selectedBoard.boardId; // 선택된 게시글의 boardId
        const memberIds = selectedBoard.memberId; // 선택된 게시글의 memberId
        console.log('클릭된 게시글의 memberId:', memberIds);
        console.log('케넥션유저 :', connetctionMemberId);


		if(connetctionMemberId!=memberIds){
			console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@');
				upb.style.setProperty('display','none','important');
				deb.style.setProperty('display','none','important');
		console.log('업데이트 버튼',upb);
	    console.log('삭제 버튼',deb);
			
		}else{
			upb.style.setProperty('display','block','important');
			deb.style.setProperty('display','block','important');
			
		}
	
		

        // 가져온 boardId를 통해 추가적인 작업을 처리할 수 있음
        readBoard(event); // 선택된 게시글의 상세 데이터를 가져오는 함수 호출
    });

	document.getElementById(insertFormId).addEventListener("submit", createBoard); // 작성
	document.getElementById(updateFormId).addEventListener("submit", modifyBoard); // 수정
	document.getElementById(delBtnId).addEventListener("click", removeBoard); // 삭제
	document.getElementById(answerForm).addEventListener("submit", boardAdd); // 댓글 작성

	// 모달이 닫힐 때 원래 상태로 복원하는 리스너
	document.getElementById(detailModalId).addEventListener('hidden.bs.modal', resetModalContent);
	
	
	// 게시글 작성 모달히 닫히고 원래의 값을 지우는 이벤트
	document.getElementById(insertModalId).addEventListener('show.bs.modal', function() {
		const insertForm = document.getElementById(insertFormId);
		insertForm.reset();
	});



	// 검색 필터 기능
	document.getElementById('quickFilter').addEventListener('input', onQuickFilterChanged);
	
});


function onQuickFilterChanged() {
	const searchBox = document.getElementById('quickFilter');
	if (gridApi) {
		gridApi.setQuickFilter(searchBox.value);
	}
}
