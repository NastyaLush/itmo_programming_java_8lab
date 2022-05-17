package test.laba.common.util;

import java.io.IOException;
import java.net.InetAddress;

public final class ValidInputDate {
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;
    private static final  int TIME_OUT = 10000;

    private ValidInputDate() {

    }

    public static boolean checkPort(String argument) {
        try {
            int port = getPort(argument);
            return MIN_PORT <= port && port <= MAX_PORT;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Integer getPort(String argument) {
        return Integer.valueOf(argument);
    }

    public static boolean checkHost(String argument) throws IOException {
        InetAddress inet = InetAddress.getByName(argument);
        return inet.isReachable(TIME_OUT);
    }

}
