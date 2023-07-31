package com.gfgtech.filemanager.dto;

import com.gfgtech.filemanager.enums.FileProcessStatus;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;
import java.util.UUID;

@Value
@Jacksonized
@EqualsAndHashCode
@Builder(toBuilder = true)
public class FileUpdateDto {

  FileProcessStatus processStatus;

  Map<String, Object> metadata;

  UUID referencedFileUuid;

  String rawName;
}
