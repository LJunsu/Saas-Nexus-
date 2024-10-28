/**
 * 
 */

document.addEventListener("DOMContentLoaded", ()=>{
	const contextPath = document.body.dataset.contextPath;
	let roleInsert = $("#roleInsert");
	console.log(roleInsert);
	$("#roleInsert").on("click", (e)=>{
		e.preventDefault();
		let projectId = $("#projectId").val();
		let projectRoleName = $('#projectRoleName').val();
		
		axios.post(`${contextPath}/group/project/roleInsert`,{
			projectId : projectId
			,projectRoleName : projectRoleName
		}).then(resp => {
			console.log(resp);
			let roleListHtml = '';
        	resp.forEach(role => {
	            roleListHtml += `<tr><td>${role.projectRoleName}</td></tr>`;
	        });

			$('#roleList').html(roleListHtml);
		}).catch(error => {
			console.log(error);
			alert("실패했습니다.")
		})
	})
})