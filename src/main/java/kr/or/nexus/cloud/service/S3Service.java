package kr.or.nexus.cloud.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import kr.or.nexus.vo.S3ObjectInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:aws.properties")
public class S3Service {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String saveFile(MultipartFile multipartFile, String folderPath) throws IOException {
		String originalFilename = multipartFile.getOriginalFilename();
		// 폴더 경로와 파일 이름을 결합하여 전체 경로 생성
		String fileKey = folderPath.endsWith("/") ? folderPath + originalFilename : originalFilename;

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

		amazonS3.putObject(bucket, fileKey, multipartFile.getInputStream(), metadata);
		return amazonS3.getUrl(bucket, fileKey).toString();
	}

	public ResponseEntity<UrlResource> downloadImage(String originalFilename) {
		try {
			// UUID를 사용하여 파일명 생성
			String safeFileName = generateSafeFileName(originalFilename);

			// 파일 다운로드 URL 생성
			UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));

			// HTTP 헤더에 안전한 파일명을 설정
			String contentDisposition = "attachment; filename=\"" + URLEncoder.encode(safeFileName, "UTF-8") + "\"";

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(urlResource);
		} catch (UnsupportedEncodingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 오류 발생 시 500 응답
		}
	}

	// UUID를 사용하여 안전한 파일명 생성
	private String generateSafeFileName(String originalFilename) {
		// 파일 확장자를 추출
		String extension = "";
		int dotIndex = originalFilename.lastIndexOf('.');
		if (dotIndex > 0) {
			extension = originalFilename.substring(dotIndex);
		}

		// UUID를 사용하여 고유한 파일명 생성
		String uniqueFileName = UUID.randomUUID().toString();

		// 확장자 추가
		return uniqueFileName + extension;
	}

	public void deleteImage(String originalFilename) {
		amazonS3.deleteObject(bucket, originalFilename);
	}

	public void deleteFolderAndContents(String folderName) {
		// 폴더 이름 뒤에 '/'를 추가하여 접두사로 사용
		String prefix = folderName.endsWith("/") ? folderName : folderName + "/";

		// 요청을 설정하여 특정 접두사를 가진 객체를 검색
		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket).withPrefix(prefix);

		// S3에서 결과를 가져옴
		ListObjectsV2Result result;
		do {
			result = amazonS3.listObjectsV2(request);

			// 검색된 객체들을 삭제
			for (S3ObjectSummary summary : result.getObjectSummaries()) {
				amazonS3.deleteObject(bucket, summary.getKey());
			}

			// 다음 페이지가 있는 경우 계속해서 처리
			request.setContinuationToken(result.getNextContinuationToken());
		} while (result.isTruncated()); // 모든 페이지를 다 처리할 때까지 반복
	}

	public List<S3ObjectInfo> listFilesInFolder(String folderName) {
		// 폴더 이름 뒤에 슬래시를 붙여서 접두사로 사용
		String prefix = folderName.endsWith("/") ? folderName : folderName + "/";

		// 요청을 설정하여 특정 접두사를 가진 객체를 검색
		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket).withPrefix(prefix)
				.withDelimiter("/"); // 폴더 구조처럼 동작하게 하기 위해 구분자를 설정

		// S3에서 결과를 가져옴
		ListObjectsV2Result result = amazonS3.listObjectsV2(request);

		// S3ObjectSummary 목록을 가져와서 파일 정보 목록을 생성
		List<S3ObjectInfo> s3ObjectInfoList = new ArrayList<>();

		// 폴더 내 파일 정보 추출
		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
			LocalDate lastModified = objectSummary.getLastModified().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			// 파일의 확장자를 추출
			String fileType = "";
			String fileName = objectSummary.getKey();
			int dotIndex = fileName.lastIndexOf('.');
			if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
				fileType = fileName.substring(dotIndex + 1).toLowerCase();
			}

			S3ObjectInfo fileInfo = new S3ObjectInfo(fileName.replaceFirst(prefix, ""), // 폴더 이름 제거
					"file", lastModified, objectSummary.getSize(), fileType);
			s3ObjectInfoList.add(fileInfo);
		}

		// 폴더 내 하위 폴더 정보 추출
		for (String commonPrefix : result.getCommonPrefixes()) {
			S3ObjectInfo folderInfo = new S3ObjectInfo(commonPrefix.replaceFirst(prefix, ""), // 접두사 제거
					"folder", null, // 폴더는 수정일 정보가 없음
					0L, // 폴더는 크기 정보가 없음
					"" // 폴더는 파일 유형이 없음
			);
			s3ObjectInfoList.add(folderInfo);
		}

		return s3ObjectInfoList;
	}

	// 기본 경로의 파일과 폴더 목록을 불러오는 메서드
	public List<S3ObjectInfo> listRootFilesAndFolders() {
		// 요청을 설정하여 루트 경로에서 객체를 검색
		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket).withDelimiter("/"); // 폴더 구조처럼
																												// 동작하게
																												// 하기 위해
																												// 구분자를
																												// 설정

		// S3에서 결과를 가져옴
		ListObjectsV2Result result = amazonS3.listObjectsV2(request);

		// 파일 정보 목록을 생성
		List<S3ObjectInfo> s3ObjectInfoList = new ArrayList<>();

		// 루트 경로의 파일 정보를 추출
		for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
			LocalDate lastModified = objectSummary.getLastModified().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();

			// 파일의 확장자를 추출 (파일명이 없는 경우 대비)
			String fileType = "";
			String fileName = objectSummary.getKey();
			int dotIndex = fileName.lastIndexOf('.');
			if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
				fileType = fileName.substring(dotIndex + 1).toLowerCase(); // 확장자를 추출하여 소문자로 변환
			}

			S3ObjectInfo fileInfo = new S3ObjectInfo(fileName, "file", // 파일임을 명시
					lastModified, objectSummary.getSize(), fileType // 추출한 파일 유형을 추가
			);
			s3ObjectInfoList.add(fileInfo);
		}

		// 루트 경로의 폴더 정보를 추출
		for (String commonPrefix : result.getCommonPrefixes()) {
			S3ObjectInfo folderInfo = new S3ObjectInfo(commonPrefix, "folder", // 폴더임을 명시
					null, // 폴더의 경우 수정일 정보가 없으므로 null로 설정
					0L, // 폴더의 경우 크기 정보도 0으로 설정
					"" // 폴더는 파일 유형이 없으므로 빈 문자열로 설정
			);
			s3ObjectInfoList.add(folderInfo);
		}

		return s3ObjectInfoList;
	}

	public void createFolder(String folderName) {
		// 폴더 이름 끝에 / 추가
		String folderKey = folderName + "/";

		// 빈 컨텐츠로 객체 메타데이터 생성
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// 빈 내용의 폴더 객체를 S3에 업로드
		amazonS3.putObject(bucket, folderKey, new ByteArrayInputStream(new byte[0]), metadata);
	}
}