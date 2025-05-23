import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private int receiptNumber;
    private String registerId;
    private Cashier cashier;
    private Map<Product, ItemSale> productsSold;
    private double totalAmount;
    private double customerMoney;

    public Receipt(int receiptNumber, String registerId, Cashier cashier, Map<Product, ItemSale> productsSold, double totalAmount, double customerMoney) {
        this.receiptNumber = receiptNumber;
        this.registerId = registerId;
        this.cashier = cashier;
        this.productsSold = productsSold;
        this.totalAmount = totalAmount;
        this.customerMoney = customerMoney;
    }

    public int getReceiptNumber() {
        return receiptNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void printReceipt() {
        System.out.println("=== Касова бележка №" + receiptNumber + " ===");
        System.out.println("Каса: " + registerId);
        System.out.println("Касиер: " + cashier.getName());
        for (Map.Entry<Product, ItemSale> entry : productsSold.entrySet()) {
            Product p = entry.getKey();
            ItemSale sale = entry.getValue();
            double lineTotal = sale.unitPrice * sale.quantity;
            System.out.printf("%s x%d - %.2f лв.%n", p.getName(), sale.quantity, lineTotal);
        }
        System.out.printf("Общо: %.2f лв.%n", totalAmount);
        System.out.printf("Платено: %.2f лв.%n", customerMoney);
        System.out.printf("Ресто: %.2f лв.%n", customerMoney - totalAmount);
        LocalDateTime dateobj = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateobj.format(formatter);
        System.out.printf("Дата на продажба: " + formattedDate + "%n");
    }

    public static class ItemSale implements Serializable {
        private static final long serialVersionUID = 1L;
        public int quantity;
        public double unitPrice;

        public ItemSale(int quantity, double unitPrice) {
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }
    }
}
