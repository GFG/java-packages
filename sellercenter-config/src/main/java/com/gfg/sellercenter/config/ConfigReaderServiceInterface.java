package com.gfg.sellercenter.config;

import java.io.IOException;

public interface ConfigReaderServiceInterface {
    ConfigRecord getByFolderAndPath(String folder, String path) throws IOException;
}
