package com.gfg.sellercenter.translation.service;

import com.gfg.sellercenter.translation.entity.Translation;
import com.gfg.sellercenter.translation.entity.TranslationRequest;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface Translator {

  /**
   * The Translation object represents the actual parameter of provided key and the correct
   * translated message.
   *
   * @param language (en-us, ru-ru, de-de etc.) is the value of the current used language.
   * @param key defines which kind of translated messaged should be returned.
   * @return Translation contains the meta data of the expected key and the translated message.
   * @throws IOException throws this exception if we can´t reach the graph service.
   * @throws URISyntaxException throws this exception if the URI syntax is incorrect.
   */
  Translation getTranslation(String language, String key) throws IOException, URISyntaxException;

  /**
   * The Translation object represents the actual parameter of provided key and the correct
   * translated message.
   *
   * @param key defines which kind of translated messaged should be returned.
   * @param language (en-us, ru-ru, de-de etc.) is the value of the current used language.
   * @param requests
   * @return Translation contains the meta data of the expected key and the translated message.
   * @throws IOException throws this exception if we can´t reach the graph service.
   * @throws URISyntaxException throws this exception if the URI syntax is incorrect.
   */
  public Map<String, Translation> getTranslations(
      String language, List<TranslationRequest> requests) throws IOException, URISyntaxException;
}
