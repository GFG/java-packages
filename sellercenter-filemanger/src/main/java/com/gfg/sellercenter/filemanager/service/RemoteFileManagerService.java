package com.gfg.sellercenter.filemanager.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gfg.sellercenter.filemanager.entity.File;
import com.gfg.sellercenter.filemanager.infra.HttpClientApi;
import com.gfg.sellercenter.filemanager.request.CreateFileRequest;
import com.gfg.sellercenter.filemanager.request.SearchFilesRequest;
import com.gfg.sellercenter.filemanager.request.UpdateStatusRequest;
import com.gfg.sellercenter.filemanager.response.SearchFilesResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.*;

@AllArgsConstructor
public class RemoteFileManagerService implements FileManagerServiceInterface {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule());
    }

    @NonNull
    private final String host;

    @NonNull
    private final HttpClientApi client;

    @Override
    public File create(@NonNull CreateFileRequest request) throws ApiException {
        try {
            String response = client.post(host, mapper.writeValueAsString(request));
            return mapper.readValue(response, File.class);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), e);
        }
    }

    @Override
    public File getById(@NonNull UUID id) throws ApiException {
        try {
            String response = client.get(host + "/" + id.toString());
            return mapper.readValue(response, File.class);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), e);
        }
    }

    @Override
    public File updateStatus(@NonNull UUID id, @NonNull UpdateStatusRequest request) throws ApiException {
        try {
            String response = client.patch(host + "/" + id.toString() + "/status", mapper.writeValueAsString(request));
            return mapper.readValue(response, File.class);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), e);
        }
    }

    @Override
    public SearchFilesResponse search(@NonNull SearchFilesRequest request) throws ApiException {
        try {
            String response = client.get(host, request.asMap());
            return mapper.readValue(response, SearchFilesResponse.class);
        } catch (Exception e) {
            throw new ApiException(e.getMessage(), e);
        }
    }
}
