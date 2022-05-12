package test.laba.common.util;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public final class Encryption {
    private Encryption() {
    }

    public static String coding(String password) {
        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
            return DatatypeConverter.printHexBinary(sha1.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // TODoooooooooooooooooo
        }
        return null;
    }
}
