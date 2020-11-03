package com.gfg.sellercenter.product.port.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive"),
    DELETED("deleted");

    @NotNull
    private String status;

    @JsonValue
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
