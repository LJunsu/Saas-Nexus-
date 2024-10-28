package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class S3ObjectInfo implements Serializable {
	private String name;
    private String type; // "file" 또는 "folder"
    private LocalDate lastModified;
    private long size;
    private String fileType; // 파일 유형 (예: "png", "jpg")
    
    public S3ObjectInfo(String name, String type, LocalDate lastModified, long size, String fileType) {
        this.name = name;
        this.type = type;
        this.lastModified = lastModified;
        this.size = size;
        this.fileType = fileType;
    }
    
    
    
    public S3ObjectInfo() {
		super();
	}



	private static final long serialVersionUID = 1L;
}
