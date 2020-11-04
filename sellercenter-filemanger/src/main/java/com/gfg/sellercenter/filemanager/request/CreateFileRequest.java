package com.gfg.sellercenter.filemanager.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.gfg.sellercenter.filemanager.type.FileKind;
import com.gfg.sellercenter.filemanager.type.FileStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
public class CreateFileRequest {
    @NotBlank
    private final String source;

    @NonNull
    private final Integer userId;

    @NonNull
    private final Integer sellerId;

    private final UUID sellerUuid;

    @NotBlank
    private final String filePath;

    @NotBlank
    private final String fileAction;

    @NotBlank
    private final String rawName;

    private final JsonNode metaData;

    private final String checksum;

    @NonNull
    private final FileStatus status;

    @NonNull
    private final FileKind kind;

    @NonNull
    private final ZonedDateTime expiresAt;
}
