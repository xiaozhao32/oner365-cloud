package com.oner365.spark.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.data.web.controller.BaseController;
import com.oner365.spark.service.SparkService;

/**
 * Spark Controller
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/test")
public class SparkTestController extends BaseController {

    @Resource
    private SparkService sparkService;

    @GetMapping("/home")
    public String getSparkHome() {
        return sparkService.getSparkHome();
    }

    @GetMapping("/reduce")
    public int reduce() {
        return sparkService.reduce();
    }

}
