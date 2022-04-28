package test.laba.client;

import test.laba.common.util.Response;
import test.laba.common.util.ObjectWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;

public class Wrapper {
    private Socket socket;
    private final int capaciti = 10000;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(capaciti);
    private BufferedReader in;
    private OutputStream out;

    public Wrapper(Socket socket) throws IOException {
        this.socket = socket;
        out = socket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sent(Response response) throws IOException {
        ByteBuffer oute = ObjectWrapper.serialize(response);
        out.write(oute.array());
        oute.clear();
        oute.flip();

    }


    public Response readResponse() throws IOException, ClassNotFoundException {
        socket.getInputStream().read(byteBuffer.array());
        return ObjectWrapper.deserialize(byteBuffer);
    }

    public Map readWithMap() throws IOException, ClassNotFoundException {
        return readResponse().getCollection();
    }

}
