package test.laba.common.IO;

import java.util.logging.Logger;

import test.laba.common.responses.Response;
import test.laba.common.responses.BasicResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;

public final class ObjectWrapper {
    public static final Logger LOGGER = Logger.getLogger(ObjectWrapper.class.getName());

    private ObjectWrapper() {
        LOGGER.setLevel(Level.WARNING);
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
        LOGGER.fine("object was serialized");
        return byteBuffer;
    }

    public static Response deserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        byteBuffer.flip();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Response response = (Response) objectInputStream.readObject();
            objectInputStream.close();
            LOGGER.fine("object was deserialized");
            return response;
        }
    }

    public static BasicResponse serverDeserialize(ByteBuffer byteBuffer) throws IOException, ClassNotFoundException {
        byteBuffer.flip();
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer.array())) {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            BasicResponse response = (BasicResponse) objectInputStream.readObject();
            objectInputStream.close();
            LOGGER.fine("object was deserialized");
            return response;
        }
    }
}
