package test.laba.client.frontEnd.frames.local;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class Local {
    protected static final ResourceBundle resourceBundleDeafult = ResourceBundle.getBundle("lang");
    protected static final ResourceBundle resourceBundleNorwegian = ResourceBundle.getBundle("lang", new Locale("nb", "NO"));
    protected static final ResourceBundle resourceBundleEnglish = ResourceBundle.getBundle("lang", new Locale("en", "EN"));
    protected static final ResourceBundle resourceBundleSpanish = ResourceBundle.getBundle("lang", new Locale("es", "NI"));
    protected static final ResourceBundle resourceBundleFrance = ResourceBundle.getBundle("lang", new Locale("fr", "FR"));
    protected static final ResourceBundle resourceBundleRussian = ResourceBundle.getBundle("lang", new Locale("ru", "RU"));
    protected static final String russian = "russian";
    protected static final String english = "english";
    protected static final String norwegian = "norwegian";
    protected static final String spanish = "spanish";
    protected static final String france = "france";
    protected static final HashMap<String, ResourceBundle> locals = new HashMap<String, ResourceBundle>() {{
        put(russian, resourceBundleRussian);
        put(english, resourceBundleEnglish);
        put(norwegian, resourceBundleNorwegian);
        put(spanish, resourceBundleSpanish);
        put(france, resourceBundleFrance);
    }};


    public static ResourceBundle getResourceBundleDeafult() {
        return resourceBundleDeafult;
    }

    public static ResourceBundle getResourceBundleEnglish() {
        return resourceBundleEnglish;
    }

    public static ResourceBundle getResourceBundleNorwegian() {
        return resourceBundleNorwegian;
    }

    public static ResourceBundle getResourceBundleFrance() {
        return resourceBundleFrance;
    }

    public static ResourceBundle getResourceBundleRussian() {
        return resourceBundleRussian;
    }

    public static ResourceBundle getResourceBundleSpanish() {
        return resourceBundleSpanish;
    }


}
