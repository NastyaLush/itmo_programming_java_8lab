package test.laba.common.util;

import test.laba.common.IO.Colors;

public final class Util {
    private Util() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static String toColor(Colors colors, String s) {
        return colors + s + Colors.END;
    }

    public static String toColor(String s, Colors colors) {
        return colors + s + Colors.END;
    }

}
