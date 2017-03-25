package florian_haas.lucas.web;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.*;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.apache.shiro.SecurityUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.*;
import org.primefaces.model.*;

import florian_haas.lucas.business.*;
import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.util.Utils;
import florian_haas.lucas.util.validation.*;
import florian_haas.lucas.web.util.WebUtils;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = -2324504686340886417L;

	@EJB
	private UserBeanLocal userBean;

	@EJB
	private GlobalDataBeanLocal globalDataBean;

	/*
	 * -------------------- Create Pupil Dialog Start --------------------
	 */

	@NotBlank
	private String createPupilDialogForename = null;

	@NotBlank
	private String createPupilDialogSurname = null;

	@NotNull
	private EnumSchoolClass createPupilDialogSchoolClass = EnumSchoolClass.A5;

	@NotBlank
	private String createPupilDialogTmpRank = null;

	private String createPupilDialogSelectedRank = null;

	private List<@TypeNotNull @NotBlankString String> createPupilDialogRanks = new ArrayList<>();

	public String getCreatePupilDialogForename() {
		return createPupilDialogForename;
	}

	public void setCreatePupilDialogForename(String createPupilDialogForename) {
		this.createPupilDialogForename = createPupilDialogForename;
	}

	public String getCreatePupilDialogSurname() {
		return createPupilDialogSurname;
	}

	public void setCreatePupilDialogSurname(String createPupilDialogSurname) {
		this.createPupilDialogSurname = createPupilDialogSurname;
	}

	public String getCreatePupilDialogTmpRank() {
		return createPupilDialogTmpRank;
	}

	public void setCreatePupilDialogTmpRank(String createPupilDialogTmpRank) {
		this.createPupilDialogTmpRank = createPupilDialogTmpRank;
	}

	public List<@TypeNotNull @NotBlankString String> getCreatePupilDialogRanks() {
		return createPupilDialogRanks;
	}

	public void setCreatePupilDialogRanks(List<String> createPupilDialogRanks) {
		this.createPupilDialogRanks = createPupilDialogRanks;
	}

	public void resetCreatePupilDialogTmpRank() {
		createPupilDialogTmpRank = null;
	}

	public String getCreatePupilDialogSelectedRank() {
		return createPupilDialogSelectedRank;
	}

	public void setCreatePupilDialogSelectedRank(String createPupilDialogSelectedRank) {
		this.createPupilDialogSelectedRank = createPupilDialogSelectedRank;
	}

	public EnumSchoolClass getCreatePupilDialogSchoolClass() {
		return createPupilDialogSchoolClass;
	}

	public void setCreatePupilDialogSchoolClass(EnumSchoolClass createPupilDialogSchoolClass) {
		this.createPupilDialogSchoolClass = createPupilDialogSchoolClass;
	}

	public void createPupil() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(userBean.findById(
					userBean.createPupil(createPupilDialogForename, createPupilDialogSurname, createPupilDialogSchoolClass, createPupilDialogRanks)),
					"lucas:userStringConverter"));
			return true;
		}, "lucas.application.userScreen.createPupil.message");
	}

	public void resetCreatePupilDialog() {
		createPupilDialogSchoolClass = EnumSchoolClass.A5;
		createPupilDialogRanks.clear();
	}

	public void editCreatePupilDialogSelectedRank() {
		if (createPupilDialogSelectedRank != null) {
			this.createPupilDialogRanks.remove(createPupilDialogSelectedRank);
			this.createPupilDialogTmpRank = createPupilDialogSelectedRank;
			createPupilDialogSelectedRank = null;
		}
	}

	/*
	 * -------------------- Create Pupil Dialog End --------------------
	 */

	/*
	 * -------------------- Create Teacher Dialog Start --------------------
	 */

	@NotBlank
	private String createTeacherDialogForename = null;

	@NotBlank
	private String createTeacherDialogSurname = null;

	@NotBlank
	private String createTeacherDialogTmpRank = null;

	private String createTeacherDialogSelectedRank = null;

	private List<@TypeNotNull @NotBlankString String> createTeacherDialogRanks = new ArrayList<>();

	public String getCreateTeacherDialogForename() {
		return createTeacherDialogForename;
	}

	public void setCreateTeacherDialogForename(String createTeacherDialogForename) {
		this.createTeacherDialogForename = createTeacherDialogForename;
	}

	public String getCreateTeacherDialogSurname() {
		return createTeacherDialogSurname;
	}

	public void setCreateTeacherDialogSurname(String createTeacherDialogSurname) {
		this.createTeacherDialogSurname = createTeacherDialogSurname;
	}

	public String getCreateTeacherDialogTmpRank() {
		return createTeacherDialogTmpRank;
	}

	public void setCreateTeacherDialogTmpRank(String createTeacherDialogTmpRank) {
		this.createTeacherDialogTmpRank = createTeacherDialogTmpRank;
	}

	public String getCreateTeacherDialogSelectedRank() {
		return createTeacherDialogSelectedRank;
	}

	public void setCreateTeacherDialogSelectedRank(String createTeacherDialogSelectedRank) {
		this.createTeacherDialogSelectedRank = createTeacherDialogSelectedRank;
	}

	public List<@TypeNotNull @NotBlankString String> getCreateTeacherDialogRanks() {
		return createTeacherDialogRanks;
	}

	public void setCreateTeacherDialogRanks(List<@TypeNotNull @NotBlankString String> createTeacherDialogRanks) {
		this.createTeacherDialogRanks = createTeacherDialogRanks;
	}

	public void resetCreateTeacherDialogTmpRank() {
		createTeacherDialogTmpRank = null;
	}

	public void createTeacher() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(
					userBean.findById(userBean.createTeacher(createTeacherDialogForename, createTeacherDialogSurname, createTeacherDialogRanks)),
					"lucas:userStringConverter"));
			return true;
		}, "lucas.application.userScreen.createTeacher.message");
	}

	public void resetCreateTeacherDialog() {
		createTeacherDialogRanks.clear();
	}

	public void editCreateTeacherDialogSelectedRank() {
		if (createTeacherDialogSelectedRank != null) {
			this.createTeacherDialogRanks.remove(createTeacherDialogSelectedRank);
			this.createTeacherDialogTmpRank = createTeacherDialogSelectedRank;
			createTeacherDialogSelectedRank = null;
		}
	}

	/*
	 * -------------------- Create Pupil Dialog End --------------------
	 */

	/*
	 * -------------------- Create Guest Dialog Start --------------------
	 */

	@NotNull
	@Min(1)
	@Max(250)
	private Integer createGuestDialogGuestCount = 1;

	public Integer getCreateGuestDialogGuestCount() {
		return createGuestDialogGuestCount;
	}

	public void setCreateGuestDialogGuestCount(Integer createGuestDialogGuestCount) {
		this.createGuestDialogGuestCount = createGuestDialogGuestCount;
	}

	public void createGuests() {
		int createdGuests = 0;
		for (int i = 0; i < createGuestDialogGuestCount; i++) {
			if (WebUtils.executeTask((params) -> {
				userBean.createGuest();
				return true;
			}, false, false, "lucas.application.userScreen.createGuest.message")) {
				createdGuests++;
			}
		}
		WebUtils.addDefaultTranslatedInformationMessage("lucas.application.userScreen.createGuest.message.success", createdGuests);
	}

	/*
	 * -------------------- Create Guest Dialog End --------------------
	 */

	@NotNull
	private Boolean useSearchUserId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserIdComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Long searchUserId = 0l;

	@NotNull
	private Boolean useSearchUserForename = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchUserForenameComparator = EnumQueryComparator.EQUAL;

	private String searchUserForename;

	@NotNull
	private Boolean useSearchUserSurname = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchUserSurnameComparator = EnumQueryComparator.EQUAL;

	private String searchUserSurname;

	@NotNull
	private Boolean useSearchUserType = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.LOGIC)
	private EnumQueryComparator searchUserTypeComparator = EnumQueryComparator.EQUAL;

	@NotNull
	private EnumUserType searchUserType = EnumUserType.PUPIL;

	@NotNull
	private Boolean useSearchUserSchoolGrade = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserSchoolGradeComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Integer searchUserSchoolGrade = EnumSchoolClass.A5.getGrade();

	@NotNull
	private Boolean useSearchUserSchoolClass = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchUserSchoolClassComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchUserSchoolClass = EnumSchoolClass.A5.getSchoolClass();

	@NotNull
	private Boolean useSearchUserRanks = Boolean.FALSE;

	private List<@TypeNotNull @NotBlankString String> searchUserRanks = new ArrayList<>();

	@QueryComparator(category = EnumQueryComparatorCategory.ARRAY)
	private EnumQueryComparator searchUserRanksComparator = EnumQueryComparator.MEMBER_OF;

	private List<User> searchUserResults = new ArrayList<>();

	private List<User> selectedUsers = new ArrayList<>();

	public Long getSearchUserId() {
		return searchUserId;
	}

	public void setSearchUserId(Long searchUserId) {
		this.searchUserId = searchUserId;
	}

	public Boolean getUseSearchUserId() {
		return useSearchUserId;
	}

	public void setUseSearchUserId(Boolean useSearchUserId) {
		this.useSearchUserId = useSearchUserId;
	}

	public String getSearchUserForename() {
		return searchUserForename;
	}

	public void setSearchUserForename(String searchUserForename) {
		this.searchUserForename = searchUserForename;
	}

	public String getSearchUserSurname() {
		return searchUserSurname;
	}

	public void setSearchUserSurname(String searchUserSurname) {
		this.searchUserSurname = searchUserSurname;
	}

	public Boolean getUseSearchUserForename() {
		return useSearchUserForename;
	}

	public void setUseSearchUserForename(Boolean useSearchUserForename) {
		this.useSearchUserForename = useSearchUserForename;
	}

	public Boolean getUseSearchUserSurname() {
		return useSearchUserSurname;
	}

	public void setUseSearchUserSurname(Boolean useSearchUserSurname) {
		this.useSearchUserSurname = useSearchUserSurname;
	}

	public EnumQueryComparator getSearchUserIdComparator() {
		return searchUserIdComparator;
	}

	public void setSearchUserIdComparator(EnumQueryComparator searchUserIdComparator) {
		this.searchUserIdComparator = searchUserIdComparator;
	}

	public EnumQueryComparator getSearchUserForenameComparator() {
		return searchUserForenameComparator;
	}

	public void setSearchUserForenameComparator(EnumQueryComparator searchUserForenameComparator) {
		this.searchUserForenameComparator = searchUserForenameComparator;
	}

	public EnumQueryComparator getSearchUserSurnameComparator() {
		return searchUserSurnameComparator;
	}

	public void setSearchUserSurnameComparator(EnumQueryComparator searchUserSurnameComparator) {
		this.searchUserSurnameComparator = searchUserSurnameComparator;
	}

	public Boolean getUseSearchUserType() {
		return useSearchUserType;
	}

	public void setUseSearchUserType(Boolean useSearchUserType) {
		this.useSearchUserType = useSearchUserType;
	}

	public EnumQueryComparator getSearchUserTypeComparator() {
		return searchUserTypeComparator;
	}

	public void setSearchUserTypeComparator(EnumQueryComparator searchUserTypeComparator) {
		this.searchUserTypeComparator = searchUserTypeComparator;
	}

	public EnumUserType getSearchUserType() {
		return searchUserType;
	}

	public void setSearchUserType(EnumUserType searchUserType) {
		this.searchUserType = searchUserType;
	}

	public Boolean getUseSearchUserSchoolGrade() {
		return this.useSearchUserSchoolGrade;
	}

	public void setUseSearchUserSchoolGrade(Boolean useSearchUserSchoolGrade) {
		this.useSearchUserSchoolGrade = useSearchUserSchoolGrade;
	}

	public EnumQueryComparator getSearchUserSchoolGradeComparator() {
		return this.searchUserSchoolGradeComparator;
	}

	public void setSearchUserSchoolGradeComparator(EnumQueryComparator searchUserSchoolGradeComparator) {
		this.searchUserSchoolGradeComparator = searchUserSchoolGradeComparator;
	}

	public Integer getSearchUserSchoolGrade() {
		return this.searchUserSchoolGrade;
	}

	public void setSearchUserSchoolGrade(Integer searchUserSchoolGrade) {
		this.searchUserSchoolGrade = searchUserSchoolGrade;
	}

	public Boolean getUseSearchUserSchoolClass() {
		return this.useSearchUserSchoolClass;
	}

	public void setUseSearchUserSchoolClass(Boolean useSearchUserSchoolClass) {
		this.useSearchUserSchoolClass = useSearchUserSchoolClass;
	}

	public EnumQueryComparator getSearchUserSchoolClassComparator() {
		return this.searchUserSchoolClassComparator;
	}

	public void setSearchUserSchoolClassComparator(EnumQueryComparator searchUserSchoolClassComparator) {
		this.searchUserSchoolClassComparator = searchUserSchoolClassComparator;
	}

	public String getSearchUserSchoolClass() {
		return this.searchUserSchoolClass;
	}

	public void setSearchUserSchoolClass(String searchUserSchoolClass) {
		this.searchUserSchoolClass = searchUserSchoolClass;
	}

	public Boolean getUseSearchUserRanks() {
		return useSearchUserRanks;
	}

	public void setUseSearchUserRanks(Boolean useSearchUserRanks) {
		this.useSearchUserRanks = useSearchUserRanks;
	}

	public List<@TypeNotNull @NotBlankString String> getSearchUserRanks() {
		return searchUserRanks;
	}

	public void setSearchUserRanks(List<@TypeNotNull @NotBlankString String> searchUserRanks) {
		this.searchUserRanks = searchUserRanks;
	}

	public EnumQueryComparator getSearchUserRanksComparator() {
		return searchUserRanksComparator;
	}

	public void setSearchUserRanksComparator(EnumQueryComparator searchUserRanksComparator) {
		this.searchUserRanksComparator = searchUserRanksComparator;
	}

	public void searchUsers() {
		WebUtils.executeTask((params) -> {
			List<User> results = userBean.findUsers(searchUserId, searchUserForename, searchUserSurname,
					EnumSchoolClass.getMatchingClasses(useSearchUserSchoolGrade ? searchUserSchoolGrade : null,
							useSearchUserSchoolClass ? searchUserSchoolClass : null, searchUserSchoolGradeComparator,
							searchUserSchoolClassComparator),
					searchUserType, searchUserRanks, useSearchUserId, useSearchUserForename, useSearchUserSurname,
					useSearchUserSchoolGrade || useSearchUserSchoolClass, useSearchUserType, useSearchUserRanks, searchUserIdComparator,
					searchUserForenameComparator, searchUserSurnameComparator, searchUserTypeComparator, searchUserRanksComparator);
			searchUserResults.clear();
			selectedUsers.clear();
			searchUserResults.addAll(results);
			params.add(results.size());
			return true;
		}, "lucas.application.userScreen.searchUser.message");
	}

	public void refreshUsers() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(User.class, searchUserResults, selectedUsers, userBean::findById, false);
			params.add(searchUserResults.size());
			return true;
		}, "lucas.application.userScreen.refreshUsers");
	}

	public List<User> getSearchUserResults() {
		return searchUserResults;
	}

	public void setSearchUserResults(List<User> searchUserResults) {
		this.searchUserResults = searchUserResults;
	}

	private List<Boolean> resultsDatatableColumns = Arrays.asList(true, true, true, true, true, true);

	public void onToggle(ToggleEvent e) {
		resultsDatatableColumns.set((Integer) e.getData() - 1, e.getVisibility() == Visibility.VISIBLE);
	}

	public List<Boolean> getResultsDatatableColumns() {
		return this.resultsDatatableColumns;
	}

	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

	/*
	 * -------------------- Edit User Dialog Start --------------------
	 */

	@NotBlankString
	private String editUserDialogForename;

	@NotBlankString
	private String editUserDialogSurname;

	private EnumSchoolClass editUserDialogSchoolClass;

	@NotBlank
	private String editUserDialogTmpRank = null;

	private String editUserDialogSelectedRank = null;

	private List<@TypeNotNull @NotBlankString String> editUserDialogRanks = new ArrayList<>();

	private User editUserDialogSelectedUser;

	public String getEditUserDialogForename() {
		return editUserDialogForename;
	}

	public void setEditUserDialogForename(String editUserDialogForename) {
		this.editUserDialogForename = editUserDialogForename;
	}

	public String getEditUserDialogSurname() {
		return editUserDialogSurname;
	}

	public void setEditUserDialogSurname(String editUserDialogSurname) {
		this.editUserDialogSurname = editUserDialogSurname;
	}

	public EnumSchoolClass getEditUserDialogSchoolClass() {
		return editUserDialogSchoolClass;
	}

	public void setEditUserDialogSchoolClass(EnumSchoolClass editUserDialogSchoolClass) {
		this.editUserDialogSchoolClass = editUserDialogSchoolClass;
	}

	public String getEditUserDialogTmpRank() {
		return editUserDialogTmpRank;
	}

	public void setEditUserDialogTmpRank(String editUserDialogTmpRank) {
		this.editUserDialogTmpRank = editUserDialogTmpRank;
	}

	public void resetEditUserDialogTmpRank() {
		editUserDialogTmpRank = null;
	}

	public String getEditUserDialogSelectedRank() {
		return editUserDialogSelectedRank;
	}

	public void setEditUserDialogSelectedRank(String editUserDialogSelectedRank) {
		this.editUserDialogSelectedRank = editUserDialogSelectedRank;
	}

	public List<@TypeNotNull @NotBlankString String> getEditUserDialogRanks() {
		return editUserDialogRanks;
	}

	public void setEditUserDialogRanks(List<@TypeNotNull @NotBlankString String> editUserDialogRanks) {
		this.editUserDialogRanks = editUserDialogRanks;
	}

	public void editEditUserDialogSelectedRank() {
		if (editUserDialogSelectedRank != null) {
			this.editUserDialogRanks.remove(editUserDialogSelectedRank);
			this.editUserDialogTmpRank = editUserDialogSelectedRank;
			editUserDialogSelectedRank = null;
		}
	}

	public EnumUserType computeUserType() {
		return (editUserDialogForename == null || editUserDialogSurname == null ? EnumUserType.GUEST
				: (editUserDialogSchoolClass == null ? EnumUserType.TEACHER : EnumUserType.PUPIL));
	}

	public void initEditUserDialog() {
		if (!this.selectedUsers.isEmpty()) {
			editUserDialogSelectedUser = this.selectedUsers.get(0);
			this.editUserDialogForename = editUserDialogSelectedUser.getForename();
			this.editUserDialogSurname = editUserDialogSelectedUser.getSurname();
			this.editUserDialogSchoolClass = editUserDialogSelectedUser.getSchoolClass();
			this.editUserDialogRanks.clear();
			this.editUserDialogRanks.addAll(editUserDialogSelectedUser.getRanks());
			this.editUserDialogTmpRank = null;
			this.editUserDialogSelectedRank = null;
		}
	}

	public void editUser() {
		if (editUserDialogSelectedUser != null) {
			WebUtils.executeTask(params -> {
				Long id = editUserDialogSelectedUser.getId();
				if (SecurityUtils.getSubject().isPermitted(EnumPermission.USER_SET_FORENAME.getPermissionString())) {
					userBean.setForename(id, editUserDialogForename);
				}
				if (SecurityUtils.getSubject().isPermitted(EnumPermission.USER_SET_SURNAME.getPermissionString())) {
					userBean.setSurname(id, editUserDialogSurname);
				}
				if (SecurityUtils.getSubject().isPermitted(EnumPermission.USER_SET_SCHOOL_CLASS.getPermissionString())) {
					userBean.setSchoolClass(id, editUserDialogSchoolClass);
				}
				User tmp = userBean.findById(id);
				if (SecurityUtils.getSubject().isPermitted(EnumPermission.USER_ADD_RANK.getPermissionString())) {
					editUserDialogRanks.forEach(rank -> {
						if (!tmp.getRanks().contains(rank)) {
							userBean.addRank(id, rank);
						}
					});
				}
				if (SecurityUtils.getSubject().isPermitted(EnumPermission.USER_REMOVE_RANK.getPermissionString())) {
					tmp.getRanks().forEach(rank -> {
						if (!editUserDialogRanks.contains(rank)) {
							userBean.removeRank(id, rank);
						}
					});
				}
				User tmp2 = userBean.findById(id);
				params.add(WebUtils.getAsString(tmp, "lucas:userStringConverter"));
				WebUtils.refreshEntities(User.class, searchUserResults, selectedUsers, tmp2, userBean::findById, true);
				return true;
			}, "lucas.application.userScreen.editUser.message");
		}
	}

	/*
	 * -------------------- Edit User Dialog End --------------------
	 */

	/*
	 * -------------------- Image Manager Dialog Start --------------------
	 */

	private String imageManagerDialogDisplayImageAsBase64 = null;

	private byte[] imageManagerDialogUploadedImage = null;

	private User imageManagerDialogSelectedUser;

	public static final String IMAGE_DIALOG_MESSAGES_ID = "imageManagerDialogMessages";

	public Long getImageManagerDialogCurrentUserId() {
		return imageManagerDialogSelectedUser.getId();
	}

	public String getImageManagerDialogDisplayImageAsBase64() {
		return this.imageManagerDialogDisplayImageAsBase64;
	}

	public User getImageManagerDialogSelectedUser() {
		return this.imageManagerDialogSelectedUser;
	}

	public Integer getMaxUserImageWidth() {
		return globalDataBean.getMaxUserImageWidth();
	}

	public Integer getMaxUserImageHeight() {
		return globalDataBean.getMaxUserImageHeight();
	}

	public void initImageManagerDialog() {
		if (!this.selectedUsers.isEmpty()) {
			imageManagerDialogSelectedUser = this.selectedUsers.get(0);
			imageManagerDialogUploadedImage = null;
			byte[] currentImage = userBean.getImage(imageManagerDialogSelectedUser.getId());
			imageManagerDialogDisplayImageAsBase64 = currentImage != null ? Base64.getEncoder().encodeToString(currentImage) : null;
		}
	}

	public void onImageUpload(FileUploadEvent event) {
		final UploadedFile file = event.getFile();
		WebUtils.executeTask(params -> {
			if (file.getContentType().equals(WebUtils.JPEG_MIME)) {
				imageManagerDialogUploadedImage = file.getContents();
				BufferedImage image = WebUtils.getBufferedImageFromBytes(imageManagerDialogUploadedImage);
				image = WebUtils.getBufferedImage(
						image.getScaledInstance(getMaxUserImageWidth(), getMaxUserImageHeight(), BufferedImage.SCALE_SMOOTH),
						BufferedImage.TYPE_INT_RGB);
				imageManagerDialogUploadedImage = WebUtils.convertBufferedImageToBytes(image, WebUtils.JPEG_TYPE);
				imageManagerDialogDisplayImageAsBase64 = Base64.getEncoder().encodeToString(imageManagerDialogUploadedImage);
				return true;
			}
			return false;
		}, "lucas.application.userScreen.uploadUserImage.message", IMAGE_DIALOG_MESSAGES_ID, Utils.asList(file.getFileName()));
	}

	public void onSave() {
		WebUtils.executeTask(params -> {
			return userBean.setImage(imageManagerDialogSelectedUser.getId(), imageManagerDialogUploadedImage);
		}, "lucas.application.userScreen.changeUserImage.message",
				Utils.asList(WebUtils.getAsString(imageManagerDialogSelectedUser, "lucas:userStringConverter")));
	}

	/*
	 * -------------------- Image Manager Dialog End --------------------
	 */

	/*
	 * -------------------- User Card Manager Dialog Start --------------------
	 */

	private User userCardManagerDialogSelectedUser;

	private List<UserCard> userCardManagerDialogSelectedUserCards = new ArrayList<>();

	private List<UserCard> userCardManagerDialogUserCards = new ArrayList<>();

	private Date userCardManagerDialogValidDate = Date.from(Instant.now());

	public static final String USER_CARD_MANAGER_DIALOG_MESSAGES_ID = "userCardManagerDialogMessages";

	public List<UserCard> getUserCardManagerDialogUserCards() {
		return this.userCardManagerDialogUserCards;
	}

	public List<UserCard> getUserCardManagerDialogSelectedUserCards() {
		return this.userCardManagerDialogSelectedUserCards;
	}

	public void setUserCardManagerDialogSelectedUserCards(List<UserCard> userCardManagerDialogSelectedUserCards) {
		this.userCardManagerDialogSelectedUserCards = userCardManagerDialogSelectedUserCards;
	}

	public User getUserCardManagerDialogSelectedUser() {
		return this.userCardManagerDialogSelectedUser;
	}

	public Date getUserCardManagerDialogValidDate() {
		return userCardManagerDialogValidDate;
	}

	public void setUserCardManagerDialogValidDate(Date userCardManagerDialogValidDate) {
		this.userCardManagerDialogValidDate = userCardManagerDialogValidDate;
	}

	public void initUserCardManagerDialog() {
		if (!selectedUsers.isEmpty()) {
			userCardManagerDialogSelectedUser = selectedUsers.get(0);
			userCardManagerDialogSelectedUserCards.clear();
			userCardManagerDialogUserCards.clear();
			userCardManagerDialogValidDate = Date.from(Instant.now());
			userCardManagerDialogUserCards.addAll(userBean.getUserCards(userCardManagerDialogSelectedUser.getId()));
		}
	}

	public void userCardManagerDialogNewUserCard() {
		if (userCardManagerDialogSelectedUser != null) {
			WebUtils.executeTask(params -> {
				Long id = userBean.addUserCard(userCardManagerDialogSelectedUser.getId());
				userCardManagerDialogUserCards.add(userBean.findUserCardById(id));
				params.add(id);
				return true;
			}, "lucas.application.userScreen.createUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUser, "lucas:userStringConverter")));
		}
	}

	public void userCardManagerDialogBlockUserCards() {
		for (UserCard card : userCardManagerDialogSelectedUserCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return userBean.blockUserCard(id);
			}, "lucas.application.userScreen.blockUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUser, "lucas:userStringConverter")))) {
				UserCard newCard = userBean.findUserCardById(id);
				WebUtils.refreshEntities(UserCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
						userBean::findUserCardById, true);
			}
		}
	}

	public void userCardManagerDialogUnblockUserCards() {
		for (UserCard card : userCardManagerDialogSelectedUserCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(id);
				return userBean.unblockUserCard(id);
			}, "lucas.application.userScreen.unblockUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
					Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUser, "lucas:userStringConverter")))) {
				UserCard newCard = userBean.findUserCardById(id);
				WebUtils.refreshEntities(UserCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
						userBean::findUserCardById, true);
			}
		}
	}

	public void initUserCardManagerDialogSetValidDayOverlay() {
		if (!userCardManagerDialogSelectedUserCards.isEmpty()) {
			LocalDate validDay = userCardManagerDialogSelectedUserCards.get(0).getValidDay();
			this.userCardManagerDialogValidDate = validDay != null ? Utils.asDate(validDay) : Date.from(Instant.now());
		}
	}

	public void userCardManagerDialogSetValidDay() {
		for (UserCard card : userCardManagerDialogSelectedUserCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				params.add(WebUtils.getAsString(Utils.asLocalDate(userCardManagerDialogValidDate), "lucas:localDateStringConverter"));
				params.add(WebUtils.getAsString(card.getValidDay(), "lucas:localDateStringConverter"));
				return userBean.setValidDate(id, Utils.asLocalDate(userCardManagerDialogValidDate));
			}, "lucas.application.userScreen.setValidDay", USER_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				UserCard newCard = userBean.findUserCardById(id);
				WebUtils.refreshEntities(UserCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
						userBean::findUserCardById, true);
			}
		}
	}

	public void userCardManagerDialogRemoveValidDay() {
		for (UserCard card : userCardManagerDialogSelectedUserCards) {
			Long id = card.getId();
			if (WebUtils.executeTask(params -> {
				params.add(card.getId());
				return userBean.setValidDate(id, null);
			}, "lucas.application.userScreen.removeValidDay", USER_CARD_MANAGER_DIALOG_MESSAGES_ID)) {
				UserCard newCard = userBean.findUserCardById(id);
				WebUtils.refreshEntities(UserCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards, newCard,
						userBean::findUserCardById, true);
			}
		}
	}

	public void userCardManagerDialogRemoveUserCards() {
		if (!userCardManagerDialogSelectedUserCards.isEmpty()) {
			ListIterator<UserCard> it = userCardManagerDialogSelectedUserCards.listIterator();
			while (it.hasNext()) {
				WebUtils.executeTask(params -> {
					UserCard userCard = it.next();
					Long id = userCard.getId();
					Boolean removed = userBean.removeUserCard(id);
					if (removed) {
						userCardManagerDialogUserCards.remove(userCard);
						it.remove();
					}
					params.add(id);
					return removed;
				}, "lucas.application.userScreen.deleteUserCard", USER_CARD_MANAGER_DIALOG_MESSAGES_ID,
						Utils.asList(WebUtils.getAsString(userCardManagerDialogSelectedUser, "lucas:userStringConverter")));

			}
		}
	}

	public void userCardManagerDialogRefreshUserCards() {
		WebUtils.executeTask((params) -> {
			WebUtils.refreshEntities(UserCard.class, userCardManagerDialogUserCards, userCardManagerDialogSelectedUserCards,
					userBean::findUserCardById, true);
			params.add(userCardManagerDialogUserCards.size());
			return true;
		}, "lucas.application.userScreen.refreshUserCards", USER_CARD_MANAGER_DIALOG_MESSAGES_ID);
	}

	/*
	 * -------------------- User Cardö Manager Dialog End --------------------
	 */

}
