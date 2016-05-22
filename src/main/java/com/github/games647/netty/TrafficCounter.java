package com.github.games647.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.atomic.AtomicLong;

public class TrafficCounter extends ChannelDuplexHandler {

    private final AtomicLong incomingBytes = new AtomicLong();
    private final AtomicLong outgoingBytes = new AtomicLong();

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof ByteBuf) {
            int readableBytes = ((ByteBuf) msg).readableBytes();
            outgoingBytes.getAndAdd(readableBytes);
        } else if (msg instanceof ByteBufHolder) {
            int readableBytes = ((ByteBufHolder) msg).content().readableBytes();
            outgoingBytes.getAndAdd(readableBytes);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            int readableBytes = ((ByteBuf) msg).readableBytes();
            incomingBytes.getAndAdd(readableBytes);
        } else if (msg instanceof ByteBufHolder) {
            int readableBytes = ((ByteBufHolder) msg).content().readableBytes();
            incomingBytes.getAndAdd(readableBytes);
        }
    }

    public AtomicLong getIncomingBytes() {
        return incomingBytes;
    }

    public AtomicLong getOutgoingBytes() {
        return outgoingBytes;
    }

    public static void register(TrafficCounter counter, Channel channel) {
        channel.pipeline().addLast(counter);
    }
}
