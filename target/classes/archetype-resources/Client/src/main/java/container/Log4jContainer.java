#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.container;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import com.alibaba.dubbo.container.Container;
import ${package}.utils.SystemProps;


/**
 * @author WMS
 *
 */
public class Log4jContainer implements Container{

	public static final String LOG4J_FILE="server.log4j.file";
	
	public static final String LOG4J_LEVEL="server.log4j.level";
	
	public static final String DEFAULT_LOG4J_LEVEL = "ERROR";
	
	@Override
	public void start() {
		String file = SystemProps.getValue(LOG4J_FILE);
		if(file!=null&&file.length()>0){
			String level = SystemProps.getValue(LOG4J_LEVEL);
			if(level==null||level.length()==0){
				level=DEFAULT_LOG4J_LEVEL;
			}
			Properties properties = new Properties();
	        properties.setProperty("log4j.rootLogger",  level+",application");
	        properties.setProperty("log4j.appender.application", "org.apache.log4j.DailyRollingFileAppender");
	        properties.setProperty("log4j.appender.application.File", file);
	        properties.setProperty("log4j.appender.application.Append", "true");
	        properties.setProperty("log4j.appender.application.DatePattern", "'.'yyyy-MM-dd");
	        properties.setProperty("log4j.appender.application.layout", "org.apache.log4j.PatternLayout");
	        properties.setProperty("log4j.appender.application.layout.ConversionPattern", "[%t] %-5p %d{yyyy-MM-dd- HH:mm:ss.SSS} %C{6} (%F:%L) - %m%n");
	        PropertyConfigurator.configure(properties);
		}
	}

	@Override
	public void stop() {
		
	}

}
