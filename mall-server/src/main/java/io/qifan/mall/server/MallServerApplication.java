package io.qifan.mall.server;

import io.qifan.mall.server.infrastructure.model.WxPayPropertiesExtension;
import org.babyfish.jimmer.client.EnableImplicitApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableImplicitApi
@EnableConfigurationProperties(value = {WxPayPropertiesExtension.class})
public class MallServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallServerApplication.class, args);
  }
}
