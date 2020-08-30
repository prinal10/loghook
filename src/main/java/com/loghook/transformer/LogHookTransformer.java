package com.loghook.transformer;

import com.loghook.advice.LogHookEnterAdvice;
import com.loghook.advice.LogHookExitAdvice;
import com.loghook.advice.LogHookExternalExitAdvice;
import com.loghook.dto.LogHookProperties;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

@Slf4j
@Setter
@AllArgsConstructor
public class LogHookTransformer implements AgentBuilder.Transformer {

    private final LogHookProperties logHookProperties;

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription,
                                            ClassLoader classLoader, JavaModule module) {
        log.debug("applying transformation to class: " + typeDescription.getClass().getName());
        return builder.visit(logHookMethodVisitor());
    }

    private AsmVisitorWrapper logHookMethodVisitor() {
        if (logHookProperties.isEnableExternalLog()) {
            LogHookExternalExitAdvice.logHookProperties = logHookProperties;
            return Advice.to(LogHookEnterAdvice.class, LogHookExternalExitAdvice.class).on(MethodDescription::isMethod);
        }
        LogHookExitAdvice.logHookProperties = logHookProperties;
        return Advice.to(LogHookEnterAdvice.class, LogHookExitAdvice.class).on(MethodDescription::isMethod);
    }

}
