package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CanvasDataDefaultVO;

@Mapper
public interface CanvasDataDefaultMapper {
    int deleteByPrimaryKey(@Param("canvasDataId") String canvasDataId, @Param("canvasId") String canvasId);

    int insert(CanvasDataDefaultVO row);

//    CanvasDataDefaultVOWithBLOBs selectByPrimaryKey(@Param("canvasDataId") String canvasDataId, @Param("canvasId") String canvasId);

    List<CanvasDataDefaultVO> selectAll();

    int updateByPrimaryKey(CanvasDataDefaultVO row);
}