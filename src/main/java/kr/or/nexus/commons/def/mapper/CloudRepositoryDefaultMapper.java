package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CloudRepositoryDefaultVO;

@Mapper
public interface CloudRepositoryDefaultMapper {
    int deleteByPrimaryKey(@Param("cloudId") String cloudId, @Param("projectId") String projectId);

    int insert(CloudRepositoryDefaultVO row);

    CloudRepositoryDefaultVO selectByPrimaryKey(@Param("cloudId") String cloudId, @Param("projectId") String projectId);

    List<CloudRepositoryDefaultVO> selectAll();

    int updateByPrimaryKey(CloudRepositoryDefaultVO row);
}