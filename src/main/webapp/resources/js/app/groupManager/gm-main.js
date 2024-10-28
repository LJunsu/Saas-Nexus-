/**
 * 
 */
document.addEventListener('DOMContentLoaded', ()=>{
	const contextPath = document.body.dataset.contextPath;
	const groupId = $('#myGroupId').val();
	
	let delFunc = function(e) {
		e.preventDefault();
		let tg = e.target;
		let form = $(tg).closest('form');
		if (confirm('정말로 삭제하시겠습니까?')) {
	        form.submit();
	    }
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
	
	$(document).on('click', '.delBtn', delFunc);
})