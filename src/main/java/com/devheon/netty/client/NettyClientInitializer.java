package com.devheon.netty.client;

import com.devheon.netty.client.message.NettyClientMessageReceiver;
import com.devheon.netty.client.message.NettyClientMessageSender;
import com.devheon.netty.client.message.NettyServerMessageHandler;
import com.devheon.netty.common.constant.IntegrityType;
import com.devheon.netty.common.constant.MessageJsonKey;
import com.devheon.netty.common.constant.MessageType;
import com.devheon.netty.common.vo.IntegrityVO;
import com.devheon.netty.common.vo.RequestVO;
import com.devheon.netty.common.vo.SystemVO;
import com.devheon.util.singleton.LogHelper;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.json.simple.JSONObject;

/**
 * <pre>
 * Description :
 *     서버와 통신을 여는 객체
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-24
 * </pre>
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> implements Runnable {

    private EventLoopGroup mGroup;

    private final int TRY_CONNECT_COUNT = 5;
    private final int TRY_CONNECT_SECODS = 5 * 1000;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
//		p.addLast(new LoggingHandler(LogLevel.INFO));
        p.addLast("encoder", new ObjectEncoder());
        p.addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(ClassLoader.getSystemClassLoader())));
        p.addLast("handler", new NettyClientInboundHandler());
    }

    @Override
    public void run() {
        try {

            /* Netty Connection */
            SystemVO serverSystemVO = NettyClientLoader.getInstance().getServerSystemVO();
            this.mGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            /* Mina LoggingFilter, MdcInjeckionFilter 등록? */
            bootstrap.group(this.mGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                    .handler(this);

            boolean isRetrying = true;
            int retryCount = 0;

            while (isRetrying) {
                if(retryCount == 0)
                    LogHelper.getInstance().getLogger().info(String.format("Try to connect Server - %s:%d", serverSystemVO.getIp(), serverSystemVO.getPort()));
                else
                    LogHelper.getInstance().getLogger().info(String.format("Retry(%d) to connect Server - %s:%d", retryCount, serverSystemVO.getIp(), serverSystemVO.getPort()));


                try {
                    ChannelFuture channelFuture = bootstrap.connect(serverSystemVO.getIp(), serverSystemVO.getPort()).sync();
                    /* Server Channel Set */
                    NettyClientMessageSender.getInstance().setServerChannel(channelFuture.channel());
                    /* Server Message Handler */
                    NettyClientMessageReceiver.getInstance().setNettyServerMessageHandler(new NettyServerMessageHandler());
                    LogHelper.getInstance().getLogger().info(String.format("Success to connect Server - %s:%d", serverSystemVO.getIp(), serverSystemVO.getPort()));

                    isRetrying = false;

                    /* 내가 검증된 클라이언트임을 증명 */
                    IntegrityVO integrityVO = new IntegrityVO(NettyClientLoader.getInstance().getClientSystemVO().getIp(), IntegrityType.CLIENT);
                    integrityVO.setIntegrityFileHashCodeMap();
                    JSONObject messageJson = new JSONObject();
                    messageJson.put(MessageJsonKey.INTEGRITY_VO, integrityVO);
                    RequestVO requestVO = RequestVO.builder()
                            .messageType(MessageType.CLIENT_INTEGRITY)
                            .messageJson(messageJson)
                            .systemVO(NettyClientLoader.getInstance().getClientSystemVO())
                            .build();

                    NettyClientMessageSender.getInstance().handleSendingMessage(requestVO);

                    channelFuture.channel().closeFuture().sync();
                    System.exit(0);


                } catch (Exception e) {
                    e.printStackTrace();
                    ++retryCount;
                    Thread.sleep(TRY_CONNECT_SECODS);
                }

                if(isRetrying && retryCount == TRY_CONNECT_COUNT) {
                    LogHelper.getInstance().getLogger().error("Cannot connect to Server.");
                    System.exit(0);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
