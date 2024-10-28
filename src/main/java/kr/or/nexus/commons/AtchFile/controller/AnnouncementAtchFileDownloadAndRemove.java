package kr.or.nexus.commons.AtchFile.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.announcement.service.AnnouncementService;
import kr.or.nexus.gnotification.service.GnoticeService;
import kr.or.nexus.vo.def.AtchFileDetailsVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/attached/announcement")
public class AnnouncementAtchFileDownloadAndRemove {
	@Autowired
	private AnnouncementService service;

	@GetMapping("{attachedFileId}/{attachedFileDetailId}")
	public ResponseEntity<Resource> download(AtchFileDetailsVO fileDetail) throws IOException {
		AtchFileDetailsVO atch = service.download(fileDetail.getAttachedFileId(), fileDetail.getAttachedFileDetailId());


		Resource savedFile = atch.getSavedFile();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(atch.getFileSize());
		ContentDisposition disposition = ContentDisposition.attachment()
											.filename(atch.getOriginalFileName(), Charset.forName("UTF-8"))
											.build();
		headers.setContentDisposition(disposition);
		return  ResponseEntity.ok()
						.headers(headers)
						.body(savedFile);
	}

	@DeleteMapping("{attachedFileId}/{attachedFileDetailId}")
	@ResponseBody
	public Map<String, Object> deleteAttatch(
	        @PathVariable String attachedFileId,
	        @PathVariable String attachedFileDetailId) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        service.removeFile(attachedFileId, attachedFileDetailId);
	        response.put("success", true);
	    } catch (Exception e) {
	        // 예외를 로깅
	        log.error("Failed to delete attachment", e);
	        response.put("success", false);
	        response.put("message", "Failed to delete attachment: " + e.getMessage());
	    }
	    return response;
	}

}
















