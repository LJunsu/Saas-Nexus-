package kr.or.nexus.announcement.dto;

public class ProjectLeader {
	private String memberId;
	private String projectId;
	private String projectRoleName;

	// 생성자
	public ProjectLeader(String memberId, String projectId, String projectRoleName) {
        this.memberId = memberId;
        this.projectId = projectId;
        this.projectRoleName = projectRoleName;
    }

	// Getters and Setters
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectRoleName() {
		return projectRoleName;
	}

	public void setProjectRoleName(String projectRoleName) {
		this.projectRoleName = projectRoleName;
	}
}
