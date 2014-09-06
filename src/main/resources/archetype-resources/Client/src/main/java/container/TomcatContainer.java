#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.container;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.JreMemoryLeakPreventionListener;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.core.ThreadLocalLeakPreventionListener;
import org.apache.catalina.startup.Tomcat;
import org.springframework.util.ResourceUtils;

import com.alibaba.dubbo.container.Container;
import ${package}.start.Main;
import ${package}.utils.SystemProps;

/**
 * @author WMS
 * 
 */
public class TomcatContainer implements Container {

	public static final String SERVER_PORT = "server.port";
	public static final String SERVER_CONTEXT = "server.context";
	public static final String SERVER_WORKPATH = "server.workpath";
	public static final String SERVER_PROJECTPATH = "server.projectpath";
	public static final String SERVER_USESSL = "server.useSSL";
	public static final String SERVER_KEYSTORE = "server.keyStore";
	public static final String SERVER_KEYSTOREPASS = "server.keystorePass";
	public static final String SERVER_SSLPORT = "server.sslPort";
	public static final int DEFAULT_SERVER_PORT = 8099;

	@Override
	public void start() {
		String serverPort = SystemProps.getValue(SERVER_PORT);
		int port;
		if (serverPort == null || serverPort.length() == 0) {
			port = DEFAULT_SERVER_PORT;
		} else {
			try {
				port = Integer.parseInt(serverPort);
			} catch (NumberFormatException e) {
				port = DEFAULT_SERVER_PORT;
			}
		}
		String projectPath = SystemProps.getValue(SERVER_PROJECTPATH);
		String contextPath = SystemProps.getValue(SERVER_CONTEXT);
		String basePath = SystemProps.getValue(SERVER_WORKPATH);
		Tomcat tomcat = Main.tomcat = new Tomcat();
		tomcat.setBaseDir(basePath);
		tomcat.setPort(port);
		tomcat.getHost().setAppBase(contextPath);
		try {
			StandardServer server = (StandardServer) tomcat.getServer();

			AprLifecycleListener aprLifecycle = new AprLifecycleListener();
			JreMemoryLeakPreventionListener jreMemoryleak = new JreMemoryLeakPreventionListener();
			ThreadLocalLeakPreventionListener threadLocalLeak = new ThreadLocalLeakPreventionListener();

			server.addLifecycleListener(aprLifecycle);
			server.addLifecycleListener(jreMemoryleak);
			server.addLifecycleListener(threadLocalLeak);

			StandardHost sh = (StandardHost) tomcat.getHost();
			sh.setAutoDeploy(true);
			sh.setUnpackWARs(true);
			sh.setDeployOnStartup(true);
			tomcat.getConnector().setURIEncoding("UTF-8");

			if (SystemProps.getValue(SERVER_USESSL, "false").equalsIgnoreCase(
					"true")) {

				StandardService service = (StandardService) tomcat.getService();
				Connector https = new Connector();
				https.setURIEncoding("UTF-8");

				int sslport;
				try {
					sslport = Integer.parseInt(SystemProps.getValue(
							SERVER_SSLPORT, "8443"));
				} catch (NumberFormatException e) {
					sslport = 8443;
				}

				https.setPort(sslport);
				https.setProtocol("HTTP/1.1");
				https.setScheme("https");
				https.setAttribute("SSLEnabled", "true");
				https.setAttribute("secure", "true");
				https.setAttribute(
						"keystoreFile",
						ResourceUtils.getFile(
								"classpath:"
										+ SystemProps.getValue(SERVER_KEYSTORE)
										+ "").getAbsolutePath());
				https.setAttribute("clientAuth", "false");
				https.setAttribute("sslProtocol", "TLS");
				https.setAttribute("keystorePass",
						SystemProps.getValue(SERVER_KEYSTOREPASS));
				service.addConnector(https);
			}

			tomcat.addWebapp(contextPath, projectPath);
		} catch (Exception e) {
			System.err.println("错误!服务器启动失败,请检查您的配置.");
			e.printStackTrace();
			try {
				tomcat.stop();
			} catch (LifecycleException e1) {
				e1.printStackTrace();
			}
		}
		try {
			tomcat.start();
		} catch (LifecycleException e) {
			System.err.println("错误!服务器启动失败,请检查您的配置.");
			e.printStackTrace();
			if (tomcat != null) {
				try {
					tomcat.stop();
				} catch (LifecycleException e1) {
					e1.printStackTrace();
				} finally {
					tomcat = null;
				}
			}
		}

	}

	@Override
	public void stop() {
		if (Main.tomcat != null) {
			try {
				Main.tomcat.stop();
			} catch (LifecycleException e1) {
				e1.printStackTrace();
			} finally {
				Main.tomcat = null;
			}
		}
	}

}
