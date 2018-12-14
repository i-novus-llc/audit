package ru.i_novus.ms.audit.exception;

public class AuditException extends RuntimeException {

    public AuditException() {
    }

    public AuditException(String message) {
        super(message);
    }

    public AuditException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditException(Throwable cause) {
        super(cause);
    }

    public AuditException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
