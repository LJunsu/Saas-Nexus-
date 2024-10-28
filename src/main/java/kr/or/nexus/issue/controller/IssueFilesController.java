package kr.or.nexus.issue.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.cloud.service.GcpService;
import kr.or.nexus.issue.service.IssueService;
import kr.or.nexus.vo.GcsObjectInfo;
import kr.or.nexus.vo.IssueVO;

@Controller
@RequestMapping("/group/{groupId}/project/{projectId}/files")
public class IssueFilesController {
	
	@Autowired
	private IssueService service;
	
	@Autowired
	private GcpService gcpService;
	
//	@GetMapping
//	public String filesUI(@PathVariable String projectId, Model model) {
//		List<IssueVO> issueList = service.retrieveIssueList(projectId);
//		List<GcsObjectInfo> list = new ArrayList<GcsObjectInfo>();
//		for(IssueVO issue : issueList) {
//			List<GcsObjectInfo> gcpvo = gcpService.listFilesInFolder(issue.getIssueIdx());			
//			for(GcsObjectInfo info : gcpvo) {
//				list.add(info);
//			}
//		}
//		
//		model.addAttribute("issueFiles", list);
//		return "tiles2:issueFiles/filesUI";
//	}
	
	@GetMapping
	public String filesUI(@PathVariable String projectId, @PathVariable String groupId, Model model) {
		List<IssueVO> issueList = service.retrieveIssueList(projectId);
	    model.addAttribute("groupId",groupId);
	    List<CompletableFuture<List<GcsObjectInfo>>> futures = issueList.stream()
	        .map(issue -> CompletableFuture.supplyAsync(() -> 
	            gcpService.issueList(issue.getIssueIdx())))
	        .collect(Collectors.toList());
	    
	    List<GcsObjectInfo> allFiles = futures.stream()
	        .map(CompletableFuture::join)
	        .flatMap(List::stream)
	        .collect(Collectors.toList());

	    // 파일들을 folderPath별로 그룹화
	    Map<String, List<GcsObjectInfo>> groupedFiles = allFiles.stream()
	        .collect(Collectors.groupingBy(GcsObjectInfo::getIssueName));
	    
	    model.addAttribute("projectId", projectId);
	    model.addAttribute("groupId", groupId);
	    model.addAttribute("groupedIssueFiles", groupedFiles);
	    return "tiles2:issueFiles/filesUI";
	}
	
	@GetMapping("/search")
	@ResponseBody
	public Map<String, List<GcsObjectInfo>> searchFiles(@PathVariable String projectId, @RequestParam String search) {
	    List<IssueVO> issueList = service.retrieveIssueList(projectId);
	    
	    List<CompletableFuture<List<GcsObjectInfo>>> futures = issueList.stream()
	        .map(issue -> CompletableFuture.supplyAsync(() -> 
	            gcpService.issueList(issue.getIssueIdx())))
	        .collect(Collectors.toList());
	    
	    List<GcsObjectInfo> allFiles = futures.stream()
	        .map(CompletableFuture::join)
	        .flatMap(List::stream)
	        .collect(Collectors.toList());

	    // 검색 쿼리와 일치하는 파일 또는 이슈 필터링
	    Map<String, List<GcsObjectInfo>> filteredFiles = allFiles.stream()
	        .filter(file -> file.getFileName().toLowerCase().contains(search.toLowerCase()) || 
	                        file.getIssueName().toLowerCase().contains(search.toLowerCase()))
	        .collect(Collectors.groupingBy(GcsObjectInfo::getIssueName));

	    return filteredFiles;
	}
}
