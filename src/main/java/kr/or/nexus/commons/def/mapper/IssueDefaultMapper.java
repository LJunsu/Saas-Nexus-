package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.IssueDefaultVO;

@Mapper
public interface IssueDefaultMapper {
    int deleteByPrimaryKey(String issueIdx);

    int insert(IssueDefaultVO row);

    IssueDefaultVO selectByPrimaryKey(String issueIdx);

    List<IssueDefaultVO> selectAll();

    int updateByPrimaryKey(IssueDefaultVO row);
}