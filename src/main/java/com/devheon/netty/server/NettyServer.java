package com.devheon.netty.server;

import com.devheon.netty.common.constant.ModeType;
import com.devheon.util.singleton.LogHelper;
import com.devheon.util.singleton.StringHelper;
import org.slf4j.LoggerFactory;

public class NettyServer {

    private static ModeType mServerModeType;

    public static final int QUEUE_SIZE = 50000;
    public static final long QUEUE_SLEEP_MILLIS = 500;

    public NettyServer() {
        startServer();
    }

    private void startServer() {
        /* Load Server Information */
        NettyServerLoader.getInstance().loadServerInformation();
        /* Open Socket Port */
        new Thread(new NettyServerInitializer()).start();
    }

    public static void main(String[] args) {
        if(StringHelper.getInstance().isEmpty(args)) {
            LogHelper.getInstance().setLogger(LoggerFactory.getLogger(NettyServer.class));
            LogHelper.getInstance().exception(new Exception("Unknown arguments."));
            return;
        }

        final String argsModeType = args[0].trim();

        if(argsModeType.equals(ModeType.RUN.getValue()))
            mServerModeType = ModeType.RUN;
        else if(argsModeType.equals(ModeType.DEBUG.getValue()))
            mServerModeType = ModeType.DEBUG;
        else
            mServerModeType = ModeType.UNKNOWN;

        LogHelper.getInstance().setLogger(LoggerFactory.getLogger(mServerModeType.getValue()));
        LogHelper.getInstance().getLogger().info((String.format("%s mode set.", mServerModeType.getValue())));

//        switch (mServerModeType) {
//            case RUN:
//                break;
//            case DEBUG:
//                break;
//            case UNKNOWN:
//                break;
//        }

        new NettyServer();
    }
}
