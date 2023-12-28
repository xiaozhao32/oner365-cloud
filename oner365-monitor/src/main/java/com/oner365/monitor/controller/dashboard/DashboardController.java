package com.oner365.monitor.controller.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ResultEnum;
import com.oner365.controller.BaseController;

/**
 * 首页信息
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    /**
     * 首页信息
     * @return ResponseResult<String>
     */
    @GetMapping("/index")
    public ResponseResult<String> index() {
        return ResponseResult.success(ResultEnum.SUCCESS.getName());
    }
}
