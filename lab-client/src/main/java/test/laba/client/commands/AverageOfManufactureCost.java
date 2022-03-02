package test.laba.client.commands;



import test.laba.client.console.Console;
import test.laba.client.mainClasses.Product;
import test.laba.client.mainClasses.Root;

import java.util.HashMap;

public final class AverageOfManufactureCost extends AbstractCommand {

    public AverageOfManufactureCost() {
        super("Average_of_manufacture_cost", "вывести среднее значение поля manufactureCost для всех элементов коллекции");
    }

    public void execute(Root root, Console console) {
        Integer answer = 0;
        try {
           /* здесь getProduct нарушает инкапсуляцию?*/
            for (HashMap.Entry<Long, Product> prod : root.getProducts().entrySet()) {
                answer += prod.getValue().getManufactureCost();
        }
        } catch (NullPointerException e) {
            answer += 0;
        }
        console.print("Average of manufacture cost: " + answer / (root.getProducts().size()));
    }

}
