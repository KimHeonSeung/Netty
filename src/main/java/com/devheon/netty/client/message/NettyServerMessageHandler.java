package com.devheon.netty.client.message;

import com.devheon.netty.common.vo.RequestVO;
import com.devheon.netty.common.vo.ResponseVO;
import com.devheon.netty.interfaces.IMessageReceiveHandlerService;
import org.json.simple.JSONObject;

/**
 * <pre>
 * Description : 
 *     서버로부터 받은 객체 핸들링
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2020-03-24
 * </pre>
 */
public class NettyServerMessageHandler implements IMessageReceiveHandlerService {
    @Override
    public void handleRequestMessage(RequestVO requestVO) {

    }

    @Override
    public void handleResponseMessage(ResponseVO responseVO) {
        switch (responseVO.getMessageType()) {
            case CLIENT_CONNECT:
                JSONObject messageJson = responseVO.getMessageJson();
                System.out.println(messageJson);
                break;
            case MESSAGE:
                break;
            case CLIENT_INTEGRITY:
                break;
            default:
                break;
        }
    }
}
