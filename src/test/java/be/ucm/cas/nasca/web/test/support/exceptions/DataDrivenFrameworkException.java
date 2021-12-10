package be.ucm.cas.nasca.web.test.support.exceptions;

public class DataDrivenFrameworkException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String message;

    public DataDrivenFrameworkException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[Data Driven Framework Exception] " + this.message;
    }
}
