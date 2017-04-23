package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.model.Room;

@FacesConverter(RoomConverter.CONVERTER_ID)
public class RoomConverter extends DefaultConverter<Room> {

	public static final String CONVERTER_ID = "lucas:roomConverter";

	public RoomConverter() {
		this(Boolean.FALSE);
	}

	protected RoomConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.roomConverter");
	}

	@Override
	protected Object[] getParamsFromValue(Room value) {
		return new Object[] {
				value.getName(), value.getId() };
	}

	@FacesConverter(ShortRoomConverter.CONVERTER_ID)
	public static class ShortRoomConverter extends RoomConverter {

		public static final String CONVERTER_ID = "lucas:shortRoomConverter";

		public ShortRoomConverter() {
			super(Boolean.TRUE);
		}
	}

}
