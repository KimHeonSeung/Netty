package com.devheon.netty.server;

import com.devheon.constant.Flag;
import com.devheon.database.DBConnectionPool;
import com.devheon.netty.common.constant.ConfigFile;
import com.devheon.netty.common.constant.PropKey;
import com.devheon.netty.common.constant.SystemType;
import com.devheon.netty.common.vo.SystemVO;
import com.devheon.util.PropertyLoader;
import com.devheon.util.singleton.LogHelper;

/**
 * <pre>
 * Description : 
 *     서버 설정에 따른 로드
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2020-03-21
 * </pre>
 */
public class NettyServerLoader {
    private boolean mIsSSL;
    private SystemVO mServerSystemVO;

    /* Getters */
    public boolean isSSL() {
        return this.mIsSSL;
    }
    public SystemVO getSystemVO() {
        return this.mServerSystemVO;
    }
    /* Getters */

    /* Singleton */
    private static NettyServerLoader instance;
    public static NettyServerLoader getInstance() {
        if(instance == null)
            instance = new NettyServerLoader();
        return instance;
    }
    /* Singleton */

    /**
     * <pre>
     * Description
     *     서버 정보와 데이터베이스 정보를 로드
     * ===============================================
     * Parameters
     *
     * Returns
     *
     * Throws
     *
     * ===============================================
     *
     * Author : HeonSeung Kim
     * Date   : 2020-03-21
     * </pre>
     */
    public void loadServerInformation() {
        LogHelper.getInstance().getLogger().info("Loading server information...");
        try{

            final PropertyLoader propertyLoader = new PropertyLoader(ConfigFile.SERVER.getAbsolutePath());

            if(propertyLoader.load()) {
                final String ssl, serverIp, serverPort;

                ssl        = propertyLoader.getPropertyValue(PropKey.SSL);
                serverIp   = propertyLoader.getPropertyValue(PropKey.SERVER_IP);
                serverPort = propertyLoader.getPropertyValue(PropKey.SERVER_PORT);

                this.mIsSSL = Flag.Y.toLowerString().equals(ssl);
                this.mServerSystemVO = new SystemVO(SystemType.SERVER, serverIp, serverPort);

                DBConnectionPool.getInstance().setConnectionPool(ConfigFile.SERVER.getAbsolutePath());

            } else {
                /* stop */
            }


        } catch (Exception e) {
            LogHelper.getInstance().exception(e);
            /* stop */
        }

        LogHelper.getInstance().getLogger().info("Success to load server information.");
    }
}
