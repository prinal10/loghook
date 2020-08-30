package com.loghook.dto.detail;

import com.loghook.dto.detail.methodlevel.MethodDetail;
import com.loghook.dto.detail.packagelevel.PackageDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public class Detail {

    private Set<PackageDetail> packageDetails = new HashSet<>();
    private Map<String, MethodDetail> methodDetails = new HashMap<>();

    public void addPackageDetail(PackageDetail packageDetail) {
        if (Objects.nonNull(packageDetail)) {
            packageDetails.add(packageDetail);
        }
    }

    public void addMethodDetail(String packageName, MethodDetail methodDetail) {
        if (Objects.nonNull(methodDetail) && Objects.nonNull(packageName) && !packageName.isEmpty()) {
            methodDetails.put(packageName, methodDetail);
        }
    }
}
