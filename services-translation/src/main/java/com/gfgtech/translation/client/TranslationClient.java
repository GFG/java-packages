package com.gfgtech.translation.client;

import com.gfgtech.translation.dto.TranslationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/** @author Claudiu MAN */
@FeignClient(value = "translationClient", url = "${app.translation.base-url}", primary = false)
public interface TranslationClient {

    @GetMapping(value = "${app.translation.get-translation-url}")
    TranslationDto getTranslationByLanguageAndNamespace(
            @PathVariable("language") String language,
            @RequestParam("namespace") String namespace,
            @RequestParam(name = "fallback", required = false) boolean fallback);
}
