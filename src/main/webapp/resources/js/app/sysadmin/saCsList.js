           let csAnsIdx = null;
           let csId = null;
           let href = null;
           let exampleModal$ = null;
           let myModal = null;
	       function autoComplete() {
	         document.getElementById('modalRnFEmail').value = '확인해보겠습니다';
	       }
           
           function reloading(){
        	   location.reload();
           }
           
//         CRUD후 모달 리로드 START
           function refreshModalContent() {
               $.ajax({
                   url: href,
                   method: "GET",
                   dataType: "json",
                   success: function(data) {
                       exampleModal$.find("#cs-content").text(data.csContent);
                       exampleModal$.find("#cs-title").text(data.csTitle);
                       exampleModal$.find("#cs-writer").text(data.memberId);
                       const answerContent = data.customerBoardAnswersVO[0].csAnsContent;
                       exampleModal$.find("#answer-div .email-list").empty();
                       console.log(data);
                       if (answerContent != null) {
                           exampleModal$.find("#answer-div").show();
                           exampleModal$.find("#answerY").show();
                           exampleModal$.find("#answerN").hide();
                           data.customerBoardAnswersVO.forEach(answer => {
                        	   console.log("Answer object:", answer);
                        	   console.log("Answer ID:", answer.csAnsIdx); // 여기에 값을 출력
                               const answerHTML = `
                            	   <li class="email-list-item" data-starred="true" style="list-style:none; position: relative; margin-bottom: 13px;">
                            	    <div class="d-flex align-items-center justify-content-between">
                            	        <div class="d-flex align-items-center">
                            	            <img src="${contextPath}/resources/nexus/assets/img/avatars/1.png" alt="user-avatar"
                            	                class="d-block flex-shrink-0 rounded-circle me-sm-3 me-2" height="32" width="32" />
                            	            <div class="email-list-item-content ms-2 ms-sm-0 me-1">
                            	                <span class="h6 email-list-item-username me-1" style="font-weight:600;">관리자</span>
                            	                <span class="email-list-item-subject d-xl-inline-block d-block contentArea">${answer.csAnsContent}</span>
                            	            </div>
                            	        </div>
                            	        <div class="email-list-item-meta d-flex align-items-center">
                            	            <ul class="list-inline email-list-item-actions m-0 text-nowrap">
                            	                <li class="list-inline-item">
                            	                    <i class="ti ti-pencil editBtn" style="cursor: pointer;" data-id="${answer.csAnsIdx}"></i>
                            	                </li>
                            	                <li class="list-inline-item">
                            	                    <i class="ti ti-x deleteBtn" style="cursor: pointer;" data-id="${answer.csAnsIdx}"></i>
                            	                </li>
                            	            </ul>
                            	        </div>
                            	    </div>
                            	</li>
                               `;
                                exampleModal$.find("#answer-div .email-list").append(answerHTML);
                           });
                       } else {
                           exampleModal$.find("#answer-div").hide();
                           exampleModal$.find("#answerN").show();
                           exampleModal$.find("#answerY").hide();
                       }
                       
                       myModal.show();
                   },
                   error: function(jqXHR, errorStatus, errorText) {
                       console.log("AJAX Error Status:", errorStatus);
                       console.log("AJAX Error Text:", errorText);
                       console.log("AJAX Full Response:", jqXHR.responseText);
                   }
               });
          }
//         CRUD후 모달 리로드 END
           
//         관리자 답변전송 + 수정전송 START
           function submitBtnCase() {
        	   var answerInput = document.getElementById('modalRnFEmail');
               console.log(submitBtn.innerHTML);
         	   if(submitBtn.innerHTML.includes("수정하기")){
         		   console.log("수정하려고합니다");
                    var data = {
                        csAnsIdx: csAnsIdx,
                        csId: csId,
                        csAnsContent: answerInput.value 
                    };
                    fetch(href, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(data)
                    })
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        if (data.success) {
                            alert('답변이 성공적으로 수정되었습니다.');
                            refreshModalContent();
                            answerInput.value=null;
                            submitBtn.innerHTML = "답변하기";
                        } else {
                            alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                        }
                    })
                    .catch((error) => {
                        console.error('Error:', error);
                    });
         		   
         	   }else{            		   
                   console.log(csId);
                   
                   var data = {
                       csAnsContent: answerInput.value,
                       csId: csId,
                       adminId: 'MEM011'
                   };
                   fetch(href, { // 이전에 클릭된 href 값을 사용
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
                           alert('답변이 성공적으로 등록되었습니다.');
                           refreshModalContent();
                           answerInput.value=null;
                           
                       } else {
                           alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                       }
                   })
                   .catch((error) => {
                       console.error('Error:', error);
                   });
         	   }
            }
//          관리자 답변전송 + 수정전송 END

           document.addEventListener("DOMContentLoaded", () => {
               exampleModal$ = $("#referAndEarn").on("hidden.bs.modal", ({target}) => {});           
               myModal = new bootstrap.Modal(exampleModal$[0]);

               
//             모달 출력 START
               document.querySelectorAll(".sysadmin-list").forEach(anchor => {
                   anchor.addEventListener("click", (e) => {
                       e.preventDefault();
                       const a = e.target.closest("a"); // 링크가 클릭된 요소가 아닌 그 부모인 a 태그를 가져옴
                       href = a.getAttribute("href"); // href 값을 상위 스코프에 정의된 변수에 저장
                       csId = href ? href.split('/').pop() : null;
                       refreshModalContent();
                   });
               });
//             모달 출력 END


//             관리자 답변수정 START
               document.addEventListener('click', function(event) {
                   if (event.target.classList.contains('editBtn')) {
                	   var submitBtn = document.querySelector('#submitBtn');
                	   submitBtn.innerHTML = "수정하기";
                	   console.log(submitBtn.innerHTML);
                	   const listItem = event.target.closest('.email-list-item');
                	   const contentArea = listItem.querySelector('.contentArea');
                	   const contentText = contentArea.textContent;
                	   console.log("Edit button clicked. Content:", contentText);
                	   
                       var answerInput = document.getElementById('modalRnFEmail');
                       answerInput.value = contentText;
                       csAnsIdx = event.target.dataset.id;
                       console.log(csAnsIdx);
                   }
               });
//             관리자 답변수정 END

//             관리자 답변삭제 START
               document.addEventListener('click', function(event) {
                   if (event.target.classList.contains('deleteBtn')) {
                       console.log(event.target); // 클릭된 요소 확인
                       const confirmDelete = confirm("정말 삭제하시겠습니까?");
                       const csAnsIdx = event.target.dataset.id;
                       if (confirmDelete) {
                           var data = {
                                   csAnsIdx: csAnsIdx
                               };
                               fetch(href, { // 이전에 클릭된 href 값을 사용
                                   method: 'DELETE',
                                   headers: {
                                       'Content-Type': 'application/json',
                                   },
                                   body: JSON.stringify(data)
                               })
                               .then(response => response.json())
                               .then(data => {
                                   console.log(data);
                                   if (data.success) {
                                       alert('답변이 성공적으로 삭제되었습니다.');
                                       refreshModalContent();
                                       answerInput.value=null;
                                   } else {
                                       alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
                                   }
                               })
                               .catch((error) => {
                                   console.error('Error:', error);
                               });
                       } else {
                           console.log("삭제가 취소되었습니다.");
                       }
                   }
               });
//             관리자 답변삭제 END
           });