package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.IssueTagsDefaultVO;

@Mapper
public interface IssueTagsDefaultMapper {
    int deleteByPrimaryKey(@Param("issueTagsId") Integer issueTagsId, @Param("issueIdx") String issueIdx);

    int insert(IssueTagsDefaultVO row);

    IssueTagsDefaultVO selectByPrimaryKey(@Param("issueTagsId") Integer issueTagsId, @Param("issueIdx") String issueIdx);

    List<IssueTagsDefaultVO> selectAll();

    int updateByPrimaryKey(IssueTagsDefaultVO row);
}