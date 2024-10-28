package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class CloudRepositoryDefaultVO implements Serializable {
    private String cloudId;

    private String projectId;

    private Long cloudStorage;

    private static final long serialVersionUID = 1L;
}