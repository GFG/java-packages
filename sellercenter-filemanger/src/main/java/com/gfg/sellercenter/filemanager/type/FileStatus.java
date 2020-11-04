package com.gfg.sellercenter.filemanager.type;

public enum FileStatus {
    FINISHED,
    PROCESSING,
    ERROR,
    QUEUED,
    EXPIRED,
    CANCELED;

    @Override
    public String toString() {
        return this.name();
    }
}
