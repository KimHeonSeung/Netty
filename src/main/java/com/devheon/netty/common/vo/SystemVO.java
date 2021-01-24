package com.devheon.netty.common.vo;

import com.devheon.netty.common.constant.SystemType;
import com.devheon.util.singleton.LogHelper;
import com.devheon.util.singleton.StringHelper;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * <pre>
 * Description :
 *     시스템에 대한 설정값을 갖는 VO 객체
 * ===============================================
 * Member fields :
 *    SystemType systemType
 *    String ip
 *    int port
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-21
 * </pre>
 */
public class SystemVO implements Serializable {
    private static final long serialVersionUID = -502134598262099302L;

    private SystemType systemType;
    private String ip;
    private int port;

    /* Getters */
    public SystemType getSystemType() {
        return this.systemType;
    }
    public String getIp() {
        return this.ip;
    }
    public int getPort() {
        return this.port;
    }
    /* Getters */

    public SystemVO(SystemType systemType, String ip, String port) {
        this.systemType = systemType;
        setIp(ip);
        setPort(port);
    }

    private void setIp(String ip) {
        if (StringHelper.getInstance().isEmpty(ip)) {
            try {
                this.ip = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception e) {
                this.ip = "127.0.0.1";
            }
        } else {
            if ("localhost".equalsIgnoreCase(ip)) {
                this.ip = "127.0.0.1";
            } else if (StringHelper.getInstance().isIpFormat(ip)) {
                this.ip = ip;
            } else {
                LogHelper.getInstance().getLogger().error("Invalid ip.");
                System.exit(0);
            }
        }
    }

    private void setPort(String port) {
        if (StringHelper.getInstance().isEmpty(ip)) {
            LogHelper.getInstance().getLogger().error("Invalid port.");
            System.exit(0);
        } else {
            if (StringHelper.getInstance().isPortFormat(port)) {
                this.port = Integer.parseInt(port);
            } else {
                LogHelper.getInstance().getLogger().error("Invalid port.");
                System.exit(0);
            }

        }
    }

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
