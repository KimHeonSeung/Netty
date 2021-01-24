package com.devheon.database.config.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * <pre>
 * Description : 
 *     데이터베이스 접속 관련 VO
 * ===============================================
 * Member fields : 
 *     String driver
 *     String url
 *     String username
 *     String password
 *     int connectionPoolSize
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2021-01-24
 * </pre>
 */
@Builder
@Getter
@Setter
public class DBConnectionCofigVO {
    private String driver;
    private String url;
    private String username;
    private String password;
    private int connectionPoolSize;
    private String mapperPackage;

    /* toString */
    public String toStringDefault() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
    public String toStringJson() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
    public String toStringMultiline() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    public String toStringNoClass() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }
    public String toStringNoFieldName() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE);
    }
    public String toStringShortPrefix() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    public String toStringSimple() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public String toString() {
        return toStringShortPrefix();
    }
    /* toString */
}
