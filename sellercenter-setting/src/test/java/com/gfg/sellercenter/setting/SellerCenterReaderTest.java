package com.gfg.sellercenter.setting;

import com.gfg.sellercenter.setting.entity.Setting;
import com.gfg.sellercenter.setting.reader.HttpReader;
import com.gfg.sellercenter.setting.service.SettingService;
import com.gfg.sellercenter.setting.service.SettingServiceInterface;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SellerCenterReaderTest {

    private static final String RESOURCES_PATH = "src/test/resources/";

    @Test
    public void getSettingByField() throws Exception
    {
        HttpReader readerMock = mock(HttpReader.class);
        when(readerMock.getSetting("field_exists"))
            .thenReturn(
                getFileContent("json/validGlobalSetting.json")
            );

        SettingServiceInterface service = new SettingService(readerMock);

        Setting expectedSetting = new Setting(
                0,
                "fk_shipping_type",
                "32"
        );

        assertTrue(expectedSetting.equals(service.getSetting("field_exists")));
    }

    @Test
    public void getSettingByFieldAndSellerId() throws Exception
    {
        HttpReader readerMock = mock(HttpReader.class);
        when(readerMock.getSetting("field_exists", 1))
            .thenReturn(
                getFileContent("json/validSellerSetting.json")
            );

        SettingServiceInterface service = new SettingService(readerMock);

        Setting expectedSetting = new Setting(
                1,
                "fk_shipping_type",
                "2"
        );

        assertTrue(expectedSetting.equals(service.getSetting("field_exists", 1)));
    }

    private JSONObject getFileContent(String filename) throws IOException {
        return new JSONObject(
                new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filename)))
        );
    }
}
