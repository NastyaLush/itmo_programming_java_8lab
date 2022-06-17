package test.laba.client.util;

import java.util.logging.Logger;

import test.laba.common.responses.Response;
import test.laba.common.IO.ObjectWrapper;
import test.laba.common.responses.BasicResponse;
import test.laba.common.util.Values;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.logging.Level;

public class Wrapper {
    public static final Logger LOGGER = Logger.getLogger(Wrapper.class.getName());
    private static final int CAPACITY = 10000;
    private Socket socket;
    private final OutputStream out;
    private final InputStream in;

    public Wrapper(Socket socket) throws IOException {
        LOGGER.setLevel(Level.INFO);
        out = socket.getOutputStream();
        in = socket.getInputStream();
        this.socket = socket;
    }

    public void sent(BasicResponse response) throws IOException {
        LOGGER.fine("response try to sent");
        ByteBuffer byteBuffer = ObjectWrapper.serialize(response);
        out.write(byteBuffer.array());
        LOGGER.fine("response was sent");
        byteBuffer.clear();
        byteBuffer.flip();

    }


    public Response readResponse() throws IOException, ClassNotFoundException {
        LOGGER.fine("response try to read");
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);
        in.read(byteBuffer.array());
        LOGGER.fine("response was given");
        return ObjectWrapper.deserialize(byteBuffer);
    }

    public Map<String, Values> readWithMap() throws IOException, ClassNotFoundException {
        return readResponse().getCollection();
    }

    public void close() throws IOException {
        out.close();
        in.close();
    }

    public Socket getSocket() {
        return socket;
    }
}
