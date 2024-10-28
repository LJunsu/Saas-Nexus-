package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.ChattingRoomDefaultVO;

@Mapper
public interface ChattingRoomDefaultMapper {
    int deleteByPrimaryKey(String chattingRoomId);

    int insert(ChattingRoomDefaultVO row);

    ChattingRoomDefaultVO selectByPrimaryKey(String chattingRoomId);

    List<ChattingRoomDefaultVO> selectAll();

    int updateByPrimaryKey(ChattingRoomDefaultVO row);
}