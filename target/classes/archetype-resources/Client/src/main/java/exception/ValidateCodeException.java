#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.exception;

import org.apache.shiro.authc.AccountException;

/**
 * @author WMS
 * 
 */
@SuppressWarnings("serial")
public class ValidateCodeException extends AccountException {

	public ValidateCodeException() {
		super();
	}

	public ValidateCodeException(String message) {
		super(message);
	}

	public ValidateCodeException(Throwable cause) {
		super(cause);
	}

	public ValidateCodeException(String message, Throwable cause) {
		super(message, cause);
	}
}
