package com.gfg.oms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class Request implements Serializable {
    protected final int api = 1;
    protected final int id = 1;

    @JsonProperty("apikey")
    protected String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
