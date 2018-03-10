package com.vbank.common.utils.baiduMct;

import java.util.List;

import com.baidubce.services.media.MediaClient;
import com.baidubce.services.media.model.Audio;
import com.baidubce.services.media.model.Clip;
import com.baidubce.services.media.model.CreateWaterMarkResponse;
import com.baidubce.services.media.model.Encryption;
import com.baidubce.services.media.model.Video;
import com.baidubce.services.media.model.WaterMark;

/**
 * ClassName: Watermark
 * Function: 数字水印是向数据多媒体（如图像、音频、视频信号等）中添加某些数字信息以达到文件真伪鉴别、版权保护等功能。
 * 			
 * 			  嵌入的水印信息隐藏于宿主文件中，不影响原始文件的可观性和完整性。

			  用户可以将BOS中的一个Object创建为水印，获得对应的watermarkId。然后在转码任务中将此水印添加到目的多媒体文件。
 * date: 2016-3-15 下午1:17:33
 *
 * @author czhang
 * @version 1.0.0
 */
public class Watermark {
	
	/**
	 * 查询当前用户水印
	 * @param client
	 */
	public  List<WaterMark> getWaterMark(MediaClient client) {
	   return client.listWaterMark().getWatermarks();
	}
	
	/**
	 * 删除水印
	 * @param client
	 * @param watermarkId
	 */
	public void getWaterMark(MediaClient client, String watermarkId) {
	    client.deleteWaterMark(watermarkId);
	}
	
	/**
	 * 创建水印
	 * @param client
	 * @param bucket
	 * @param key
	 */
	public CreateWaterMarkResponse createWaterMark(MediaClient client, String bucket, String key) {
	    return client.createWaterMark(bucket, key, "left", "top");
	}
	
	/**
	 * 创建水印的转码任务
	 * @param client
	 * @param presetName
	 * @param description
	 * @param container
	 * @param clip
	 * @param audio
	 * @param video
	 * @param encryption
	 * @param watermarkId
	 */
	public void createPreset(MediaClient client, String presetName, String description, String container,
            Clip clip,Audio audio, Video video, Encryption encryption, String watermarkId) {

	    client.createPreset(presetName, description, container, clip, audio, video, encryption, watermarkId);
	
	    //String jobId = client.createJob(pipelineName, sourceKey, targetKey, presetName).getJobId();
	}
	
}
