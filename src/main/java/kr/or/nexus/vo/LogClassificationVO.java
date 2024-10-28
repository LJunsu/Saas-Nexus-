package kr.or.nexus.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class LogClassificationVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String classIdx;
    private String className;
    private String classAddress;
    private String classParent;
    private String classMethod;
}