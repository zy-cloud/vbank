package com.vbank.common.utils.baiduMct;

import java.util.List;

import com.baidubce.services.media.model.GetJobResponse;
import com.baidubce.services.media.model.Job;

/**
 * ClassName: Transcoding
 * Function: Transcoding Job(任务)是音视频转码中最基本的执行单元，每个任务将一个原始的音视频资源转码成目标规格的音视频资源。
 * 			  因此，任务和转码的目标是一一对应的，也就是说如果用户需要将一个原始多媒体文件转换成三种目标规格，
 * 			  比如从AVI格式转码成FLV/MP4/HLS格式，那么用户将会需要创建三个任务。
 * date: 2016-3-15 下午12:57:53
 *
 * @author czhang
 * @version 1.0.0
 */
@SuppressWarnings("deprecation")
public class Transcoding {
	
	/**
	 * 创建Transcoding Job
	 * @param client
	 * @param pipelineName
	 * @param sourceKey
	 * @param targetKey
	 * @param presetName
	 */
	public String createJob(String pipelineName,
	          String sourceKey, String targetKey, String presetName) {
		String jobId = BceMediaClient.getClient().createJob(pipelineName, sourceKey, targetKey, presetName).getJobId();
	    return jobId;
	}
	
	/**
	 * 列出指定Pipeline的所有Transcoding Job
	 * @param client
	 * @param pipelineName
	 */
	public List<Job> listJobs(String pipelineName) {
	    // 获取指定Pipeline下的所有Job信息
	    return BceMediaClient.getClient().listJobs(pipelineName).getJobs();
	}
	
	/**
	 * 查询指定的Transcoding Job信息
	 * @param client
	 * @param jobId
	 */
	public GetJobResponse getJob(String jobId) {
	   return BceMediaClient.getClient().getJob(jobId);
	}
	
}
