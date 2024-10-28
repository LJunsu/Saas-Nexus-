package kr.or.nexus.cloud.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.cloud.service.GcpService;
import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.vo.GcsObjectInfo;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping
@Slf4j
public class GcpController {

	@Autowired
	private GcpService service;
	
	@Autowired
	private ProjectMemberService pmService;
	
	@Autowired
    private FolderAccessEvaluator folderAccessEvaluator;
	
	@Value("${gcp.bucket.name}")
    private String bucketName;
	
	@PostMapping("/gcp/{projectId}/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("folderPath") String folderPath,
                                             @PathVariable String projectId,
                                             HttpSession session) {
        try {
        	MemberManagementVO member = (MemberManagementVO) session.getAttribute("user");
            String username = member.getMemberName();
            String fileUrl = service.saveFile(file, folderPath, projectId, username);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        }
    }

	@PostMapping("/gcp/upload")
	public ResponseEntity<List<String>> upload(@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "files", required = false) List<MultipartFile> files,
			@RequestParam("folderPath") String folderPath) {
		List<String> fileUrls = new ArrayList<>();
	    List<MultipartFile> filesToUpload = new ArrayList<>();

	    // 단일 파일이 전송된 경우
	    if (file != null && !file.isEmpty()) {
	        filesToUpload.add(file);
	    }

	    // 다중 파일이 전송된 경우
	    if (files != null && !files.isEmpty()) {
	        filesToUpload.addAll(files);
	    }

	    // 파일이 전송되지 않은 경우
	    if (filesToUpload.isEmpty()) {
	        return ResponseEntity.badRequest().body(Collections.singletonList("No files were uploaded."));
	    }

	    for (MultipartFile single : filesToUpload) {
	        try {
	            String fileUrl = service.uploadFile(single, folderPath);
	            fileUrls.add(fileUrl);
	        } catch (IOException e) {
	            log.error("Failed to upload file: " + single.getOriginalFilename(), e);
	        }
	    }

	    if (fileUrls.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                             .body(Collections.singletonList("모든 파일 업로드에 실패했습니다."));
	    }

	    return ResponseEntity.ok(fileUrls);
	}

    @GetMapping("/gcp/{projectId}/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileName") String filename, @PathVariable String projectId) {
        try {
        	// URL 디코딩
            String decodedFilename = URLDecoder.decode(filename, "UTF-8");
            
            return service.downloadFile(decodedFilename, projectId);
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @GetMapping("/gcp/download")
    public ResponseEntity<ByteArrayResource> download(@RequestParam("fileName") String filename) {
    	try {
    		// URL 디코딩
    		String decodedFilename = URLDecoder.decode(filename, "UTF-8");
    		
    		return service.download(decodedFilename);
    	} catch (UnsupportedEncodingException e) {
    		return ResponseEntity.status(500).body(null);
    	}
    }

    @DeleteMapping("/gcp/{projectId}/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String filename, @PathVariable String projectId) {
    	// URL 디코딩
        try {
			String decodedFileName = URLDecoder.decode(filename, "UTF-8");
			service.deleteFile(decodedFileName, projectId);
			return ResponseEntity.ok("File deleted successfully");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to decode file name", e);
		}
    }

    @DeleteMapping("/gcp/{projectId}/deleteFolder")
    public ResponseEntity<String> deleteFolderAndContents(@RequestParam("folderName") String folderName, @PathVariable String projectId) {
    	// URL 디코딩
        try {
			String decodedFileName = URLDecoder.decode(folderName, "UTF-8");
			service.deleteFolderAndContents(decodedFileName, projectId);
			return ResponseEntity.ok("Folder and its contents deleted successfully");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Failed to decode file name", e);
		}
    }

    @GetMapping("/gcp/{projectId}/listFiles")
    public ResponseEntity<List<GcsObjectInfo>> listFilesInFolder(@RequestParam("folderName") String folderName,
    															@PathVariable String projectId,
    															HttpSession session) {
        try {
        	folderName = (folderName == null || folderName.isEmpty()) ? "" : folderName;
        	MemberManagementVO member = (MemberManagementVO) session.getAttribute("user");
        	ProjectMemberVO input = new ProjectMemberVO();
        	input.setProjectId(projectId);
        	input.setMemberId(member.getMemberId());
        	ProjectMemberVO pm = pmService.retrieveProjectMember(input);
        	String folderPath = projectId + '/' + folderName;
        	
        	if (!folderAccessEvaluator.hasAccessToFolder(pm.getProjectMemIdx(), projectId, folderPath)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        	
            List<GcsObjectInfo> fileList = service.listFilesInFolder(folderName, projectId);
            return ResponseEntity.ok(fileList);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/gcp/listFiles")
    public ResponseEntity<List<GcsObjectInfo>> listFiles(@RequestParam("folderName") String folderName,
    		HttpSession session) {
    	try {
    		folderName = (folderName == null || folderName.isEmpty()) ? "" : folderName;
    		
    		List<GcsObjectInfo> fileList = service.listFilesInFolder(folderName);
    		return ResponseEntity.ok(fileList);
    	} catch (IllegalArgumentException e) {
    		return ResponseEntity.badRequest().body(null);
    	}
    }

    @PostMapping("/gcp/{projectId}/createFolder")
    public ResponseEntity<String> createFolder(@RequestBody Map<String, String> payload, @PathVariable String projectId) {
    	String folderName = payload.get("folderName");
    	service.createFolder(folderName, projectId);
        return ResponseEntity.ok("Folder created successfully");
    }
    
    @GetMapping("/gcp/{projectId}/search")
    public ResponseEntity<List<GcsObjectInfo>> searchFilesAndFolders(
            @RequestParam("searchName") String searchName,
            @PathVariable String projectId,
            HttpSession session) {
        try {
            MemberManagementVO member = (MemberManagementVO) session.getAttribute("user");
            ProjectMemberVO input = new ProjectMemberVO();
            input.setProjectId(projectId);
            input.setMemberId(member.getMemberId());
            ProjectMemberVO pm = pmService.retrieveProjectMember(input);
            
            if (!folderAccessEvaluator.hasAccessToFolder(pm.getProjectMemIdx(), projectId, "")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            List<GcsObjectInfo> searchResults = service.searchFilesAndFolders(searchName, projectId);
            return ResponseEntity.ok(searchResults);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @GetMapping("/gcp/{projectId}/preview")
    public String preview(@RequestParam("fileName") String filename, @PathVariable String projectId) {
//        try {
            // URL 디코딩
//            String decodedFileName = URLDecoder.decode(filename, "UTF-8");
            // GCS에서 파일의 URL을 가져옵니다.
            String url = String.format("https://storage.googleapis.com/%s/%s/%s", bucketName, projectId, filename);
            return url;
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("Failed to decode file name", e);
//        }
    }
    
    @GetMapping("/gcp/filePreview")
    public String filePreview(@RequestParam("fileName") String filename) {
//        try {
            // URL 디코딩
//            String decodedFileName = URLDecoder.decode(filename, "UTF-8");
            // GCS에서 파일의 URL을 가져옵니다.
            String url = String.format("https://storage.googleapis.com/%s/%s", bucketName, filename);
            return url;
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("Failed to decode file name", e);
//        }
    }
}
