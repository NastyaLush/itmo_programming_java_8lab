package test.laba.client;

import test.laba.common.util.Response;
import test.laba.common.util.Serealize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Wrapper {
    SocketChannel socketChannel;
    Response response;
    ByteBuffer byteBuffer = ByteBuffer.allocate(16000);
    ByteArrayOutputStream byteArrayOutputStream;
    public Wrapper(SocketChannel socketChannel){
        this.socketChannel = socketChannel;
        //this.byteBuffer = ByteBuffer.allocate(200);
    }
    public void sent(String s){
        System.out.println("the request is sent:" + s);
        response = new Response(s);
        try {
            byteBuffer = Serealize.serialize(response);
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            byteBuffer.flip();
            /*socketChannel.read(byteBuffer);
            System.out.println(byteBuffer + "dde");*/
            /*byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }*/
        } catch (IOException e) {
            System.out.println(e);
            // TODO: 15.04.2022
        }

    }

    public Response readResponse(){
        try {
            System.out.println("read response"+ byteBuffer);
            //socketChannel.socket().getInputStream().read(byteBuffer.array());
            socketChannel.read(byteBuffer);
            System.out.println(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Response("bla bla") ;//Serealize.deserealize(byteBuffer);
    }
    public String read(){
        return readResponse().getMessage();
    }
}
