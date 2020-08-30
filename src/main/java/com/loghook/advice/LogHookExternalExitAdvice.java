package com.loghook.advice;

import com.loghook.dto.LogHookProperties;
import net.bytebuddy.asm.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LogHookExternalExitAdvice {
    public static LogHookProperties logHookProperties;
    private static final Logger log = LoggerFactory.getLogger(LogHookExternalExitAdvice.class);

    @Advice.OnMethodExit
    public static void endTimer(@Advice.Enter long startTime, @Advice.Origin String method) {
        String logTime = logHookProperties.isUseCustomLogLine() ? logHookProperties.getCustomLogLine() :
                "Method: " + method + " executed in " + (System.currentTimeMillis() - startTime) + "milliseconds.";
        try {
            Files.write(Paths.get(logHookProperties.getLogFilePath()), logTime.getBytes(), StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error("Loghook failed to log execution to file: " + logHookProperties.getLogFilePath(), e);
        }
    }
}
