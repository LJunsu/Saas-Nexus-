/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {

	const contextPath = document.body.dataset.contextPath;
	const groupId = $('#groupId').val();
	const projectId = $('#projectId').val();

	let downFunc = function(e) {
		e.preventDefault();
		let file = encodeURIComponent(e.target.dataset.dzDownload);
		let downUrl = `${contextPath}/gcp/download?fileName=${file}`

		window.location.href = downUrl
	}

	let searchFunc = function(e) {
		if (e.keyCode === 13) { // Enter 키를 누를 때만 검색 실행
			e.preventDefault();
			let searchQuery = $('#searchName').val();
			axios.get(`${contextPath}/group/${groupId}/project/${projectId}/files/search`, { params: { search: searchQuery } })
				.then(resp => {
					console.log(resp.data);
					displaySearchResults(resp.data);
				}).catch(error => {
					console.error('검색 중 오류 발생:', error)
				})
		}
	}

	let searchBtnFunc = function(e) {
		e.preventDefault();
		let searchQuery = $('#searchName').val();
		axios.get(`${contextPath}/group/${groupId}/project/${projectId}/files/search`, { params: { search: searchQuery } })
			.then(resp => {
				console.log(resp.data);
				displaySearchResults(resp.data);
			}).catch(error => {
				console.error('검색 중 오류 발생:', error)
			})
	}

	function displaySearchResults(data) {
		let resultHtml = '';
		for (let [issueName, files] of Object.entries(data)) {
			resultHtml += `
            <div class="folder-group" style="background-color:#7367f0; border-radius:16px;">
                <h3><a href="${contextPath}/issueAdapter?url=/issue/${groupId}/${projectId}/issueMain&last=${files[0].folderPath}" style="color:#ffffff;">${issueName}</a></h3>
                <div class="file-list">`;

			files.forEach(file => {
				resultHtml += `
                <div class="dz-preview dz-processing dz-image-preview dz-success dz-complete" style="border: none; ">
                    <div class="dz-details">
                        <div class="dz-thumbnail">
                            <img data-dz-thumbnail="" alt="${file.fileName}" src="https://storage.googleapis.com/java_bucket_hstest1/${file.folderPath}/${file.fileName}">
                            <span class="dz-nopreview">No preview</span>
                            <div class="dz-error-mark"></div>
                            <div class="dz-error-message"><span data-dz-errormessage=""></span></div>
                            <div class="progress">
                                <div class="progress-bar progress-bar-primary" role="progressbar" aria-valuemin="0" aria-valuemax="100" data-dz-uploadprogress="" style="width: 100%;"></div>
                            </div>
                        </div>
                        <div class="dz-filename" data-dz-name="">${file.fileName}</div>
                        <div class="dz-size" data-dz-size=""><strong>${file.formatSize}</strong></div>
                    </div>
                    <a class="dz-remove" href="javascript::" data-dz-download="${file.folderPath}/${file.fileName}">다운로드</a>
                </div>`;
			});

			resultHtml += `
                </div>
            </div>`;
		}

		$('.card-body').html(resultHtml);
	}

	$(document).on('keyup', '#searchName', searchFunc);
	$(document).on('click', '#searchBtn', searchBtnFunc);
	$(document).on('click', '.dz-remove', downFunc);

})