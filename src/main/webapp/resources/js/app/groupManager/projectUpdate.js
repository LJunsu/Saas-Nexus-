document.addEventListener("DOMContentLoaded", () => {

	let fileUrl = '';

	function initializeCustomSelect() {
		let customSelects = document.querySelectorAll('.custom-select');

		customSelects.forEach(function(customSelect) {
			let selectSelected = customSelect.querySelector('.select-selected');
			let selectItems = customSelect.querySelector('.select-items');
			let realSelect = customSelect.nextElementSibling.querySelector('select');


			selectSelected.addEventListener('click', function(e) {
				e.stopPropagation();
				closeAllSelect(this);
				this.nextElementSibling.classList.toggle('select-hide');
				this.classList.toggle('select-arrow-active');

			});

			let optionDivs = selectItems.getElementsByTagName('div');
			for (let i = 0; i < optionDivs.length; i++) {
				optionDivs[i].addEventListener('click', function(e) {
					e.stopPropagation();
					let value = this.getAttribute('data-value');
					let option = realSelect.querySelector(`option[value="${value}"]`);
					option.selected = !option.selected;
					this.classList.toggle('same-as-selected');
					updateSelectText(selectSelected, realSelect);
				});
			}
		});
	}

	function updateSelectText(selectSelected, realSelect) {
		let selectedOptions = Array.from(realSelect.selectedOptions).map(option => option.textContent);
		selectSelected.textContent = selectedOptions.length > 0 ? selectedOptions.join(', ') : '역할 선택';
	}

	function closeAllSelect(elmnt) {
		let selectItems = document.getElementsByClassName('select-items');
		let selectSelected = document.getElementsByClassName('select-selected');
		for (let i = 0; i < selectSelected.length; i++) {
			if (elmnt != selectSelected[i]) {
				selectSelected[i].classList.remove('select-arrow-active');
			}
		}
		for (let i = 0; i < selectItems.length; i++) {
			if (elmnt != selectItems[i] && elmnt != selectSelected[i]) {
				selectItems[i].classList.add('select-hide');
			}
		}
	}

	document.addEventListener('click', closeAllSelect);

	const contextPath = document.body.dataset.contextPath;
	const projectId = $("#projectId").val();
	const roleInsertFunc = function(e) {
		e.preventDefault();
		let projectRoleName = $('#projectRoleName').val();

		axios.post(`${contextPath}/group/project/roleInsert`, {
			projectId: projectId
			, projectRoleName: projectRoleName
		}).then(resp => {
			let roleListHtml = '';
			let selectHtml = '';
			let selectItems = $('.select-items');
			console.log(selectItems);
			let roleSelect = `<select style="display: none;">
											<option value="">역할 선택</option>`;
			resp.data.forEach(role => {
				console.log("aaa", role);
				roleListHtml += `<tr>
									<input type="hidden" class="roleName" value="${role.projectRoleIdx}">
									<input type="hidden" class="prn" value="${role.projectRoleName}">
									<td>${role.projectRoleName}</td>
									<td><button class="roleDelBtn btn btn-danger btn-sm">제거</button></td>
								</tr>`;
				roleSelect += `<option value="${role.projectRoleIdx}">${role.projectRoleName}</option>`;
				selectHtml += `<div data-value="${role.projectRoleIdx}">${role.projectRoleName}</div>`;
			});
			roleSelect += `</select>`;
			$('#roleList').html(roleListHtml);
			$('.selecter').html(roleSelect);
			selectItems.html(selectHtml);
			$('#projectRoleName').val('');
			initializeCustomSelect();

		}).catch(error => {
			console.log(error);
			alert("중복된 역할명!");
		})
		initializeCustomSelect();
	}

	const roleDelFunc = function(e) {
		e.preventDefault();
		let tg = e.target;
		let projectRoleIdx = $(tg).closest('tr').find('.roleName').val();
		let prn = $(tg).closest('tr').find('.prn').val();
		if (prn == '프로젝트 리더') {
			alert('프로젝트 리더 제거 불가');
			return;
		}

		axios.post(`${contextPath}/group/project/roleDelete`, {
			projectId: projectId
			, projectRoleIdx: projectRoleIdx
		}).then(resp => {
			let roleListHtml = '';
			let selectHtml = '';
			let selectItems = $('.select-items');
			let roleSelect = `<select style="display: none;">
											<option value="">역할 선택</option>`;
			resp.data.forEach(role => {
				roleListHtml += `<tr>
									<input type="hidden" class="roleName" value="${role.projectRoleIdx}">
									<input type="hidden" class="prn" value="${role.projectRoleName}">
									<td>${role.projectRoleName}</td>
									<td><button class="roleDelBtn btn btn-danger btn-sm">제거</button></td>
								</tr>`;
				roleSelect += `<option value="${role.projectRoleIdx}">${role.projectRoleName}</option>`;
				selectHtml += `<div data-value="${role.projectRoleIdx}">${role.projectRoleName}</div>`;
			});
			roleSelect += `</select>`;
			$('#roleList').html(roleListHtml);
			$('.selecter').html(roleSelect);
			selectItems.html(selectHtml);
			initializeCustomSelect();

		}).catch(error => {
			console.log(error);
			alert("현재 사용중인 역할입니다.");
		})
		initializeCustomSelect();
	}

	const groupId = $('#groupId').val();
	const insertFunc = function(e) {
		e.preventDefault();
		let aa = e.target;
		let closeTr = $(aa).closest('tr');
		let memberId = closeTr.find('.memberId').val();
		let selectedRole = closeTr.find('select').val();
		if (selectedRole == null || selectedRole == '') {
			alert('역할을 선택해주세요');
			return;
		}

		axios.post(`${contextPath}/group/${groupId}/project/${projectId}/memberInsert`, {
			memberId: memberId
			, selectedRole: selectedRole
			, groupId: groupId
			, projectId: projectId
		}).then(resp => {
			let projectMember = '';
			let groupMember = '';
			if (resp.data && resp.data.pmList) {
				resp.data.pmList.forEach(pm => {
					projectMember += `<tr>
                            <td>${pm.groupMember.member.memberName}</td>
                            <td>${pm.groupMember.member.memberEmail}</td>
                            <td><table class="table">`;
					pm.projectRole.forEach((role, i) => {
						let idxValue = pm.projectMemIdxes[i];
						projectMember += `<tr>
												<input type="hidden" class="projectMemIdx" value="${idxValue}">
												<td>${role.projectRoleName}</td>
												<td><button class="btn btn-sm btn-danger projectMemberDelete">역할 제거</button></td>
											</tr>`;
					});
					projectMember += `</table></td>
                        </tr>`;
				});
			} else {
				console.log('pmList가 없거나 비어있습니다.');
			}
			if (resp.data && resp.data.groupList) {
				resp.data.groupList.forEach(gm => {
					groupMember += `<tr>
                                <input type="hidden" class="memberId" value="${gm.memberId}">
                                <td>${gm.member.memberName}</td>
                                <td>${gm.member.memberEmail}</td>
                                <td class="roleSelect">
                                    <div class="custom-select">
                                        <div class="select-selected">
                                            <span>역할 선택</span> <i class="fas fa-chevron-down"></i>
                                        </div>
                                        <div class="select-items select-hide">
                                        `
					if (resp.data.prList) {
						resp.data.prList.forEach(role => {
							groupMember += `<div data-value="${role.projectRoleIdx}">${role.projectRoleName}</div>`;
						});
					}
					groupMember += `
                                        </div>
									</div> <div class="selecter"> <select style="display: none;">
										<option value="">역할 선택</option>
                                        `;
					if (resp.data.prList) {
						resp.data.prList.forEach(role => {
							groupMember += `<option value="${role.projectRoleIdx}">${role.projectRoleName}</option>`;
						});
					}
					groupMember += `
                                        </select>
									</div>
                                    </div>
                                </td>
                                <td><button class="projectMemberInsert btn btn-sm btn-primary">프로젝트 인원 등록</button></td>
                            </tr>`;
				});
			} else {
				console.log('groupList가 없거나 비어있습니다.');
			}
			$('#projectMember').html(projectMember);
			$("#groupMemberList").html(groupMember);

			initializeCustomSelect(); // Reinitialize custom select

		}).catch(error => {
			console.error('Error:', error);
			if (error.response) {
				// 서버가 2xx 범위를 벗어나는 상태 코드를 반환한 경우
				if (error.response.status === 404) {
					alert('요청한 리소스를 찾을 수 없습니다.');
				} else if (error.response.status === 500) {
					alert("프로젝트 리더 중복 불가");
				} else {
					alert('오류가 발생했습니다: ' + error.response.data.message);
				}
			} else if (error.request) {
				// 요청은 보냈지만 응답을 받지 못한 경우
				alert('서버로부터 응답을 받지 못했습니다. 인터넷 연결을 확인해 주세요.');
			} else {
				// 요청 설정 중에 오류가 발생한 경우
				alert('요청 설정 중 오류가 발생했습니다: ' + error.message);
			}
		});
	}

	const deleteFunc = function(e) {
		e.preventDefault();
		let aa = e.target;
		let closeTr = $(aa).closest('tr');
		let projectMemIdx = closeTr.find('.projectMemIdx').val();
		if (confirm('정말로 삭제하시겠습니까?')) {

			axios.post(`${contextPath}/group/${groupId}/project/${projectId}/memberDelete`, {
				groupId: groupId
				, projectId: projectId
				, projectMemIdx: projectMemIdx
			}).then(resp => {
				let projectMember = '';
				let groupMember = '';
				if (resp.data && resp.data.pmList) {
					resp.data.pmList.forEach(pm => {
						projectMember += `<tr>
                            <td>${pm.groupMember.member.memberName}</td>
                            <td>${pm.groupMember.member.memberEmail}</td>
                            <td><table class="table">`;
						pm.projectRole.forEach(role => {
							//							console.log("pm", pm);
							idxValue = pm.projectMemIdxes[0];
							//							console.log("aaa",idxValue);
							projectMember += `<tr>
												<input type="hidden" class="projectMemIdx" value="${idxValue}">
												<td>${role.projectRoleName}</td>
												<td><button class="btn btn-sm btn-danger projectMemberDelete">역할 제거</button></td>
											</tr>`;
						});
						projectMember += `</table></td>
                        </tr>`;
					});
				} else {
					console.log('pmList가 없거나 비어있습니다.');
				}
				if (resp.data && resp.data.groupList) {
					resp.data.groupList.forEach(gm => {

						groupMember += `<tr>
                                <input type="hidden" class="memberId" value="${gm.memberId}">
                                <td>${gm.member.memberName}</td>
                                <td>${gm.member.memberEmail}</td>
                                <td class="roleSelect">
                                    <div class="custom-select">
                                        <div class="select-selected">
                                            <span>역할 선택</span> <i class="fas fa-chevron-down"></i>
                                        </div>
                                        <div class="select-items select-hide">
                                        `
						if (resp.data.prList) {
							resp.data.prList.forEach(role => {
								groupMember += `<div data-value="${role.projectRoleIdx}">${role.projectRoleName}</div>`;
							});
						}
						groupMember += `
                                        </div>
									</div> <div class="selecter"> <select style="display: none;">
										<option value="">역할 선택</option>
                                        `;
						if (resp.data.prList) {
							resp.data.prList.forEach(role => {
								groupMember += `<option value="${role.projectRoleIdx}">${role.projectRoleName}</option>`;
							});
						}
						groupMember += `
                                        </select>
									</div>
                                    </div>
                                </td>
                                <td><button class="projectMemberInsert btn btn-sm btn-primary">프로젝트 인원 등록</button></td>
                            </tr>`;
					});
				} else {
					console.log('groupList가 없거나 비어있습니다.');
				}
				$('#projectMember').html(projectMember);
				$("#groupMemberList").html(groupMember);

				initializeCustomSelect(); // Reinitialize custom select

			}).catch(error => {
				console.error('Error:', error);

				if (error.response) {
					// 서버가 2xx 범위를 벗어나는 상태 코드를 반환한 경우
					if (error.response.status === 404) {
						alert('요청한 리소스를 찾을 수 없습니다.');
					} else if (error.response.status === 500) {
						alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
					} else {
						alert('오류가 발생했습니다: ' + error.response.data.message);
					}
				} else if (error.request) {
					// 요청은 보냈지만 응답을 받지 못한 경우
					alert('서버로부터 응답을 받지 못했습니다. 인터넷 연결을 확인해 주세요.');
				} else {
					// 요청 설정 중에 오류가 발생한 경우
					alert('요청 설정 중 오류가 발생했습니다: ' + error.message);
				}
			});
		}
	}

	const groupMemberSearch = function(e) {
		e.preventDefault();

		let searchName = $('#searchMember').val();
		axios.post(`${contextPath}/group/${groupId}/project/${projectId}/groupMemberSearch`, {
			groupId: groupId
			, projectId: projectId
			, searchName: searchName
		}).then(resp => {
			let groupMember = '';
			if (resp.data && resp.data.searchList) {
				resp.data.searchList.forEach(gm => {

					groupMember += `<tr>
                                <input type="hidden" class="memberId" value="${gm.memberId}">
                                <td>${gm.member.memberName}</td>
                                <td>${gm.member.memberEmail}</td>
                                <td class="roleSelect">
                                    <div class="custom-select">
                                        <div class="select-selected">
                                            <span>역할 선택</span> <i class="fas fa-chevron-down"></i>
                                        </div>
                                        <div class="select-items select-hide">
                                        `
					if (resp.data.prList) {
						resp.data.prList.forEach(role => {
							groupMember += `<div data-value="${role.projectRoleIdx}">${role.projectRoleName}</div>`;
						});
					}
					groupMember += `
                                        </div>
									</div> <div class="selecter"> <select style="display: none;">
										<option value="">역할 선택</option>
                                        `;
					if (resp.data.prList) {
						resp.data.prList.forEach(role => {
							groupMember += `<option value="${role.projectRoleIdx}">${role.projectRoleName}</option>`;
						});
					}
					groupMember += `
                                        </select>
									</div>
                                    </div>
                                </td>
                                <td><button class="projectMemberInsert btn btn-sm btn-primary">프로젝트 인원 등록</button></td>
                            </tr>`;
				});
			} else {
				console.log('groupList가 없거나 비어있습니다.');
			}
			$("#groupMemberList").html(groupMember);

			initializeCustomSelect(); // Reinitialize custom select
			$('#searchMember').val('');

		}).catch(error => {
			console.error('Error:', error);

			if (error.response) {
				// 서버가 2xx 범위를 벗어나는 상태 코드를 반환한 경우
				if (error.response.status === 404) {
					alert('요청한 리소스를 찾을 수 없습니다.');
				} else if (error.response.status === 500) {
					alert('서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.');
				} else {
					alert('오류가 발생했습니다: ' + error.response.data.message);
				}
			} else if (error.request) {
				// 요청은 보냈지만 응답을 받지 못한 경우
				alert('서버로부터 응답을 받지 못했습니다. 인터넷 연결을 확인해 주세요.');
			} else {
				// 요청 설정 중에 오류가 발생한 경우
				alert('요청 설정 중 오류가 발생했습니다: ' + error.message);
			}
		});
	}

	$('#deleteProject').on('click', (e) => {
		e.preventDefault();
		if (confirm('정말로 삭제하시겠습니까?')) {
			alert('삭제되었습니다.');
			delForm.requestSubmit();
		} else {

		}
	})

	let fileInput = function(e) {
		var file = e.target.files[0];
		console.log(file);
		if (file) {
			// FormData 객체 생성
			var formData = new FormData();
			var path = 'pictures/';

			// 파일명과 폴더 경로를 FormData에 추가
			formData.append('file', file);
			formData.append('folderPath', path);

			// Axios로 파일 업로드 요청 보내기
			axios.post(`${contextPath}/gcp/upload`, formData)
				.then(resp => {
					// 업로드 성공 시 처리할 내용
					fileUrl = resp.data;
					console.log('파일 업로드 성공:', resp.data);
					preview(fileUrl);
				}).catch(error => {
					// 업로드 실패 시 처리할 내용
					console.error('파일 업로드 실패:', error);
				});
		}
	};

	let preview = function(url) {
		console.log(url);
		$('#image').attr('src', url);
	}

	let AutoBtn = function(e) {
		e.preventDefault();
		$('#projectName').val('스마트시티 통합 관제 시스템 구축');
		$('#projectDescription').val('본 프로젝트는 도시 전체의 교통, 환경, 안전, 에너지 등 다양한 분야의 데이터를 실시간으로 수집하고 분석하여 효율적인 도시 운영을 가능케 하는 통합 관제 시스템을 구축하는 것을 목표로 합니다. IoT 센서, 빅데이터 분석, AI 기술을 활용하여 도시 문제를 사전에 예측하고 대응할 수 있는 지능형 플랫폼을 개발합니다. 시민들의 삶의 질 향상과 도시 자원의 최적화를 통해 지속 가능한 도시 발전을 이루고자 합니다.');
		$('#projectEndDate').val('2025-06-30');
		$('#projectBudget').val('500000000');
		let roles = ['시스템 아키텍트', '데이터 사이언티스트', 'UI/UX 디자이너', '품질 관리자', 'IoT 전문가', '행정 지원'];

		function addRole(role) {
			return new Promise((resolve) => {
				setTimeout(() => {
					$('#projectRoleName').val(role);
					$('#roleInsert').click();
					resolve();
				}, 200);
			});
		}

		async function addAllRoles() {
			for (let role of roles) {
				await addRole(role);
			}
		}

		addAllRoles();
	}

	$(document).on('click', '.projectMemberInsert', insertFunc);
	$(document).on('click', '.projectMemberDelete', deleteFunc);
	$(document).on('click', '#groupMemberSearch', groupMemberSearch);
	$(document).on('click', '#roleInsert', roleInsertFunc);
	$(document).on('click', '.roleDelBtn', roleDelFunc);
	$(document).on('change', '#projectImg', fileInput);
	$(document).on('click', '#UpdateAuto', AutoBtn);

	initializeCustomSelect(); // Initialize on DOM ready
});
