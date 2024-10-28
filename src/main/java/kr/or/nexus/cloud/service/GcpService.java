package kr.or.nexus.cloud.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.BlobListOption;
import com.google.common.collect.ImmutableMap;

import kr.or.nexus.issue.service.IssueService;
import kr.or.nexus.vo.GcsObjectInfo;
import kr.or.nexus.vo.IssueVO;

@Service
@PropertySource("classpath:gcp.properties")
public class GcpService {

	@Autowired
	private Storage storage;

	@Value("${gcp.bucket.name}")
	private String bucketName;

	@Autowired
	private IssueService issueService;

	public String saveFile(MultipartFile multipartFile, String folderPath, String projectId, String username)
			throws IOException {

		String originalFilename = multipartFile.getOriginalFilename();
		String fileKey = "";
		if (projectId == null || projectId.isEmpty()) {
			fileKey = (folderPath.isEmpty() ? originalFilename
					: (folderPath.endsWith("/") ? folderPath + originalFilename : folderPath + "/" + originalFilename));
		} else {
			// projectId를 기본 경로로 사용하고, folderPath를 추가
			fileKey = projectId + "/" + (folderPath.isEmpty() ? originalFilename
					: (folderPath.endsWith("/") ? folderPath + originalFilename : folderPath + "/" + originalFilename));
		}

		BlobId blobId = BlobId.of(bucketName, fileKey);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType())
				.setMetadata(ImmutableMap.of("username", username)) // 메타데이터에 사용자 이름 추가
				.build();

		storage.create(blobInfo, multipartFile.getBytes());
		return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileKey);
	}

	public String uploadFile(MultipartFile multipartFile, String folderPath) throws IOException {

		String originalFilename = multipartFile.getOriginalFilename();
		String fileKey = "";
		fileKey = (folderPath.isEmpty() ? originalFilename
				: (folderPath.endsWith("/") ? folderPath + originalFilename : folderPath + "/" + originalFilename));

		BlobId blobId = BlobId.of(bucketName, fileKey);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();

		storage.create(blobInfo, multipartFile.getBytes());
		String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, fileKey);
		return fileUrl;
	}

	public String uploadProfile(MultipartFile multipartFile, String folderPath) throws IOException {

//		String originalFilename = multipartFile.getOriginalFilename();
//		String extension = getFileExtension(originalFilename);
		String profile = "profile.png";
		String fileKey = "";
		fileKey = (folderPath.isEmpty() ? profile
				: (folderPath.endsWith("/") ? folderPath + profile : folderPath + "/" + profile));

		BlobId blobId = BlobId.of(bucketName, fileKey);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();

		storage.create(blobInfo, multipartFile.getBytes());
		String fileUrl = String.format("https://storage.cloud.google.com/%s/%s", bucketName, fileKey);
		return fileUrl;
	}

	public String uploadEnterFile(MultipartFile multipartFile, String folderPath) throws IOException {

		String file = "businessLicense.png";
		String fileKey = "";
		fileKey = (folderPath.isEmpty() ? file
				: (folderPath.endsWith("/") ? folderPath + file : folderPath + "/" + file));

		BlobId blobId = BlobId.of(bucketName, fileKey);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(multipartFile.getContentType()).build();

		storage.create(blobInfo, multipartFile.getBytes());
		String fileUrl = String.format("https://storage.googleapis.com/%s/%s", bucketName, fileKey);
		return fileUrl;
	}

	public ResponseEntity<ByteArrayResource> downloadFile(String originalFilename, String projectId)
			throws UnsupportedEncodingException {
		Blob blob = storage.get(BlobId.of(bucketName, projectId + "/" + originalFilename));
		String safeFileName = generateSafeFileName(originalFilename);

		HttpHeaders headers = new HttpHeaders();
		String encodedFileName = URLEncoder.encode(safeFileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+",
				"%20");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);
		headers.add(HttpHeaders.CONTENT_TYPE, blob.getContentType());

		ByteArrayResource resource = new ByteArrayResource(blob.getContent());

		return ResponseEntity.ok().headers(headers).body(resource);
	}

	public ResponseEntity<ByteArrayResource> download(String originalFilename) throws UnsupportedEncodingException {
		Blob blob = storage.get(BlobId.of(bucketName, originalFilename));
		String safeFileName = generateSafeFileName(originalFilename);

		HttpHeaders headers = new HttpHeaders();
		String encodedFileName = URLEncoder.encode(safeFileName, StandardCharsets.UTF_8.toString()).replaceAll("\\+",
				"%20");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName);
		headers.add(HttpHeaders.CONTENT_TYPE, blob.getContentType());

		ByteArrayResource resource = new ByteArrayResource(blob.getContent());

		return ResponseEntity.ok().headers(headers).body(resource);
	}

	private String generateSafeFileName(String originalFilename) {
		String extension = "";
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex > 0) {
			extension = originalFilename.substring(dotIndex);
		}
		return UUID.randomUUID() + extension;
	}

	public void deleteFile(String filename, String projectId) {
		storage.delete(BlobId.of(bucketName, projectId + "/" + filename));
	}

	public void deleteFolderAndContents(String folderName, String projectId) {
		String prefix = projectId + "/" + (folderName.endsWith("/") ? folderName : folderName + "/");
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.prefix(prefix));

		for (Blob blob : blobs.iterateAll()) {
			storage.delete(blob.getBlobId());
		}
	}

	public List<GcsObjectInfo> listFilesInFolder(String folderName, String projectId) {
		if (folderName == null) {
			throw new IllegalArgumentException("Folder name cannot be null");
		}

		String prefix = projectId + "/" + (folderName.isEmpty() || folderName.equals("/") ? ""
				: (folderName.endsWith("/") ? folderName : folderName + "/"));
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.prefix(prefix), BlobListOption.currentDirectory());

		List<GcsObjectInfo> objectInfoList = new ArrayList<>();
		for (Blob blob : blobs.iterateAll()) {
			String blobName = blob.getName();
			if (blobName.equals(prefix))
				continue; // Skip the folder itself

			String fileName;
			boolean isDirectory;

			if (prefix.isEmpty()) {
				// 루트 디렉토리의 경우
				int slashIndex = blobName.indexOf('/');
				if (slashIndex == -1) {
					// 파일인 경우
					fileName = blobName;
					isDirectory = false;
				} else {
					// 폴더인 경우
					fileName = blobName.substring(0, slashIndex);
					isDirectory = true;
				}
			} else {
				// 하위 디렉토리의 경우
				fileName = blobName.substring(prefix.length());
				isDirectory = fileName.endsWith("/");
				if (isDirectory) {
					fileName = fileName.substring(0, fileName.length() - 1); // Remove trailing slash
				}
			}

			OffsetDateTime updateTime = blob.getUpdateTimeOffsetDateTime();
			LocalDate lastModified = updateTime != null ? updateTime.toLocalDate() : null;
			String fileType = isDirectory ? "" : getFileExtension(fileName);
			String formatSize = formatFileSize(blob.getSize());

			String username = blob.getMetadata() != null ? blob.getMetadata().get("username") : null;
			GcsObjectInfo objectInfo = new GcsObjectInfo(fileName, isDirectory ? "folder" : "file", lastModified,
					blob.getSize(), fileType, username, formatSize);
			objectInfoList.add(objectInfo);
		}
		return objectInfoList;
	}

	public List<GcsObjectInfo> listFilesInFolder(String folderName) {
		if (folderName == null) {
			throw new IllegalArgumentException("Folder name cannot be null");
		}

		String prefix = (folderName.isEmpty() || folderName.equals("/") ? ""
				: (folderName.endsWith("/") ? folderName : folderName + "/"));
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.prefix(prefix), BlobListOption.currentDirectory());

		List<GcsObjectInfo> objectInfoList = new ArrayList<>();
		for (Blob blob : blobs.iterateAll()) {
			String blobName = blob.getName();
			if (blobName.equals(prefix))
				continue; // Skip the folder itself

			String fileName;
			boolean isDirectory;

			if (prefix.isEmpty()) {
				// 루트 디렉토리의 경우
				int slashIndex = blobName.indexOf('/');
				if (slashIndex == -1) {
					// 파일인 경우
					fileName = blobName;
					isDirectory = false;
				} else {
					// 폴더인 경우
					fileName = blobName.substring(0, slashIndex);
					isDirectory = true;
				}
			} else {
				// 하위 디렉토리의 경우
				fileName = blobName.substring(prefix.length());
				isDirectory = fileName.endsWith("/");
				if (isDirectory) {
					fileName = fileName.substring(0, fileName.length() - 1); // Remove trailing slash
				}
			}

			OffsetDateTime updateTime = blob.getUpdateTimeOffsetDateTime();
			LocalDate lastModified = updateTime != null ? updateTime.toLocalDate() : null;
			String fileType = isDirectory ? "" : getFileExtension(fileName);

			GcsObjectInfo objectInfo = new GcsObjectInfo(fileName, isDirectory ? "folder" : "file", lastModified,
					blob.getSize(), fileType);
			objectInfoList.add(objectInfo);
		}
		return objectInfoList;
	}

	@Cacheable(value = "fileListCache", key = "#folderName")
	public List<GcsObjectInfo> issueList(String folderName) {
		if (folderName == null) {
			throw new IllegalArgumentException("Folder name cannot be null");
		}

		String prefix = (folderName.isEmpty() || folderName.equals("/") ? ""
				: (folderName.endsWith("/") ? folderName : folderName + "/"));
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.prefix(prefix), BlobListOption.currentDirectory());

		return StreamSupport.stream(blobs.iterateAll().spliterator(), false)
				.filter(blob -> !blob.getName().equals(prefix)).map(this::mapToGcsObjectInfo).filter(Objects::nonNull) // null이
																														// 아닌
																														// 객체만
																														// 필터링
				.collect(Collectors.toList());
	}

	private GcsObjectInfo mapToGcsObjectInfo(Blob blob) {
		String blobName = blob.getName();
		String folderPath;
		String fileName;
		boolean isDirectory;

		int lastSlashIndex = blobName.lastIndexOf('/');
		if (lastSlashIndex == -1) {
			// 루트 디렉토리의 파일
			folderPath = "";
			fileName = blobName;
			isDirectory = false;
		} else {
			folderPath = blobName.substring(0, lastSlashIndex);
			fileName = blobName.substring(lastSlashIndex + 1);
			isDirectory = fileName.isEmpty(); // 폴더인 경우 마지막 슬래시 뒤에 아무것도 없음
		}

		IssueVO issue = issueService.beforeIssueDetail(folderPath);
		if (issue == null) {
			return null; // issue가 null인 경우 null 반환
		}

		String issueName = (issue != null) ? issue.getIssueTitle() : "Unknown Issue";

		if (isDirectory) {
			fileName = folderPath.substring(folderPath.lastIndexOf('/') + 1);
		}

		OffsetDateTime updateTime = blob.getUpdateTimeOffsetDateTime();
		LocalDate lastModified = updateTime != null ? updateTime.toLocalDate() : null;
		String fileType = isDirectory ? "" : getFileExtension(fileName);
		String formatSize = formatFileSize(blob.getSize());

		return new GcsObjectInfo(folderPath, fileName, isDirectory ? "folder" : "file", lastModified, blob.getSize(),
				fileType, issueName, formatSize);
	}

	private String getFileExtension(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
		return (dotIndex > 0 && dotIndex < fileName.length() - 1) ? fileName.substring(dotIndex + 1).toLowerCase() : "";
	}
	
	private String formatFileSize(long size) {
	    if (size <= 0) return "0 B";
	    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}

	public void createFolder(String folderName, String projectId) {
		String folderKey = projectId + "/" + (folderName.endsWith("/") ? folderName : folderName + "/");
		BlobId blobId = BlobId.of(bucketName, folderKey);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, new byte[0]);
	}

	public List<GcsObjectInfo> searchFilesAndFolders(String searchName, String projectId) {
		if (searchName == null || searchName.trim().isEmpty()) {
			throw new IllegalArgumentException("Search name cannot be null or empty");
		}

		String prefix = projectId + "/";
		Page<Blob> blobs = storage.list(bucketName, BlobListOption.prefix(prefix));

		List<GcsObjectInfo> searchResults = new ArrayList<>();
		for (Blob blob : blobs.iterateAll()) {
			String blobName = blob.getName().substring(prefix.length()); // Remove projectId prefix
			if (blobName.toLowerCase().contains(searchName.toLowerCase())) {
				boolean isDirectory = blobName.endsWith("/");
				String fileName = isDirectory ? blobName.substring(0, blobName.length() - 1) : blobName;

				int lastSlashIndex = fileName.lastIndexOf('/');
				if (lastSlashIndex != -1) {
					fileName = fileName.substring(lastSlashIndex + 1);
				}

				OffsetDateTime updateTime = blob.getUpdateTimeOffsetDateTime();
				LocalDate lastModified = updateTime != null ? updateTime.toLocalDate() : null;
				String fileType = isDirectory ? "" : getFileExtension(fileName);
				String formatSize = formatFileSize(blob.getSize());
				String username = blob.getMetadata() != null ? blob.getMetadata().get("username") : null;

				GcsObjectInfo objectInfo = new GcsObjectInfo(fileName, isDirectory ? "folder" : "file", lastModified,
						blob.getSize(), fileType, username, formatSize);
				searchResults.add(objectInfo);
			}
		}

		return searchResults;
	}

}
