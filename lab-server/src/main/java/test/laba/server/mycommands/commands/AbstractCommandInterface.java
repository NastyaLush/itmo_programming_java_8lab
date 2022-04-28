package test.laba.server.mycommands.commands;

import java.io.Serializable;

public interface AbstractCommandInterface extends Serializable {
    String toString();
    String getDescription();
    String getName();
}
