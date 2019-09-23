package me.minidigger.miniserver.test.netty;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import me.minidigger.miniserver.test.api.Player;
import me.minidigger.miniserver.test.protocol.Packet;
import me.minidigger.miniserver.test.protocol.PacketState;

public class MiniConnection {

    private final ChannelHandlerContext ctx;
    private PacketState state = PacketState.HANDSHAKE;

    private Player player = new Player();

    public MiniConnection(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public PacketState getState() {
        return state;
    }

    public void setState(PacketState state) {
        this.state = state;
    }

    public SocketAddress getRemoteAddress() {
        return ctx.channel().remoteAddress();
    }

    public void sendPacket(Packet packet) {
        ctx.channel().writeAndFlush(packet);
    }

    public Player getPlayer() {
        return player;
    }

    public void initPlayer(String username) {
        player.setName(username);
    }
}