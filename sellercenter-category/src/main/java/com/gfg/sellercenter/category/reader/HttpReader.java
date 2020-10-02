package com.gfg.sellercenter.category.reader;

import lombok.AllArgsConstructor;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The HttpReader will do a request via HTTP
 * @author Dennis Munchausen <dennis.munchausen@global-fashion-group.com>
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public class HttpReader {

    private final String fqdnUrl;

    /**
     * This json array represent a list of products and categories information
     * @param productIds a list of product ids in order to fetch related categories
     * @return JSONArray It retrieves a json array with a product and category information
     * @throws Exception throws an exception when the endpoint is not reachable
     */
    public JSONArray getProductCategoriesByProductIds(List<Integer> productIds) throws Exception
    {
        String productIdsConverted = productIds.stream().map(String::valueOf)
                .collect(Collectors.joining(","));

        return new JSONArray(loadResponse("?product_ids=[" + productIdsConverted + "]"));
    }

    private String loadResponse(String extraQuery) throws IOException
    {
        String path = "/api/categories-by-product-ids/v1/";
        URL url = new URL(fqdnUrl + path + extraQuery);
        try (InputStream stream = url.openStream()) {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(stream, StandardCharsets.UTF_8)
            );

            return toJsonObject(bufferedReader);
        }
    }

    private String toJsonObject(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        return sb.toString();
    }
}
