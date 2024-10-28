package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.VideoMeetingConferenceDefaultVO;

@Mapper
public interface VideoMeetingConferenceDefaultMapper {
    int deleteByPrimaryKey(String videoConferenceId);

    int insert(VideoMeetingConferenceDefaultVO row);

    VideoMeetingConferenceDefaultVO selectByPrimaryKey(String videoConferenceId);

    List<VideoMeetingConferenceDefaultVO> selectAll();

    int updateByPrimaryKey(VideoMeetingConferenceDefaultVO row);
}