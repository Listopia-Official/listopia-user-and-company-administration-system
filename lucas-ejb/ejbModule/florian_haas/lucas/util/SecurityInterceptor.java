package florian_haas.lucas.util;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.interceptor.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;

/*
 * The code of this interceptor was taken from:
 * http://balusc.omnifaces.org/2013/01/apache-shiro-is-it-ready-for-java-ee-6.html
 */
@Interceptor
@Secured
public class SecurityInterceptor implements Serializable {

	private static final long serialVersionUID = 3776722784196829617L;

	@AroundInvoke
	public Object interceptShiroSecurity(InvocationContext context) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		Class<?> parentClass = context.getTarget().getClass();
		Method method = context.getMethod();

		if (!subject.isAuthenticated() && hasAnnotation(parentClass, method,
				RequiresAuthentication.class)) { throw new UnauthenticatedException("Authentication required"); }

		if (subject.getPrincipal() != null
				&& hasAnnotation(parentClass, method, RequiresGuest.class)) { throw new UnauthenticatedException("Guest required"); }

		if (subject.getPrincipal() == null
				&& hasAnnotation(parentClass, method, RequiresUser.class)) { throw new UnauthenticatedException("User required"); }

		RequiresRoles roles = getAnnotation(parentClass, method, RequiresRoles.class);

		if (roles != null) {
			subject.checkRoles(Arrays.asList(roles.value()));
		}

		RequiresPermissions permissions = getAnnotation(parentClass, method, RequiresPermissions.class);

		if (permissions != null) {
			subject.checkPermissions(permissions.value());
		}

		return context.proceed();
	}

	private static boolean hasAnnotation(Class<?> c, Method m, Class<? extends Annotation> a) {
		return m.isAnnotationPresent(a) || c.isAnnotationPresent(a) || c.getSuperclass().isAnnotationPresent(a);
	}

	private static <A extends Annotation> A getAnnotation(Class<?> c, Method m, Class<A> a) {
		return m.isAnnotationPresent(a) ? m.getAnnotation(a) : c.isAnnotationPresent(a) ? c.getAnnotation(a) : c.getSuperclass().getAnnotation(a);
	}

}
