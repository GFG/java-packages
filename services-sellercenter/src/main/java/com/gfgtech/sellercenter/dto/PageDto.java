package com.gfgtech.sellercenter.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageDto<T> {

  List<T> items;
  PaginationDto pagination;
}
