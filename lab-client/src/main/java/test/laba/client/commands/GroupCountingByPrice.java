package test.laba.client.commands;

import test.laba.client.dataClasses.Root;
import java.util.HashMap;
import java.util.Map;

/**
 * group counting by price
 */
public class GroupCountingByPrice extends AbstractCommand {
   public GroupCountingByPrice() {
        super("Group_counting_by_price ", "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе");
    }

    /**
     * group the collection items by the value of the price field, output the number of items in each group
     * @param root object contained collection values
     * @return collection with information, key- price, value- count
     */
    public String execute(String arg, Root root) {
        HashMap<Long, Long> countingByPrice = root.groupCountingByPrice();
        StringBuilder answer = null;
        answer.append("price = count\n");
        for (Map.Entry<Long, Long> entry: countingByPrice.entrySet()) {
            answer.append(entry.getKey() + " =  " + entry.getValue());
        }
        return answer.toString();
    }
}
