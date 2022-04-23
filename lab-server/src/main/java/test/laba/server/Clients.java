package test.laba.server;

import test.laba.common.util.Response;
import test.laba.common.util.Serealize;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Clients {
    Socket socket;
    public Clients(Socket socket){
        this.socket = socket;
    }
    public void sendMessage(String message){
        Response response = new Response(message);
        ByteBuffer byteBuffer;
        try {
            byteBuffer = Serealize.serialize(response);
            socket.getOutputStream().write(byteBuffer.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
