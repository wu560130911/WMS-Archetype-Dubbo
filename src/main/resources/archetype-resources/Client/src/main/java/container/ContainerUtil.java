#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.container;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.alibaba.dubbo.container.Container;

/**
 * @author WMS
 * 
 */
public class ContainerUtil {

	/**
	 * Container配置文件的位置
	 */
	public static final String CONTAINER_FILE_PATH = "container.properties";

	// 不需要多线程访问，所以这儿不需要使用ConcurrentHashMap
	public static Map<String, String> containerClass = new HashMap<String, String>();

	/**
	 * 初始化配置文件，并将配置文件配置的所有的Container实例保存在containerClass中。
	 * 
	 * @return 返回是否加载成功
	 */
	public static boolean init() {
		Properties pro = new Properties();
		try {
			InputStream is = ContainerUtil.class
					.getResourceAsStream(CONTAINER_FILE_PATH);
			pro.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		String key = null, value = null;
		Iterator<Entry<Object, Object>> it = pro.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = it.next();
			key = String.valueOf(entry.getKey());
			value = String.valueOf(entry.getValue());
			if (key != null && key.length() > 0 && value != null
					&& value.length() > 0) {
				containerClass.put(key, value);
			}
		}
		return true;
	}

	/**
	 * 获取指定Container的实例
	 * 
	 * @param key
	 * @return 返回指定实例化的Container，如果不存在，则返回空
	 */
	public static Container getContainer(String key) {
		String className = containerClass.get(key);
		if (className != null) {
			try {
				/**
				 * 通过反射原理实例化Container
				 */
				Container container = (Container) ContainerUtil.class
						.getClassLoader().loadClass(className).newInstance();
				return container;
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
