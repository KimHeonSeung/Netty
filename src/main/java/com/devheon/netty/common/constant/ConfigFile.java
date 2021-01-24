package com.devheon.netty.common.constant;

/**
 * <pre>
 * Description :
 *     설정파일 경로
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-12
 * </pre>
 */
public enum ConfigFile {

    SERVER("conf/server.properties"),
    CLIENT("conf/client.properties");

    private String absolutePath;
    private ConfigFile(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
