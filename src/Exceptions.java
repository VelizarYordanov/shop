public class Exceptions {

    public static class InsufficientQuantityException extends Exception {
        public InsufficientQuantityException(String productName, int requested, int available) {
            super("Недостатъчно количество от продукт: " + productName + ". Изискано: " + requested + ", налично: " + available + ". Нужни са още " + (requested - available));
        }
    }

    public static class InsufficientMoneyException extends Exception {
        public InsufficientMoneyException(double needed) {
            super("Недостатъчно пари. Трябват още: " + String.format("%.2f", needed) + " лв.");
        }
    }

    public static class ExpiredProductException extends Exception {
        public ExpiredProductException(String message) {
            super(message);
        }
    }
}
