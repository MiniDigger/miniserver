package me.minidigger.miniserver.test.api;

import me.minidigger.miniserver.test.protocol.PacketState;
import me.minidigger.miniserver.test.protocol.server.ServerHandshake;
import me.minidigger.miniserver.test.protocol.server.ServerStatusRequest;
import me.minidigger.miniserver.test.netty.MiniConnection;

public class Client {

    private String username;
    private MiniConnection connection;

    public Client(String username) {
        this.username = username;
    }

    public void start() {
        new Thread(() -> {
            while (connection == null) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            doServerListPing();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }, "ClientThread").start();
    }

    public void doServerListPing() {
        ServerHandshake handshake = new ServerHandshake();
        handshake.setProtocolVersion(-1);
        handshake.setServerAddress("localhost");
        handshake.setServerPort((short) 25565);
        handshake.setNextState(PacketState.STATUS);
        connection.sendPacket(handshake);
        connection.setState(PacketState.STATUS);

        ServerStatusRequest statusRequest = new ServerStatusRequest();
        connection.sendPacket(statusRequest);
    }

    public void setConnection(MiniConnection connection) {
        this.connection = connection;
    }
}
