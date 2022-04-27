package com.dazo66.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.io.IOException;

/**
 * @author Dazo66
 */
@Configuration
public class BotConfig {

    @Value("${qqId}")
    private Long qqId;
    @Value("${qqPassword}")
    private String password;

    @Bean
    public Bot getBot() throws IOException {
        if (qqId == null || password == null) {
            throw new RuntimeException("请在参数中放入qq账号密码");
        }
        BotConfiguration configuration = BotConfiguration.getDefault();
        configuration.copy();
        //conf.enableContactCache()
        FileReader reader = new FileReader("device.json");
        configuration.loadDeviceInfoJson(IOUtils.toString(reader));
        reader.close();
        Bot bot = BotFactory.INSTANCE.newBot(qqId, password, configuration);
        bot.login();

        // bot.join();
        return bot;
    }
}