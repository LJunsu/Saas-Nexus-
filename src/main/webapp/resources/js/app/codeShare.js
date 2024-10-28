const contextPath = document.body.dataset.contextPath;
const baseURI = `${contextPath}/rest/codeShare`;
const groupId = document.querySelector("#topContainer").dataset.groupId;
const projectId = document.querySelector("#topContainer").dataset.projectId;
const memberId = document.querySelector("#topContainer").dataset.realMemId;
const codeShareId = document.querySelector("#topContainer").dataset.codeShareId;
const codeShareContent = document.querySelector("#codeShareContent").value;
const resultSpan = document.querySelector("#resultSpan");
let codeShareSocket = null;
let editor = null;


function connectWs() {
//	let domainName = location.href.split("/")[2];

	let socketUrl = `${contextPath}/grp/${groupId}/project/${projectId}/codeShare/${codeShareId}`;

	codeShareSocket = new SockJS(socketUrl);

	codeShareSocket.onopen = function() {
		console.log("codeShare 소켓 열림");
	};

	codeShareSocket.onmessage = function(e) {
		const message = e.data;

	    if (message.startsWith("USER_LIST:")) {
	        // 사용자 목록 업데이트 처리
	        updateUserList(message.substring(10));
	    } else {
	        console.log("도착한 데이터 : " + message);
	        messageEditorSet(message);
	    }
	}

	codeShareSocket.onclose = function() {
		console.log("codeShare 소켓 닫힘");
	};
};

function updateUserList(userList) {
    const userTable = document.querySelector("#botContainer table");

    // 테이블의 기존 행 삭제
    while (userTable.rows.length > 2) {
        userTable.deleteRow(2);
    }

    // 사용자 목록을 테이블에 추가
    userList.split(",").forEach(user => {
        const [userId, userName] = user.split(":");
        const row = userTable.insertRow(-1);
        const cell1 = row.insertCell(0);
        const cell2 = row.insertCell(1);
        cell1.textContent = userId;
        cell2.textContent = userName;
    });
}

ace.require("ace/ext/language_tools");
document.addEventListener("DOMContentLoaded", async ()=>{
	editor = ace.edit("editor");

	document.querySelector("#insertAuto").addEventListener("click", function() {
editor
.setValue(`public class st {
 public static void main (String[] args) {
  for(int i = 1 ; i <= 5 ; i++) {
   for(int j = 0 ; j < i ; j++) {
    System.out.print("*");
   }
   // 줄바꿈 필요합니다.
  }
 }
}
`);

		document.querySelector("#titleInput").value = "자동완성 제목";
	});

	editor.setTheme("ace/theme/terminal");
	var JavaMode = ace.require("ace/mode/java").Mode;
	editor.getSession().setMode(new JavaMode());

	editor.setOptions({
		enableBasicAutocompletion: true,
		enableSnippets: true,
		enableLiveAutocompletion: true
	});

	editor.commands.addCommand({
		name: "compileAndRun",
		bindKey: {win: "Ctrl-F11"},
		exec: function(editor) {
			$(compileBtn).trigger("click");
		}
	});

	document.querySelector("#compilingBtn").addEventListener("click", compiling);
	document.querySelector("#editorSubmit").addEventListener("click", editorSubmit);
	document.querySelector("#editor").addEventListener("keyup", editorInsert);
	document.querySelector("#codeShareDelete").addEventListener("click", codeShareDelete);
});

const compiling = async () => {
	let code = editor.getValue();

	let resp = await FetchUtils.fetchForJSON(`${baseURI}/compiling`, {
		method : "post",
		headers : {
			"Content-Type" : "application/json",
			"Accept" : "application/json"
		},
		body : JSON.stringify(code)
	});

	console.log(resp);
	console.log(resp.result);

	resultSpan.innerHTML = resp.result;
}

const codeShareDelete = async () => {

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

			let resp = await FetchUtils.fetchForJSON(`${baseURI}/${codeShareId}`, {
				method : "delete",
				headers : {
					"Content-Type" : "application/json"
				}
			});

			if (resp.YN == "Y") {
				Swal.fire({
					title: '삭제 완료',
					text: '정상적으로 삭제되었습니다',
					icon: 'success',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				location.href = contextPath + "/group/" + groupId + "/project/" + projectId + "/codeShareList";
			} else {
				Swal.fire({
					title: '오류 발생',
					text: "삭제에 실패했습니다.",
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});

				alert("삭제 안됨");
			}
		}
	});
}

const editorInsert = () => {
	let code = editor.getValue();

	if(codeShareSocket && codeShareSocket.readyState === SockJS.OPEN) {
		codeShareSocket.send(code);
	} else {
        alert("소켓이 열려 있지 않습니다.");
    }
}

const messageEditorSet = (code) => {
	editor.setValue(code);
}

const editorSubmit = async () => {
	let code = editor.getValue();
	let title = document.querySelector("#titleInput").value;

	let codeShareVO = {
		codeShareTitle : title,
		codeShareContent : code,
		groupId : groupId,
		projectId : projectId,
		memberId : memberId,
		codeShareId : codeShareId
	}



	Swal.fire({
		title: '저장 하시겠습니까?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: '네, 저장할게요!',
		cancelButtonText: '아니요, 취소할래요!',
		backdrop: `rgba(0, 0, 0, 0.4)`,
		customClass: {
			popup: 'swal2-custom-zindex'
		}
	}).then(async (result) => {
		if (result.isConfirmed) {

			let resp = await FetchUtils.fetchForJSON(`${baseURI}/update`, {
				method : "post",
				headers : {
					"Content-Type" : "application/json",
					"Accept" : "application/json"
				},
				body : JSON.stringify(codeShareVO)
			});

			if (resp.codeDetail) {
				Swal.fire({
					title: '저장 완료',
					text: '정상적으로 저장되었습니다',
					icon: 'success',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});
			} else {
				Swal.fire({
					title: '오류 발생',
					text: "저장에 실패했습니다.",
					icon: 'error',
					customClass: {
						popup: 'swal2-custom-zindex'
					}
				});
			}
		}
	});
}