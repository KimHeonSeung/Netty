package com.devheon.netty.common.vo;

import com.devheon.netty.common.constant.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.simple.JSONObject;

import java.io.Serializable;

/**
 * <pre>
 * Description :
 *     통신 메세지 요청에 대한 결과 객체
 * ===============================================
 * Member fields :
 *     SystemVO systemVO
 *     MessageType messageType
 *     JSONObject messageJson
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-01-05
 * </pre>
 */
@Builder
@Getter
@Setter
public class ResponseVO implements Serializable {
    private static final long serialVersionUID = -2783815310692630622L;

    private SystemVO systemVO;
    private MessageType messageType;
    private JSONObject messageJson;

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
