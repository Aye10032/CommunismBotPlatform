package com.aye10032.communismbotplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.aye10032"})
@MapperScans({
        @MapperScan("com.aye10032.mapper")
})
@EnableTransactionManagement
public class CommunismBotPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunismBotPlatformApplication.class, args);
    }

}
