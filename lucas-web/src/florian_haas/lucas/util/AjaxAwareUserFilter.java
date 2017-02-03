package florian_haas.lucas.util;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.UserFilter;

/*
 * This was taken from
 * http://balusc.omnifaces.org/2013/01/apache-shiro-is-it-ready-for-java-ee-6.html
 */
public class AjaxAwareUserFilter extends UserFilter {

	private static final String FACES_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<partial-response><redirect url=\"%s\"></redirect></partial-response>";

	@Override
	protected void redirectToLogin(ServletRequest req, ServletResponse res) throws IOException {
		HttpServletRequest request = (HttpServletRequest) req;

		if ("partial/ajax".equals(request.getHeader("Faces-Request"))) {
			res.setContentType("text/xml");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().printf(FACES_REDIRECT_XML, request.getContextPath() + getLoginUrl());
		} else {
			super.redirectToLogin(req, res);
		}
	}

}
