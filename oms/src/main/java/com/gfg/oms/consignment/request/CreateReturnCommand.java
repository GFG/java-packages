package com.gfg.oms.consignment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public class CreateReturnCommand extends CreateCommand {
    @JsonProperty("method")
    protected final String method = "createConsignmentReturnRequest";
}
