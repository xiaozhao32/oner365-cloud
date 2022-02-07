package com.oner365.sys.controller.system;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oner365.common.ResponseResult;
import com.oner365.common.query.AttributeBean;
import com.oner365.common.query.QueryCriteriaBean;
import com.oner365.controller.BaseController;
import com.oner365.sys.dto.SysMessageDto;
import com.oner365.sys.enums.MessageStatusEnum;
import com.oner365.sys.enums.MessageTypeEnum;
import com.oner365.sys.service.ISysMessageService;

/**
 * 消息通知
 * 
 * @author zhaoyong
 */
@RestController
@RequestMapping("/message")
public class SysMessageController extends BaseController {

  @Autowired
  private ISysMessageService sysMessageService;

  /**
   * 查询结果 有返回 true 并且删除
   * 
   * @param messageType 消息类型
   * @return ResponseResult<Boolean>
   */
  @GetMapping("/refresh")
  public ResponseResult<Boolean> refresh(@RequestParam("messageType") MessageTypeEnum messageType) {
    QueryCriteriaBean data = new QueryCriteriaBean();
    data.setWhereList(Collections.singletonList(new AttributeBean("messageType", messageType)));
    List<SysMessageDto> list = sysMessageService.findList(data);
    if (!list.isEmpty()) {
      list.forEach(entity -> sysMessageService.editStatus(entity.getId(), MessageStatusEnum.READ));
      return ResponseResult.success(Boolean.TRUE);
    }
    return ResponseResult.success(Boolean.FALSE);
  }
}
