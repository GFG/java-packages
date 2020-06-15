package com.gfg.oms.consignment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class Attribute {
    @NotNull
    private String label;
    private String value;
}
