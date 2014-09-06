#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.container;

import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;

/**
 * @author WMS
 *
 */
public class ChartLog4jContainer implements Container {

	public static final String LOG4J_FILE = "dubbo.log4j.file";

    public static final String LOG4J_LEVEL = "dubbo.log4j.level";

    public static final String LOG4J_SUBDIRECTORY = "dubbo.log4j.subdirectory";

    public static final String DEFAULT_LOG4J_LEVEL = "ERROR";
    
    public static final String LOG4J_MAXFILESIZE="dubbo.log4j.MaxFileSize";
	
	@Override
	@SuppressWarnings("unchecked")
	public void start() {
		String file = ConfigUtils.getProperty(LOG4J_FILE);
		String filesize = ConfigUtils.getProperty(LOG4J_MAXFILESIZE,"10MB");
        if (file != null && file.length() > 0) {
            String level = ConfigUtils.getProperty(LOG4J_LEVEL);
            if (level == null || level.length() == 0) {
                level = DEFAULT_LOG4J_LEVEL;
            }
            Properties properties = new Properties();
            properties.setProperty("log4j.rootLogger", level + ",application");
            properties.setProperty("log4j.appender.application", "org.apache.log4j.RollingFileAppender");
            properties.setProperty("log4j.appender.application.File", file);
            properties.setProperty("log4j.appender.application.MaxFileSize", filesize);
            properties.setProperty("log4j.appender.application.Append", "true");
            properties.setProperty("log4j.appender.application.layout", "org.apache.log4j.PatternLayout");
            properties.setProperty("log4j.appender.application.layout.ConversionPattern", "[%t] %-5p %d{yyyy-MM-dd- HH:mm:ss.SSS} %C{6} (%F:%L) - %m%n");
            PropertyConfigurator.configure(properties);
        }
        String subdirectory = ConfigUtils.getProperty(LOG4J_SUBDIRECTORY);
        if (subdirectory != null && subdirectory.length() > 0) {
			Enumeration<org.apache.log4j.Logger> ls = LogManager.getCurrentLoggers();
            while (ls.hasMoreElements()) {
                org.apache.log4j.Logger l = ls.nextElement();
                if (l != null) {
                    Enumeration<Appender> as = l.getAllAppenders();
                    while (as.hasMoreElements()) {
                        Appender a = as.nextElement();
                        if (a instanceof FileAppender) {
                            FileAppender fa = (FileAppender)a;
                            String f = fa.getFile();
                            if (f != null && f.length() > 0) {
                                int i = f.replace('${symbol_escape}${symbol_escape}', '/').lastIndexOf('/');
                                String path;
                                if (i == -1) {
                                    path = subdirectory;
                                } else {
                                    path = f.substring(0, i);
                                    if (! path.endsWith(subdirectory)) {
                                        path = path + "/" + subdirectory;
                                    }
                                    f = f.substring(i + 1);
                                }
                                fa.setFile(path + "/" + f);
                                fa.activateOptions();
                            }
                        }
                    }
                }
            }
        }
	}

	@Override
	public void stop() {

		
	}

}
