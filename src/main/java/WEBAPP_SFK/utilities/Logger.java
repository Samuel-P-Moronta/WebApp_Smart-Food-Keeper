package WEBAPP_SFK.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
    private static Logger logger = new Logger();
    private Log log;

    private Logger() { }

    public static Logger getInstance() {
        return logger;
    }

    public Log getLog(Class<?> type) {
        log = LogFactory.getLog(type);
        return log;
    }
}
