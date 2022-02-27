package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class GroupCountingByPrice extends AbstractCommand {
    public GroupCountingByPrice() {
        super("Group_counting_by_price ", "сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе");
    }

    public void execute(Root root, Console console) {
        HashMap<Long, Long> products = new LinkedHashMap<>();
        HashMap<Long, Product> result = new LinkedHashMap<>();
        HashMap<Long, Long> answer = new LinkedHashMap<>();
        long flag;
        for (HashMap.Entry<Long, Product> entry : root.getProducts().entrySet()) {
            products.put(entry.getKey(), entry.getValue().getPrice());
            try {
                flag = answer.get(entry.getValue().getPrice());
            } catch (NullPointerException e) {
                flag = 0;
            }
            answer.put(entry.getValue().getPrice(), ++flag);
        }
        Stream<Map.Entry<Long, Long>> st = products.entrySet().stream();
        st.sorted(Map.Entry.comparingByValue())
                .forEach(e -> result.put(e.getKey(), root.getProducts().get(e.getKey())));
        root.setProducts(result);
        console.print(answer);
    }
}
