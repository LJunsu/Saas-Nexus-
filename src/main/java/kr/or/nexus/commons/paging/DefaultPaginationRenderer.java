package kr.or.nexus.commons.paging;

public class DefaultPaginationRenderer implements PaginationRenderer {

	@Override
	public String renderPagination(PaginationInfo paging, String funcName) {
		int firstPage = paging.getFirstPage();
		int lastPage = paging.getLastPage();
		int pageSize = paging.getPageSize();
		int totalPage = paging.getTotalPage();
		int currentPage = paging.getPage();
		
		StringBuffer pagingHTML = new StringBuffer();
		pagingHTML.append("<ul class='pagination'>");
	    String pattern = "<li class='%s'><a class='page-link' href='javascript:;' data-page='%d'>%s</a></li>\n";

        if(firstPage > pageSize) {
        	 pagingHTML.append(String.format(pattern, "page-item", firstPage-pageSize, "이전"));
        }
        
        for(int page = firstPage; page <= lastPage; page++) {
            if(page == currentPage) {
               pagingHTML.append(
                     String.format(pattern, "page-item active", page, page)
                  );
            }else {
               pagingHTML.append(
                  String.format(pattern, "page-item", page, page)
               );
            }
         }

        if(lastPage < totalPage) {
        	pagingHTML.append(String.format(pattern, "page-item", lastPage+1, "다음"));
        }
        return pagingHTML.toString();
	}

}
