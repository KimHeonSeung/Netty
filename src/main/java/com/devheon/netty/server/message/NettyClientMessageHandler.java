package com.devheon.netty.server.message;

import com.devheon.constant.Flag;
import com.devheon.netty.common.constant.IntegrityFile;
import com.devheon.netty.common.constant.MessageJsonKey;
import com.devheon.netty.common.constant.MessageType;
import com.devheon.netty.common.vo.IntegrityVO;
import com.devheon.netty.common.vo.RequestVO;
import com.devheon.netty.common.vo.ResponseVO;
import com.devheon.netty.interfaces.IMessageReceiveHandlerService;
import com.devheon.netty.server.NettyClientChannelMap;
import com.devheon.netty.server.NettyServerLoader;
import io.netty.channel.Channel;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class NettyClientMessageHandler implements IMessageReceiveHandlerService {

    @Override
    public void handleRequestMessage(RequestVO requestVO) {
        switch (requestVO.getMessageType()) {
            case CLIENT_CONNECT:
                JSONObject messageJson = requestVO.getMessageJson();

                if(!"firstMsg".equals(messageJson.get("msg"))) {
                    /* 검증된 클라이언트인지 test code */

                    JSONObject responseMessageJson = new JSONObject();
                    responseMessageJson.put(MessageJsonKey.REQ_RESULT, Flag.N);

                    ResponseVO responseVO = ResponseVO.builder()
                            .systemVO(NettyServerLoader.getInstance().getSystemVO())
                            .messageType(MessageType.CLIENT_CONNECT)
                            .messageJson(responseMessageJson)
                            .build();

                    Channel unknown = NettyClientChannelMap.getInstace().getClientChannel(requestVO.getSystemVO());
                    NettyServerMessageSender.getInstance().handleSendingMessage(unknown, responseVO);

                    unknown.close();
                    NettyClientChannelMap.getInstace().removeClient(requestVO.getSystemVO());
                    return;
                } else {
                    /* 검증된 클라이언트 */
                    System.out.println(requestVO);

                }

                break;
            case MESSAGE:
                break;
            case CLIENT_INTEGRITY:
                final Channel clientChannel = NettyClientChannelMap.getInstace().getClientChannel(requestVO.getSystemVO());
                final IntegrityVO integrityVO = (IntegrityVO) requestVO.getMessageJson().get(MessageJsonKey.INTEGRITY_VO);
                HashMap<IntegrityFile, String > integrityFileHashCodeMap = integrityVO.getIntegrityFileHashCodeMap();
                for(IntegrityFile integrityFile : integrityFileHashCodeMap.keySet()) {
                    System.out.println(integrityFile.toString());
                    System.out.println(integrityFileHashCodeMap.get(integrityFile));
                }
                break;
        }
    }

    @Override
    public void handleResponseMessage(ResponseVO responseVO) {

    }
}
