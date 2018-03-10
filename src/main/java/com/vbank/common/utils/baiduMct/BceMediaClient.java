package com.vbank.common.utils.baiduMct;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.media.MediaClient;
import com.jfinal.kit.PropKit;

/**
 * ClassName: Mediainfo
 * Function: MediaClient是Media服务的Java客户端，为调用者与Media服务进行交互提供了一系列的方法。
 * date: 2016-3-15 下午1:12:39
 *
 * @author czhang
 * @version 1.0.0
 */
public class BceMediaClient {
	
	private static final String ACCESS_KEY_ID = PropKit.get("bce_ak");
	private static final String SECRET_ACCESS_KEY = PropKit.get("bce_sk");
	private static final String ENDPOINT = PropKit.get("end_point");
	
	/**
	 * 获取服务端client
	 * 通过BceClientConfiguration能指定的所有参数如下表所示：

		参数	说明
		UserAgent	用户代理，指HTTP的User-Agent头
		Protocol	连接协议类型
		ProxyDomain	访问NTLM验证的代理服务器的Windows域名
		ProxyHost	代理服务器主机地址
		ProxyPort	代理服务器端口
		ProxyUsername	代理服务器验证的用户名
		ProxyPassword	代理服务器验证的密码
		ProxyPreemptiveAuthenticationEnabled	是否设置用户代理认证
		ProxyWorkstation	NTLM代理服务器的Windows工作站名称
		LocalAddress	本地地址
		ConnectionTimeoutInMillis	建立连接的超时时间（单位：毫秒）
		SocketTimeoutInMillis	通过打开的连接传输数据的超时时间（单位：毫秒）
		MaxConnections	允许打开的最大HTTP连接数
		RetryPolicy	连接重试策略
		SocketBufferSizeInBytes	Socket缓冲区大小
	 * @return
	 */
	public static MediaClient getClient() {
	    // 初始化一个MediaClient
	    BceClientConfiguration config = new BceClientConfiguration();
	    config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
	    return new MediaClient(config);
	}
	
	/**
	 * 如果用户需要自己制定域名，可以通过传入ENDPOINT参数来指定。
	 */
	public MediaClient getPointClient(String endpoint) {
		BceClientConfiguration config = new BceClientConfiguration();
		config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID,SECRET_ACCESS_KEY));
		config.setEndpoint(ENDPOINT);
		return new MediaClient(config);
	}
	
	/**
	 * 客户端使用代理访问Media服务
	 * @return
	 */
	public MediaClient getProxyClient(String host, int port){
		// 创建BceClientConfiguration实例
		BceClientConfiguration config = new BceClientConfiguration();

		// 配置代理为本地8080端口
		config.setProxyHost(host);
		config.setProxyPort(port);

		// 配置认证秘钥和服务器信息
		config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID,SECRET_ACCESS_KEY));
		config.setEndpoint(ENDPOINT);

		// 创建Media客户端
		return new MediaClient(config);
	}
	
}
