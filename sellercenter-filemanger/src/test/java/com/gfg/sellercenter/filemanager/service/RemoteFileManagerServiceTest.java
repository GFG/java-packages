package com.gfg.sellercenter.filemanager.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gfg.sellercenter.filemanager.entity.File;
import com.gfg.sellercenter.filemanager.infra.HttpClientApi;

import com.gfg.sellercenter.filemanager.request.CreateFileRequest;
import com.gfg.sellercenter.filemanager.request.SearchFilesRequest;
import com.gfg.sellercenter.filemanager.request.UpdateStatusRequest;
import com.gfg.sellercenter.filemanager.response.SearchFilesResponse;
import com.gfg.sellercenter.filemanager.type.FileKind;
import com.gfg.sellercenter.filemanager.type.FileStatus;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RemoteFileManagerServiceTest {
    private static final String RESOURCES_PATH = "src/test/resources/json/";
    private static final String HOST = "http://localhost:8080/v1/file";

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetFileById() throws ApiException, IOException, URISyntaxException {
        UUID id = UUID.fromString("621c7698-b623-4600-aa94-f21a84df7d47");
        String url = HOST + "/" + id.toString();

        HttpClientApi client = mock(HttpClientApi.class);
        when(client.get(url)).thenReturn(readFile("singleFile.json"));

        FileManagerServiceInterface service = new RemoteFileManagerService(HOST, client);
        File file = service.getById(id);

        assertNotNull(file);
    }

    @Test
    public void testCreateFile() throws ApiException, IOException {
        CreateFileRequest request = CreateFileRequest.builder()
                .source("orderservice")
                .userId(2)
                .sellerId(12)
                .sellerUuid(UUID.fromString("4e8c4328-1130-4787-8e0a-c94e15f83458"))
                .status(FileStatus.QUEUED)
                .kind(FileKind.IMPORT)
                .fileAction("fileAction")
                .filePath("filePath")
                .checksum("checksum")
                .metaData(new TextNode("test"))
                .rawName("rawName")
                .expiresAt(ZonedDateTime.parse("2021-01-01T01:02:02+01:00"))
                .build();

        HttpClientApi client = mock(HttpClientApi.class);
        when(client.post(HOST, mapper.writeValueAsString(request))).thenReturn(readFile("singleFile.json"));

        FileManagerServiceInterface service = new RemoteFileManagerService(HOST, client);
        File file = service.create(request);
        assertNotNull(file);
    }

    @Test
    public void testUpdateStatus() throws ApiException, IOException {
        UUID id = UUID.fromString("c9326ce6-3a92-4e66-b634-83e3e52286ba");
        UpdateStatusRequest request = UpdateStatusRequest.builder()
                .status(FileStatus.EXPIRED)
                .metaData(new TextNode("test"))
                .build();

        HttpClientApi client = mock(HttpClientApi.class);
        when(client.patch(HOST + "/" + id.toString() + "/status",
                mapper.writeValueAsString(request))).thenReturn(readFile("singleFile.json"));

        FileManagerServiceInterface service = new RemoteFileManagerService(HOST, client);
        File file = service.updateStatus(id, request);
        assertNotNull(file);
    }

    @Test
    public void testSearchFiles() throws ApiException, IOException, URISyntaxException {
        SearchFilesRequest request = SearchFilesRequest.builder()
                .limit(10)
                .offset(0)
                .sellerId(12)
                .build();

        HttpClientApi client = mock(HttpClientApi.class);

        when(client.get(HOST, request.asMap())).thenReturn(readFile("multiFile.json"));

        FileManagerServiceInterface service = new RemoteFileManagerService(HOST, client);
        SearchFilesResponse response = service.search(request);
        assertNotNull(response);
        assertEquals(6, response.getData().size());
        assertEquals(new SearchFilesResponse.Pagination(0, 100, 6),
                response.getPagination());
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + fileName)));
    }
}
