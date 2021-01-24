package com.devheon.netty.interfaces;

import io.netty.channel.Channel;

/**
 * <pre>
 * Description :
 *     송신 관련 인터페이스
 * ===============================================
 * Methods :
 *     void handleSendingMessage(Channel ch, Object message)
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-28
 * </pre>
 */
public interface IMessageSendService {
    void handleSendingMessage(Channel ch, Object message);
}
