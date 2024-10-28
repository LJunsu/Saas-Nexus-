/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {

	const contextPath = document.body.dataset.contextPath;

	const cloudBody = document.getElementById('cloudBody');

	const projectId = $('#projectId').val();

	let searchName = '';
	
	const plusTr = '';

//	const plusTr = `
//					<tr id="tgl">
//  						<td id="addBtn" colspan="6">
//  							<div class="dropdown">
//  								<button type="button" class="btn p-0 dropdown-toggle hide-arrow"
//  									data-bs-toggle="dropdown" aria-expanded="false">+ 추가하기</button>
//  								<div class="dropdown-menu" style="">
//  									<a id="folderInsert" class="dropdown-item waves-effect" href="javascript:void(0);">폴더</a>
//  									<a id="fileInsert" class="dropdown-item waves-effect" href="javascript:void(0);">파일</a>
//  									<input type="file" id="fileInput" name="file" style="display: none;">
//  								</div>
//  							</div>
//  						</td>
//  					</tr>`;

	const configTd = `
  					<td>
            <div class="dropdown">
              <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown" aria-expanded="false"><i class="ti ti-dots-vertical"></i></button>
              <div class="dropdown-menu" style="">				
                <a class="dropdown-item waves-effect filePreview" href="javascript:void(0);">미리보기</a>
                <a class="dropdown-item waves-effect fileDownloadBtn" href="javascript:void(0);">다운로드</a>
                <a class="dropdown-item waves-effect text-danger fileDelBtn" href="javascript:void(0);">삭제</a>
              </div>
            </div>
          </td>`;
	const userRole = $('#userRole').val();
	console.log("역할: ", userRole);
	let folderConfigTd = '';
	if (userRole == '프로젝트 리더') {
		folderConfigTd = `
  					<td>
            <div class="dropdown">
              <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown" aria-expanded="false"><i class="ti ti-dots-vertical"></i></button>
              <div class="dropdown-menu" style="">
                <a class="dropdown-item waves-effect fileAccessModal" href="javascript:void(0);">접근권한 설정</a>
                <a class="dropdown-item waves-effect text-danger fileDelBtn" href="javascript:void(0);">삭제</a>
              </div>
            </div>
          </td>`;
	} else {
		folderConfigTd = `
  					<td>
            <div class="dropdown">
              <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown" aria-expanded="false"><i class="ti ti-dots-vertical"></i></button>
              <div class="dropdown-menu" style="">
                <a class="dropdown-item waves-effect text-danger fileDelBtn" href="javascript:void(0);">삭제</a>
              </div>
            </div>
          </td>`;
	}

	let selectedRow = null;

	let currentPath = '';

	//	sessionStorage.setItem('currentPath', '');
	function saveCurrentPath() {
		sessionStorage.setItem('currentPath', currentPath);
	}

	function loadCurrentPath() {
		return sessionStorage.getItem('currentPath') || '';
	}

	function displayFiles(files) {

		cloudBody.innerHTML = '';

		// 현재 경로 표시
		const pathElement = document.createElement('div');
		pathElement.textContent = `현재 경로: ${currentPath || '/'}`; // root 경로는 '/'로 표시
		cloudBody.appendChild(pathElement);

		const filteredFiles = files.filter(file => {
			// 파일인 경우 size가 0보다 큰 파일만 필터링
			if (file.type === 'file') {
				return file.size > 0;
			}
			// 폴더인 경우 필터링하지 않음
			return file.type === 'folder';
		});

		filteredFiles.forEach((file) => {
			if (file.name == 'pictures') {

			} else {

				const newRow = document.createElement('tr');
				const nameCell = document.createElement('td');
				const typeCell = document.createElement('td');
				const lastModifiedCell = document.createElement('td');
				const sizeCell = document.createElement('td');
				const userCell = document.createElement('td');

				// 파일명에서 마지막 '/' 제거
				let displayName = file.name.endsWith('/') ? file.name.slice(0, -1) : file.name;
				nameCell.textContent = displayName;
				nameCell.classList.add('fileName');
				// 파일 타입 결정 (폴더 또는 파일)
				const isFolder = file.type === 'folder';
				const fileFolder = document.createElement('input');
				fileFolder.type = 'hidden';
				fileFolder.classList.add('fileType');
				fileFolder.value = file.type;

				typeCell.textContent = file.fileType;

				lastModifiedCell.textContent = file.lastModified;
				sizeCell.textContent = file.size ? `${file.formatSize}` : '-';
				userCell.textContent = file.username || '-';

				newRow.appendChild(nameCell);
				newRow.appendChild(typeCell);
				newRow.appendChild(lastModifiedCell);
				newRow.appendChild(sizeCell);
				newRow.appendChild(userCell);
				newRow.appendChild(fileFolder);

				if (!isFolder) {
					newRow.innerHTML += configTd;
					newRow.classList.add('file');
				} else {
					// 폴더인 경우 더블클릭 이벤트 추가
					newRow.innerHTML += folderConfigTd;
					newRow.classList.add('folder');
					newRow.setAttribute('data-folder-name', file.name);
					//				newRow.addEventListener('dblclick', () => loadFolderContents(file.name));
				}
				newRow.classList.add('newRow');
				newRow.style.cursor = 'default';

				cloudBody.appendChild(newRow);
				document.querySelector('#tgl').classList.remove('toggleTr');
			}
		});

		// 상위 폴더로 이동 버튼 추가 (루트 폴더가 아닐 경우)
		if (currentPath !== '') {
			const upButton = document.createElement('button');
			upButton.classList.add('upBtn');
			upButton.textContent = '상위 폴더로';
			//			upButton.addEventListener('click', goToParentFolder);
			//			upButton.onclick = goToParentFolder;
			cloudBody.insertBefore(upButton, cloudBody.firstChild);
		}

		cloudBody.innerHTML += plusTr;
		//		saveCurrentPath(); // 현재 경로 저장
	}

	let selectFunc = function(e) {
		// 이전에 선택된 행이 있으면 클래스 제거
		if (selectedRow) {
			console.log('선택된 행', selectedRow);
			console.log('클래스?', selectedRow.classList);
			selectedRow.classList.remove('selected');
		}
		// 현재 클릭된 행에 selected 클래스 추가
		$(this).addClass('selected');
		// 현재 클릭된 행을 selectedRow 변수에 저장
		selectedRow = this;
	}

	//	$(document).on('dbclick', '.folder', loadFolderContents(file.name));

	let loadFolderContents = function(folderName) {
		if (folderName === '/') {
			folderName = '';
		}

		// 폴더 이름이 비어있지 않고 '/'로 끝나지 않으면 추가
		if (folderName !== '' && !folderName.endsWith('/')) {
			folderName = folderName + '/';
		}
		let errPath = currentPath;
		currentPath = folderName;

		axios.get(`${contextPath}/gcp/${projectId}/listFiles`, { params: { folderName: folderName } })
			.then(resp => {
				currentPath = folderName;
				// 기존 내용을 지우고 새로운 내용으로 교체
				displayFiles(resp.data);
			})
			.catch(error => {
				console.error('폴더 내용을 불러오는 중 오류가 발생했습니다:', error);
				alert('폴더 접근 권한이 없습니다.');
				currentPath = errPath;
			});
	}

	let goToParentFolder = function() {
		const parentPath = currentPath.split('/').slice(0, -2).join('/') + '/';
		loadFolderContents(parentPath);
	}

	//	let listFunc = function() {
	//		$('#cloudBody').html('');
	//		axios.get(`${contextPath}/api/s3/list-root`)
	//			.then(resp => {
	//				displayFiles(resp.data);
	//			})
	//			.catch(error => {
	//				console.error('목록을 불러오는 중 오류가 발생했습니다:', error);
	//			});
	//	};
	//
	//	listFunc();

	async function initializeFileList() {
		//		currentPath = loadCurrentPath();
		if (currentPath == '/') {
			currentPath = '';
		}
		try {
			if (currentPath) {
				await loadFolderContents(currentPath);
			} else {
				const response = await axios.get(`${contextPath}/gcp/${projectId}/listFiles`, { params: { folderName: currentPath } });
				await displayFiles(response.data);
			}
		} catch (error) {
			console.error('목록을 불러오는 중 오류가 발생했습니다:', error);
		}
		console.log('currentPath:', currentPath);
	}

	window.addEventListener('load', initializeFileList);

	// 파일 인서트 버튼 클릭
	let fileInsertFunc = function(e) {
		e.preventDefault();
		$('#fileInput').click();
	}

	// 파일 인서트 이후 파일 선택
	let fileInput = function(e) {
		var file = e.target.files[0];
		if (file) {
			// FormData 객체 생성
			var formData = new FormData();

			// currentPath가 '/'인 경우 빈 문자열로 설정
			let path = currentPath === '/' ? '' : currentPath;

			// 파일명과 폴더 경로를 FormData에 추가
			formData.append('file', file);
			formData.append('folderPath', path);

			// Axios로 파일 업로드 요청 보내기
			axios.post(`${contextPath}/gcp/${projectId}/upload`, formData, {
				headers: {
					'Content-Type': 'multipart/form-data' // Content-Type을 명시적으로 설정
				}
			}).then(resp => {
				// 업로드 성공 시 처리할 내용
				console.log('파일 업로드 성공:', resp.data);
				loadFolderContents(currentPath); // 목록 새로고침
			}).catch(error => {
				// 업로드 실패 시 처리할 내용
				console.error('파일 업로드 실패:', error);
			});
		}
		console.log("input 이후:", currentPath);
	};


	let folderModal = new bootstrap.Modal(document.getElementById('folderModal'));
	let folderInsertFunc = function(e) {
		e.preventDefault();

		// 모달 표시
		folderModal.show();


	};

	// 폴더 생성 버튼 클릭 이벤트
	let createFolder = function() {
		let folderName = document.getElementById('folderNameInput').value.trim();
		if (folderName !== null && folderName.trim() !== "") {
			// currentPath가 '/'인 경우 빈 문자열로 설정
			let path = currentPath === '/' ? '' : currentPath;

			// 폴더명을 현재 경로에 맞게 설정
			let fullPath = `${path}${folderName}`; // 폴더명 끝에 슬래시 추가

			// 서버에 폴더 생성 요청 보내기
			axios.post(`${contextPath}/gcp/${projectId}/createFolder`, { folderName: fullPath })
				.then(response => {
					console.log("폴더가 성공적으로 생성되었습니다:", response.data);
					folderModal.hide();
					folderName = '';
					loadFolderContents(currentPath); // 목록 새로고침
					console.log(currentPath);
				})
				.catch(error => {
					console.error("폴더 생성 중 오류 발생:", error);
				});
		} else {
			console.log("폴더 생성이 취소되었습니다.");
		};
	}

	let deleteFunc = function(e) {
		let tg = e.target;
		let fileType = $(tg).closest('tr').find("input[class='fileType']").val();
		let fileName = $(tg).closest('tr').find('td.fileName').text();
		let folderName = currentPath;
		let trueName = encodeURIComponent(folderName + fileName);
		if (confirm('정말로 삭제하시겠습니까?')) {
			if (fileType == 'file') {
				axios.delete(`${contextPath}/gcp/${projectId}/delete?fileName=${trueName}`).then(resp => {
					console.log("파일이 성공적으로 삭제됨");
					loadFolderContents(currentPath);
				}).catch(error => {
					console.error("파일 삭제중 에러", error);
				})
			} else {
				axios.delete(`${contextPath}/gcp/${projectId}/deleteFolder?folderName=${trueName}`).then(resp => {
					console.log('폴더가 성공적으로 삭제됨');
					loadFolderContents(currentPath);
				}).catch(error => {
					console.error("파일 삭제중 에러", error);
				})
			}
		} else {

		}
	}

	let downloadFunc = function(e) {
		e.preventDefault(); // 기본 링크 클릭 동작 방지

		let tg = e.target;
		let fileName = $(tg).closest('tr').find('td.fileName').text();
		let folderName = currentPath;
		let trueName = encodeURIComponent(folderName + fileName);

		// 다운로드 요청을 위한 URL 생성
		let downloadUrl = `${contextPath}/gcp/${projectId}/download?fileName=${trueName}`;

		// 새로운 창 또는 탭에서 다운로드 요청
		window.location.href = downloadUrl;
	}

	// 모달 열기
	let showPreview = function(e) {
		e.preventDefault();
		let tg = e.target;
		let fileName = $(tg).closest('tr').find('td.fileName').text();
		let folderName = currentPath;
		let trueName = encodeURIComponent(folderName) + encodeURIComponent(fileName);
		axios.get(`${contextPath}/gcp/${projectId}/preview`, { params: { fileName: trueName } })
			.then(response => {
				const url = response.data;
				const fileExtension = trueName.split('.').pop().toLowerCase();

				// 모달 내용 요소
				const modalBody = document.getElementById('modal-body');
				const downloadBtn = document.getElementById('download-btn');

				// 미리보기 요소 초기화
				modalBody.innerHTML = '';

				if (['jpg', 'jpeg', 'png', 'gif'].includes(fileExtension)) {
					modalBody.innerHTML = `<img src="${url}" class="preview-img" alt="Image Preview">`;
				} else if (['mp4', 'webm', 'ogg'].includes(fileExtension)) {
					modalBody.innerHTML = `<video src="${url}" class="preview-video" controls>Your browser does not support the video tag.</video>`;
				} else if (fileExtension === 'pdf') {
					modalBody.innerHTML = `<embed src="${url}" class="preview-pdf" type="application/pdf">`;
				} else {
					modalBody.innerHTML = '<p>미리보기를 지원하지 않는 파일 형식입니다.</p>';
				}

				// 다운로드 버튼 설정
				downloadBtn.onclick = function() {
					window.location.href = `${contextPath}/gcp/${projectId}/download?fileName=${trueName}`;
				};

				// 모달 표시
				document.getElementById('preview-modal').style.display = 'block';
			})
			.catch(error => {
				console.error('미리보기 로딩 중 오류 발생:', error);
			});
	}


	// 모달 닫기
	document.getElementById('close-btn').onclick = function() {
		document.getElementById('preview-modal').style.display = 'none';
	}

	// 모달 외부 클릭 시 닫기
	window.onclick = function(event) {
		if (event.target === document.getElementById('preview-modal')) {
			document.getElementById('preview-modal').style.display = 'none';
		}
	}

	let accessFolderName = '';
	let accessModalBtn = function(e) {
		e.preventDefault();
		let tg = e.target;
		accessFolderName = $(tg).closest('tr').find('td.fileName').text();
		document.getElementById('access-modal').style.display = 'block';
	}

	let folderAccess = function(e) {
		e.preventDefault();
		let selectedRoles = [];
		$('.role-checkbox:checked').each(function() {
			selectedRoles.push($(this).closest('tr').find('input.role-idx').val());
		});
		let folderName = currentPath + accessFolderName + '/';
		let trueName = encodeURIComponent(projectId + '/' + folderName);
		axios.post(`${contextPath}/gcp/projects/${projectId}/folder-access`, {
			projectRoleIdx: selectedRoles
			, folderPattern: trueName
		}).then(resp => {
			document.getElementById('access-modal').style.display = 'none';
		}).catch(error => {
			console.error(error);
		})
	}

	// 모달 닫기
	document.getElementById('close-acs').onclick = function() {
		document.getElementById('access-modal').style.display = 'none';
	}

	// 모달 외부 클릭 시 닫기
	window.onclick = function(event) {
		if (event.target === document.getElementById('access-modal')) {
			document.getElementById('access-modal').style.display = 'none';
		}
	}

	let searchFunc = function(e) {
		e.preventDefault();
		if (e.keyCode === 13) {
			searchName = $('#searchName').val();
			if (searchName == null || searchName == '') {
				loadFolderContents(currentPath);
			} else {

				axios.get(`${contextPath}/gcp/${projectId}/search`, { params: { searchName: searchName } })
					.then(resp => {
						displayFiles(resp.data);
						document.querySelector('#tgl').classList.add('toggleTr');
					}).catch(err => {
						console.error(err);
					})
			}
		}
	}

	let searchFuncBtn = function(e) {
		e.preventDefault();
		searchName = $('#searchName').val();
		if (searchName == null || searchName == '') {
			loadFolderContents(currentPath);
		} else {

			axios.get(`${contextPath}/gcp/${projectId}/search`, { params: { searchName: searchName } })
				.then(resp => {
					displayFiles(resp.data);
					document.querySelector('#tgl').classList.add('toggleTr');
				}).catch(err => {
					console.error(err);
				})
		}
	}


	$(document).on('keyup', '#searchName', searchFunc);
	$(document).on('click', '#searchBtn', searchFuncBtn);
	$(document).on('click', '.filePreview', showPreview);
	$(document).on('dblclick', '.file', showPreview);
	$(document).on('click', '.newRow', selectFunc);
	$(document).on('click', '.fileAccessModal', accessModalBtn);
	$(document).on('click', '#access-btn', folderAccess);
	$(document).on('click', '.fileDownloadBtn', downloadFunc);
	$(document).on('click', '.fileDelBtn', deleteFunc);
	$(document).on('click', '#fileInsert', fileInsertFunc);
	$(document).on('click', '.upBtn', goToParentFolder);
	$(document).on('click', '#folderInsert', folderInsertFunc);
	$(document).on('change', '#fileInput', fileInput);
	$(document).on('click', '#createFolderBtn', createFolder);
	$(document).off('dblclick', '.folder'); // 기존 리스너 제거
	$(document).on('dblclick', '.folder', function() {
		const folderName = currentPath + $(this).data('folder-name');
		loadFolderContents(folderName);
	});


})