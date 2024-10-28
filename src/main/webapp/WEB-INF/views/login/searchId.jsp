<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Content -->
<input type="hidden" id="msg" value="${message }">
    <div class="container-xxl">
      <div class="authentication-wrapper authentication-basic container-p-y">
        <div class="authentication-inner py-4">
          <!-- Login -->
          <div class="card">
            <div class="card-body">
              
              <h4 class="mb-2 pt-2" style="font-weight:600;">아이디 찾기 👋</h4>
              <p class="mb-4">아이디 찾기를 위해 이름, 이메일을 입력해주세요</p>

              <form id="formAuthentication" class="mb-3" action="searchId" method="post">
                <div class="mb-3">
                  <label for="email" class="form-label">이름</label>
                  <input
                    type="text"
                    class="form-control"
                    id="memberName"
                    name="memberName"
                    placeholder="이름을 입력해주세요"
                    autofocus />
                </div>
                <div class="mb-3 form-password-toggle">
                  <label class="form-label" for="password">이메일</label>
                  <div class="input-group input-group-merge">
                    <input
                      type="text"
                      id="memberEmail"
                      class="form-control"
                      name="memberEmail"
                      placeholder="이메일을 입력해주세요"
                      aria-describedby="password" />
                  </div>
                </div>
                <div class="mb-3">
                  <button class="btn btn-primary d-grid w-100" type="submit">아이디 찾기</button>
                </div>
              </form>


            </div>
          </div>
          <!-- /Register -->
        </div>
      </div>
    </div>

    <!-- / Content -->
<script>
	var message = $('#msg').val();
	if(message != null && message != ''){
		alert(message);		
	}
</script>