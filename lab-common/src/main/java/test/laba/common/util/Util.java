package test.laba.common.util;

import test.laba.common.IO.Colors;

public final class Util {
    private Util() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }
    public static void toColor(Colors colors, String s) {
        System.out.println(colors + s + " " + Colors.END);
    }

    public static void toColor(String s, Colors colors) {
        System.out.println(colors + s + " " + Colors.END);
    }

}
