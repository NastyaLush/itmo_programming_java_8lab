package test.laba.client.commands;
import test.laba.client.dataClasses.Root;
public final class AverageOfManufactureCost extends AbstractCommand {
    /**
     * average of manufacture cost command
     */
    public AverageOfManufactureCost() {
        super("Average_of_manufacture_cost", "вывести среднее значение поля manufactureCost для всех элементов коллекции");
    }

    /**
     * return the average value of the manufacture Co st field for all items in the collection
     * @param root object contained collection values
     * @return the average value of the manufacture Co st field for all items in the collection
     */
    public double execute(Root root) {
        if (root.getProducts().size() != 0) {
            return root.averageOfManufactureCost() / (root.getProducts().size());
        } else {
            return 0;
        }
    }

}
