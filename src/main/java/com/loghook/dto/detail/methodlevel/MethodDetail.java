package com.loghook.dto.detail.methodlevel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class MethodDetail {
    private String name;
    private List<String> parameters;
}
