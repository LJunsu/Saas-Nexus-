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
	
	let licenseUpload = function(e) {
		var file = e.target.files[0];
		var filename = file.name;
		console.log(file);
		$('#bus-file-name').html(filename);
	}
	
	let preview = function(url) {
		let trueName = encodeURIComponent(url);
		const fileExtension = trueName.split('.').pop().toLowerCase();
		console.log("파일 확장자 체크", fileExtension);
		$('#image').attr('src', url);
	}
	
	let canFunc = function(e) {
		e.preventDefault();
		window.location.href = `${contextPath}/login.do`;
	}
	
	let UIFunc = function(e) {
		e.preventDefault();
		window.location.href = `${contextPath}/login/createAccount_gm`;
	}
	
	let AutoBtn = function(e) {
		e.preventDefault();
		$('#memberId').val('nexus557');
		$('#memberPw').val('java');
		$('#memberName').val('이서은');
		$('#memberRegno1').val('880925');
		$('#memberRegno2').val('2345678');
		$('#sample6_postcode').val('13494');
		$('#sample6_address').val('경기도 성남시 분당구 판교역로 235');
		$('#sample6_detailAddress').val('분당 에이치스퀘어 N동 718호');
		$('#memberTel').val('010-9876-5432');
		$('#memberEmail').val('tpwotpwo13@gmail.com');
		$('#memberDescription').val('35살 프리랜서 디자이너로, 창의적인 프로젝트를 통해 새로운 도전을 즐깁니다.');
		$('#memberAuthQuestion').val(2);
		$('#memberAuthResponse').val('서울');
	}
	
	let AutoGroupBtn = function(e) {
		e.preventDefault();		
		$('#groupName').val('이노베이션 테크');
		$('#sample6_postcode').val('06236');
		$('#sample6_address').val('서울특별시 강남구 테헤란로 152');
		$('#sample6_detailAddress').val('강남파이낸스센터 28층');
		$('#groupComment').val('이노베이션 테크 솔루션즈는 2010년 설립된 혁신적인 IT 기업으로, 최첨단 기술을 활용한 비즈니스 솔루션을 제공합니다. 우리는 인공지능(AI), 빅데이터 분석, 클라우드 컴퓨팅, 그리고 사물인터넷(IoT) 분야에서 독보적인 기술력을 바탕으로 다양한 산업 분야의 디지털 전환을 선도하고 있습니다.');
	}
	
	$(document).on('change', '#businessLicense', licenseUpload);
	$(document).on('change', '#memberImage', fileInput);
	$(document).on('change', '#groupProfile', fileInput);
	$(document).on('click', '#cancelBtn', canFunc);
	$(document).on('click', '#backBtn', UIFunc);
	$(document).on('click', '#UpdateAuto', AutoBtn);
	$(document).on('click', '#UpdateGroupAuto', AutoGroupBtn);
})