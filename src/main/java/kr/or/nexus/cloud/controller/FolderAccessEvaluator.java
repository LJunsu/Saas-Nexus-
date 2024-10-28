package kr.or.nexus.cloud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.or.nexus.cloud.service.CloudService;
import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.vo.FolderAccessVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Component
public class FolderAccessEvaluator {

	@Autowired
    private ProjectMemberService projectMemberService;
    
    @Autowired
    private CloudService service;

    public boolean hasAccessToFolder(String projectMemIdx, String projectId, String folderPath) {
        ProjectMemberVO projectMember = projectMemberService.findByPk(projectMemIdx);
        if (projectMember == null || projectMember.getProjectRoleIdx() == null || projectMember.getProjectRoleIdx().isEmpty()) {
            return false;
        }
        
     // 프로젝트 리더인 경우 항상 접근 허용
        if (projectMember.getProjectRoleVO() != null && 
            projectMember.getProjectRoleVO().getProjectRoleName().equals("프로젝트 리더")) {
            return true;
        }

        String userRoleIdx = projectMember.getProjectRoleIdx();
        
        FolderAccessVO vo = new FolderAccessVO();
        vo.setProjectId(projectId);
        vo.setAccessPattern(folderPath);
        
        List<FolderAccessVO> accessPatterns = service.findByProjectId(vo);
        
        // 접근 패턴이 없으면 접근 허용
        if (accessPatterns.isEmpty()) {
            return true;
        }
        
        return accessPatterns.stream()
                .filter(pattern -> pattern.getProjectRoleIdx().equals(userRoleIdx))
                .anyMatch(pattern -> folderMatchesPattern(folderPath, pattern.getAccessPattern()));
    }

    private boolean folderMatchesPattern(String folderPath, String pattern) {
        String regex = pattern.replace("*", ".*");
        return folderPath.matches(regex);
    }
	
}
