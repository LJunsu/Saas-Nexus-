package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupManagementVO implements Serializable {
    private String groupId;

    private String groupName;

    private String groupProfilePicture;

    private MultipartFile groupImage;
    public void setGroupImage(MultipartFile groupImage) {
		if(groupImage!=null && groupImage.isEmpty()) return;
		this.groupImage = groupImage;
		this.groupProfilePicture = UUID.randomUUID().toString();
	}

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate groupCreationDate;

    private String groupDelyn;

    private String useStatus;

    private String businessLicenseId;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate applicationDate;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate approvalDate;

    private String groupComment;

    private String groupZip;
    private String groupAddress;
    private String groupAddress2;

    private String memberId;

    private MemberManagementVO memberManagementVO;
    private List<ProjectVO> project;
    private List<SubscriptionPaymentVO> subscriptionPaymentList;

    private static final long serialVersionUID = 1L;
}