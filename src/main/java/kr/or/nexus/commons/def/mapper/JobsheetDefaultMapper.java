package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.JobsheetDefaultVO;

@Mapper
public interface JobsheetDefaultMapper {
    int deleteByPrimaryKey(Short jobsheetId);

    int insert(JobsheetDefaultVO row);

    JobsheetDefaultVO selectByPrimaryKey(Short jobsheetId);

    List<JobsheetDefaultVO> selectAll();

    int updateByPrimaryKey(JobsheetDefaultVO row);
}