package florian_haas.lucas.web;

import java.util.*;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.business.*;
import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.security.EnumPermission;
import florian_haas.lucas.web.converter.*;
import florian_haas.lucas.web.util.WebUtils;

@Named
@ViewScoped
public class RoomBean extends BaseBean<Room> {

	public RoomBean() {
		super("room", 3);
	}

	private static final long serialVersionUID = 9056222365379147492L;

	@EJB
	private RoomBeanLocal roomBean;

	@EJB
	private EntityBeanLocal entityBean;

	@NotNull
	@Min(0)
	private Long searchRoomId = 0l;

	@NotNull
	private Boolean useSearchRoomId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchRoomIdComparator = EnumQueryComparator.EQUAL;

	@NotBlank
	private String searchRoomName = "";

	@NotNull
	private Boolean useSearchRoomName = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.TEXT)
	private EnumQueryComparator searchRoomNameComparator = EnumQueryComparator.EQUAL;

	@NotNull
	@Min(0)
	private Long searchRoomSectionId = 0l;

	@NotNull
	private Boolean useSearchRoomSectionId = Boolean.FALSE;

	@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC)
	private EnumQueryComparator searchRoomSectionIdComparator = EnumQueryComparator.EQUAL;

	public Long getSearchRoomId() {
		return searchRoomId;
	}

	public void setSearchRoomId(Long searchRoomId) {
		this.searchRoomId = searchRoomId;
	}

	public Boolean getUseSearchRoomId() {
		return useSearchRoomId;
	}

	public void setUseSearchRoomId(Boolean useSearchRoomId) {
		this.useSearchRoomId = useSearchRoomId;
	}

	public EnumQueryComparator getSearchRoomIdComparator() {
		return searchRoomIdComparator;
	}

	public void setSearchRoomIdComparator(EnumQueryComparator searchRoomIdComparator) {
		this.searchRoomIdComparator = searchRoomIdComparator;
	}

	public String getSearchRoomName() {
		return searchRoomName;
	}

	public void setSearchRoomName(String searchRoomName) {
		this.searchRoomName = searchRoomName;
	}

	public Boolean getUseSearchRoomName() {
		return useSearchRoomName;
	}

	public void setUseSearchRoomName(Boolean useSearchRoomName) {
		this.useSearchRoomName = useSearchRoomName;
	}

	public EnumQueryComparator getSearchRoomNameComparator() {
		return searchRoomNameComparator;
	}

	public void setSearchRoomNameComparator(EnumQueryComparator searchRoomNameComparator) {
		this.searchRoomNameComparator = searchRoomNameComparator;
	}

	public Long getSearchRoomSectionId() {
		return searchRoomSectionId;
	}

	public void setSearchRoomSectionId(Long searchRoomSectionId) {
		this.searchRoomSectionId = searchRoomSectionId;
	}

	public Boolean getUseSearchRoomSectionId() {
		return useSearchRoomSectionId;
	}

	public void setUseSearchRoomSectionId(Boolean useSearchRoomSectionId) {
		this.useSearchRoomSectionId = useSearchRoomSectionId;
	}

	public EnumQueryComparator getSearchRoomSectionIdComparator() {
		return searchRoomSectionIdComparator;
	}

	public void setSearchRoomSectionIdComparator(EnumQueryComparator searchRoomSectionIdComparator) {
		this.searchRoomSectionIdComparator = searchRoomSectionIdComparator;
	}

	@Override
	public EnumPermission getFindDynamicPermission() {
		return EnumPermission.ROOM_FIND_DYNAMIC;
	}

	@Override
	public EnumPermission getPrintPermission() {
		return EnumPermission.ROOM_PRINT;
	}

	@Override
	public EnumPermission getExportPermission() {
		return EnumPermission.ROOM_EXPORT;
	}

	@Override
	protected List<Room> searchEntities() {
		return roomBean.findRooms(searchRoomId, searchRoomName, searchRoomSectionId, useSearchRoomId, useSearchRoomName, useSearchRoomSectionId,
				searchRoomIdComparator, searchRoomNameComparator, searchRoomSectionIdComparator);
	}

	@Override
	protected Room entityGetter(Long entityId) {
		return roomBean.findById(entityId);
	}

	/*
	 * -------------------- Create Room Dialog Start --------------------
	 */

	@NotBlank
	private String createRoomDialogName;

	@NotNull
	@Min(0)
	@Max(25)
	private Integer createRoomDialogSectionCount = 0;

	public String getCreateRoomDialogName() {
		return createRoomDialogName;
	}

	public void setCreateRoomDialogName(String createRoomDialogName) {
		this.createRoomDialogName = createRoomDialogName;
	}

	public Integer getCreateRoomDialogSectionCount() {
		return createRoomDialogSectionCount;
	}

	public void setCreateRoomDialogSectionCount(Integer createRoomDialogSectionCount) {
		this.createRoomDialogSectionCount = createRoomDialogSectionCount;
	}

	public void initCreateRoomDialog() {
		createRoomDialogName = null;
		createRoomDialogSectionCount = 0;
	}

	public void createRoom() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(roomBean.findById(roomBean.createRoom(createRoomDialogName, createRoomDialogSectionCount)),
					RoomConverter.CONVERTER_ID));
			return true;
		}, "lucas.application.roomScreen.createRoom.message", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.roomScreen.createRoom.message.notUniqueName", createRoomDialogName);
		});
	}

	/*
	 * -------------------- Create Room Dialog End --------------------
	 */

	/*
	 * -------------------- Edit Room Dialog Start --------------------
	 */

	@NotBlank
	private String editRoomDialogName;

	private Room editRoomDialogSelectedRoom;

	public String getEditRoomDialogName() {
		return editRoomDialogName;
	}

	public void setEditRoomDialogName(String editRoomDialogName) {
		this.editRoomDialogName = editRoomDialogName;
	}

	public void initEditRoomDialog() {
		if (!selectedEntities.isEmpty()) {
			editRoomDialogSelectedRoom = selectedEntities.get(0);
			editRoomDialogName = editRoomDialogSelectedRoom.getName();
		}
	}

	public void editRoom() {
		WebUtils.executeTask((params) -> {
			Long id = editRoomDialogSelectedRoom.getId();
			roomBean.setName(id, editRoomDialogName);
			Room tmp = roomBean.findById(id);
			params.add(WebUtils.getAsString(tmp, RoomConverter.CONVERTER_ID));
			params.add(editRoomDialogName);
			WebUtils.refreshEntities(Room.class, searchResults, selectedEntities, tmp, roomBean::findById, true);
			return true;
		}, "lucas.application.roomScreen.editRoom.message", (exception, params) -> {
			return WebUtils.getTranslatedMessage("lucas.application.roomScreen.editRoom.message.notUniqueName", editRoomDialogName);
		});
	}

	/*
	 * -------------------- Edit Room Dialog End --------------------
	 */

	/*
	 * -------------------- Section Manager Dialog Start --------------------
	 */

	private Room sectionManagerDialogSelectedRoom;

	private List<RoomSection> sectionManagerDialogSelectedSections = new ArrayList<>();

	public static final String SECTION_MANAGER_DIALOG_MESSAGES_COMPONENT_ID = "sectionManagerDialogMessages";

	public Room getSectionManagerDialogSelectedRoom() {
		return this.sectionManagerDialogSelectedRoom;
	}

	public List<RoomSection> getSectionManagerDialogSelectedSections() {
		return this.sectionManagerDialogSelectedSections;
	}

	public void setSectionManagerDialogSelectedSections(List<RoomSection> sectionManagerDialogSelectedSections) {
		this.sectionManagerDialogSelectedSections = sectionManagerDialogSelectedSections;
	}

	public void initSectionManagerDialog() {
		if (!selectedEntities.isEmpty()) {
			sectionManagerDialogSelectedRoom = selectedEntities.get(0);
		}
	}

	public void addSection() {
		WebUtils.executeTask((params) -> {
			params.add(WebUtils.getAsString(roomBean.findRoomSectionById(roomBean.addSection(sectionManagerDialogSelectedRoom.getId())),
					RoomSectionConverter.CONVERTER_ID));
			Room tmp = roomBean.findById(sectionManagerDialogSelectedRoom.getId());
			sectionManagerDialogSelectedRoom = tmp;
			WebUtils.refreshEntities(Room.class, searchResults, selectedEntities, tmp, roomBean::findById, true);
			return true;
		}, "lucas.application.roomScreen.createRoomSection", SECTION_MANAGER_DIALOG_MESSAGES_COMPONENT_ID);
	}

	public void removeSection() {
		sectionManagerDialogSelectedSections.forEach(section -> {
			WebUtils.executeTask((params) -> {
				params.add(WebUtils.getAsString(section, RoomSectionConverter.CONVERTER_ID));
				Boolean success = roomBean.removeSection(sectionManagerDialogSelectedRoom.getId(), section.getId());
				if (success) {
					Room tmp = roomBean.findById(sectionManagerDialogSelectedRoom.getId());
					sectionManagerDialogSelectedRoom = tmp;
					WebUtils.refreshEntities(Room.class, searchResults, selectedEntities, tmp, roomBean::findById, true);
				}
				return success;
			}, "lucas.application.roomScreen.removeRoomSection", SECTION_MANAGER_DIALOG_MESSAGES_COMPONENT_ID);
		});
	}

	/*
	 * -------------------- Section Manager Dialog End --------------------
	 */

}
