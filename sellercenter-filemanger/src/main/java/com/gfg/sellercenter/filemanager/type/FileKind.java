package com.gfg.sellercenter.filemanager.type;

public enum FileKind {
    IMPORT,
    EXPORT;

    @Override
    public String toString() {
        return this.name();
    }
}
