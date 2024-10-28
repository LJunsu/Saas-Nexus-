package kr.or.nexus.commons.paging;

public class BootStrap5PaginationRenderer implements PaginationRenderer{

	@Override
	public String renderPagination(PaginationInfo paging, String funcName) {
		int firstPage = paging.getFirstPage();
		int lastPage = paging.getLastPage();
		int pageSize = paging.getPageSize();
		int totalPage = paging.getTotalPage();
		int currentPage = paging.getPage();

		StringBuffer pagingHTML = new StringBuffer();
		pagingHTML.append("<nav aria-label='...'>\n");
		pagingHTML.append("<ul class='pagination'>\n");

		boolean disabled = firstPage == 1;
		// 이전 페이지 링크
		pagingHTML.append(
			String.format("<li class='page-item %s'>\n", disabled ? "disabled" : "")
		);
		pagingHTML.append(
			String.format("<a class='page-link' href='#' %s data-page='%d'>이전</a>\n", disabled?"tabindex='-1' aria-disabled='true'":"", firstPage-1)
		);
		pagingHTML.append("</li>\n");

        for(int page = firstPage; page <= lastPage; page++) {
        	if(page == currentPage) {
        		pagingHTML.append("<li class='page-item active' aria-current='page'>\n");
        		pagingHTML.append(
        			String.format("<a class='page-link' href='#'>%d</a>\n", page)
        		);
        		pagingHTML.append("</li>\n");
        	}else {
        		pagingHTML.append(
        			String.format("<li class='page-item'><a class='page-link' href='#' data-page='%d'>%d</a></li>", page, page)
        		);
        	}
        }

        // 다음 페이지 링크
        disabled = lastPage == totalPage;
        pagingHTML.append(
        	String.format("<li class='page-item %s'>\n", disabled ? "disabled" : "")
        );
        pagingHTML.append(
        	String.format("<a class='page-link' href='#' %s data-page='%d'>다음</a>\n", disabled?"tabindex='-1' aria-disabled='true'":"", lastPage+1)
        );
        pagingHTML.append("</li>\n");

        pagingHTML.append("</ul>\n");
        pagingHTML.append("</nav>\n");
		return pagingHTML.toString();
	}

}
