package com.gfgtech.translation.dto;

import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

/** @author Claudiu MAN */
@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranslationDto {
    List<TranslationPayload> payload;

    public boolean isTranslationPayloadNull() {
        return payload == null;
    }

    public boolean isTranslationPayloadEmpty() {
        return payload.isEmpty();
    }
}
