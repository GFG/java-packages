package com.gfgtech.filemanager.client;

import com.gfgtech.filemanager.dto.FileDto;
import com.gfgtech.filemanager.dto.FileUpdateDto;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Client used for sending requests to FileManager. The url should be defined in the app.file-manager.endpoint
 * property
 */
@FeignClient(value = "fileManagerClient", url = "${app.file-manager.endpoint}", primary = false)
public interface FileManagerClient {

  String SELLER_UUID = "X-Seller-Uuid";
  String USER_UUID = "X-User-Uuid";
  String INSTANCE = "X-Instance";
  String CONTEXT = "X-Context";

  /***
   * Get file by uuid
   * @param sellerUuid the given seller uuid
   * @param userUuid the given user uuid
   * @param instance the given instance
   * @param uuid the given file  Uuid
   *
   * @return file information
   */
  @GetMapping(value = "{uuid}")
  FileDto getFileByUuid(
          @RequestHeader(value = SELLER_UUID, required = false) UUID sellerUuid,
          @RequestHeader(USER_UUID) UUID userUuid,
          @RequestHeader(INSTANCE) String instance,
          @RequestHeader(CONTEXT) String context,
          @PathVariable("uuid") UUID uuid);

  /**
   * Create registered file request on file-manger service.
   *
   * @param sellerUuid the given seller uuid
   * @param userUuid the given user uuid
   * @param instance the given instance
   * @param fileDTO the given file request
   * @return Registered file
   */
  @PostMapping
  FileDto createFile(
          @RequestHeader(value = SELLER_UUID, required = false) UUID sellerUuid,
          @RequestHeader(USER_UUID) UUID userUuid,
          @RequestHeader(INSTANCE) String instance,
          @RequestHeader(CONTEXT) String context,
          @RequestBody FileDto fileDTO);

  /**
   * Update file status.
   *
   * @param sellerUuid the given seller uuid
   * @param userUuid the given user uuid
   * @param instance the given instance
   * @param uuid the given file uuid
   * @param fileUpdateDto the given update body
   * @return updated file status
   */
  @PatchMapping(value = "{uuid}")
  FileDto updateFile(
          @RequestHeader(value = SELLER_UUID, required = false) UUID sellerUuid,
          @RequestHeader(USER_UUID) UUID userUuid,
          @RequestHeader(INSTANCE) String instance,
          @RequestHeader(CONTEXT) String context,
          @PathVariable("uuid") UUID uuid,
          @RequestBody FileUpdateDto fileUpdateDto);

  /**
   * Upload file to file manager service.
   *
   * @param sellerUuid the given seller uuid
   * @param userUuid the given user uuid
   * @param instance the given instance
   * @param id the given file id
   * @param file the uploaded file
   */
  @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  FileDto uploadFile(
          @RequestHeader(value = SELLER_UUID, required = false) UUID sellerUuid,
          @RequestHeader(USER_UUID) UUID userUuid,
          @RequestHeader(INSTANCE) String instance,
          @RequestHeader(CONTEXT) String context,
          @PathVariable("id") UUID id,
          @RequestPart(value = "file") MultipartFile file);

  /** Download file by given fileId */
  @GetMapping(value = "download/{id}")
  Response downloadFile(
          @RequestHeader(value = SELLER_UUID, required = false) UUID sellerUuid,
          @RequestHeader(USER_UUID) UUID userUuid,
          @RequestHeader(INSTANCE) String instance,
          @RequestHeader(CONTEXT) String context,
          @PathVariable("id") UUID id);

  /** Get file by uuid */
  @GetMapping(value = "{uuid}")
  FileDto getFile(
          @RequestHeader(value = SELLER_UUID, required = false) UUID sellerUuid,
          @RequestHeader(USER_UUID) UUID userUuid,
          @RequestHeader(INSTANCE) String instance,
          @RequestHeader(CONTEXT) String context,
          @PathVariable("uuid") UUID uuid);
}
