package florian_haas.lucas.security;

import java.util.ArrayList;

public enum EnumPermission {

	ACCOUNT_PAYIN("account:payIn"), ACCOUNT_PAYOUT("account:payOut"), ACCOUNT_TRANSACTION("account:transaction");

	private String permissionString;

	private EnumPermission(String permissionString) {
		this.permissionString = permissionString;
	}

	public String getPermissionString() {
		return permissionString;
	}

	public static String[] getPermissionStringsFromArray(EnumPermission[] permissions) {
		ArrayList<String> list = new ArrayList<>();
		for (EnumPermission permission : permissions) {
			list.add(permission.permissionString);
		}
		return list.toArray(new String[list.size()]);
	}

	@Override
	public String toString() {
		return permissionString;
	}

}
