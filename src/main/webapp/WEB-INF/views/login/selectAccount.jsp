<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="container-xxl">
  <div class="authentication-wrapper authentication-basic container-p-y">
    <div class="row">
      <!-- Left Side Login -->
      <div class="col-md-6">
        <div class="authentication-inner py-4">
          <div class="card">
            <div class="card-body d-flex justify-content-center align-items-center text-center" style="height: 500px">
              <h1><a href="${cPath }/login/createAccount">일반회원으로 가입</a></h1>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Side Login -->
      <div class="col-md-6">
        <div class="authentication-inner py-4">
          <div class="card">
            <div class="card-body d-flex justify-content-center align-items-center text-center" style="height: 500px">
              <h1><a href="${cPath }/login/createAccount_gm">그룹관리자로 가입</a></h1>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>