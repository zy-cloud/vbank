package com.vbank.common.bean;

import java.io.Serializable;

import com.jfinal.kit.PropKit;

/**文件上传返回信息
 * @author dyzeng
 *
 */
public class SaveFile implements Serializable {

	private static final long serialVersionUID = 1L;
	String name = ""; //文件的原始名字
	String prefix = PropKit.get("domain"); //服务器的前缀
	String filePath = ""; //上传成功后返回的相对路径
	Boolean uploadStatus = false;//本次上传状态

	public String getFilePath() {
		return filePath;
	}

	public SaveFile(String name) {
		super();
		this.name = name;
	}

	public SaveFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public SaveFile(String name, String filePath,Boolean uploadStatus) {
		super();
		this.name = name;
		this.filePath = filePath; 
		this.uploadStatus = uploadStatus; 
	}

	public Boolean getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	@Override
	public String toString() {
		return "SaveFile [name=" + name + ", prefix=" + prefix + ", filePath=" + filePath + ", uploadStatus="
				+ uploadStatus + "]";
	}

	

}
