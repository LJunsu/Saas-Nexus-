document.addEventListener("DOMContentLoaded", (e) => {
	e.preventDefault();
	$(document).ready(function() {
		const announcementIdElement = document.getElementById('announcementId');
		const boardId = announcementIdElement.value;
		const contextPath = document.body.dataset.contextPath;
		const groupId = announcementIdElement.value;
		const projectId = announcementIdElement.value;

		listJson();

		$("#btnAnswer").click(function() {
			var cmtContent = $("#cmtContent").val();
			var param = "cmtContent=" + cmtContent + "&boardId=" + boardId;

			$.ajax({
				type: "post",
				url: `${contextPath}/${groupId}/${projectId}/announcement/${boardId}/comment/insert`,
				data: param,
				success: function() {
					Swal.fire({
						icon: 'success',
						title: '댓글이 등록되었습니다!',
						confirmButtonText: '확인'
					}).then(() => {
						$("#cmtContent").val('');
						listJson(); // 댓글 목록 새로 고침
					});
				},
				error: function() {
					Swal.fire({
						icon: 'error',
						title: '오류',
						text: '댓글 등록 중 오류가 발생했습니다.'
					});
				}
			});
		});

		function listJson() {
			$.ajax({
				type: "get",
				url: `${contextPath}/${groupId}/${projectId}/announcement/${boardId}/comment/listJson`,
				success: function(result) {
					var answerList = result.answerList;
					var currentUserId = result.currentUserId;

					var output = "<table class='datatables-customers table border-top dataTable no-footer dtr-column'"
						+ "id='DataTables_Table_0' aria-describedby='DataTables_Table_0_info'"
						+ "style='width: 1394px;'>";

					output += "<thead>"
					output += "<tr>"
					output += "<th class='' tabindex='0' aria-controls='DataTables_Table_0' rowspan='1' colspan='1' style='width: 100px; ' aria-sort='descending;'>작성자"
					output += "</th>"
					output += "<th class='' tabindex='0' aria-controls='DataTables_Table_0' rowspan='1' colspan='1' style='width: 100px; ' aria-sort='descending;'>댓글 내용"
					output += "</th>"
					output += "<th class='' tabindex='0' aria-controls='DataTables_Table_0' rowspan='1' colspan='1' style='width: 100px; ' aria-sort='descending;'>작성일"
					output += "</th>"
					output += "<th class='' tabindex='0' aria-controls='DataTables_Table_0' rowspan='1' colspan='1' style='width: 100px; ' aria-sort='descending;'>수정삭제"
					output += "</th>"
					output += "</tr>"
					output += "</thead>"

					for (var i in answerList) {
						output += "<tr id='list'>";
						output += "<td style='width: 150px;'>" + answerList[i].memberName + "</td>";
						output += "<td style='width: 600px;'>" + answerList[i].cmtContent + "</td>";
						output += "<td style='width: 150px;'>"
						output += "(" + changeDate(answerList[i].cmtCreationDate) + ")";
						output += "</td>";
						output += "<td style='width: 100px;'>"
						if (answerList[i].memberId === currentUserId) {
							output += "<input type='button' class='btnModify btn-primary' value='수정' data-id='" + i + "'>";
							output += "<input type='button' class='btnDelete btn-danger' value='삭제' data-id='" + i + "'>";
						}
						output += "</td>";
						output += "</tr>";
					}
					output += "</table>";
					$("#listJson").html(output);

					// 이벤트 핸들러를 중복 등록하지 않도록 처리
					$(".btnModify").click(function() {
						var index = $(this).data("id");
						var cmtContent = answerList[index].cmtContent;
						var boardCmtId = answerList[index].boardCmtId;  // 댓글 ID

						 var td = $(this).closest('tr').find('td').eq(1);
						td.html("<textarea id='editTextarea'>" + cmtContent + "</textarea>");
						td.append("<input id='answerSave' type='button' class='btnSave btn-primary' value='저장'>");

						// 저장 버튼 클릭 시 서버로 데이터 전송
						$(".btnSave").click(function() {
							var newContent = $("#editTextarea").val();

							// AJAX 요청으로 서버에 수정된 내용 전송
							$.ajax({
								type: "put",
								url: `${contextPath}/${groupId}/${projectId}/announcement/${boardId}/comment/update`,  // 수정 처리 URL
								data: {
									boardCmtId: boardCmtId,  // 댓글 ID
									cmtContent: newContent  // 수정된 내용
								},
								success: function(response) {
									if (response === "success") {
										// 성공 시 화면에 반영
										td.html(newContent);
										Swal.fire({
											icon: 'success',
											title: '댓글이 수정되었습니다!',
											confirmButtonText: '확인'
										});

										listJson();
									} else {
										Swal.fire({
											icon: 'error',
											title: '수정 실패',
											text: '댓글 수정에 실패했습니다.'
										});

									}
								},
								error: function(error) {
									console.log("Error:", error);
									Swal.fire({
										icon: 'error',
										title: '오류',
										text: '수정 중 오류가 발생했습니다.'
									});

								}
							});
						});
					});

					$(".btnDelete").on("click", function() {
						var index = $(this).data("id");
						var boardCmtId = answerList[index].boardCmtId;  // 댓글 ID
						Swal.fire({
							title: '정말 삭제하시겠습니까?',
							text: "삭제한 내용은 복구할 수 없습니다!",
							icon: 'warning',
							showCancelButton: true,
							confirmButtonColor: '#3085d6',
							cancelButtonColor: '#d33',
							confirmButtonText: '<i class="fas fa-trash-alt"></i> 삭제',
							cancelButtonText: '취소'
						}).then((result) => {
							if (result.isConfirmed) {
								$.ajax({
									type: "delete",
									url: `${contextPath}/${groupId}/${projectId}/announcement/${boardId}/comment/delte`,  // 수정된 URL
									data: {
										boardCmtId: boardCmtId
									},
									success: function(response) {
										if (response === "success") {
											Swal.fire({
												icon: 'success',
												title: '삭제 완료',
												text: '댓글이 삭제되었습니다.'
											});
											listJson();
										} else {
											Swal.fire({
												icon: 'error',
												title: '삭제 실패',
												text: '댓글 삭제에 실패했습니다.'
											});
										}
									},
									error: function() {
										Swal.fire({
											icon: 'error',
											title: '오류',
											text: '삭제 중 오류가 발생했습니다.'
										});
									}
								});
							}
						});
					});
				},
				error: function() {
					Swal.fire({
						icon: 'error',
						title: '오류',
						text: '댓글 목록 조회 중 오류가 발생했습니다.'
					});
				}
			});
		}

		function changeDate(date) {
			const [year, month, day, hour, minute, second] = date;
			const d = new Date(Date.UTC(year, month - 1, day, hour, minute, second));
			const strDate = d.toISOString().slice(0, 19).replace('T', ' ');
			return strDate;
		}
	});
});
