package com.oner365.websocket.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.reponse.ResponseData;
import com.oner365.data.web.controller.BaseController;

/**
 * 测试ws
 * @author liutao
 */
@RefreshScope
@RestController
@RequestMapping("/websocket2")
public class WebsocketController extends BaseController {


	/**
	 * 测试接收ws数据
	 * @param token token
	 * @return ResponseData
	 */
	@GetMapping
	public ResponseData<String> test(String token) {
		logger.info("token:{}", token);
		return ResponseData.success(token);
	}
}
