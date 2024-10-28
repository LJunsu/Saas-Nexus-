package kr.or.nexus.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "accessId")
public class FolderAccessVO implements Serializable {
	private String accessId;
	private String projectId;
	private String projectRoleIdx;
	private String accessPattern;
	
	private ProjectRoleVO projectRole;

	private static final long serialVersionUID = 1L;
}
