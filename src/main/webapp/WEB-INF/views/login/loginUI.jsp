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
              
              <h4 class="mb-1 pt-2" style="font-weight:600;">NEXUS에 오신걸 환영합니다! 👋</h4>
              <p class="mb-4 mt-2">계정에 로그인하고 서비스를 시작해보세요.</p>

              <form id="formAuthentication" class="mb-3" action="login.do" method="post">
                <div class="mb-2">
                  <label for="email" class="form-label">회원 ID</label>
                  <input
                    type="text"
                    class="form-control"
                    id="memberId"
                    name="memberId"
                    placeholder="아이디를 입력해주세요"
                    autofocus />
                </div>
                <div class="d-flex justify-content-between">
                  	<label class="form-label" for="password"></label>
                    <a href="${cPath }/login/searchId">
                      <small>아이디를 잊어버리셨나요?</small>
                    </a>
                  </div>
                <div class="mb-3 form-password-toggle">
                  <label class="form-label" for="password">회원 PW</label>
                  <div class="input-group input-group-merge mb-2">
                    <input
                      type="password"
                      id="memberPw"
                      class="form-control"
                      name="memberPw"
                      placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
                      aria-describedby="password" />
                    <span class="input-group-text cursor-pointer"><i class="ti ti-eye-off"></i></span>
                  </div>
                  <div class="d-flex justify-content-between">
                  	<label class="form-label" for="password"></label>
                    <a href="${cPath }/login/searchPw">
                      <small>패스워드를 잊어버리셨나요?</small>
                    </a>
                  </div>
                </div>
                <div class="mb-3">
                  <div class="form-check">
                    <input class="form-check-input" type="checkbox" id="remember-me" />
                    <label class="form-check-label" for="remember-me" style="font-weight:500;"> ID 기억하기 </label>
                  </div>
                </div>
                <div class="mb-3">
                  <button class="btn btn-primary d-grid w-100" type="submit">로그인하기</button>
                </div>
              </form>

              <p class="text-center">
                <span>아직 회원이 아니신가요?</span>
                <a href="${cPath }/login/selectAccount">
                  <span style="font-weight:500;">회원가입 하기</span>
                </a>
              </p>

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