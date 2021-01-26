package com.gfg.sellercenter.translation.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class HttpReader {

  private static final String PATH = "/api/translations/v1";

  private final String domain;

  private final String schema;

  private CloseableHttpClient httpClient;

  public HttpReader(String domain, String schema) {
    this.domain = domain;
    this.schema = schema;
  }

  public JSONObject getTranslation(String language, String key)
      throws IOException, URISyntaxException {
    return new JSONObject(this.loadResponse(language, key));
  }

  private String loadResponse(String language, String key) throws IOException, URISyntaxException {
    URIBuilder urlBuilder = new URIBuilder();
    URI uri =
        urlBuilder
            .setScheme(schema)
            .setHost(this.domain)
            .setPath(PATH)
            .addParameter("language", language)
            .addParameter("key", key)
            .build();

    try (InputStream stream = uri.toURL().openStream()) {
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));

      return toJsonString(bufferedReader);
    }
  }

  public JSONObject getTranslations(JSONObject payloadTranslationRequest)
      throws IOException, URISyntaxException {

    URIBuilder urlBuilder = new URIBuilder();
    URI uri = urlBuilder.setScheme(this.schema).setHost(this.domain).setPath(PATH).build();

    HttpPost postRequest = new HttpPost(uri.toURL().toString());

    StringEntity entity = new StringEntity(payloadTranslationRequest.toString());
    postRequest.setEntity(entity);
    postRequest.setHeader("Accept", "application/json");
    postRequest.setHeader("Content-type", "application/json");

    CloseableHttpResponse response = this.getHttpClient().execute(postRequest);

    try (InputStream stream = response.getEntity().getContent()) {
      BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
      this.getHttpClient().close();
      return new JSONObject(toJsonString(bufferedReader));
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

  private CloseableHttpClient getHttpClient() throws URISyntaxException {

    if (this.httpClient == null) {
      this.httpClient = HttpClients.createDefault();
    }

    return this.httpClient;
  }
}
