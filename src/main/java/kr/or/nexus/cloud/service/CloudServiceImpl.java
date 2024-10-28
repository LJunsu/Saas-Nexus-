package kr.or.nexus.cloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.cloud.dao.CloudMapper;
import kr.or.nexus.vo.FolderAccessVO;

@Service
public class CloudServiceImpl implements CloudService {
	
	@Autowired
	private CloudMapper dao;

	@Override
	public List<FolderAccessVO> findByProjectId(FolderAccessVO vo) {
		return dao.findByProjectId(vo);
	}

	@Override
	public void insert(FolderAccessVO pattern) {
		dao.insert(pattern);
	}

	@Override
	public void update(FolderAccessVO pattern) {
		dao.update(pattern);
	}

	@Override
	public void delete(String accessId) {
		dao.delete(accessId);
	}

	
	
}
