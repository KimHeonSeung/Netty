package com.devheon.netty.server.message;

import com.devheon.datastructure.CircularQueue;
import com.devheon.netty.common.vo.RequestVO;
import com.devheon.netty.common.vo.ResponseVO;
import com.devheon.netty.interfaces.IMessageReceiveHandlerService;
import com.devheon.netty.interfaces.IMessageReceiveService;
import com.devheon.netty.server.NettyServer;

/**
 * <pre>
 * Description :
 *     여러 클라이언트로부터 수신되는 메세지를 받는 객체. Queue 관리
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021-03-27
 * </pre>
 */
public class NettyServerMessageReceiver implements IMessageReceiveService {

    private CircularQueue mCircularQueue;

    /* Client Message Handler */
    private IMessageReceiveHandlerService mNettyClientMessageHandler;
    public IMessageReceiveHandlerService getNettyClientMessageHandler() {
        return mNettyClientMessageHandler;
    }
    public void setNettyClientMessageHandler(IMessageReceiveHandlerService nettyClientMessageHandler) {
        this.mNettyClientMessageHandler = nettyClientMessageHandler;
    }
    /* Client Message Handler */

    /* Singleton */
    private static NettyServerMessageReceiver instance;
    public static NettyServerMessageReceiver getInstance() {
        if(instance == null)
            instance = new NettyServerMessageReceiver();
        return instance;
    }
    /* Singleton */

    /* Inner Class Thread */
    private class NettyServerMessageReceiverThread implements Runnable {
        @Override
        public void run() {
            Object item;

            while(!Thread.currentThread().isInterrupted()) {
                item = mCircularQueue.dequeue();
                if(item != null)
                    distributeMessage(item);
                else {
                    try {
                        Thread.sleep(NettyServer.QUEUE_SLEEP_MILLIS);
                    } catch (InterruptedException e) {};
                }

            }
        }
    }
    /* Inner Class Thread */

    private NettyServerMessageReceiver() {
        this.mCircularQueue = new CircularQueue(NettyServer.QUEUE_SIZE);

        Thread messageDequeueThread = new Thread(new NettyServerMessageReceiverThread());
        messageDequeueThread.start();
    }

    @Override
    public void handleReceivingMessage(Object message) {
        this.mCircularQueue.enqueue(message);
    }

    @Override
    public void distributeMessage(Object message) {

        if(message instanceof RequestVO) {
            RequestVO requestVO = (RequestVO) message;

            switch (requestVO.getSystemVO().getSystemType()) {
                case CLIENT:
                    mNettyClientMessageHandler.handleRequestMessage(requestVO);
                    break;
                case SERVER:
                    break;
            }

        } else if(message instanceof ResponseVO) {
            ResponseVO responseVO = (ResponseVO) message;

            switch (responseVO.getSystemVO().getSystemType()) {
                case CLIENT:
                    mNettyClientMessageHandler.handleResponseMessage(responseVO);
                    break;
                case SERVER:
                    break;
            }
        }
    }
}
