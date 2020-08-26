package com.gfg.sellercenter.setting.reader;

import java.net.URL;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import lombok.AllArgsConstructor;
import java.nio.charset.StandardCharsets;

/**
 * The HttpReader will do a request via HTTP
 * @author Dennis Munchausen <dennis.munchausen@global-fashion-group.com>
 * @version 1.0
 * @since 1.0
 */
@AllArgsConstructor
public class HttpReader
{
    private String fqdnUrl;

    private String query;

    /**
     * This json object represent the actual setting value either on a global or on a seller level
     * @param field Name of the field in the database (seller setting which override global)
     * @param sellerId Value of the seller field in the database for which you are looking for
     * @return JSONObject It retrieves a json object with main important values for a setting
     * @throws Exception throws an exception when the endpoint is not reachable or no setting were found
     */
    public JSONObject getSetting(String label, int sellerId) throws Exception
    {
        return new JSONObject(loadResponse("?field=" + label + "&sellerId=" + sellerId));
    }

    /**
     * This json  object represent the actual setting value only on a global level
     * @param field Description name of the field in the database (global setting)
     * @return JSONObject It retrieves a json object with main important values for a setting
     * @throws Exception throws an exception when the endpoint is not reachable or no setting were found
     */
    public JSONObject getSetting(String label) throws Exception
    {
        return new JSONObject(loadResponse("?field=" + label));
    }

    private String loadResponse(String extraQuery) throws IOException
    {
        URL url = new URL(fqdnUrl + query + extraQuery);
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
