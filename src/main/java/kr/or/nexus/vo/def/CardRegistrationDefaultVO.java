package kr.or.nexus.vo.def;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CardRegistrationDefaultVO implements Serializable {
    private Long cardId;

    private String cardNum;

    private LocalDateTime cardValidityDate;

    private String cardMemberName;

    private String cardPw;

    private String cardCompany;

    private String groupId;

    private static final long serialVersionUID = 1L;
}