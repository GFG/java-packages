package com.gfg.sellercenter.translation.service;

import com.gfg.sellercenter.translation.entity.Translation;
import com.gfg.sellercenter.translation.reader.HttpReader;
import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

@AllArgsConstructor
public class TranslateService implements Translator {

    private final HttpReader httpReader;

    @Override
    public Translation getTranslation(String language, String key) throws IOException, URISyntaxException {

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
}
