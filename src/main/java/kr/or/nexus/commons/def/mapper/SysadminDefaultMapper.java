package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.SysadminDefaultVO;

@Mapper
public interface SysadminDefaultMapper {
    int deleteByPrimaryKey(String sysadminId);

    int insert(SysadminDefaultVO row);

    SysadminDefaultVO selectByPrimaryKey(String sysadminId);

    List<SysadminDefaultVO> selectAll();

    int updateByPrimaryKey(SysadminDefaultVO row);
}