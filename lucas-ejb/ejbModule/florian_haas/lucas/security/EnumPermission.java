package florian_haas.lucas.security;

import static florian_haas.lucas.security.EnumPermissionModule.*;

import java.util.ArrayList;

public enum EnumPermission {

	ALL(null, "*"),

	ACCOUNT_ALL(ACCOUNT, "*"),
	ACCOUNT_PAY_IN(ACCOUNT, "payIn"),
	ACCOUNT_PAY_OUT(ACCOUNT, "payOut"),
	ACCOUNT_TRANSACTION(ACCOUNT, "transaction"),
	ACCOUNT_BLOCK(ACCOUNT, "block"),
	ACCOUNT_UNBLOCK(ACCOUNT, "unblock"),
	ACCOUNT_PROTECT(ACCOUNT, "protect"),
	ACCOUNT_UNPROTECT(ACCOUNT, "unprotect"),
	ACCOUNT_FIND_ALL(ACCOUNT, "findAll"),
	ACCOUNT_FIND_BY_ID(ACCOUNT, "findById"),
	ACCOUNT_FIND_DYNAMIC(ACCOUNT, "findDynamic"),
	ACCOUNT_TRANSACTION_FROM_PROTECTED(ACCOUNT, "transactionFromProtected"),
	ACCOUNT_TRANSACTION_TO_PROTECTED(ACCOUNT, "transactionToProtected"),
	ACCOUNT_IGNORE_TRANSACTION_LIMIT(ACCOUNT, "ignoreTransactionLimit"),
	ACCOUNT_FIND_ACCOUNT_OWNER(ACCOUNT, "findAccountOwner"),
	ACCOUNT_ACCESS_VIEW(ACCOUNT, "accessView"),
	ACCOUNT_VIEW_TRANSACTION_LOGS(ACCOUNT, "viewTransactionLogs"),

	ATTENDANCE_ALL(ATTENDANCE, "*"),
	ATTENDANCE_SCAN(ATTENDANCE, "scan"),
	ATTENDANCE_EVALUATE_ALL(ATTENDANCE, "evaluateAll"),
	ATTENDANCE_FIND_ALL(ATTENDANCE, "findAll"),
	ATTENDANCE_FIND_BY_ID(ATTENDANCE, "findById"),
	ATTENDANCE_FIND_BY_USER_CARD_ID(ATTENDANCE, "findByUserCardId"),
	ATTENDANCE_FIND_DYNAMIC(ATTENDANCE, "findDynamic"),

	COMPANY_ALL(COMPANY, "*"),
	COMPANY_CREATE(COMPANY, "create"),
	COMPANY_FIND_ALL(COMPANY, "findAll"),
	COMPANY_FIND_BY_ID(COMPANY, "findById"),
	COMPANY_FIND_DYNAMIC(COMPANY, "findDynamic"),
	COMPANY_SET_NAME(COMPANY, "setName"),
	COMPANY_SET_DESCRIPTION(COMPANY, "setDescription"),
	COMPANY_SET_ROOM(COMPANY, "setRoom"),
	COMPANY_SET_SECTION(COMPANY, "setSection"),
	COMPANY_SET_COMPANY_TYPE(COMPANY, "setCompanyType"),
	COMPANY_SET_PARENT_COMPANY(COMPANY, "setParentCompany"),
	COMPANY_REMOVE_PARENT_COMPANY(COMPANY, "removeParentCompany"),
	COMPANY_SET_REQUIRED_EMPLOYEES_COUNT(COMPANY, "setRequiredEmployeesCount"),
	COMPANY_ADD_TAXDATA(COMPANY, "addTaxdata"),
	COMPANY_REMOVE_TAXDATA(COMPANY, "removeTaxdata"),
	COMPANY_SET_INCOMINGS(COMPANY, "setIncomings"),
	COMPANY_SET_OUTGOINGS(COMPANY, "setOutgoings"),
	COMPANY_ADD_COMPANY_CARD(COMPANY, "addCompanyCard"),
	COMPANY_BLOCK_COMPANY_CARD(COMPANY, "blockCompanyCard"),
	COMPANY_UNBLOCK_COMPANY_CARD(COMPANY, "unblockCompanyCard"),

	EMPLOYMENT_ALL(EMPLOYMENT, "*"),
	EMPLOYMENT_CREATE_DEFAULT(EMPLOYMENT, "createDefault"),
	EMPLOYMENT_CREATE_ADVANCED(EMPLOYMENT, "createAdvanced"),
	EMPLOYMENT_REMOVE(EMPLOYMENT, "remove"),
	EMPLOYMENT_SET_EMPLOYEE_POSITION(EMPLOYMENT, "setEmployeePosition"),
	EMPLOYMENT_SET_SALARY_CLASS(EMPLOYMENT, "setSalaryClass"),
	EMPLOYMENT_ADD_WORK_SHIFT(EMPLOYMENT, "addWorkShift"),
	EMPLOYMENT_REMOVE_WORK_SHIFT(EMPLOYMENT, "removeWorkShift"),
	EMPLOYMENT_ADD_ATTENDANCEDATA(EMPLOYMENT, "addAttendancedata"),
	EMPLOYMENT_SET_ATTENDANCEDATA_DATE(EMPLOYMENT, "setAttendancedataDate"),
	EMPLOYMENT_SET_ATTENDANCEDATA_WAS_PRESENT(EMPLOYMENT, "setAttendancedataWasPresent"),
	EMPLOYMENT_REMOVE_ATTENDANCEDATA(EMPLOYMENT, "remvoeAttendancedata"),
	EMPLOYMENT_PAY_SALARY(EMPLOYMENT, "paySalary"),

	ENTITY_ALL(ENTITY, "*"),
	ENTITY_EXISTS(ENTITY, "exists"),

	GLOBAL_DATA_ALL(GLOBAL_DATA, "*"),
	GLOBAL_DATA_GET_SALARIES(GLOBAL_DATA, "getSalaries"),
	GLOBAL_DATA_GET_MIN_TIME_PRESENT(GLOBAL_DATA, "getMinTimePresent"),
	GLOBAL_DATA_GET_MINIMUM_WAGE(GLOBAL_DATA, "getMinimumWage"),
	GLOBAL_DATA_SET_SALARY(GLOBAL_DATA, "setSalary"),
	GLOBAL_DATA_SET_MIN_TIME_PRESENT(GLOBAL_DATA, "setMinTimePresent"),
	GLOBAL_DATA_SET_MINIMUM_WAGE(GLOBAL_DATA, "setMinimumWage"),
	GLOBAL_DATA_GET_WAREHOUSE(GLOBAL_DATA, "getWarehouse"),
	GLOBAL_DATA_SET_WAREHOUSE(GLOBAL_DATA, "setWarehouse"),
	GLOBAL_DATA_GET_INSTANCE(GLOBAL_DATA, "getInstance"),
	GLOBAL_DATA_GET_TRANSACTION_LIMIT(GLOBAL_DATA, "getTransactionLimit"),
	GLOBAL_DATA_SET_TRANSACTION_LIMIT(GLOBAL_DATA, "setTransactionLimit"),
	GLOBAL_DATA_GET_CURRENCY_SYMBOL(GLOBAL_DATA, "getCurrencySymbol"),
	GLOBAL_DATA_SET_CURRENCY_SYMBOL(GLOBAL_DATA, "setCurrencySymbol"),
	GLOBAL_DATA_GET_MAX_IDLE_TIME(GLOBAL_DATA, "getMaxIdleTime"),
	GLOBAL_DATA_SET_MAX_IDLE_TIME(GLOBAL_DATA, "setMaxIdleTime"),
	GLOBAL_DATA_GET_MAX_USER_IMAGE_WIDTH(GLOBAL_DATA, "getMaxUserImageWidth"),
	GLOBAL_DATA_SET_MAX_USER_IMAGE_WIDTH(GLOBAL_DATA, "setMaxUserImageWidth"),
	GLOBAL_DATA_GET_MAX_USER_IMAGE_HEIGHT(GLOBAL_DATA, "getMaxUserImageHeight"),
	GLOBAL_DATA_SET_MAX_USER_IMAGE_HEIGHT(GLOBAL_DATA, "setMaxUserImageHeight"),

	ITEM_ALL(ITEM, "*"),
	ITEM_SELL(ITEM, "sell"),
	ITEM_FIND_ALL(ITEM, "findAll"),
	ITEM_FIND_BY_ID(ITEM, "findById"),
	ITEM_FIND_DYNAMIC(ITEM, "findDynamic"),
	ITEM_SET_NAME(ITEM, "setName"),
	ITEM_SET_DESCRIPTION(ITEM, "setDescription"),
	ITEM_SET_PRICE_PER_ITEM(ITEM, "setPricePerItem"),
	ITEM_ADD_ITEMS_AVAIBLE(ITEM, "addItemsAvaible"),
	ITEM_SUB_ITEMS_AVAIBLE(ITEM, "subItemsAvaible"),
	ITEM_CREATE(ITEM, "create"),

	USER_ALL(USER, "*"),
	USER_CREATE_PUPIL(USER, "createPupil"),
	USER_CREATE_TEACHER(USER, "createTeacher"),
	USER_CREATE_GUEST(USER, "createGuest"),
	USER_IMPORT_PUPILS(USER, "importPupils"),
	USER_IMPORT_TEACHERS(USER, "importTeachers"),
	USER_FIND_ALL(USER, "findAll"),
	USER_FIND_BY_ID(USER, "findById"),
	USER_FIND_DYNAMIC(USER, "findDynamic"),
	USER_SET_FORENAME(USER, "setForename"),
	USER_SET_SURNAME(USER, "setSurname"),
	USER_SET_SCHOOL_CLASS(USER, "setSchoolClass"),
	USER_ADD_RANK(USER, "addRank"),
	USER_REMOVE_RANK(USER, "removeRank"),
	USER_ADD_USER_CARD(USER, "addUserCard"),
	USER_REMOVE_USER_CARD(USER, "removeUserCard"),
	USER_BLOCK_USER_CARD(USER, "blockUSerCard"),
	USER_UNBLOCK_USER_CARD(USER, "unblockUserCard"),
	USER_SET_VALID_DATE_USER_CARD(USER, "setValidDate"),
	USER_FIND_USER_CARD_BY_ID(USER, "findUserCardById"),
	USER_SET_IMAGE(USER, "setImage"),
	USER_GET_IMAGE_FROM_ID(USER, "getImageFromId"),
	USER_GET_USER_CARDS(USER, "getUserCards"),
	USER_ACCESS_VIEW(USER, "accessView"),

	LOGIN_ALL(LOGIN, "*"),
	LOGIN_CREATE_RAW_LOGIN_USER(LOGIN, "createRawLoginUser"),
	LOGIN_CREATE_REGISTERED_LOGIN_USER(LOGIN, "createRegisteredLoginUser"),
	LOGIN_CHANGE_PASSWORD(LOGIN, "changePassword"),
	LOGIN_NEW_PASSWORD(LOGIN, "newPassword"),
	LOGIN_ROLE_ACCESS_VIEW(LOGIN_ROLE, "accessView"),
	LOGIN_ROLE_FIND_DYNAMIC(LOGIN_ROLE, "findDynamic"),
	LOGIN_USER_FIND_DYNAMIC(LOGIN, "findDynamic"),
	LOGIN_USER_ACCESS_VIEW(LOGIN, "accessView");

	private String permissionString;

	private EnumPermission(EnumPermissionModule module, String permissionString) {
		this.permissionString = (module != null ? module.getModuleName() + ":" : "") + permissionString;
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
