package com.example;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketHandshakeException;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.AttributeKey;

import java.util.List;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Created by yfmacmini001 on 2017/3/18.
 */
public class WebSocketServerProtocolHandler extends WebSocketProtocolHandler {
    public enum ServerHandshakeStateEvent {
        /**
         * The Handshake was complete succesful and so the channel was upgraded to websockets
         */
        HANDSHAKE_COMPLETE
    }

    private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY =
            AttributeKey.valueOf(WebSocketServerHandshaker.class.getName() + ".HANDSHAKER");

    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadLength;
    private final boolean checkStartsWith;

    public WebSocketServerProtocolHandler(String websocketPath) {
        this(websocketPath, null, false);
    }

    public WebSocketServerProtocolHandler(String websocketPath, boolean checkStartsWith) {
        this(websocketPath, null, false, 65536, checkStartsWith);
    }

    public WebSocketServerProtocolHandler(String websocketPath, String subprotocols) {
        this(websocketPath, subprotocols, false);
    }

    public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
        this(websocketPath, subprotocols, allowExtensions, 65536);
    }

    public WebSocketServerProtocolHandler(String websocketPath, String subprotocols,
                                          boolean allowExtensions, int maxFrameSize) {
        this(websocketPath, subprotocols, allowExtensions, maxFrameSize, false);
    }

    public WebSocketServerProtocolHandler(String websocketPath, String subprotocols,
                                          boolean allowExtensions, int maxFrameSize, boolean checkStartsWith) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        maxFramePayloadLength = maxFrameSize;
        this.checkStartsWith = checkStartsWith;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        ChannelPipeline cp = ctx.pipeline();
        if (cp.get(WebSocketServerProtocolHandshakeHandler.class) == null) {
            // Add the WebSocketHandshakeHandler before this one.
            ctx.pipeline().addBefore(ctx.name(), WebSocketServerProtocolHandshakeHandler.class.getName(),
                    new WebSocketServerProtocolHandshakeHandler(websocketPath, subprotocols,
                            allowExtensions, maxFramePayloadLength, checkStartsWith));
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
        if (frame instanceof CloseWebSocketFrame) {
            WebSocketServerHandshaker handshaker = getHandshaker(ctx.channel());
            if (handshaker != null) {
                frame.retain();
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame);
            } else {
                ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
            return;
        }
        super.decode(ctx, frame, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof WebSocketHandshakeException) {
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(cause.getMessage().getBytes()));
            ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.close();
        }
    }

    static WebSocketServerHandshaker getHandshaker(Channel channel) {
        return channel.attr(HANDSHAKER_ATTR_KEY).get();
    }

    static void setHandshaker(Channel channel, WebSocketServerHandshaker handshaker) {
        channel.attr(HANDSHAKER_ATTR_KEY).set(handshaker);
    }

    static ChannelHandler forbiddenHttpRequestResponder() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                if (msg instanceof FullHttpRequest) {
                    ((FullHttpRequest) msg).release();
                    FullHttpResponse response =
                            new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.FORBIDDEN);
                    ctx.channel().writeAndFlush(response);
                } else {
                    ctx.fireChannelRead(msg);
                }
            }
        };
    }
}
