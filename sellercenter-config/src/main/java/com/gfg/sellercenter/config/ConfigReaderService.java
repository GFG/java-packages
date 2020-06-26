package com.gfg.sellercenter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfg.sellercenter.config.infra.JsonHttpReader;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@AllArgsConstructor
public class ConfigReaderService implements ConfigReaderServiceInterface {
    @NotNull
    @NotEmpty
    private final String hostUrl;

    @NotNull
    private final JsonHttpReader jsonReader;

    private static final String API_PATH = "/api/config/v1";

    @Override
    public ConfigRecord getByFolderAndPath(String folder, String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String requestUrl = hostUrl + API_PATH + "?folder=" + folder + "&path=" + path;
        return mapper.readValue(jsonReader.readOne(requestUrl).toString(), ConfigRecord.class);
    }
}
