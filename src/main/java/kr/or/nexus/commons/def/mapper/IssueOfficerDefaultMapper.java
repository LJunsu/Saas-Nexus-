package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.IssueOfficerDefaultVO;

@Mapper
public interface IssueOfficerDefaultMapper {
    int deleteByPrimaryKey(@Param("issueOfficerIdx") String issueOfficerIdx, @Param("issueIdx") String issueIdx);

    int insert(IssueOfficerDefaultVO row);

    IssueOfficerDefaultVO selectByPrimaryKey(@Param("issueOfficerIdx") String issueOfficerIdx, @Param("issueIdx") String issueIdx);

    List<IssueOfficerDefaultVO> selectAll();

    int updateByPrimaryKey(IssueOfficerDefaultVO row);
}