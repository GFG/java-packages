package com.gfg.sellercenter.setting.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * @author Dennis Munchausen <dennis.munchausen@global-fashion-group.com>
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Setting {

    @NotNull
    private int sellerId;

    @NotNull
    private String label;

    @NotNull
    private String value;
}
