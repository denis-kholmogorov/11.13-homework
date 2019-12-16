package Bank;

public class UnavailableQuantityException  extends RuntimeException {
    public UnavailableQuantityException(String message) {
        super(message);
    }
}
