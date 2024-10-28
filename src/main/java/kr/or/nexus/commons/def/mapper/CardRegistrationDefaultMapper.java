package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.CardRegistrationDefaultVO;

@Mapper
public interface CardRegistrationDefaultMapper {
    int deleteByPrimaryKey(Long cardId);

    int insert(CardRegistrationDefaultVO row);

    CardRegistrationDefaultVO selectByPrimaryKey(Long cardId);

    List<CardRegistrationDefaultVO> selectAll();

    int updateByPrimaryKey(CardRegistrationDefaultVO row);
}