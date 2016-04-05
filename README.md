# TcpNoDelayMod

A forge mod for minecraft 1.7.2, 1.7.10 and 1.8 that sets TCP_NODELAY to true, instead of the regular false. This reduces ingame latency and gives a smoother gameplay experience.

This mod is not needed in 1.8.8 and later as minecraft already has this change.

The mod transforms an inner class of `net.minecraft.network.NetworkManager`, essentially doing the following:
```diff
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
-        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
+        p_initChannel_1_.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(true));
     }
     catch (ChannelException var3)
     {
         ;
     }

     p_initChannel_1_.pipeline().addLast("timeout", new ReadTimeoutHandler(20)).addLast("splitter", new MessageDeserializer2()).addLast("decoder", new MessageDeserializer(NetworkManager.field_152462_h)).addLast("prepender", new MessageSerializer2()).addLast("encoder", new MessageSerializer(NetworkManager.field_152462_h)).addLast("packet_handler", var2);
 }
```
