package kr.or.nexus.commons.paging;

public interface PaginationRenderer {
	public String renderPagination(PaginationInfo paging, String funcName);
	public default String renderPagination(PaginationInfo paging) {
		return renderPagination(paging, null);
	}
}
