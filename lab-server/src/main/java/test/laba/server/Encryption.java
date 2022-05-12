package test.laba.server;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class Encryption {
    private Encryption() {
    }

    public static String coding(String password) throws NoSuchAlgorithmException {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            return DatatypeConverter.printHexBinary(sha1.digest(password.getBytes()));
    }
}
