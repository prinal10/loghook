package com.loghook.dto;

import com.loghook.dto.detail.Detail;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class LogHookProperties {

    private List<String> packages = Collections.singletonList("DEFAULT_NO_SELECTION_PACKAGE");
    private String customLogLine;
    private boolean useCustomLogLine;
    private String logFilePath;
    private boolean enableExternalLog;
    private boolean useConsoleLogOnNoLogger;
    private List<String> processIds;
    private String logHookJarPath;
    private Detail detail;

}
