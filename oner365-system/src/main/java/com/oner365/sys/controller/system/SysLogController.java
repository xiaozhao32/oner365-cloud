package com.oner365.sys.controller.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.enums.ErrorInfoEnum;
import com.oner365.common.enums.ResultEnum;
import com.oner365.common.page.PageInfo;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysLogDto;
import com.oner365.sys.service.ISysLogService;
import com.oner365.sys.vo.SysLogVo;
import com.oner365.util.DateUtil;

/**
 * 系统日志控制器
 *
 * @author zhaoyong
 */
@RestController
@RequestMapping("/log")
public class SysLogController extends BaseController {

	@Autowired
	private ISysLogService logService;

	/**
	 * 列表
	 *
	 * @param data 查询参数
	 * @return PageInfo<SysLog>
	 */
	@PostMapping("/list")
	public PageInfo<SysLogDto> list(@RequestBody QueryCriteriaBean data) {
		return logService.pageList(data);
	}

	/**
	 * 获取信息
	 *
	 * @param id 编号
	 * @return SysLog
	 */
	@GetMapping("/get/{id}")
	public SysLogDto get(@PathVariable String id) {
		return logService.getById(id);
	}

	/**
	 * 保存
	 *
	 * @param sysLogVo 菜单类型对象
	 * @return ResponseResult<String>
	 */
	@PutMapping("/save")
	public ResponseResult<String> save(@RequestBody SysLogVo sysLogVo) {
    if (sysLogVo != null) {
      logService.save(sysLogVo);
      return ResponseResult.success(ResultEnum.SUCCESS.getName());
    }
    return ResponseResult.error(ErrorInfoEnum.SAVE_ERROR.getName());
  }

	/**
	 * 删除
	 *
	 * @param ids 编号
	 * @return Integer
	 */
	@DeleteMapping("/delete")
	public Integer delete(@RequestBody String... ids) {
		int code = 0;
		for (String id : ids) {
			code = logService.deleteById(id);
		}
		return code;
	}

	/**
	 * 按日期删除日志
	 *
	 * @param days 天数
	 * @return Integer
	 */
	@DeleteMapping("/days/delete")
	public Integer deleteLog(@RequestParam("days") Integer days) {
		Date date = DateUtil.getDateAgo(days);
		return logService.deleteLog(DateUtil.dateToLocalDateTime(date));
	}

	/**
	 * 导出日志
	 *
	 * @param data 查询参数
	 * @return ResponseEntity<byte[]>
	 */
	@PostMapping("/export")
	public ResponseEntity<byte[]> exportItem(@RequestBody QueryCriteriaBean data) {
		List<SysLogDto> list = logService.findList(data);

		String[] titleKeys = new String[] { "编号", "请求IP", "请求方法", "服务名称", "请求地址", "请求内容", "创建时间" };
		String[] columnNames = { "id", "operationIp", "methodName", "operationName", "operationPath",
				"operationContext", "createTime" };

		String fileName = SysLogDto.class.getSimpleName() + System.currentTimeMillis();
		return exportExcel(fileName, titleKeys, columnNames, list);
	}

}
