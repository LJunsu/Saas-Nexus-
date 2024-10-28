package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.ChatRoomSettingsDefaultVO;

@Mapper
public interface ChatRoomSettingsDefaultMapper {
    int deleteByPrimaryKey(String chatSettingsId);

    int insert(ChatRoomSettingsDefaultVO row);

    ChatRoomSettingsDefaultVO selectByPrimaryKey(String chatSettingsId);

    List<ChatRoomSettingsDefaultVO> selectAll();

    int updateByPrimaryKey(ChatRoomSettingsDefaultVO row);
}