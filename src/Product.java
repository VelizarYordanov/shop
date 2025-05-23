import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double purchasePrice;
    private Category category;
    private LocalDate expirationDate;
    private int quantity;

    public Product(String id, String name, double purchasePrice, Category category, LocalDate expirationDate) {
        this(id, name, purchasePrice, category, expirationDate, 0);
    }

    public Product(String id, String name, double purchasePrice, Category category, LocalDate expirationDate, int quantity) {
        this.id = id;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.category = category;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public void decreaseQuantity(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Недостатъчно количество от продукта " + name);
        }
        quantity -= amount;
    }
}
