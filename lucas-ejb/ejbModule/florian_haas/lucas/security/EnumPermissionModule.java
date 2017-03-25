package florian_haas.lucas.security;

public enum EnumPermissionModule {

	ACCOUNT("account"),
	ATTENDANCE("attendance"),
	COMPANY("company"),
	EMPLOYMENT("employment"),
	ENTITY("entity"),
	GLOBAL_DATA("globalData"),
	ITEM("item"),
	LOGIN("login"),
	LOGIN_ROLE("loginRole"),
	LOGIN_USER("loginUser"),
	USER("user");

	private String moduleName;

	private EnumPermissionModule(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

	@Override
	public String toString() {
		return moduleName;
	}

}
