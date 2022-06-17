package test.laba.client.frontEnd.frames.local;

import test.laba.client.util.Constants;

import java.util.ResourceBundle;

public interface Localized {
    default String localisation(Constants constants) {
        return getResourceBundle().getString(constants.getString());
    }
    ResourceBundle getResourceBundle();
}
