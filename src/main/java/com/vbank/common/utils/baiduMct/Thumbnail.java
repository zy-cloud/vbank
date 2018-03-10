package com.vbank.common.utils.baiduMct;

import com.baidubce.services.media.MediaClient;
import com.baidubce.services.media.model.GetThumbnailJobResponse;
import com.baidubce.services.media.model.ListThumbnailJobsResponse;
import com.baidubce.services.media.model.ThumbnailCapture;
import com.baidubce.services.media.model.ThumbnailTarget;

/**
 * ClassName: Thumbnail
 * Function: 缩略图是图片、视频经压缩方式处理后的小图。因其小巧，加载速度非常快，故用于快速浏览。缩略图任务可用于为BOS中的多媒体资源创建缩略图。
 * date: 2016-3-15 下午1:15:35
 *
 * @author czhang
 * @version 1.0.0
 */
public class Thumbnail {

	/**
	 * 通过pipeline，BOS Key以及其他配置信息为指定媒体生成缩略图
	 * @param client
	 * @param pipelineName
	 * @param sourceKey
	 */
	public String createThumbnailJob(MediaClient client, String pipelineName, String sourceKey) {
	    ThumbnailTarget target = new ThumbnailTarget().withFormat("jpg").withSizingPolicy("keep");

	    ThumbnailCapture capture =
	            new ThumbnailCapture().withMode("manual").withStartTimeInSecond(0)
	            .withEndTimeInSecond(5).withIntervalInSecond(1);

	    String jobId = client.createThumbnailJob(pipelineName, sourceKey, target, capture).getJobId();
	    
	    return jobId;
	}
	
	/**
	 * 查询指定Thumbnail Job
	 * @param client
	 * @param jobId
	 */
	public GetThumbnailJobResponse getThumbnailJob(MediaClient client, String jobId) {
	    return client.getThumbnailJob(jobId);
	}
	
	/**
	 * 查询指定队列的Thumbnail Jobs
	 * @param client
	 * @param pipelineName
	 */
	public ListThumbnailJobsResponse listThumbnailJobs(MediaClient client, String pipelineName) {
		return client.listThumbnailJobs(pipelineName);
	}
}
