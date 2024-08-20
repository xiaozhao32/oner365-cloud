package com.oner365.sys.audit.config;

import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.oner365.data.web.utils.RequestUtils;

/**
 * 审计功能 - 操作人
 * 
 * @author zhaoyong
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @NotNull
  @Override
  public Optional<String> getCurrentAuditor() {
    if (RequestUtils.getAuthUser() == null) {
      return Optional.empty();
    }
    String userName = RequestUtils.getAuthUser().getUserName();
    return Optional.of(userName);
  }

}
