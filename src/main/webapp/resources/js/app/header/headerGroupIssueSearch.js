const groupId2 = document.querySelector('.container-xxl').dataset.groupId;
const cPath2 = document.querySelector('.container-xxl').dataset.contextPath;
const projectId2 = document.querySelector('.container-xxl').dataset.projectId2;

console.log("groupId2",groupId2);
console.log("projectId2",projectId2);



function ajaxCall() {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `${cPath2}/rest/${groupId2}/issueSearch`,
            type: 'GET',
            contentType: 'application/json',
            success: function(response) {
                console.log('GroupIssueList:', response);
                resolve(response);  // 성공 시 데이터를 resolve로 반환
            },
            error: function(xhr, status, error) {
                console.error('GroupIssueList error:', status, error);
                reject(error);  // 에러 발생 시 reject로 에러 반환
            }
        });
    });
}
//작업 상태를 한글로 매핑
function translateIssueStatus(status) {
    // 영어 상태를 한글 텍스트와 색상으로 매핑
    const statusMap = new Map([
        ["Completed", { text: "완료됨", color: "green" }],
        ["In Progress", { text: "진행중", color: "blue" }],
        ["Not Started", { text: "준비중", color: "red" }]
    ]);

    // 상태 값에 해당하는 텍스트와 색상을 반환
    return statusMap.get(status) || { text: status, color: "black" }; // 매핑되지 않은 경우 기본값 반환
}

function groupIssuesByProject(issues) {
    const groupedIssues = {};

    issues.forEach(function(issue) {
        const projectId = issue.projectId;
        const projectName = issue.projectName;

        if (!groupedIssues[projectId]) {
            groupedIssues[projectId] = {
                projectName:'📢[프로젝트]:	'+ projectName,
                issues: []
            };
        }

        groupedIssues[projectId].issues.push({
            id: issue.issueIdx,
           text : `[${issue.issueIdx}] ${issue.issueContent} |    ${issue.memberName} | ${formatDate(issue.issueCreationDate)} | 
<span id="progressStatusColor" style="color: ${translateIssueStatus(issue.issueProgressStatus).color};">${translateIssueStatus(issue.issueProgressStatus).text}</span>`,
			projectId: projectId
        });
    });

    return groupedIssues;
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

function select2Option(selectElement, groupedData) {
    const $select = $(selectElement);
    
    // Select2에서 사용할 데이터 생성
    let options = [];
    for (const projectId in groupedData) {
        const project = groupedData[projectId];

        const optgroup = {
            text: project.projectName,
            children: project.issues
        };

        options.push(optgroup);
    }

    // Select2 옵션 설정
    $select.select2({
        data: options,
        placeholder: '검색할 이슈를 입력해주세요',
        allowClear: true,
        templateResult: formatState,  // 템플릿 커스터마이징
        templateSelection: formatState // 선택한 항목 템플릿
    });

}

// 템플릿 커스터마이징 (선택 옵션 스타일링)
function formatState(state) {
    if (!state.id) {
        return state.text;
    }

    // 하위 이슈의 경우 단순 텍스트 표시
    var $state = $(
        '<span>' + state.text + '</span>'
    );
    return $state;
}

function getSelectedProjectId() {
    // 실제 프로젝트 ID를 반환하는 로직 작성 (예: Select2 데이터에서 추출)
    return $("#projectSearchSelect").find(':selected').data('projectId');
}

document.addEventListener('DOMContentLoaded', () => {
    ajaxCall()
        .then(function(SearchObject) {
            const groupedData = groupIssuesByProject(SearchObject);  // projectId로 그룹화
            select2Option('#projectSearchSelect', groupedData);  // Select2에 옵션 설정
        })
        .catch(function(error) {
            console.error('Error loading issues:', error);
        });
	// Select2 이벤트 핸들러
    $("#projectSearchSelect").on('select2:select', function(e) {
        let select2Val = $("#projectSearchSelect").val();
        console.log("select2Val", select2Val);

        // 선택된 옵션의 projectId 가져오기
        let selectedOption = $("#projectSearchSelect").select2('data')[0];
        let projectId2 = selectedOption.projectId; // 선택된 옵션의 projectId
        
        if (projectId2) {
            console.log("projectId2", projectId2);
            const issueHeaderSearchBtn = document.getElementById('issueheaderSearchBtn');
            issueHeaderSearchBtn.setAttribute('href', `${cPath2}/issueAdapter?url=/issue/${groupId2}/${projectId2}/issueMain&last=${select2Val}`);
        } else {
            console.error("projectId2 not found");
        }
    });
});
