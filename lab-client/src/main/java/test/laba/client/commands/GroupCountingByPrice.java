package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GroupCountingByPrice extends AbstractCommand {
    HashMap<Long, Long> countingByPrice = new LinkedHashMap<>();
    public GroupCountingByPrice() {
        super("Group_counting_by_price ", "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе");
    }

    public void execute(Root root, Console console) {
        HashMap<Long, Long> products = new LinkedHashMap<>();
        long productsCount;
        for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()) {
            products.put(entry.getKey(), entry.getValue().getPrice());
            try {
                productsCount = countingByPrice.get(entry.getValue().getPrice());
            } catch (NullPointerException e) {
                productsCount = 0;
            }
            countingByPrice.put(entry.getValue().getPrice(), ++productsCount);
        }

        console.print("price = count\n");
        for (Map.Entry<Long, Long> entry: countingByPrice.entrySet()){
            console.print(entry.getKey() + " =  " + entry.getValue());
        }

    }
}
