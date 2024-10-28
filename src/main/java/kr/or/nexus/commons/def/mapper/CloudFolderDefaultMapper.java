package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CloudFolderDefaultVO;

@Mapper
public interface CloudFolderDefaultMapper {
    int deleteByPrimaryKey(@Param("cloudFolderId") String cloudFolderId, @Param("cloudId") String cloudId, @Param("projectId") String projectId);

    int insert(CloudFolderDefaultVO row);

    CloudFolderDefaultVO selectByPrimaryKey(@Param("cloudFolderId") String cloudFolderId, @Param("cloudId") String cloudId, @Param("projectId") String projectId);

    List<CloudFolderDefaultVO> selectAll();

    int updateByPrimaryKey(CloudFolderDefaultVO row);
}