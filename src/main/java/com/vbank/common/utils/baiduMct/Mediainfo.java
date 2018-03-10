package com.vbank.common.utils.baiduMct;

import com.baidubce.services.media.MediaClient;
import com.baidubce.services.media.model.GetMediaInfoOfFileResponse;

/**
 * ClassName: Mediainfo
 * Function: 对于BOS中某个Object，可以通过下面代码获取媒体信息
 * date: 2016-3-15 下午1:12:39
 *
 * @author czhang
 * @version 1.0.0
 */
public class Mediainfo {

	/**
	 * Mediainfo(媒体信息)
	 * @param client
	 * @param bucket
	 * @param key
	 */
	public GetMediaInfoOfFileResponse getMediaInfoOfFile(MediaClient client, String bucket, String key) {
	    return client.getMediaInfoOfFile(bucket, key);
	}
	
	
	public static GetMediaInfoOfFileResponse getMediaInfoOfFile(String bucket, String key) {
		MediaClient client = BceMediaClient.getClient();
	    GetMediaInfoOfFileResponse mediaInfo = client.getMediaInfoOfFile(bucket, key);
//	    System.out.println("fileSizeInByte: " + mediaInfo.getFileSizeInByte());
//	    System.out.println("etag: " + mediaInfo.getEtag());
//	    System.out.println("type: " + mediaInfo.getType());
//	    System.out.println("type: " + mediaInfo.getDurationInSecond());
	    return mediaInfo;
	}
	
}
