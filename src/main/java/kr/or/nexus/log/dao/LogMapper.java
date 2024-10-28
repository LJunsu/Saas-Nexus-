package kr.or.nexus.log.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.LogClassificationVO;
import kr.or.nexus.vo.LogVO;

@Mapper
public interface LogMapper {

	public LogClassificationVO confirmLogClass(LogClassificationVO logClassificationVO);

	public void insertLogClass(LogClassificationVO logClassificationVO);

	public void insertLog(Map<String, Object> params);
}
