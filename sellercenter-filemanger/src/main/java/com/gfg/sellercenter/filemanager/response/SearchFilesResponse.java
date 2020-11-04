package com.gfg.sellercenter.filemanager.response;

import com.gfg.sellercenter.filemanager.entity.File;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SearchFilesResponse {
    @NonNull
    private List<File> data;

    @NonNull
    private Pagination pagination;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    public static class Pagination {
        private int offset;
        private int limit;
        private int total;
    }
}
