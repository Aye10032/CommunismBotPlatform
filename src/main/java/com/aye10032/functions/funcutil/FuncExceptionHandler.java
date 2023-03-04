package com.aye10032.functions.funcutil;

import com.aye10032.CommunismBot;
import com.aye10032.utils.ExceptionUtils;
import com.dazo66.command.exceptions.RedundantParametersException;
import com.dazo66.command.interfaces.ExceptionHandler;

/**
 * @author Dazo66
 */
public class FuncExceptionHandler implements ExceptionHandler {

    public static final FuncExceptionHandler INSTENCE = new FuncExceptionHandler();

    @Override
    public void checkExceptionCetch(Exception e) {
        CommunismBot.logWarningStatic(ExceptionUtils.printStack(e));
    }

    @Override
    public void commandRuntimeExceptionCatch(Exception e) {
        CommunismBot.logWarningStatic(ExceptionUtils.printStack(e));
    }

    @Override
    public void ifNotRunntimeExceptionCatch(Exception e) {
        CommunismBot.logWarningStatic(ExceptionUtils.printStack(e));
    }

    @Override
    public void redundantParametersExceptionCatch(RedundantParametersException e) {
        CommunismBot.logWarningStatic(ExceptionUtils.printStack(e));
    }
}
