package test.laba.common.IO;

import test.laba.common.responses.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public final class ObjectWrapper {

    private ObjectWrapper() {
    }

    public static ByteBuffer serialize(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(object);

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());

        objectOutputStream.flush();
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return byteBuffer;
    }

    public static Response deserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        byteBuffer.flip();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Response response = (Response) objectInputStream.readObject();
            objectInputStream.close();
            return response;
        }
    }
}