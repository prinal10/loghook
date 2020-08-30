package com.loghook.advice;

import net.bytebuddy.asm.Advice;

public class LogHookEnterAdvice {
    @Advice.OnMethodEnter
    public static long startTimer() {
        return System.currentTimeMillis();
    }
}
