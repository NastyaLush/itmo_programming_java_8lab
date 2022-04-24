package test.laba.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class Serealize {

    public static ByteBuffer serialize(Object object)  {
        System.out.println(object);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);

            ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

            objectOutputStream.flush();
            objectOutputStream.close();
            byteArrayOutputStream.close();
            System.out.println("serialisation");
            return byteBuffer;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Response deserealize(ByteBuffer byteBuffer){
        System.out.println("deserialization");
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Response response = (Response) objectInputStream.readObject();
            objectInputStream.close();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }
}
