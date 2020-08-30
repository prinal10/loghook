package com.loghook.inject;

import com.loghook.dto.LogHookProperties;
import com.loghook.dto.LogHookPropertiesLoader;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

@Slf4j
public class LogHook {

    public static void main(String[] args) {
        log.info("Starting LogHookInjection");
        LogHookProperties logHookProperties = LogHookPropertiesLoader.loadProperties();
        if (CollectionUtils.isEmpty(logHookProperties.getProcessIds()) ||
                StringUtils.isEmpty(logHookProperties.getLogHookJarPath())) {
            log.error("No processIds or LogHookJarPath provided.");
        }
        for (String processId : logHookProperties.getProcessIds()) {
            try {
                VirtualMachine virtualMachine = VirtualMachine.attach(processId);
                virtualMachine.loadAgent(logHookProperties.getLogHookJarPath(), null);
                virtualMachine.detach();
            } catch (AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException e) {
                log.error("Cannot inject log hook in jvm process with processId: " + processId);
            }
        }
        log.info("Completed LogHookInjection");
    }
}
