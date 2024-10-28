<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:set value="${pageContext.request.userPrincipal}" var="authentication" />
<c:set value="${authentication.principal}" var="princiapl" />
<c:set value="${authentication.principal.realUser.memberName}" var="memberName" />
<c:set value="${authentication.principal.realUser.memberId}" var="memberId" />

<!-- content -->   
<footer class="content-footer footer bg-footer-theme">
  <div class="container-xxl">
    <div
      class="footer-container d-flex align-items-center justify-content-between py-2 flex-md-row flex-column">
      <div>
        ©
        <script>
          document.write(new Date().getFullYear());
        </script>
        , made with ❤️ by
        <span class="footer-link fw-medium"
          >2팀</span
        >
      </div>
      <div class="d-none d-lg-inline-block">
        <a href="${pageContext.request.contextPath}/cs" class="footer-link me-4"  data-bs-toggle="modal" data-bs-target="#editUser"
          >고객센터 문의</a
        >
        <span
          class="footer-link me-4"
          >Nexus</span
        >
      </div>
    </div>
  </div>
  <!-- modal -->
  <div class="modal fade" id="editUser" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-simple modal-edit-user">
                  <div class="modal-content p-3 p-md-5">
                    <div class="modal-body">
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      <div class="text-center mb-4">
                        <h3 class="mb-2" style="font-weight:700;">고객센터 문의</h3>
                        <p class="text-muted" style="font-weight:500;">작성한 문의사항은 관리자에게 전달되고 마이페이지에서 답변 확인가능합니다.</p>
                      </div>
                      <form id="editUserForm" class="row g-3" onsubmit="return false">
                        <div class="col-12 col-md-6">
                          <label class="form-label" for="modalEditUserFirstName">문의 제목</label>
                          <input
                            type="text"
                            id="modalEditUserFirstName"
                            name="modalEditUserFirstName"
                            class="form-control"
                            placeholder="제목을 입력해주세요" />
                        </div>
                        <div class="col-12">
                          <label class="form-label" for="modalEditUserName">문의 내용</label>
                          <input
                            type="text"
                            id="modalEditUserName"
                            name="modalEditUserName"
                            class="form-control"
                            placeholder="내용을 입력해주세요" style="padding-bottom:30px;"/>
                        </div>
                        <div class="col-12 text-center mt-4">
                          <button type="submit" class="btn btn-primary me-1" onclick="submitBtnCs();">제출하기</button>
                          <button
                            type="reset"
                            class="btn btn-label-secondary"
                            data-bs-dismiss="modal"
                            aria-label="Close">
                            취소
                          </button>
                        </div>
                      </form>
                    </div>
                  </div>
                </div>
              </div>
  <!-- /modal -->
</footer>
<!-- /content -->            
<script>
var footerCsHref = `${gitdozicPath}/nexus/cs`;
console.log("footerCsHref : ", footerCsHref);
function reloading(){
	location.reload();
}
function submitBtnCs(){
  var csTitle = document.querySelector("#modalEditUserFirstName");
  var csContent = document.querySelector("#modalEditUserName");
  if(!confirm('문의를 제출하시겠습니까?')){
	  return;
  }else{
    var data = {
    	 csTitle: csTitle.value,
      	 csContent: csContent.value,
      	 memberId: "${memberId}"
         };
         fetch(footerCsHref, {
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
                 alert('문의 제출이 완료되었습니다.');
                 reloading();
             } else {
                 alert('오류가 발생했습니다: ' + JSON.stringify(data.errors));
             }
         })
         .catch((error) => {
             console.error('Error:', error);
         }); 
  }
}
</script>