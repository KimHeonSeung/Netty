package com.devheon.netty.common.constant;

public enum IntegrityFile {
    DEFAULT("conf/default"),
    CLIENT_CONFIG("conf/client.properties"),
    SERVER_CONFIG("conf/server.properties");
    private String absolutePath;
    private IntegrityFile(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }
}
