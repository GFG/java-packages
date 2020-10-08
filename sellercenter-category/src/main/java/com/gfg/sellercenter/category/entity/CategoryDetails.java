package com.gfg.sellercenter.category.entity;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CategoryDetails implements Serializable {

    private int id;

    @NotNull private String name;

    private Integer srcId;

    @NotNull private CategoryStatus status;

    private int lft;

    private int rgt;

    private boolean visible;

    private boolean fulfillmentVisible;

    public void markAsNotFulfilmentVisible() {
        this.fulfillmentVisible = false;
    }

    public void markAsFulfilmentVisible() {
        this.fulfillmentVisible = true;
    }

    public boolean hasSrcId() {
        return this.srcId != null;
    }
}
