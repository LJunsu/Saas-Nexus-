package kr.or.nexus.cloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.FolderAccessVO;

@Mapper
public interface CloudMapper {
	
	public List<FolderAccessVO> findByProjectId(FolderAccessVO vo);
	
    public void insert(FolderAccessVO pattern);
    
    public void update(FolderAccessVO pattern);
    
    public void delete(String accessId);

}
