package test.laba.common.util;

import java.io.IOException;
import java.net.InetAddress;

public final class ValidInputDate {
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;
    private static final  int TIME_OUT = 5000;

    private ValidInputDate() {

    }

    public static boolean checkPort(String argument) {
        try {
            int port = getPort(argument);
            if (!(MIN_PORT <= port) || !(port <= MAX_PORT)) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static Integer getPort(String argument) {
        return Integer.valueOf(argument);
    }

    public static boolean checkHost(String argument) throws IOException {
        String ip = argument;
        InetAddress inet = InetAddress.getByName(ip);
        if (inet.isReachable(TIME_OUT)) {
            return true;
        }
        return false;
    }

}
