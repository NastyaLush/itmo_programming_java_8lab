package test.laba.server.mycommands.commands;

import test.laba.common.responses.Response;
import test.laba.server.mycommands.Root;

/**
 * abstract class, contains getters and setters
 */
public abstract class AbstractCommand implements AbstractCommandInterface {
    private final String description;
    private final String name;
    protected AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Override
    public String toString() {
        return "AbstractCommand{"
                + "description='" + description + '\''
                + ", name='" + name + '\''
                + '}';
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }
    public abstract Response execute(String arguments, Root root);
}
