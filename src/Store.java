import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Store {
    private double foodMarkupPercent;
    private double nonFoodMarkupPercent;
    private int daysBeforeExpirationForDiscount;
    private double discountPercent;

    public Store(double foodMarkupPercent, double nonFoodMarkupPercent, int daysBeforeExpirationForDiscount, double discountPercent) {
        this.foodMarkupPercent = foodMarkupPercent;
        this.nonFoodMarkupPercent = nonFoodMarkupPercent;
        this.daysBeforeExpirationForDiscount = daysBeforeExpirationForDiscount;
        this.discountPercent = discountPercent;
    }

    public double calculateSellingPrice(Product product) {
        LocalDate today = LocalDate.now();
        if (product.getExpirationDate().isBefore(today) || product.getExpirationDate().isEqual(today)) {
            // Product expired, should not be sold
            return -1;
        }

        double markup = (product.getCategory() == Category.FOOD) ? foodMarkupPercent : nonFoodMarkupPercent;
        double basePrice = product.getPurchasePrice() * (1 + markup / 100);

        long daysToExpire = ChronoUnit.DAYS.between(today, product.getExpirationDate());
        if (daysToExpire < daysBeforeExpirationForDiscount) {
            basePrice *= (1 - discountPercent / 100);
        }

        return basePrice;
    }
}
