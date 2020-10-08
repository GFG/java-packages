package com.gfg.sellercenter.category.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public enum CategoryStatus {
    ACTIVE("active"),
    INACTIVE("inactive"),
    INHERITED_INACTIVE("inherited_inactive"),
    DELETED("deleted");

    @NotNull
    private String status;

    public String getStatus() {
        return status;
    }

    @JsonValue
    public String toJson() {
        return this.status;
    }
}
