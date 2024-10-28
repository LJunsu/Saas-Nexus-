package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.FolderAccessRoleDefaultVO;

@Mapper
public interface FolderAccessRoleDefaultMapper {
    int deleteByPrimaryKey(@Param("projectRoleIdx") String projectRoleIdx, @Param("cloudFolderId") String cloudFolderId, @Param("projectId") String projectId);

    int insert(FolderAccessRoleDefaultVO row);

    FolderAccessRoleDefaultVO selectByPrimaryKey(@Param("projectRoleIdx") String projectRoleIdx, @Param("cloudFolderId") String cloudFolderId, @Param("projectId") String projectId);

    List<FolderAccessRoleDefaultVO> selectAll();

    int updateByPrimaryKey(FolderAccessRoleDefaultVO row);
}