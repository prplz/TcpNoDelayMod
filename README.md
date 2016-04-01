# TcpNoDelayMod

A forge mod for minecraft 1.7.10 that sets TCP_NODELAY to true, instead of the regular false. This reduces ingame latency and gives a smoother gameplay experience.

Additionally it removes the setting of IP_TOS, which was also removed in some 1.8.x release of minecraft.

This is achieved by transforming the inner class `net.minecraft.network.NetworkManager$2`.

Before:
```java
protected void initChannel(Channel p_initChannel_1_)
{
    try
    {
        p_initChannel_1_.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
    }
    catch (ChannelException var4)
    {
        ;
    }

    try
    {
        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
    }
    catch (ChannelException var3)
    {
        ;
    }

    p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", var2);
}
```

After:
```java
protected void initChannel(Channel p_initChannel_1_)
{
    try
    {
        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
    }
    catch (ChannelException var3)
    {
        ;
    }

    p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", var2);
}
```
