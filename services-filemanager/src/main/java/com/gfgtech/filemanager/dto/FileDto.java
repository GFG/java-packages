package com.gfgtech.filemanager.dto;

import com.gfgtech.filemanager.enums.FileProcessStatus;
import com.gfgtech.filemanager.enums.FileProcessType;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class FileDto {

  UUID id;
  String countryCode;
  String instanceId;
  FileProcessStatus processStatus;
  Map<String, Object> metadata;
  String source;
  FileProcessType processType;
  String fileAction;
  String checksum;
  String rawName;
  String filePath;
  UUID referencedFileUuid;
}
