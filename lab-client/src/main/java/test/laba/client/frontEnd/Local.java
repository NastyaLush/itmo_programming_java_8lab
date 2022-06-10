package test.laba.client.frontEnd;

import java.util.Locale;
import java.util.ResourceBundle;

public class Local {
    protected static final ResourceBundle resourceBundleDeafult = ResourceBundle.getBundle("lang");
    protected static final ResourceBundle resourceBundleEnglish = ResourceBundle.getBundle("lang", new Locale("en", "EN"));
    protected static final ResourceBundle resourceBundleNorwegian = ResourceBundle.getBundle("lang", new Locale("es", "NI"));
    protected static final ResourceBundle resourceBundleFrance = ResourceBundle.getBundle("lang", new Locale("fr", "FR"));
    protected static final ResourceBundle resourceBundleRussian = ResourceBundle.getBundle("lang", new Locale("ru", "RU"));

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
}
