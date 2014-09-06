#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.alibaba.dubbo.container.Container;
import ${package}.container.ContainerUtil;
import ${package}.realm.UserRealm.ShiroUser;


/**
 * @author WMS
 * 
 */
public class SystemProps {

	public static final String SERVER_FILE = "server.properties";

	public static final String SERVER_CONTAINER = "server.container";

	public static Properties props = null;

	public static final String TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
	
	public static boolean InitProps() {
		try {
			props = PropertiesLoaderUtils.loadAllProperties(SERVER_FILE);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return props != null && ContainerUtil.init();
	}

	public static String getValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static String getValue(String key) {
		return getValue(key, null);
	}

	public static List<Container> getAllContainers() {

		String containss = getValue(SERVER_CONTAINER);
		if (containss != null) {
			List<Container> containers = new ArrayList<Container>();
			String[] contain = containss.split(",");
			for (String c : contain) {
				Container container = ContainerUtil.getContainer(c);
				if (container != null) {
					containers.add(container);
				}
			}
			return containers;
		}
		return null;
	}

	
	public static String getDateFormatString(Date date){
		return sdf.format(date);
	}
	
	public static String getDateFormatString(long times){
		return sdf.format(times);
	}
	
	public static String getCurrentlyUser(){
		Subject subject = SecurityUtils.getSubject();
		if(subject==null){
			return null;
		}
		ShiroUser su = (ShiroUser)subject.getPrincipal();
		return su.loginName;
	}
}
