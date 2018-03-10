package com.vbank.common.utils.baiduMct;

import java.net.URL;
import java.util.List;

import com.baidubce.services.bos.model.CompleteMultipartUploadRequest;
import com.baidubce.services.bos.model.CompleteMultipartUploadResponse;
import com.baidubce.services.bos.model.InitiateMultipartUploadRequest;
import com.baidubce.services.bos.model.InitiateMultipartUploadResponse;
import com.baidubce.services.bos.model.ListPartsRequest;
import com.baidubce.services.bos.model.ListPartsResponse;
import com.baidubce.services.bos.model.ObjectMetadata;
import com.baidubce.services.bos.model.PartETag;
import com.baidubce.services.bos.model.PartSummary;

/**
 * ClassName: BceObjectUtils
 * Function: 
 * date: 2016-3-30 下午6:50:44
 *
 * @author czhang
 * @version 1.0.0
 */
public class BceObjectUtil extends BceBosClientUtil {

	public static List<PartSummary> objList(String bucketName, String objectKey, String uploadId){
		ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, objectKey, uploadId);

		// 获取上传的所有Part信息
		ListPartsResponse partListing = getBosClient().listParts(listPartsRequest);

		// 遍历所有Part
		for (PartSummary part : partListing.getParts()) {
		    System.out.println("PartNumber: " + part.getPartNumber() + " ETag: " + part.getETag());
		}
		
		return partListing.getParts();
	}
	
	public static String getUploadId(String bucketName, String objectKey){
		// 开始Multipart Upload
		InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, objectKey);
		InitiateMultipartUploadResponse initiateMultipartUploadResponse = getBosClient().initiateMultipartUpload(initiateMultipartUploadRequest);

		return initiateMultipartUploadResponse.getUploadId();
	}
	
	/*public static void upload(String bucketName, CommonsMultipartFile partFile) throws Exception{
		// 设置每块为 5MB
		String fileName = partFile.getOriginalFilename();
		
		String uploadId = getUploadId(bucketName, fileName);
		
		final long partSize = 1024 * 1024 * 5L;

		long fileSize = partFile.getSize();
		
		// 计算分块数目
		int partCount = (int) (fileSize / partSize);
		if (fileSize % partSize != 0){
		    partCount++;
		}

		// 新建一个List保存每个分块上传后的ETag和PartNumber
		List<PartETag> partETags = new ArrayList<PartETag>();

		for(int i = 0; i < partCount; i++){
		    // 获取文件流
		    FileInputStream fis = (FileInputStream) partFile.getInputStream();

		    // 跳到每个分块的开头
		    long skipBytes = partSize * i;
		    fis.skip(skipBytes);

		    // 计算每个分块的大小
		    long size = partSize < fileSize - skipBytes ?
		            partSize : fileSize - skipBytes;

		    // 创建UploadPartRequest，上传分块
		    UploadPartRequest uploadPartRequest = new UploadPartRequest();
		    uploadPartRequest.setBucketName(bucketName);
		    uploadPartRequest.setKey(fileName);
		    uploadPartRequest.setUploadId(uploadId);
		    uploadPartRequest.setInputStream(fis);
		    uploadPartRequest.setPartSize(size);
		    uploadPartRequest.setPartNumber(i + 1);
		    UploadPartResponse uploadPartResponse = getBosClient().uploadPart(uploadPartRequest);

		    // 将返回的PartETag保存到List中。
		    partETags.add(uploadPartResponse.getPartETag());

		    // 关闭文件
		    fis.close();
		}
		
		finishTags(bucketName, fileName, partETags, uploadId);
	}*/
	
	public static void finishTags(String bucketName, String objectKey, List<PartETag> partETags, String uploadId){
		CompleteMultipartUploadRequest completeMultipartUploadRequest =
		        new CompleteMultipartUploadRequest(bucketName, objectKey, uploadId, partETags);

		// 完成分块上传
		CompleteMultipartUploadResponse completeMultipartUploadResponse = 
				getBosClient().completeMultipartUpload(completeMultipartUploadRequest);

		// 打印Object的ETag
		System.out.println(completeMultipartUploadResponse.getETag());
	}
	
	
	public static void deleteObject(String bucketName, String objectKey) {
	    // 删除Object
		getBosClient().deleteObject(bucketName, objectKey);           //指定要删除的Object所在Bucket名称和该Object名称
	}
	
	public static ObjectMetadata getObjectMetadata(String bucketName, String objectKey){
		ObjectMetadata objectMetadata = getBosClient().getObjectMetadata(bucketName, objectKey);
		return objectMetadata;
	}
	
	/**
	 * 	用户在调用该函数前，需要手动设置endpoint为所属区域域名。百度开放云目前开放了多区域 支持，请参考区域选择说明。
		目前支持“华北-北京”和“华南-广州”两个区域。
		北京区域：http://bj.bcebos.com
		广州区域：http://gz.bcebos.com
		expirationInSeconds为指定的URL有效时长，时间从当前时间算起，为可选参数，不配置时系统默认值为1800秒。
		如果要设置为永久不失效的时间，可以将expirationInSeconds参数设置为 -1，不可设置为其他负数。
	 * @param bucketName
	 * @param objectKey
	 * @param expirationInSeconds
	 * @return
	 */
	public static String generatePresignedUrl(String bucketName, String objectKey, int expirationInSeconds) {
		URL url = getEndpointBosClient().generatePresignedUrl(bucketName, objectKey, expirationInSeconds);          
		return url.toString();
	}
	
}
