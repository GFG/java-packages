package com.gfg.sellercenter.translation.entity;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
    @NotNull
    private String namespace;
    @NotNull
    private String language;
    @NotNull
    private String key;
    @NotNull
    private String message;
}
