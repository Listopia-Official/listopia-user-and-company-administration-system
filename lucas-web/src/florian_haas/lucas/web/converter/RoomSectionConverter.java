package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.RoomBeanLocal;
import florian_haas.lucas.model.ReadOnlyRoomSection;

@FacesConverter(value = RoomSectionConverter.CONVERTER_ID, managed = true)
public class RoomSectionConverter extends DefaultConverter<ReadOnlyRoomSection> {

	public static final String CONVERTER_ID = "lucas:roomSectionConverter";

	// @EJB
	// private RoomBeanLocal roomBean;

	public RoomSectionConverter() {
		this(Boolean.FALSE);
	}

	protected RoomSectionConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.roomSectionConverter", RoomBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyRoomSection value) {
		return new Object[] {
				value.getRoom().getName(), value.getSectionIndex(), value.getId() };
	}

	@Override
	protected ReadOnlyRoomSection getObjectFromId(Object getter, Long id) {
		return ((RoomBeanLocal) getter).findRoomSectionById(id);
	}

	@FacesConverter(ShortRoomSectionConverter.CONVERTER_ID)
	public static class ShortRoomSectionConverter extends RoomSectionConverter {

		public static final String CONVERTER_ID = "lucas:shortRoomSectionConverter";

		public ShortRoomSectionConverter() {
			super(Boolean.TRUE);
		}

	}

}
