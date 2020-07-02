package com.gfg.sellercenter.seller;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Seller implements Serializable {
    @NotNull
    private Integer id;

    private String email;

    private String name;

    @JsonProperty("company_name")
    private String companyName;

    @JsonProperty("short_code")
    private String shortCode;

    @JsonProperty("src_id")
    private String srcId;
}
