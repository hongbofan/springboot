package com.example;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by yfmacmini001 on 2017/3/18.
 */
public class Global {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
