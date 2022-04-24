package test.laba.client;

import test.laba.common.util.Response;
import test.laba.common.util.Serealize;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Wrapper {
    SocketChannel socketChannel;
    Socket socket;
    Response response;
    byte[] bytes = new byte[3000];
    ByteBuffer byteBuffer= ByteBuffer.allocate(3000);
    BufferedReader in;
    OutputStream out;
    public Wrapper(Socket socket) throws IOException {
        this.socket = socket;
        out = socket.getOutputStream();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
    public void sent(String s){
        System.out.println("the request is sent:" + s);
        response = new Response(s);
        try {
            ByteBuffer oute = Serealize.serialize(response);
            out.write(oute.array());
            oute.clear();
            oute.flip();
        } catch (IOException e) {
            System.out.println(e);
            // TODO: 15.04.2022
        }

    }

    public Response readResponse(){
        try {
            System.out.println("read response");
            socket.getInputStream().read(byteBuffer.array());
        } catch (IOException e) {
            System.out.println(e);
        }
        return Serealize.deserealize(byteBuffer);
    }
    public String read(){
        return readResponse().getMessage();
    }
}
