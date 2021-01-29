package com.gfg.sellercenter.translation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.gfg.sellercenter.translation.entity.Translation;
import com.gfg.sellercenter.translation.reader.HttpReader;
import com.gfg.sellercenter.translation.service.TranslateService;
import com.gfg.sellercenter.translation.service.Translator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.junit.Test;

public class TranslateServiceTest {

  private static final String RESOURCE_PATH = "src/test/resources/";

  @Test
  public void getValidTranslation() throws IOException, URISyntaxException {
    HttpReader readerMock = mock(HttpReader.class);
    when(readerMock.getTranslation("en-us", "login:restore-password.action.loading.label"))
        .thenReturn(this.getFileContent("json/validTranslation.json"));

    Translator svc = new TranslateService(readerMock);

    Translation trl = svc.getTranslation("en-us", "login:restore-password.action.loading.label");

    assertEquals(
        new Translation(
            "login", "fallback", "restore-password.action.loading.label", "Restoring password..."),
        trl);
  }

  @Test
  public void getInvalidTranslation() throws IOException, URISyntaxException {
    HttpReader readerMock = mock(HttpReader.class);
    when(readerMock.getTranslation("en-us", "login:restore-password.action.loading"))
        .thenReturn(this.getFileContent("json/invalidTranslation.json"));

    TranslateService svc = new TranslateService(readerMock);

    Translation trl = svc.getTranslation("en-us", "login:restore-password.action.loading");

    assertNull(trl);
  }

  private JSONObject getFileContent(String filename) throws IOException {
    return new JSONObject(new String(Files.readAllBytes(Paths.get(RESOURCE_PATH + filename))));
  }
}
