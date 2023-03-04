package com.aye10032.functions;

import com.aye10032.CommunismBot;
import com.aye10032.functions.funcutil.BaseFunc;
import com.aye10032.functions.funcutil.FuncExceptionHandler;
import com.aye10032.functions.funcutil.SimpleMsg;
import com.dazo66.command.Commander;
import com.dazo66.command.CommanderBuilder;
import org.springframework.stereotype.Service;

@Service
public class MessageInformFunc extends BaseFunc {

    private Commander<SimpleMsg> commander;

    public MessageInformFunc(CommunismBot communismBot) {
        super(communismBot);
        commander = new CommanderBuilder<SimpleMsg>()
                .seteHandler(FuncExceptionHandler.INSTENCE)
                .start()
                .or(this::containsMe)
                .run((msg) -> {
                    if (msg.getFromClient() != 2375985957L && msg.getFromClient() != 2155231604L) {
                        String text = "来自群【" + communismBot.getGroupName(msg.getFromGroup()) + "】的消息\n" +
                                communismBot.getUserName(msg.getFromClient()) + "[" + msg.getFromClient() + "]：" +
                                msg.getMsg();
                        communismBot.toPrivateMsg(2375985957L, text);
                    }
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

    private boolean containsMe(String msg) {
        return msg.contains("aye") || msg.contains("Aye") || msg.contains("阿叶")
                || msg.contains("小叶") || msg.contains("叶受") || msg.contains("叶哥哥");
    }
}
