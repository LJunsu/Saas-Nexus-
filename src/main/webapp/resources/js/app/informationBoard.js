/**
 *
 */



// formData to JSON 데이터 변환
const formDataToJSON = (form)=>{
	let formData = new FormData(form);
	let data = {};
	for(let name of formData.keys()){
		let value = formData.get(name);
		data[name] = value;
	}
	return JSON.stringify(data);
}

// command object의 프로퍼티 값을 form 의 입력 태그(type='password' 제외)의 값으로 초기화
const objectToFormData = (obj, form)=>{
	Object.keys(obj)
			.map(p=>form[p])
			.filter(i=>i && i.type!="password")
			.forEach(i=>i.value=obj[i.name]);
}

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



const contextPath = document.body.dataset.contextPath;
const realMem = document.querySelector("#informationBoardContainer").dataset.realMemId;
const groupId = document.querySelector("#informationBoardContainer").dataset.groupId;
const tbody = document.querySelector("#informationTbody");
const detailModalBody = document.querySelector("#detailModalBody");
const detailModalAnsw = document.querySelector("#detailModalAnsw");
const baseURI = `${contextPath}/informationBoard`;


const boardLikeAdd = async (boardId, realMem)=> {

	var form = new Object();
	form.ifBoardId = boardId;
	form.memberId = realMem;

	const response = FetchUtils.fetchForJSON(`${baseURI}/like/${boardId}`, {
								method:"post",
								headers:{
									"content-type":"application/json"
									, "accept":"application/json"
								}, body : JSON.stringify(form)
							});

	return response;

}

const boardLikeDel = async (boardId, realMem)=> {

	var form = new Object();
	form.ifBoardId = boardId;
	form.memberId = realMem;

	const response = FetchUtils.fetchForJSON(`${baseURI}/like/${boardId}`, {
								method:"delete",
								headers:{
									"content-type":"application/json"
									, "accept":"application/json"
								}, body : JSON.stringify(form)
							});

	return response;

}


async function detailModalLikeReset(ifBoardId) {

	detailModalAnsw.innerHTML = singleAnswersThUI;

	let boardOne = await boardDetail(ifBoardId);

	detailModalBody.innerHTML = await singleDetailUI(boardOne);


	// input에 게시글 정보를 hidden으로 넣어두기 위함
	let ifBoardIdInp = document.querySelector("#answBoardInp");
	ifBoardIdInp.value = boardOne.ifBoardId;

	// 댓글
	console.log(boardOne);
	let answList = boardOne.ifBoardAnswersList;
	console.log(answList);
	let answTags = "";

	if(answList && answList.length > 0) {
		answTags += answList.map(singleAnswersUI).join("\n");
	} else {
		answTags += singleAnswersNullUI;
	}
	detailModalAnsw.innerHTML += answTags;

	let answFormList = document.querySelectorAll(".answUpdateForm");
	for(i = 0 ; i < answFormList.length ; i++) {
		answFormList[i].addEventListener("submit", answUpdate);
	}


	$("#detailModal").modal("show");
}

const likeClick = async (pThis) => {
	let boardId = pThis.dataset.boardId;
	let likeChk = pThis.dataset.likeChk;

	let rslt = null;
	if(likeChk == "on") {
		rslt = await boardLikeDel(boardId, realMem); // 좋아요를 누른적이 있을때 활성화되는 버튼의 동작
	} else {
		rslt = await boardLikeAdd(boardId, realMem); // 좋아요를 누른적이 없다면 활성화되는 버튼의 동작
	}

	if(rslt && rslt.success){
		await detailModalLikeReset(boardId);
	} else {
		alert(rslt.message ?? JSON.stringify(rslt.errors));
		for(let prop in rslt.errors){
			if(form[prop]){
				console.log(rslt.errors[prop]);
				form[prop].focus();
				break;
			}
		}
	}
}


const boardLikeYN = async (boardId, realMem) => {

	var form = new Object();
	form.ifBoardId = boardId;
	form.memberId = realMem;

	const response = FetchUtils.fetchForJSON(`${baseURI}/like/chk/${boardId}`, {
								method:"post",
								headers:{
									"content-type":"application/json"
									, "accept":"application/json"
								}, body : JSON.stringify(form)
							});

	return response;

}


// 글 목록 조회
const boardList = () => FetchUtils.fetchForJSON(`${baseURI}/${groupId}`);

const singleDetailUI =  async (information) => {

	let innerDetail = "등록된 파일이 없습니다.";
	// 등록된 파일이 있다면
	if(information.attachedFile) {
		innerDetail =
			information.attachedFile?.fileDetails
			?.filter(f => f && f.originalFileName)
            .map(
                f =>
                    `<li><a href="${contextPath}/attached/Gnotice/${f.attachedFileId}/${f.attachedFileDetailId}" >${f.originalFileName}</a></li>`
            ).join('') || "<li>첨부된 파일 없음</li>";
	}

	let boardDate = formatDate(information.ifBoardCreationDate);

	let likeYN = await boardLikeYN(information.ifBoardId, realMem);


	let boardLikeBtnOnOff = "좋아요 버튼";
	if(likeYN) { // 이전에 좋아요를 눌렀다면
		boardLikeBtnOnOff = `<a class="btn" id="boardLikeBtn" data-board-id="${information.ifBoardId}" data-like-chk="on" onclick="likeClick(this)"><i class="fa-solid fa-heart" style="margin-right: 15px;"></i> ${information.ifBoardLikeList[0].ifBoardLike} 개</a>`;
	} else { // 이전에 좋아요를 누르지 않았다면
		boardLikeBtnOnOff = `<a class="btn" id="boardLikeBtn" data-board-id="${information.ifBoardId}" data-like-chk="off" onclick="likeClick(this)"><i class="fa-regular fa-heart" style="margin-right: 15px;"></i> ${information.ifBoardLikeList[0].ifBoardLike} 개</a>`;
	}

	let boardDetailUI = `
		<tr>
			<th style="width: 20%;">정보 게시판 ID</th>
			<td id="detailId" data-detail-board-id="${information.ifBoardId}">${information.ifBoardId}</td>
		</tr>
		<tr>
			<th style="width: 20%;">정보 게시판 제목</th>
			<td>${information.ifBoardTitle}</td>
		</tr>
		<tr>
			<th style="width: 20%;">정보 게시판 내용</th>
			<td>${information.ifBoardContent}</td>
		</tr>
		<tr>
			<th style="width: 20%;">정보 게시글 생성일</th>
			<td>${boardDate}</td>
		</tr>
		<tr>
			<th style="width: 20%;">게시판 좋아요 수</th>
			<td>
				${boardLikeBtnOnOff}
			</td>
		</tr>
		<tr>
			<th style="width: 20%;">조회수</th>
			<td>${information.ifBoardNum}</td>
		</tr>
		<tr>
			<th style="width: 20%;">작성자</th>
			<td>${information.member.memberName}</td>
		</tr>
		<tr>
			<th style="width: 20%;">첨부파일</th>
			<td>
				<ul>
					${innerDetail}
				</ul>
			</td>
		</tr>
	`;

	return boardDetailUI;
}




const answDeleteSend = async (form) => {
	let ifBoardCmtId = form.ifBoardCmtId;

	let boardOne = await boardDetail(form.ifBoardId);
	let answList = boardOne.ifBoardAnswersList;
	let eq = false;
	console.log("answList", answList);

	for(let i = 0 ; i < answList.length ; i++) {
		if(answList[i].ifBoardParentCmtId == ifBoardCmtId) {
			eq = true;
		}
	}

	if(!eq) {
		const response = FetchUtils.fetchForJSON(`${baseURI}/answ/${ifBoardCmtId}`, {
									method:"delete",
									headers:{
										"content-type":"application/json"
										, "accept":"application/json"
									}, body : JSON.stringify(form)
								});
		return response;
	} else {
		return false;
	}

}

const answDelete = async (pThis) => {

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

			let dataNum = pThis.dataset.boardAnwsMatNum;
			let trTag = $(`[data-board-anws-num="${dataNum}"`)[0];

			console.log(trTag);

			let content = trTag.querySelector(".answUpdateInput").value;
			let cmtId = trTag.querySelector(".answCmtId").value;
			let memId = trTag.querySelector(".answMemberId").value;
			let boardId = trTag.querySelector(".answIfBoardId").value;

			var form = new Object();
			form.ifBoardCmtContent = content;
			form.ifBoardCmtId = cmtId;
			form.memberId = memId;
			form.ifBoardId = boardId;

			let rslt = await answDeleteSend(form);

			if (rslt == false) {
				Swal.fire({
					title: '오류 발생',
					text: "하위 댓글이 존재하면 삭제할 수 없습니다.",
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});
			} else {
				if(rslt && rslt.success){
					await detailModalLikeReset(boardId);
				} else {
					Swal.fire({
						title: '오류 발생',
						text: "삭제에 실패했습니다." + rslt.errors,
						icon: 'error',
						customClass: {
							popup: 'swal2-custom-zindex'
						}
					});

					for(let prop in rslt.errors) {
						if(form[prop]) {
							console.log(rslt.errors[prop]);
							form[prop].focus();
							break;
						}
					}
				}
			}
		}
	});
};

const answUpdateSend = async (form) => {
	let ifBoardCmtId = form.ifBoardCmtId;

	const response = FetchUtils.fetchForJSON(`${baseURI}/answ/${ifBoardCmtId}`, {
								method:"post",
								headers:{
									"content-type":"application/json"
									, "accept":"application/json"
								}, body : JSON.stringify(form)
							});
	return response;
}

const answUpdate = async (pThis) => {

		Swal.fire({
		title: '정말 수정 하시겠습니까?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: '네, 수정할게요!',
		cancelButtonText: '아니요, 취소할래요!',
		backdrop: `rgba(0, 0, 0, 0.4)`,
		customClass: {
			popup: 'swal2-custom-zindex'
		}
	}).then(async (result) => {
		if (result.isConfirmed) {
			let dataNum = pThis.dataset.boardAnwsMatNum;
			let trTag = $(`[data-board-anws-num="${dataNum}"`)[0];


			let content = trTag.querySelector(".answUpdateInput").value;
			let cmtId = trTag.querySelector(".answCmtId").value;
			let memId = trTag.querySelector(".answMemberId").value;
			let boardId = trTag.querySelector(".answIfBoardId").value;

			var form = new Object();
			form.ifBoardCmtContent = content;
			form.ifBoardCmtId = cmtId;
			form.memberId = memId;
			form.ifBoardId = boardId;

			let rslt = await answUpdateSend(form);

			if (rslt && rslt.success) {
				Swal.fire({
					title: '수정 완료',
					text: '정상적으로 수정되었습니다',
					icon: 'success',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				await detailModalLikeReset(boardId);

			} else {
				Swal.fire({
					title: '오류 발생',
					text: "수정에 실패했습니다.",
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				for(let prop in rslt.errors){
					if(form[prop]){
						console.log(rslt.errors[prop]);
						form[prop].focus();
						break;
					}
				}

			}
		}
	});
};



const answAnswInput = async (boardAnswId) => {
	let boardAnswIdBox = document.querySelector("#boardAnswId");
	boardAnswIdBox.value = boardAnswId;
}

const singleAnswersThUI = `
		<div style="display: flex; width: 100%;">
			<div style="line-height: 50px; width: 40px;"></div>
			<div style="width: 7%; text-align: center; line-height: 50px;"></div>
			<div style="width: 60%; text-align: center; line-height: 50px;">내용</div>
			<div style="width: 10%; text-align: center; line-height: 50px;">작성자</div>
			<div style="width: 15%; text-align: center; line-height: 50px;">작성일</div>
		</div>
`;

const singleAnswersUI = (informationAnswers) => {

	let memberId = realMem;

	let tdContent = `<div class="answUpdateInput" style="width: 60%; line-height: 50px; padding-left: 10px;">${informationAnswers.ifBoardCmtContent}</div>`;
	let tdBtn = ``;

	if(informationAnswers.memberId == memberId) {
		tdContent = `<div style="width: 60%"> <textarea rows="1" style="width:100%; height: 80%; border: none; box-shadow: 0 0.125rem 0.25rem rgba(165, 163, 174, 0.3); padding: 0.6rem 0.5rem;" class="answUpdateInput">${informationAnswers.ifBoardCmtContent}</textarea> </div>`;

//		tdBtn =
//		`<div style="display: flex; width: 7%;">
//			<button type="button" class="answUpdate btn btn-primary" onclick="answUpdate(this)" data-board-anws-mat-num="${informationAnswers.ifBoardCmtId}" style="padding:0; width: 50%; border: 1px solid #aaaaaa;">수정</button>
//			<button type="button" class="answDel btn btn-danger" onclick="answDelete(this)" data-board-anws-mat-num="${informationAnswers.ifBoardCmtId}" style="padding:0; width: 50%; border: 1px solid #aaaaaa;">삭제</button>
//		</div>`;
		tdBtn =
			`<div class="dropdown" style="line-height: 50px;">
				<button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown" aria-expanded="false">
					<i class="ti ti-dots-vertical"></i>
				</button>

				<div class="dropdown-menu" style="background: #ffffff; padding: 0; box-shadow: 0 0.25rem 1rem rgba(165, 163, 174, 0.4);">
					<a class="dropdown-item waves-effect updateBtn" onclick="answUpdate(this)" data-board-anws-mat-num="${informationAnswers.ifBoardCmtId}">수정</a>
					<a class="dropdown-item waves-effect text-danger delBtn" onclick="answDelete(this)" data-board-anws-mat-num="${informationAnswers.ifBoardCmtId}">삭제</a>
				</div>
			</div>`;
	}

	let boardDate = formatDate(informationAnswers.ifBoardCmtCrDate);


	let answDivTag = "";
	let level = Number(informationAnswers.commentLevel);
	let anwsMargin = 0;
//	if(level <= 4) {
//		anwsMargin = (Number(informationAnswers.commentLevel) - 1) * 30;
//	} else {
//		anwsMargin = (Number(informationAnswers.commentLevel) - 1) + 120;
//	}
	if(level >= 2) {
		anwsMargin = (Number(informationAnswers.commentLevel) - 1) + 30;
	}

	answDivTag += `
		<div data-board-anws-num="${informationAnswers.ifBoardCmtId}" style="display: flex; width: 100%; position: relative;">
			<input type="hidden" value="${informationAnswers.ifBoardCmtId}" class="answCmtId">
			<input type="hidden" value="${informationAnswers.memberId}" class="answMemberId">
			<input type="hidden" value="${informationAnswers.ifBoardId}" class="answIfBoardId">
	`;

	if(level > 1) {
		answDivTag += `<div style="line-height: 50px; width: 40px; position: relative; left: ${anwsMargin}px;">└></div>`;
	} else {
		answDivTag += `<div style="line-height: 50px; width: 40px;"></div>`;
	}

	answDivTag += `
			<div style="width: 7%; line-height: 50px; color:#7367f0; cursor: pointer; padding-left: ${anwsMargin}px;" onclick="answAnswInput(${informationAnswers.ifBoardCmtId})"> ${informationAnswers.ifBoardCmtId}</div>
			${tdContent}
			<div style="width: 10%; text-align: center; line-height: 50px;">${informationAnswers.memberName}</div>
			<div style="width: 15%; text-align: center; line-height: 50px;">${boardDate}</div>
			${tdBtn}
		</div>
	`;

	return answDivTag;
}

const singleAnswersNullUI = `
	<tr>
		<td colspan="6">댓글 없음</td>
	</tr>
`;

// 상세보기 모달 활성화
async function detailModal(e) {
	e.preventDefault();

	document.querySelector("#boardAnswId").value = "";

	detailModalAnsw.innerHTML = singleAnswersThUI;

	let ifBoardId = e.target.dataset.ifBdId;
	await boardNumPlue(ifBoardId);
	let boardOne = await boardDetail(ifBoardId);

	if(boardOne.memberId == realMem) {
		myBoardBtn.style.display = "block";
	} else {
		myBoardBtn.style.display = "none";
	}

	detailModalBody.innerHTML = await singleDetailUI(boardOne);


	// input에 게시글 정보를 hidden으로 넣어두기 위함
	let ifBoardIdInp = document.querySelector("#answBoardInp");
	ifBoardIdInp.value = boardOne.ifBoardId;

	// 댓글
	let answList = boardOne.ifBoardAnswersList;
	let answTags = "";

	if(answList && answList.length > 0) {
		answTags += answList.map(singleAnswersUI).join("\n");
	} else {
		answTags += singleAnswersNullUI;
	}
	detailModalAnsw.innerHTML += answTags;

	let answFormList = document.querySelectorAll(".answUpdateForm");
	for(i = 0 ; i < answFormList.length ; i++) {
		answFormList[i].addEventListener("submit", answUpdate);
	}


	$("#detailModal").modal("show");
}



// 글 상세 조회
const boardDetail = (ifBoardId)=> FetchUtils.fetchForJSON(`${baseURI}/${groupId}/${ifBoardId}`);

// 조회수
const boardNumPlue = (ifBoardId)=> FetchUtils.fetchForJSON(`${baseURI}/${groupId}/${ifBoardId}/boardNumPlue`);


// 댓글 작성
const answInsert = async (form) => {
	let ifBoardId = form.ifBoardId.value;

	FetchUtils.fetchForJSON(`${baseURI}/${ifBoardId}`, {
								method:form.method,
								headers:{
									"accept":"application/json"
								}, body : new FormData(form)
							});
}


async function detailModalAnswNew(ifBoardId) {

	detailModalAnsw.innerHTML = singleAnswersThUI;
	let boardOne = await boardDetail(ifBoardId);

	detailModalBody.innerHTML = singleDetailUI(boardOne);

	// 댓글
	let answList = boardOne.ifBoardAnswersList;
	let answTags = "";

	if(answList && answList.length > 0) {
		answTags += answList.map(singleAnswersUI).join("\n");
	} else {
		answTags += singleAnswersNullUI;
	}
	detailModalAnsw.innerHTML += answTags;

}

// 댓글 작성 컨트롤
const boardAnswAdd = async (e)=>{
	e.preventDefault();
	e.stopPropagation();

	let answForm = e.target.closest(".modal-content").querySelector("#detailForm");

	let ifBoardId = answForm.ifBoardId.value;

	await answInsert(answForm);

	setTimeout(()=>{
		detailModalLikeReset(ifBoardId);
	}, 500);


//	detailModalAnswNew(answForm.ifBoardId.value);
	document.querySelector("#answInput").value = "";
	document.querySelector("#boardAnswId").value = "";
};



// 글 생성 - 컨트롤러에서 파라미터로
const boardInsert = (form) => FetchUtils.fetchForJSON(baseURI, {
								method:form.method,
								headers:{
									"accept":"application/json"
								},
								body:new FormData(form)
							});

// 글 작성 컨트롤
const createBoard = async (e)=>{
	e.preventDefault();
	let form = e.target;

	let rslt = await boardInsert(form);

	console.log(rslt);

	if(rslt.success){
//		readBoardList();
		await dataTableReadBoardList();
		document.querySelector("#informationFormInsert").reset();
		$('#informationInsertModal').offcanvas("hide");

   		if(socket){
    	const boardTiatle = rslt.target.map(item => item.ifBoardTitle);
        	socket.send(boardTiatle);
           }

	} else{
		alert(rslt.message ?? JSON.stringify(rslt.errors));
		for(let prop in rslt.errors){
			if(form[prop]){
				console.log(rslt.errors[prop]);
				form[prop].focus();
				break;
			}
		}
	}
}



// 글 수정
const boardUpdate = (form) => {
	console.log(form);
	return FetchUtils.fetchForJSON(baseURI + "/update", {
									method:"post",
									headers:{
										"accept":"application/json"
									},
									body:new FormData(form)
							  });
}

// 글 수정 컨트롤
const modifyBoard = async (e) => {
	e.preventDefault();
//	e.stopPropergation();  // 이벤트 버블링 막기

	Swal.fire({
		title: '정말 수정 하시겠습니까?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: '네, 수정할게요!',
		cancelButtonText: '아니요, 취소할래요!',
		backdrop: `rgba(0, 0, 0, 0.4)`,
		customClass: {
			popup: 'swal2-custom-zindex'
		}
	}).then(async (result) => {
		if (result.isConfirmed) {
			const form = e.target.closest(".modal-body").querySelector("form");

			let rslt = await boardUpdate(form);
			console.log("rslt", rslt);

			if (rslt.success) {
				Swal.fire({
					title: '수정 완료',
					text: '정상적으로 수정되었습니다',
					icon: 'success',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				await dataTableReadBoardList();
				$("#updateModal").modal("hide");

			} else {
				Swal.fire({
					title: '오류 발생',
					text: "수정에 실패했습니다.",
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				for(let prop in rslt.errors){
					if(form[prop]){
						console.log(rslt.errors[prop]);
						form[prop].focus();
						break;
					}
				}
			}
		}
	});
}

const updateBtnClick = async (e) => {
	$("#detailModal").modal("hide");

	let form = e.target.closest("form");
	let tdElement = form.querySelector("#detailId");
	let dataDetailBoardId = tdElement.dataset.detailBoardId;

	let boardOne = await boardDetail(dataDetailBoardId);
	console.log(boardOne);

	let ifBoardIdInp = document.querySelector("#ifBoardId");
	let ifBoardTitleInp = document.querySelector("#ifBoardTitle");
	let ifBoardContentInp = document.querySelector("#ifBoardContent");
	let fileDivInp = document.querySelector("#fileDiv");

	ifBoardIdInp.value = boardOne.ifBoardId;
	ifBoardTitleInp.value = boardOne.ifBoardTitle;
	ifBoardContentInp.value = boardOne.ifBoardContent;

	let innerDetail = "등록된 파일이 없습니다.";
	// 등록된 파일이 있다면
	if(boardOne.attachedFile) {
		innerDetail =
			boardOne.attachedFile?.fileDetails
			?.filter(f => f && f.originalFileName)
            .map(
                f =>
                    `<li><a href="${contextPath}/attached/Gnotice/${f.attachedFileId}/${f.attachedFileDetailId}" >${f.originalFileName}</a></li>`
            ).join('') || "<li>첨부된 파일 없음</li>";
	}
	fileDivInp.innerHTML = innerDetail;

	$("#updateModal").modal("show");
}

// 글 삭제
const boardDelete = (dataDetailBoardId) => FetchUtils.fetchForJSON(`${baseURI}/${dataDetailBoardId}`, {
									method:"delete"
									, headers:{
										"content-type":"application/json"
										, "accept":"application/json"
									}, body : JSON.stringify(dataDetailBoardId)
							  });

const deleteBtnClick = async (e) => {

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
				let form = e.target.closest("form");
				let tdElement = form.querySelector("#detailId");
				let dataDetailBoardId = tdElement.dataset.detailBoardId;

				let delYN = await boardDelete(dataDetailBoardId);
			if (delYN) {
				Swal.fire({
					title: '삭제 완료',
					text: '정상적으로 삭제되었습니다',
					icon: 'success',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				dataTableReadBoardList();
				$("#detailModal").modal("hide");
			} else {
				Swal.fire({
					title: '오류 발생',
					text: "삭제에 실패했습니다.",
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});
			}
		}
	});
}

// 글 목록 조회 컨트롤
const dataTableReadBoardList = async ()=>{
	let dataTable = document.dataTables[tableId];
	dataTable.data.data = [];
	dataTable.refresh();

	let list = await boardList();

	list = list.map(board => {
        return {
            ...board,
            ifBoardTitle: `<a class="detailBtn" data-if-bd-id="${board.ifBoardId}" href="${baseURI}/${board.ifBoardId}">${board.ifBoardTitle}</a>`,
            ifBoardLike: `${board.ifBoardLikeList[0].ifBoardLike}`,
//            memberId: `[${board.memberId}] ${board.member.memberName}`
            memberId: `${board.member.memberName}`
        };
    });

	console.log(list);

	dataTable.insert(list);

	titleAddEvent();
}

const tableId = "DataTables_Table_0";

const titleAddEvent = () => {
	let btnList = document.querySelectorAll(".detailBtn");
	for(i = 0 ; i < btnList.length ; i++) {
		btnList[i].addEventListener("click", detailModal);
	}
}

document.addEventListener("DOMContentLoaded", async () => {

	document.querySelector("#insertAuto").addEventListener("click", function() {
		document.querySelector("#informationFormInsert [name = ifBoardTitle]").value = "화상회의 시스템 업데이트 예정 안내";
		document.querySelector("#informationFormInsert [name = ifBoardContent]").value = "안녕하세요, 화상회의 시스템이 업데이트될 예정입니다. 새로 추가된 기능들을 통해 회의가 더 간편해질 예정입니다. 많은 관심 부탁드립니다.";
	});

	document.querySelector("#answInsertAuto").addEventListener("click", function() {
		document.querySelector("#answInput").value = "확인했습니다. 항상 감사합니다.";
	});

	document.querySelector("#UpdateAuto").addEventListener("click", function() {
		document.querySelector("#ifBoardTitle").value = "화상회의 시스템 업데이트 안내";
		document.querySelector("#ifBoardContent").value = "안녕하세요, 화상회의 시스템이 업데이트 됐습니다. 새로 추가된 기능들을 통해 회의가 더 간편해졌습니다. 많은 관심 부탁드립니다.";
	});

	const dataTable = new simpleDatatables.DataTable(`#${tableId}`, {
		columns: [
			{
				select: 3,
				type: "date",
				format: "YYYY/DD/MM",
				sort: "desc",
				render: (data) => {
					return formatDate(data); // 날짜 배열을 원하는 형식으로 변환
				}
			}
		],
		data: {
			headings: [
				{
					text: "게시글 번호",
					data: "ifBoardId"
				}, {
					text: "제목",
					data: "ifBoardTitle"
				}, {
					text: "작성자",
					data: "memberId"
				}, {
					text: "작성일",
					data: "ifBoardCreationDate"
				}, {
					text: "좋아요",
					data: "ifBoardLike"
				}, {
					text: "조회수",
					data: "ifBoardNum"
				}
			]
		}
	});
//	도연 추가 START
document.querySelector('.datatable-dropdown label').childNodes[2].textContent = '개씩 출력';
//	도연 추가 END
	document.dataTables = {[tableId]:dataTable};

	dataTable.on("datatable.init", dataTableReadBoardList);

	document.querySelector(".datatable-input").className += " form-control";
	document.querySelector(".datatable-selector").className += " form-control";

	dataTable.on("datatable.search", function() {
		titleAddEvent();
	});

	dataTable.on("datatable.perpage", function() {
		titleAddEvent();
	});

	dataTable.on("datatable.update", function() {
    	titleAddEvent();
	});

	document.querySelector("#updateBtn").addEventListener("click", updateBtnClick);
	document.querySelector("#updateSubmitBtn").addEventListener("click", modifyBoard); // 수정

	document.querySelector("#deleteBtn").addEventListener("click", deleteBtnClick); // 삭제

	document.querySelector("#informationFormInsert").addEventListener("submit", createBoard); // 등록

	document.querySelector("#informationAnswAdd").addEventListener("click", boardAnswAdd); // 댓글 등록
});