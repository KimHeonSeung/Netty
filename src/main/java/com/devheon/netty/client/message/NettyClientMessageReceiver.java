package com.devheon.netty.client.message;

import com.devheon.netty.common.vo.RequestVO;
import com.devheon.netty.common.vo.ResponseVO;
import com.devheon.netty.interfaces.IMessageReceiveHandlerService;
import com.devheon.netty.interfaces.IMessageReceiveService;

/**
 * <pre>
 * Description :
 *     클라이언트로부터 수신 받은 메세지를 처리하는 객체 - Singleton
 *     서버로부터 수신 받는 정보는 많지 않으므로 Queue 관리 안함
 * ===============================================
 * Member fields :
 *     IMessageReceiveHandlerService serverMessageHandler
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-24
 * </pre>
 */
public class NettyClientMessageReceiver implements IMessageReceiveService {

    /* Server Message Handler */
    private IMessageReceiveHandlerService nettyServerMessageHandler;
    public IMessageReceiveHandlerService getNettyServerMessageHandler() {
        return nettyServerMessageHandler;
    }
    public void setNettyServerMessageHandler(IMessageReceiveHandlerService nettyServerMessageHandler) {
        this.nettyServerMessageHandler = nettyServerMessageHandler;
    }
    /* Server Message Handler */

    /* Singleton */
    private static NettyClientMessageReceiver instance;
    public static NettyClientMessageReceiver getInstance() {
        if(instance == null)
            instance = new NettyClientMessageReceiver();
        return instance;
    }
    /* Singleton */

    @Override
    public void handleReceivingMessage(Object message) {
        distributeMessage(message);
    }

    @Override
    public void distributeMessage(Object message) {
        if(message instanceof RequestVO) {
            RequestVO requestVO = (RequestVO) message;

            switch (requestVO.getSystemVO().getSystemType()) {
                case SERVER:
                    nettyServerMessageHandler.handleRequestMessage(requestVO);
                    break;
                default:
                    break;

            }

        } else if(message instanceof ResponseVO) {
            ResponseVO responseVO = (ResponseVO) message;

            switch (responseVO.getSystemVO().getSystemType()) {
                case SERVER:
                    nettyServerMessageHandler.handleResponseMessage(responseVO);
                    break;
                default:
                    break;
            }
        }
    }
}
