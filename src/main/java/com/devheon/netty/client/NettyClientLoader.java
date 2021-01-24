package com.devheon.netty.client;

import com.devheon.constant.Flag;
import com.devheon.netty.common.constant.ConfigFile;
import com.devheon.netty.common.constant.PropKey;
import com.devheon.netty.common.constant.SystemType;
import com.devheon.netty.common.vo.SystemVO;
import com.devheon.util.PropertyLoader;
import com.devheon.util.singleton.LogHelper;

/**
 * <pre>
 * Description : 
 *     클라이언트 설정에 따른 로드
 * ===============================================
 * Member fields : 
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-21
 * </pre>
 */
public class NettyClientLoader {
    private boolean mIsSSL;
    private SystemVO mServerSystemVO;
    private SystemVO mClientSystemVO;

    /* Getters */
    public boolean isSSL() {
        return this.mIsSSL;
    }
    public SystemVO getServerSystemVO() {
        return this.mServerSystemVO;
    }
    public SystemVO getClientSystemVO() {
        return this.mClientSystemVO;
    }
    /* Getters */

    /* Singleton */
    private static NettyClientLoader instance;
    public static NettyClientLoader getInstance() {
        if(instance == null)
            instance = new NettyClientLoader();
        return instance;
    }
    /* Singleton */

    public void loadClientInformation() {
        LogHelper.getInstance().getLogger().info("Loading client information...");
        try{

            final PropertyLoader propertyLoader = new PropertyLoader(ConfigFile.CLIENT.getAbsolutePath());

            if(propertyLoader.load()) {
                final String ssl, serverIp, serverPort, clientIp, clientPort;

                ssl        = propertyLoader.getPropertyValue(PropKey.SSL);
                serverIp   = propertyLoader.getPropertyValue(PropKey.SERVER_IP);
                serverPort = propertyLoader.getPropertyValue(PropKey.SERVER_PORT);
                clientIp   = propertyLoader.getPropertyValue(PropKey.CLIENT_IP);
                clientPort = propertyLoader.getPropertyValue(PropKey.CLIENT_PORT);

                this.mIsSSL = Flag.Y.toLowerString().equals(ssl);
                this.mServerSystemVO = new SystemVO(SystemType.SERVER, serverIp, serverPort);
                this.mClientSystemVO = new SystemVO(SystemType.CLIENT, clientIp, clientPort);

            } else {
                /* stop */
            }


        } catch (Exception e) {
            LogHelper.getInstance().exception(e);
            /* stop */
        }
        LogHelper.getInstance().getLogger().info("Success to load client information.");
    }
}
