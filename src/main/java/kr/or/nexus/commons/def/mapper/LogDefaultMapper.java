package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.LogDefaultVO;

@Mapper
public interface LogDefaultMapper {
    int deleteByPrimaryKey(Integer logIdx);

    int insert(LogDefaultVO row);

    LogDefaultVO selectByPrimaryKey(Integer logIdx);

    List<LogDefaultVO> selectAll();

    int updateByPrimaryKey(LogDefaultVO row);
}