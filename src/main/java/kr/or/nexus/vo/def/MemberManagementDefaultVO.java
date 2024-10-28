package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberManagementDefaultVO implements Serializable {
    private String memberId;

    private String memberPw;

    private String memberName;

    private Integer memberRegno1;

    private Integer memberRegno2;

    private Integer memberZip;

    private String memberAddress1;

    private String memberAddress2;

    private String memberTel;

    private String memberEmail;

    private String memberProfilePict;

    private String memberDescription;

    private String memberAuthQuestion;

    private String memberAuthResponse;

    private String memberDelyn;

    private static final long serialVersionUID = 1L;
}