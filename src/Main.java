import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(0.3, 0.2, 3, 0.1);

        Product p1 = new Product("1", "Мляко", 1.20, Category.FOOD, LocalDate.now().plusDays(2), 10);
        Product p2 = new Product("2", "Хляб", 0.80, Category.FOOD, LocalDate.now().plusDays(1), 5);
        Product p3 = new Product("3", "Шоколад", 1.50, Category.FOOD, LocalDate.now().plusDays(20), 20);
        Product p4 = new Product("4", "Сапун", 2.00, Category.NON_FOOD, LocalDate.now().plusDays(100), 15);
        Product p5 = new Product("5", "Паста за зъби", 3.00, Category.NON_FOOD, LocalDate.now().plusDays(50), 10);
        Product p6 = new Product("6", "Кисело мляко", 1.00, Category.FOOD, LocalDate.now().plusDays(1), 8);
        Product p7 = new Product("7", "Бисквити", 1.80, Category.FOOD, LocalDate.now().plusDays(10), 12);
        Product p8 = new Product("8", "Детски шампоан", 4.00, Category.NON_FOOD, LocalDate.now().plusDays(365), 5);
        Product p9 = new Product("9", "Салфетки", 0.90, Category.NON_FOOD, LocalDate.now().plusDays(180), 30);
        Product p10 = new Product("10", "Кафе", 5.00, Category.FOOD, LocalDate.now().plusDays(120), 25);

        store.addProduct(p1);
        store.addProduct(p2);
        store.addProduct(p3);
        store.addProduct(p4);
        store.addProduct(p5);
        store.addProduct(p6);
        store.addProduct(p7);
        store.addProduct(p8);
        store.addProduct(p9);
        store.addProduct(p10);

        Cashier cashier = new Cashier("1001", "Георги Иванов", 1200);
        CashRegister register = new CashRegister("K001", cashier, store);

        try {
            register.addProductToSale(p1, 10);
            register.addProductToSale(p3, 1);
            register.addProductToSale(p4, 1);

            Receipt receipt = register.completeSale(20);

            System.out.println("Общо издадени касови бележки: " + Receipt.getReceiptCounter());
            System.out.println("Общ оборот: " + Receipt.getTotalTurnover() + " лв.");

            // Отделен try-catch за IO и ClassNotFound при четене на файл
            try {
                Receipt loadedReceipt = Receipt.readFromFile("receipt_" + receipt.getReceiptNumber() + ".ser");
                System.out.println("=== Заредена касова бележка от файл ===");
                loadedReceipt.printReceipt();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Грешка при зареждане на касовата бележка: " + e.getMessage());
            }

        } catch (Exceptions.InsufficientQuantityException | IllegalArgumentException e) {
            System.out.println("Грешка при продажбата: " + e.getMessage());
        } catch (Exceptions.InsufficientFundsException e) {
            System.out.println("Грешка при продажбата: " + e.getMessage());
        }
    }
}
