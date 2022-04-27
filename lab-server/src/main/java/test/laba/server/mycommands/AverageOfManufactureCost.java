package test.laba.server.mycommands;
import test.laba.common.IO.Colors;
import test.laba.common.commands.AbstractCommand;
import test.laba.common.commands.Root;
import test.laba.common.util.Response;

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
    public Response execute(String arg, Root root) {
        StringBuilder s = new StringBuilder(Colors.BlUE + "Average_of_manufacture_cost" + Colors.END);
        if (root.getProducts().size() != 0) {
            s.append(root.sumOfManufactureCost() / (root.getProducts().size()));
            return new Response(s.toString());
        } else {
            return new Response("0");
        }
    }

}
