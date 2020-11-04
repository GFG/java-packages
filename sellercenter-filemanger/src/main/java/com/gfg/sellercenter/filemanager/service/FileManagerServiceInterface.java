package com.gfg.sellercenter.filemanager.service;

import com.gfg.sellercenter.filemanager.entity.File;
import com.gfg.sellercenter.filemanager.request.CreateFileRequest;
import com.gfg.sellercenter.filemanager.request.SearchFilesRequest;
import com.gfg.sellercenter.filemanager.request.UpdateStatusRequest;
import com.gfg.sellercenter.filemanager.response.SearchFilesResponse;
import lombok.NonNull;

import java.util.UUID;

public interface FileManagerServiceInterface {
    public File create(@NonNull CreateFileRequest request) throws ApiException;

    public File getById(@NonNull UUID id) throws ApiException;

    public File updateStatus(@NonNull UUID id, @NonNull UpdateStatusRequest request) throws ApiException;

    public SearchFilesResponse search(@NonNull SearchFilesRequest request) throws ApiException;
}
