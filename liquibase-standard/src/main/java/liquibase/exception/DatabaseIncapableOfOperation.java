package liquibase.exception;

import liquibase.database.Database;

public class DatabaseIncapableOfOperation extends RuntimeException {
    private static final long serialVersionUID = -2179551294831803877L;
    private final String operation;
    private final String reason;

    public DatabaseIncapableOfOperation(String operation, String reason, Database database) {
        super(operation + " is not supported on " + database.getDisplayName() + ": " + reason);
        this.operation = operation;
        this.reason = reason;
    }

    public String getOperation() {
        return operation;
    }

    public String getReason() {
        return reason;
    }
}
