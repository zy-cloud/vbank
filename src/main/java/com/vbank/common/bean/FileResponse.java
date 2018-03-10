package com.vbank.common.bean;

import java.util.List;

/**
 * @author malongbo
 * @date 2015/1/28
 * @package com.pet.project.bean
 */
public class FileResponse extends BaseResponse {
    /*
    * 保存上传成功的文件名
     */
	private List<SaveFile> successFiles;
	/*
    * 保存上传失败的文件名
     */
    private List<SaveFile> failedFiles;
	public List<SaveFile> getSuccessFiles() {
		return successFiles;
	}
	public void setSuccessFiles(List<SaveFile> successFiles) {
		this.successFiles = successFiles;
	}
	public List<SaveFile> getFailedFiles() {
		return failedFiles;
	}
	public void setFailedFiles(List<SaveFile> failedFiles) {
		this.failedFiles = failedFiles;
	}
   
}
