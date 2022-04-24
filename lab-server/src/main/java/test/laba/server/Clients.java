package test.laba.server;

import test.laba.common.util.Response;
import test.laba.common.util.Serealize;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Clients {
    private SocketChannel socket;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(3000);
    private boolean isHendle = false;
    public Clients(SocketChannel socket){
        this.socket = socket;
    }
    public void sendMessage(Response response){
        try {
            byteBuffer = Serealize.serialize(response);
            socket.write(byteBuffer);
            isHendle = false;
            byteBuffer.clear();
            byteBuffer.flip();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void recieveMessage(){
        try {
            isHendle = false;
            int i = 0;
            if (socket.read(byteBuffer) != 0) {
                while (socket.isOpen() && socket.read(byteBuffer) != -1) {
                    // В случае длительного соединения вам нужно вручную определить, прочитаны ли данные или нет (здесь делается простое суждение: запрос завершен, если он превышает 0 байт)
                    if (byteBuffer.position() > 0) {
                        System.out.println(true);
                        isHendle = true;
                        break;
                    }
                }
            } else {
                BufferedInputStream in =new BufferedInputStream(socket.socket().getInputStream());
                //System.out.println(in.);
                System.out.println("byte =0");
            }
        }
        catch(IOException e){
        e.printStackTrace();
    }
    }

    public boolean isResponse(){
        recieveMessage();
        return isHendle;
    }
    public ByteBuffer getResponse(){
        try {
            return byteBuffer;
        }
        finally {
            byteBuffer.clear();
            byteBuffer.flip();
        }
    }
}
