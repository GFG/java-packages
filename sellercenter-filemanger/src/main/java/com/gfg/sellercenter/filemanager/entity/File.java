package com.gfg.sellercenter.filemanager.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.gfg.sellercenter.filemanager.type.FileKind;
import com.gfg.sellercenter.filemanager.type.FileStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class File {
    @NonNull
    private UUID id;

    @NotBlank
    private String source;

    @NonNull
    private Integer userId;

    @NonNull
    private Integer sellerId;

    private UUID sellerUuid;

    @NotBlank
    private String filePath;

    @NotBlank
    private String fileAction;

    @NotBlank
    private String rawName;

    private JsonNode metaData;

    private String checksum;

    @NonNull
    private FileStatus status;

    @NonNull
    private FileKind kind;

    @NonNull
    private ZonedDateTime expiresAt;

    @NonNull
    private ZonedDateTime createdAt;

    @NonNull
    private ZonedDateTime updatedAt;
}
