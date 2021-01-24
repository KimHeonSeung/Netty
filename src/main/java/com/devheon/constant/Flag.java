package com.devheon.constant;

/**
 * <pre>
 * Description :
 *     YES/NO에 대한 Enum
 * ===============================================
 * Member fields :
 *     Y(true, 1, "y", "Y")
 *     N(false, 0, "n", "N")
 *     boolean booleanValue
 *     int intValue
 *     String stringLowerValue
 *     String stringUpperValue
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-12
 * </pre>
 */
public enum Flag {
    Y(true, 1, "y", "Y"),
    N(false, 0, "n", "N");

    private boolean booleanValue;
    private int intValue;
    private String stringLowerValue;
    private String stringUpperValue;

    private Flag(boolean booleanValue, int intValue, String stringLowerValue, String stringUpperValue) {
        this.booleanValue     = booleanValue;
        this.intValue         = intValue;
        this.stringLowerValue = stringLowerValue;
        this.stringUpperValue = stringUpperValue;
    }

    /**
     * <pre>
     * Description
     *     boolean 값 반환
     * ===============================================
     * Parameters
     *     none
     * Returns
     *     Y -> true
     *     N -> false
     * Throws
     *     none
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public boolean toBoolean() { return booleanValue; }

    /**
     * <pre>
     * Description
     *     int 값 반환
     * ===============================================
     * Parameters
     *     none
     * Returns
     *     Y -> 1
     *     N -> 0
     * Throws
     *     none
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public int	   toInt() { return intValue; }

    /**
     * <pre>
     * Description
     *     소문자 문자열 반환
     * ===============================================
     * Parameters
     *     none
     * Returns
     *     Y -> "y"
     *     N -> "n"
     * Throws
     *     none
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public String  toLowerString() { return stringLowerValue; }

    /**
     * <pre>
     * Description
     *     대문자 문자열 반환
     * ===============================================
     * Parameters
     *     none
     * Returns
     *     Y -> "Y"
     *     N -> "N"
     * Throws
     *     none
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-12
     * </pre>
     */
    public String  toUpperString() { return stringUpperValue; }
}
