const validateDates = () => {
	const startDate = new Date(issueDate.value);
	const endDate = new Date(issueDead.value);

	if (startDate > endDate) {
		Swal.fire({
			icon: "error",
			title: "경고",
			text: "이슈 시작기한은 마감기한보다 이후일 수 없습니다.",
	});

	issueDate.value = ""; // 시작기한 필드 초기화

	}
}



// 밀리초를 시간으로 계산
const daysToMilliseconds = (days) => {
	return days * 24 * 60 * 60 * 1000;
}



// 툴바 추가 함수
const drawToolbarFnc = () => { // 해당 키 값은 구글 시트 주소의 일부이며, 현재는 임시 데이터로 되어있는데, 이후 엑셀에 결과를 넣을 수 있을 때 수정할 것
	var components = [
		{type: 'html', datasource: 'https://spreadsheets.google.com/tq?key=1SWSwjUqa-ajC6LWIWeKfmvEvZtSQFtprW6RgkAbiQvg'},
		{type: 'csv', datasource: 'https://spreadsheets.google.com/tq?key=1SWSwjUqa-ajC6LWIWeKfmvEvZtSQFtprW6RgkAbiQvg'},
		{type: 'htmlcode', datasource: 'https://spreadsheets.google.com/tq?key=1SWSwjUqa-ajC6LWIWeKfmvEvZtSQFtprW6RgkAbiQvg',
			style: 'width: 800px; height: 700px; border: 3px solid purple;'}
	];

	var container = document.getElementById('toolbar_div');
	google.visualization.drawToolbar(container, components);
}




// 타임라인에 적용될 이슈 리스트
const timeLineList = async ()=> {
	return FetchUtils.fetchForJSON(`${baseURI}/${projectId}/timeLineList`);
}



// 타임라인 이슈 추가
const timeLineInsert = async (e) => {
	e.preventDefault();
	let formData = new FormData(e.target);

	Swal.fire({
		title: '정말 저장 하시겠습니까?',
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

			axios({
				url : `${baseURI}/${projectId}/addOrUpdate`,
				method : "post",
				header : {
					"Content-Type" : "application/json"
				},
				data : formData
			})
			.then(resp => {
				console.log(resp);

				if (resp.data.success == true) {
					Swal.fire({
						title: '저장 완료',
						text: '정상적으로 수정되었습니다',
						icon: 'success',
						customClass: {
							popup: 'swal2-custom-zindex'
						}
					});

					google.charts.setOnLoadCallback(DrawChart);
					document.querySelector("#resetBtn").click();
					document.querySelector("#modalResetBtn").click();

				} else {
					let err = "";
					Object.keys(resp.data.errors).forEach(function(k){
					    err += k + " : " + resp.data.errors[k] + ". ";
					});
					Swal.fire({
						title: '오류 발생',
						text: err,
						icon: 'error',
						customClass: {
							popup: 'swal2-custom-zindex'
						}
					});
				}
			});

		}
	});

//	axios({
//		url : `${baseURI}/${projectId}/addOrUpdate`,
//		method : "post",
//		header : {
//			"Content-Type" : "application/json"
//		},
//		data : formData
//	})
//	.then(resp => {
//		console.log(resp);
//
//		if(resp.data.success == false) {
//			let err = "";
//			Object.keys(resp.data.errors).forEach(function(k){
//			    err += k + " : " + resp.data.errors[k] + ". ";
//			});
//			Swal.fire({
//				title: '오류 발생',
//				text: err,
//				icon: 'error',
//				customClass: {
//					popup: 'swal2-custom-zindex'
//				}
//			});
//		} else {
//			google.charts.setOnLoadCallback(DrawChart);
//			document.querySelector("#resetBtn").click();
//			document.querySelector("#modalResetBtn").click();
//		}
//	})
}



const timeLineSearch = (e) => {
	e.preventDefault();
	let formData = new FormData(e.target);

	axios({
		url : `${baseURI}/search`,
		method : "post",
		header : {
			"Accept" : "application/json"
		},
		data : formData
	})
	.then(resp => {
		console.log(resp.data);
		if(resp.data.length > 0) {
			searchDrawChart(resp.data);
		} else {
			Swal.fire({
				title: '검색 결과',
				text: "검색 조건에 해당하는 이슈가 없습니다.",
				icon: 'error',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});
		}
	})
}



const timeLineMemSelect = (e) => {
	e.preventDefault();
	let formData = new FormData(e.target);

	axios({
		url : `${baseURI}/memberSelect`,
		method : "post",
		header : {
			"Accept" : "application/json"
		},
		data : formData
	})
	.then(resp => {
		console.log(resp.data);
		if(resp.data.length > 0) {
			searchDrawChart(resp.data);
		} else {
			Swal.fire({
				title: '검색 결과',
				text: "검색 조건에 해당하는 이슈가 없습니다.",
				icon: 'error',
				customClass: {
					popup: 'swal2-custom-zindex'
				}
			});
		}
	})

}

const issueDisabList = async (issueIdx) => {
	let issueVO = {
		issueIdx : issueIdx,
		projectId : projectId
	}

	axios({
		url : `${baseURI}/issueDisabList`,
		method : "post",
		header : {
			"Accept" : "application/json"
		},
		data : issueVO
	})
	.then(resp => {

		for(let i = 0 ; i < resp.data.length ; i++) {
			$(`select[name=subIssueIdx] option[value='${resp.data[i].issueIdx}']`).prop('disabled', false);
		}
		$(`select[name=subIssueIdx] option[value='']`).prop('disabled', false);

		return resp
	})

}


////////////////////////////////////////////////////////////////////////////////////////
const DrawChart = async () => {
	let issueList = await timeLineList();
	const data = new google.visualization.DataTable();
	data.addColumn('string', 'issueIdx');
	data.addColumn('string', 'issueTitle');
	data.addColumn('date', 'issueDate');
	data.addColumn('date', 'issueDeadline');
	data.addColumn('number', 'Duration');
	data.addColumn('number', 'issueProgressStatus');
	data.addColumn('string', 'subIssueIdx');

	let rows = [];

	for(let i = 0 ; i < issueList.length ; i++) {

		let subIssue = null;
		for(let j = 0 ; j < issueList.length ; j++) {
			if(i != j) {
				if(issueList[i].subIssueIdx == issueList[j].issueIdx) {
					subIssue = issueList[i].subIssueIdx;
					break;
				}
			}
		}

		if(issueList[i].issueDate != null || issueList[i].issueDeadline != null) {
			let startDate = new Date(issueList[i].issueDate[0], issueList[i].issueDate[1] - 1, issueList[i].issueDate[2]);
			let endDate = new Date(issueList[i].issueDeadline[0], issueList[i].issueDeadline[1] - 1, issueList[i].issueDeadline[2]);
			let statusStr = issueList[i].issueProgressStatus;
			let statusNum = 0;
			if(statusStr == 'Completed') {
				statusNum = 100;
			} else if(statusStr == 'In Progress') {
				statusNum = 50;
			}

			rows.push([
				issueList[i].issueIdx,
				issueList[i].issueTitle,
				startDate,
				endDate,
				null,
				statusNum,
				subIssue
			]);
		}
	}

	if(rows <= 0) {
		document.querySelector("#chart_div").innerHTML = "<h3 style='text-align: center;'>이슈가 없습니다.</h3>"
		return;
	}

	data.addRows(rows);


	var rowHeight = 40; // Task 당 높이 (픽셀)
	var numRows = data.getNumberOfRows(); // 데이터 행 수
	var chartHeight = rowHeight * numRows + 200; // 차트의 높이 계산

	var options = {
		height: chartHeight
	};

	var container = document.getElementById('chart_div');
	var chart = new google.visualization.Gantt(container);

	chart.draw(data, options);


	//
	drawToolbarFnc();
	//

	google.visualization.events.addListener(chart, 'select', async function(e) {

		var selection = chart.getSelection();
		if (selection.length > 0) {
			var item = selection[0];
			if (item.row !== undefined) {
				console.log('선택된 행: ', item.row);

				// 선택 상태를 비활성화하기 위해 setSelection([]) 호출
				setTimeout(function() {
					chart.setSelection([]);  // 약간의 지연을 두고 호출
				}, 100);  // 100ms 정도의 짧은 딜레이를 줌

				$("#btnTimeLineInsertModal").click();
				$("#deleteBtn").remove();
				$("#issueProgressStatus").attr("disabled", false);

				let issueIdx = issueList[item.row].issueIdx;
		        let issueDetail = issueList[item.row];
//				let issueIdx = issueList[chart.getSelection()[0].row].issueIdx;
//		        let issueDetail = issueList[chart.getSelection()[0].row];
				DateIussueIdx = issueIdx;



				$("#subIssueIdx option").prop('disabled', true);
				await issueDisabList(issueIdx);



				if(issueDetail.issueOfficer == newProjectMemIdx || issueDetail.projectMemIdx == newProjectMemIdx) {
					$("#timeLineInsertModalBtn").append(`
						<button type="button" class="btn btn-label-danger waves-effect" id="deleteBtn" data-issue-idx="${issueIdx}" style="margin-top: 15px;">이슈 삭제</button>
					`);
				}

				axios({
					url : `${baseURI}/${issueIdx}`,
					method : "get",
					header : {
						"Content-Type" : "application/json"
					},
				})
				.then(issue => {
					let issueData = issue.data;

					document.querySelector("#timeLineInsert").reset();
					$("#issueIdxInp").val(`${issueData.issueIdx}`);

					modal.querySelector("#issueTitle").value = issueData.issueTitle;
					modal.querySelector("#issueContent").value = issueData.issueContent;
					$("#issueOfficer").val(`${issueData.issueOfficer}`).prop("selected", true);
					$("#issueProgressStatus").val(`${issueData.issueProgressStatus}`).prop("selected", true);
					$("select[name=issuePriorityName]").val(`${issueData.issuePriorityName}`).prop("selected", true);
					modal.querySelector("#issueTagName").value = issueData.issueTags[0].issueTagName;
					modal.querySelector("#issueDate").valueAsDate = new Date(issueData.issueDate[0], issueData.issueDate[1] - 1, issueData.issueDate[2] + 1);
					modal.querySelector("#issueDeadline").valueAsDate = new Date(issueData.issueDeadline[0], issueData.issueDeadline[1] - 1, issueData.issueDeadline[2] + 1);
					$("select[name=subIssueIdx]").val(`${issueData.subIssueIdx}`).prop("selected", true);

		            if(issueDetail.issueOfficer == newProjectMemIdx || issueDetail.projectMemIdx == newProjectMemIdx) {
						document.querySelector("#deleteBtn").addEventListener("click", timeLineDelete);
					}
				});
			}
		} else {
			console.log('선택된 항목이 없습니다.');
		}
	});

    const timeLineDelete = async (e) => {

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

				e.preventDefault();
				console.log(e.target);
				let issueIdx = e.target.dataset.issueIdx;

				axios.delete(`${baseURI}/${issueIdx}`)
				.then(() => {

					Swal.fire({
						title: '삭제 완료',
						text: '정상적으로 수정되었습니다',
						icon: 'success',
						customClass: {
							popup: 'swal2-custom-zindex'
						}
					});

					$("#modalResetBtn").click();
					google.charts.setOnLoadCallback(DrawChart);

				});
			}
		});
	}

}
////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////////
const searchDrawChart = async (issueList) => {
	const data = new google.visualization.DataTable();
	data.addColumn('string', 'issueIdx');
	data.addColumn('string', 'issueTitle');
	data.addColumn('date', 'issueDate');
	data.addColumn('date', 'issueDeadline');
	data.addColumn('number', 'Duration');
	data.addColumn('number', 'issueProgressStatus');
	data.addColumn('string', 'subIssueIdx');

	let rows = [];

	for(let i = 0 ; i < issueList.length ; i++) {

		let subIssue = null;
		for(let j = 0 ; j < issueList.length ; j++) {
			if(i != j) {
				if(issueList[i].subIssueIdx == issueList[j].issueIdx) {
					subIssue = issueList[i].subIssueIdx;
					break;
				}
			}
		}

		if(issueList[i].issueDate != null || issueList[i].issueDeadline != null) {
			let startDate = new Date(issueList[i].issueDate[0], issueList[i].issueDate[1] - 1, issueList[i].issueDate[2]);
			let endDate = new Date(issueList[i].issueDeadline[0], issueList[i].issueDeadline[1] - 1, issueList[i].issueDeadline[2]);
			let statusStr = issueList[i].issueProgressStatus;
			let statusNum = 0;
			if(statusStr == 'Completed') {
				statusNum = 100;
			} else if(statusStr == 'In Progress') {
				statusNum = 50;
			}

			rows.push([
				issueList[i].issueIdx,
				issueList[i].issueTitle,
				startDate,
				endDate,
				null,
				statusNum,
				subIssue
			]);
		}
	}

	if(rows <= 0) {
		document.querySelector("#chart_div").innerHTML = "<h3 style='text-align: center;'>이슈가 없습니다.</h3>"
		return;
	}

	data.addRows(rows);


	var rowHeight = 40; // Task 당 높이 (픽셀)
	var numRows = data.getNumberOfRows(); // 데이터 행 수
	var chartHeight = rowHeight * numRows + 200; // 차트의 높이 계산

	var options = {
		height: chartHeight
	};

	var container = document.getElementById('chart_div');
	var chart = new google.visualization.Gantt(container);

	chart.draw(data, options);


	//
	drawToolbarFnc();
	//


	google.visualization.events.addListener(chart, 'select', async function(e) {

		var selection = chart.getSelection();
		if (selection.length > 0) {
			var item = selection[0];
			if (item.row !== undefined) {
				console.log('선택된 행: ', item.row);
				setTimeout(function() {
					chart.setSelection([]);
				}, 100);

				$("#btnTimeLineInsertModal").click();
				$("#deleteBtn").remove();
				$("#issueProgressStatus").attr("disabled", false);

				let issueIdx = issueList[item.row].issueIdx;
		        let issueDetail = issueList[item.row];


				$("#subIssueIdx option").prop('disabled', true);
				await issueDisabList(issueIdx);


				DateIussueIdx = issueIdx;

				if(issueDetail.issueOfficer == newProjectMemIdx || issueDetail.projectMemIdx == newProjectMemIdx) {
					$("#timeLineInsertModalBtn").append(`
						<button type="button" class="btn btn-label-danger waves-effect" id="deleteBtn" data-issue-idx="${issueIdx}">타임라인 삭제</button>
					`);
				}

				axios({
					url : `${baseURI}/${issueIdx}`,
					method : "get",
					header : {
						"Content-Type" : "application/json"
					},
				})
				.then(issue => {
					let issueData = issue.data;

					document.querySelector("#timeLineInsert").reset();
					$("#issueIdxInp").val(`${issueData.issueIdx}`);

					modal.querySelector("#issueTitle").value = issueData.issueTitle;
					modal.querySelector("#issueContent").value = issueData.issueContent;
					$("#issueOfficer").val(`${issueData.issueOfficer}`).prop("selected", true);
					$("select[name=issueProgressStatus]").val(`${issueData.issueProgressStatus}`).prop("selected", true);
					$("select[name=issuePriorityName]").val(`${issueData.issuePriorityName}`).prop("selected", true);
					modal.querySelector("#issueTagName").value = issueData.issueTags[0].issueTagName;
					modal.querySelector("#issueDate").valueAsDate = new Date(issueData.issueDate[0], issueData.issueDate[1] - 1, issueData.issueDate[2] + 1);
					modal.querySelector("#issueDeadline").valueAsDate = new Date(issueData.issueDeadline[0], issueData.issueDeadline[1] - 1, issueData.issueDeadline[2] + 1);
					$("select[name=subIssueIdx]").val(`${issueData.subIssueIdx}`).prop("selected", true);

		            if(issueDetail.issueOfficer == newProjectMemIdx || issueDetail.projectMemIdx == newProjectMemIdx) {
						document.querySelector("#deleteBtn").addEventListener("click", timeLineDelete);
					}
				});
			}
		} else {
			console.log('선택된 항목이 없습니다.');
		}
	});

    const timeLineDelete = async (e) => {
		e.preventDefault();
		console.log(e.target);
		let issueIdx = e.target.dataset.issueIdx;

		axios.delete(`${baseURI}/${issueIdx}`)
		.then(() => {
			console.log($("#modalResetBtn"));
			$("#modalResetBtn").click();
			google.charts.setOnLoadCallback(DrawChart);
		});
	}

}
////////////////////////////////////////////////////////////////////////////////////////


const searchReset = async () => {
	document.querySelector("#timeLineSearchForm").reset();
	document.querySelector("#timeLineMemForm").reset();

	google.charts.setOnLoadCallback(DrawChart);
}




const dateChartRead = (dateChart) => {

	let issueData = {
		projectId : projectId,
		issueOfficer : chartIssueOfficer
	};

	console.log(issueData);

	axios.post(`${contextPath}/timeLine/timeLineChart`, issueData).then((issueList) => {
		console.log(issueList);

		let labels = [];
		let completedCount = [];
		let allCount = [];

//		let tr = `
//			<tr style="border-bottom: 1px solid #6f6b7d;">
//				<th style="width: 30%; padding: 5px 0;">담당자</th>
//				<td style="width: 30%;">완료 이슈</td>
//				<td style="width: 30%;">전체 이슈</td>
//			</tr>
//		`;
//		if(issueList.data.length <= 0) {
//			tr = `<td colspan="3">이슈가 없습니다.</td>`;
//		}

		for(let i = 0 ; i < issueList.data.length ; i++) {
			labels.push(issueList.data[i].member.memberName);
			completedCount.push(issueList.data[i].completedCount);
			allCount.push(issueList.data[i].allCount);

//			tr += `
//				<tr>
//					<th style="padding: 5px 0;">${issueList.data[i].issueOfficer}</th>
//					<td>${issueList.data[i].completedCount}</td>
//					<td>${issueList.data[i].allCount}</td>
//				</tr>
//			`;
		}



		let chartTable = document.querySelector("#chartTable");
//		chartTable.innerHTML = tr;



		console.log("labels : ", labels);
		console.log("completedCount : ", completedCount);
		console.log("allCount : ", allCount);

		if (dateChartInstance) dateChartInstance.destroy();

		dateChartInstance = new Chart(dateChart, {
			type: 'bar',
			data: {
				labels: labels,
				datasets: [
						{
							label: '완료 이슈',
							data: completedCount,
							borderWidth: 1,
							stack: 'completedCount'
						},
						{
							label: '총 이슈',
							data: allCount,
							borderWidth: 1,
							stack: 'allCount'
						}
					],
				},
			options: {
				scales: {
					x: {
						stacked: true
					},
					y: {
						stacked: true
					}
				}
			}
		});
	});

}



const priorityChartRead = (priorityChart) => {

	let issueData = {
		projectId : projectId,
		issueOfficer : chartIssueOfficer
	};

	console.log(issueData);

	axios.post(`${contextPath}/timeLine/timeLinePriorityChart`, issueData).then((issueList) => {
		console.log(issueList);

		let labels = [];
		let priorityCount = [];

//		let tr = ``;
//		if(issueList.data.length <= 0) {
//			tr = `<td colspan="2">이슈가 없습니다.</td>`;
//		}

		for(let i = 0 ; i < issueList.data.length ; i++) {
			let priorityKor = "";
			if(issueList.data[i].issuePriorityName == "Highest") {
				priorityKor = "매우 높음";
			} else if(issueList.data[i].issuePriorityName == "High") {
				priorityKor = "높음";
			} else if(issueList.data[i].issuePriorityName == "Low") {
				priorityKor = "낮음";
			} else if(issueList.data[i].issuePriorityName == "Lowest") {
				priorityKor = "매우 낮음";
			} else {
				priorityKor = "중간";
			}

			labels.push(priorityKor);
			priorityCount.push(issueList.data[i].priorityCount);

//			tr += `
//				<tr>
//					<th style="width: 50%; border-right: 1px solid #6f6b7d;">${priorityKor}</th>
//					<td style="width: 50%;">${issueList.data[i].priorityCount}</td>
//				</tr>
//			`;
		}

		let priorityChartTable = document.querySelector("#priorityChartTable");
//		priorityChartTable.innerHTML = tr;

		console.log("labels : ", labels);
		console.log("priorityCount : ", priorityCount);

		if (priorityChartInstance) priorityChartInstance.destroy();

		priorityChartInstance = new Chart(priorityChart, {
			type: 'pie',
			data: {
				labels: labels,
				datasets: [{
					data: priorityCount
				}]
			},
			options: {
				plugins: {
					legend: {
						display: true,
						position: 'right'
					}
				}
			}
		});
	});
}



const statusChartRead = (statusChart) => {

	let issueData = {
		projectId : projectId,
		issueOfficer : chartIssueOfficer
	};

	console.log(issueData);

	axios.post(`${contextPath}/timeLine/timeLineStatusChart`, issueData).then((issueList) => {
		console.log(issueList);

		let labels = [];
		let statusCount = [];

//		let tr = ``;
//		if(issueList.data.length <= 0) {
//			tr = `<td colspan="2">이슈가 없습니다.</td>`;
//		}
		for(let i = 0 ; i < issueList.data.length ; i++) {
			let StatusKor = "";
			if(issueList.data[i].issueProgressStatus == "Completed") {
				StatusKor = "완료됨";
			} else if(issueList.data[i].issueProgressStatus == "In Progress") {
				StatusKor = "진행중";
			} else {
				StatusKor = "준비중";
			}

			labels.push(StatusKor);
			statusCount.push(issueList.data[i].statusCount);

//			tr += `
//				<tr>
//					<th style="width: 50%; border-right: 1px solid #6f6b7d;">${StatusKor}</th>
//					<td style="width: 50%;">${issueList.data[i].statusCount}</td>
//				</tr>
//			`;
		}

		let statusChartTable = document.querySelector("#statusChartTable");
//		statusChartTable.innerHTML = tr;

		console.log("labels : ", labels);
		console.log("statusCount : ", statusCount);

		if (statusChartInstance) statusChartInstance.destroy();

		statusChartInstance = new Chart(statusChart, {
			type: 'pie',
			data: {
				labels: labels,
				datasets: [{
					data: statusCount
				}]
			},
			options: {
				plugins: {
					legend: {
						display: true,
						position: 'right'
					}
				}
			}
		});
	});
}

let subToggle=true;
const showBtn = document.querySelector("#showBtn");
const showSummaryDiv = document.querySelector("#showSummaryDiv");
const showSummary = () => {
  if(subToggle) {
    showSummaryDiv.style.height = "350px";
    subToggle =! subToggle;
  } else {
    showSummaryDiv.style.height = "0px";
    subToggle =! subToggle;
  }

}


const contextPath = document.body.dataset.contextPath;
const baseURI = `${contextPath}/timeLine`;
const modal = document.querySelector("#timeLineInsertModal");
const newProjectMemIdx = document.querySelector("#timeLineInsertModalBtn").dataset.projectMemIdx;
const realMemId = document.querySelector("#informationBoardContainer").dataset.realMemId;
const groupId = document.querySelector("#informationBoardContainer").dataset.groupId;
const projectId = document.querySelector("#informationBoardContainer").dataset.projectId;
const realProjectMemId = document.querySelector("#informationBoardContainer").dataset.realProjectMemId;
const issueDate = document.querySelector("input[name='issueDate']");
const issueDead = document.querySelector("input[name='issueDeadline']");
var DateIussueIdx;

const dateChart = document.getElementById("dateChart");
let dateChartInstance = null;
const priorityChart = document.getElementById("priorityChart");
let priorityChartInstance = null;
const statusChart = document.getElementById("statusChart");
let statusChartInstance = null;
let chartIssueOfficer = null;

const chartMemSelect = (e) => {
	chartIssueOfficer = e.target.issueOfficer.value;

	if(chartIssueOfficer == "선택") chartIssueOfficer = null;

	dateChartRead(dateChart);
	priorityChartRead(priorityChart);
	statusChartRead(statusChart);
}

document.addEventListener("DOMContentLoaded", async () => {

	document.querySelector("#insertAuto").addEventListener("click", function() {
		document.querySelector("#issueTitle").value = "모델 학습 및 튜닝";
		document.querySelector("#issueContent").value = "선택된 알고리즘을 바탕으로 고객 데이터를 학습하고, 하이퍼파라미터 튜닝을 통해 성능을 최적화";
		let selectOfficer = document.querySelector("#issueOfficer").value="PM860"
		selectOfficer.selected;
		let selectPriority = document.querySelector("#issuePriorityName").value="High"
		selectPriority.selected;
		let selectTagName = document.querySelector("#issueTagName").value="performance"
		selectTagName.selected;
		document.querySelector("#issueDate").value="2024-09-20";
		document.querySelector("#issueDeadline").value="2024-10-03";
	});

	document.querySelector("#updateAuto").addEventListener("click", function() {
		document.querySelector("#issueTitle").value = "모델 성능 개선 및 최적화";
		document.querySelector("#issueContent").value = "선택된 알고리즘을 사용하여 고객 데이터를 학습한 후, 하이퍼파라미터를 미세 조정하여 모델의 성능을 개선하고, 최적화된 모델을 테스트";
		let selectOfficer = document.querySelector("#issueOfficer").value="PM860"
		selectOfficer.selected;
		let selectPriority = document.querySelector("#issuePriorityName").value="Highest"
		selectPriority.selected;
		let selectTagName = document.querySelector("#issueTagName").value="performance"
		selectTagName.selected;
		document.querySelector("#issueDate").value="2024-09-23";
		document.querySelector("#issueDeadline").value="2024-10-06";
	});

	showBtn.addEventListener("click", showSummary);

	chartIssueOfficer = realProjectMemId;

	let officerSelected = document.querySelector("#chartIssueOfficerSelect");
	officerSelected.value = chartIssueOfficer
	officerSelected.selected;

	dateChartRead(dateChart);
	priorityChartRead(priorityChart);
	statusChartRead(statusChart);


	timeLineMemForm.addEventListener("submit", chartMemSelect);

	document.querySelector("#resetBtn").addEventListener("click", function() {
		chartIssueOfficer = null;
		dateChartRead(dateChart);
		priorityChartRead(priorityChart);
		statusChartRead(statusChart);
	});

	google.charts.load('current', {'packages':['gantt'], 'language' : 'ko'});

	let formData = new FormData();
	formData.append("projectId", projectId);
	formData.append("issueOfficer", chartIssueOfficer);

// "#memSubmitBtn" 버튼을 강제로 클릭시켜서 현재 로그인한 회원의 차트가 처음 나올 수 있도록 함
	await google.charts.setOnLoadCallback(DrawChart);


	// 타임라인 이슈 추가
	document.querySelector("#timeLineInsert").addEventListener("submit", timeLineInsert);
	document.querySelector("#btnTimeLineInsertModal").addEventListener("click", function() {
		$("#subIssueIdx option").prop('disabled', false);
		$("#issueProgressStatus").attr("disabled", true);
		$("#deleteBtn").remove();
		document.querySelector("#timeLineInsert").reset();
	});

	// 타임라인 검색
	document.querySelector("#timeLineSearchForm").addEventListener("submit", timeLineSearch);

	// 타임라인 멤버로 필터
	document.querySelector("#timeLineMemForm").addEventListener("submit", timeLineMemSelect);

	document.querySelector("#resetBtn").addEventListener("click", searchReset);

	issueDate.addEventListener("change", validateDates);
	issueDead.addEventListener("change", validateDates);


	document.querySelector("#memSubmitBtn").click();
});