package kr.or.nexus.commons.AtchFile.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
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

import kr.or.nexus.gnotification.service.GnoticeService;
import kr.or.nexus.informationBoard.service.InformationBoardService;
import kr.or.nexus.vo.def.AtchFileDetailsVO;

@Controller
@RequestMapping("/attached/Information")
public class InformationAtchFileDownloadAndRemove {
	@Autowired
	private InformationBoardService service;

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
	public Map<String, Object> deleteAttatch(@PathVariable String atchFileId, @PathVariable String atchFileDetailId) {
		service.removeFile(atchFileId, atchFileDetailId);
		return Collections.singletonMap("success", true);
	}

}
















