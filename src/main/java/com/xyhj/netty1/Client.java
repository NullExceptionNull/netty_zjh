package com.xyhj.netty1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by zhangjianghong on 2017/6/1.
 */
public class Client {
     public static void main(String[] args)throws Exception {
        EventLoopGroup pGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(pGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture cf1 = bootstrap.connect("127.0.0.1",8765).sync();
       //
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("hello netty".getBytes()));
         cf1.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
    }
}
