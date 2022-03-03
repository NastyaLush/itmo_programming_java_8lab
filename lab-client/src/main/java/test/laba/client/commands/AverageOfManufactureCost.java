package test.laba.client.commands;
import test.laba.client.mainClasses.Root;
public final class AverageOfManufactureCost extends AbstractCommand {

    public AverageOfManufactureCost() {
        super("Average_of_manufacture_cost", "вывести среднее значение поля manufactureCost для всех элементов коллекции");
    }

    public Integer execute(Root root) {

        return root.averageOfManufactureCost() / (root.getProducts().size());
    }

}
