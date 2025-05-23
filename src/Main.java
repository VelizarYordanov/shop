import java.time.LocalDate;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(0.3, 0.2, 3, 0.1);

        Product p1 = new Product("1", "Мляко", 1.20, Category.FOOD, LocalDate.now().plusDays(2), 15);
        Product p2 = new Product("2", "Хляб", 0.80, Category.FOOD, LocalDate.now().plusDays(1), 10);
        Product p3 = new Product("3", "Шоколад", 1.50, Category.FOOD, LocalDate.now().plusDays(20), 20);
        Product p4 = new Product("4", "Сапун", 2.00, Category.NON_FOOD, LocalDate.now().plusDays(100), 15);

        store.addProduct(p1);
        store.addProduct(p2);
        store.addProduct(p3);
        store.addProduct(p4);

        Cashier cashier = new Cashier("1001", "Георги Иванов", 1200);
        CashRegister register = new CashRegister("K001", cashier, store);

        try {
            // Първа продажба
            register.addProductToSale(p1, 5);
            register.addProductToSale(p3, 2);
            Receipt receipt1 = register.completeSale(20);
            receipt1.printReceipt();
            receipt1.saveReceiptToFile();

            System.out.println();

            // Втора продажба
            register.addProductToSale(p2, 3);
            register.addProductToSale(p4, 1);
            Receipt receipt2 = register.completeSale(10);
            receipt2.printReceipt();
            receipt2.saveReceiptToFile();

        } catch (Exceptions.InsufficientQuantityException | Exceptions.InsufficientFundsException | IllegalArgumentException e) {
            System.out.println("Грешка при продажбата: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Грешка при запис на касовата бележка във файл: " + e.getMessage());
        }

        System.out.println();
        System.out.println("Общо издадени касови бележки: " + Receipt.getReceiptCount());
        System.out.printf("Общ оборот: %.2f лв.%n", Receipt.getTotalTurnover());
    }
}
