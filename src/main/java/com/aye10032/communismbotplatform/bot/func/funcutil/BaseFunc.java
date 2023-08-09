package com.aye10032.communismbotplatform.bot.func.funcutil;

import com.aye10032.communismbotplatform.bot.CommunismBot;

/**
 * 方法的基础类 所有方法只需要继承了IFunc
 * 并且通过注解 @Service 进行标记
 * 即可自动注入
 *
 * @author Dazo66
 */
public abstract class BaseFunc implements IFunc {

    public CommunismBot bot;
    protected String appDirectory;


    public BaseFunc(CommunismBot bot) {
        this.bot = bot;
        if (bot == null) {
            appDirectory = "";
        } else {
            appDirectory = bot.appDirectory;
        }
    }

    /**
     * 根据传入的消息，回复消息
     *
     * @param fromMsg 从哪来的什么消息
     * @param msg     要回复的内容
     */
    public void replyMsg(SimpleMsg fromMsg, String msg) {
        if (bot != null) {
            bot.replyMsg(fromMsg, msg);
        } else {
            System.out.println(msg);
        }
    }

    /**
     * 根据传入的消息，引用回复消息
     *
     * @param fromMsg 从哪来的什么消息
     * @param msg     要回复的内容
     */
    public void replyMsgWithQuote(SimpleMsg fromMsg, String msg) {
        if (bot != null) {
            bot.replyMsgWithQuote(fromMsg, msg);
        } else {
            System.out.println(msg);
        }
    }

    /**
     * 回复消息 并且施加回复钩子 如果有用户回复这个消息会触发钩子
     *
     * @param fromMsg 从哪来的什么消息
     * @param msg     要回复的内容
     */
    public void replyMsgWithQuoteHook(SimpleMsg fromMsg, String msg, IQuoteHook hook) {
        if (bot != null) {
            bot.replyMsgWithQuoteHook(fromMsg, msg, hook);
        } else {
            System.out.println(msg);
        }
    }
}
