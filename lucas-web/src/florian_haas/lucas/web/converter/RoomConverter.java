package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.RoomBeanLocal;
import florian_haas.lucas.model.ReadOnlyRoom;

@FacesConverter(value = RoomConverter.CONVERTER_ID, managed = true)
public class RoomConverter extends DefaultConverter<ReadOnlyRoom> {

	public static final String CONVERTER_ID = "lucas:roomConverter";

	// @EJB
	// private RoomBeanLocal roomBean;

	public RoomConverter() {
		this(Boolean.FALSE);
	}

	protected RoomConverter(Boolean isShortConverter) {
		super(isShortConverter, "lucas.application.roomConverter", RoomBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyRoom value) {
		return new Object[] {
				value.getName(), value.getId() };
	}

	@Override
	protected ReadOnlyRoom getObjectFromId(Object getter, Long id) {
		return ((RoomBeanLocal) getter).findById(id);
	}

	@FacesConverter(ShortRoomConverter.CONVERTER_ID)
	public static class ShortRoomConverter extends RoomConverter {

		public static final String CONVERTER_ID = "lucas:shortRoomConverter";

		public ShortRoomConverter() {
			super(Boolean.TRUE);
		}
	}

}
