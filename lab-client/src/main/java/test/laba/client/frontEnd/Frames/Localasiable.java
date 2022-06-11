package test.laba.client.frontEnd.Frames;

import test.laba.client.util.Constants;

import java.util.ResourceBundle;

public interface Localasiable {
    default String localisation(ResourceBundle resourceBundle, Constants constants) {
        return resourceBundle.getString(constants.getString());
    }
}
