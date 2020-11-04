package com.gfg.sellercenter.filemanager.request;

import com.gfg.sellercenter.filemanager.type.FileKind;
import com.gfg.sellercenter.filemanager.type.FileStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Getter
@AllArgsConstructor
public class SearchFilesRequest {
    @NonNull
    private final Integer sellerId;
    private final List<Integer> userIds;
    private final FileStatus statuses;
    private final int limit;
    private final int offset;
    private final FileKind kind;
    private final ZonedDateTime createdAtGte;
    private final ZonedDateTime createdAtLte;
    private final List<String> actions;

    public Map<String, String> asMap() {
        Map<String, String> map = new HashMap<>();

        if (getActions() != null) {
            map.put("actions", String.join(",", getActions()));
        }

        if (getCreatedAtGte() != null) {
            map.put("createdAtGte", getCreatedAtGte().toString());
        }

        if (getCreatedAtLte() != null) {
            map.put("createdAtLte", getCreatedAtLte().toString());
        }

        if (getKind() != null) {
            map.put("kind", getKind().toString());
        }

        if (getStatuses() != null) {
            map.put("statuses", getStatuses().toString());
        }

        if (getSellerId() != null) {
            map.put("sellerId", getSellerId().toString());
        }

        if (getUserIds() != null) {
            Set<String> userIds = getUserIds().stream().map(String::valueOf).collect(Collectors.toSet());
            map.put("userIds", String.join(",", userIds));
        }

        map.put("limit", String.valueOf(getLimit()));
        map.put("offset", String.valueOf(getOffset()));

        return map;
    }
}
