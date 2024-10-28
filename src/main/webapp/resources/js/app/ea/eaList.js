function reloading(){
  location.reload();
}
document.addEventListener('DOMContentLoaded', function() {
console.log("groupId:",groupId);
	const documentId = document.querySelector('#lastArea').dataset.lastId;
	

	const cPath = document.body.dataset.contextPath;
    const waitingApprovalFromSender = document.getElementById('waitingApprovalFromSender');
    const waitingApproval = document.getElementById('waitingApproval');
    const completeApproval = document.getElementById('completeApproval');
    const totalApproval = document.getElementById('totalApproval');
    let acceptBtn = document.querySelector("#acceptBtn");
    let refusalBtn = document.querySelector("#refusalBtn");
    let acceptSuccess = document.querySelector("#acceptSuccess");
    let chooseApproval = document.querySelector("#chooseApproval");
    let updateHref = null;
    let eaListDiv = document.getElementById('eaListDiv');
    let clickedDiv = null;
    let url = null;
    let eaId = null;
    let eaApprovalYn = null;
    let today = null;

    console.log('waitingApprovalFromSender:', waitingApprovalFromSender);
    console.log('waitingApproval:', waitingApproval);
    console.log('completeApproval:', completeApproval);
    console.log('totalApproval:', totalApproval);

    // Define the boardList function first
    const boardList = (status) => {
        console.log("Requested status:", status);
        console.log('Request URL:', `${cPath}/${groupId}/ea/rest`);

        axios.get(`${cPath}/${groupId}/ea/rest`, {
            params: {
                approvalProgressStatus: status
            }
        })
        .then(response => {
            console.log("response입니다.", response.data);
            const eaList = response.data.eaList;

            // Initialize the HTML string
            let html = '';
            if(Array.isArray(eaList) && eaList.length !== 0){
	            for (let ea of eaList) {
		            let badge = '';
	                if (ea['EA_APPROVAL_YN'] === 'Y') {
	                    badge = '<span class="badge bg-label-success mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 완료</span>';
	                } else if (ea['EA_APPROVAL_YN'] === 'N') {
	                    badge = '<span class="badge bg-label-danger mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 거절</span>';
	                } else {
	                    badge = '<span class="badge bg-label-warning mb-2" style="font-size: 0.75rem; padding: 0.3rem 0.55rem;">승인 대기</span>';
	                }
		           console.log(ea);
	                html += `
	                <div class="mb-2 order-0 order-xxl-4 selectedApproval" data-file-url="${ea['FILE_URL']}" data-id-url="${ea['EA_ID']}"
	                  id="${ea['EA_ID']}" data-yn-url="${ea['EA_APPROVAL_YN']}" data-receiver-url="${ea['EA_RECEIVER']}" data-bs-toggle="modal" data-bs-target="#onboardImageModal">
	                  <div class="card">
	                    <div class="d-flex">
	                      <div class="card-header d-flex justify-content-between pb-1">
	                        <div class="card-title" style="padding:20px 50px 0px 20px;">
	                          <div>
	                             ${badge}
	                          </div>
	                        </div>
	                        <div class="card-title" style="padding-top:11px; padding-right: 270px;">
	                          <h5 class="m-0 me-2" style="font-size: 1.3rem; font-weight: 600;">${ea['PROJECT_NAME']}</h5>
	                          <div>
	                            <small class="text-muted pt-1 scrolling-text" style="white-space : nowrap; overflow : hidden; text-overflow : ellipsis; width: 400px; display: inline-block;">
	                              ${ea['CREATION_REASON']}
	                            </small>
	                          </div>
	                        </div>
	                      </div>
	                      <div class="d-flex">
	                        <!-- 첫 번째 컨텐츠 -->
	                        <div class="flex-fill me-5" style="padding-right: 20px;">
	                          <div class="nav-align-top">
	                            <div class="tab-content px-2 mx-1 pb-0">
	                              <div class="tab-pane fade show active" id="navs-justified-new" role="tabpanel">
	                                <ul class="timeline mb-0 pb-1">
	                                  <li class="timeline-item ps-4 border-left-dashed pb-1">
	                                    <span class="timeline-indicator-advanced timeline-indicator-success">
	                                      <i class="ti ti-circle-check"></i>
	                                    </span>
	                                    <div class="timeline-event px-0 pb-0">
	                                      <div class="timeline-header pb-1">
	                                        <small class="text-success text-uppercase fw-medium">sender</small>
	                                      </div>
	                                      <h6 class="mb-1">${ea['EA_SENDER_NAME']}</h6>
	                                      <p class="text-muted mb-0" style="white-space : nowrap; overflow : hidden; text-overflow : ellipsis; width: 150px; display: inline-block;">${ea['EA_SENDER_EMAIL']}</p>
	                                    </div>
	                                  </li>
	                                </ul>
	                              </div>
	                            </div>
	                          </div>
	                        </div>
	                        <!-- 두 번째 컨텐츠 -->
	                        <div class="flex-fill me-3">
	                          <div class="nav-align-top">
	                            <div class="tab-content px-2 mx-1 pb-0">
	                              <div class="tab-pane fade show active" id="navs-justified-new" role="tabpanel">
	                                <ul class="timeline mb-0 pb-1">
	                                  <li class="timeline-item ps-4 border-left-dashed pb-1">
	                                    <span class="timeline-indicator-advanced timeline-indicator-primary">
	                                      <i class="ti ti-map-pin"></i>
	                                    </span>
	                                    <div class="timeline-event px-0 pb-0">
	                                      <div class="timeline-header pb-1">
	                                        <small class="text-primary text-uppercase fw-medium">Receiver</small>
	                                      </div>
	                                      <h6 class="mb-1">${ea['EA_RECEIVER_NAME']}</h6>
	                                      <p class="text-muted mb-0" style="white-space : nowrap; overflow : hidden; text-overflow : ellipsis; width: 150px; display: inline-block;">${ea['EA_RECEIVER_EMAIL']}</p>
	                                    </div>
	                                  </li>
	                                </ul>
	                              </div>
	                            </div>
	                          </div>
	                        </div>
	                     </div>
	                  </div>
	                </div>
	              </div>
	              </div>`;
	            }
            }else{
				html += `
				 <div class="d-flex justify-content-center align-items-center" style="height:470px;">
                	<div>
                 		<span class="justify-content-center align-items-center" style="font-size:50px; padding-left:120px;">😯</span>
                 		<span class="d-flex fw-medium" style="display: inline-block; margin-top: 10px; font-size:26px;">전자결재가 존재하지 않습니다!</span>
                 	</div>
                </div>
				`;
			}

            // Update the DOM after the loop
            eaListDiv.innerHTML = html;

            eaListDiv.addEventListener('click', function(event) {


				// 승인도장
				document.querySelector("#Stamp").style.display = "none";
				// 승인도장


                if (event.target.closest('.selectedApproval')) {
                    clickedDiv = event.target.closest('.selectedApproval');
                    console.log('클릭됨', clickedDiv);
                    url = clickedDiv.getAttribute('data-file-url'); // 데이터 속성을 읽어옵니다.
                    eaId = clickedDiv.getAttribute('data-id-url'); // 데이터 속성을 읽어옵니다.
                    eaApprovalYn = clickedDiv.getAttribute('data-yn-url'); // 데이터 속성을 읽어옵니다.
                    receiverId = clickedDiv.getAttribute('data-receiver-url'); // 데이터 속성을 읽어옵니다.
                    console.log("url은", url);
                    console.log("id은", eaId);
                    console.log("eaApprovalYn은", eaApprovalYn);
                    console.log("receiverId은", receiverId);
                    preview(url);
                   // 모달이 열렸을 때, 해당 모달의 footer를 선택
                   const modalFooter = document.querySelector('#onboardImageModal .modal-footer');

                   if (eaApprovalYn === 'Y'||receiverId != memberId) {
                       modalFooter.style.display = 'none'; // 해당 모달의 footer를 숨김
                   } else {
                       modalFooter.style.display = ''; // 숨겨진 footer를 다시 보이게 (필요한 경우)
                   }


					// 승인도장
					if (eaApprovalYn === 'Y') {
						document.querySelector("#Stamp").style.display = "block";
					}
					// 승인도장


                }
            });

	

        })
        .catch(error => {
            // Handle error
            console.error('Error fetching board list:', error);
        });
    };


    // Register button click event listeners
    waitingApprovalFromSender.addEventListener('click', () => boardList('waitingApprovalFromSender'));
    waitingApproval.addEventListener('click', () => boardList('waitingApproval'));
    completeApproval.addEventListener('click', () => boardList('completeApproval'));
    totalApproval.addEventListener('click', () => boardList('totalApproval'));



	let preview = function(url) {
		let trueName = encodeURIComponent(url);
		const fileExtension = trueName.split('.').pop().toLowerCase();
		console.log("파일 확장자 체크", fileExtension);
		$('#image').attr('src', url);
	}

	// 결재 승인 START
    chooseApproval.addEventListener("click", (e)=>{

    	const event = e || window.event;
        console.log(groupId);
	     updateHref = `${cPath}/${groupId}/ea/rest/${eaId}`;
         console.log("업데이트는!!", updateHref);
    	 if(event.target.id === 'acceptBtn'){
    		const confirmAccept = confirm("해당 결재를 승인하시겠습니까?");
    		if(confirmAccept){
	         today = new Date();
             var data = {
          	  	 eaApprovalDate: today,
          	  	 eaApprovalYn : 'Y',
                 eaId : eaId
                 };
                 fetch(updateHref, {
                     method: 'POST',
                     headers: {
                         'Content-Type': 'application/json',
                     },
                     body: JSON.stringify(data)
                 })
                 .then(response => response.json())
                 .then(data => {
                     console.log(data);
                     if (data.success) {
                         alert('결재가 승인되었습니다');

						 // 동적으로 form 생성 및 제출
					    const form = document.createElement('form');
					    form.method = 'POST';
					    form.action = `${cPath}/group/${groupId}/project/ea/insert`;

					    const eaIdInput = document.createElement('input');
					    eaIdInput.type = 'hidden';
					    eaIdInput.name = 'eaId';
					    eaIdInput.value = eaId;

					    form.appendChild(eaIdInput);
					    document.body.appendChild(form);
					    form.submit();
//                         reloading();
                     } else {
                         alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                     }
                 })
                 .catch((error) => {
                     console.error('Error:', error);
                 });
    		}else {
               console.log("승인이 취소되었습니다.");
           }
        }
    });
    // 결재 승인 END
});
