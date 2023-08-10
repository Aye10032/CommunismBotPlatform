package com.aye10032.communismbotplatform.controller;

import com.aye10032.communismbotplatform.bot.CommunismBot;
import com.aye10032.communismbotplatform.foundation.entity.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: CommunismBotPlatform
 * @description:
 * @author: Aye10032
 * @create: 2023-08-10 08:39
 **/

@RestController
@Slf4j
public class MessageController {

    @Autowired
    private CommunismBot bot;

    @RequestMapping(value = "send", method = {RequestMethod.POST, RequestMethod.GET})
    public Result<?> sendMessage(Long group, String msg) {
        if (group != null) {
            bot.toGroupMsg(group, msg);
            log.info("向群" + group + "发送了消息：" + msg);
            return Result.success("success");
        } else {
            return new Result<>("400", "群号不可为空", "");
        }
    }

}
