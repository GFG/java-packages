package com.gfgtech.translation.dto;

import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import org.springframework.util.CollectionUtils;

/** @author Claudiu MAN */
@Getter
@Jacksonized
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TranslationDto {
    List<TranslationPayload> payload;

    public boolean isTranslationPayloadEmpty() {
        return CollectionUtils.isEmpty(payload);
    }
}
