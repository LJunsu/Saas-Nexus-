package kr.or.nexus.commons.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {
	private String groupId;
	private String projectId;

	private int totalRecord; // DB 조회
	private int page; // 사용자의 요청 파라미터
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public void setPage(int page) {
		this.page = page;
	}


	private int recordCount; // 임의 결정
	private int pageSize; // 임의 결정

	public PaginationInfo() {
		this(10, 5);
	}
	public PaginationInfo(int recordCount, int pageSize) {
		super();
		this.recordCount = recordCount;
		this.pageSize = pageSize;
	}

	private SimpleCondition simpleCondition;
	public void setSimpleCondition(SimpleCondition simpleCondition) {
		this.simpleCondition = simpleCondition;
	}

	// 연산 필요
	private int totalPage;
	private int firstPage;
	private int lastPage;
	private int firstRecord;
	private int lastRecord;

	public int getTotalPage() {
		return ((totalRecord-1)/recordCount)+1;
	}
	public int getFirstPage() {
		return ((page-1)/pageSize)*pageSize + 1;
	}
	public int getLastPage() {
		int lastPageNoOnPageList = getFirstPage()+pageSize;
		if(lastPageNoOnPageList>getTotalPage()){lastPageNoOnPageList=getTotalPage();}
		return lastPageNoOnPageList;
	}
	public int getFirstRecord() {
		return (page - 1) * recordCount + 1;
	}
	public int getLastRecord() {
		int lastRecordOnPage = page * recordCount;
	    if (lastRecordOnPage > totalRecord) {
	        lastRecordOnPage = totalRecord;
	    }
	    return lastRecordOnPage;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}


