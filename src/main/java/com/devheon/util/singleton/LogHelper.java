package com.devheon.util.singleton;

import org.slf4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <pre>
 * Description :
 *     Logger를 싱글톤으로 관리. 프로그램 구동 최초에 setLogger를 통해 로거 설정을 해야함
 * ===============================================
 * Member fields :
 *     org.slf4j.Logger logger
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2019-11-24
 * </pre>
 */
public class LogHelper {
    /* Singleton */
    private static LogHelper instance;
    public static LogHelper getInstance() {
        if(instance == null)
            instance = new LogHelper();
        return instance;
    }
    /* Singleton */

    private Logger logger;

    public Logger getLogger() {
        return this.logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void exception(Throwable e) {
        logger.error("[Exception] " + convertExceptionToString(e));
    }

    private String convertExceptionToString(Throwable e) {
        final StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
