package test.laba.client.util;

public enum Pictures {
    ANIMATION("pictures/picture.png"),
    SHOW("pictures/restart.png"),
    HELP("pictures/question.png"),
    HISTORY("pictures/history.png"),
    SCRIPT("pictures/script.png"),
    INFO("pictures/info.png"),
    TRASH("pictures/trash.png"),
    MINUS("pictures/minus.png"),
    PLUS("pictures/plus.png")
    ;

    String path;

    Pictures(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
