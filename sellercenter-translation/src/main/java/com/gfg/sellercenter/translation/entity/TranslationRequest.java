package com.gfg.sellercenter.translation.entity;

import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TranslationRequest implements Serializable {
  private String key;
  private Map<String, String> params;
}
