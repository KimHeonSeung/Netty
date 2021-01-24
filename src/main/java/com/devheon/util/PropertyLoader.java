package com.devheon.util;

import com.devheon.netty.common.constant.PropKey;
import com.devheon.util.singleton.LogHelper;
import com.devheon.util.singleton.StringHelper;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * <pre>
 * Description :
 *     설정파일 값을 읽는 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2019-11-24
 * </pre>
 */
public class PropertyLoader {
    private String mFileName;
    private Properties mProperties;

    public PropertyLoader(String fileName) {
        this.mFileName = fileName;
    }

    /**
     * <pre>
     * Description
     *     설정파일 로딩
     * ===============================================
     * Parameters
     *
     * Returns
     *     설정파일 로딩 성공 여부
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2019-11-24
     * </pre>
     */
    public boolean load() {
        boolean result = false;
        try {

            if(StringHelper.getInstance().isEmpty(mFileName))
                throw new Exception("properties file name not set.");

            if(mProperties == null)
                mProperties = new Properties();

            try(FileInputStream fis = new FileInputStream(mFileName)) {
                mProperties.load(fis);
                result = true;
            } catch (Exception propsLoadException) {
                throw propsLoadException;
            }

        } catch (Exception e) {
            LogHelper.getInstance().exception(e);
        }

        return result;
    }

    public String getPropertyValue(String key) throws Exception {
        if(mProperties == null)
            throw new Exception("properties not loaded.");

        return mProperties.getProperty(key);
    }
    
    /**
     * <pre>
     * Description
     *     설정파일을 읽어 키에 해당하는 값을 반환
     * ===============================================
     * Parameters
     *     PropKey propKey
     * Returns
     *     PropKey에 해당하는 값이 없다면 기본값 반환
     * Throws
     *     
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-01-24
     * </pre>
     */
    public String getPropertyValue(PropKey propKey) throws Exception {
        if(mProperties == null)
            throw new Exception("properties not loaded.");
        
        return mProperties.getProperty(propKey.getKey(), propKey.getDefaultValue());
    }
}
