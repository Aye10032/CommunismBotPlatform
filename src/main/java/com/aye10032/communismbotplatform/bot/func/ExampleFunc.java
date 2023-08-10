package com.aye10032.communismbotplatform.bot.func;

import com.aye10032.communismbotplatform.bot.CommunismBot;
import com.aye10032.communismbotplatform.bot.func.funcutil.BaseFunc;
import com.aye10032.communismbotplatform.bot.func.funcutil.FuncExceptionHandler;
import com.aye10032.communismbotplatform.bot.func.funcutil.SimpleMsg;
import com.aye10032.communismbotplatform.foundation.utils.command.Commander;
import com.aye10032.communismbotplatform.foundation.utils.command.CommanderBuilder;

/**
 * @program: CommunismBotPlatform
 * @description: 功能示例
 * @author: Aye10032
 * @create: 2023-08-10 09:20
 **/

public class ExampleFunc extends BaseFunc {

    private Commander<SimpleMsg> commander;

    public ExampleFunc(CommunismBot bot) {
        super(bot);
        commander = new CommanderBuilder<SimpleMsg>()
                .seteHandler(FuncExceptionHandler.INSTENCE)
                .start()
                .or("测试"::equals)
                .run((msg) -> {
                    bot.replyMsgWithQuote(msg, "buzai");
                })
                .build();
    }

    @Override
    public void setUp() {

    }

    @Override
    public void run(SimpleMsg simpleMsg) {
        commander.execute(simpleMsg);
    }
}
