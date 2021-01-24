package com.devheon.netty.client;

import com.devheon.netty.common.constant.ModeType;
import com.devheon.util.singleton.LogHelper;
import com.devheon.util.singleton.StringHelper;
import org.slf4j.LoggerFactory;

public class NettyClient {
    private static ModeType mClientModeType;

    public static final int QUEUE_SIZE = 50000;
    public static final long QUEUE_SLEEP_MILLIS = 500;

    public NettyClient() {
        startClient();
    }

    private void startClient() {
        /* Load Client Information */
        NettyClientLoader.getInstance().loadClientInformation();
        new Thread(new NettyClientInitializer()).start();
    }

    public static void main(String[] args) {
        if(StringHelper.getInstance().isEmpty(args)) {
            LogHelper.getInstance().setLogger(LoggerFactory.getLogger(NettyClient.class));
            LogHelper.getInstance().exception(new Exception("Unknown arguments."));
            return;
        }

        final String argsModeType = args[0].trim();

        if(argsModeType.equals(ModeType.RUN.getValue()))
            mClientModeType = ModeType.RUN;
        else if(argsModeType.equals(ModeType.DEBUG.getValue()))
            mClientModeType = ModeType.DEBUG;
        else
            mClientModeType = ModeType.UNKNOWN;

        LogHelper.getInstance().setLogger(LoggerFactory.getLogger(mClientModeType.getValue()));
        LogHelper.getInstance().getLogger().info((String.format("%s mode set.", mClientModeType.getValue())));

//        switch (mServerModeType) {
//            case RUN:
//                break;
//            case DEBUG:
//                break;
//            case UNKNOWN:
//                break;
//        }

        new NettyClient();
    }
}
