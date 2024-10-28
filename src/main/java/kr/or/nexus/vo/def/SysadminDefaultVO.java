package kr.or.nexus.vo.def;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysadminDefaultVO implements Serializable {
    private String sysadminId;

    private String sysadminPw;

    private static final long serialVersionUID = 1L;
}