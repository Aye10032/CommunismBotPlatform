package com.aye10032.functions;

import com.aye10032.CommunismBot;
import com.aye10032.functions.funcutil.BaseFunc;
import com.aye10032.functions.funcutil.SimpleMsg;
import org.springframework.stereotype.Service;

@Service
public class GbferKillerFunc extends BaseFunc {

    public GbferKillerFunc(CommunismBot communismBot) {
        super(communismBot);
    }

    @Override
    public void setUp() {

    }

    @Override
    public void run(SimpleMsg simpleMsg) {
        if (simpleMsg.getMsg().contains("granbluefantasy")) {
            communismBot.muteMember(simpleMsg.getFromGroup(), simpleMsg.getFromClient(), 114);
            communismBot.replyMsgWithQuote(simpleMsg, "空骑士爪巴" + communismBot.getImg(appDirectory + "/gbfKiller.jpg"));
        }
    }
}
