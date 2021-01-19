package com.gfg.sellercenter.translation.reader;

import lombok.AllArgsConstructor;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.utils.URIBuilder;

@AllArgsConstructor
public class HttpReader {

    private final String fqdnUrl;

    private final String path;

    public JSONObject getTranslation(String language, String key) throws IOException, URISyntaxException {
        return new JSONObject(this.loadResponse(language, key));
    }

    private String loadResponse(String language, String key) throws IOException, URISyntaxException {
        URIBuilder urlBuilder = new URIBuilder();
        URI uri = urlBuilder.setHost(this.fqdnUrl)
                .setPath(this.path)
                .addParameter("language", language)
                .addParameter("key", key)
                .build();

        try (InputStream stream = uri.toURL().openStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8)
            );

            return toJsonString(bufferedReader);
        }
    }

    private String toJsonString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        return sb.toString();
    }
}
