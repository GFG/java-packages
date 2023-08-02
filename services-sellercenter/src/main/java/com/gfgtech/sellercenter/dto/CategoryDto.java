package com.gfgtech.sellercenter.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {

  Long id;

  Long parentId;

  String name;

  List<String> path;

  List<Long> pathIds;

  List<Long> childrenIds;

  boolean visible;

  String status;

  UUID uuid;
}
