package com.gfg.sellercenter.filemanager.infra;

import lombok.NonNull;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientApi {
    private static final int HTTP_STATUS_OK = 200;
    private static final int HTTP_STATUS_NOT_FOUND = 404;

    public String post(@NonNull String url, @NonNull String body) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(body));

        return request(httpPost);
    }

    public String get(@NonNull String url, Map<String, String> params) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(url);

        if (params != null) {
            params.keySet().forEach(p -> builder.setParameter(p, params.get(p)));
        }

        HttpGet httpGet = new HttpGet(builder.build());

        return request(httpGet);
    }

    public String get(@NonNull String url) throws IOException, URISyntaxException {
        return get(url, null);
    }

    public String patch(@NonNull String url, @NonNull String body) throws IOException {
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setEntity(new StringEntity(body));

        return request(httpPatch);
    }

    private String request(HttpUriRequest request) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        String content = readStream(response.getEntity().getContent());

        client.close();

        if (statusCode == HTTP_STATUS_OK) {
            return content;
        } else if (statusCode == HTTP_STATUS_NOT_FOUND) {
            throw new IOException(String.format("Request resulted in 404 response for url %s", request.getURI()));
        } else {
            throw new IOException(String.format("Internal error on remote for request url %s, error %s",
                    request.getURI(),
                    content));
        }
    }

    private String readStream(InputStream inputStream) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }
}
