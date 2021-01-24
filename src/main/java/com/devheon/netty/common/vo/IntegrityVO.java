package com.devheon.netty.common.vo;

import com.devheon.netty.common.constant.IntegrityFile;
import com.devheon.netty.common.constant.IntegrityType;
import com.devheon.util.singleton.Sha256Helper;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * <pre>
 * Description :
 *     무결성 VO
 * ===============================================
 * Member fields :
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-04-13
 * </pre>
 */
public class IntegrityVO implements Serializable {
    private static final long serialVersionUID = -9110637761684981680L;

    private String ip;
    private IntegrityType integrityType;
    private HashMap<IntegrityFile, String> integrityFileHashCodeMap = new HashMap<>();

    public IntegrityVO(String ip, IntegrityType integrityType) {
        this.ip = ip;
        this.integrityType = integrityType;
    }

    public void setIntegrityFileHashCodeMap() {
        File temporaryIntegrityFile;
        IntegrityFile integrityFile;
        switch (this.integrityType) {
            case SERVER:
                temporaryIntegrityFile = new File(IntegrityFile.SERVER_CONFIG.getAbsolutePath());
                integrityFile = IntegrityFile.SERVER_CONFIG;
                break;
            case CLIENT:
                temporaryIntegrityFile = new File(IntegrityFile.CLIENT_CONFIG.getAbsolutePath());
                integrityFile = IntegrityFile.CLIENT_CONFIG;
                break;
            default:
                temporaryIntegrityFile = new File(IntegrityFile.DEFAULT.getAbsolutePath());
                integrityFile = IntegrityFile.DEFAULT;
                break;
        }

        try {
            final String hashCode = Sha256Helper.getInstance().getHashCode(temporaryIntegrityFile);
            this.integrityFileHashCodeMap.put(integrityFile, hashCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<IntegrityFile, String> getIntegrityFileHashCodeMap() {
        return this.integrityFileHashCodeMap;
    }
}
