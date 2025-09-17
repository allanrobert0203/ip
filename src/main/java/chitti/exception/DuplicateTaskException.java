package chitti.exception;

/**
 * Domain-specific exception for duplicate task addition
 */
public class DuplicateTaskException extends ChittiException {
    public DuplicateTaskException(String message) {
        super(message);
    }
}
