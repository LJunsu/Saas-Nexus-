package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.RecipientNotificationsDefaultVO;

@Mapper
public interface RecipientNotificationsDefaultMapper {
    int deleteByPrimaryKey(@Param("alarmMessageIdx") String alarmMessageIdx, @Param("alarmNotifications") String alarmNotifications);

    int insert(RecipientNotificationsDefaultVO row);

    RecipientNotificationsDefaultVO selectByPrimaryKey(@Param("alarmMessageIdx") String alarmMessageIdx, @Param("alarmNotifications") String alarmNotifications);

    List<RecipientNotificationsDefaultVO> selectAll();

    int updateByPrimaryKey(RecipientNotificationsDefaultVO row);
}