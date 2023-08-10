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

    @Bean("tigangSub")
    public SimpleSubscription tigang(CommunismBot bot) {
        return new SimpleSubscription(
                "0 0 19 * * ? ",
                bot.getImg(bot.appDirectory + "/tigang.jpg")) {
            @Override
            public String getName() {
                return "提肛小助手";
            }
        };
    }

}
