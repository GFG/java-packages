package com.gfg.sellercenter.translation.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.JSONArray;

@AllArgsConstructor
@Getter
public class PayloadTranslationRequest implements Serializable {

  private String language;

  private JSONArray translations;
}
