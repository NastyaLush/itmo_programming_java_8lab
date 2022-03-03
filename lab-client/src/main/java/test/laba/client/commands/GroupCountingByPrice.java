package test.laba.client.commands;

import test.laba.client.mainClasses.Root;
import java.util.HashMap;

public class GroupCountingByPrice extends AbstractCommand {
   public GroupCountingByPrice() {
        super("Group_counting_by_price ", "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе");
    }

    public HashMap<Long, Long> execute(Root root) {
        return root.groupCountingByPrice();
    }
}
