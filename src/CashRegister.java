import java.util.HashMap;
import java.util.Map;

public class CashRegister {
    private String registerId;
    private Cashier cashier;
    private Store store;
    private Map<Product, Integer> currentSale;

    public CashRegister(String registerId, Cashier cashier, Store store) {
        this.registerId = registerId;
        this.cashier = cashier;
        this.store = store;
        this.currentSale = new HashMap<>();
    }

    public void addProductToSale(Product product, int quantity) throws Exceptions.InsufficientQuantityException {
        int alreadyRequested = currentSale.getOrDefault(product, 0);
        int totalRequested = alreadyRequested + quantity;
        if (product.getQuantity() < totalRequested) {
            throw new Exceptions.InsufficientQuantityException(product.getName(), totalRequested, product.getQuantity());
        }
        currentSale.put(product, totalRequested);
    }

    public Receipt completeSale(double customerMoney) throws Exceptions.InsufficientFundsException {
        double totalAmount = 0;
        for (Map.Entry<Product, Integer> entry : currentSale.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            totalAmount += store.calculateSellingPrice(product) * quantity;
        }

        if (customerMoney < totalAmount) {
            double shortage = totalAmount - customerMoney;
            throw new Exceptions.InsufficientFundsException(shortage);
        }

        // Намаляваме количествата след успешна проверка на парите
        for (Map.Entry<Product, Integer> entry : currentSale.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            product.decreaseQuantity(quantity);
        }

        Receipt receipt = new Receipt(registerId, cashier, new HashMap<>(currentSale), totalAmount, customerMoney, store);
        currentSale.clear();
        return receipt;
    }
}
