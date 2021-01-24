package com.devheon.netty.server.message;

import com.devheon.netty.interfaces.IMessageSendService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * <pre>
 * Description :
 *     서버 메세지 전송자
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-27
 * </pre>
 */
public class NettyServerMessageSender implements IMessageSendService {
    /* Singleton */
    private static NettyServerMessageSender instance;
    public static NettyServerMessageSender getInstance() {
        if(instance == null)
            instance = new NettyServerMessageSender();
        return instance;
    }
    /* Singleton */

    @Override
    public void handleSendingMessage(Channel ch, Object message) {
        ChannelFuture channelFuture = ch.writeAndFlush(message);
        channelFuture.awaitUninterruptibly();

        /* recursive */
        if(!channelFuture.isSuccess())
            handleSendingMessage(ch, message);
    }
}
