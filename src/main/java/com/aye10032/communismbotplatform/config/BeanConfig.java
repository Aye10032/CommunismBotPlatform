package com.aye10032.communismbotplatform.config;

import com.aye10032.communismbotplatform.foundation.utils.timeutil.AsynchronousTaskPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AsynchronousTaskPool asynchronousTaskPool() {
        return new AsynchronousTaskPool();
    }

}
