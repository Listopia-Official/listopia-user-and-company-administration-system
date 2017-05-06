package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.ReadOnlyRoomSection;

@FacesConverter(RoomSectionConverter.CONVERTER_ID)
public class RoomSectionConverter extends DefaultConverter<ReadOnlyRoomSection> {

	public static final String CONVERTER_ID = "lucas:roomSectionConverter";

	public RoomSectionConverter() {
		this(Boolean.FALSE);
	}

	protected RoomSectionConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.roomSectionConverter");
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyRoomSection value) {
		return new Object[] {
				value.getRoom().getName(), value.getSectionIndex(), value.getId() };
	}

	@FacesConverter(ShortRoomSectionConverter.CONVERTER_ID)
	public static class ShortRoomSectionConverter extends RoomSectionConverter {

		public static final String CONVERTER_ID = "lucas:shortRoomSectionConverter";

		public ShortRoomSectionConverter() {
			super(Boolean.TRUE);
		}

	}

}
