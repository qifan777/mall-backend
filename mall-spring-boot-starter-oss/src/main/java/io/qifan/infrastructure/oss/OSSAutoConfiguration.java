package io.qifan.infrastructure.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSProperties;
import io.qifan.infrastructure.oss.aliyun.AliYunOSSService;
import io.qifan.infrastructure.oss.controller.OSSController;
import io.qifan.infrastructure.oss.service.OSSService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
@EnableConfigurationProperties(OSSProperties.class)
public class OSSAutoConfiguration {
    @Bean
    @ConditionalOnBean(OSSService.class)
    public OSSController ossController(OSSService ossService) {
        return new OSSController(ossService);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "oss", name = "provider", havingValue = "ALI_YUN")
    @EnableConfigurationProperties(AliYunOSSProperties.class)
    public static class AliYunConfig {
        @Bean
        public OSS aliYunOSS(AliYunOSSProperties aliYunOSSProperties) {
            return new OSSClientBuilder().build(aliYunOSSProperties.getEndpoint(),
                    aliYunOSSProperties.getAccessKeyId(),
                    aliYunOSSProperties.getAccessKeySecret());
        }

        @Bean
        @ConditionalOnMissingBean(OSSService.class)
        public OSSService aliYunOSSService(OSS aliyunOss, AliYunOSSProperties aliYunOSSProperties) {
            return new AliYunOSSService(aliYunOSSProperties, aliyunOss);
        }
    }
}
