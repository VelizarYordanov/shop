import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Product> products = new ArrayList<>();
    private List<Cashier> cashiers = new ArrayList<>();
    private List<Product> deliveredProducts = new ArrayList<>();
    private List<Receipt> issuedReceipts = new ArrayList<>();

    private double foodMarkup;
    private double nonFoodMarkup;
    private int expirationDaysThreshold;
    private double discountPercentage;

    private int receiptCounter = 0;

    public Store(double foodMarkup, double nonFoodMarkup, int expirationDaysThreshold, double discountPercentage) {
        this.foodMarkup = foodMarkup;
        this.nonFoodMarkup = nonFoodMarkup;
        this.expirationDaysThreshold = expirationDaysThreshold;
        this.discountPercentage = discountPercentage;
    }

    public void addProduct(Product product) {
        products.add(product);
        deliveredProducts.add(product);
    }

    public void addCashier(Cashier cashier) {
        cashiers.add(cashier);
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Cashier> getCashiers() {
        return cashiers;
    }

    public List<Product> getDeliveredProducts() {
        return deliveredProducts;
    }

    public List<Receipt> getIssuedReceipts() {
        return issuedReceipts;
    }

    public double calculateSellingPrice(Product product) throws Exceptions.ExpiredProductException {
        if (product.getExpirationDate().isBefore(LocalDate.now())) {
            throw new Exceptions.ExpiredProductException("Продуктът е с изтекъл срок на годност: " + product.getName());
        }

        double markup = (product.getCategory() == Category.FOOD) ? foodMarkup : nonFoodMarkup;
        double price = product.getPurchasePrice() * (1 + markup);

        long daysToExpire = LocalDate.now().until(product.getExpirationDate()).getDays();
        if (daysToExpire <= expirationDaysThreshold) {
            price *= (1 - discountPercentage);
        }

        return price;
    }

    public int getNextReceiptNumber() {
        receiptCounter++;
        return receiptCounter;
    }

    public void addReceipt(Receipt receipt) {
        issuedReceipts.add(receipt);
    }

    public void saveReceiptToFile(Receipt receipt) throws IOException {
        String filename = "receipt_" + receipt.getReceiptNumber() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(receipt);
        }
    }

    public Receipt loadReceiptFromFile(int receiptNumber) throws IOException, ClassNotFoundException {
        String filename = "receipt_" + receiptNumber + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Receipt) ois.readObject();
        }
    }

    public double calculateTotalSalaries() {
        double total = 0;
        for (Cashier cashier : cashiers) {
            total += cashier.getMonthlySalary();
        }
        return total;
    }

    public double calculateTotalDeliveryCosts() {
        double total = 0;
        for (Product product : deliveredProducts) {
            total += product.getPurchasePrice() * product.getQuantity();
        }
        return total;
    }

    public double calculateTotalRevenue() {
        double total = 0;
        for (Receipt receipt : issuedReceipts) {
            total += receipt.getTotalAmount();
        }
        return total;
    }

    public double calculateProfit() {
        return calculateTotalRevenue() - (calculateTotalSalaries() + calculateTotalDeliveryCosts());
    }
}
