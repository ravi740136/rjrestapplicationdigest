package rj.training.rest.model;

public class UploadResponse {
	public String getMultiFileName() {
		return multiFileName;
	}
	public void setMultiFileName(String multiFileName) {
		this.multiFileName = multiFileName;
	}
	private String multiFileName;
private String originalFileName;
public String getOriginalFileName() {
	return originalFileName;
}
public void setOriginalFileName(String originalFileName) {
	this.originalFileName = originalFileName;
}
public String getFileType() {
	return fileType;
}
public void setFileType(String fileType) {
	this.fileType = fileType;
}
public Long getBytes() {
	return bytes;
}
public void setBytes(Long bytes) {
	this.bytes = bytes;
}
private String fileType;
private Long bytes;
}
