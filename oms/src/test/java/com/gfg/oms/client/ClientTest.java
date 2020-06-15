package com.gfg.oms.client;

import com.fasterxml.jackson.core.JsonParseException;
import com.gfg.oms.Config;
import com.gfg.oms.consignment.request.CreateCommand;
import com.gfg.oms.consignment.response.CreateConsignmentResponse;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ClientTest {

    private static final String RESOURCES_PATH = "src/test/resources/json/";

    @Test(expected = JsonParseException.class)
    public void doCreateConsignmentRequest_withAbsolutelyInvalidJsonResponseFromOms() throws IOException {
        Config config = new Config("whatever url", "some password");
        ConnectionManager connectionManager = Mockito.mock(ConnectionManager.class);
        CreateCommand createCommand = Mockito.mock(CreateCommand.class);

        HttpURLConnection httpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
        InputStream inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(connectionManager.getConnection(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(httpURLConnectionMock);
        Mockito.when(httpURLConnectionMock.getResponseCode()).thenReturn(200);
        Mockito.when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        Mockito.when(connectionManager.getResponseContent(Mockito.any())).thenReturn("some string");

        Client client = new Client(config, connectionManager);
        CreateConsignmentResponse createConsignmentResponse = client.doCreateConsignmentRequest(createCommand);
        System.out.println(createConsignmentResponse);
    }

    @Test
    public void doCreateConsignmentRequest_successful() throws IOException {
        Config config = new Config("whatever url", "some password");
        ConnectionManager connectionManager = Mockito.mock(ConnectionManager.class);
        CreateCommand createCommand = Mockito.mock(CreateCommand.class);

        HttpURLConnection httpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
        InputStream inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(connectionManager.getConnection(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(httpURLConnectionMock);
        Mockito.when(httpURLConnectionMock.getResponseCode()).thenReturn(200);
        Mockito.when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        Mockito.when(connectionManager.getResponseContent(Mockito.any())).thenReturn("{\"OmsSuccessResponse\":true,\"message\":{\"purchase_order_id\":\"1d6d40a5-9e3a-4065-8619-b5bc384b0b51\",\"purchase_order_number\":\"123-25\",\"items\":[]}}");

        Client client = new Client(config, connectionManager);
        CreateConsignmentResponse createConsignmentResponse = client.doCreateConsignmentRequest(createCommand);
        assertTrue(createConsignmentResponse.getIsSuccessful());
        assertEquals("1d6d40a5-9e3a-4065-8619-b5bc384b0b51", createConsignmentResponse.getMessage().getPurchaseOrderId());
        assertTrue(createConsignmentResponse.getErrors().isEmpty());
    }

    @Test
    public void doCreateConsignmentRequest_withErrorFromOms() throws IOException {
        Config config = new Config("whatever url", "some password");
        ConnectionManager connectionManager = Mockito.mock(ConnectionManager.class);
        CreateCommand createCommand = Mockito.mock(CreateCommand.class);

        HttpURLConnection httpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
        InputStream inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(connectionManager.getConnection(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(httpURLConnectionMock);
        Mockito.when(httpURLConnectionMock.getResponseCode()).thenReturn(400);
        Mockito.when(httpURLConnectionMock.getErrorStream()).thenReturn(inputStreamMock);
        Mockito.when(connectionManager.getResponseContent(Mockito.any())).thenReturn("{\"OmsSuccessResponse\":false,\"message\":\"No valid json provided\"}");

        Client client = new Client(config, connectionManager);
        CreateConsignmentResponse createConsignmentResponse = client.doCreateConsignmentRequest(createCommand);
        assertFalse(createConsignmentResponse.getIsSuccessful());
        assertFalse(createConsignmentResponse.getErrors().isEmpty());
        assertEquals("No valid json provided", createConsignmentResponse.getErrors().get(0).getError().getMessage());
    }

    @Test
    public void doCreateConsignmentRequest_errorParsedCorrectlyEvenWithMisleadingStatusCode() throws IOException {
        Config config = new Config("whatever url", "some password");
        ConnectionManager connectionManager = Mockito.mock(ConnectionManager.class);
        CreateCommand createCommand = Mockito.mock(CreateCommand.class);

        HttpURLConnection httpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
        InputStream inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(connectionManager.getConnection(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(httpURLConnectionMock);
        Mockito.when(httpURLConnectionMock.getResponseCode()).thenReturn(200);
        Mockito.when(httpURLConnectionMock.getErrorStream()).thenReturn(inputStreamMock);
        Mockito.when(connectionManager.getResponseContent(Mockito.any())).thenReturn("{\"OmsSuccessResponse\":false,\"message\":\"some error message, but with status code 200\"}");

        Client client = new Client(config, connectionManager);
        CreateConsignmentResponse createConsignmentResponse = client.doCreateConsignmentRequest(createCommand);
        assertFalse(createConsignmentResponse.getIsSuccessful());
        assertFalse(createConsignmentResponse.getErrors().isEmpty());
        assertEquals("some error message, but with status code 200", createConsignmentResponse.getErrors().get(0).getError().getMessage());
    }

    @Test
    public void doCreateConsignmentRequest_successfulWithLotOfDataAndWithPurchaseOrderNumber() throws IOException {
        Config config = new Config("whatever url", "some password");
        ConnectionManager connectionManager = Mockito.mock(ConnectionManager.class);
        CreateCommand createCommand = Mockito.mock(CreateCommand.class);

        HttpURLConnection httpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
        InputStream inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(connectionManager.getConnection(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(httpURLConnectionMock);
        Mockito.when(httpURLConnectionMock.getResponseCode()).thenReturn(200);
        Mockito.when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        Mockito.when(connectionManager.getResponseContent(Mockito.any())).thenReturn(readFile("successfulResponseForCreateConsignmentRequest.json"));

        Client client = new Client(config, connectionManager);
        CreateConsignmentResponse createConsignmentResponse = client.doCreateConsignmentRequest(createCommand);
        assertTrue(createConsignmentResponse.getIsSuccessful());
        assertEquals("MPG31995", createConsignmentResponse.getMessage().getPurchaseOrderId());
        assertEquals("MPG31995_1", createConsignmentResponse.getMessage().getItems().get(0).getPurchaseOrderItemId());
        assertEquals("MP002XW10FPVINXS", createConsignmentResponse.getMessage().getItems().get(0).getSimpleSku());
        assertTrue(createConsignmentResponse.getErrors().isEmpty());
    }

    @Test
    public void doCreateConsignmentRequest_successfulWithLotOfDataAndWithoutPurchaseOrderNumber() throws IOException {
        Config config = new Config("whatever url", "some password");
        ConnectionManager connectionManager = Mockito.mock(ConnectionManager.class);
        CreateCommand createCommand = Mockito.mock(CreateCommand.class);

        HttpURLConnection httpURLConnectionMock = Mockito.mock(HttpURLConnection.class);
        InputStream inputStreamMock = Mockito.mock(InputStream.class);

        Mockito.when(connectionManager.getConnection(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(httpURLConnectionMock);
        Mockito.when(httpURLConnectionMock.getResponseCode()).thenReturn(200);
        Mockito.when(httpURLConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        Mockito.when(connectionManager.getResponseContent(Mockito.any())).thenReturn(readFile("successfulResponseForCreateConsignmentRequestWithoutPON.json"));

        Client client = new Client(config, connectionManager);
        CreateConsignmentResponse createConsignmentResponse = client.doCreateConsignmentRequest(createCommand);
        assertTrue(createConsignmentResponse.getIsSuccessful());
        assertEquals("MPG31995", createConsignmentResponse.getMessage().getPurchaseOrderId());
        assertEquals("MPG31995_1", createConsignmentResponse.getMessage().getItems().get(0).getPurchaseOrderItemId());
        assertEquals("MP002XW10FPVINXS", createConsignmentResponse.getMessage().getItems().get(0).getSimpleSku());
        assertTrue(createConsignmentResponse.getErrors().isEmpty());
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + fileName)));
    }
}
