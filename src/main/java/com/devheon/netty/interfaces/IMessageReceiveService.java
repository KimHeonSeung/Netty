package com.devheon.netty.interfaces;

/**
 * <pre>
 * Description : 
 *     수신 관련 인터페이스
 * ===============================================
 * Methods : 
 *     void handleReceivingMessage(Object message)
 *     void distributeMessage(Object message)
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2020-03-27
 * </pre>
 */
public interface IMessageReceiveService {
	void handleReceivingMessage(Object message);
	void distributeMessage(Object message);
}
