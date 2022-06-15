package test.laba.client.frontEnd.frames.local;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Local {
    public static final String RUSSIAN = "russian";
    public static final String NORWEGIAN = "norwegian";
    public static final String SPANISH = "spanish";
    public static final String FRANCE = "france";
    public static final HashMap<String, ResourceBundle> LOCALS;
    private static final ResourceBundle RESOURCE_BUNDLE_DEFAULT = ResourceBundle.getBundle("lang");
    private static final ResourceBundle RESOURCE_BUNDLE_NORWEGIAN = ResourceBundle.getBundle("lang", new Locale("nb", "NO"));
    private static final ResourceBundle RESOURCE_BUNDLE_SPANISH = ResourceBundle.getBundle("lang", new Locale("es", "NI"));
    private static final ResourceBundle RESOURCE_BUNDLE_FRANCE = ResourceBundle.getBundle("lang", new Locale("fr", "FR"));
    private static final ResourceBundle RESOURCE_BUNDLE_RUSSIAN = ResourceBundle.getBundle("lang", new Locale("ru", "RU"));

    static {
        LOCALS = new HashMap<>();
            LOCALS.put(RUSSIAN, RESOURCE_BUNDLE_RUSSIAN);
            LOCALS.put(NORWEGIAN, RESOURCE_BUNDLE_NORWEGIAN);
            LOCALS.put(SPANISH, RESOURCE_BUNDLE_SPANISH);
            LOCALS.put(FRANCE, RESOURCE_BUNDLE_FRANCE);
    }

    private Local() {
    }

    public static ResourceBundle getResourceBundleDefault() {
        return RESOURCE_BUNDLE_DEFAULT;
    }



}
