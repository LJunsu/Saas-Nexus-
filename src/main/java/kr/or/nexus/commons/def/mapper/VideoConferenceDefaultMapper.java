package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.VideoConferenceDefaultVO;

@Mapper
public interface VideoConferenceDefaultMapper {
    int deleteByPrimaryKey(String videoConferenceId);

    int insert(VideoConferenceDefaultVO row);

    VideoConferenceDefaultVO selectByPrimaryKey(String videoConferenceId);

    List<VideoConferenceDefaultVO> selectAll();

    int updateByPrimaryKey(VideoConferenceDefaultVO row);
}