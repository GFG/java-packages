package com.gfg.sellercenter.translation.entity;

import javax.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Translation {
  @NotNull private String namespace;
  @NotNull private String language;
  @NotNull private String key;
  @NotNull private String message;
}
