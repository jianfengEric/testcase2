package com.gea.portal.agent.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tng.portal.common.util.HttpClientUtils;
import com.tng.portal.common.util.PropertiesUtil;

/**
 * Time cleaningtoken
 */
public class DeleteTokenGeaJob implements Job{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/*@Autowired
	private DeleteTokenService deleteTokenService;*/
	
	@Autowired
	private HttpClientUtils httpClientUtils;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			//删除子模块的token
            httpClientUtils.httpGet(PropertiesUtil.getAppValueByKey("ewp.server.url").concat("/remote_delete_token/v1/delete-ana-token"), String.class, null);
            httpClientUtils.httpGet(PropertiesUtil.getAppValueByKey("mp.server.url").concat("/remote_delete_token/v1/delete-ana-token"), String.class, null);
            httpClientUtils.httpGet(PropertiesUtil.getAppValueByKey("apv.server.url").concat("/remote_delete_token/v1/delete-ana-token"), String.class, null);
            httpClientUtils.httpGet(PropertiesUtil.getAppValueByKey("tre.server.url").concat("/remote_delete_token/v1/delete-ana-token"), String.class, null);
            httpClientUtils.httpGet(PropertiesUtil.getAppValueByKey("srv.server.url").concat("/remote_delete_token/v1/delete-ana-token"), String.class, null);
            httpClientUtils.httpGet(PropertiesUtil.getAppValueByKey("ord.server.url").concat("/remote_delete_token/v1/delete-ana-token"), String.class, null);
			/*deleteTokenService.deleteApvToken();
			deleteTokenService.deleteEwpToken();
			deleteTokenService.deleteMpToken();
			deleteTokenService.deleteTreToken();
			deleteTokenService.deleteAnaToken();*/
		} catch (Exception e) {
			logger.error("error",e);
		}
	}

}
