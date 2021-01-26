package com.gfg.sellercenter.translation.service;

import com.gfg.sellercenter.translation.entity.PayloadTranslationRequest;
import com.gfg.sellercenter.translation.entity.Translation;
import com.gfg.sellercenter.translation.entity.TranslationRequest;
import com.gfg.sellercenter.translation.reader.HttpReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

@AllArgsConstructor
public class TranslateService implements Translator {

  private final HttpReader httpReader;

  @Override
  public Translation getTranslation(String language, String key)
      throws IOException, URISyntaxException {

    JSONObject response = this.httpReader.getTranslation(language, key);

    if (!response.getBoolean("status")) {
      return null;
    }

    JSONObject payload = response.getJSONObject("payload");

    return Translation.builder()
        .key(payload.getString("key"))
        .language(payload.getString("language"))
        .namespace(payload.getString("namespace"))
        .message(payload.getString("value"))
        .build();
  }

  public Map<String, Translation> getTranslations(
      String language, List<TranslationRequest> requests) throws IOException, URISyntaxException {

    PayloadTranslationRequest payload =
        new PayloadTranslationRequest(language, new JSONArray(requests));
    JSONObject response = this.httpReader.getTranslations(new JSONObject(payload));

    Map<String, Translation> translationMap = new HashMap<>();

    response
        .getJSONArray("translations")
        .forEach(item -> this.hydrateMap(translationMap, (JSONObject) item));

    return translationMap;
  }

  private void hydrateMap(Map<String, Translation> map, JSONObject responsePayload) {

    Translation trl =
        Translation.builder()
            .key(responsePayload.getString("key"))
            .language(responsePayload.getString("language"))
            .namespace(responsePayload.getString("namespace"))
            .message(responsePayload.getString("value"))
            .build();
    map.put(responsePayload.getString("key"), trl);
  }
}
