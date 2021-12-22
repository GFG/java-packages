package com.gfgtech.translation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

/** @author Claudiu MAN */
@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranslationPayload {
    String language;

    String key;

    String value;
}
