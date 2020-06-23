package com.gfg.oms.consignment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfg.oms.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateConsignmentResponse extends Response implements Serializable {
    @JsonProperty("message")
    private Message message;
}
