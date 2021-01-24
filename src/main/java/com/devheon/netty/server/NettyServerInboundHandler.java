package com.devheon.netty.server;

import com.devheon.netty.server.message.NettyServerMessageReceiver;
import com.devheon.util.singleton.LogHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;

public class NettyServerInboundHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogHelper.getInstance().getLogger().debug("channelActive - " + ctx.channel().remoteAddress());
        String ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        NettyClientChannelMap.getInstace().addClient(ip, ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogHelper.getInstance().getLogger().info("channelInactive - " + ctx.channel().remoteAddress());
        String ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
        NettyClientChannelMap.getInstace().removeClient(ip);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyServerMessageReceiver.getInstance().handleReceivingMessage(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogHelper.getInstance().getLogger().debug("exceptionCaught " + ctx.channel().remoteAddress() + " - " + cause.getMessage());
    }
}
