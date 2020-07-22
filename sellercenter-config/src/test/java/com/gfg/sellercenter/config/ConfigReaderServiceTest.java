package com.gfg.sellercenter.config;

import com.gfg.sellercenter.config.infra.JsonHttpReader;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConfigReaderServiceTest {

    private static final String RESOURCES_PATH = "src/test/resources/json/";

    @Test
    public void getByFolderAndPathWithSimpleValue() throws IOException {
        JsonHttpReader jsonReaderMock = mock(JsonHttpReader.class);
        when(jsonReaderMock.readOne("http://somehost:8081/api/config/v1?folder=core&path=country"))
                .thenReturn(
                        new JSONObject(readFile("simpleConfigRecord.json"))
                );

        ConfigReaderService service = new ConfigReaderService(
                "http://somehost:8081",
                jsonReaderMock
        );

        ConfigRecord expectedResult = new ConfigRecord(36, "core", "country", "BR");

        assertEquals(expectedResult, service.getByFolderAndPath("core", "country"));
    }

    @Test
    public void getByFolderAndPathWithSlashesInPathAndEmptyValue() throws IOException {
        JsonHttpReader jsonReaderMock = mock(JsonHttpReader.class);
        when(jsonReaderMock.readOne("http://somehost:8081/api/config/v1?folder=core&path=order/blocked_states"))
                .thenReturn(
                        new JSONObject(readFile("configRecordEmptyWithSlashes.json"))
                );

        ConfigReaderService service = new ConfigReaderService(
                "http://somehost:8081",
                jsonReaderMock
        );

        ConfigRecord expectedResult = new ConfigRecord(27, "core", "order/blocked_states", "");

        assertEquals(expectedResult, service.getByFolderAndPath("core", "order/blocked_states"));
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + fileName)));
    }

    @Test
    public void getInstance() {
        String url = "some url";
        ConfigReaderService expectedResult = new ConfigReaderService(url, new JsonHttpReader());
        assertEquals(ConfigReaderService.class, expectedResult.getClass());
    }
}
