package com.devheon.netty.client.message;

import com.devheon.datastructure.CircularQueue;
import com.devheon.netty.client.NettyClient;
import com.devheon.netty.interfaces.IMessageSendService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * <pre>
 * Description :
 *     다양한 작업의 결과를 클라이언트로 송신하는 객체 - Singleton
 *     Queue 처리
 * ===============================================
 * Member fields :
 *     CircularQueue mCircularQueue
 *     Channel mServerChannel
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-24
 * </pre>
 */
public class NettyClientMessageSender implements IMessageSendService {

    private CircularQueue mCircularQueue;
    private Channel mServerChannel = null;

    public void setServerChannel(Channel serverChannel) {
        this.mServerChannel = serverChannel;
    }

    /* Singleton */
    private static NettyClientMessageSender instance;
    public static NettyClientMessageSender getInstance() {
        if(instance == null)
            instance = new NettyClientMessageSender();
        return instance;
    }
    /* Singleton */

    /* Inner Class Thread */
    private class NettyClientMessageSenderThread implements Runnable {
        @Override
        public void run() {
            Object item;
            while(!Thread.currentThread().isInterrupted()) {
                item = mCircularQueue.dequeue();
                if(item != null)
                    sendMessage(item);
                else {
                    try {
                        Thread.sleep(NettyClient.QUEUE_SLEEP_MILLIS);
                    } catch (InterruptedException e) {};
                }
            }
        }
    }
    /* Inner Class Thread */

    private NettyClientMessageSender() {
        this.mCircularQueue = new CircularQueue(NettyClient.QUEUE_SIZE);
        Thread messageDequeueThread = new Thread(new NettyClientMessageSenderThread());
        messageDequeueThread.start();
    }

    private void sendMessage(Object item) {
        if(mServerChannel != null) {
            ChannelFuture cf = mServerChannel.writeAndFlush(item);
            cf.awaitUninterruptibly();
            if(!cf.isSuccess())
                sendMessage(item);
        }
    }

    @Override
    public void handleSendingMessage(Channel ch, Object message) {
        this.mCircularQueue.enqueue(message);
    }
    public void handleSendingMessage(Object message) {
        handleSendingMessage(mServerChannel, message);
    }
}
