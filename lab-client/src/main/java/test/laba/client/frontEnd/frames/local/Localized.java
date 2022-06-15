package test.laba.client.frontEnd.frames.local;

import test.laba.client.util.Constants;

import java.util.ResourceBundle;

public interface Localized {
    default String localisation(ResourceBundle resourceBundle, Constants constants) {
        return resourceBundle.getString(constants.getString());
    }
}
