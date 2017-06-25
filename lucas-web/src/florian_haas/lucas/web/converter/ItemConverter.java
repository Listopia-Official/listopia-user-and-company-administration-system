package florian_haas.lucas.web.converter;

import javax.faces.convert.FacesConverter;

import florian_haas.lucas.business.ItemBeanLocal;
import florian_haas.lucas.model.ReadOnlyItem;

@FacesConverter(value = ItemConverter.CONVERTER_ID, managed = true)
public class ItemConverter extends DefaultConverter<ReadOnlyItem> {

	public static final String CONVERTER_ID = "lucas:itemConverter";

	public ItemConverter() {
		super(Boolean.FALSE, "lucas.application.itemConverter", ItemBeanLocal.class);
	}

	@Override
	protected Object[] getParamsFromValue(ReadOnlyItem value) {
		return new Object[] {
				value.getId(), value.getName() };
	}

	@Override
	protected ReadOnlyItem getObjectFromId(Object getter, Long id) {
		return ((ItemBeanLocal) getter).findById(id);
	}

}
