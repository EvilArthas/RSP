package org.example.input;

import io.netty.channel.Channel;

public interface UserInput {

    void process(Channel channel);
}
