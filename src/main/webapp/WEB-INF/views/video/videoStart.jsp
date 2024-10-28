<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
.header-wrapper {
  float: left;
  
}

.search-wrapper {
  float: left;
  margin-left:20px;
  text
}

.footer-wrapper {
/*   clear: both; */
/*   text-align: center; */
margin-left:20px;
}
</style>

<div class="container-xxl flex-grow-1 container-p-y">

    <div class="card" data-member-id='${memberId}' data-member-name='${memberName}' style="margin-top:60px">
        <div class="card-datatable table-responsive" style="overflow: hidden;">
            <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper dt-bootstrap5 no-footer">
<!--                 <div class="card-header d-flex border-top rounded-0 flex-wrap py-0 flex-column flex-md-row align-items-start" > -->
<!--                     <div class="me-5 ms-n4 pe-5 mb-n6 mb-md-0"> -->
                       
<!--                     </div> -->
                    
                    
                    <div class="emails-list-header p-3 py-lg-3 py-2 ps-5 pe-4">
				<div class="d-flex justify-content-between align-items-center">
					<div class="d-flex align-items-center w-100">
						<i class="ti ti-menu-2 ti-sm cursor-pointer d-block d-lg-none me-3" data-bs-toggle="sidebar" data-target="#app-email-sidebar" data-overlay></i>
						<div class="mb-0 mb-lg-2 w-100 d-flex text-nowrap align-items-center" style="margin-bottom:0px !important;">
						    <h4 class="mt-3" style="font-weight:700;">화상회의</h4>
							<div class="input-group input-group-merge shadow-none ps-1" id="customSearchWrapper">
                                    <div class="d-flex align-items-center">
                                        <input id="customSearchInput" name="search" type="search" aria-controls="DataTables_Table_0" 
                                        class="form-control ms-3" placeholder="방 제목을 입력해주세요." style="max-width: 300px;border-top-right-radius: 0; border-bottom-right-radius: 0; border: 1px solid #c9c8ce;">
                                        <button id="customSearchBtn" type="button" class="btn btn-primary" 
                                        style="height: 40.5px; white-space: nowrap; padding: 0 20px; border-top-left-radius: 0; border-bottom-left-radius: 0;">검색</button>
                                    </div>
                                </div>
						</div>
					</div>
					<div class="d-flex align-items-center mb-0">
						<div class="dropdown d-flex align-self-center text-nowrap" style="width: 300px; justify-content: flex-end;">
							<div class="btn btn-secondary add-new btn-primary py-2 waves-effect waves-light" type="button" style="width: auto; height:40.5px;" data-bs-toggle="modal" data-bs-target="#modalCenter">
								+ 회의 생성하기
							</div>
						</div>
					</div>
				</div>
			</div>
			
			
			
                    
<!--                     <div class="d-flex justify-content-start justify-content-md-end align-items-baseline">
                        <div class="dt-action-buttons d-flex flex-column align-items-start align-items-sm-center justify-content-sm-center pt-0 gap-sm-4 gap-sm-0 flex-sm-row" style="margin-top: 10%;">
                            <div class="dt-buttons btn-group flex-wrap d-flex mb-6 mb-sm-0">
                                <div class="btn-group">
                                </div>
                                Button trigger modal
                                <button type="button" class="btn btn-primary waves-effect waves-light" data-bs-toggle="modal" data-bs-target="#modalCenter">
                                    + 회의 생성하기
                                </button>
                            </div>
                        </div>
                    </div> -->
<!--                 </div> -->
                <table class="datatables-products table dataTable no-footer dtr-column" id="DataTables_Table_0" aria-describedby="DataTables_Table_0_info" style="width: 1200px;">
                    <thead class="border-top">
                        <tr>
<!--                             <th class="control sorting_disabled dtr-hidden" rowspan="1" colspan="1" style="width: 0px; display: none;" aria-label=""></th> -->
<!--                             <th class="sorting_disabled dt-checkboxes-cell dt-checkboxes-select-all" rowspan="1" colspan="1" style="width: 18px;" data-col="1" aria-label=""><input type="checkbox" class="form-check-input"></th> -->
                            <th class="sorting sorting_asc" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 40px;"></th>
                            <th class="sorting" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 170px;" ></th>
                            <th class="sorting" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 130px;" ></th>
                            <th class="sorting" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 130px;" ></th>
                            <th class="sorting" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" style="width: 48px;"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Table rows will go here -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<div class="d-flex pt-2 justify-content-end">Powered by Gooroome API</div>
	</div>			


<!-- Modal -->
<div class="mt-4">
	<div class="modal fade" id="modalCenter" tabindex="-1"
		style="display: none;" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modalCenterTitle">회의 생성하기</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col mb-4">
							<label for="nameWithTitle" class="form-label">회의 제목</label> <input
								type="text" id="nameWithTitle" class="form-control"
								placeholder="제목 입력" required="required">
						</div>
					</div>
					<div class="row g-4">
						<div class="col mb-0">
							<label for="RoomPassword" class="form-label">회의 비밀번호</label> 
							<input type="password" id="RoomPassword"
								class="form-control" placeholder="비밀번호 입력" required="required">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary waves-effect waves-light" id="CreateRoomBtn3">생성</button>
					<button type="button" class="btn btn-label-secondary waves-effect" data-bs-dismiss="modal">취소</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="${cPath }/resources/js/app/videoStart.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        // Initialize DataTable
        var table = $('#DataTables_Table_0').DataTable();

        // Hide the default search box provided by DataTables
        $('#DataTables_Table_0_filter').hide();

        // Bind the custom search input to trigger the DataTables search
        $('#customSearchInput').on('input', function() {
            table.search($(this).val()).draw();  // Trigger the DataTables search with input value
        });

        // Optional: If you prefer to trigger the search with a button click
        $('#customSearchBtn').on('click', function() {
            table.search($('#customSearchInput').val()).draw();  // Trigger DataTables search on button click
        });
        
        $('.dataTables_info').hide();
    });
</script>
