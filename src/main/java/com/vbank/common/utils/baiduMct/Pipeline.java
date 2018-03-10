package com.vbank.common.utils.baiduMct;

import java.util.List;

import com.baidubce.services.media.model.GetPipelineResponse;
import com.baidubce.services.media.model.PipelineStatus;

/**
 * ClassName: Pipeline
 * Function: TODO
 * date: 2016-3-15 下午12:52:15
 *
 * @author czhang
 * @version 1.0.0
 */
public class Pipeline {
	
	/**
	 * 新建一个Pipeline，默认的capacity值为20
	 * @param client
	 * @param pipelineName
	 * @param sourceBucket
	 * @param targetBucket
	 * @param capacity
	 */
	public void createPipeline (String pipelineName,
		String sourceBucket, String targetBucket, int capacity) {
	    // 新建一个Pipeline
		BceMediaClient.getClient().createPipeline(pipelineName, sourceBucket, targetBucket, capacity);
	}
	
	/**
	 * 列出用户所有的Pipeline
	 * @param client
	 */
	public List<PipelineStatus> listPipelines () {
	    // 获取用户的Pipeline列表
		List<PipelineStatus> list = BceMediaClient.getClient().listPipelines().getPipelines();

		return list;
	}
	
	/**
	 * 查询指定的Pipeline
	 * @param client
	 * @param pipelineName
	 */
	public GetPipelineResponse getPipeline (String pipelineName) {
	    GetPipelineResponse pipeline = BceMediaClient.getClient().getPipeline(pipelineName);
	    return pipeline;
	}
	
	/**
	 * 删除Pipeline
	 * @param client
	 * @param pipelineName
	 */
	public void deletePipeline (String pipelineName) {
	    // 删除Pipeline
	    BceMediaClient.getClient().deletePipeline(pipelineName);
	}
	
}
