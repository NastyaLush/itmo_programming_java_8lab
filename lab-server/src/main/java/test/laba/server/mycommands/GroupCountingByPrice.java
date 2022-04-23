package test.laba.server.mycommands;

import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;

import java.util.Map;

/**
 * group counting by price
 */
public class GroupCountingByPrice extends AbstractCommand {
   public GroupCountingByPrice() {
        super("Group_counting_by_price", "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе");
    }

    /**
     * group the collection items by the value of the price field, output the number of items in each group
     * @param root object contained collection values
     * @return collection with information, key- price, value- count
     */
    public String execute(String arg, Root root) {
        Map<Long, Long> countingByPrice = root.groupCountingByPrice();
        StringBuilder answer = new StringBuilder();
        answer.append("price = count\n");
        countingByPrice.entrySet().forEach(entry -> answer.append(entry.getKey() + " =  " + entry.getValue() + '\n'));
        return answer.toString();
    }
}
