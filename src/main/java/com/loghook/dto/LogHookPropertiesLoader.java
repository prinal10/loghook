package com.loghook.dto;

import com.loghook.dto.detail.Detail;
import com.loghook.dto.detail.methodlevel.MethodDetail;
import com.loghook.dto.detail.packagelevel.PackageDetail;
import com.loghook.premain.LogHookPreMain;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class LogHookPropertiesLoader {

    public static LogHookProperties loadProperties() {
        Yaml yaml = new Yaml(new Constructor(LogHookProperties.class));
        InputStream inputStream = LogHookPreMain.class
                .getClassLoader()
                .getResourceAsStream("loghook.yaml");
        return getDetails(yaml.load(inputStream));
    }

    private static LogHookProperties getDetails(LogHookProperties logHookProperties) {
        if (logHookProperties.getPackages().stream()
                .anyMatch("DEFAULT_NO_SELECTION_PACKAGE"::equalsIgnoreCase)) {
            throw new IllegalStateException("No package or method specified for loghook.");
        }
        Detail detail = new Detail();
        logHookProperties.getPackages().stream().filter(packageName -> packageName != null && !packageName.isEmpty())
                .forEach(packageName -> setDetails(packageName, detail));
        logHookProperties.setDetail(detail);
        return logHookProperties;
    }

    private static void setDetails(String packageName, Detail detail) {
        String packageDetailName = packageName;
        if (packageName.endsWith(")")) {
            String methodName = packageName.substring(0, packageName.indexOf('('));
            packageDetailName = methodName.substring(0, methodName.lastIndexOf('.'));
            List<String> parameters =
                    Arrays.asList(
                            packageName.substring(packageName.indexOf('(') + 1, packageName.indexOf(')')).split(","));
            detail.addMethodDetail(packageDetailName, new MethodDetail(methodName, parameters));
        }
        detail.addPackageDetail(new PackageDetail(packageDetailName));
    }

}
