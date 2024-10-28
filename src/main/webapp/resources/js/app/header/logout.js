/**
 *
 */
document.addEventListener('DOMContentLoaded', () => {
	const contextPath = document.body.dataset.contextPath;
	const groupId = $('#myGroupId').val();
	function logout() {
		axios.post(`${contextPath}/logout.do`)
			.then(resp => {
				console.log('Logout successful');
				// 로그아웃 후 처리 (예: 로그인 페이지로 리다이렉트)
				window.location.href = `${contextPath}/login.do`;
			}).error(err => {
				console.error(err);
			});
	}

	$('#logoutBtn').on('click', (e) => {
		e.preventDefault();
		logout();
	})

	let useNeed = function(e) {
		e.preventDefault();

		Swal.fire({
			icon: 'warning',
			title: '구독이 필요한 서비스입니다!',
			confirmButtonText: '확인',
		}).then((result) => {
			if (result.isConfirmed) {
				// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션
				window.location.href = `${contextPath}/group/${groupId}/billing/subscription`;
			}
		});
	}
	
	let useNeedUser = function(e) {
		e.preventDefault();

		Swal.fire({
			icon: 'warning',
			title: '구독이 필요한 서비스입니다!',
			confirmButtonText: '확인',
		}).then((result) => {
			if (result.isConfirmed) {
				// 팝업 확인 버튼이 클릭되면 페이지를 리디렉션
			}
		});
	}

	$(document).on('click', '.useNeed', useNeed);
	$(document).on('click', '.useNeedUser', useNeedUser);

})