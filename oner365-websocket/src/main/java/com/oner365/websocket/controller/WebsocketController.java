package com.oner365.websocket.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseData;

/**
 * 测试ws
 * @author liutao
 */
@RefreshScope
@RestController
@RequestMapping("/websocket2")
public class WebsocketController {


	/**
	 * 测试接收ws数据
	 * @param token token
	 * @return ResponseData
	 */
	@GetMapping
	public ResponseData<String> test(String token) {
		System.err.println(token);
		return ResponseData.success(null);
	}
}
