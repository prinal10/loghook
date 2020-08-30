package com.loghook.advice;

import com.loghook.dto.LogHookProperties;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHookExitAdvice {
    public static LogHookProperties logHookProperties;

    @Advice.OnMethodExit
    public static void endTimer(@Advice.Enter long startTime, @Advice.Origin String method,
                                @Advice.This(optional = true) Object object) {
        String logTime = logHookProperties.isUseCustomLogLine() ? logHookProperties.getCustomLogLine() :
                "Method: " + method + " executed in " + (System.currentTimeMillis() - startTime) + "milliseconds.";
        if (object == null) {
            if (logHookProperties.isUseConsoleLogOnNoLogger()) {
                System.out.println(logTime);
            }
            return;
        }
        Logger log = LoggerFactory.getLogger(object.getClass());
        log.info(logTime);
    }
}
