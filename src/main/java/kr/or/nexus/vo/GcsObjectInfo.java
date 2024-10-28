package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GcsObjectInfo implements Serializable {
	private String name;
    private String type; // "file" 또는 "folder"
    private LocalDate lastModified;
    private long size;
    private String fileType; // 파일 유형 (예: "png", "jpg")
    private String folderPath;
    private String fileName;
    private String issueName;
    private String username;
    private String formatSize;
    
    public GcsObjectInfo(String name, String type, LocalDate lastModified, long size, String fileType) {
        this.name = name;
        this.type = type;
        this.lastModified = lastModified;
        this.size = size;
        this.fileType = fileType;
    }
    
    public GcsObjectInfo(String name, String type, LocalDate lastModified, long size, String fileType, String username) {
    	this.name = name;
        this.type = type;
        this.lastModified = lastModified;
        this.size = size;
        this.fileType = fileType;
        this.username = username;
    }
    
    public GcsObjectInfo(String name, String type, LocalDate lastModified, long size, String fileType, String username, String formatSize) {
    	this.name = name;
    	this.type = type;
    	this.lastModified = lastModified;
    	this.size = size;
    	this.fileType = fileType;
    	this.username = username;
    	this.formatSize = formatSize;
    }
    
    public GcsObjectInfo(String folderPath, String fileName, String type, LocalDate lastModified, long size, String fileType, String issueName) {
        this.folderPath = folderPath;
        this.fileName = fileName;
        this.type = type;
        this.lastModified = lastModified;
        this.size = size;
        this.fileType = fileType;
        this.issueName = issueName;
    }
    
    public GcsObjectInfo(String folderPath, String fileName, String type, LocalDate lastModified, long size, String fileType, String issueName, String formatSize) {
    	this.folderPath = folderPath;
    	this.fileName = fileName;
    	this.type = type;
    	this.lastModified = lastModified;
    	this.size = size;
    	this.fileType = fileType;
    	this.issueName = issueName;
    	this.formatSize = formatSize;
    }
    
    public GcsObjectInfo() {
		super();
	}



	private static final long serialVersionUID = 1L;
}
