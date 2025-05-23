import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(0.3, 0.2, 3, 0.1);

        // Добавяне на продукти
        Product p1 = new Product("1", "Мляко", 1.20, Category.FOOD, LocalDate.now().plusDays(2), 10);
        Product p2 = new Product("2", "Хляб", 0.80, Category.FOOD, LocalDate.now().plusDays(1), 5);
        Product p3 = new Product("3", "Шоколад", 1.50, Category.FOOD, LocalDate.now().plusDays(20), 20);
        Product p4 = new Product("4", "Сапун", 2.00, Category.NON_FOOD, LocalDate.now().plusDays(100), 15);
        Product p5 = new Product("5", "Паста за зъби", 3.00, Category.NON_FOOD, LocalDate.now().plusDays(50), 10);

        store.addProduct(p1);
        store.addProduct(p2);
        store.addProduct(p3);
        store.addProduct(p4);
        store.addProduct(p5);

        // Добавяне на касиери
        Cashier cashier1 = new Cashier("1001", "Георги Иванов", 1200);
        Cashier cashier2 = new Cashier("1002", "Иван Петров", 1000);

        store.addCashier(cashier1);
        store.addCashier(cashier2);

        // Създаване на касови апарати с касиери
        CashRegister register1 = new CashRegister("K001", cashier1, store);
        CashRegister register2 = new CashRegister("K002", cashier2, store);

        try {
            // Първа продажба
            register1.addProductToSale(p1, 5);
            register1.addProductToSale(p4, 2);
            Receipt receipt1 = register1.completeSale(20);
            receipt1.printReceipt();

            System.out.println();

            // Втора продажба
            register2.addProductToSale(p3, 3);
            register2.addProductToSale(p5, 1);
            Receipt receipt2 = register2.completeSale(15);
            receipt2.printReceipt();

            System.out.println();

            // Прочитане на касовата бележка от файл
            Receipt loadedReceipt = store.loadReceiptFromFile(receipt1.getReceiptNumber());
            System.out.println("Прочетена касова бележка от файл:");
            loadedReceipt.printReceipt();

        } catch (Exceptions.InsufficientMoneyException | Exceptions.InsufficientQuantityException | Exceptions.ExpiredProductException e) {
            System.out.println("Грешка при продажбата: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Грешка при работа с файл: " + e.getMessage());
        }

        System.out.println("\nОбщо издадени касови бележки: " + store.getIssuedReceipts().size());
        System.out.printf("Общ оборот: %.2f лв.%n", store.calculateTotalRevenue());
        System.out.printf("Общо разходи за заплати: %.2f лв.%n", store.calculateTotalSalaries());
        System.out.printf("Общо разходи за доставки: %.2f лв.%n", store.calculateTotalDeliveryCosts());
        System.out.printf("Печалба: %.2f лв.%n", store.calculateProfit());
    }
}
