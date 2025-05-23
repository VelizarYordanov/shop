import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(20.0, 30.0, 5, 10.0);

        List<Product> products = new ArrayList<>();
        products.add(new Product("P001", "Хляб", 1.0, Category.FOOD, LocalDate.now().plusDays(2)));
        products.add(new Product("P002", "Мляко", 0.8, Category.FOOD, LocalDate.now().plusDays(7)));
        products.add(new Product("P003", "Ябълки", 0.5, Category.FOOD, LocalDate.now().plusDays(1)));
        products.add(new Product("P004", "Банани", 0.6, Category.FOOD, LocalDate.now().minusDays(1)));
        products.add(new Product("P005", "Сок", 1.2, Category.FOOD, LocalDate.now().plusDays(10)));

        products.add(new Product("P006", "Шампоан", 3.0, Category.NON_FOOD, LocalDate.now().plusDays(365)));
        products.add(new Product("P007", "Сапун", 0.7, Category.NON_FOOD, LocalDate.now().plusDays(180)));
        products.add(new Product("P008", "Паста за зъби", 2.0, Category.NON_FOOD, LocalDate.now().plusDays(30)));
        products.add(new Product("P009", "Тоалетна хартия", 1.5, Category.NON_FOOD, LocalDate.now().plusDays(60)));
        products.add(new Product("P010", "Перилен препарат", 5.0, Category.NON_FOOD, LocalDate.now().plusDays(15)));

        for (Product product : products) {
            double sellingPrice = store.calculateSellingPrice(product);
            if (sellingPrice < 0) {
                System.out.println(product.getName() + " (ID: " + product.getId() + ") не може да се продава - изтекъл срок.");
            } else {
                System.out.printf("%s (ID: %s): %.2f лв.%n", product.getName(), product.getId(), sellingPrice);
            }
        }
    }
}
