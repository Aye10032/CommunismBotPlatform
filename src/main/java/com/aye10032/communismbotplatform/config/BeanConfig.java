package com.aye10032.communismbotplatform.config;

import com.aye10032.communismbotplatform.bot.CommunismBot;
import com.aye10032.communismbotplatform.bot.timetask.SimpleSubscription;
import com.aye10032.communismbotplatform.foundation.utils.timeutil.AsynchronousTaskPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public AsynchronousTaskPool asynchronousTaskPool() {
        return new AsynchronousTaskPool();
    }

    @Bean("simpleSub")
    public SimpleSubscription tigang(CommunismBot bot) {
        return new SimpleSubscription(
                "0 0 19 * * ? ",
                bot.getImg(bot.appDirectory + "/image/test.jpg")) {
            @Override
            public String getName() {
                return "简易订阅器";
            }
        };
    }

}
