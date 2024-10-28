document.addEventListener("DOMContentLoaded", () => {
	$(".paging-area").on("click", "a[data-page]", function(e) {
		e.preventDefault();
		let page = this.dataset.page; // this는 a[data-page]
		searchform.page.value = page;
		searchform.requestSubmit();
	});

	const $searchUI = $("#searchUI").on("click", "#searchBtn", function() {
		$searchUI.find(":input[name]").each(function(index, input) {
			let name = this.name;
			let value = $(this).val();
			searchform[name].value = value;
		});
		searchform.requestSubmit();
	});
})