package com.aye10032.functions;

import com.aye10032.CommunismBot;
import com.aye10032.data.HistoryEventType;
import com.aye10032.data.historytoday.entity.HistoryToday;
import com.aye10032.data.historytoday.service.HistoryTodayService;
import com.aye10032.functions.funcutil.BaseFunc;
import com.aye10032.functions.funcutil.FuncExceptionHandler;
import com.aye10032.functions.funcutil.SimpleMsg;
import com.dazo66.command.Commander;
import com.dazo66.command.CommanderBuilder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @program: communismbot
 * @className: HistoryTodayFunc
 * @Description: 历史上的今天功能
 * @version: v1.0
 * @author: Aye10032
 * @date: 2022/6/2 下午 6:24
 */
@Service
public class HistoryTodayFunc extends BaseFunc {

    private HistoryTodayService historyTodayService;

    private Commander<SimpleMsg> commander;

    public HistoryTodayFunc(CommunismBot communismBot, HistoryTodayService historyTodayService) {
        super(communismBot);
        this.historyTodayService = historyTodayService;
        commander = new CommanderBuilder<SimpleMsg>()
                .seteHandler(FuncExceptionHandler.INSTENCE)
                .start()
                .or(this::containsToday)
                .run((msg) -> {
                    int event_count = 0;
                    StringBuilder builder = new StringBuilder();
                    builder.append("今天是")
                            .append(getDateString())
                            .append("，历史上的今天发生了这些事：\n");

                    List<HistoryToday> history_today_list = historyTodayService.getTodayHistory(getDate());
                    event_count += history_today_list.size();
                    if (event_count != 0) {
                        for (int i = 0; i < history_today_list.size(); i++) {
                            builder.append(i + 1)
                                    .append("、");
                            if (!history_today_list.get(i).getYear().equals("")) {
                                builder.append(history_today_list.get(i).getYear())
                                        .append(" ");
                            }
                            builder.append(history_today_list.get(i).getHistory())
                                    .append("\n");
                        }
                    }
                    if (msg.isGroupMsg()) {
                        List<HistoryToday> group_history_list = historyTodayService.getGroupHistory(getDate(), msg.getFromGroup());
                        event_count += group_history_list.size();
                        if (event_count == 0) {
                            communismBot.replyMsg(msg, "历史上的今天无事发生");
                        } else {
                            if (group_history_list.size() != 0) {
                                if (history_today_list.size() != 0) {
                                    builder.append("-------------\n");
                                }
                                for (int i = 0; i < group_history_list.size(); i++) {
                                    builder.append(i + 1)
                                            .append("、");
                                    if (!group_history_list.get(i).getYear().equals("")) {
                                        builder.append(group_history_list.get(i).getYear())
                                                .append(" ");
                                    }
                                    builder.append(group_history_list.get(i).getHistory())
                                            .append("\n");
                                }
                            }
                            communismBot.replyMsg(msg, builder.toString());
                        }
                    }else {
                        if (event_count==0){
                            communismBot.replyMsg(msg, "历史上的今天无事发生");
                        }else {
                            communismBot.replyMsg(msg, builder.toString());
                        }
                    }
                })
                .next()
                .orArray(s -> true)
                .run((msg) -> {
                    if (msg.isPrivateMsg() && msg.getFromClient() == 2375985957L) {
                        String[] msgs = msg.getCommandPieces();
                        if (msgs.length == 2) {
                            historyTodayService.insertHistory(msgs[1], "", getDate());
                            communismBot.replyMsg(msg, "done");
                        } else if (msgs.length == 3) {
                            historyTodayService.insertHistory(msgs[1], msgs[2], getDate());
                            communismBot.replyMsg(msg, "done");
                        } else {
                            communismBot.replyMsg(msg, "格式不正确！");
                        }
                    } else if (msg.isGroupMsg()) {
                        String[] msgs = msg.getCommandPieces();
                        if (msgs.length == 2) {
                            historyTodayService.insertHistory(msgs[1], getYear(), getDate(), msg.getFromGroup());
                            communismBot.replyMsg(msg, "done");
                            communismBot.toPrivateMsg(2375985957L, msg.getFromClient() + "添加了一条历史：" + msgs[1]);
                        } else {
                            communismBot.replyMsg(msg, "格式不正确！");
                        }
                    } else {
                        communismBot.replyMsg(msg, "no access!");
                    }
                })
                .pop()
                .or(this::containsTomorrow)
                .next()
                .orArray(s -> true)
                .run((msg) -> {
                    if (msg.isPrivateMsg() && msg.getFromClient() == 2375985957L) {
                        String[] msgs = msg.getCommandPieces();
                        if (msgs.length == 2) {
                            historyTodayService.insertHistory(msgs[1], "", getTomorrow());
                            communismBot.replyMsg(msg, "done");
                        } else if (msgs.length == 3) {
                            historyTodayService.insertHistory(msgs[1], msgs[2], getTomorrow());
                            communismBot.replyMsg(msg, "done");
                        } else {
                            communismBot.replyMsg(msg, "格式不正确！");
                        }
                    } else if (msg.isGroupMsg()) {
                        String[] msgs = msg.getCommandPieces();
                        if (msgs.length == 2) {
                            historyTodayService.insertHistory(msgs[1], getYear(), getTomorrow(), msg.getFromGroup());
                            communismBot.replyMsg(msg, "done");
                            communismBot.toPrivateMsg(2375985957L, msg.getFromClient() + "添加了一条历史：" + msgs[1]);
                        } else {
                            communismBot.replyMsg(msg, "格式不正确！");
                        }
                    } else {
                        communismBot.replyMsg(msg, "no access!");
                    }
                })
                .pop()
                .or(".岁月史书"::equals)
                .next()
                .orArray(s -> true)
                .run((msg) -> {
                    String[] msgs = msg.getCommandPieces();
                    if (msg.isGroupMsg() && msgs.length == 2) {
                        HistoryToday history = historyTodayService.selectHistory(msgs[1], getDate(), msg.getFromGroup());
                        if (history == null){
                            communismBot.replyMsg(msg, "不存在这条历史");
                        }else if (history.getEventType().equals(HistoryEventType.HISTORY)){
                            communismBot.replyMsg(msg, "因果不够，无法抹除这条历史");
                        }else {
                            historyTodayService.deleteHistory(history.getHistory(), history.getEventDate());
                            communismBot.replyMsg(msg, communismBot.at(msg.getFromClient())+" 发动了岁月史书，抹去了一条历史");
                        }
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

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
        String date = String.format("%02d", calendar.get(Calendar.DATE));

        return month + date;
    }

    private String getDateString() {
        Calendar calendar = Calendar.getInstance();
        String month = String.format("%d月", calendar.get(Calendar.MONTH) + 1);
        String date = String.format("%d日", calendar.get(Calendar.DATE));

        return month + date;
    }

    private String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String month = String.format("%02d", calendar.get(Calendar.MONTH) + 1);
        String date = String.format("%02d", calendar.get(Calendar.DATE));

        return month + date;
    }

    private String getYear() {
        Calendar calendar = Calendar.getInstance();
        String year = String.format("%d年", calendar.get(Calendar.YEAR));

        return year;
    }

    private boolean containsToday(String command){
        return (command.equals("历史上的今天")||command.equals(".历史上的今天")||command.equalsIgnoreCase(".LSSDJT"));
    }

    private boolean containsTomorrow(String command){
        return (command.equals("历史上的明天")||command.equals(".历史上的明天")||command.equalsIgnoreCase(".LSSDMT"));
    }
}
