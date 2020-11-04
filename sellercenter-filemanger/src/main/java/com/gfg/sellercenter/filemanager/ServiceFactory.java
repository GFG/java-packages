package com.gfg.sellercenter.filemanager;

import lombok.NonNull;

import com.gfg.sellercenter.filemanager.infra.HttpClientApi;
import com.gfg.sellercenter.filemanager.service.FileManagerServiceInterface;
import com.gfg.sellercenter.filemanager.service.RemoteFileManagerService;

public class ServiceFactory {
    public static FileManagerServiceInterface createFileManagerService(@NonNull String host) {
        return new RemoteFileManagerService(host, new HttpClientApi());
    }
}
