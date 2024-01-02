package com.oner365.monitor.controller.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.commons.enums.ResultEnum;
import com.oner365.data.commons.reponse.ResponseResult;
import com.oner365.data.web.controller.BaseController;

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
