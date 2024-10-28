/**
 * 
 */
document.addEventListener('DOMContentLoaded', () => {
	const contextPath = document.body.dataset.contextPath;
	const groupId = $('#groupId').val();
	const groupName = $('#groupName').val();
	var emailModalEl = document.getElementById('emailModal')
	var emailModal = new bootstrap.Modal(document.getElementById('emailModal'));
	let emailInput = '';

	let toUser = '';
	const emailSearch = function(e) {
		e.preventDefault();
		emailModal.show();
		emailInput = $('#form-alignment-email').val();

	}

	const emailModalAppear = function(event) {
		axios.post(`${contextPath}/group/${groupId}/memberInvite/emailSearch`, {
			memberEmail: emailInput
		}).then(resp => {
			let trhtml = '';
			let trhtml2 = '';
			resp.data.memberManagementVOList.forEach(member => {
				
				if (member.groupMemberVO.groupId == null || member.groupMemberVO.groupDelyn == 'Y') {
					trhtml += `<tr>
							<td>${member.memberId}</td>
							<td>${member.memberName}</td>
							<td class="toUser">${member.memberEmail}</td>
							<td><button class="btn btn-primary invbtn">초대</button></td>
						</tr>`;
				} else {
					trhtml2 += `<tr>
							<td>${member.memberId}</td>
							<td>${member.memberName}</td>
							<td colspan='2'>이 회원은 이미 가입된 그룹이 있습니다.</td>
							</tr>`
				}
			});
			$('#emailhtml').html(trhtml);
			document.querySelector('#emailhtml').innerHTML += trhtml2;
		});

		//			const invbtn = function(ev) {
		//				ev.preventDefault();
		//				emailModal2.show();
		//				let evBtn = ev.target;
		//				toUser = $(evBtn).closest('tr').find('td.toUser').text();


		//				$(document).on('click', '#mailSend', inviteFunc);
		//			}


	}

	const inviteFunc = function(ev) {
		ev.preventDefault();
		let evBtn = ev.target;
		toUser = $(evBtn).closest('tr').find('td.toUser').text();
		axios.post(`${contextPath}/email/send`, {
			toUser: toUser,
			groupId: groupId,
			groupName: groupName
		}).then(() => {
			alert("초대 메일이 전송되었습니다.");
		}).catch((error) => {
			console.log(error);
			alert("메일 전송에 실패!");
		})
	}
	
	let AutoBtn = function(e) {
		e.preventDefault();
		$('#form-alignment-email').val('tpwotpwo13@gmail.com');
	}


	$(document).on('click', '.invbtn', inviteFunc);
	$(document).on('shown.bs.modal', emailModalEl, emailModalAppear);
	$(document).on('click', '#emailBtn', emailSearch);
	$(document).on('click', '#UpdateAuto', AutoBtn);
})
