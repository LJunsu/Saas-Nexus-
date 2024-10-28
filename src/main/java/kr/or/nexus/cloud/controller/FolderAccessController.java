package kr.or.nexus.cloud.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.cloud.dao.CloudMapper;
import kr.or.nexus.commons.def.mapper.ProjectRoleDefaultMapper;
import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.vo.FolderAccessVO;
import kr.or.nexus.vo.ProjectRoleVO;

@RestController
@RequestMapping("/gcp/projects/{projectId}/folder-access")
public class FolderAccessController {

    @Autowired
    private CloudMapper cloudMapper;
    
    @Autowired
    private ProjectRoleService service;

    @PostMapping
    @Transactional
    public ResponseEntity<?> addFolderAccessPattern(
            @PathVariable String projectId,
            @RequestBody Map<String, Object> payload
            ) throws UnsupportedEncodingException {
        
    	List<String> projectRoleIdxList = (List<String>) payload.get("projectRoleIdx");
        String folderPattern = (String) payload.get("folderPattern");
        String decodedFolderPattern = URLDecoder.decode(folderPattern, "UTF-8");
        
        for (String projectRoleIdx : projectRoleIdxList) {
            ProjectRoleVO role = service.findById(projectRoleIdx);
            if (role == null) {
                return ResponseEntity.badRequest().body("Role not found: " + projectRoleIdx);
            }
            
            FolderAccessVO accessPattern = new FolderAccessVO();
            accessPattern.setProjectId(projectId);
            accessPattern.setAccessPattern(decodedFolderPattern);
            accessPattern.setProjectRoleIdx(projectRoleIdx);
            
            cloudMapper.insert(accessPattern);
        }
        
        
        return ResponseEntity.ok().build();
    }

    // 기타 패턴 관리 API (삭제, 수정 등)
}