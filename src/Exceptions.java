public class Exceptions {

    public static class InsufficientQuantityException extends Exception {
        public InsufficientQuantityException(String productName, int requested, int available) {
            super("Недостатъчно количество от продукт: " + productName + ". Изискано: " + requested + ", налично: " + available + ". Нужни са още " + (requested - available));
        }
    }

    public static class InsufficientFundsException extends Exception {
        public InsufficientFundsException(double requiredAmount) {
            super(String.format("Недостатъчно пари. Трябват още %.2f лв.", requiredAmount));
        }
    }
}
