package florian_haas.lucas.security;

import java.util.Collection;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.*;
import org.apache.shiro.realm.Realm;
import org.slf4j.*;

public final class ExtendedModularRealmAuthenticator extends ModularRealmAuthenticator {

	private static final Logger log = LoggerFactory.getLogger(ExtendedModularRealmAuthenticator.class);

	@Override
	protected AuthenticationInfo doMultiRealmAuthentication(Collection<Realm> realms, AuthenticationToken token) {
		AuthenticationStrategy strategy = getAuthenticationStrategy();

		AuthenticationInfo aggregate = strategy.beforeAllAttempts(realms, token);

		if (log.isTraceEnabled()) {
			log.trace("Iterating through {} realms for PAM authentication", Integer.valueOf(realms.size()));
		}

		for (Realm realm : realms) {
			try {
				aggregate = strategy.beforeAttempt(realm, token, aggregate);
			}
			catch (BreakAuthenticationException breakException) {
				break;
			}

			if (realm.supports(token)) {
				log.trace("Attempting to authenticate token [{}] using realm [{}]", token, realm);

				AuthenticationInfo info = null;
				Throwable t = null;
				try {
					info = realm.getAuthenticationInfo(token);
				}
				catch (Throwable throwable) {
					t = throwable;
					if (log.isWarnEnabled()) {
						String msg = "Realm [" + realm + "] threw an exception during a multi-realm authentication attempt:";
						log.warn(msg, t);
					}
				}

				aggregate = strategy.afterAttempt(realm, token, info, aggregate, t);
			} else {
				log.debug("Realm [{}] does not support token {}.  Skipping realm.", realm, token);
			}
		}

		aggregate = strategy.afterAllAttempts(token, aggregate);

		return aggregate;
	}

}
