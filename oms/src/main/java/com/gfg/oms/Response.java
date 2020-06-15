package com.gfg.oms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfg.oms.response.Error;
import com.gfg.oms.response.GeneralMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response implements Serializable {
    @JsonProperty("OmsSuccessResponse")
    private Boolean isSuccessful;

    @JsonProperty("message")
    private GeneralMessage message;

    @JsonProperty("error")
    private List<Error> errors = new ArrayList<>();

    public void addError(Error error) {
        this.errors.add(error);
    }
}
