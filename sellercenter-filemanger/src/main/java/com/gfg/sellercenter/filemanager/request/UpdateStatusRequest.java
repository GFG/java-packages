package com.gfg.sellercenter.filemanager.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.gfg.sellercenter.filemanager.type.FileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Builder
@Getter
public class UpdateStatusRequest {
    @NonNull
    private final FileStatus status;

    private final JsonNode metaData;
}
