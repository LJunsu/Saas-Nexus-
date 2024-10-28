package kr.or.nexus.codeShare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.codeShare.dao.CodeShareMapper;
import kr.or.nexus.codeShare.vo.codeShareVO;

@Service
public class CodeShareServiceImpl implements CodeShareService {

	@Autowired
	private CodeShareMapper codeShareMapper;

	@Override
	public int codeShareInsert(codeShareVO codeShareVO) {

		return codeShareMapper.codeShareInsert(codeShareVO);

	}

	@Override
	public List<codeShareVO> codeShareList(codeShareVO codeShareVO) {

		return codeShareMapper.codeShareList(codeShareVO);

	}

	@Override
	public codeShareVO codeShareDetail(codeShareVO codeShareVO) {

		return codeShareMapper.codeShareDetail(codeShareVO);

	}

	@Override
	public int codeShareUpdate(codeShareVO codeShareVO) {

		return codeShareMapper.codeShareUpdate(codeShareVO);

	}

	@Override
	public int codeShareDelete(codeShareVO codeShareVO) {

		return codeShareMapper.codeShareDelete(codeShareVO);

	}

	@Override
	public codeShareVO codeShareCreate(codeShareVO codeShareVO) {

		String nowSeqId = codeShareMapper.nowSeqId();
		codeShareVO.setCodeShareId(nowSeqId);
		return codeShareMapper.codeShareCreate(codeShareVO);

	}

}
