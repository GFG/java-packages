package com.gfg.oms.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Error implements Serializable {
    @JsonProperty("error")
    private ErrorData error;

    public static Error generalError(String message) {
        return new Error(new ErrorData(null, message));
    }
}
