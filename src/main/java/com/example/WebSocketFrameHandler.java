package com.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Created by yfmacmini001 on 2017/3/18.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // TODO Auto-generated method stub
        if (frame instanceof TextWebSocketFrame) {
            String request = ((TextWebSocketFrame)frame).text();
            System.out.println(request);
            //ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.CHINA)));
            Global.group.writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.CHINA)));
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            System.out.println(message);
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Global.group.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Global.group.add(ctx.channel());
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
        // TODO Auto-generated method stub
        //cause.printStackTrace();
        ctx.close(); //出异常的时候关闭channel
    }
}
