package io.qifan.mall.server;

import org.babyfish.jimmer.client.EnableImplicitApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableImplicitApi
public class MallServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MallServerApplication.class, args);
  }
}
