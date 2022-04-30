package test.laba.client.util;

import test.laba.common.responses.Response;
import test.laba.common.IO.ObjectWrapper;
import test.laba.common.util.Values;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;

public class Wrapper {
    private final int capacity = 10000;
    private final OutputStream out;
    private final InputStream in;

    public Wrapper(Socket socket) throws IOException {
        out = socket.getOutputStream();
        in = socket.getInputStream();
    }

    public void sent(Response response) throws IOException {
        ByteBuffer oute = ObjectWrapper.serialize(response);
        out.write(oute.array());
        oute.clear();
        oute.flip();

    }


    public Response readResponse() throws IOException, ClassNotFoundException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        in.read(byteBuffer.array());
        return ObjectWrapper.deserialize(byteBuffer);
    }

    public Map<String, Values> readWithMap() throws IOException, ClassNotFoundException {
        return readResponse().getCollection();
    }

    public void close() throws IOException {
        out.close();
        in.close();
    }
}
