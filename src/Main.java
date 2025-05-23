import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Store store = new Store(0.3, 0.2, 3, 0.1);

        Product milk = new Product("P001", "Мляко", 1.20, Category.FOOD, LocalDate.now().plusDays(2), 50);
        Product bread = new Product("P002", "Хляб", 0.80, Category.FOOD, LocalDate.now().plusDays(1), 30);
        Product chocolate = new Product("P003", "Шоколад", 1.50, Category.FOOD, LocalDate.now().plusDays(20), 100);
        Product soap = new Product("P004", "Сапун", 2.00, Category.NON_FOOD, LocalDate.now().plusDays(100), 80);
        Product toothpaste = new Product("P005", "Паста за зъби", 3.00, Category.NON_FOOD, LocalDate.now().plusDays(50), 40);

        store.addProduct(milk);
        store.addProduct(bread);
        store.addProduct(chocolate);
        store.addProduct(soap);
        store.addProduct(toothpaste);

        Cashier cashier1 = new Cashier("C001", "Георги Иванов", 1200);
        Cashier cashier2 = new Cashier("C002", "Иван Петров", 1000);

        store.addCashier(cashier1);
        store.addCashier(cashier2);

        CashRegister register1 = new CashRegister("K001", cashier1, store);
        CashRegister register2 = new CashRegister("K002", cashier2, store);

        try {
            // Продажба на каса 1
            register1.addProductToSale(milk, 5);
            register1.addProductToSale(soap, 3);
            Receipt receipt1 = register1.completeSale(20);
            System.out.println("Продажба 1:");
            receipt1.printReceipt();
            System.out.println();

            // Продажба на каса 2
            register2.addProductToSale(chocolate, 10);
            register2.addProductToSale(toothpaste, 1);
            Receipt receipt2 = register2.completeSale(25);
            System.out.println("Продажба 2:");
            receipt2.printReceipt();
            System.out.println();

            // Опит за продажба с недостатъчно количество
            try {
                register1.addProductToSale(bread, 40);
                Receipt receipt3 = register1.completeSale(50);
            } catch (Exceptions.InsufficientQuantityException e) {
                System.out.println("Грешка: " + e.getMessage());
            }

            // Опит за продажба на продукт с изтекъл срок
            Product expiredProduct = new Product("P006", "Кисело мляко", 1.00, Category.FOOD, LocalDate.now().minusDays(1), 10);
            store.addProduct(expiredProduct);

            try {
                register2.addProductToSale(expiredProduct, 1);
                Receipt receipt4 = register2.completeSale(5);
            } catch (Exceptions.ExpiredProductException e) {
                System.out.println("Грешка: " + e.getMessage());
            }

            // Опит за продажба с недостатъчно пари
            register1.addProductToSale(chocolate, 2);
            try {
                Receipt receipt5 = register1.completeSale(1); // недостатъчна сума
            } catch (Exceptions.InsufficientMoneyException e) {
                System.out.println("Грешка: " + e.getMessage());
            }

            System.out.println("Общо издадени касови бележки: " + store.getIssuedReceipts().size());
            System.out.printf("Общ оборот: %.2f лв.%n", store.calculateTotalRevenue());
            System.out.printf("Общо разходи за заплати: %.2f лв.%n", store.calculateTotalSalaries());
            System.out.printf("Общо разходи за доставки: %.2f лв.%n", store.calculateTotalDeliveryCosts());
            System.out.printf("Печалба: %.2f лв.%n", store.calculateProfit());

        } catch (Exception e) {
            System.out.println("Непредвидена грешка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
