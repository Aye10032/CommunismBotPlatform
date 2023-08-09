package com.aye10032.communismbotplatform.config;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

/**
 * @program: CommunismBotPlatform
 * @description: bot configuration
 * @author: Aye10032
 * @create: 2023-08-09 15:20
 **/

@Configuration
public class BotConfig {

    @Value("${bot.qqId}")
    private Long qqId;
    @Value("${bot.qqPassword}")
    private String password;
    @Value("${spring.profiles.active}")
    private String profiles;

    @Bean
    @Profile("!mock")
    public Bot getBot() throws IOException {
        if (qqId == null || password == null) {
            throw new RuntimeException("请在参数中放入qq账号密码");
        }
        BotConfiguration configuration = BotConfiguration.getDefault();
        configuration.copy();

        configuration.fileBasedDeviceInfo("device.json");
        configuration.setHeartbeatStrategy(BotConfiguration.HeartbeatStrategy.STAT_HB);
        configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);

        Bot bot = BotFactory.INSTANCE.newBot(qqId, BotAuthorization.byQRCode(), configuration);

        if (!profiles.contains("test")) {
            bot.login();
        }

        return bot;
    }

}
