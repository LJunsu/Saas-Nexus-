package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CanvasDefaultVO;

@Mapper
public interface CanvasDefaultMapper {
    int deleteByPrimaryKey(@Param("canvasId") Integer canvasId, @Param("projectId") String projectId);

    int insert(CanvasDefaultVO row);

    CanvasDefaultVO selectByPrimaryKey(@Param("canvasId") Integer canvasId, @Param("projectId") String projectId);

    List<CanvasDefaultVO> selectAll();

    int updateByPrimaryKey(CanvasDefaultVO row);
}