<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- modal -->
<div class="modal fade" id="refusalModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-simple modal-refer-and-earn">
    <div class="modal-content p-3 p-md-5">
      <div class="modal-body">
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="reloading();"></button>
        <div class=" mb-4">
          <h3 class="mb-2 mt-2" id="cs-title">그룹 거절 사유를 입력해주세요.</h3>
          <p id="cs-content" class="text-muted mb-5 m-auto">해당 사유는 고객의 이메일로 전송됩니다.</p>
        </div>
        <form class="row g-3" onsubmit="return false">
          <div class="col-lg-10">
            <input
              type="text"
              id="modalRnFEmail"
              class="form-control"
              placeholder="사유를 입력해주세요."
              aria-label="example@domain.com" />
          </div>
          <div class="col-lg-2 d-flex align-items-end">
            <button type="button" class="btn btn-primary" id="submitBtn" onclick="submitBtnRefusal();">전송하기</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>
<!-- /modal -->
