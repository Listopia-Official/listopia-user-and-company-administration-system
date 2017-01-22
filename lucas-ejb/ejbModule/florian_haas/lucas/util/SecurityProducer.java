package florian_haas.lucas.util;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;

@Singleton
public class SecurityProducer {
	private org.apache.shiro.mgt.SecurityManager securityManager;

	@PostConstruct
	public void init() {
		final String iniFile = "/shiro.ini";
		securityManager = new IniSecurityManagerFactory(iniFile).getInstance();
		SecurityUtils.setSecurityManager(securityManager);
	}

	@Produces
	public org.apache.shiro.mgt.SecurityManager getSecurityManager() {
		return securityManager;
	}

	@Produces
	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}
}
