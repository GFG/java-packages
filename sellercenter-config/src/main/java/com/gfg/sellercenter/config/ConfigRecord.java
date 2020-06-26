package com.gfg.sellercenter.config;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ConfigRecord implements Serializable {
    @NotNull
    private Integer id;

    @NotNull
    private String folder;

    @NotNull
    private String path;

    @NotNull
    private String value;
}
