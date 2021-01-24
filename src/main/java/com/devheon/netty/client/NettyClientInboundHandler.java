package com.devheon.netty.client;

import com.devheon.netty.client.message.NettyClientMessageReceiver;
import com.devheon.util.singleton.LogHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogHelper.getInstance().getLogger().info("channelActive - " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogHelper.getInstance().getLogger().info("channelInactive - " + ctx.channel().remoteAddress());
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyClientMessageReceiver.getInstance().handleReceivingMessage(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogHelper.getInstance().getLogger().info("exceptionCaught " + ctx.channel().remoteAddress() + " - " + cause.getMessage());
    }
}
