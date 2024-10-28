/**
 * 
 */
document.addEventListener("DOMContentLoaded", ()=>{
	const cPath = document.body.dataset.contextPath;
	$("#memberId").on("blur", ()=>{
		$idChkBtn.trigger("click");
	});
	
	$("#memberEmail").on("blur", ()=>{
		$emailChkBtn.trigger("click");
	});
	
	const $idChkBtn = $("#id-chk-btn").on("click", ({target})=>{
		let $input = $(target).siblings("#memberId");
		let spanTag = $(target).siblings("#memberId-error");
		spanTag.remove();
		let inputId = $input.val();
		if(!inputId.length) return false;
		axios.post(`${cPath}/login/idChk`, {
			inputId:inputId
		}).then(resp=>{
			console.log(resp);
			if(!resp.data.valid){
				let msgTag = `<span id='memberId-error' class='text-danger'>
					${resp.data.message}</span>`;
				$input.after(msgTag);
				$input.val("");
				$input.focus();
			}else{
				let msgTag = `<span id='memberId-error' style="color:green;">
					${resp.data.message}</span>`;
				$input.after(msgTag);
			}
		})
	});
	
	const $emailChkBtn = $("#email-chk-btn").on("click", ({target})=>{
		let $input = $(target).siblings("#memberEmail");
		console.log($input);
		let spanTag = $(target).siblings("#memberEmail-error");
		spanTag.remove();
		let inputEmail = $input.val();
		if(!inputEmail.length) return false;
		axios.post(`${cPath}/login/eMailChk`, {
			inputEmail:inputEmail
		}).then(resp=>{
			console.log(resp);
			if(!resp.data.valid){
				let msgTag = `<span id='memberEmail-error' class='text-danger'>
					${resp.data.message}</span>`;
				$input.after(msgTag);
				$input.val("");
				$input.focus();
			}else{
				let msgTag = `<span id='memberId-error' style="color:green;">
					${resp.data.message}</span>`;
				$input.after(msgTag);
			}
		})
	});
	
	
	
//	const contextPath = document.body.dataset.contextPath;
//	$("#id-chk-btn").on("click", (e)=>{
//		e.preventDefault();
//		memId = $("input[name='memId']").val();
//		console.log(memId);
//		$.ajax({
//			url:contextPath+'/member/idCheck',
//			method:'post',
//			dataType: 'json',
//			data : {
//				memId : memId
//			},
//			success: function(json){
//				console.log(json);
//				alert("가능한 아이디");
////				document.querySelector(".table").innerHTML += "<input type='hidden' name='check' value='true'>";
//			}, error:function(jqXHR, errorStatus, errorText){
//				console.log(jqXHR);
//				console.log(errorStatus);
//				console.log(errorText);
//				alert("아이디 중복")
//			}
//		})
//	});
//	
//	$("#ok").on("requestSubmit", (e)=>{
//		e.preventDefault();
//		if(success=="success"){
//			e.on("click", ()=>{
//				requestSubmit();
//			})
//		}
//	})
})