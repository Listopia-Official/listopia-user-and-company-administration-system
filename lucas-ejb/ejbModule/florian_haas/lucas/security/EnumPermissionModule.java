package florian_haas.lucas.security;

public enum EnumPermissionModule {

	ACCOUNT("account");

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
