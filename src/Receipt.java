import java.util.Map;

public class Receipt {
    private String registerId;
    private Cashier cashier;
    private Map<Product, Integer> products;
    private double totalAmount;
    private double customerMoney;
    private Store store;

    public Receipt(String registerId, Cashier cashier, Map<Product, Integer> products, double totalAmount, double customerMoney, Store store) {
        this.registerId = registerId;
        this.cashier = cashier;
        this.products = products;
        this.totalAmount = totalAmount;
        this.customerMoney = customerMoney;
        this.store = store;
    }

    public void printReceipt() {
        System.out.println("=== Касова бележка ===");
        System.out.println("Каса: " + registerId);
        System.out.println("Касиер: " + cashier.getName());
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            double unitPrice = store.calculateSellingPrice(p);
            System.out.printf("%s x%d - %.2f лв.%n", p.getName(), qty, unitPrice * qty);
        }
        System.out.printf("Общо: %.2f лв.%n", totalAmount);
        System.out.printf("Платено: %.2f лв.%n", customerMoney);
        System.out.printf("Ресто: %.2f лв.%n", customerMoney - totalAmount);
    }
}
