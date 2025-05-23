 public class InsufficientQuantityException extends Exception {
        public InsufficientQuantityException(String productName, int requested, int available) {
            super("Недостатъчно количество от продукт: " + productName + ". Изискано: " + requested + ", налично: " + available + ". Нужни са още " + (requested - available));
        }
}
