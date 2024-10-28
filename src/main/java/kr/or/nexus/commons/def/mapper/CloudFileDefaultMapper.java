package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CloudFileDefaultVO;

@Mapper
public interface CloudFileDefaultMapper {
    int deleteByPrimaryKey(@Param("cloudFileId") String cloudFileId, @Param("cloudFolderId") String cloudFolderId, @Param("cloudId") String cloudId, @Param("projectId") String projectId);

    int insert(CloudFileDefaultVO row);

    CloudFileDefaultVO selectByPrimaryKey(@Param("cloudFileId") String cloudFileId, @Param("cloudFolderId") String cloudFolderId, @Param("cloudId") String cloudId, @Param("projectId") String projectId);

    List<CloudFileDefaultVO> selectAll();

    int updateByPrimaryKey(CloudFileDefaultVO row);
}