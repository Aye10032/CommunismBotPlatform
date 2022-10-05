package com.aye10032.functions;

import com.aye10032.Zibenbot;
import com.aye10032.functions.funcutil.BaseFunc;
import com.aye10032.functions.funcutil.FuncExceptionHandler;
import com.aye10032.functions.funcutil.SimpleMsg;
import com.dazo66.command.Commander;
import com.dazo66.command.CommanderBuilder;
import net.mamoe.mirai.message.data.OnlineAudio;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @program: communismbot
 * @className: VideoFunction
 * @Description: 语音自动倒放功能
 * @version: v1.0
 * @author: Aye10032
 * @date: 2022/10/1 下午 8:28
 */
@Service
public class AudioFunction extends BaseFunc {

    private Commander<SimpleMsg> commander;

    public AudioFunction(Zibenbot zibenbot) {
        super(zibenbot);
        commander = new CommanderBuilder<SimpleMsg>()
                .seteHandler(FuncExceptionHandler.INSTENCE)
                .start()
                .or(this::isAudio)
                .run((msg)->{
                    if (msg.isPrivateMsg()&&msg.getFromClient() == 2375985957L){
                        File audio = zibenbot.getAudioFromMsg(msg);
                        zibenbot.replyMsg(msg, "done" + audio.getAbsolutePath());
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

    private boolean isAudio(String msg){
        String reg = "\\[mirai:audio:\\{(\\w{8})-(\\w{4})-(\\w{4})-(\\w{4})-(\\w{12})}.mirai]";

        return Pattern.matches(reg, msg);
    }
}
