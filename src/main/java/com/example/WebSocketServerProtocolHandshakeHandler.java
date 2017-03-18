package com.example;

import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.ssl.SslHandler;

import static io.netty.handler.codec.http.HttpVersion.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpMethod.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
/**
 * Created by yfmacmini001 on 2017/3/18.
 */
public class WebSocketServerProtocolHandshakeHandler extends ChannelInboundHandlerAdapter {
    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    private final boolean checkStartsWith;

    WebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols,
                                            boolean allowExtensions, int maxFrameSize, boolean checkStartsWith) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        maxFramePayloadSize = maxFrameSize;
        this.checkStartsWith = checkStartsWith;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        final FullHttpRequest req = (FullHttpRequest) msg;
        if (checkStartsWith) {
            if (!req.getUri().startsWith(websocketPath)) {
                ctx.fireChannelRead(msg);
                return;
            }
        } else if (!req.getUri().equals(websocketPath)) {
            ctx.fireChannelRead(msg);
            return;
        }

        try {
            if (req.getMethod() != GET) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
                return;
            }

            final WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    getWebSocketLocation(ctx.pipeline(), req, websocketPath), subprotocols,
                    allowExtensions, maxFramePayloadSize);
            final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                final ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
                handshakeFuture.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if (!future.isSuccess()) {
                            ctx.fireExceptionCaught(future.cause());
                        } else {
                            ctx.fireUserEventTriggered(
                                    WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                        }
                    }
                });
                WebSocketServerProtocolHandler.setHandshaker(ctx.channel(), handshaker);
                ctx.pipeline().replace(this, "WS403Responder",
                        WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
            }
        } finally {
            req.release();
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        return protocol + "://" + req.headers().get(Names.HOST) + path;
    }
}
