package kz.kazgisa.data.dto;

import kz.kazgisa.data.enums.StorageType;

import java.io.Serializable;
import java.util.Date;

public class FileDto implements Serializable {

	public static final long serialVersionUID = 1L;
	public String uid;
	public String fileName;
	public String contentType;
	public Long fileSize;
	public String fileDesc;
	public String fileHash;
	public String fileUser;
	public String containerClass;
	public Long containerId;
    public String documentCategory;
	public StorageType storageType;
    public Date uploadDateTime;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileDesc() {
		return fileDesc;
	}

	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}

	public String getFileHash() {
		return fileHash;
	}

	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}

	public String getFileUser() {
		return fileUser;
	}

	public void setFileUser(String fileUser) {
		this.fileUser = fileUser;
	}

	public String getContainerClass() {
		return containerClass;
	}

	public void setContainerClass(String containerClass) {
		this.containerClass = containerClass;
	}

	public Long getContainerId() {
		return containerId;
	}

	public void setContainerId(Long containerId) {
		this.containerId = containerId;
	}

	public String getDocumentCategory() {
		return documentCategory;
	}

	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}

	public StorageType getStorageType() {
		return storageType;
	}

	public void setStorageType(StorageType storageType) {
		this.storageType = storageType;
	}

	public Date getUploadDateTime() {
		return uploadDateTime;
	}

	public void setUploadDateTime(Date uploadDateTime) {
		this.uploadDateTime = uploadDateTime;
	}

	@Override
    public String toString() {
        return "FileDto{" +
            "uid='" + uid + '\'' +
            ", fileName='" + fileName + '\'' +
            ", contentType='" + contentType + '\'' +
            ", fileSize=" + fileSize +
            ", fileDesc='" + fileDesc + '\'' +
            ", fileHash='" + fileHash + '\'' +
            ", fileUser='" + fileUser + '\'' +
            ", containerClass='" + containerClass + '\'' +
            ", containerId=" + containerId +
            ", documentCategory='" + documentCategory + '\'' +
            ", storageType=" + storageType +
            ", uploadDateTime=" + uploadDateTime +
            '}';
    }
}
