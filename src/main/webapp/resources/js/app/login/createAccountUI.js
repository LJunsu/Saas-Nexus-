/**
 * 
 */
document.addEventListener('DOMContentLoaded', ()=>{
	
	const contextPath = document.body.dataset.contextPath;
	let fileUrl = '';
	
	let fileInput = function(e) {
		var file = e.target.files[0];
		console.log(file);
		if (file) {
			// FormData 객체 생성
			var formData = new FormData();
			var path = 'pictures/';
			var filename = file.name;
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
				$('#file-name').html(filename);
			}).catch(error => {
				// 업로드 실패 시 처리할 내용
				console.error('파일 업로드 실패:', error);
			});
		}
	};
	
	let preview = function(url) {
		let trueName = encodeURIComponent(url);
		const fileExtension = trueName.split('.').pop().toLowerCase();
		console.log("파일 확장자 체크", fileExtension);
		$('#image').attr('src', url);
		
		/*
		axios.get(`${contextPath}/gcp/filePreview`, { params: { fileName: trueName } })
			.then(response => {
				const url = response.data;
				const fileExtension = trueName.split('.').pop().toLowerCase();

				// 미리보기 요소 초기화
				$('#image').attr('src', '');

				if (['jpg', 'jpeg', 'png', 'gif'].includes(fileExtension)) {
					$('#image').attr('src', url);
				}

			})
			.catch(error => {
				console.error('미리보기 로딩 중 오류 발생:', error);
			});
			*/
			
			
	}
	
	let canFunc = function(e) {
		e.preventDefault();
		window.location.href = `${contextPath}/login.do`;
	}
	
	let AutoBtn = function(e) {
		e.preventDefault();
		$('#memberId').val('nexus777');
		$('#memberPw').val('java');
		$('#memberName').val('김상현');
		$('#memberRegno1').val('910317');
		$('#memberRegno2').val('1234567');
		$('#sample6_postcode').val('06134');
		$('#sample6_address').val('서울특별시 강남구 테헤란로 152');
		$('#sample6_detailAddress').val('강남파이낸스센터 1807호');
		$('#memberTel').val('010-1234-5678');
		$('#memberEmail').val('tpwotpwo13@gmail.com');
		$('#memberDescription').val('28살 직장인으로, 주말에는 등산을 즐기는 열정적인 아웃도어 애호가입니다.');
		$('#memberAuthQuestion').val(2);
		$('#memberAuthResponse').val('서울');
	}
	
	$(document).on('change', '#memberImage', fileInput);
	$(document).on('click', '#cancelBtn', canFunc);
	$(document).on('click', '#UpdateAuto', AutoBtn);
})