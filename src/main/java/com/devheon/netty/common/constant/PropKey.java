package com.devheon.netty.common.constant;

public enum PropKey {
    SSL("ssl", "n"),
    CLIENT_IP("client_ip", "127.0.0.1"),
    CLIENT_PORT("client_port", "10001"),
    SERVER_IP("server_ip", "127.0.0.1"),
    SERVER_PORT("server_port", "10001"),
    DB_DRIVER("db_driver", "org.mariadb.jdbc.Driver"),
    DB_URL("db_url", "jdbc:mariadb://127.0.0.1:3306/netty?serverTimezone=UTC"),
    DB_USERNAME("db_username", "netty"),
    DB_PASSWORD("db_password", "netty"),
    DB_POOLSIZE("db_poolsize", "5"),
    DB_MAPPER_PACKAGE("db_mapper_package", "com.devheon.netty.database.mapper");

    private String key;
    private String defaultValue;
    private PropKey(String key, String defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String getKey() {
        return this.key;
    }
    public String getDefaultValue() {
        return this.defaultValue;
    }
}
