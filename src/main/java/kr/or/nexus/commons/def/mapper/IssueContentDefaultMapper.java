package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.IssueContentDefaultVO;

@Mapper
public interface IssueContentDefaultMapper {
    int deleteByPrimaryKey(@Param("issueContentIdx") String issueContentIdx, @Param("issueIdx") String issueIdx);

    int insert(IssueContentDefaultVO row);

    IssueContentDefaultVO selectByPrimaryKey(@Param("issueContentIdx") String issueContentIdx, @Param("issueIdx") String issueIdx);

    List<IssueContentDefaultVO> selectAll();

    int updateByPrimaryKey(IssueContentDefaultVO row);
}