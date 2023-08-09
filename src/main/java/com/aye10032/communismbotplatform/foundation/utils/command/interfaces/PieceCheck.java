package com.aye10032.communismbotplatform.foundation.utils.command.interfaces;

/**
 * 单位片段检查器
 *
 * @author Dazo66
 */
@FunctionalInterface
public interface PieceCheck {
    boolean check(String piece);
}
