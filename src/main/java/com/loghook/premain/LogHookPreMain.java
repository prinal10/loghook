package com.loghook.premain;

import com.loghook.dto.LogHookProperties;
import com.loghook.dto.LogHookPropertiesLoader;
import com.loghook.transformer.LogHookTransformer;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;

import java.lang.instrument.Instrumentation;

@Slf4j
public class LogHookPreMain {

    public static void premain(final String args, Instrumentation instrumentation) {
        log.info("Starting PreMain.");
        LogHookProperties logHookProperties = LogHookPropertiesLoader.loadProperties();
        new AgentBuilder.Default()
                .disableClassFormatChanges()//disable rebasing a method and forces only redefinition/RETRANSFORMATION
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)//this transformation will also apply to already loaded classes as well
                .type(typeDescription -> checkClassPackage(typeDescription, logHookProperties))
                .transform(new LogHookTransformer(logHookProperties))
                .installOn(instrumentation);
        log.info("Completed PreMain.");
    }

    private static boolean checkClassPackage(TypeDescription typeDescription, LogHookProperties logHookProperties) {
        if (typeDescription == null || logHookProperties.getDetail().getPackageDetails() == null ||
                logHookProperties.getDetail().getPackageDetails().isEmpty()) {
            return false;
        } else {
            return logHookProperties.getDetail().getPackageDetails().stream()
                    .anyMatch(packageName -> packageName.getName()
                            .equalsIgnoreCase(typeDescription.getPackage().getName()));
        }
    }
}
