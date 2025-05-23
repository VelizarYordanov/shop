import java.time.LocalDate;

public class Product {
    private String id;
    private String name;
    private double purchasePrice;
    private Category category;
    private LocalDate expirationDate;

    public Product(String id, String name, double purchasePrice, Category category, LocalDate expirationDate) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.category = category;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
