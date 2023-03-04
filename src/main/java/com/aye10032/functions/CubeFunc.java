package com.aye10032.functions;

import com.aye10032.CommunismBot;
import com.aye10032.functions.funcutil.BaseFunc;
import com.aye10032.functions.funcutil.FuncExceptionHandler;
import com.aye10032.functions.funcutil.SimpleMsg;
import com.dazo66.command.Commander;
import com.dazo66.command.CommanderBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Random;
import java.util.Stack;

@Service
public class CubeFunc extends BaseFunc {

    private Stack<String> cubeStack = new Stack<String>();
    private String[] cubarr = new String[]{"F", "B", "U", "D", "R", "L"};
    private String[] statusarr = new String[]{"", "", "'", "'", "2"};
    private int step = 20;
    private String cuberandom = "";

    private Commander<SimpleMsg> commander;

    public CubeFunc(CommunismBot communismBot) {
        super(communismBot);
        commander = new CommanderBuilder<SimpleMsg>()
                .seteHandler(FuncExceptionHandler.INSTENCE)
                .start()
                .or(".3"::equals)
                .run((cqmsg) -> {
                    newCuberandom();
                    communismBot.replyMsg(cqmsg, getCuberandom());
                })
                .or(".cfop"::equalsIgnoreCase)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP1.jpg"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP2.jpg"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP3.jpg"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP4.jpg")));
                })
                .next()
                .or("f2l"::equalsIgnoreCase)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg, communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP2.jpg")));
                })
                .or("oll"::equalsIgnoreCase)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg, communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP3.jpg")));
                })
                .or("pll"::equalsIgnoreCase)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg, communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/CFOP4.jpg")));
                })
                .pop()
                .or(".mega"::equalsIgnoreCase)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/Mega1.jpg"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/Mega2.jpg")));
                })
                .or(".22"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/2X201.png"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/2X202.png"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/2X203.png")));
                })
                .or(".彳亍"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu01.png"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu02.png"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu03.png"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu04.png"))
                                    + communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu05.png")));
                })
                .next()
                .or("编码"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu_bianma.png")));
                })
                .or("角"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu_jiao.png")));
                })
                .or("棱1"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu_leng.png")));
                })
                .or("棱2"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu_leng2.png")));
                })
                .or("翻棱"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu_fanleng.png")));
                })
                .or("翻角"::equals)
                .run((cqmsg) -> {
                    communismBot.replyMsg(cqmsg,
                            communismBot.getImg(new File(communismBot.appDirectory + "/image/cube/chichu_fanjiao.png")));
                })
                .pop()
                .build();
    }

    private String nextStep(Random random) {
        String temp = "";

        if (!cubeStack.empty()) {
            while (temp.equals("") || cubeStack.peek().contains(temp)) {
                temp = cubarr[random.nextInt(cubarr.length)];
            }
        } else {
            temp = cubarr[random.nextInt(cubarr.length)];
        }

        return temp;
    }

    private void newCuberandom() {
        Random random = new Random();
        int num = random.nextInt(5);
        step += num;
        for (int i = 0; i < step; i++) {
            String thisStep = nextStep(random) + statusarr[random.nextInt(statusarr.length)];
            cubeStack.push(thisStep);
        }

        String temp = "";
        while (!cubeStack.empty()) {
            temp += cubeStack.pop() + " ";
        }

        setCuberandom(temp);
    }

    public String getCuberandom() {
        return this.cuberandom;
    }

    public void setCuberandom(String cuberandom) {
        this.cuberandom = cuberandom;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void run(SimpleMsg simpleMsg) {
        commander.execute(simpleMsg);
    }
}
