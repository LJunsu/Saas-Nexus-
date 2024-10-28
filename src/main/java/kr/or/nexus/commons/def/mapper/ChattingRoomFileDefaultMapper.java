package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.ChattingRoomFileDefaultVO;

@Mapper
public interface ChattingRoomFileDefaultMapper {
    int deleteByPrimaryKey(@Param("chattingRoomFileId") String chattingRoomFileId, @Param("chattingRoomId") String chattingRoomId, @Param("attachedFileId") String attachedFileId);

    int insert(ChattingRoomFileDefaultVO row);

    List<ChattingRoomFileDefaultVO> selectAll();
}