package com.gfgtech.sellercenter.client;

import com.gfgtech.sellercenter.dto.CategoryDto;
import com.gfgtech.sellercenter.dto.PageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.UUID;

@FeignClient(value = "sellerCenterClient", primary = false)
public interface SellerCenterClient {

  @GetMapping(value = "${app.seller-center.get-categories}")
  PageDto<CategoryDto> getCategories(
      URI baseUrl,
      @RequestHeader(name = "X-User-Uuid") UUID userUuid,
      @RequestHeader(name = "X-Seller-Uuid") UUID sellerUuid,
      @RequestParam(name = "query", required = false) String query,
      @RequestParam(name = "includeInaccessible", required = false) boolean includeInaccessible,
      @RequestParam(name = "limit", defaultValue = "20") int limit,
      @RequestParam(name = "offset") int offset);

  @GetMapping(value = "${app.seller-center.get-categories}")
  PageDto<CategoryDto> getCategories(
      URI baseUrl,
      @RequestHeader(name = "X-User-Uuid") UUID userUuid,
      @RequestHeader(name = "X-Seller-Uuid") UUID sellerUuid,
      @RequestParam(name = "limit", defaultValue = "20") int limit,
      @RequestParam(name = "offset", defaultValue = "0") int offset);
}
