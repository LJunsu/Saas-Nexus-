document.addEventListener('DOMContentLoaded', () => {
	const contextPath = document.body.dataset.contextPath;
	const groupId = $('#groupId').val();
	let modalBtn = $('.my-modal');
	var myModalEl = document.getElementById('myModal')
	var modal = new bootstrap.Modal(document.getElementById('myModal'));
	let memberId = '';
	modalBtn.on('click', (e) => {
		e.preventDefault();
		let tg = e.target
		modal.show();
		myModalEl.addEventListener('shown.bs.modal', (event) => {
			memberId = $(tg).closest('tr').find('td.memberId').text();
			axios.get(`${contextPath}/group/${groupId}/memberManage/groupMember/${memberId}`)
				.then(resp => {
					bodyhtml = `<h4>인적사항</h4>
						<table class="table table-bordered">
						<div class="image-container"><img id="image" alt="" width="100" height="130" src="${resp.data.groupMemberVO.member.memberProfilePict}"></div>
						<tr>
						<td>회원ID</td>
						<td class='memberId'>${resp.data.groupMemberVO.memberId}</td>
						</tr>
						<tr>
						<td>이름</td>
						<td>${resp.data.groupMemberVO.member.memberName}</td>
						</tr>
						<tr>
						<td>주소</td>
						<td>${resp.data.groupMemberVO.member.memberAddress1}<br>${resp.data.groupMemberVO.member.memberAddress2}</td>
						</tr>
						<tr>
						<td>연락처</td>
						<td>${resp.data.groupMemberVO.member.memberTel}</td>
						</tr>
						<tr>
						<td>이메일</td>
						<td>${resp.data.groupMemberVO.member.memberEmail}</td>
						</tr>
						<tr>
						<td>가입일</td>
						<td>${resp.data.groupMemberVO.groupJoinDate}</td>
						</tr>
						<tr>
						<td>소개글</td>
						<td>${resp.data.groupMemberVO.member.memberDescription}</td>
						</tr>
						</table>
					`;


					$('.modal-body').html(bodyhtml);
				});

			$('#delMemberBtn').on('click', (ev) => {
				ev.preventDefault();
				if (confirm('정말로 탈퇴시키겠습니까?')) {

					axios.post(`${contextPath}/group/${groupId}/memberManage/groupMember/${memberId}/deleteGroupMember`)
						.then((resp) => {
							window.location.reload();
						})
				}
			})
		})
	})

//	let searchFunc = function(e) {
//		e.preventDefault();
//		let memberName = $('#searchName').val();
//		axios.get(`${contextPath}/group/${groupId}/memberManage/groupMember/search`, { params: { memberName: memberName } })
//			.then(resp => {
//				let tableBody = $('#DataTables_Table_0 tbody');
//				tableBody.empty(); // 기존 테이블 내용을 비웁니다.
//
//				if (resp.data.groupMemberVOList && resp.data.groupMemberVOList.length > 0) {
//					resp.data.groupMemberVOList.forEach(groupMember => {
//						let row = `
//                        <tr>
//                            <td class="memberId">${groupMember.memberId}</td>
//                            <td style="width: 10px"><a href="javascript::" class="my-modal">${groupMember.member.memberName}</a></td>
//                            <td>${groupMember.member.memberEmail}</td>
//                            <td>${groupMember.member.memberTel}</td>
//                            <td>${groupMember.member.memberAddress1}</td>
//                        </tr>
//                    `;
//						tableBody.append(row);
//					});
//				} else {
//					tableBody.append('<tr><td colspan="5">검색된 그룹원 없음</td></tr>');
//				}
//			})
//	}

	$(document).on('click', '#searchBtn', searchFunc);
})