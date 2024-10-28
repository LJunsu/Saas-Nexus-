package kr.or.nexus.vo.def;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


//첨부파일 한건에 대한VO == 해당 파일에 하나를 설정하는VO 
@EqualsAndHashCode(of = { "attachedFileId", "attachedFileDetailId" }) // { }안의 필드들을 기준으로 equals, hashCode 메서드 생성 , 첨부파일 그룹 ID , 해당 첨부파일 그룹에 속한 하나의 파일에대한 ID
@Data
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
public class AtchFileDetailsVO {

	@JsonIgnore // JSON 직렬화에서 이 필드를 제외
	@ToString.Exclude // toString 메서드에서 이 필드를 제외
	@Nullable // null 값을 허용
	private transient MultipartFile uploadFile; // 업로드된 파일을 임시로 저장하는 필드
	
	
	 public AtchFileDetailsVO(MultipartFile uploadFile) { // 생성자
	        super();
	        setUploadFile(uploadFile); // 업로드 파일 설정
	   }

	
	 
	 //클라이언트가 업로드한 파일에 대한 설정(SET) 메소드?
	 public void setUploadFile(MultipartFile uploadFile) {
		 	this.uploadFile = uploadFile;						//업로드된 파일
		 	this.saveFileName = UUID.randomUUID().toString();	//랜덤 UUID로 *저장파일명* 생성
		 	this.originalFileName = uploadFile.getOriginalFilename(); //클라이언트가 업로드한 실제 파일명
		 	this.fileExtensions = FilenameUtils.getExtension(originalFileName); //원본파일의 확장자
		 	this.fileSize = uploadFile.getSize();   //파일 크기 설정( tyep = long으로 설정)
		 	this.fileFancysize = FileUtils.byteCountToDisplaySize(fileSize); //이쁘게 볼수있는 파일 사이즈
		 	this.fileMime = uploadFile.getContentType();  //파일의 MIME타입 설정
		 	this.fileDwncnt = 0; // 파일 다운로드 횟수 초기화
	 }
	 
	 
	 
	 //파일을 실제 경로(폴더)에 때려박는 메소드
	 public void uploadFileSaveTo(File saveFolder) throws IOException{ 
		 if(uploadFile != null ) {   //만약 클라이언트가 올린 파일이 null이 아니면
			 File saveFile = new File(saveFolder,saveFileName); //saveFolder에 savaFileName 로실제 저장 할 객체 생성
			 uploadFile.transferTo(saveFile);
			 this.savePath = saveFile.getCanonicalPath();  //파일 한건에 대한 저장 경로 설정
			 
		 }
		 
		 
	 }
	 
	 
	
	
	private String attachedFileDetailId; // 첨부파일상세ID
	private String attachedFileId; // 첨부파일분류ID
	private String originalFileName; // 원본파일명
	private String saveFileName; // 저장파일명
	private String savePath; // 저장경로
	private String fileExtensions; // 파일확장자
	private long fileSize; // 파일사이즈
	
	private String fileFancysize; // 사람이 읽을 수 있는 파일 크기  	-->DB에 컬럼 넣어야함
	private String fileMime; // 파일 MIME 타입  						-->DB에 컬럼 넣어야함
	private int fileDwncnt; // 파일 다운로드 횟수 						--> DB에 컬럼 넣어야함
	
	private LocalDate fileCreationDate; // 파일 생성일시
	private LocalDate fileDelDate; // 파일 삭제 일시
	private String fileDelyn; // 삭제여부
	
	private Resource savedFile;		

}
