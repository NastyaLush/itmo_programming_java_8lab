package test.laba.client.commands;



import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.HashMap;

public class GroupCountingByPrice extends AbstractCommand {
    public GroupCountingByPrice(){
        super("group_counting_by_price ","сгруппировать элементы коллекции по значению поля price, вывести количество элементов в каждой группе");
    }

    public void groupCountingByPrice(Root root){
        HashMap<Long, Long> products = new HashMap<>();
        HashMap< Long, Product> answer = new HashMap<>();
        for (HashMap.Entry <Long,Product> entry : root.getProducts().entrySet()) {
            products.put(entry.getKey(), entry.getValue().getPrice());
        }
        products.entrySet().stream().sorted(HashMap.Entry.<Long, Long>comparingByValue().reversed()).forEach((key)->answer.put(key.getKey(),root.getProducts().get(key.getKey())));

        root.setProducts(answer);
    }
}
