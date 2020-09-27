package com.aye10032.utils.timeutil;


import com.aye10032.Zibenbot;
import com.aye10032.utils.ExceptionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 异步线程池，同时执行多个Runnable 结束后执行回调方法
 *
 * @author Dazo66
 */
public class AsynchronousTaskPool extends TimedTaskBase {

    ExecutorService pool;
    Map<Runnable, List<Future<?>>> runnableMap = new ConcurrentHashMap<>();
    Map<Runnable, AsynTaskStatus> statusMap = new ConcurrentHashMap<>();

    public AsynchronousTaskPool(){
        pool = Executors.newCachedThreadPool();
        setTimes(-1).setCycle(TimeUtils.NEXT_SEC)
                .setTiggerTime(new Date(System.currentTimeMillis() + 1000));
    }

    /**
     * 异步执行多个方法，并在时间主线程执行回调方法
     * 注意：要保证可运行的在有限的时间内会运行完毕
     * 否则永久都不会执行回调
     *
     * @param callback 回调方法 所有方法执行完成之后调用
     * @param runnables 可运行的列表
     */
    public AsynTaskStatus execute(Runnable callback, Runnable... runnables) {
        List<Future<?>> list = Collections.synchronizedList(new ArrayList<>());
        AsynTaskStatus status = new AsynTaskStatus();
        statusMap.put(callback, status);
        for (Runnable run : runnables) {
            StackTraceElement[] traceElements = Thread.currentThread().getStackTrace();
            list.add(pool.submit(() -> {
                try {
                    statusMap.get(callback).setStatus(AsynTaskStatus.TASKS_RUNNING);
                    run.run();
                } catch (Exception e) {
                    Zibenbot.logger.warning("异步线程执行出错！:" + e + "\n" + ExceptionUtils.printStack(traceElements));
                }
            }));
        }
        runnableMap.put(callback, list);
        return status;
    }


    @Override
    public void run(Date current) {
        if (runnableMap.size() != 0) {
            List<Runnable> list = new ArrayList<>();
            for (Runnable runnable : runnableMap.keySet()) {
                boolean allExecut = true;
                for (Future<?> future : runnableMap.get(runnable)) {
                    if (!future.isDone() && !future.isCancelled()) {
                        allExecut = false;
                    }
                }
                if (allExecut) {
                    list.add(runnable);
                }
            }
            //一定要先移除 再运行
            list.forEach(r -> runnableMap.remove(r));
            list.forEach(r -> {
                try {
                    statusMap.get(r).setStatus(AsynTaskStatus.CALL_BACK_RUNNING);
                    r.run();
                } catch (Exception e) {
                    Zibenbot.logger.warning("异步线程回调执行异常", e);
                } finally {
                    statusMap.get(r).setStatus(AsynTaskStatus.CALL_BACK_RUNNED);
                    statusMap.remove(r);
                }
            });
        }
    }

}