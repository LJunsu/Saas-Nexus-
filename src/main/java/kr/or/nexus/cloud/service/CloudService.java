package kr.or.nexus.cloud.service;

import java.util.List;

import kr.or.nexus.vo.FolderAccessVO;

public interface CloudService {
	
	public List<FolderAccessVO> findByProjectId(FolderAccessVO vo);
    public void insert(FolderAccessVO pattern);
    public void update(FolderAccessVO pattern);
    public void delete(String accessId);
	
}
