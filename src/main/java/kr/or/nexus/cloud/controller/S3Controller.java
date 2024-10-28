package kr.or.nexus.cloud.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;

import kr.or.nexus.cloud.service.S3Service;
import kr.or.nexus.vo.S3ObjectInfo;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
public class S3Controller {
	
	private final S3Service s3Service;
	
	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@GetMapping("/list")
    public List<S3ObjectInfo> listFiles(@RequestParam("folder") String folderName) {
        // S3FolderService의 listFilesInFolder 메서드를 호출하여 파일 목록을 가져옴
        return s3Service.listFilesInFolder(folderName);
    }
	
	@GetMapping("/list-root")
    public List<S3ObjectInfo> listRootFilesAndFolders() {
        // S3FolderService의 listRootFilesAndFolders 메서드를 호출하여 루트 경로의 파일과 폴더 목록을 가져옴
        return s3Service.listRootFilesAndFolders();
    }

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(
	    @RequestParam("file") MultipartFile file, 
	    @RequestParam(value = "folderPath", required = false) String folderPath) {
	    try {
	        // 폴더 경로가 null이거나 빈 문자열이면 루트로 설정
	        folderPath = (folderPath == null || folderPath.isEmpty()) ? "" : folderPath;

	        // S3UploadService의 saveFile 메서드를 호출하여 파일을 S3에 업로드
	        String fileUrl = s3Service.saveFile(file, folderPath);
	        
	        // 파일 업로드가 성공하면 파일의 URL을 반환
	        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
	    } catch (IOException e) {
	        // 파일 업로드가 실패하면 에러 메시지와 상태 코드를 반환
	        return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    @PostMapping("/create-folder")
    public ResponseEntity<?> createFolder(@RequestBody Map<String, String> payload) {
        String folderName = payload.get("folderName");
        
        try {
            // S3 또는 파일 시스템에 폴더 생성 로직
            s3Service.createFolder(folderName);
            return ResponseEntity.ok("Folder created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to create folder: " + e.getMessage());
        }
    }
    
    @GetMapping("/download")
    public ResponseEntity<UrlResource> downloadFile(@RequestParam("filename") String filename) {
        // S3UploadService의 downloadImage 메서드를 호출하여 파일을 다운로드합니다.
    	try {
            // URL 디코딩
            String decodedFilename = URLDecoder.decode(filename, "UTF-8");

            // 파일 다운로드 처리
            return s3Service.downloadImage(decodedFilename);
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.badRequest().build(); // 디코딩 실패 시 400 응답
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam("fileName") String filename) {
        try {
            // S3UploadService의 deleteImage 메서드를 호출하여 S3에서 파일을 삭제합니다.
        	s3Service.deleteImage(filename);

            // 삭제가 성공하면 HTTP 상태 코드 200과 성공 메시지를 반환합니다.
            return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            // 에러가 발생하면 HTTP 상태 코드 500과 에러 메시지를 반환합니다.
            return new ResponseEntity<>("Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/deleteFolder")
    public ResponseEntity<String> deleteFolder(@RequestParam("fileName") String filename) {
        try {
            // S3UploadService의 deleteImage 메서드를 호출하여 S3에서 파일을 삭제합니다.
        	s3Service.deleteFolderAndContents(filename);

            // 삭제가 성공하면 HTTP 상태 코드 200과 성공 메시지를 반환합니다.
            return new ResponseEntity<>("File deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            // 에러가 발생하면 HTTP 상태 코드 500과 에러 메시지를 반환합니다.
            return new ResponseEntity<>("Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/preview")
    public String preview(@RequestParam("fileName") String filename) {
    	try {
            // URL 디코딩
            String decodedFileName = URLDecoder.decode(filename, "UTF-8");
            // S3에서 파일의 URL을 가져옵니다.
            String url = amazonS3.getUrl(bucket, decodedFileName).toString();
            return url;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to decode file name", e);
        }
    }
}
