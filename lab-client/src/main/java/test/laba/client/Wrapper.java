package test.laba.client;

import test.laba.common.dataClasses.Product;
import test.laba.common.util.Response;
import test.laba.common.util.Serealize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Map;

public class Wrapper {
    Socket socket;
    Response response;
    ByteBuffer byteBuffer= ByteBuffer.allocate(3000);
    BufferedReader in;
    OutputStream out;
    public Wrapper(Socket socket) throws IOException {
        this.socket = socket;
        out = socket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public void sent(Response response) throws IOException {
        System.out.println("the request is sent:" + response.getCommand());
            ByteBuffer oute = Serealize.serialize(response);
            out.write(oute.array());
            oute.clear();
            oute.flip();

    }

    public void sentWithArguments(Response response) throws IOException {
        System.out.println("the request is sent:" + response.getCommand());
        ByteBuffer oute = Serealize.serialize(response);
        out.write(oute.array());
        oute.clear();
        oute.flip();
    }


    public Response readResponse() throws IOException {
        System.out.println("read response");
        socket.getInputStream().read(byteBuffer.array());

        return Serealize.deserealize(byteBuffer);
    }
    public String read() throws IOException {
        return readResponse().getCommand();
    }
    public Map readWithMap() throws IOException{
        return readResponse().getCollection();
    }

}
