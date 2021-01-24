package com.devheon.netty.server;

import com.devheon.netty.common.vo.SystemVO;
import com.devheon.netty.server.message.NettyClientMessageHandler;
import com.devheon.netty.server.message.NettyServerMessageReceiver;
import com.devheon.util.singleton.LogHelper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * <pre>
 * Description : 
 *     클라이언트와 통신을 여는 클래스 <br>
 *     포트를 여는것은 스레드 작업으로, 직렬화된 객체 통신을 준비한다.
 * ===============================================
 * Member fields : 
 *     
 * ===============================================
 * 
 * Author : HeonSeung Kim
 * Date   : 2020-03-26
 * </pre>
 */
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> implements Runnable {

    private EventLoopGroup mBossGroup;
    private EventLoopGroup mWorkerGroup;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
        p.addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())));
        p.addLast("encoder", new ObjectEncoder());
        p.addLast("handler", new NettyServerInboundHandler());
    }

    @Override
    public void run() {

        try {
            /* Netty Connection */
            SystemVO serverSystemVO = NettyServerLoader.getInstance().getSystemVO();
            this.mBossGroup = new NioEventLoopGroup(1);
            this.mWorkerGroup = new NioEventLoopGroup();

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /* Mina LoggingFilter, MdcInjeckionFilter 등록? */
            serverBootstrap.group(mBossGroup, mWorkerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                    .childHandler(this);
            Channel channel = serverBootstrap.bind(serverSystemVO.getIp(), serverSystemVO.getPort()).sync().channel();
            LogHelper.getInstance().getLogger().info(String.format("Success to open %s:%d", serverSystemVO.getIp(), serverSystemVO.getPort()));
            /* 채널이 열리면 클라이언트 핸들러 등록 */
            NettyServerMessageReceiver.getInstance().setNettyClientMessageHandler(new NettyClientMessageHandler());
            channel.closeFuture().sync();
        } catch (Exception e) {

        }
    }
}
