package com.aye10032.functions;

import com.aye10032.Zibenbot;
import com.aye10032.functions.funcutil.BaseFunc;
import com.aye10032.functions.funcutil.SimpleMsg;
import com.aye10032.utils.SeleniumUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @author Dazo66
 */
@Service
public class ScreenshotFunc extends BaseFunc {

    static String BLANK = " ";

    public ScreenshotFunc(Zibenbot zibenbot) {
        super(zibenbot);
    }

    static {

    }

    @Override
    public void run(SimpleMsg simpleMsg) {
        String msg = simpleMsg.getMsg();
        if (msg.startsWith("网页快照") || msg.startsWith(".网页快照")){
            msg = msg.replaceAll(" +", " ");
            String[] args = msg.split(" ");
            zibenbot.pool.timeoutEvent(10, () -> {
                try {
                    if (args.length == 3) {
                        replyMsg(simpleMsg, zibenbot.getImg(getScreenshot(args[1], Integer.parseInt(args[2]))));
                    } else if (args.length == 2) {
                        replyMsg(simpleMsg, zibenbot.getImg(getScreenshot(args[1], 4000)));
                    } else {
                        replyMsg(simpleMsg, "参数异常，Example：网页快照 [url] [timeout]");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    replyMsg(simpleMsg, "获取网页快照失败，可能是网页不支持" + e);
                }
            });

        }

    }

    public File getScreenshot(String url, int timeOut) throws IOException, InterruptedException {
        String outFileName = getOutFileName(url);
        return SeleniumUtils.getScreenshot(url, outFileName, timeOut);
    }

    @Override
    public void setUp() {
        File dir = new File(zibenbot.appDirectory + "/screenshot");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //清理缓存
        long current = System.currentTimeMillis();
        int i = 0;
        for (File f : dir.listFiles()) {
            if (f.isFile() && current - f.lastModified() > 86400 * 3 * 1000) {
                f.delete();
                i++;
            }
        }
        Zibenbot.logInfoStatic("清理了三天前的缓存 " + i + " 张。");
    }


    public String getOutFileName(String url){
        return zibenbot.appDirectory + "\\screenshot\\" + url.hashCode() + ".jpg";
    }
}
