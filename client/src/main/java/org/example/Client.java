package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.input.KeyboardInput;
import org.example.netty.ClientInitializer;

public class Client {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Channel channel = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientInitializer())
                    .connect("localhost", 8080).sync().channel();

            new KeyboardInput().process(channel);

            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}