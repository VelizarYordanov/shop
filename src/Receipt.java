import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int receiptCounter = 0;
    private static double totalTurnover = 0;

    private String registerId;
    private Cashier cashier;
    private Map<Product, Integer> products;
    private double totalAmount;
    private double customerMoney;
    private Store store;
    private int receiptNumber; // пореден номер на касовата бележка
    private LocalDateTime saleDate;

    public Receipt(String registerId, Cashier cashier, Map<Product, Integer> products, double totalAmount, double customerMoney, Store store) {
        this.registerId = registerId;
        this.cashier = cashier;
        this.products = products;
        this.totalAmount = totalAmount;
        this.customerMoney = customerMoney;
        this.store = store;
        this.saleDate = LocalDateTime.now();

        synchronized (Receipt.class) {
            receiptCounter++;
            receiptNumber = receiptCounter;
            totalTurnover += totalAmount;
        }
    }

    public static int getReceiptCounter() {
        return receiptCounter;
    }

    public static double getTotalTurnover() {
        return totalTurnover;
    }

    public int getReceiptNumber() {
        return receiptNumber;
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
        String formattedDate = saleDate.format(formatter);
        System.out.printf("Дата на продажба: " + formattedDate + "%n");
    }

    public void saveToFile() throws IOException {
        String fileName = "receipt_" + receiptNumber + ".ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        }

        // Записваме и текстова версия
        String textFileName = "receipt_" + receiptNumber + ".txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(textFileName))) {
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
            writer.println("Дата на продажба: " + saleDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        }
    }

    public static Receipt readFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Receipt) ois.readObject();
        }
    }
}
