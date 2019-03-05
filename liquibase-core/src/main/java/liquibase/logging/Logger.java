package liquibase.logging;

import liquibase.ExtensibleObject;
import liquibase.plugin.Plugin;

import java.util.logging.Level;

/**
 * Interface to class that does the actual logging.
 * Instances will be created by {@link LogService}, normally through {@link liquibase.Scope#getLog(Class)}).
 *
 * All log methods take a {@link LogType} to describe the type of message being logged.
 */
public interface Logger extends ExtensibleObject {

    /**
     * Returns the {@link Level} for this logger. Logs that are not using {@link java.util.logging.Logger} still translate to this standard.
     */
    Level getLogLevel();


    /**
     * Sets the {@link Level} for this logger.
     */
    void setLogLevel(Level level);

    /**
     * Log an error that occurred, using the {@link LogType#LOG} type.
     */
    void severe(String message);

    /**
     * Log an error together with data from an error/exception, using the {@link LogType#LOG} type.
     */
    void severe(String message, Throwable e);

    /**
     * Log an error that occurred.
     */
    void severe(LogType target, String message);

    /**
     * Log an error together with data from an error/exception
     */
    void severe(LogType target, String message, Throwable e);

    /**
     * Log a event the user should be warned about, using the {@link LogType#LOG} type.
     */
    void warning(String message);

    /**
     * Log a event the user should be warned about together with data from an error/exception, using the {@link LogType#LOG} type.
     */
    void warning(String message, Throwable e);

    /**
     * Log a event the user should be warned about
     */
    void warning(LogType target, String message);

    /**
     * Log a event the user should be warned about together with data from an error/exception
     */
    void warning(LogType target, String message, Throwable e);

    /**
     * Logs a general event that might be useful for the user, using the {@link LogType#LOG} type.
     */
    void info(String message);

    /**
     * Logs a general event that might be useful for the user together with data from an error/exception, using the {@link LogType#LOG} type.
     */
    void info(String message, Throwable e);

    /**
     * Logs a general event that might be useful for the user.
     */
    void info(LogType logType, String message);

    /**
     * Logs a general event that might be useful for the user together with data from an error/exception
     */
    void info(LogType target, String message, Throwable e);


    /**
     * Logs a debugging event to aid in troubleshooting, using the {@link LogType#LOG} type.
     */
    void debug(String message);

    /**
     * Logs a debugging event to aid in troubleshooting together with data from an error/exception, using the {@link LogType#LOG} type.
     */
    void debug(String message, Throwable e);

    /**
     * Logs a debugging event to aid in troubleshooting
     */
    void debug(LogType target, String message);

    /**
     * Logs a debugging event to aid in troubleshooting together with data from an error/exception
     */
    void debug(LogType target, String message, Throwable e);

}
