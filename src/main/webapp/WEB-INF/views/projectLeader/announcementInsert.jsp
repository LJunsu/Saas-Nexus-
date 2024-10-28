<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!-- Content -->
<style>
#mytextarea-container {
	width: 100%;
}
</style>
<script
	src="https://cdn.tiny.cloud/1/l36zuy0pre8iripbdbkylheu8eh1t2md7vcnx1az1k07uftv/tinymce/5/tinymce.min.js"
	referrerpolicy="origin"></script>

<div class="container-xxl flex-grow-1 container-p-y">
	<h4 class="py-3 mb-0">
		<span class="text-muted fw-light"></span><span class="fw-medium"></span>
	</h4>

	<div class="app-ecommerce">
		<!-- Add Product -->
		<div
			class="d-flex flex-column flex-md-row justify-content-between align-items-start align-items-md-center mb-3">
			<div class="d-flex flex-column justify-content-center">
				<h4 class="mb-1 mt-3">공지사항 등록</h4>
			</div>
		</div>

		<div class="row">
			<!-- First column-->
			<div class="col-12 col-lg-8" style="width: 1416px">
				<!-- Product Information -->
				<div class="card mb-4">
					<form:form id="insertForm" method="post"
						enctype="multipart/form-data" modelAttribute="newAnnouncement"
						name="atchFile">
						<div class="d-flex align-content-center flex-wrap gap-3"
							style="display: flex; justify-content: flex-end;">
							<button type="button" class="btn btn-primary" id="autoFillBtn"
								style="margin-top: 10px; margin-right: 10px;">자동완성</button>
							<button type="submit" class="btn btn-primary"
								style="margin-top: 10px; margin-right: 10px;">공지 추가하기</button>
						</div>
						<div class="card-header">
							<h5 class="card-tile mb-0">공지입력</h5>
						</div>

						<div class="card-body">
							<div class="mb-3">
								<label class="form-label" for="ecommerce-product-name">공지제목</label>
								<form:input type="text" class="form-control"
									id="ecommerce-product-name" required="required"
									name="boardTitle" aria-label="Product title" path="boardTitle" />
							</div>
							<div>
								<label class="form-label">공지내용</label>
								<div class="form-control p-0 pt-1">
									<form:textarea id="mytextarea" name="boardContent"
										path="boardContent"></form:textarea>
								</div>
							</div>
							<div class="card mb-4">
								<div class="card-body">
									<div class="fallback">
										<div class="file-upload-container">
											<input type="file" name="atchFile.fileDetails[0].uploadFile"
												class="form-control" multiple />
										</div>
									</div>
								</div>
							</div>
					</form:form>
				</div>
				<!-- /Product Information -->
			</div>
			<!-- /First column-->
		</div>
		<!-- /Row -->
	</div>
	<div class="d-flex pt-2 justify-content-end">Powered by tinymce</div>
	<!-- /app-ecommerce -->
</div>
<!-- /container-xxl -->

<script>
        tinymce.init({
            selector: "#mytextarea",
            plugins: "paste image imagetools",
            height: 500,
            width: '100%',
            toolbar: "undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | outdent indent | image",
            paste_data_images: true,
            file_picker_types: 'image',
            resize: true, // 자동 크기 조정 활성화
            setup: function (editor) {
                editor.on('init', function (e) {
                    // 에디터 초기화 후 컨테이너 너비에 맞게 조정
                    editor.getContainer().style.width = '100%';
                });
                window.addEventListener('resize', function () {
                    editor.getContainer().style.width = '100%';
                });
            },
            images_upload_handler(blobInfo, success) {
                const file = new File([blobInfo.blob()], blobInfo.filename());
                const fileName = blobInfo.filename();

                if (fileName.includes("blobid")) {
                    success(URL.createObjectURL(file));
                } else {
                    imageFiles.push(file);
                    success(URL.createObjectURL(file));
                }
            }
        });

     // tinymce.init({...}); 설정은 그대로 유지합니다.

       const predefinedData = [
            {
                title: 'AI 기반 자동화 시스템 업데이트 완료 안내',
                content: 'AI 기반 자동화 시스템의 주요 업데이트가 완료되었습니다. 새로 적용된 기능을 테스트하고, 관련 문서를 숙지해 주시기 바랍니다. 시스템 성능이 크게 개선되었으니, 작업 효율 향상에 많은 도움이 될 것으로 기대됩니다. 세부 사항은 프로젝트 관리 도구에서 확인 가능합니다.'
            },
            {
                title: '차세대 클라우드 통합 프로젝트 진척 현황 보고',
                content: '차세대 클라우드 통합 프로젝트가 예상보다 빠르게 진척되고 있습니다. 이번 주 주요 완료 사항은 데이터 마이그레이션과 보안 최적화입니다. 각 팀은 다음 단계 작업을 준비해주시고, 해당 사항에 대한 피드백은 리더에게 공유해 주시기 바랍니다.'
            },
            {
                title: '프로젝트 작업 시간 조정 및 야간 작업 안내',
                content: '프로젝트 일정 조율로 인해 일부 팀은 야간 작업이 필요할 수 있습니다. 야간 작업이 예정된 팀은 작업 시간을 미리 확인하고 준비해 주시기 바랍니다. 필요한 지원 사항이 있다면 즉시 보고해 주세요.'
            }
        ];

        let currentIndex = 0;

        document.getElementById('autoFillBtn').addEventListener('click', function() {
            const data = predefinedData[currentIndex];
            document.getElementById('ecommerce-product-name').value = data.title;
            tinymce.get('mytextarea').setContent(data.content);

            // 다음 인덱스로 이동
            currentIndex = (currentIndex + 1) % predefinedData.length;
        });
    </script>
    </script>