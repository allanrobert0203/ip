package chitti.exception;

/**
 * Domain-specific exception for user and application errors.
 */
public class ChittiException extends Exception{

    public ChittiException(String message) {
        super(message);
    }
}
