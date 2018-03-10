package com.vbank.common.utils.baiduMct;

import com.baidubce.services.media.model.Audio;
import com.baidubce.services.media.model.Clip;
import com.baidubce.services.media.model.Encryption;
import com.baidubce.services.media.model.GetPresetResponse;
import com.baidubce.services.media.model.ListPresetsResponse;
import com.baidubce.services.media.model.Video;

/**
 * ClassName: Preset
 * Function: 模板是系统预设的对于一个视频资源在做转码计算时所需定义的集合。用户可以更简便的将一个模板应用于一个和多个视频的转码任务，以使这些任务输出相同规格的目标视频资源。

			  音视频转码为用户预设了丰富且完备的系统模板，以满足用户对于目标规格在格式、码率、分辨率、加解密、水印等诸多方向上的普遍需求，对于不希望过多了解音视频复杂技术背景的用户来说，是最佳的选择。百度为那些在音视频技术上有着丰富积累的用户，提供了可定制化的转码模板，以帮助他们满足复杂业务条件下的转码需求。

			  当用户仅需对于音视频的容器格式做变化时，百度提供Transmux模板帮助用户以秒级的延迟快速完成容器格式的转换，比如从MP4转换成HLS，而保持原音视频的属性不变。
 * date: 2016-3-15 下午12:58:34
 *
 * @author czhang
 * @version 1.0.0
 */
public class Preset {
	
	/**
	 * 查询所有系统Preset
	 * @param client
	 * @return
	 */
	public ListPresetsResponse listPresets() {
	    return BceMediaClient.getClient().listPresets();
	}
	
	/**
	 * 查询指定的Preset信息
	 * @param client
	 * @param presetName
	 */
	public GetPresetResponse getPreset(String presetName) {
	    return BceMediaClient.getClient().getPreset(presetName);
	}
	
	/**
	 * 创建仅支持容器格式转换的Preset
	 * @param client
	 * @param presetName
	 * @param description
	 * @param container
	 */
	public void createPreset(String presetName, String description, String container) {
	    BceMediaClient.getClient().createPreset(presetName, description, container);
	}
	
	/**
	 * 创建音频文件的转码Preset，不需要截取片段和加密
	 * @param client
	 * @param presetName
	 * @param description
	 * @param container
	 * @param audio
	 */
	public void createPreset(String presetName, String description, String container, Audio audio) {
		BceMediaClient.getClient().createPreset(presetName, description, container, audio);
	}
	
	/**
	 * 创建音频文件转码Preset，需要设置片段截取属性和加密属性
	 * @param client
	 * @param presetName
	 * @param description
	 * @param container
	 * @param clip
	 * @param audio
	 * @param encryption
	 */
	public void createPreset(String presetName, String description, String container,
            Clip clip, Audio audio, Encryption encryption) {
	    BceMediaClient.getClient().createPreset(presetName, description, container, clip, audio, encryption);
	}
	
	/**
	 * 创建视频文件转码Preset，不需要截取片段、加密和水印属性
	 * @param client
	 * @param presetName
	 * @param description
	 * @param container
	 * @param audio
	 * @param video
	 */
	public void createPreset(String presetName, String description, String container,
            Audio audio, Video video) {
	    BceMediaClient.getClient().createPreset(presetName, description, container, audio, video);
	}
	
	/**
	 * 创建视频文件转码Preset，需要设置片段截取、加密和水印属性
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
	public void createPreset(String presetName, String description, String container,
            Clip clip,Audio audio, Video video, Encryption encryption, String watermarkId) {
	    BceMediaClient.getClient().createPreset(presetName, description, container, clip, audio, video, encryption, watermarkId);
	}
	
	/**
	 * 创建Preset，指定所有的参数
	 * @param client
	 * @param presetName
	 * @param description
	 * @param container
	 * @param transmux
	 * @param clip
	 * @param audio
	 * @param video
	 * @param encryption
	 * @param watermarkId
	 */
	public void createPreset(String presetName, String description, String container,
            boolean transmux, Clip clip,Audio audio, Video video, Encryption encryption, String watermarkId) {
	    BceMediaClient.getClient().createPreset(presetName, description, container, transmux, clip, audio, video, encryption, watermarkId);
	}
	
	
	
	
}
