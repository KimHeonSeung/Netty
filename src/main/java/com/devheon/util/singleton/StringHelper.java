package com.devheon.util.singleton;

import org.apache.commons.validator.routines.InetAddressValidator;

/**
 * <pre>
 * Description :
 *     문자열 관련 유틸 - Singleton
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2019-11-24
 * </pre>
 */
public class StringHelper {
    /* Singleton */
    private static StringHelper instance;
    public static StringHelper getInstance() {
        if(instance == null)
            instance = new StringHelper();
        return instance;
    }
    /* Singleton */

    private final String IP_ALL = "0.0.0.0";

    /**
     * <pre>
     * Description
     *     문자열이 비어있는지 체크
     * ===============================================
     * Parameters
     *     String target
     * Returns
     *     boolean : 비어있다면 true
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-11-24
     * </pre>
     */
    public boolean isEmpty(String target) {
        return (target == null || target.length() == 0) ? true : false;
    }

    /**
     * <pre>
     * Description
     *     문자열 배열이 비어있는지 체크
     * ===============================================
     * Parameters
     *     String[] target
     * Returns
     *     boolean : 비어있다면 true
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-11-24
     * </pre>
     */
    public boolean isEmpty(String[] target) {
        return (target == null || target.length == 0) ? true : false;
    }

    /**
     * <pre>
     * Description
     *     문자열이 IP 형식인지 체크
     * ===============================================
     * Parameters
     *     String target
     * Returns
     *     boolean : 문자열이 IP 형식이면 true
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-11-24
     * </pre>
     */
    public boolean isIpFormat(String target) {
        return !IP_ALL.equals(target) && InetAddressValidator.getInstance().isValidInet4Address(target);
    }

    /**
     * <pre>
     * Description
     *     문자열이 PORT 형식인지 체크
     * ===============================================
     * Parameters
     *     String target
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-11-24
     * </pre>
     */
    public boolean isPortFormat(String target) {
        boolean result = false;
        try {
            int port = Integer.parseInt(target);
            if(port > 0 && port <= 65535)
                result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
