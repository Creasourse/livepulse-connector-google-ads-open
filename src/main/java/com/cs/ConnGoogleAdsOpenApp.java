package com.cs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Google Ads 连接器应用主类
 * 推送客户匹配数据到 Google Ads 平台，支持 SHA-256 加密邮箱、手机号、地址及移动设备 ID
 *
 * @author LivePulse
 * @version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("com.cs.mapper")
public class ConnGoogleAdsOpenApp {

    public static void main(String[] args) {
        SpringApplication.run(ConnGoogleAdsOpenApp.class, args);
    }
}
