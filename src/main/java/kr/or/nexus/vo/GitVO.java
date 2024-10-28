package kr.or.nexus.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class GitVO implements Serializable {

    private String gitIdx;
    
    private String gitUrl;
    
    private String owner;
    
    private String repo;

    private String gitToken;
    
    private String projectId;
    
    private static final long serialVersionUID = 1L;
}