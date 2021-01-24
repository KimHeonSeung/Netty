package com.devheon.netty.server;

import com.devheon.netty.common.vo.SystemVO;
import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * <pre>
 * Description :
 *     연결된 클라이언트 채널 관리 맵
 * ===============================================
 * Member fields :
 *     HashMap<String, Channel> clientChannelMap
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-27
 * </pre>
 */
public class NettyClientChannelMap {
    private HashMap<String, Channel> clientChannelMap;

    private static NettyClientChannelMap instance;

    public static NettyClientChannelMap getInstace() {
        if (instance == null)
            instance = new NettyClientChannelMap();
        return instance;
    }

    public NettyClientChannelMap() {
        this.clientChannelMap = new HashMap<String, Channel>();
    }

    public HashMap<String, Channel> getClientChannelMap() {
        return this.clientChannelMap;
    }

    public void addClient(String ip, Channel ch) {
        this.clientChannelMap.put(ip, ch);
    }

    public void removeClient(String ip) {
        this.clientChannelMap.remove(ip);
    }

    public void removeClient(SystemVO clientSystemVo) {
        this.clientChannelMap.remove(clientSystemVo.getIp());
    }

    public Channel getClientChannel(String ip) {
        return this.clientChannelMap.get(ip);
    }

    public Channel getClientChannel(SystemVO clientSystemVo) {
        return this.clientChannelMap.get(clientSystemVo.getIp());
    }
}
