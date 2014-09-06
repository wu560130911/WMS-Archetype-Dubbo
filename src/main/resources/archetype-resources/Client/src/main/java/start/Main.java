#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.start;

import java.util.List;

import org.apache.catalina.startup.Tomcat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.dubbo.container.Container;
import ${package}.utils.SystemProps;


/**
 * @author WMS
 *
 */
public class Main {

	private static final Log logger = LogFactory.getLog(Main.class);
	
	public static final String SYSTEM_NAME="server.name";
	
	public static Tomcat tomcat = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		
		if(!SystemProps.InitProps()){
			System.err.println("系统配置文件丢失,系统启动失败.");
			System.exit(1);
		}
		System.out.println(SystemProps.getValue(SYSTEM_NAME)+"系统开始启动中...");
		System.out.println("系统启动时间:"+SystemProps.getDateFormatString(startTime));
		
		final List<Container> containers = SystemProps.getAllContainers();
		for(Container container:containers){
			container.start();
			logger.info("WMS-Project " + container.getClass().getSimpleName() + " started!");
		}
		if(tomcat==null){
			System.err.println("错误!服务器启动失败,请检查您的配置.");
			System.exit(1);
		}
		
		System.out.println("本系统端口号为:"+tomcat.getConnector().getPort());
		long endTime = System.currentTimeMillis();
        System.out.println("系统启动完成时间:"+SystemProps.getDateFormatString(endTime));
        System.out.println("系统启动完成,总耗时:"+(endTime-startTime)+"ms.");
        tomcat.getServer().await();
        
	}

}
