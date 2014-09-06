#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ${package}.model.User;

/**
 * @author WMS
 *
 */
public interface UserService {

	public void save(User user);
	
	public void delete(User user);
	
	public void update(User user);
	
	public User findById(String id);
	
	public Page<User> list(Pageable pageable);
	
	public void updateState(User user);
}
