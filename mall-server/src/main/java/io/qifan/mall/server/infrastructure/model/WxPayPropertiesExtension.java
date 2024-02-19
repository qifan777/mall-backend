package io.qifan.mall.server.infrastructure.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "wx.pay"
)
@Data
public class WxPayPropertiesExtension {
  private String notifyUrl;
}