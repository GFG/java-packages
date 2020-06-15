package com.gfg.oms.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RawResponse {
    private Integer code;
    private String content;
    private String errorMessage;
}
