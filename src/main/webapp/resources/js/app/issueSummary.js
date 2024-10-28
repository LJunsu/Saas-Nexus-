const contextPath = document.body.dataset.contextPath;

const projectId = document.querySelector("#issueSummartyMainDiv").dataset.projectId;
const startDateInp = document.getElementById("startDateInp");
const endDateInp = document.getElementById("endDateInp");

const projectMemSelect = document.querySelector("#projectMemSelect");

const dateChart = document.getElementById("dateChart");
let dateChartInstance = null;

const priorityChart = document.getElementById("priorityChart");
let priorityChartInstance = null;

const statusChart = document.getElementById("statusChart");
let statusChartInstance = null;



document.addEventListener("DOMContentLoaded", ()=>{

	let endDate = new Date();
	let startDate = new Date();
	startDate.setMonth(endDate.getMonth() - 6);
	startDateInp.valueAsDate = startDate;
	endDateInp.valueAsDate = endDate;

	projectMemSelect.addEventListener("change", function() {
		console.log("projectMemSelect : ", projectMemSelect.value);

		dateChartRead(dateChart, startDate, endDate);
		priorityChartRead(priorityChart, startDate, endDate);
		statusChartRead(statusChart, startDate, endDate);
	});

	startDateInp.addEventListener("change", function() {
		startDate = startDateInp.valueAsDate;
		console.log("startDate : ", startDate);

		dateChartRead(dateChart, startDate, endDate);
		priorityChartRead(priorityChart, startDate, endDate);
		statusChartRead(statusChart, startDate, endDate);
	});

	endDateInp.addEventListener("change", function() {
		endDate = endDateInp.valueAsDate;
		console.log("endDate : ", endDate);

		dateChartRead(dateChart, startDate, endDate);
		priorityChartRead(priorityChart, startDate, endDate);
		statusChartRead(statusChart, startDate, endDate);
	});


	dateChartRead(dateChart, startDate, endDate);
	priorityChartRead(priorityChart, startDate, endDate);
	statusChartRead(statusChart, startDate, endDate);

});



const dateChartRead = (dateChart, startDate, endDate) => {

	let issueData = {
		projectId : projectId,
		startDate : startDate,
		endDate : endDate
	};

	console.log(issueData);

	axios.post(`${contextPath}/DateChart`, issueData).then((issueList) => {
		console.log(issueList);

		let labels = [];
		let completedCount = [];
		let allCount = [];

		let tr = `
			<tr style="border-bottom: 1px solid #e5e5e5;">
				<th style="width: 30%; font-size: 16px; font-weight: 700; padding-bottom:10px;">담당자</th>
				<td style="width: 30%; font-size: 16px; font-weight: 700; padding-bottom:10px;">완료 이슈</td>
				<td style="width: 30%; font-size: 16px; font-weight: 700; padding-bottom:10px;">전체 이슈</td>
			</tr>

			<tr style="height: 10px;"></tr>
		`;
		if(issueList.data.issueVOList.length <= 0) {
			tr = `<td colspan="3">이슈가 없습니다.</td>`;
		}

		for(let i = 0 ; i < issueList.data.issueVOList.length ; i++) {
			labels.push(issueList.data.issueVOList[i].member.memberName);
			completedCount.push(issueList.data.issueVOList[i].completedCount);
			allCount.push(issueList.data.issueVOList[i].allCount);

			tr += `
				<tr>
					<th style="padding: 4px 0;">${issueList.data.issueVOList[i].member.memberName}</th>
					<td>${issueList.data.issueVOList[i].completedCount}개</td>
					<td>${issueList.data.issueVOList[i].allCount}개</td>
				</tr>
			`;
		}

		let chartTable = document.querySelector("#chartTable");
		chartTable.innerHTML = tr;

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



const priorityChartRead = (priorityChart, startDate, endDate) => {

	let issueData = {
		projectId : projectId,
		startDate : startDate,
		endDate : endDate,
		issueOfficer : projectMemSelect.value
	};

	console.log(issueData);

	axios.post(`${contextPath}/PriorityChart`, issueData).then((issueList) => {
		console.log(issueList);

		let labels = [];
		let priorityCount = [];

		let tr = ``;
		if(issueList.data.issueVOList.length <= 0) {
			tr = `<td colspan="2">이슈가 없습니다.</td>`;
		}

		let th = ``;
		let td = ``;
		let thSize = 100 / issueList.data.issueVOList.length;

		for(let i = 0 ; i < issueList.data.issueVOList.length ; i++) {
			let priorityKor = "";
			if(issueList.data.issueVOList[i].issuePriorityName == "Highest") {
				priorityKor = "매우높음";
			} else if(issueList.data.issueVOList[i].issuePriorityName == "High") {
				priorityKor = "높음";
			} else if(issueList.data.issueVOList[i].issuePriorityName == "Low") {
				priorityKor = "낮음";
			} else if(issueList.data.issueVOList[i].issuePriorityName == "Lowest") {
				priorityKor = "매우낮음";
			} else {
				priorityKor = "중간";
			}

			labels.push(priorityKor);
			priorityCount.push(issueList.data.issueVOList[i].priorityCount);

			th += `
				<th style="width: ${thSize}%; font-size: 18px; font-weight: 700; padding-bottom:10px;">${priorityKor}</th>
			`;

			td += `
				<td>${issueList.data.issueVOList[i].priorityCount}개</td>
			`;

		}


		tr += `
			<tr style="border-bottom: 1px solid #e5e5e5;">${th}</tr>
			<tr style="height: 10px;"></tr>
			<tr>${td}</tr>
		`;

		let priorityChartTable = document.querySelector("#priorityChartTable");
		priorityChartTable.innerHTML = tr;

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



const statusChartRead = (statusChart, startDate, endDate) => {

	let issueData = {
		projectId : projectId,
		startDate : startDate,
		endDate : endDate,
		issueOfficer : projectMemSelect.value
	};

	console.log(issueData);

	axios.post(`${contextPath}/StatusChart`, issueData).then((issueList) => {
		console.log(issueList);

		let labels = [];
		let statusCount = [];

		let tr = ``;

		if(issueList.data.issueVOList.length <= 0) {
			tr = `<td colspan="2">이슈가 없습니다.</td>`;
		}

		let th = ``;
		let td = ``;
		let thSize = 100 / issueList.data.issueVOList.length;

		for(let i = 0 ; i < issueList.data.issueVOList.length ; i++) {
			let StatusKor = "";
			if(issueList.data.issueVOList[i].issueProgressStatus == "Completed") {
				StatusKor = "완료됨";
			} else if(issueList.data.issueVOList[i].issueProgressStatus == "In Progress") {
				StatusKor = "진행중";
			} else {
				StatusKor = "준비중";
			}

			labels.push(StatusKor);
			statusCount.push(issueList.data.issueVOList[i].statusCount);

			th += `
				<th style="width: ${thSize}%; font-size: 18px; font-weight: 700; padding-bottom:10px;">${StatusKor}</th>
			`;

			td += `
				<td>${issueList.data.issueVOList[i].statusCount}개</td>
			`;

		}


		tr += `
			<tr style="border-bottom: 1px solid #e5e5e5;">${th}</tr>
			<tr style="height: 10px;"></tr>
			<tr>${td}</tr>
		`;

		let statusChartTable = document.querySelector("#statusChartTable");
		statusChartTable.innerHTML = tr;

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