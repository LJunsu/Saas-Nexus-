package kr.or.nexus.commons.paging;

public class DefaultPaginationRenderer2 implements PaginationRenderer {

	@Override
	public String renderPagination(PaginationInfo paging, String funcName) {
	    int firstPage = paging.getFirstPage();
	    int lastPage = paging.getLastPage();
	    int pageSize = paging.getPageSize();
	    int totalPage = paging.getTotalPage();
	    int currentPage = paging.getPage();

	    StringBuffer pagingHTML = new StringBuffer();

	    // Opening tags for the navigation and pagination list
	    pagingHTML.append("<nav aria-label=\"Page navigation\">\n");
	    pagingHTML.append("<ul class=\"pagination justify-content-center\">\n");

	    if (firstPage > pageSize) {
	        pagingHTML.append("<li class=\"page-item prev\">\n");
	        pagingHTML.append("<a class=\"page-link\" href=\"javascript:void(0);\" data-page=\"" + (firstPage - pageSize) + "\"><i class=\"ti ti-chevrons-left ti-xs\"></i></a>\n");
	        pagingHTML.append("</li>\n");
	    } else {
	        pagingHTML.append("<li class=\"page-item prev disabled\">\n");
	        pagingHTML.append("<a class=\"page-link\" href=\"javascript:void(0);\"><i class=\"ti ti-chevrons-left ti-xs\"></i></a>\n");
	        pagingHTML.append("</li>\n");
	    }

	    // Page numbers
	    for (int page = firstPage; page <= lastPage; page++) {
	        if (page == currentPage) {
	            pagingHTML.append("<li class=\"page-item active\">\n");
	            pagingHTML.append("<a class=\"page-link\" href=\"javascript:void(0);\">" + page + "</a>\n");
	            pagingHTML.append("</li>\n");
	        } else {
	            pagingHTML.append("<li class=\"page-item\">\n");
	            pagingHTML.append("<a class=\"page-link\" href=\"javascript:void(0);\" data-page=\"" + page + "\">" + page + "</a>\n");
	            pagingHTML.append("</li>\n");
	        }
	    }

	    // Next button
	    if (lastPage < totalPage) {
	        pagingHTML.append("<li class=\"page-item next\">\n");
	        pagingHTML.append("<a class=\"page-link\" href=\"javascript:void(0);\" data-page=\"" + (lastPage + 1) + "\"><i class=\"ti ti-chevrons-right ti-xs\"></i></a>\n");
	        pagingHTML.append("</li>\n");
	    } else {
	        pagingHTML.append("<li class=\"page-item next disabled\">\n");
	        pagingHTML.append("<a class=\"page-link\" href=\"javascript:void(0);\"><i class=\"ti ti-chevrons-right ti-xs\"></i></a>\n");
	        pagingHTML.append("</li>\n");
	    }


	    // Closing tags for the navigation and pagination list
	    pagingHTML.append("</ul>\n");
	    pagingHTML.append("</nav>\n");

	    return pagingHTML.toString();
	}
}












