import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CashRegister {
    private String registerId;
    private Cashier cashier;
    private Store store;
    private Map<Product, Integer> saleItems;

    public CashRegister(String registerId, Cashier cashier, Store store) {
        this.registerId = registerId;
        this.cashier = cashier;
        this.store = store;
        this.saleItems = new HashMap<>();
    }

    public void addProductToSale(Product product, int quantity) throws Exceptions.InsufficientQuantityException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество трябва да е положително.");
        }
        if (product.getQuantity() < quantity) {
            throw new Exceptions.InsufficientQuantityException(product.getName(), quantity, product.getQuantity());
        }
        saleItems.put(product, saleItems.getOrDefault(product, 0) + quantity);
    }

    public Receipt completeSale(double paidAmount) throws Exceptions.InsufficientMoneyException, Exceptions.InsufficientQuantityException, Exceptions.ExpiredProductException, IOException {
        if (saleItems.isEmpty()) {
            throw new IllegalStateException("Няма добавени продукти за продажба.");
        }

        double totalPrice = 0;
        Map<Product, Receipt.ItemSale> productsSoldMap = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : saleItems.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            double unitPrice = store.calculateSellingPrice(product);

            if (product.getQuantity() < quantity) {
                throw new Exceptions.InsufficientQuantityException(product.getName(), quantity, product.getQuantity());
            }

            totalPrice += unitPrice * quantity;
            productsSoldMap.put(product, new Receipt.ItemSale(quantity, unitPrice));
        }

        if (paidAmount < totalPrice) {
            double diff = totalPrice - paidAmount;
            throw new Exceptions.InsufficientMoneyException(diff);
        }

        for (Map.Entry<Product, Receipt.ItemSale> entry : productsSoldMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue().quantity;
            product.decreaseQuantity(quantity);
        }

        int receiptNumber = store.getNextReceiptNumber();

        Receipt receipt = new Receipt(
                receiptNumber,
                this.registerId,
                this.cashier,
                productsSoldMap,
                totalPrice,
                paidAmount
        );

        store.addReceipt(receipt);
        store.saveReceiptToFile(receipt);

        saleItems.clear();

        return receipt;
    }

    public String getRegisterId() {
        return registerId;
    }

    public Cashier getCashier() {
        return cashier;
    }
}
