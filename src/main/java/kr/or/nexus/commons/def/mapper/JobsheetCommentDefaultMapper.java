package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.JobsheetCommentDefaultVO;

@Mapper
public interface JobsheetCommentDefaultMapper {
    int deleteByPrimaryKey(@Param("jobsheetCommentId") Long jobsheetCommentId, @Param("jobsheetId") Short jobsheetId);

    int insert(JobsheetCommentDefaultVO row);

    JobsheetCommentDefaultVO selectByPrimaryKey(@Param("jobsheetCommentId") Long jobsheetCommentId, @Param("jobsheetId") Short jobsheetId);

    List<JobsheetCommentDefaultVO> selectAll();

    int updateByPrimaryKey(JobsheetCommentDefaultVO row);
}