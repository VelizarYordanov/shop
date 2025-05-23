import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Store {
    private List<Product> products = new ArrayList<>();
    private double foodMarkup;
    private double nonFoodMarkup;
    private int expirationDaysThreshold;
    private double discountPercentage;

    public Store(double foodMarkup, double nonFoodMarkup, int expirationDaysThreshold, double discountPercentage) {
        this.foodMarkup = foodMarkup;
        this.nonFoodMarkup = nonFoodMarkup;
        this.expirationDaysThreshold = expirationDaysThreshold;
        this.discountPercentage = discountPercentage;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public double calculateSellingPrice(Product product) {
        if (product.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Продуктът е с изтекъл срок на годност: " + product.getName());
        }

        double markup = (product.getCategory() == Category.FOOD) ? foodMarkup : nonFoodMarkup;
        double price = product.getPurchasePrice() * (1 + markup);

        long daysToExpire = LocalDate.now().until(product.getExpirationDate()).getDays();
        if (daysToExpire <= expirationDaysThreshold) {
            price *= (1 - discountPercentage);
        }

        return price;
    }
}