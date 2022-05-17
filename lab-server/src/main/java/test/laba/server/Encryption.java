package test.laba.server;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class Encryption {
    private Encryption() {
    }

    public static String coding(String password, long count) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        byte[] answer = password.getBytes();
        for (int i = 0; i < count; i++) {
                answer = sha1.digest(answer);
        }
        return DatatypeConverter.printHexBinary(answer);
    }
}
