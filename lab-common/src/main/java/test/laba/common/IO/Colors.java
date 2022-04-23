package test.laba.common.IO;

public enum Colors {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BlUE("\u001B[34m"),
    END("\u001B[0m");
    private String color;
    Colors(String color) {
        this.color = color;
    }
    public String toString() {
        return color;
    }
}
