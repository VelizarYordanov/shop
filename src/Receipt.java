import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int receiptCount = 0;
    private static double totalTurnover = 0;

    private int receiptNumber;
    private String registerId;
    private Cashier cashier;
    private Map<Product, Integer> products;
    private double totalAmount;
    private double customerMoney;
    private Store store;
    private LocalDateTime dateTime;

    public Receipt(String registerId, Cashier cashier, Map<Product, Integer> products, double totalAmount, double customerMoney, Store store) {
        this.registerId = registerId;
        this.cashier = cashier;
        this.products = products;
        this.totalAmount = totalAmount;
        this.customerMoney = customerMoney;
        this.store = store;
        this.dateTime = LocalDateTime.now();

        receiptCount++;
        receiptNumber = receiptCount;
        totalTurnover += totalAmount;
    }

    public void printReceipt() {
        System.out.println("=== Касова бележка №" + receiptNumber + " ===");
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        System.out.printf("Дата на продажба: %s%n", dateTime.format(formatter));
    }

    public void saveReceiptToFile() throws IOException {
        String filename = "receipt_" + receiptNumber + ".ser";

        // Сериализиране в .ser файл
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }

        // Запис като текстов файл за лесно четене
        String textFilename = "receipt_" + receiptNumber + ".txt";
        try (PrintWriter writer = new PrintWriter(textFilename)) {
            writer.println("=== Касова бележка №" + receiptNumber + " ===");
            writer.println("Каса: " + registerId);
            writer.println("Касиер: " + cashier.getName());
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                double unitPrice = store.calculateSellingPrice(p);
                writer.printf("%s x%d - %.2f лв.%n", p.getName(), qty, unitPrice * qty);
            }
            writer.printf("Общо: %.2f лв.%n", totalAmount);
            writer.printf("Платено: %.2f лв.%n", customerMoney);
            writer.printf("Ресто: %.2f лв.%n", customerMoney - totalAmount);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            writer.printf("Дата на продажба: %s%n", dateTime.format(formatter));
        }
    }

    // Метод за десериализация от файл
    public static Receipt loadReceiptFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Receipt) ois.readObject();
        }
    }

    public static int getReceiptCount() {
        return receiptCount;
    }

    public static double getTotalTurnover() {
        return totalTurnover;
    }
}
