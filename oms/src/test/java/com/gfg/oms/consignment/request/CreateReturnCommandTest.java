package com.gfg.oms.consignment.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CreateReturnCommandTest {
    private static final String RESOURCES_PATH = "src/test/resources/json/";

    @Test
    public void convertedToProperJson() throws IOException {
        CreateReturnCommand createReturnCommand = new CreateReturnCommand(
                "123",
                null,
                new ArrayList<>(Arrays.asList(
                        new Item(
                                "some sku", 13, "123", 123.4, "vafds", "dfas", "dfsads",
                                new ArrayList<>(Arrays.asList(new Attribute("attributeLabel1", "some value 1"), new Attribute("attributeLabel2", "some value 2")))
                        ),
                        new Item(
                                "some sku 2", 5, "567.0", 65.9, null, null, null,
                                new ArrayList<>()
                        )
                )),
                "some delivery type",
                ZonedDateTime.of(2020, 5, 15, 12, 27, 54, 0, ZoneId.systemDefault()),
                "some comment",
                "gis mt number",
                new ArrayList<>(Arrays.asList(new Attribute("request level attribute 1", "去手還兒這精象"), new Attribute("request level attribute 1", "мольба отъезд")))
        );
        ObjectMapper mapper = new ObjectMapper();
        String convertedToJson = mapper.writeValueAsString(createReturnCommand);
        String expectedJson = readFile("expectedConsignmentCreateReturnRequest.json");
        assertEquals(expectedJson, convertedToJson);
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + fileName)));
    }

}