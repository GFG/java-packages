package com.gfg.oms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ErrorData implements Serializable {
    @JsonProperty("field")
    private String field;
    @JsonProperty("message")
    private String message;
}
