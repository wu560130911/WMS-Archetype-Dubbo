#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.start;

import java.util.Properties;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author WMS
 *
 */
public class Start {

	private static Log log = LogFactory.getLog(Start.class);
	private static String CONTEXT_PATH="/";
	private static String PROJECT_PATH = "G:${symbol_escape}${symbol_escape}Project8${symbol_escape}${symbol_escape}${artifactId}${symbol_escape}${symbol_escape}src${symbol_escape}${symbol_escape}main${symbol_escape}${symbol_escape}webapp";
	private static Tomcat tomcat = new Tomcat();
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		System.out.println("Chart-WebSocket-Client系统开始启动中...");
		logg();
		tomcat.setBaseDir("G:${symbol_escape}${symbol_escape}Project8${symbol_escape}${symbol_escape}${artifactId}${symbol_escape}${symbol_escape}");
		tomcat.setPort(8099);
		tomcat.getHost().setAppBase(CONTEXT_PATH);
        try {
        	StandardServer server = (StandardServer)tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            server.addLifecycleListener(listener);
            
            StandardHost sh = (StandardHost) tomcat.getHost();
            sh.setAutoDeploy(true);
            sh.setUnpackWARs(true);
            sh.setDeployOnStartup(true);
            
            tomcat.addWebapp(CONTEXT_PATH, PROJECT_PATH);
		} catch (ServletException e) {
			e.printStackTrace();  
            log.error(e.getMessage());
            throw e;
		}
        System.out.println("本系统端口号为:"+8099);
        try{  
            tomcat.start();
            long endTime = System.currentTimeMillis();
            System.out.println("系统启动完成,总耗时:"+(endTime-startTime)+"ms.");
            tomcat.getServer().await();  
        }catch(LifecycleException e){  
            e.printStackTrace();
            log.error(e.getMessage());  
            throw e;
        }
	}
	
	public static void logg(){
		Properties properties = new Properties();
        properties.setProperty("log4j.rootLogger",  "INFO,application");
        properties.setProperty("log4j.appender.application", "org.apache.log4j.DailyRollingFileAppender");
        properties.setProperty("log4j.appender.application.File", "log/log.log");
        properties.setProperty("log4j.appender.application.Append", "true");
        properties.setProperty("log4j.appender.application.DatePattern", "'.'yyyy-MM-dd");
        properties.setProperty("log4j.appender.application.layout", "org.apache.log4j.PatternLayout");
        properties.setProperty("log4j.appender.application.layout.ConversionPattern", "[%t] %-5p %d{yyyy-MM-dd- HH:mm:ss.SSS} %C{6} (%F:%L) - %m%n");
        PropertyConfigurator.configure(properties);
	}

}
