package florian_haas.lucas.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

public final class ExtendedFirstSuccessfullAuthenticationStrategy extends FirstSuccessfulStrategy {

	private boolean stopAfterFirstSuccess = true;

	public void setStopAfterFirstSuccess(boolean stopAfterFirstSuccess) {
		this.stopAfterFirstSuccess = stopAfterFirstSuccess;
	}

	public boolean getStopAfterFirstSuccess() {
		return stopAfterFirstSuccess;
	}

	public AuthenticationInfo beforeAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
		if (stopAfterFirstSuccess && aggregate != null && !isEmpty(aggregate.getPrincipals())) throw new BreakAuthenticationException();
		return aggregate;
	}

	private static boolean isEmpty(PrincipalCollection pc) {
		return pc == null || pc.isEmpty();
	}
}
