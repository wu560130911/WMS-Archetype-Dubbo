#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class ForceLogoutFilter extends AccessControlFilter {

	public static final String SESSION_FORCE_LOGOUT_KEY="sessionForceLogout";
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object object) throws Exception {
		Session session = getSubject(request, response).getSession(false);
		if(session==null){
			return true;
		}
		return session.getAttribute(SESSION_FORCE_LOGOUT_KEY)==null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception {
		try{
			getSubject(request, response).logout();
		}catch(Exception e){
			
		}
		String loginUrl = getLoginUrl() + (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;
	}

}
